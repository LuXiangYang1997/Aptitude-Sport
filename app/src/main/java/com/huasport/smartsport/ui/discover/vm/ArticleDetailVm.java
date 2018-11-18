package com.huasport.smartsport.ui.discover.vm;

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
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.ArticledetailLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.ArticleDetailAdapter;
import com.huasport.smartsport.ui.discover.bean.ArticleDetailBean;
import com.huasport.smartsport.ui.discover.bean.CommentFavourBean;
import com.huasport.smartsport.ui.discover.bean.FavourBean;
import com.huasport.smartsport.ui.discover.view.ArticleDetailActivity;
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
import com.huasport.smartsport.util.popwindow.ShareCallBack;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleDetailVm extends BaseViewModel implements View.OnClickListener, RefreshLoadMoreListener {

    private ArticleDetailActivity articleDetailActivity;
    private ArticleDetailAdapter articleDetailAdapter;
    private ArticledetailLayoutBinding binding;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private MyApplication application;
    private String dynamicId;
    private ShareUtil shareUtil;
    private String token = "";
    private String registerCode = "";
    private PopupWindow popWindow;
    private int page = 1;
    private String timestamp = "";
    private int tabType = 0;
    private int totalPage = 0;
    private ArticleDetailBean.ResultBean.DataBean dataBean;
    private EditText editText;
    private String commentId;
    private String nickName = "";
    private String registerID = "";
    private String replyId = "";
    private UserBean userBean;

    public ArticleDetailVm(ArticleDetailActivity articleDetailActivity, ArticleDetailAdapter articleDetailAdapter) {
        this.articleDetailActivity = articleDetailActivity;
        this.articleDetailAdapter = articleDetailAdapter;
        binding = articleDetailActivity.getBinding();
        init();
        initClick();
        initHeaderData();
        initCommentListData(StatusVariable.REFRESH);
    }

    /**
     * 初始化
     */
    private void init() {

        //初始化toast
        toastUtil = new ToastUtil(articleDetailActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(articleDetailActivity, R.style.LoadingDialog);
        //初始化Application
        application = MyApplication.getInstance();
        //初始化刷新加载
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //文章Id ，上个界面带来
        dynamicId = articleDetailActivity.getIntent().getStringExtra("dynamicId");
        //初始化分享
        shareUtil = new ShareUtil(articleDetailActivity);
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
     * 初始化头部数据
     */
    private void initHeaderData() {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl2);
        params.put("id", dynamicId);
        params.put("baseMethod", Method.DYNAMICARTICLEDETAIL);

        OkHttpUtil.getRequest(articleDetailActivity, params, new RequestCallBack<ArticleDetailBean>() {
            @Override
            public void onSuccess(Response<ArticleDetailBean> response) {
                ArticleDetailBean articleDetailBean = response.body();

                if (!EmptyUtil.isEmpty(articleDetailBean)) {
                    int resultCode = articleDetailBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        ArticleDetailBean.ResultBean resultBean = articleDetailBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            String isOneself = resultBean.getIsOneself();
                            dataBean = resultBean.getData();

                            registerID = articleDetailBean.getResult().getData().getRegisterID();

                            articleDetailActivity.isOneSelf.set(isOneself);

                            articleDetailActivity.dyId.set(articleDetailBean.getResult().getData().getRegisterID());

                            // registerCode = (String) SharedPreferencesUtils.getParam(articleDetailActivity, "registerCode", "");

                            if (!registerCode.equals(articleDetailBean.getResult().getData().getRegisterID())) {
                                binding.tvFollow.setVisibility(View.VISIBLE);
                            } else {
                                articleDetailActivity.setRightText();
                                binding.tvFollow.setVisibility(View.GONE);
                            }

                            if (dataBean.getIsLike().equals("0")) {
                                binding.imgDynamiczan.setImageResource(R.mipmap.icon_grayzan);
                            } else {
                                binding.imgDynamiczan.setImageResource(R.mipmap.icon_lightzan);
                            }
                            //标题
                            if (!EmptyUtil.isEmpty(dataBean.getTitle())) {
                                binding.tvArticleTitle.setText((String) dataBean.getTitle());
                            }

                            //名字
                            if (!EmptyUtil.isEmpty(dataBean.getRegisterNickName())) {
                                binding.tvArticleName.setText(dataBean.getRegisterNickName());
                            } else {
                                binding.tvArticleName.setText(articleDetailActivity.getResources().getString(R.string.tourist));
                            }
                            //头像
                            if (!EmptyUtil.isEmpty(dataBean.getRegisterPhoto())) {
                                GlideUtil.LoadCircleImage(articleDetailActivity, dataBean.getRegisterPhoto(), binding.imgHeader);
                            } else {
                                binding.imgHeader.setImageResource(R.mipmap.icon_defaultheader_yes);

                            }
//                        //详情
                            String pruduce = (String) dataBean.getPosition();
                            //是否是认证 1:是 0：否
                            if (!EmptyUtil.isEmpty(dataBean.getIsAuth())) {
                                if (dataBean.getIsAuth().equals(StatusVariable.YESAUTH)) {
                                    binding.imgV.setVisibility(View.VISIBLE);
                                } else {
                                    binding.imgV.setVisibility(View.GONE);
                                }
                            }
                            //是否是精品 0：不是 1：是
                            if (!EmptyUtil.isEmpty(dataBean.getSplendidStatic())) {
                                if (dataBean.getSplendidStatic().equals(StatusVariable.SPLENDIDSTATIC)) {
                                    binding.articlesift.setVisibility(View.VISIBLE);
                                } else {
                                    binding.articlesift.setVisibility(View.GONE);
                                }
                            }
                            String position = (String) dataBean.getPosition();
                            long time = dataBean.getCreateTime();
                            long fronttimelong = DateUtil.frontDate();//当前时间的前一天时间
                            //动态时间
                            if (!EmptyUtil.isEmpty(time)) {
                                String date = DateUtil.DateToStr(time, StatusVariable.HM);
                                if (DateUtil.isSameDay(System.currentTimeMillis(), time)) {
                                    if (!EmptyUtil.isEmpty(pruduce)) {
                                        binding.tvArticletime.setText("今天" + date + " " + (String) dataBean.getPosition());
                                    } else {
                                        binding.tvArticletime.setText("今天" + date);
                                    }
                                } else if (DateUtil.isSameDay(fronttimelong, time)) {
                                    if (!EmptyUtil.isEmpty(pruduce)) {
                                        binding.tvArticletime.setText("昨天" + date + " " + (String) dataBean.getPosition());
                                    } else {
                                        binding.tvArticletime.setText("昨天" + date);
                                    }

                                } else {
                                    String timeStr = DateUtil.DateToStr(time, StatusVariable.YMD);
                                    if (!EmptyUtil.isEmpty(timeStr)) {
                                        if (!EmptyUtil.isEmpty(position)) {
                                            binding.tvArticletime.setText(timeStr + " " + position);
                                        } else {
                                            binding.tvArticletime.setText(timeStr);
                                        }
                                    } else {
                                        binding.tvArticletime.setText(position);
                                    }


                                }
                            } else {
                                if (!EmptyUtil.isEmpty(pruduce)) {
                                    binding.tvArticletime.setText((String) dataBean.getPosition());
                                }
                            }
                            //平台类型
                            String platform = articleDetailBean.getResult().getData().getPlatform();
                            String content = articleDetailBean.getResult().getData().getContent();
                            if (!EmptyUtil.isEmpty(platform)) {

                                if (!EmptyUtil.isEmpty(content)) {
                                    String content1 = Util.getFinalContent(content);
                                    String appNewContent = Util.getAPPNewContent(content1);
                                    binding.webWebView.loadData(appNewContent, "text/html; charset=UTF-8", null);
                                }

                            }
                            if (!EmptyUtil.isEmpty(dataBean.getIsFollow())) {
                                String isFollow = dataBean.getIsFollow();
                                if (isFollow.equals("1")) {
                                    binding.tvFollow.setEnabled(true);
                                    binding.tvFollow.setText("已关注");
                                    binding.tvFollow.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_999999));
                                    binding.tvFollow.setBackground(articleDetailActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
                                } else {
                                    binding.tvFollow.setEnabled(true);
                                    binding.tvFollow.setText("关注");
                                    binding.tvFollow.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_FF8F00));
                                    binding.tvFollow.setBackground(articleDetailActivity.getResources().getDrawable(R.drawable.attentionbg_no));
                                }
                            }
                            binding.tvCommand.setText("评论" + dataBean.getCommentsCount());
                            binding.tvFavour.setText("点赞" + dataBean.getLikesCount());
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
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    /**
     * 评论数据
     *
     * @param loadType
     */
    private void initCommentListData(final int loadType) {

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

                if (!EmptyUtil.isEmpty(commentFavourBean)) {
                    int resultCode = commentFavourBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        CommentFavourBean.ResultBean resultBean = commentFavourBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            totalPage = resultBean.getTotalPage();
                            List<CommentFavourBean.ResultBean.DataBean> dataList = resultBean.getData();

                            if (tabType == 0) {
                                binding.tvCommand.setText("评论" + commentFavourBean.getResult().getAllRow());
                                if (totalPage > 0) {

                                    if (loadType == StatusVariable.LOADMORE) {
                                        articleDetailAdapter.loadMoreData(dataList);
                                        binding.smartRefreshlayout.finishLoadMore();
                                    } else {
                                        binding.smartRefreshlayout.finishRefresh();
                                        articleDetailAdapter.loadData(dataList);
                                    }
                                } else {
                                    toastUtil.centerToast(articleDetailActivity.getResources().getString(R.string.command_null));
                                    articleDetailAdapter.loadData(new ArrayList<CommentFavourBean.ResultBean.DataBean>());
                                }
                            } else {
                                binding.tvCommand.setText("评论" + commentFavourBean.getResult().getAllRow());
                            }
                        }
                        page++;
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
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    /**
     * 点赞数据
     *
     * @param loadType
     */
    public void initFavourListData(final int loadType) {

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

                if (!EmptyUtil.isEmpty(favourBean)) {
                    int resultCode = favourBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        FavourBean.ResultBean resultBean = favourBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            totalPage = resultBean.getTotalPage();

                            if (tabType == 0) {
                                binding.tvFavour.setText("点赞" + resultBean.getTotal());
                            } else {
                                binding.tvFavour.setText("点赞" + resultBean.getTotal() + "");

                                if (totalPage > 0) {
                                    List<FavourBean.ResultBean.ListBean> list = resultBean.getList();
                                    List<CommentFavourBean.ResultBean.DataBean> data = new ArrayList<>();
                                    if (!EmptyUtil.isEmpty(list)) {
                                        for (FavourBean.ResultBean.ListBean listBean : list) {
                                            CommentFavourBean.ResultBean.DataBean dataBean = new CommentFavourBean.ResultBean.DataBean();
                                            dataBean.setPosition(listBean.getPosition());
                                            dataBean.setRegisterId(listBean.getRegisterId());
                                            dataBean.setRegisterNickName(listBean.getRegisterNickName());
                                            dataBean.setRegisterPhoto(listBean.getRegisterPhoto());
                                            dataBean.setCreateDate(listBean.getCreateDate());
                                            dataBean.setIsAuth(listBean.getIsAuth());
                                            data.add(dataBean);
                                        }

                                        if (!EmptyUtil.isEmpty(data)) {
                                            if (loadType == StatusVariable.REFRESH) {
                                                articleDetailAdapter.loadData(data);
                                            } else {
                                                articleDetailAdapter.loadMoreData(data);
                                            }
                                        }
                                    }
                                } else {
                                    toastUtil.centerToast(articleDetailActivity.getResources().getString(R.string.zan_null));
                                    articleDetailAdapter.loadData(new ArrayList<CommentFavourBean.ResultBean.DataBean>());
                                }
                            }
                        }
                        page++;
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
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.centerToast(msg);
                }

            }
        });

    }

    /**
     * 删除文章
     */
    public void deleteArticle() {

        if (EmptyUtil.isEmpty(token)) {
            IntentUtil.startActivity(articleDetailActivity, LoginActivity.class);
            return;
        }

        HttpRequestUtil.delete(articleDetailActivity, token, dynamicId, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        toastUtil.centerToast(articleDetailActivity.getResources().getString(R.string.delete_success));
                        articleDetailActivity.finish();
                    } else {
                        toastUtil.centerToast(articleDetailActivity.getResources().getString(R.string.delete_failed));
                    }
                }
            }

            @Override
            public void httpFailed(int code, String msg) {
                toastUtil.centerToast(msg);
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

        if (EmptyUtil.isEmpty(token)) {
            IntentUtil.startActivity(articleDetailActivity, LoginActivity.class);
            return;
        }

        HttpRequestUtil.attention(articleDetailActivity, token, dataBean.getRegisterID(), new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {


                        if (resultBean.getResultMsg().equals("关注用户成功")) {
                            binding.tvFollow.setEnabled(true);
                            binding.tvFollow.setText("已关注");
                            binding.tvFollow.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_999999));
                            binding.tvFollow.setBackground(articleDetailActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
                        } else {
                            binding.tvFollow.setEnabled(true);
                            binding.tvFollow.setText("关注");
                            binding.tvFollow.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_FF8F00));
                            binding.tvFollow.setBackground(articleDetailActivity.getResources().getDrawable(R.drawable.attentionbg_no));
                        }
                        toastUtil.centerToast(resultBean.getResultMsg());
                    } else {
                        toastUtil.centerToast(resultBean.getResultMsg());
                    }
                }
            }

            @Override
            public void httpFailed(int code, String msg) {
                toastUtil.centerToast(msg);
            }

            @Override
            public void httpFinish() {

            }
        });
    }

    /**
     * 文章评论
     */
    public void articleComment() {

        showCommentEdit("articleComment");

    }

    /**
     * 评论
     *
     * @param
     */
    public void showCommentEdit(final String commentType) {

        PopWindowUtil.commandPop(articleDetailActivity, new CommandCallback() {
            @Override
            public void releaseCommand(PopupWindow popupWindow, String s, EditText commentEdittext) {
                popWindow = popupWindow;
                editText = commentEdittext;
                if (EmptyUtil.isEmpty(s)) {
                    toastUtil.centerToast(articleDetailActivity.getResources().getString(R.string.command_nulltip));
                } else {
                    if (commentType.equals("articleComment")) {
                        comment(dynamicId, s);
                    } else {
                        commentReview(s);
                    }
                }
                Util.softBoard(articleDetailActivity);
            }
        });
    }

    /**
     * 对评论进行评论
     * @param replyContent
     */
    public void commentReview(String replyContent) {

        HttpRequestUtil.commentReview(articleDetailActivity, token, commentId, replyId, replyContent, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        editText.setText("");
                        refreshCommand();
                        toastUtil.centerToast(articleDetailActivity.getResources().getString(R.string.command_success));
                    } else {
                        toastUtil.centerToast(resultBean.getResultMsg());
                    }
                }
            }

            @Override
            public void httpFailed(int code, String msg) {
                toastUtil.centerToast(msg);
            }

            @Override
            public void httpFinish() {

            }
        });
    }

    /**
     * 评论
     */
    public void comment(String id, String content) {

        HttpRequestUtil.comment(articleDetailActivity, token, id, content, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        refreshCommand();
                        editText.setText("");
                        toastUtil.centerToast(articleDetailActivity.getResources().getString(R.string.command_success));
                    } else {
                        toastUtil.centerToast(resultBean.getResultMsg());
                    }
                }
            }

            @Override
            public void httpFailed(int code, String msg) {
                toastUtil.centerToast(msg);
            }

            @Override
            public void httpFinish() {

            }
        });

    }

    /**
     * 分享
     */
    public void shareClick() {
        if (!EmptyUtil.isEmpty(dataBean)) {
            UserBean userBean = application.getUserBean();
            if (!EmptyUtil.isEmpty(userBean)) {
                nickName = userBean.getNickName();
            } else {
                IntentUtil.startActivity(articleDetailActivity, LoginActivity.class);
            }
            PopWindowUtil.share(articleDetailActivity, new ShareCallBack() {
                @Override
                public void wechatQuanShare(PopupWindow popupWindow) {
                    shareUtil.shareFriendsQuan(dataBean.getShareURl(), dataBean.getContent(), R.mipmap.icon_share, nickName);
                    HttpLog.shareHttp(articleDetailActivity, token, dynamicId, new ShareStatusCallback() {
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
                    HttpLog.shareHttp(articleDetailActivity, token, dynamicId, new ShareStatusCallback() {
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

    }

    /**
     * 点赞
     */
    public void priseClick() {

        HttpRequestUtil.favour(articleDetailActivity, token, dynamicId, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        refreshFavour();
                        if (resultBean.getResultMsg().equals("点赞成功")) {
                            binding.imgDynamiczan.setImageResource(R.mipmap.icon_lightzan);
                        } else if ((resultBean.getResultMsg().equals("取消点赞成功"))) {
                            binding.imgDynamiczan.setImageResource(R.mipmap.icon_grayzan);
                        }
                        toastUtil.centerToast(resultBean.getResultMsg());
                    }
                }
            }

            @Override
            public void httpFailed(int code, String msg) {
                toastUtil.centerToast(msg);
            }

            @Override
            public void httpFinish() {

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
     * 初始化点击事件
     */
    private void initClick() {

        binding.llCommand.setOnClickListener(this);
        binding.llFavour.setOnClickListener(this);

        articleDetailAdapter.setArticleClick(new ArticleDetailAdapter.ArticleClick() {
            @Override
            public void articleclick(CommentFavourBean.ResultBean.DataBean dataBean, int position, String id) {
                if (EmptyUtil.isEmpty(token)) {
                    IntentUtil.startActivity(articleDetailActivity, LoginActivity.class);
                    return;
                }
                if (!registerID.equals(registerCode)) {
                    if (registerCode.equals(registerID) || id.equals(registerCode)) {
                        commentId = dataBean.getId();
                        showCommentEdit("reView");
                    } else {
                        toastUtil.centerToast(articleDetailActivity.getResources().getString(R.string.forbid_command));
                    }
                } else {
                    String socialInfoId = dataBean.getId();
                    commentId = socialInfoId;
                    showCommentEdit("reView");
                }

            }
        });

        articleDetailAdapter.setArticleReplyInfoClick(new ArticleDetailAdapter.ArticleReplyInfoClick() {
            @Override
            public void articleReplyInfoClick(CommentFavourBean.ResultBean.DataBean.ReplyInfosBean dataBean, String strid, String id) {
                if (EmptyUtil.isEmpty(token)) {
                    IntentUtil.startActivity(articleDetailActivity, LoginActivity.class);
                    return;
                }
                if (!registerID.equals(registerCode)) {
                    if (registerCode.equals(id)) {
                        commentId = strid;
                        replyId = dataBean.getCommentId();
                        showCommentEdit("reView");
                    } else {
                        toastUtil.centerToast(articleDetailActivity.getResources().getString(R.string.forbid_command));
                    }
                } else {
                    commentId = dataBean.getCommentId();
                    showCommentEdit("reView");
                }
            }
//                else {
//                    SharedPreferencesUtils.setParam(articleDetailActivity, "loginstate", true);
//                    articleDetailActivity.startActivity2(LoginActivity.class);
//                }
        });

        //关注
        binding.tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (EmptyUtil.isEmpty(token)) {
                    IntentUtil.startActivity(articleDetailActivity, LoginActivity.class);
                    return;
                }
                attention();
            }
        });

        binding.imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EmptyUtil.isEmpty(dataBean)) {
//                    intent = new Intent(articleDetailActivity, ReleaseActivity.class);
//                    intent.putExtra("registerId", data.getRegisterID());
//                    articleDetailActivity.startActivityForResult(intent, 0);
                }
            }
        });
    }

    /**
     * 刷新点赞
     */
    private void refreshFavour() {

        binding.smartRefreshlayout.setNoMoreData(false);
        page = 1;
        timestamp = "";
        initFavourListData(StatusVariable.REFRESH);
    }

    /**
     * 刷新评论
     */
    private void refreshCommand() {
        articleDetailAdapter.mapPos.clear();
        binding.smartRefreshlayout.setNoMoreData(false);
        page = 1;
        timestamp = "";
        initCommentListData(StatusVariable.REFRESH);
    }

    /**
     * 刷新
     *
     * @param refreshLayout
     * @param type
     */
    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        binding.smartRefreshlayout.setNoMoreData(false);
        page = 1;
        timestamp = "";
        initFavourListData(type);

    }

    /**
     * 加载
     *
     * @param refreshLayout
     * @param type
     */
    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {

        if (page <= totalPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            if (tabType == 0) {
                initCommentListData(type);
            } else {
                initFavourListData(type);
            }
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_command:
                tabType = 0;
                articleDetailActivity.tabPos = 0;
                binding.llCommand.setClickable(false);
                binding.llFavour.setClickable(true);
                binding.tvCommand.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tabCommand.setVisibility(View.VISIBLE);
                binding.tvFavour.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_333333));
                binding.tabFavour.setVisibility(View.GONE);
                page = 1;
                totalPage = 0;
                initCommentListData(StatusVariable.REFRESH);
                break;
            case R.id.ll_favour:
                tabType = 1;
                articleDetailActivity.tabPos = 1;
                binding.llCommand.setClickable(true);
                binding.llFavour.setClickable(false);
                binding.tvFavour.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tabFavour.setVisibility(View.VISIBLE);
                binding.tvFavour.setTextColor(articleDetailActivity.getResources().getColor(R.color.color_333333));
                binding.tabCommand.setVisibility(View.GONE);
                page = 1;
                totalPage = 0;
                initFavourListData(StatusVariable.REFRESH);
                break;
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
            registerCode = userBean.getRegisterCode();
        }else{
            token = "";
            registerCode = "";
        }
        initHeaderData();
    }

}
