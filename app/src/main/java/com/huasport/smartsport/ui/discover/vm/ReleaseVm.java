package com.huasport.smartsport.ui.discover.vm;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.HttpLog;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.api.ShareStatusCallback;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.PcreleaseLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.ReleaseAdapter;
import com.huasport.smartsport.ui.discover.bean.ApproveBean;
import com.huasport.smartsport.ui.discover.bean.AttentionSocialInfoBean;
import com.huasport.smartsport.ui.discover.bean.PicBean;
import com.huasport.smartsport.ui.discover.view.ReleaseActivity;
import com.huasport.smartsport.ui.pcenter.bean.UserCenterInfo;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.settings.view.PersonalMsgActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ShareUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.httprequest.HttpRequestCallBack;
import com.huasport.smartsport.util.httprequest.HttpRequestUtil;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.util.popwindow.ShareCallBack;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReleaseVm extends BaseViewModel implements View.OnClickListener, RefreshLoadMoreListener {

    private ReleaseActivity releaseActivity;
    private String token;
    private PcreleaseLayoutBinding binding;
    private ReleaseAdapter releaseAdapter;
    private int totalPage = 0;
    private int page = 1;
    private int dynamicPage = 1;
    private int articlePage = 1;
    private List<AttentionSocialInfoBean.ResultBean.DataBean> dataList = new ArrayList();
    private List dynamicList = new ArrayList();
    private List articleList = new ArrayList();
    private UserCenterInfo.ResultBean.UserBean userBean;
    private TextView tv_releaseCount;
    private TextView tv_attentionCount;
    private TextView tv_fansCount;
    private ImageView pcrelease_img;
    private TextView tv_authStatus;
    private View releaseheaderView;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private MyApplication application;
    private String dynamicId;
    private ShareUtil shareUtil;
    private String registerCode;
    private Intent intent;
    private String approveStatus = "";
    private String certStatus = "";
    private LinearLayout ll_authstatus;
    private View view;
    private View comment_send;
    private ApproveBean.ResultBean.AuthBean authBean;
    private TextView tv_attention;
    private ImageView vImg;
    private String timeStemp = "";
    private ApproveBean.ResultBean.AuthBean auth;
    private LinearLayout ll_dynamic;
    private View tab_dynamic;
    private TextView tv_dynamic;
    private LinearLayout ll_article;
    private TextView tv_article;
    private View tab_article;
    private int tabType = 0;
    private String tabStr = "dynamic";
    private List<AttentionSocialInfoBean.ResultBean.DataBean> dataBean;
    private String isOneself = "";
    private String statusType = "";
    private UserBean appUserBean;
    private String registerId = "";
    private PopupWindow popWindow;

    public ReleaseVm(ReleaseActivity releaseActivity, ReleaseAdapter releaseAdapter) {
        this.releaseActivity = releaseActivity;
        this.releaseAdapter = releaseAdapter;
        binding = releaseActivity.getBinding();
        init();
        initHeader();
        initUserData();
        initClick();
        initData(StatusVariable.REFRESH);
    }

    /**
     * 初始化
     */
    private void init() {

        //初始化toast
        toastUtil = new ToastUtil(releaseActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(releaseActivity, R.style.LoadingDialog);
        //初始化Application
        application = MyApplication.getInstance();
        //初始化刷新加载
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //registerID
        registerId = releaseActivity.getIntent().getStringExtra("registerId");
        //初始化分享
        shareUtil = new ShareUtil(releaseActivity);
        appUserBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(appUserBean)) {
            token = appUserBean.getToken();
            registerCode = appUserBean.getRegisterCode();
        } else {
            token = "";
            registerCode = "";
        }
    }

    /**
     * 初始化头数据
     */
    private void initHeader() {

        authBean = (ApproveBean.ResultBean.AuthBean) releaseActivity.getIntent().getSerializableExtra("authBean");

        final View releaseheaderView = LayoutInflater.from(releaseActivity).inflate(R.layout.release_header_layout, null);
        binding.releaseXrecyclerView.addHeaderView(releaseheaderView);

        tv_releaseCount = releaseheaderView.findViewById(R.id.tv_releaseCount);
        tv_attentionCount = releaseheaderView.findViewById(R.id.tv_attentionCount);
        tv_fansCount = releaseheaderView.findViewById(R.id.tv_fansCount);
        pcrelease_img = releaseheaderView.findViewById(R.id.pcrelease_img);
        tv_authStatus = releaseheaderView.findViewById(R.id.tv_authStatus);

        ll_authstatus = releaseheaderView.findViewById(R.id.ll_authstatus);
        tv_attention = releaseheaderView.findViewById(R.id.tv_attention);


        ll_dynamic = releaseheaderView.findViewById(R.id.ll_dynamic);
        tab_dynamic = releaseheaderView.findViewById(R.id.tab_dynamic);
        tv_dynamic = releaseheaderView.findViewById(R.id.tv_dynamic);
        ll_article = releaseheaderView.findViewById(R.id.ll_article);
        tv_article = releaseheaderView.findViewById(R.id.tv_article);
        tab_article = releaseheaderView.findViewById(R.id.tab_article);

        ll_dynamic.setOnClickListener(this);
        ll_article.setOnClickListener(this);

        vImg = releaseheaderView.findViewById(R.id.img_releasev);
        tv_authStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approve();

            }
        });
        tv_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmptyUtil.isEmpty(token)){
                    IntentUtil.startActivity(releaseActivity,LoginActivity.class);
                    return;
                }
                attention();
            }
        });
        //到个人信息界面
        pcrelease_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOneself.equals("1")) {
                    IntentUtil.startActivityForResult(releaseActivity,PersonalMsgActivity.class);
                }
            }
        });

    }

    /**
     * 动态文章列表数据
     *
     * @param loadType
     */
    private void initData(final int loadType) {
//        token = MyApplication.getToken(releaseActivity);
        HashMap params = new HashMap();
        params.put("baseMethod", Method.DYNAMICARTICLE);
        params.put("baseUrl", Config.baseUrl2);
        params.put("token", token);
        params.put("registerId", registerId);
        params.put("type", tabStr);
        params.put("pageSize", "10");
        params.put("currentPage", page + "");
        params.put("timestamp", timeStemp);

        OkHttpUtil.getRequest(releaseActivity, params, new RequestCallBack<AttentionSocialInfoBean>() {
            @Override
            public void onSuccess(Response<AttentionSocialInfoBean> response) {
                AttentionSocialInfoBean attentionSocialInfoBean = response.body();
                if (!EmptyUtil.isEmpty(attentionSocialInfoBean)) {
                    int resultCode = attentionSocialInfoBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        AttentionSocialInfoBean.ResultBean resultBean = attentionSocialInfoBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            totalPage = resultBean.getTotalPage();
                            timeStemp = resultBean.getTimestamp();
                            dataBean = resultBean.getData();

                            if (totalPage > 0) {
                                if (loadType == StatusVariable.LOADMORE) {
                                    dataList.addAll(dataBean);
                                    releaseAdapter.loadMoreData(dataBean);
                                    binding.smartRefreshlayout.finishLoadMore();
                                } else {
                                    dataList.clear();
                                    dataList.addAll(dataBean);
                                    releaseAdapter.loadData(dataBean);
                                    binding.smartRefreshlayout.finishRefresh();
                                }

                                if (tabStr.equals(StatusVariable.DYNAMICSTR)) {
                                    dynamicPage = page;
                                    dynamicList.addAll(dataList);
                                } else if (tabStr.equals(StatusVariable.ARTICLESTR)) {
                                    articlePage = page;
                                    articleList.addAll(dataList);
                                }
                            } else {
                                releaseAdapter.loadData(new ArrayList<AttentionSocialInfoBean.ResultBean.DataBean>());
                                toastUtil.centerToast(releaseActivity.getResources().getString(R.string.data_null));
                            }
                        }
                        page++;
                    }

                }
            }

            @Override
            public AttentionSocialInfoBean parseNetworkResponse(String jsonResult) {

                AttentionSocialInfoBean attentionSocialInfoBean = JSON.parseObject(jsonResult, AttentionSocialInfoBean.class);

                return attentionSocialInfoBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });

    }

    /**
     * 用户信息
     */
    private void initUserData() {
//        token = MyApplication.getToken(releaseActivity);
        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl2);
        params.put("token", token);
        params.put("baseMethod", Method.GETUSERCENTERINFO);
        params.put("registerId", registerId);

        OkHttpUtil.getRequest(releaseActivity, params, new RequestCallBack<UserCenterInfo>() {
            @Override
            public void onSuccess(Response<UserCenterInfo> response) {
                UserCenterInfo userCenterInfo = response.body();
                if (!EmptyUtil.isEmpty(userCenterInfo)) {
                    int resultCode = userCenterInfo.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        UserCenterInfo.ResultBean resultBean = userCenterInfo.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            isOneself = resultBean.getIsOneself();
                            UserCenterInfo.ResultBean.UserBean userBean = resultBean.getUser();
                            releaseActivity.setTitle(userBean.getRegisterNickName());
                        if (isOneself.equals("0")) {
                            if (!EmptyUtil.isEmpty(userBean.getRegisterNickName())) {
                                releaseActivity.setTitle(userBean.getRegisterNickName());
                            } else {
                                releaseActivity.setTitle("游客");
                            }
                            tv_attention.setVisibility(View.VISIBLE);
                            String isFollow = userCenterInfo.getResult().getUser().getIsFollow();
                            if (isFollow.equals("0")) {
                                tv_attention.setText("关注");
                                tv_attention.setTextColor(releaseActivity.getResources().getColor(R.color.color_FF8F00));
                                tv_attention.setBackground(releaseActivity.getResources().getDrawable(R.drawable.attentionbg_no));
                            } else {
                                tv_attention.setText("已关注");
                                tv_attention.setTextColor(releaseActivity.getResources().getColor(R.color.color_999999));
                                tv_attention.setBackground(releaseActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
                            }
                        } else {
                            if (!EmptyUtil.isEmpty(userBean.getRegisterNickName())) {
                                releaseActivity.setTitle(userBean.getRegisterNickName());
                            } else {
                                releaseActivity.setTitle("游客");
                            }
                            tv_attention.setVisibility(View.GONE);
                        }
                        //粉丝
                        if (!EmptyUtil.isEmpty(userBean.getFansNumber())) {
                            tv_fansCount.setText(userBean.getFansNumber() + "");
                        } else {
                            tv_fansCount.setText("0");
                        }
                        //发布
                        if (!EmptyUtil.isEmpty(userBean.getReleaseNumber())) {
                            tv_releaseCount.setText(userBean.getReleaseNumber() + "");
                        } else {
                            tv_releaseCount.setText("0");
                        }
                        //关注
                        if (!EmptyUtil.isEmpty(userBean.getFollowNumber())) {
                            tv_attentionCount.setText(userBean.getFollowNumber() + "");
                        } else {
                            tv_attentionCount.setText("0");
                        }
                        if (!EmptyUtil.isEmpty(userBean.getRegisterPhoto())) {
                            GlideUtil.LoadCircleImage(releaseActivity, userBean.getRegisterPhoto(), pcrelease_img);
                        } else {
                            pcrelease_img.setImageResource(R.mipmap.icon_defaultheader_yes);
                        }

                        if (!EmptyUtil.isEmpty(userBean)) {

                            String status = userBean.getStatus();
                            String certType = userBean.getCertType();

                            if (!EmptyUtil.isEmpty(status)) {
                                //认证状态
                                if (isOneself.equals("0")) {
                                    tv_authStatus.setClickable(false);
                                    tv_authStatus.setTextColor(releaseActivity.getResources().getColor(R.color.color_black));
                                    vImg.setImageResource(R.mipmap.icon_v);
                                    ll_authstatus.setPadding(0, 10, 10, 10);
                                    if (status.equals(StatusVariable.WAIT_AUTH)) {//未认证
                                        tv_authStatus.setText("未认证");
                                    } else if (status.equals(StatusVariable.PASS)) {//已认证
                                        tv_authStatus.setText("已认证");
                                    } else if (status.equals(StatusVariable.REJECT)) {//认证失败
                                        tv_authStatus.setText("未认证");
                                    } else if (status.equals(StatusVariable.WAIT_AUDIT)) {//认证中
                                        tv_authStatus.setText("未认证");
                                    }
                                } else {
                                    vImg.setImageResource(R.mipmap.icon_white_v);
                                    ll_authstatus.setPadding(10, 10, 10, 10);
                                    ll_authstatus.setBackground(releaseActivity.getResources().getDrawable(R.drawable.release_bg));
                                    tv_authStatus.setTextColor(releaseActivity.getResources().getColor(R.color.color_FFFFFF));
                                    tv_authStatus.setClickable(true);
                                    if (status.equals(StatusVariable.WAIT_AUTH)) {//未认证
                                        tv_authStatus.setText("申请认证");
                                    } else if (status.equals(StatusVariable.PASS)) {//已认证
                                        tv_authStatus.setText("已认证");
                                    } else if (status.equals(StatusVariable.REJECT)) {//认证失败
                                        tv_authStatus.setText("申请认证");
                                    } else if (status.equals(StatusVariable.WAIT_AUDIT)) {//认证中
                                        tv_authStatus.setText("申请认证");
                                    }

                                }
                            } else {
                                if (isOneself.equals("0")) {
                                    tv_authStatus.setClickable(false);
                                    tv_authStatus.setTextColor(releaseActivity.getResources().getColor(R.color.color_black));
                                    vImg.setImageResource(R.mipmap.icon_v);
                                    ll_authstatus.setPadding(0, 10, 10, 10);
                                    if (!EmptyUtil.isEmpty(status)) {
                                        if (status.equals(StatusVariable.WAIT_AUTH)) {//未认证
                                            tv_authStatus.setText("未认证");
                                        } else if (status.equals(StatusVariable.PASS)) {//已认证
                                            tv_authStatus.setText("已认证");
                                        } else if (status.equals(StatusVariable.REJECT)) {//认证失败
                                            tv_authStatus.setText("未认证");
                                        } else if (status.equals(StatusVariable.WAIT_AUDIT)) {//认证中
                                            tv_authStatus.setText("未认证");
                                        }

                                    } else {
                                        tv_authStatus.setText("未认证");
                                    }

                                } else {
                                    vImg.setImageResource(R.mipmap.icon_white_v);
                                    ll_authstatus.setPadding(10, 10, 10, 10);
                                    ll_authstatus.setBackground(releaseActivity.getResources().getDrawable(R.drawable.release_bg));
                                    tv_authStatus.setTextColor(releaseActivity.getResources().getColor(R.color.color_FFFFFF));
                                    tv_authStatus.setClickable(true);
                                    if (!EmptyUtil.isEmpty(status)) {
                                        if (status.equals(StatusVariable.WAIT_AUTH)) {//未认证
                                            tv_authStatus.setText("申请认证");
                                        } else if (status.equals(StatusVariable.PASS)) {//已认证
                                            tv_authStatus.setText("已认证");
                                        } else if (status.equals(StatusVariable.REJECT)) {//认证失败
                                            tv_authStatus.setText("申请认证");
                                        } else if (status.equals(StatusVariable.WAIT_AUDIT)) {//认证中
                                            tv_authStatus.setText("申请认证");
                                        }
                                    } else {
                                        tv_authStatus.setText("申请认证");

                                    }
                                }

                            }
                            certStatus = certType;
                            approveStatus = status;
                        }

                        tv_dynamic.setText("动态" + "(" + userBean.getDynamicNumber() + ")");
                        tv_article.setText("文章" + "(" + userBean.getArticleNumber() + ")");
                        }

                    }
                }
            }

            @Override
            public UserCenterInfo parseNetworkResponse(String jsonResult) {

                UserCenterInfo userCenterInfo = JSON.parseObject(jsonResult, UserCenterInfo.class);

                return userCenterInfo;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
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
    private void sharePop(final AttentionSocialInfoBean.ResultBean.DataBean dataBean, final int position) {

        final String nickName = application.getUserBean().getNickName();

        PopWindowUtil.share(releaseActivity, new ShareCallBack() {
            @Override
            public void wechatQuanShare(PopupWindow popupWindow) {

                shareUtil.shareFriendsQuan(dataBean.getShareURl(), "", R.mipmap.icon_share, nickName);
                HttpLog.shareHttp(releaseActivity, token, dataBean.getId(), new ShareStatusCallback() {
                    @Override
                    public void shareSuccess() {
                        dataList.get(position).setShareCount(dataList.get(position).getShareCount() + 1);
                        releaseAdapter.loadData(dataList);
                    }

                    @Override
                    public void shareFailed() {

                    }
                });
            }

            @Override
            public void weChatFriendsShare(PopupWindow popupWindow) {

                shareUtil.shareFriends(dataBean.getShareURl(), dataBean.getContent(), R.mipmap.icon_share, nickName);
                HttpLog.shareHttp(releaseActivity, token, dataBean.getId(), new ShareStatusCallback() {
                    @Override
                    public void shareSuccess() {
                        dataList.get(position).setShareCount(dataList.get(position).getShareCount() + 1);
                        releaseAdapter.loadData(dataList);
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
    public void praise(String id, final int pos) {
      HashMap params = new HashMap();
        params.put("token", token);
        params.put("socialInfoId", id);
        params.put("baseMethod", Method.PRAISE);
        params.put("baseUrl", Config.baseUrl2);

        HttpRequestUtil.favour(releaseActivity, token, id, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    if (resultBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
                        if (resultBean.getResultMsg().equals("点赞成功")) {
                            dataList.get(pos).setIsLike("1");
                            dataList.get(pos).setLikesCount(dataList.get(pos).getLikesCount() + 1);
                        } else if (resultBean.getResultMsg().equals("取消点赞成功")) {
                            dataList.get(pos).setIsLike("0");
                            dataList.get(pos).setLikesCount(dataList.get(pos).getLikesCount() - 1);
                        }
                        releaseAdapter.loadData(dataList);
                        toastUtil.centerToast(resultBean.getResultMsg());
                    } else {
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
     * 关注
     */
    public void attention() {

        HttpRequestUtil.attention(releaseActivity, token, registerId, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {

                if (!EmptyUtil.isEmpty(resultBean)) {
                    if (resultBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
                        if (resultBean.getResultMsg().equals("关注用户成功")) {
                            tv_attention.setText("已关注");
                            tv_attention.setTextColor(releaseActivity.getResources().getColor(R.color.color_999999));
                            tv_attention.setBackground(releaseActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
                        } else {
                            tv_attention.setText("关注");
                            tv_attention.setTextColor(releaseActivity.getResources().getColor(R.color.color_FF8F00));
                            tv_attention.setBackground(releaseActivity.getResources().getDrawable(R.drawable.attentionbg_no));
                        }
                    }
                    toastUtil.centerToast(resultBean.getResultMsg());
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
     * 查看大图
     *
     * @param picsBeansList
     * @param position
     */
    private void showBigImage(List<AttentionSocialInfoBean.ResultBean.DataBean.PicsBean> picsBeansList, int position) {

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
        PopWindowUtil.lookBigImg(releaseActivity, picBeansList, position);

    }


    /**
     * 申请认证
     */
    public void approve() {

        tv_authStatus.setClickable(false);
        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl3);
        params.put("token", token);
        params.put("baseMethod", Method.GETCERTIFICATIONINFO);
        params.put("terminal", "ANDROID");

        OkHttpUtil.getRequest(releaseActivity, params, new RequestCallBack<ApproveBean>() {
            @Override
            public void onSuccess(Response<ApproveBean> response) {
                ApproveBean approveBean = response.body();
                if (!EmptyUtil.isEmpty(approveBean)){
                    int resultCode = approveBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        ApproveBean.ResultBean resultBean = approveBean.getResult();
                        String status = resultBean.getAuthStatus();
                        auth = resultBean.getAuth();

                        if (!EmptyUtil.isEmpty(auth)) {
                            String certType = auth.getCertType();
                            certStatus = certType;
                        }
                        if (!EmptyUtil.isEmpty(status)) {
                            //认证状态

//                            String registerCode = (String) SharedPreferencesUtils.getParam(releaseActivity, "registerCode", "");
                            statusType = (String) SharedPreferencesUtil.getParam(releaseActivity, registerCode, "");
                            if (!EmptyUtil.isEmpty(registerCode)) {
                                if (status.equals(StatusVariable.WAIT_AUTH)) {
//                                    intent = new Intent(releaseActivity, ApproveActivity.class);
                                } else {
                                    if (!EmptyUtil.isEmpty(auth)) {
                                        if (status.equals(StatusVariable.REJECT)) {
                                            if (status.equals(statusType)) {
//                                                intent = new Intent(releaseActivity, ApproveActivity.class);
                                            } else {
//                                                intent = new Intent(releaseActivity, ApproveResultActivity.class);
                                                intent.putExtra("certType", certStatus);
                                                intent.putExtra("authStatus", (Serializable) auth);
                                                intent.putExtra("type", "personalcenter");
                                            }
                                        } else {
//                                            intent = new Intent(releaseActivity, ApproveResultActivity.class);
                                            intent.putExtra("certType", certStatus);
                                            intent.putExtra("authStatus", (Serializable) auth);
                                            intent.putExtra("type", "personalcenter");
                                        }

                                    } else {
//                                        intent = new Intent(releaseActivity, ApproveActivity.class);
                                    }
                                }
                                releaseActivity.startActivity(intent);
                                tv_authStatus.setClickable(true);
                            }
                        } else {
//                            intent = new Intent(releaseActivity, ApproveActivity.class);
//                            releaseActivity.startActivity(intent);
                        }


                    }


                }
            }

            @Override
            public ApproveBean parseNetworkResponse(String jsonResult) {

                ApproveBean approveBean = JSON.parseObject(jsonResult, ApproveBean.class);

                return approveBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });


//        OkhttpUtils.getRequest(releaseActivity, params, new BaseHttpCallBack<ApproveBean>(releaseActivity, false) {
//
//            private String statusType;
//
//            @Override
//            public ApproveBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                ApproveBean approveBean = JSONObject.parseObject(jsonResult, ApproveBean.class);
//
//                return approveBean;
//            }
//
//            @Override
//            public void onSuccess(ApproveBean approveBean, Call call, Response response) {
//                if (!EmptyUtil.isEmpty(approveBean)) {
//
//                    if (approveBean.getResultCode() == StatusVariable.SUCCESS) {
//
//                        String status = approveBean.getResult().getAuthStatus();
//                        auth = approveBean.getResult().getAuth();
//
//                        if (!EmptyUtil.isEmpty(auth)) {
//                            String certType = auth.getCertType();
//                            certStatus = certType;
//                        }
//                        if (!EmptyUtil.isEmpty(status)) {
//                            //认证状态
//
//                            String registerCode = (String) SharedPreferencesUtils.getParam(releaseActivity, "registerCode", "");
//                            statusType = (String) SharedPreferencesUtils.getParam(releaseActivity, registerCode, "");
//                            if (!EmptyUtil.isEmpty(registerCode)) {
//                                if (status.equals(StatusVariable.WAITAUTH)) {
//                                    intent = new Intent(releaseActivity, ApproveActivity.class);
//                                } else {
//                                    if (!EmptyUtil.isEmpty(auth)) {
//                                        if (status.equals(StatusVariable.REJECT)) {
//                                            if (status.equals(statusType)) {
//                                                intent = new Intent(releaseActivity, ApproveActivity.class);
//                                            } else {
//                                                intent = new Intent(releaseActivity, ApproveResultActivity.class);
//                                                intent.putExtra("certType", certStatus);
//                                                intent.putExtra("authStatus", (Serializable) auth);
//                                                intent.putExtra("type", "personalcenter");
//                                            }
//                                        } else {
//                                            intent = new Intent(releaseActivity, ApproveResultActivity.class);
//                                            intent.putExtra("certType", certStatus);
//                                            intent.putExtra("authStatus", (Serializable) auth);
//                                            intent.putExtra("type", "personalcenter");
//                                        }
//
//                                    } else {
//                                        intent = new Intent(releaseActivity, ApproveActivity.class);
//                                    }
//                                }
//                                releaseActivity.startActivity(intent);
//                                tv_authStatus.setClickable(true);
//                            }
//                        } else {
//                            intent = new Intent(releaseActivity, ApproveActivity.class);
//                            releaseActivity.startActivity(intent);
//                        }
//
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                ToastUtils.toast(releaseActivity, msg);
//            }
//        });


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (releaseActivity.refreshStatus.get().equals("refresh")) {
//            releaseActivity.refreshStatus.set("normal");
//            timeStemp = "";
//            dynamicList.clear();
//            articleList.clear();
//            page = 1;
//            initData("load");
//            initUserData();
////            SharedPreferencesUtils.setParam(releaseActivity, "ActivityState", true);
//        }
//
//    }
    /**
     * 初始化点击事件
     */
    private void initClick() {

        releaseAdapter.setReleaseClick(new ReleaseAdapter.ReleaseClick() {
            @Override
            public void releaseclick(int type, AttentionSocialInfoBean.ResultBean.DataBean dataBean, int position) {
//                if (login) {
                if (type == StatusVariable.SHARE) {
                    sharePop(dataBean, position);
                } else if (type == StatusVariable.FAVOUR) {
                    praise(dataBean.getId(), position);
                }
            }
        });
        //查看大图
        releaseAdapter.setLookImgClick(new ReleaseAdapter.LookReleaseImgClick() {
            @Override
            public void lookimgClick(List<AttentionSocialInfoBean.ResultBean.DataBean.PicsBean> picsBeansList, int position) {
                showBigImage(picsBeansList, position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_dynamic:
                tabType = 0;
                releaseActivity.tabPosition = 0;
                ll_dynamic.setClickable(false);
                ll_article.setClickable(true);
                tv_dynamic.setTextColor(releaseActivity.getResources().getColor(R.color.color_FF8F00));
                tab_dynamic.setVisibility(View.VISIBLE);
                tv_article.setTextColor(releaseActivity.getResources().getColor(R.color.color_333333));
                tab_article.setVisibility(View.GONE);
                tabData(tabType);
                break;
            case R.id.ll_article:
                releaseActivity.tabPosition = 1;
                ll_dynamic.setClickable(true);
                ll_article.setClickable(false);
                tabType = 1;
                tv_article.setTextColor(releaseActivity.getResources().getColor(R.color.color_FF8F00));
                tab_article.setVisibility(View.VISIBLE);
                tv_dynamic.setTextColor(releaseActivity.getResources().getColor(R.color.color_333333));
                tab_dynamic.setVisibility(View.GONE);
                tabData(tabType);
                break;
        }
    }

    private void tabData(int tabType) {

        if (tabType == 0) {
            tabStr = "dynamic";
            if (dynamicList.size() > 0) {
                page = dynamicPage;
                releaseAdapter.loadData(dynamicList);
            } else {
                page = 1;
                timeStemp = "";
                initData(StatusVariable.REFRESH);
            }
        } else if (tabType == 1) {
            tabStr = "article";
            if (articleList.size() > 0) {
                page = articlePage;
                releaseAdapter.loadData(articleList);
            } else {
                timeStemp = "";
                page = 1;
                initData(StatusVariable.REFRESH);
            }
        }
    }

    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {

    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        page = 1;
        timeStemp = "";
        initData(StatusVariable.REFRESH);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserBean appuserBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(appuserBean)) {
            token = appuserBean.getToken();
            registerCode = appuserBean.getRegisterCode();
        } else {
            token = "";
            registerCode = "";
        }
        initUserData();
    }
}
