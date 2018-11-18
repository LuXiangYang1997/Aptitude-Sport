package com.huasport.smartsport.ui.discover.vm;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.HttpLog;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.api.ShareStatusCallback;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.LightsocialsearchLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.LightSocialSearchAdapter;
import com.huasport.smartsport.ui.discover.bean.CommandPraiseBean;
import com.huasport.smartsport.ui.discover.bean.LightSocialSearchBean;
import com.huasport.smartsport.ui.discover.bean.PicBean;
import com.huasport.smartsport.ui.discover.view.LightSocialSearchActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ShareUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.edittext.EdittextListener;
import com.huasport.smartsport.util.edittext.EdittextUtil;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.util.popwindow.ShareCallBack;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.huasport.smartsport.wxapi.ThirdPart;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LightSocailSearchVm extends BaseViewModel implements View.OnClickListener, RefreshLoadMoreListener, EdittextListener {

    private final ThirdPart mThirdPart;
    private LightSocialSearchActivity lightSocialSearchActivity;
    private LightSocialSearchAdapter lightSocialSearchAdapter;
    private LightsocialsearchLayoutBinding binding;
    private int page = 1;
    private String token;
    private String loadtype = "all";
    private List<LightSocialSearchBean.ResultBean.ListBean> dataList = new ArrayList();
    private List allList = new ArrayList();//全部数据
    private List attentionList = new ArrayList();//关注数据
    private List dynamicList = new ArrayList();//动态数据
    private List articleList = new ArrayList();//文章数据
    private int allPage = 1;
    private int userPage = 1;
    private int articlePage = 1;
    private int dynamicPage = 1;
    private int allTotalPage = 0;
    private int userTotalPage = 0;
    private int dynamicTotalPage = 0;
    private int articleTotalPage = 0;
    private String allKeyWord = "";
    private String userKeyWord = "";
    private String dynamicKeyWord = "";
    private String articleKeyWord = "";
    private String tabType;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private MyApplication application;
    private ShareUtil shareUtil;
    private UserBean userBean;
    private String registerCode = "";
    private int totalPage = 0;


    public LightSocailSearchVm(LightSocialSearchActivity lightSocialSearchActivity, LightSocialSearchAdapter lightSocialSearchAdapter) {
        this.lightSocialSearchActivity = lightSocialSearchActivity;
        this.lightSocialSearchAdapter = lightSocialSearchAdapter;
        binding = lightSocialSearchActivity.getBinding();
        mThirdPart = new ThirdPart(lightSocialSearchActivity);
        init();
        initClick();
        tabData("全部");
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化toast
        toastUtil = new ToastUtil(lightSocialSearchActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(lightSocialSearchActivity, R.style.LoadingDialog);
        //初始化Application
        application = MyApplication.getInstance();
        //初始化刷新加载
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //初始化分享
        shareUtil = new ShareUtil(lightSocialSearchActivity);
        //初始化搜索监听
        binding.lightsocialSearchEdittext.setOnKeyListener(onKeyListener);
        //初始化Edittext监听
        EdittextUtil edittextUtil = new EdittextUtil(binding.lightsocialSearchEdittext,this);
        userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
            registerCode = userBean.getRegisterCode();
        } else {
            token = "";
            registerCode = "";
        }
    }

    /**
     * 初始化数据
     * @param edit
     * @param loadStatus
     * @param tabType
     */
    private void initData(String edit, final String loadStatus, final String tabType) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.ALLSEARCH);
        params.put("baseUrl", Config.baseUrl2);
        params.put("keyword", edit);
        params.put("token", token);
        params.put("type", tabType);
        params.put("currentPage", page + "");
        params.put("pageSize", "10");
        Log.e("params", params.toString());

        OkHttpUtil.getRequest(lightSocialSearchActivity, params, new RequestCallBack<LightSocialSearchBean>() {
            @Override
            public void onSuccess(Response<LightSocialSearchBean> response) {
                LightSocialSearchBean lightSocialSearchBean = response.body();
                if (!EmptyUtil.isEmpty(lightSocialSearchBean)) {
                    int resultCode = lightSocialSearchBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        LightSocialSearchBean.ResultBean resultBean = lightSocialSearchBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            totalPage = resultBean.getTotalPage();
                            if (totalPage == 0) {
                                NullStateUtil.setNullState(binding.nulldata, true);
                            } else {
                                NullStateUtil.setNullState(binding.nulldata, false);
                            }
                            List<LightSocialSearchBean.ResultBean.ListBean> list = resultBean.getList();

                            if (loadStatus.equals("load")) {
                                dataList.clear();
                                dataList.addAll(list);
                                binding.smartRefreshlayout.finishRefresh();
                                lightSocialSearchAdapter.loadData(list);
                            } else {
                                dataList.addAll(list);
                                binding.smartRefreshlayout.finishLoadMore();
                                lightSocialSearchAdapter.loadMoreData(list);
                            }

                            page++;
                            if (tabType.equals("all")) {
                                allList.clear();
                                allPage = page;
                                allTotalPage = totalPage;
                                allList.addAll(dataList);
                            } else if (tabType.equals("user")) {
                                attentionList.clear();
                                userPage = page;
                                userTotalPage = totalPage;
                                attentionList.addAll(dataList);
                            } else if (tabType.equals("dynamic")) {
                                dynamicList.clear();
                                dynamicPage = page;
                                dynamicTotalPage = totalPage;
                                dynamicList.addAll(dataList);
                            } else if (tabType.equals("article")) {
                                articleList.clear();
                                articlePage = page;
                                articleTotalPage = totalPage;
                                articleList.addAll(dataList);
                            }

                        }
                    } else {
                        NullStateUtil.setNullState(binding.nulldata, true);
                    }

                }


            }

            @Override
            public LightSocialSearchBean parseNetworkResponse(String jsonResult) {
                LightSocialSearchBean lightSocialSearchBean = JSON.parseObject(jsonResult, LightSocialSearchBean.class);

                return lightSocialSearchBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    /**
     * 取消点击事件
     */
    public void cancelSearch() {

        lightSocialSearchActivity.finish();

    }

    /**
     * 搜索点击事件
     */
    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                /*隐藏软键盘*/
                InputMethodManager inputMethodManager = (InputMethodManager) lightSocialSearchActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                String text = binding.lightsocialSearchEdittext.getText().toString();
                page = 1;
                if (tabType.equals("all")) {
                    allKeyWord = text;
                    initData(allKeyWord, "load", tabType);
                } else if (tabType.equals("user")) {
                    userKeyWord = text;
                    initData(userKeyWord, "load", tabType);
                } else if (tabType.equals("dynamic")) {
                    dynamicKeyWord = text;
                    initData(dynamicKeyWord, "load", tabType);
                } else if (tabType.equals("article")) {
                    articleKeyWord = text;
                    initData(articleKeyWord, "load", tabType);
                }
//                }
                return true;
            }
            return false;
        }
    };

    /**
     * 清除输入框
     */
    public void clearEdittext() {

        binding.lightsocialSearchEdittext.setText("");

    }

    /**
     * 图标点击搜索事件
     */
    public void iconGoSearch() {
        String text = binding.lightsocialSearchEdittext.getText().toString();
        page = 1;
        if (tabType.equals("all")) {
            allKeyWord = text;
            initData(allKeyWord, "load", tabType);//搜索
        } else if (tabType.equals("user")) {
            userKeyWord = text;
            initData(userKeyWord, "load", tabType);//搜索
        } else if (tabType.equals("dynamic")) {
            dynamicKeyWord = text;
            initData(dynamicKeyWord, "load", tabType);//搜索
        } else if (tabType.equals("article")) {
            articleKeyWord = text;
            initData(articleKeyWord, "load", tabType);//搜索
        }


    }

    /**
     * 分享
     *
     * @param dataBean
     * @param position
     */
    private void sharePop(final LightSocialSearchBean.ResultBean.ListBean dataBean, final int position) {

        final String nickName = application.getUserBean().getNickName();

        PopWindowUtil.share(lightSocialSearchActivity, new ShareCallBack() {
            @Override
            public void wechatQuanShare(PopupWindow popupWindow) {

                shareUtil.shareFriendsQuan(dataBean.getShareURl(), "", R.mipmap.icon_share, nickName);
                HttpLog.shareHttp(lightSocialSearchActivity, token, dataBean.getId(), new ShareStatusCallback() {
                    @Override
                    public void shareSuccess() {
                        dataList.get(position).setShareCount(dataList.get(position).getShareCount() + 1);
                        lightSocialSearchAdapter.loadData(dataList);
                    }

                    @Override
                    public void shareFailed() {

                    }
                });
            }

            @Override
            public void weChatFriendsShare(PopupWindow popupWindow) {

                shareUtil.shareFriends(dataBean.getShareURl(), dataBean.getContent(), R.mipmap.icon_share, nickName);
                HttpLog.shareHttp(lightSocialSearchActivity, token, dataBean.getId(), new ShareStatusCallback() {
                    @Override
                    public void shareSuccess() {
                        dataList.get(position).setShareCount(dataList.get(position).getShareCount() + 1);
                        lightSocialSearchAdapter.loadData(dataList);
                    }

                    @Override
                    public void shareFailed() {

                    }
                });
            }
        });
    }

    /**
     * 点赞
     */
    public void praise(String id, final int position) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("socialInfoId", id);
        params.put("baseMethod", Method.PRAISE);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.postRequest(lightSocialSearchActivity, params, new RequestCallBack<CommandPraiseBean>() {
            @Override
            public void onSuccess(Response<CommandPraiseBean> response) {
                CommandPraiseBean commandPraiseBean = response.body();
                if (!EmptyUtil.isEmpty(commandPraiseBean)) {
                    int resultCode = commandPraiseBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        String resultMsg = commandPraiseBean.getResultMsg();
                        if (resultMsg.equals(lightSocialSearchActivity.getResources().getString(R.string.success_zan))) {

                            if (!EmptyUtil.isEmpty(dataList.get(position))) {
                                dataList.get(position).setIsLike("1");
                                dataList.get(position).setLikesCount(dataList.get(position).getLikesCount() + 1);
                                lightSocialSearchAdapter.loadData(dataList);
                            }

                        } else if (resultMsg.equals(lightSocialSearchActivity.getResources().getString(R.string.cancel_zan))) {

                            if (!EmptyUtil.isEmpty(dataList.get(position))) {
                                dataList.get(position).setIsLike("0");
                                if (dataList.get(position).getLikesCount() != 0) {
                                    dataList.get(position).setLikesCount(dataList.get(position).getLikesCount() - 1);
                                }
                                lightSocialSearchAdapter.loadData(dataList);
                            }
                        }
                        toastUtil.centerToast(resultMsg);
                    }

                }
            }

            @Override
            public CommandPraiseBean parseNetworkResponse(String jsonResult) {

                CommandPraiseBean commandPraiseBean = JSON.parseObject(jsonResult, CommandPraiseBean.class);

                return commandPraiseBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.centerToast(msg);
                }
            }
        });
//        OkhttpUtils.postRequest(lightSocialSearchActivity, params, new BaseHttpCallBack<CommandPraiseBean>(lightSocialSearchActivity, true) {
//            @Override
//            public CommandPraiseBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                CommandPraiseBean commandPraiseBean = JSON.parseObject(jsonResult, CommandPraiseBean.class);
//
//                return commandPraiseBean;
//            }
//
//            @Override
//            public void onSuccess(CommandPraiseBean commandPraiseBean, Call call, Response response) {
//                if (!EmptyUtils.isEmpty(commandPraiseBean)) {
//                    if (commandPraiseBean.getResultCode() == StatusVariable.SUCCESS) {
//                        refreshState();
//                        if (commandPraiseBean.getResultMsg().equals("点赞成功")) {
//                            dataList.get(pos).setIsLike("1");
//                            dataList.get(pos).setLikesCount(dataList.get(pos).getLikesCount() + 1);
//                        } else if (commandPraiseBean.getResultMsg().equals("取消点赞成功")) {
//                            dataList.get(pos).setIsLike("0");
//                            dataList.get(pos).setLikesCount(dataList.get(pos).getLikesCount() - 1);
//                        }
//                        lightSocialSearchAdapter.loadData(dataList);
//                        SharedPreferencesUtils.setParam(lightSocialSearchActivity, "ActivityState", true);
//                    } else {
//                        ToastUtils.toast(lightSocialSearchActivity, commandPraiseBean.getResultMsg());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                ToastUtils.toast(lightSocialSearchActivity, msg);
//            }
//        });


    }

    /**
     * 查看大图
     *
     * @param picsBeansList
     * @param position
     */
    private void showBigImage(List<LightSocialSearchBean.ResultBean.ListBean.PicsBean> picsBeansList, int position) {

        List<PicBean> picBeansList = new ArrayList<>();

        for (int i = 0; i < picsBeansList.size(); i++) {
            PicBean picBean = new PicBean();
            picBean.setHeight(picsBeansList.get(i).getHeight());
            picBean.setSort(picsBeansList.get(i).getSort());
            picBean.setWidth(picsBeansList.get(i).getWidth());
            picBean.setId(picsBeansList.get(i).getId());
            picBean.setUrl(picsBeansList.get(i).getUrl());
            picBeansList.add(picBean);
        }
        PopWindowUtil.lookBigImg(lightSocialSearchActivity, picBeansList, position);
    }

    /**
     * tab切换
     * @param tabStr
     */
    private void tabData(String tabStr) {
        dataList.clear();
        binding.smartRefreshlayout.setNoMoreData(false);
        if (tabStr.equals("全部")) {
            loadtype = "all";
        } else if (tabStr.equals("用户")) {
            loadtype = "user";
        } else if (tabStr.equals("动态")) {
            loadtype = "dynamic";
        } else if (tabStr.equals("文章")) {
            loadtype = "article";
        }
        page = 1;
        //记录上一次选中的tab
        tabType = loadtype;
        //判断是否有数据，有则直接加载，无则重新请求数据
        if (tabType.equals("all")) {
            if (!EmptyUtil.isEmpty(allKeyWord)) {
                binding.lightsocialSearchEdittext.setText(allKeyWord);
            } else {
                binding.lightsocialSearchEdittext.setText("");
            }
            if (allList.size() > 0) {
                NullStateUtil.setNullState(binding.nulldata, false);
                page = allPage;
                totalPage = allTotalPage;
                lightSocialSearchAdapter.loadData(allList);
                dataList.addAll(allList);

                if (page > allTotalPage) {
                    binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
                    binding.smartRefreshlayout.setNoMoreData(true);
                } else {
                    binding.smartRefreshlayout.setNoMoreData(false);
                }

            } else {
                page = 1;
                binding.smartRefreshlayout.setNoMoreData(false);
                initData(allKeyWord, "load", tabType);
            }
        } else if (tabType.equals("user")) {
            if (!EmptyUtil.isEmpty(userKeyWord)) {
                binding.lightsocialSearchEdittext.setText(userKeyWord);
            } else {
                binding.lightsocialSearchEdittext.setText("");
            }
            if (attentionList.size() > 0) {
                NullStateUtil.setNullState(binding.nulldata, false);
                page = userPage;
                totalPage = userTotalPage;
                lightSocialSearchAdapter.loadData(attentionList);
                dataList.addAll(attentionList);

                if (page > userTotalPage) {
                    binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
                    binding.smartRefreshlayout.setNoMoreData(true);
                } else {
                    binding.smartRefreshlayout.setNoMoreData(false);
                }

            } else {
                binding.smartRefreshlayout.setNoMoreData(false);
                page = 1;
                initData(userKeyWord, "load", tabType);
            }
        } else if (tabType.equals("dynamic")) {
            if (!EmptyUtil.isEmpty(dynamicKeyWord)) {
                binding.lightsocialSearchEdittext.setText(dynamicKeyWord);
            } else {
                binding.lightsocialSearchEdittext.setText("");
            }

            if (dynamicList.size() > 0) {
                NullStateUtil.setNullState(binding.nulldata, false);
                page = dynamicPage;
                lightSocialSearchAdapter.loadData(dynamicList);
                dataList.addAll(dynamicList);
                totalPage = dynamicTotalPage;
                if (page > dynamicTotalPage) {
                    binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
                    binding.smartRefreshlayout.setNoMoreData(true);
                } else {
                    binding.smartRefreshlayout.setNoMoreData(false);
                }

            } else {
                page = 1;
                binding.smartRefreshlayout.setNoMoreData(false);
                initData(dynamicKeyWord, "load", tabType);
            }
        } else if (tabType.equals("article")) {
            if (!EmptyUtil.isEmpty(articleKeyWord)) {
                binding.lightsocialSearchEdittext.setText(articleKeyWord);
            } else {
                binding.lightsocialSearchEdittext.setText("");
            }
            if (articleList.size() > 0) {
                NullStateUtil.setNullState(binding.nulldata, false);
                page = articlePage;
                lightSocialSearchAdapter.loadData(articleList);
                dataList.addAll(articleList);
                totalPage = articleTotalPage;

                if (page > articleTotalPage) {
                    binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
                    binding.smartRefreshlayout.setNoMoreData(true);
                } else {
                    binding.smartRefreshlayout.setNoMoreData(false);
                }
            } else {
                binding.smartRefreshlayout.setNoMoreData(false);
                page = 1;
                initData(articleKeyWord, "load", tabType);
            }
        }
    }

    /**
     * 点击事件
     */
    private void initClick() {
        binding.llAll.setOnClickListener(this);
        binding.llUser.setOnClickListener(this);
        binding.llDynamic.setOnClickListener(this);
        binding.llArticle.setOnClickListener(this);

        // SHARE:分享 COMMENT：评论 FAVOUR：点赞
        lightSocialSearchAdapter.setCommentClick(new LightSocialSearchAdapter.CommentClick() {
            @Override
            public void commentclick(int type, LightSocialSearchBean.ResultBean.ListBean dataBean, int position) {
                if (EmptyUtil.isEmpty(token)) {
                    IntentUtil.startActivity(lightSocialSearchActivity, LoginActivity.class);
                    return;
                }
                if (type == StatusVariable.SHARE) {
                    sharePop(dataBean, position);
                } else if (type == StatusVariable.FAVOUR) {
                    praise(dataBean.getId(), position);
                }
            }
        });
        //查看大图
        lightSocialSearchAdapter.setLookImgClick(new LightSocialSearchAdapter.LookSearchImgClick() {
            @Override
            public void lookimgClick(List<LightSocialSearchBean.ResultBean.ListBean.PicsBean> picsBeansList, int position) {
                showBigImage(picsBeansList, position);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_all:
                binding.tvAll.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tabAll.setVisibility(View.VISIBLE);
                binding.tvUser.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabUser.setVisibility(View.GONE);
                binding.tvDynamic.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabDynamic.setVisibility(View.GONE);
                binding.tvArticle.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabArticle.setVisibility(View.GONE);
                tabData("全部");
                break;
            case R.id.ll_user:
                binding.tvAll.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabAll.setVisibility(View.GONE);
                binding.tvUser.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tabUser.setVisibility(View.VISIBLE);
                binding.tvDynamic.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabDynamic.setVisibility(View.GONE);
                binding.tvArticle.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabArticle.setVisibility(View.GONE);
                tabData("用户");
                break;
            case R.id.ll_dynamic:
                binding.tvAll.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabAll.setVisibility(View.GONE);
                binding.tvUser.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabUser.setVisibility(View.GONE);
                binding.tvDynamic.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tabDynamic.setVisibility(View.VISIBLE);
                binding.tvArticle.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabArticle.setVisibility(View.GONE);
                tabData("动态");
                break;
            case R.id.ll_article:
                binding.tvAll.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabAll.setVisibility(View.GONE);
                binding.tvUser.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabUser.setVisibility(View.GONE);
                binding.tvDynamic.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_333333));
                binding.tabDynamic.setVisibility(View.GONE);
                binding.tvArticle.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tabArticle.setVisibility(View.VISIBLE);
                tabData("文章");
                break;

        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //输入大于0的话则显示清除按钮
        if (s.length() > 0) {
            binding.clearEdittext.setVisibility(View.VISIBLE);
        } else {
            binding.clearEdittext.setVisibility(View.GONE);
        }
    }

    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        page = 1;
        binding.smartRefreshlayout.setNoMoreData(false);
        initData(binding.lightsocialSearchEdittext.getText().toString(), "load", tabType);
    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {
        if (page <= totalPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            initData(binding.lightsocialSearchEdittext.getText().toString(), "loadMore", tabType);
        } else {
            binding.smartRefreshlayout.setNoMoreData(true);
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
        }
    }
    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);        String s = lightSocialSearchActivity.refreshStatus.get();
//
//        if (s.equals("refresh")) {
//            lightSocialSearchActivity.refreshStatus.set("normal");
//            clearData();
//            page = 1;
//            toPage = 0;
//            binding.smartRefreshlayout.setNoMoreData(false);
//            initData(binding.lightsocialSearchEdittext.getText().toString(), "load", loadtype);
//        }
//    }
    @Override
    public void onResume() {
        super.onResume();
        userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
            registerCode = userBean.getRegisterCode();
        } else {
            token = "";
            registerCode = "";
        }
    }

}
