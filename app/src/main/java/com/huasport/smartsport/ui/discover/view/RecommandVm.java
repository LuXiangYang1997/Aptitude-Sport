package com.huasport.smartsport.ui.discover.view;

import android.view.View;
import android.widget.EditText;
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
import com.huasport.smartsport.databinding.RecommandLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.RecommandDetailAdapter;
import com.huasport.smartsport.ui.discover.bean.PicBean;
import com.huasport.smartsport.ui.discover.bean.RecommandBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ShareUtil;
import com.huasport.smartsport.util.ToastUtil;
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

public class RecommandVm extends BaseViewModel implements RefreshLoadMoreListener {

    private RecommandActvity recommandActvity;
    private String token;
    private RecommandDetailAdapter recommandDetailAdapter;
    private int page = 1;
    private int totalPage = 0;
    private RecommandLayoutBinding binding;
    private boolean load;
    private ThirdPart mThirdPart;
    private View view;
    private View comment_send;
    private PopupWindow popupWindow;
    private List<RecommandBean.ResultBean.DataBean> dataList = new ArrayList();
    private int mHeight;
    private int mWidth;
    private boolean activityState;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private MyApplication application;
    private String dynamicId;
    private ShareUtil shareUtil;
    private UserBean userBean;
    private String registerCode;
    private String nickName;
    private PopupWindow popWindow;
    private EditText editText;

    public RecommandVm(RecommandActvity recommandActvity, RecommandDetailAdapter recommandDetailAdapter) {
        this.recommandActvity = recommandActvity;
        this.recommandDetailAdapter = recommandDetailAdapter;
        binding = recommandActvity.getBinding();
        init();
        initData(StatusVariable.REFRESH);
        initClick();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化toast
        toastUtil = new ToastUtil(recommandActvity);
        //初始化加载框
        loadingDialog = new LoadingDialog(recommandActvity, R.style.LoadingDialog);
        //初始化Application
        application = MyApplication.getInstance();
        //初始化刷新加载
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //文章Id ，上个界面带来
        dynamicId = recommandActvity.getIntent().getStringExtra("dynamicId");
        //初始化分享
        shareUtil = new ShareUtil(recommandActvity);
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
     *
     * @param loadType
     */
    private void initData(final int loadType) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.RECOMMANDLIST);
        params.put("baseUrl", Config.baseUrl2);
        params.put("currentPage", page + "");
        params.put("pageSize", "10");

        OkHttpUtil.getRequest(recommandActvity, params, new RequestCallBack<RecommandBean>() {
            @Override
            public void onSuccess(Response<RecommandBean> response) {
                RecommandBean recommandBean = response.body();
                if (!EmptyUtil.isEmpty(recommandBean)) {
                    int resultCode = recommandBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        RecommandBean.ResultBean resultBean = recommandBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            totalPage = resultBean.getTotalPage();
                            List<RecommandBean.ResultBean.DataBean> dataBean = resultBean.getData();
                            if (RecommandVm.this.totalPage == 0) {
                                NullStateUtil.setNullState(binding.nulldata, true);
                            } else {
                                NullStateUtil.setNullState(binding.nulldata, false);
                            }
                            if (loadType == StatusVariable.LOADMORE) {
                                binding.smartRefreshlayout.finishLoadMore();
                                recommandDetailAdapter.loadMoreData(dataBean);
                                dataList.addAll(dataBean);
                            } else {
                                binding.smartRefreshlayout.finishRefresh();
                                recommandDetailAdapter.loadData(dataBean);
                                dataList.clear();
                                dataList.addAll(dataBean);
                            }
                        }
                        page++;
                    }
                }
            }

            @Override
            public RecommandBean parseNetworkResponse(String jsonResult) {
                RecommandBean recommandBean = JSON.parseObject(jsonResult, RecommandBean.class);
                return recommandBean;
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
     * 分享
     *
     * @param dataBean
     * @param position
     */
    private void sharePop(final RecommandBean.ResultBean.DataBean dataBean, final int position) {

        if (EmptyUtil.isEmpty(token)) {
            IntentUtil.startActivity(recommandActvity, LoginActivity.class);
            return;
        }
        if (!EmptyUtil.isEmpty(dataBean)) {

            if (!EmptyUtil.isEmpty(userBean)) {
                nickName = userBean.getNickName();
            } else {
                IntentUtil.startActivity(recommandActvity, LoginActivity.class);
            }
            PopWindowUtil.share(recommandActvity, new ShareCallBack() {
                @Override
                public void wechatQuanShare(PopupWindow popupWindow) {
                    shareUtil.shareFriendsQuan(dataBean.getShareURl(), dataBean.getContent(), R.mipmap.icon_share, nickName);
                    HttpLog.shareHttp(recommandActvity, token, dynamicId, new ShareStatusCallback() {
                        @Override
                        public void shareSuccess() {

                        }

                        @Override
                        public void shareFailed() {

                        }
                    });
                }

                @Override
                public void weChatFriendsShare(PopupWindow popupWindow) {
                    shareUtil.shareFriends(dataBean.getShareURl(), dataBean.getContent(), R.mipmap.icon_share, nickName);
                    HttpLog.shareHttp(recommandActvity, token, dynamicId, new ShareStatusCallback() {
                        @Override
                        public void shareSuccess() {

                        }

                        @Override
                        public void shareFailed() {

                        }
                    });
                }
            });
        }

//        token = MyApplication.getToken(recommandActvity);
//
//        View shareView = LayoutInflater.from(recommandActvity).inflate(R.layout.social_sharelayout, null, false);
//        final PopupWindow sharePop = new PopupWindow(shareView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        sharePop.setContentView(shareView);
//        sharePop.showAtLocation(shareView, Gravity.BOTTOM, 0, 0);
//        sharePop.setOutsideTouchable(false);
//        Util.backgroundAlpha(recommandActvity, 0.5f);
//        final String userNick = (String) SharedPreferencesUtils.getParam(recommandActvity, "userNickName", "");
//        //微信好友分享
//        shareView.findViewById(R.id.ll_social_shareWechat).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mThirdPart.wxUrlShare(dataBean.getShareURl() +
//                                "&uSource=share", "来自于" + userNick + "的动态分享"
//                        , dataBean.getContent()
//                        , R.mipmap.logo_share, ThirdPart.WECHAT_FRIEND);
//                LogHttp.shareHttp(recommandActvity, token, dataBean.getId(), new ShareCallBack() {
//                    @Override
//                    public void shareSuccess(ResultBean resultBean) {
//                        if (resultBean.getResultCode() == StatusVariable.SUCCESS) {
//                            SharedPreferencesUtils.setParam(recommandActvity, "ActivityState", true);
//                            dataList.get(position).setShareCount(dataList.get(position).getShareCount() + 1);
//                            recommandDetailAdapter.loadData(dataList);
//                        }
//                    }
//
//                    @Override
//                    public void shareFailed(String code, String msg) {
//
//                    }
//                });
//
//            }
//        });
//
//
//        //微信朋友圈分享
//        shareView.findViewById(R.id.ll_social_sharefriend).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mThirdPart.wxUrlShare(dataBean.getShareURl() +
//                                "&uSource=share", "来自于" + userNick + "的动态分享"
//                        , ""
//                        , R.mipmap.logo_share, ThirdPart.WECHAT_MOMENT);
//                LogHttp.shareHttp(recommandActvity, token, dataBean.getId(), new ShareCallBack() {
//                    @Override
//                    public void shareSuccess(ResultBean resultBean) {
//                        if (resultBean.getResultCode() == StatusVariable.SUCCESS) {
//                            SharedPreferencesUtils.setParam(recommandActvity, "ActivityState", true);
//                            dataList.get(position).setIsLike("1");
//                            dataList.get(position).setLikesCount(dataList.get(position).getShareCount() + 1);
//                            recommandDetailAdapter.loadData(dataList);
//                        }
//                    }
//
//                    @Override
//                    public void shareFailed(String code, String msg) {
//
//                    }
//                });
//
//            }
//        });
//
//
//        shareView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sharePop.dismiss();
//            }
//        });
//
//        sharePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                Util.backgroundAlpha(recommandActvity, 1f);
//            }
//        });
    }

    /**
     * 点赞
     */
    public void praise(String id, final int pos) {
//        token = MyApplication.getToken(recommandActvity);
//        HashMap params = new HashMap();
//        params.put("token", token);
//        params.put("socialInfoId", id);
//        params.put("method", Method.PRAISE);
//        params.put("baseUrl", Config.baseUrl2);
//
//
//        OkhttpUtils.postRequest(recommandActvity, params, new BaseHttpCallBack<CommandPraiseBean>(recommandActvity, true) {
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
//                        if (commandPraiseBean.getResultMsg().equals(recommandActvity.getString(R.string.favour_success))) {
//                            dataList.get(pos).setIsLike("1");
//                            dataList.get(pos).setLikesCount(dataList.get(pos).getLikesCount() + 1);
//                        } else if (commandPraiseBean.getResultMsg().equals(recommandActvity.getString(R.string.cancelfavour_success))) {
//                            dataList.get(pos).setIsLike("0");
//                            dataList.get(pos).setLikesCount(dataList.get(pos).getLikesCount() - 1);
//                        }
//                        SharedPreferencesUtils.setParam(recommandActvity, "ActivityState", true);
//                        recommandDetailAdapter.loadData(dataList);
//                    } else {
//                        ToastUtils.toast(recommandActvity, commandPraiseBean.getResultMsg());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                ToastUtils.toast(recommandActvity, msg);
//
//            }
//        });


    }

    /**
     * 查看大图
     *
     * @param picsBeansList
     */
    private void showBigImage(List<RecommandBean.ResultBean.DataBean.PicsBean> picsBeansList, int position) {

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
        PopWindowUtil.lookBigImg(recommandActvity, picBeansList, position);

    }

    /**
     * 初始化点击事件
     */
    private void initClick() {


        recommandDetailAdapter.setCommentClick(new RecommandDetailAdapter.CommentClick() {
            @Override
            public void commentclick(int type, RecommandBean.ResultBean.DataBean dataBean, int position) {
                if (EmptyUtil.isEmpty(token)) {
                    IntentUtil.startActivity(recommandActvity, LoginActivity.class);
                    return;
                }
                if (type == StatusVariable.SHARE) {
                    sharePop(dataBean, position);
                } else if (type == StatusVariable.FAVOUR) {
                    praise(dataBean.getId(), position);
                }
            }
//                else {
//                    SharedPreferencesUtils.setParam(recommandActvity, "loginstate", true);
//                    Intent intent = new Intent(recommandActvity, LoginActivity.class);
//                    recommandActvity.startActivityForResult(intent,0);
//                }

        });

        //查看大图
        recommandDetailAdapter.setLookImgClick(new RecommandDetailAdapter.LookRecommandImgClick() {
            @Override
            public void lookimgClick(List<RecommandBean.ResultBean.DataBean.PicsBean> picsBeansList, int position) {
                showBigImage(picsBeansList, position);
            }
        });
    }

    /**
     * 刷新
     *
     * @param refreshLayout
     * @param type
     */
    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        page = 1;
        binding.smartRefreshlayout.setNoMoreData(false);
        initData(type);
    }

    /**
     * 加載
     *
     * @param refreshLayout
     * @param type
     */
    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {
        if (page <= totalPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            initData(type);
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }
    }

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
