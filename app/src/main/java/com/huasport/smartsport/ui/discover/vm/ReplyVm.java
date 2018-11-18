package com.huasport.smartsport.ui.discover.vm;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.ReplyLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.ReplyAdapter;
import com.huasport.smartsport.ui.discover.bean.CommentFavourBean;
import com.huasport.smartsport.ui.discover.bean.ReplyBean;
import com.huasport.smartsport.ui.discover.view.ReplyActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ShareUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.httprequest.HttpRequestCallBack;
import com.huasport.smartsport.util.httprequest.HttpRequestUtil;
import com.huasport.smartsport.util.popwindow.CommandCallback;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.HashMap;
import java.util.List;

public class ReplyVm extends BaseViewModel implements RefreshLoadMoreListener {

    private ReplyActivity replyActivity;
    private final ReplyLayoutBinding binding;
    private ImageView img_reply_headerImg;
    private ImageView img_replyheaderv;
    private TextView tv_replyname;
    private TextView tv_replytime;
    private TextView tv_replycontent;
    private String commentId = "";
    private int page = 1;
    private CommentFavourBean.ResultBean.DataBean replyBean;
    private int totalPage = 0;
    private ReplyAdapter replyAdapter;
    private String token;
    private View view;
    private View comment_send;
    private String replyId = "";
    private PopupWindow popupWindow;
    private String registerCode;
    private String dynamicId;
    private String isOneSelf;
    private String dynamicCode;
    private EditText edittext;
    private ShareUtil shareUtil;
    private MyApplication application;
    private LoadingDialog loadingDialog;
    private ToastUtil toastUtil;
    private UserBean userBean;

    public ReplyVm(ReplyActivity replyActivity, ReplyAdapter replyAdapter) {
        this.replyActivity = replyActivity;
        this.replyAdapter = replyAdapter;
        binding = replyActivity.getBinding();
        init();
        initHeader();
        initReplyInfo(StatusVariable.REFRESH);
    }

    /**
     * 初始化
     */
    private void init() {

        dynamicId = replyActivity.getIntent().getStringExtra("dynamicId");
        isOneSelf = replyActivity.getIntent().getStringExtra("isOneSelf");

        //初始化toast
        toastUtil = new ToastUtil(replyActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(replyActivity, R.style.LoadingDialog);
        //初始化Application
        application = MyApplication.getInstance();
        //初始化刷新加载
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //文章Id ，上个界面带来
        dynamicId = replyActivity.getIntent().getStringExtra("dynamicId");
        //初始化分享
        shareUtil = new ShareUtil(replyActivity);
        userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
            registerCode = userBean.getRegisterCode();
        } else {
            token = "";
            registerCode = "";
        }
        replyActivity.isOneSelf.set(isOneSelf);

    }

    /**
     * 初始化头部
     */
    private void initHeader() {

        replyBean = (CommentFavourBean.ResultBean.DataBean) replyActivity.getIntent().getSerializableExtra("replyBean");

        dynamicCode = replyBean.getRegisterId();

        View replyView = LayoutInflater.from(replyActivity).inflate(R.layout.reply_headerview, null, false);
        binding.replyRecyclerView.addHeaderView(replyView);

        img_reply_headerImg = replyView.findViewById(R.id.img_reply_headerImg);
        img_replyheaderv = replyView.findViewById(R.id.img_replyheaderv);
        tv_replyname = replyView.findViewById(R.id.tv_replyname);
        tv_replytime = replyView.findViewById(R.id.tv_replytime);
        tv_replycontent = replyView.findViewById(R.id.tv_replycontent);

        if (!EmptyUtil.isEmpty(replyBean)) {
            replyActivity.dynamicRegisterId.set(dynamicId);
            commentId = replyBean.getId();
            replyId = replyBean.getId();
            //头像
            if (!EmptyUtil.isEmpty(replyBean.getRegisterPhoto())) {
                GlideUtil.LoadCircleImage(replyActivity, replyBean.getRegisterPhoto(), img_reply_headerImg);
            } else {
                img_reply_headerImg.setImageResource(R.mipmap.icon_defaultheader_yes);
            }

            //是否是认证 1:是 0：否
            if (!EmptyUtil.isEmpty(replyBean.getIsAuth())) {
                if (replyBean.getIsAuth() == StatusVariable.YESAUTH) {
                    img_replyheaderv.setVisibility(View.VISIBLE);
                } else {
                    img_replyheaderv.setVisibility(View.GONE);
                }
            }

            //名字
            String registerNickName = replyBean.getRegisterNickName();
            if (!EmptyUtil.isEmpty(registerNickName)) {
                tv_replyname.setText(registerNickName);
            }
            //时间
            long time = replyBean.getCreateTime();
            long fronttimelong = DateUtil.frontDate();//当前时间的前一天时间
            //动态时间
            if (!EmptyUtil.isEmpty(time)) {
                String date = DateUtil.DateToStr(time, StatusVariable.HM);
                if (DateUtil.isSameDay(System.currentTimeMillis(), time)) {
                    tv_replytime.setText("今天 " + date);
                } else if (DateUtil.isSameDay(fronttimelong, time)) {
                    tv_replytime.setText("昨天" + date);
                } else {
                    String timeStr = DateUtil.DateToStr(time, StatusVariable.YMD);
                    tv_replytime.setText(timeStr);
                }
            }
            //评论详情
            String content = replyBean.getContent();
            if (!EmptyUtil.isEmpty(content)) {
                tv_replycontent.setText(content);
            }
        }
    }

    /**
     * 评论列表
     *
     * @param loadType
     */
    private void initReplyInfo(final int loadType) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.REPLYINFOLIST);
        params.put("baseUrl", Config.baseUrl2);
        params.put("commentId", commentId);
        params.put("currentPage", page + "");
        params.put("pageSize", "10");

        OkHttpUtil.postRequest(replyActivity, params, new RequestCallBack<ReplyBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ReplyBean> response) {
                ReplyBean replyBean = response.body();
                if (!EmptyUtil.isEmpty(replyBean)) {
                    int resultCode = replyBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        ReplyBean.ResultBean resultBean = replyBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            totalPage = resultBean.getTotalPage();
                            replyActivity.setTitleText(replyBean.getResult().getAllRow() + "条回复");
                            ReplyBean.ResultBean.DataBean data = resultBean.getData();
                            List<ReplyBean.ResultBean.DataBean.ReplyInfosBean> replyInfos = data.getReplyInfos();
                            if (loadType == StatusVariable.REQUESTSUCCESS) {
                                replyAdapter.loadData(replyInfos);
                                binding.smartRefreshlayout.finishRefresh();
                            } else {
                                replyAdapter.loadMoreData(replyInfos);
                                binding.replyRecyclerView.loadMoreComplete();
                                binding.smartRefreshlayout.finishLoadMore();
                            }
                            page++;
                        }

                    }
                }
            }

            @Override
            public ReplyBean parseNetworkResponse(String jsonResult) {
                ReplyBean replyBean = JSON.parseObject(jsonResult, ReplyBean.class);
                return replyBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });


//        OkhttpUtils.postRequest(replyActivity, params, new BaseHttpCallBack<ReplyBean>(replyActivity, true) {
//            @Override
//            public ReplyBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                ReplyBean replyBean = JSON.parseObject(jsonResult, ReplyBean.class);
//
//                return replyBean;
//            }
//
//            @Override
//            public void onSuccess(ReplyBean replyBean, Call call, Response response) {
//                if (!EmptyUtils.isEmpty(replyBean)) {
//
//                    if (replyBean.getResultCode() == StatusVariable.SUCCESS) {
//
//                        toPage = replyBean.getResult().getTotalPage();
//                        replyActivity.setTitleText(replyBean.getResult().getAllRow() + "条回复");
//                        ReplyBean.ResultBean.DataBean data = replyBean.getResult().getData();
//                        List<ReplyBean.ResultBean.DataBean.ReplyInfosBean> replyInfos = data.getReplyInfos();
//                        if (loadType.equals("load")) {
//                            replyAdapter.loadData(replyInfos);
//                        } else {
//                            replyAdapter.loadMoreData(replyInfos);
//                            binding.replyRecyclerView.loadMoreComplete();
//                        }
//                        page++;
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                ToastUtils.toast(replyActivity, msg);
//            }
//        });


    }

    /**
     * 评论
     *
     * @param
     */
    public void showCommentEdit() {

        PopWindowUtil.commandPop(replyActivity, new CommandCallback() {
            @Override
            public void releaseCommand(PopupWindow popupWindow, String s, EditText commentEdittext) {
                edittext = commentEdittext;
                if (EmptyUtil.isEmpty(commentEdittext.getText().toString().trim())) {
                    toastUtil.centerToast(replyActivity.getString(R.string.commandtip));
                } else {
                    commentReview(commentEdittext.getText().toString().trim());
                }
                Util.softBoard(replyActivity);
            }
        });
    }

    /**
     * 对评论进行评论
     *
     * @param replyContent
     */
    public void commentReview(String replyContent) {

        HttpRequestUtil.commentReview(replyActivity, token, commentId, replyId, replyContent, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    if (resultBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
                        edittext.setText("");
                        refreshCommand();
                        toastUtil.centerToast(replyActivity.getResources().getString(R.string.command_success));
                    }else{
                        toastUtil.centerToast(resultBean.getResultMsg());
                    }
                }
            }

            @Override
            public void httpFailed(int code, String msg) {

            }

            @Override
            public void httpFinish() {

            }
        });
    }

    /**
     * 回复评论
     */
    public void replyClick() {

        if (EmptyUtil.isEmpty(token)) {
            IntentUtil.startActivity(replyActivity,LoginActivity.class);
            return;
        }
        if (registerCode.equals(dynamicId)) {
            showCommentEdit();
        } else {
            if (registerCode.equals(replyBean.getRegisterId())) {
                showCommentEdit();
            } else {
                toastUtil.centerToast(replyActivity.getString(R.string.forbid_command));
            }
        }

    }

    /**
     * 刷新评论
     */
    public void refreshCommand(){

        binding.smartRefreshlayout.setNoMoreData(false);
        page = 1;
        initReplyInfo(StatusVariable.REQUESTSUCCESS);
    }

    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {

    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {
        if (page <= totalPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            initReplyInfo(type);
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
