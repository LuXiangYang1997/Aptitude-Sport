package com.huasport.smartsport.ui.discover.vm;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.ArticledetailLayoutBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.ui.discover.adapter.ArticleDetailAdapter;
import com.huasport.smartsport.ui.discover.bean.ArticleDetailBean;
import com.huasport.smartsport.ui.discover.bean.CommandPraiseBean;
import com.huasport.smartsport.ui.discover.bean.CommentFavourBean;
import com.huasport.smartsport.ui.discover.bean.FavourBean;
import com.huasport.smartsport.ui.discover.view.ArticleDetailActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;

import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.SoftKeyBoardListener;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.wxapi.ThirdPart;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;

public class ArticleDetailVm extends BaseViewModel implements View.OnClickListener {

    private ArticleDetailActivity articleDetailActivity;
    private final ThirdPart mThirdPart;
    private String registerCode;
    private String token;
    private String dynamicId;
    private boolean login;
    private ArticleDetailBean articleDetailBean;
    private String isMySelf = "";
    private final ArticledetailLayoutBinding binding;
    private int page = 1;
    private int toPage=0;
    private ArticleDetailAdapter articleDetailAdapter;
    private PopupWindow popupWindow;
    private View view;
    private View comment_send;
    private String commentId = "";
    private String replyId = "";
    private ArticleDetailBean.ResultBean.DataBean data;
    private String timestamp = "";
    private Intent intent;
    private int tabselect = 0;
    private String dynamicRegisterCode = "";
    private int lastItemPosition;
    private int firstItem;
    private int commandPos;
    private int favourPos;
    private int locationPos;
    private String edittext = "";
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private MyApplication application;

    public ArticleDetailVm(ArticleDetailActivity articleDetailActivity, ArticleDetailAdapter articleDetailAdapter) {
        this.articleDetailActivity = articleDetailActivity;
        this.articleDetailAdapter = articleDetailAdapter;
        binding = articleDetailActivity.getBinding();
        mThirdPart = new ThirdPart(articleDetailActivity);
        init();
        initData();
        initCommentListData("load");
        onClick();
    }

    private void init() {

        //初始化toast
        toastUtil = new ToastUtil(articleDetailActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(articleDetailActivity, R.style.LoadingDialog);
        //初始化Application
        application = MyApplication.getInstance();


        //初始化选中的tab
        articleDetailActivity.tabPos = 0;

        popupWindow = new PopupWindow(articleDetailActivity);

        dynamicId = articleDetailActivity.getIntent().getStringExtra("dynamicId");

        binding.llCommand.setOnClickListener(this);
        binding.llFavour.setOnClickListener(this);


        //关注
        binding.tvArticleattention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!login) {
                    login();
                    return;
                }
                attention();
            }
        });

        //监听软键盘显示隐藏状态
        SoftKeyBoardListener.setListener(articleDetailActivity, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
        binding.articleheaderImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EmptyUtil.isEmpty(data)) {
//                    intent = new Intent(articleDetailActivity, ReleaseActivity.class);
//                    intent.putExtra("registerId", data.getRegisterID());
//                    articleDetailActivity.startActivityForResult(intent, 0);
                }
            }
        });
    }

    /**
     * 点击事件
     */

    private void onClick() {

        articleDetailAdapter.setArticleClick(new ArticleDetailAdapter.ArticleClick() {
            @Override
            public void articleclick(CommentFavourBean.ResultBean.DataBean dataBean, int position, String id) {
//                final boolean login = LoginUtil.isLogin(articleDetailActivity);
//                    registerCode = (String) SharedPreferencesUtils.getParam(articleDetailActivity, "registerCode", "");
                    if (!dynamicRegisterCode.equals(registerCode)) {
                        if (registerCode.equals(dynamicRegisterCode) || id.equals(registerCode)) {
                            String socialInfoId = dataBean.getId();
                            commentId = socialInfoId;
                            showCommentEdit("reView");
//                        } else {
//                            ToastUtils.toast(articleDetailActivity, articleDetailActivity.getResources().getString(R.string.forbidcommand));
                        }
                    } else {
                        String socialInfoId = dataBean.getId();
                        commentId = socialInfoId;
                        showCommentEdit("reView");
                    }

                }
//                else {
////                    SharedPreferencesUtils.setParam(articleDetailActivity, "loginstate", true);
////                    Intent intent = new Intent(articleDetailActivity, LoginActivity.class);
////                    articleDetailActivity.startActivityForResult(intent, 0);
//                }
        });

        articleDetailAdapter.setArticleReplyInfoClick(new ArticleDetailAdapter.ArticleReplyInfoClick() {
            @Override
            public void articleReplyInfoClick(CommentFavourBean.ResultBean.DataBean.ReplyInfosBean dataBean, String strid, String id) {
//                final boolean login = LoginUtil.isLogin(articleDetailActivity);
//                if (login) {
//                    registerCode = (String) SharedPreferencesUtils.getParam(articleDetailActivity, "registerCode", "");
//                    if (!dynamicRegisterCode.equals(registerCode)) {
//                        if (registerCode.equals(id)) {
//                            commentId = strid;
//                            replyId = dataBean.getCommentId();
//                            showCommentEdit("reView");
//                        } else {
//                            ToastUtils.toast(articleDetailActivity, articleDetailActivity.getResources().getString(R.string.forbidcommand));
//                        }
//                    } else {
//                        String socialInfoId = dataBean.getCommentId();
//                        commentId = socialInfoId;
//                        showCommentEdit("reView");
//                    }
//                } else {
//                    SharedPreferencesUtils.setParam(articleDetailActivity, "loginstate", true);
//                    articleDetailActivity.startActivity2(LoginActivity.class);
//                }
            }
        });


    }

    /**
     * 初始化头部数据
     */
    private void initData() {
//        token = MyApplication.getToken(articleDetailActivity);
        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl2);
        params.put("id", dynamicId);
        params.put("baseMethod", Method.DYNAMICARTICLEDETAIL);

        OkHttpUtil.getRequest(articleDetailActivity, params, new RequestCallBack<ArticleDetailBean>() {
            @Override
            public void onSuccess(Response<ArticleDetailBean> response) {
                ArticleDetailBean articleDetailBean = response.body();

                if (!EmptyUtil.isEmpty(articleDetailBean)){
                    int resultCode = articleDetailBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        ArticleDetailBean.ResultBean resultBean = articleDetailBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            String isOneself = resultBean.getIsOneself();
                            ArticleDetailBean.ResultBean.DataBean dataList = resultBean.getData();

                        }

                    }

                }
            }

            @Override
            public ArticleDetailBean parseNetworkResponse(String jsonResult) {

                ArticleDetailBean articleDetailBean = JSON.parseObject(jsonResult, ArticleDetailBean.class);

                return articleDetailBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });



//        OkhttpUtils.getRequest(articleDetailActivity, params, new BaseHttpCallBack<ArticleDetailBean>(articleDetailActivity, true) {
//
//            private String registerCode;
//
//            @Override
//            public ArticleDetailBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                articleDetailBean = JSON.parseObject(jsonResult, ArticleDetailBean.class);
//
//                return articleDetailBean;
//            }
//
//            @Override
//            public void onSuccess(ArticleDetailBean articleDetailBean, Call call, Response response) {
//                if (!EmptyUtil.isEmpty(articleDetailBean)) {
//                    if (articleDetailBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
//
//                        data = articleDetailBean.getResult().getData();
//
//                        String isOneself = articleDetailBean.getResult().getIsOneself();
//
//                        isMySelf = isOneself;
//
//                        dynamicRegisterCode = articleDetailBean.getResult().getData().getRegisterID();
//
//                        articleDetailActivity.isOneSelf.set(isOneself);
//
//                        articleDetailActivity.dyId.set(articleDetailBean.getResult().getData().getRegisterID());
//
////                        if (isOneself.equals("0")) {
////                            tvarticleAttention.setVisibility(View.VISIBLE);
////                        } else {
////                            articleDetailActivity.setRightText();
////                            tvarticleAttention.setVisibility(View.GONE);
////                        }
//
//                       // registerCode = (String) SharedPreferencesUtils.getParam(articleDetailActivity, "registerCode", "");
//
//                        if (!registerCode.equals(articleDetailBean.getResult().getData().getRegisterID())) {
//                            binding.tvArticleattention.setVisibility(View.VISIBLE);
//                        } else {
//                            articleDetailActivity.setRightText();
//                            binding.tvArticleattention.setVisibility(View.GONE);
//                        }
//
//                        if (data.getIsLike().equals("0")) {
//                            binding.imgDynamiczan.setImageResource(R.mipmap.icon_grayzan);
//                        } else {
//                            binding.imgDynamiczan.setImageResource(R.mipmap.icon_lightzan);
//                        }
//                        //标题
//                        if (!EmptyUtil.isEmpty(data.getTitle())) {
//                            binding.tvArticleTitle.setText((String) data.getTitle());
//                        }
//
//                        //名字
//                        if (!EmptyUtil.isEmpty(data.getRegisterNickName())) {
//                            binding.articlename.setText(data.getRegisterNickName());
//                        } else {
//                            binding.articlename.setText(articleDetailActivity.getResources().getString(R.string.tourist));
//                        }
//                        //头像
//                        if (!EmptyUtil.isEmpty(data.getRegisterPhoto())) {
//                            GlideUtil.LoadCircleImage(articleDetailActivity, data.getRegisterPhoto(), binding.articleheaderImg);
//                        } else {
//                            binding.articleheaderImg.setImageResource(R.mipmap.icon_defaultheader_yes);
//
//                        }
////                        //详情
//                        String pruduce = (String) data.getPosition();
//                        //是否是认证 1:是 0：否
//                        if (!EmptyUtil.isEmpty(data.getIsAuth())) {
//                            if (data.getIsAuth().equals(StatusVariable.YESAUTH)) {
//                                binding.articleimgV.setVisibility(View.VISIBLE);
//                            } else {
//                                binding.articleimgV.setVisibility(View.GONE);
//                            }
//                        }
//                        //是否是精品 0：不是 1：是
//                        if (!EmptyUtil.isEmpty(data.getSplendidStatic())) {
//                            if (data.getSplendidStatic().equals(StatusVariable.SPLENDIDSTATIC)) {
//                                binding.articlesift.setVisibility(View.VISIBLE);
//                            } else {
//                                binding.articlesift.setVisibility(View.GONE);
//                            }
//                        }
//                        String position = (String) data.getPosition();
//                        long time = data.getCreateTime();
//                        long fronttimelong = DateUtil.frontDate();//当前时间的前一天时间
//                        //动态时间
//                        if (!EmptyUtil.isEmpty(time)) {
//                            String date = DateUtil.DateToStr(time, StatusVariable.HM);
//                            if (DateUtil.isSameDay(System.currentTimeMillis(), time)) {
//                                if (!EmptyUtil.isEmpty(pruduce)) {
//                                    binding.tvArticlesifttime.setText("今天" + date + " " + (String) data.getPosition());
//                                } else {
//                                    binding.tvArticlesifttime.setText("今天" + date);
//                                }
//                            } else if (DateUtil.isSameDay(fronttimelong, time)) {
//                                if (!EmptyUtil.isEmpty(pruduce)) {
//                                    binding.tvArticlesifttime.setText("昨天" + date + " " + (String) data.getPosition());
//                                } else {
//                                    binding.tvArticlesifttime.setText("昨天" + date);
//                                }
//
//                            } else {
//                                String timeStr = DateUtil.DateToStr(time, StatusVariable.YMD);
//                                if (!EmptyUtil.isEmpty(timeStr)) {
//                                    if (!EmptyUtil.isEmpty(position)) {
//                                        binding.tvArticlesifttime.setText(timeStr + " " + position);
//                                    } else {
//                                        binding.tvArticlesifttime.setText(timeStr);
//                                    }
//                                } else {
//                                    binding.tvArticlesifttime.setText(position);
//                                }
//
//
//                            }
//                        } else {
//                            if (!EmptyUtil.isEmpty(pruduce)) {
//                                binding.tvArticlesifttime.setText((String) data.getPosition());
//                            }
//                        }
//                        //平台类型
//                        String platform = articleDetailBean.getResult().getData().getPlatform();
//                        String content = articleDetailBean.getResult().getData().getContent();
//                        if (!EmptyUtil.isEmpty(platform)) {
//
//                            if (!EmptyUtil.isEmpty(content)) {
//                                String content1 = Util.getFinalContent(content);
//                                String appNewContent = Util.getAPPNewContent(content1);
//                                binding.articleWebView.loadData(appNewContent, "text/html; charset=UTF-8", null);
//                            }
//
//                        }
//                        if (!EmptyUtil.isEmpty(data.getIsFollow())) {
//                            String isFollow = data.getIsFollow();
//                            if (isFollow.equals("1")) {
//                                binding.tvArticleattention.setEnabled(true);
//                                binding.tvArticleattention.setText("已关注");
//                                binding.tvArticleattention.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_999999));
//                                binding.tvArticleattention.setBackground(articleDetailActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
//                            } else {
//                                binding.tvArticleattention.setEnabled(true);
//                                binding.tvArticleattention.setText("关注");
//                                binding.tvArticleattention.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_FF8F00));
//                                binding.tvArticleattention.setBackground(articleDetailActivity.getResources().getDrawable(R.drawable.attentionbg_no));
//                            }
//                        }
//                        binding.tvCommand.setText("评论" + data.getCommentsCount());
//                        binding.tvFavour.setText("点赞" + data.getLikesCount());
//                    } else {
////                        ToastUtils.toast(articleDetailActivity, articleDetailBean.getResultMsg());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
////                ToastUtils.toast(articleDetailActivity, msg);
//            }
//        });


    }

    /**
     * 评论数据
     *
     * @param loadType
     */
    private void initCommentListData(final String loadType) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.COMMENTLIST);
        params.put("baseUrl", Config.baseUrl2);
        params.put("id", dynamicId);
        params.put("currentPage", page + "");
        params.put("pageSize", "10");
        params.put("token", token);
        params.put("timestamp", timestamp);

        OkHttpUtil.postRequest(articleDetailActivity, params, new RequestCallBack<CommentFavourBean>() {
            @Override
            public void onSuccess(Response<CommentFavourBean> response) {
                CommentFavourBean commentFavourBean = response.body();

                if (!EmptyUtil.isEmpty(articleDetailBean)){
                    int resultCode = commentFavourBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        CommentFavourBean.ResultBean resultBean = commentFavourBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            int totalPage = resultBean.getTotalPage();
                            List<CommentFavourBean.ResultBean.DataBean> data = resultBean.getData();
                        }

                    }

                }
            }

            @Override
            public CommentFavourBean parseNetworkResponse(String jsonResult) {

                CommentFavourBean commandPraiseBean = JSON.parseObject(jsonResult, CommentFavourBean.class);

                return commandPraiseBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });



//        OkhttpUtils.postRequest(articleDetailActivity, params, new BaseHttpCallBack<CommentFavourBean>(articleDetailActivity, true) {
//            @Override
//            public CommentFavourBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                CommentFavourBean commentFavourBean = JSON.parseObject(jsonResult, CommentFavourBean.class);
//
//                return commentFavourBean;
//            }
//
//            @Override
//            public void onSuccess(CommentFavourBean commentFavourBean, Call call, Response response) {
//                if (!EmptyUtil.isEmpty(commentFavourBean)) {
//                    if (commentFavourBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
//                        CommentFavourBean.ResultBean resultBean = commentFavourBean.getResult();
//                        timestamp = resultBean.getTimestamp();
//
//                        toPage = resultBean.getTotalPage();
//
//                        if (tabselect == 0) {
//                            binding.tvCommand.setText("评论" + commentFavourBean.getResult().getAllRow());
//                            List<CommentFavourBean.ResultBean.DataBean> data = commentFavourBean.getResult().getData();
//                            if (toPage > 0) {
//
//                                if (loadType.equals("loadMore")) {
//                                    articleDetailAdapter.loadMoreData(data);
//                                    binding.smartRefreshlayout.finishLoadMore();
//                                } else {
//
//                                    if (articleDetailAdapter.mList.size() > 0) {
//                                        articleDetailAdapter.mList.clear();
//                                    }
//
//                                    binding.smartRefreshlayout.finishRefresh();
//                                    articleDetailAdapter.loadData(data);
//                                }
//                            } else {
//                                ToastUtils.toast(articleDetailActivity, "暂无评论");
//                                articleDetailAdapter.loadData(data);
//                            }
////                            scrollToPosition();
//                        } else {
//                            binding.tvCommand.setText("评论" + commentFavourBean.getResult().getAllRow());
//                        }
//                        page++;
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//
//            }
//        });

    }

    /**
     * 点赞数据
     *
     * @param loadType
     */
    public void initFavourListData(final String loadType) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.FAVOURLIST);
        params.put("baseUrl", Config.baseUrl2);
        params.put("socialInfoId", dynamicId);
        params.put("currentPage", page + "");
        params.put("pageSize", "10");
        params.put("token", token);

        OkHttpUtil.getRequest(articleDetailActivity, params, new RequestCallBack<FavourBean>() {
            @Override
            public void onSuccess(Response<FavourBean> response) {

                FavourBean favourBean = response.body();


                if (!EmptyUtil.isEmpty(favourBean)){
                    int resultCode = favourBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        FavourBean.ResultBean resultBean = favourBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            int totalPage = resultBean.getTotalPage();
                            List<FavourBean.ResultBean.ListBean> list = resultBean.getList();
                        }

                    }

                }


            }

            @Override
            public FavourBean parseNetworkResponse(String jsonResult) {

                FavourBean favourBean = JSON.parseObject(jsonResult, FavourBean.class);

                return favourBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }

            }
        });



//        OkhttpUtils.getRequest(articleDetailActivity, params, new BaseHttpCallBack<FavourBean>(articleDetailActivity, true) {
//            @Override
//            public FavourBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                FavourBean favourBean = JSON.parseObject(jsonResult, FavourBean.class);
//
//                return favourBean;
//            }
//
//            @Override
//            public void onSuccess(FavourBean favourBean, Call call, Response response) {
//                if (!EmptyUtil.isEmpty(favourBean)) {
//                    if (favourBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
//                        FavourBean.ResultBean resultBean = favourBean.getResult();
//                        toPage = resultBean.getTotalPage();
//                        if (tabselect == 0) {
//
//                            binding.tvFavour.setText("点赞" + favourBean.getResult().getTotal());
//
//                        } else {
//                            binding.tvFavour.setText("点赞" + favourBean.getResult().getTotal());
//
//
//                            List<FavourBean.ResultBean.ListBean> list = favourBean.getResult().getList();
//                            List<CommentFavourBean.ResultBean.DataBean> data = new ArrayList<>();
//                            if (!EmptyUtil.isEmpty(list)) {
//                                for (FavourBean.ResultBean.ListBean listBean : list) {
//                                    CommentFavourBean.ResultBean.DataBean dataBean = new CommentFavourBean.ResultBean.DataBean();
//                                    dataBean.setPosition(listBean.getPosition());
//                                    dataBean.setRegisterId(listBean.getRegisterId());
//                                    dataBean.setRegisterNickName(listBean.getRegisterNickName());
//                                    dataBean.setRegisterPhoto(listBean.getRegisterPhoto());
//                                    dataBean.setCreateDate(listBean.getCreateDate());
//                                    dataBean.setIsAuth(listBean.getIsAuth());
//                                    data.add(dataBean);
//                                }
//
//                                if (!EmptyUtil.isEmpty(data)) {
//                                    if (loadType.equals("loadMore")) {
//                                        articleDetailAdapter.loadMoreData(data);
//                                    } else {
//
//                                        if (articleDetailAdapter.mList.size() > 0) {
//                                            articleDetailAdapter.mList.clear();
//                                        }
//                                        articleDetailAdapter.loadData(data);
//                                    }
//                                }
//
//                            } else {
//                                ToastUtils.toast(articleDetailActivity, "暂无点赞");
//                                articleDetailAdapter.loadData(data);
//                            }
//                        }
//                        page++;
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//
//            }
//        });


    }

    /**
     * 删除文章
     */
    public void deleteArticle() {
//
//        if (!login) {
//            SharedPreferencesUtils.setParam(articleDetailActivity, "loginstate", true);
//            articleDetailActivity.startActivity2(LoginActivity.class);
//            return;
//        }

        BaseDialog.show(articleDetailActivity, "提示", "确认删除吗？", "删除", "取消", true, false, articleDetailActivity.getResources().getColor(R.color.color_333333), new DialogCallBack() {
            @Override
            public void submit(CustomDialog.Builder customDialog) {
                HashMap params = new HashMap();
                params.put("baseMethod", Method.DELETEDYANDART);
                params.put("token", token);
                params.put("id", dynamicId);
                params.put("baseUrl", Config.baseUrl2);

                OkHttpUtil.getRequest(articleDetailActivity, params, new RequestCallBack<ResultBean>() {
                    @Override
                    public void onSuccess(Response<ResultBean> response) {
                        ResultBean resultBean = response.body();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            int resultCode = resultBean.getResultCode();
                            if (resultCode == StatusVariable.REQUESTSUCCESS){

                            }else{


                            }
                        }
                    }

                    @Override
                    public ResultBean parseNetworkResponse(String jsonResult) {

                        ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                        return resultBean;
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        if(!EmptyUtil.isEmpty(msg)){
                            toastUtil.centerToast(msg);
                        }
                    }
                });


//                OkhttpUtils.getRequest(articleDetailActivity, params, new BaseHttpCallBack<ResultBean>(articleDetailActivity, true) {
//                    @Override
//                    public ResultBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                        ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);
//
//                        return resultBean;
//                    }
//
//                    @Override
//                    public void onSuccess(ResultBean resultBean, Call call, Response response) {
//                        if (!EmptyUtil.isEmpty(resultBean)) {
//                            if (resultBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
//                                articleDetailActivity.setResult(1000);
//                                articleDetailActivity.finish2();
//                                ToastUtils.toast(articleDetailActivity, "删除成功");
//                            } else {
//                                ToastUtils.toast(articleDetailActivity, "删除失败");
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailed(String code, String msg) {
//                        ToastUtils.toast(articleDetailActivity, msg);
//                    }
//                });
            }

            @Override
            public void cancel(CustomDialog.Builder customDialog) {
                customDialog.dismiss();
            }
        });


    }

    /**
     * 设置登录状态
     */
    private void login() {

//        SharedPreferencesUtils.setParam(articleDetailActivity, "loginstate", true);
//        Intent intent = new Intent(articleDetailActivity, LoginActivity.class);
//        articleDetailActivity.startActivityForResult(intent, 0);
    }

    /**
     * 关注
     */
    public void attention() {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.ATTENTION);
        params.put("userId", data.getRegisterID());
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.postRequest(articleDetailActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)){
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){

                    }else{


                    }
                }
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });

//        OkhttpUtils.postRequest(articleDetailActivity, params, new BaseHttpCallBack<AttentionResultBean>(articleDetailActivity, true) {
//            @Override
//            public AttentionResultBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                AttentionResultBean attentionResultBean = JSON.parseObject(jsonResult, AttentionResultBean.class);
//
//                return attentionResultBean;
//            }
//
//            @Override
//            public void onSuccess(AttentionResultBean attentionResultBean, Call call, Response response) {
//                if (!EmptyUtil.isEmpty(attentionResultBean)) {
//
//                    if (attentionResultBean.getResultCode().equals("200")) {
//
//                        if (attentionResultBean.getResultMsg().equals("关注用户成功")) {
//                            binding.tvArticleattention.setEnabled(true);
//                            binding.tvArticleattention.setText("已关注");
//                            binding.tvArticleattention.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_999999));
//                            binding.tvArticleattention.setBackground(articleDetailActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
//                        } else {
//                            binding.tvArticleattention.setEnabled(true);
//                            binding.tvArticleattention.setText("关注");
//                            binding.tvArticleattention.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_FF8F00));
//                            binding.tvArticleattention.setBackground(articleDetailActivity.getResources().getDrawable(R.drawable.attentionbg_no));
//                        }
//                        ToastUtils.toast(articleDetailActivity, attentionResultBean.getResultMsg());
//                    } else {
//                        ToastUtils.toast(articleDetailActivity, attentionResultBean.getResultMsg());
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                ToastUtils.toast(articleDetailActivity, msg);
//
//            }
//        });
    }


    /**
     * 文章评论
     */
    public void articleComment() {

        if (!login) {
            login();
            return;
        }
        showCommentEdit("articleComment");
    }

    /**
     * 评论
     *
     * @param
     */
    public void showCommentEdit(final String commentType) {
//
//        view = LayoutInflater.from(articleDetailActivity).inflate(R.layout.comment_edittext_layout, null);
//        final EditText commentEdittext = view.findViewById(R.id.comment_edit);
//        if (!EmptyUtil.isEmpty(edittext)){
//            commentEdittext.setText(edittext);
//        }else{
//            commentEdittext.setText("");
//        }
//
//        comment_send = view.findViewById(R.id.comment_send);
//        comment_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (EmptyUtil.isEmpty(commentEdittext.getText().toString().trim())) {
//                    ToastUtils.toast(articleDetailActivity, "请填写评论内容");
//                } else {
//                    if (commentType.equals("articleComment")) {
//                        comment(dynamicId, commentEdittext.getText().toString().trim());
//                    } else {
//                        commentReview(commentEdittext.getText().toString().trim());
//                    }
//
//                }
//                Util.softBoard(articleDetailActivity);
//            }
//
//        });
//
//        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//
//        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        popupWindow.setBackgroundDrawable(new ColorDrawable());
//
//        popupWindow.setFocusable(true);
//
//        popupWindow.setTouchable(true);
//
//        popupWindow.setOutsideTouchable(false);
//
////        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);//防止遮挡edittext
//
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//        popupWindow.setContentView(view);
//
//        commentEdittext.requestFocus();
//
//        commentEdittext.setHint("评论");
//
//        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//
//
//        if (commentEdittext.hasFocus()) {
//            Util.softBoard(articleDetailActivity);
//        }
//
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//
//                edittext = commentEdittext.getText().toString();
//
//            }
//        });

    }

    /**
     * 对评论进行评论
     *
     * @param replyContent
     */
    public void commentReview(String replyContent) {


        HashMap params = new HashMap();
        params.put("token", token);
        params.put("method", Method.COMMENTREVIEW);
        params.put("baseUrl", Config.baseUrl2);
        params.put("replyId", replyId);
        params.put("commentId", commentId);
        params.put("replyContent", replyContent);


        OkHttpUtil.getRequest(articleDetailActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)){
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){

                    }else{


                    }
                }
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });


//        Log.e("params", params.toString());
//        OkhttpUtils.postRequest(articleDetailActivity, params, new BaseHttpCallBack<ResultBean>(articleDetailActivity, true) {
//            @Override
//            public ResultBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);
//
//                return resultBean;
//            }
//
//            @Override
//            public void onSuccess(ResultBean resultBean, Call call, Response response) {
//                if (!EmptyUtil.isEmpty(resultBean)) {
//                    if (resultBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
//                        edittext = "";
//                        clearData();
//                        refreshCommand();
//                    } else {
//                        ToastUtils.toast(articleDetailActivity, articleDetailBean.getResultMsg());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                ToastUtils.toast(articleDetailActivity, msg);
//            }
//        });
//

    }

    /**
     * 评论
     */
    public void comment(String id, String content) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.COMMAND);
        params.put("id", id);
        params.put("content", content);
        params.put("baseUrl", Config.baseUrl2);


        OkHttpUtil.getRequest(articleDetailActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)){
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){

                    }else{


                    }
                }
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });


//        OkhttpUtils.postRequest(articleDetailActivity, params, new BaseHttpCallBack<CommandPraiseBean>(articleDetailActivity, true) {
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
//                if (!EmptyUtil.isEmpty(commandPraiseBean)) {
//                    if (commandPraiseBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
//                        clearData();
//                        refreshCommand();
//                        edittext = "";
//                        ToastUtils.toast(articleDetailActivity, "评论成功");
//                    } else {
//                        ToastUtils.toast(articleDetailActivity, commandPraiseBean.getResultMsg());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                ToastUtils.toast(articleDetailActivity, msg);
//            }
//        });


    }

    /**
     * 分享
     */
    public void shareClick() {

        if (!login) {
            login();
            return;
        }
        sharePop(data);

    }

    /**
     * 分享弹出框
     *
     * @param dataBean
     */
    private void sharePop(final ArticleDetailBean.ResultBean.DataBean dataBean) {
//
//        View shareView = LayoutInflater.from(articleDetailActivity).inflate(R.layout.social_sharelayout, null, false);
//        final PopupWindow sharePop = new PopupWindow(shareView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        sharePop.setContentView(shareView);
//        sharePop.showAtLocation(shareView, Gravity.BOTTOM, 0, 0);
//        sharePop.setOutsideTouchable(false);
//        Util.backgroundAlpha(articleDetailActivity, 0.5f);
//        final String userNick = (String) SharedPreferencesUtils.getParam(articleDetailActivity, "userNickName", "");
        //微信好友分享
//        shareView.findViewById(R.id.ll_social_shareWechat).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(!EmptyUtil.isEmpty(dataBean.getTitle())){
////                    String newTitle = Util.removeSpecialKey((String) dataBean.getTitle());
//                    mThirdPart.wxUrlShare(dataBean.getShareURl() +
//                                    "&uSource=share", Util.getHtmlContent((String) dataBean.getTitle())
//                            , Util.getHtmlContent(dataBean.getContent())
//                            , R.mipmap.logo_share, ThirdPart.WECHAT_FRIEND);
//                }
//
//
//
//                LogHttp.shareHttp(articleDetailActivity, token, dataBean.getId(), new ShareCallBack() {
//                    @Override
//                    public void shareSuccess(ResultBean resultBean) {
//
//                    }
//
//                    @Override
//                    public void shareFailed(String code, String msg) {
//
//                    }
//                });
//
//
//            }
//        });
//
//
//        //微信朋友圈分享
//        shareView.findViewById(R.id.ll_social_sharefriend).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                    mThirdPart.wxUrlShare(dataBean.getShareURl() +
//                                    "&uSource=share", Util.getHtmlContent((String) dataBean.getTitle())
//                            , "", R.mipmap.logo_share, ThirdPart.WECHAT_MOMENT);
//
//                LogHttp.shareHttp(articleDetailActivity, token, dataBean.getId(), new ShareCallBack() {
//                    @Override
//                    public void shareSuccess(ResultBean resultBean) {
//
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
//                Util.backgroundAlpha(articleDetailActivity, 1f);
//            }
//        });
    }

    /**
     * 点赞
     */
    public void priseClick() {

        if (!login) {
            login();
            return;
        }
        HashMap params = new HashMap();
        params.put("token", token);
        params.put("socialInfoId", dynamicId);
        params.put("baseMethod", Method.PRAISE);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.getRequest(articleDetailActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)){
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){

                    }else{


                    }
                }
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });


//        OkhttpUtils.postRequest(articleDetailActivity, params, new BaseHttpCallBack<CommandPraiseBean>(articleDetailActivity, true) {
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
//                if (!EmptyUtil.isEmpty(commandPraiseBean)) {
//                    if (commandPraiseBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
//                        clearData();
//                        refreshFavour();
//                        if (commandPraiseBean.getResultMsg().equals("点赞成功")) {
//                            binding.imgDynamiczan.setImageResource(R.mipmap.icon_siftprise_select);
//                        } else if (commandPraiseBean.getResultMsg().equals("取消点赞成功")) {
//                            binding.imgDynamiczan.setImageResource(R.mipmap.icon_siftpraise);
//                        }
//                        SharedPreferencesUtils.setParam(articleDetailActivity, "ActivityState", true);
//                        ToastUtils.toast(articleDetailActivity, commandPraiseBean.getResultMsg());
//                    } else {
//                        ToastUtils.toast(articleDetailActivity, commandPraiseBean.getResultMsg());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                ToastUtils.toast(articleDetailActivity, msg);
//            }
//        });

    }

    /**
     * 加载更多数据
     */
    public void loadMore() {

        if (page <= toPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            if (tabselect == 0) {
                initCommentListData("loadMore");
            } else {
                initFavourListData("loadMore");
            }
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        boolean backState = (boolean) SharedPreferencesUtils.getParam(articleDetailActivity, "BackState", true);
//        if (resultCode == 1000 || backState == true) {
//            if (tabselect == 0) {
//                refreshCommand();
//            } else {
//                refreshFavour();
//            }
////            SharedPreferencesUtils.setParam(articleDetailActivity, "BackState", false);
//        } else if (resultCode == -1) {
//            initData();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        token = MyApplication.getToken(articleDetailActivity);
//        login = LoginUtil.isLogin(articleDetailActivity);
//    }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_command:
                tabselect = 0;
                articleDetailActivity.tabPos = 0;
                binding.llCommand.setClickable(false);
                binding.llFavour.setClickable(true);
                binding.tvCommand.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tabCommand.setVisibility(View.VISIBLE);
                binding.tvFavour.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_333333));
                binding.tabFavour.setVisibility(View.GONE);
                refreshCommand();
                break;
            case R.id.ll_favour:
                tabselect = 1;
                articleDetailActivity.tabPos = 1;
                binding.llCommand.setClickable(true);
                binding.llFavour.setClickable(false);
                binding.tvFavour.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tabFavour.setVisibility(View.VISIBLE);
                binding.tvFavour.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_333333));
                binding.tabCommand.setVisibility(View.GONE);
                refreshFavour();
                break;
        }


    }

    /**
     * 刷新点赞
     */
    private void refreshFavour() {

        binding.smartRefreshlayout.setNoMoreData(false);
        page = 1;
        timestamp = "";
        initFavourListData("load");
    }

    /**
     * 刷新评论
     */
    private void refreshCommand() {
        articleDetailAdapter.mapPos.clear();
        binding.smartRefreshlayout.setNoMoreData(false);
        page = 1;
        timestamp = "";
        initCommentListData("load");
    }

    private void clearData() {

        articleDetailAdapter.mapPos.clear();
        timestamp = "";
        page = 1;
        articleDetailAdapter.mapPos.clear();
        binding.smartRefreshlayout.setNoMoreData(false);

    }

    //拿到显示条目的position位置
    private void location(final int tabselect) {

        binding.dynamicDteailRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    firstItem = linearManager.findFirstVisibleItemPosition();
                    if (tabselect == 0) {

                        commandPos = lastItemPosition;
                    } else {
                        favourPos = lastItemPosition;
                    }
                }
            }
        });
    }
}
