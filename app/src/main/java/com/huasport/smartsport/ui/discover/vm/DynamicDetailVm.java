package com.huasport.smartsport.ui.discover.vm;

import android.support.v7.widget.GridLayoutManager;
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
import com.huasport.smartsport.databinding.DynamicDetailLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.DynamicDetailAdapter;
import com.huasport.smartsport.ui.discover.adapter.DynamicImageAdapter;
import com.huasport.smartsport.ui.discover.bean.CommentFavourBean;
import com.huasport.smartsport.ui.discover.bean.DynamicDetailBean;
import com.huasport.smartsport.ui.discover.bean.FavourBean;
import com.huasport.smartsport.ui.discover.bean.PicBean;
import com.huasport.smartsport.ui.discover.view.DynamicDetailActivity;
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

public class DynamicDetailVm extends BaseViewModel implements View.OnClickListener, RefreshLoadMoreListener {

    private DynamicDetailActivity dynamicDetailActivity;
    private DynamicDetailAdapter dynamicDetailAdapter;
    private DynamicDetailLayoutBinding binding;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private MyApplication application;
    private String dynamicId;
    private ShareUtil shareUtil;
    private String token = "";
    private String registerCode = "";
    private PopupWindow popWindow;
    private int page = 1;
    private String timeStemp = "";
    private int tabType = 0;
    private int totalPage = 0;
    private EditText editText;
    private String commentId;
    private String nickName = "";
    private String registerID = "";
    private String replyId = "";
    private UserBean userBean;
    private DynamicDetailBean.ResultBean.DataBean dataBean;
    private String isOneself;
    private DynamicImageAdapter dynamicImageAdapter;

    public DynamicDetailVm(DynamicDetailActivity dynamicDetailActivity, DynamicDetailAdapter dynamicDetailAdapter) {
        this.dynamicDetailActivity = dynamicDetailActivity;
        this.dynamicDetailAdapter = dynamicDetailAdapter;
        binding = dynamicDetailActivity.getBinding();
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
        toastUtil = new ToastUtil(dynamicDetailActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(dynamicDetailActivity, R.style.LoadingDialog);
        //初始化Application
        application = MyApplication.getInstance();
        //初始化刷新加载
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //文章Id ，上个界面带来
        dynamicId = dynamicDetailActivity.getIntent().getStringExtra("dynamicId");
        //初始化分享
        shareUtil = new ShareUtil(dynamicDetailActivity);
        userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
            registerCode = userBean.getRegisterCode();
        } else {
            token = "";
            registerCode = "";
        }
        dynamicImageAdapter = new DynamicImageAdapter(dynamicDetailActivity);
    }

    /**
     * 评论数据
     *
     * @param loadType
     */
    private void initCommentListData(final int loadType) {
//        token = MyApplication.getToken(dynamicDetailActivity);
        HashMap params = new HashMap();
        params.put("baseMethod", Method.COMMENTLIST);
        params.put("baseUrl", Config.baseUrl2);
        params.put("id", dynamicId);
        params.put("currentPage", page + "");
        params.put("pageSize", "10");
        params.put("token", token);
        params.put("timestamp", timeStemp);
        OkHttpUtil.postRequest(dynamicDetailActivity, params, new RequestCallBack<CommentFavourBean>() {
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
                                        dynamicDetailAdapter.loadMoreData(dataList);
                                        binding.smartRefreshlayout.finishLoadMore();
                                    } else {
                                        binding.smartRefreshlayout.finishRefresh();
                                        dynamicDetailAdapter.loadData(dataList);
                                    }
                                } else {
                                    toastUtil.centerToast(dynamicDetailActivity.getResources().getString(R.string.command_null));
                                    dynamicDetailAdapter.loadData(new ArrayList<CommentFavourBean.ResultBean.DataBean>());
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
        params.put("timestamp", timeStemp);

        OkHttpUtil.getRequest(dynamicDetailActivity, params, new RequestCallBack<FavourBean>() {
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
                                                dynamicDetailAdapter.loadData(data);
                                            } else {
                                                dynamicDetailAdapter.loadMoreData(data);
                                            }
                                        }
                                    }
                                } else {
                                    toastUtil.centerToast(dynamicDetailActivity.getResources().getString(R.string.zan_null));
                                    dynamicDetailAdapter.loadData(new ArrayList<CommentFavourBean.ResultBean.DataBean>());
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
     * header数据
     */
    private void initHeaderData() {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl2);
        params.put("id", dynamicId);
        params.put("baseMethod", Method.DYNAMICARTICLEDETAIL);

        OkHttpUtil.getRequest(dynamicDetailActivity, params, new RequestCallBack<DynamicDetailBean>() {
            @Override
            public void onSuccess(Response<DynamicDetailBean> response) {
                DynamicDetailBean dynamicDetailBean = response.body();

                if (!EmptyUtil.isEmpty(dynamicDetailBean)) {
                    int resultCode = dynamicDetailBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        DynamicDetailBean.ResultBean resultBean = dynamicDetailBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            dataBean = resultBean.getData();
                            isOneself = resultBean.getIsOneself();
                            registerID = dataBean.getRegisterID();

                            dynamicDetailActivity.isOneSelf.set(isOneself);
                            dynamicDetailActivity.dynamicRegisterId.set(registerID);

                            if (isOneself.equals("0")) {
                                binding.tvAttention.setVisibility(View.VISIBLE);
                            } else {
                                dynamicDetailActivity.setRightText();
                                binding.tvAttention.setVisibility(View.GONE);
                            }
                            if (dataBean.getIsLike().equals("0")) {
                                binding.imgDynamiczan.setImageResource(R.mipmap.icon_grayzan);
                            } else {
                                binding.imgDynamiczan.setImageResource(R.mipmap.icon_lightzan);
                            }
                            //名字
                            if (!EmptyUtil.isEmpty(dataBean.getRegisterNickName())) {
                                binding.tvName.setText(dataBean.getRegisterNickName());
                            } else {
                                binding.tvName.setText(dynamicDetailActivity.getResources().getString(R.string.tourist));
                            }

                            //头像
                            if (!EmptyUtil.isEmpty(dataBean.getRegisterPhoto())) {
                                GlideUtil.LoadCircleImage(dynamicDetailActivity, dataBean.getRegisterPhoto(), binding.imgHeader);
                            } else {
                                binding.imgHeader.setImageResource(R.mipmap.icon_defaultheader_yes);
                            }
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
                                    binding.imgSift.setVisibility(View.VISIBLE);
                                } else {
                                    binding.imgSift.setVisibility(View.GONE);
                                }
                            }
                            String produce = dataBean.getContent();
                            String position = (String) dataBean.getPosition();
                            long time = dataBean.getCreateTime();
                            long fronttimelong = DateUtil.frontDate();//当前时间的前一天时间

                            //动态时间
                            if (!EmptyUtil.isEmpty(time)) {
                                String date = DateUtil.DateToStr(time, StatusVariable.HM);
                                if (DateUtil.isSameDay(System.currentTimeMillis(), time)) {
                                    if (!EmptyUtil.isEmpty(position)) {
                                        binding.tvTime.setText("今天 " + date + " " + position);
                                    } else {
                                        binding.tvTime.setText("今天 " + date);
                                    }
                                } else if (DateUtil.isSameDay(fronttimelong, time)) {
                                    if (!EmptyUtil.isEmpty(position)) {
                                        binding.tvTime.setText("昨天" + date + " " + position);
                                    } else {
                                        binding.tvTime.setText("昨天" + date);
                                    }

                                } else {
                                    String timeStr = DateUtil.DateToStr(time, StatusVariable.YMD);
                                    if (!EmptyUtil.isEmpty(timeStr)) {
                                        if (!EmptyUtil.isEmpty(position)) {
                                            binding.tvTime.setText(timeStr + " " + position);
                                        } else {
                                            binding.tvTime.setText(timeStr);
                                        }
                                    } else {
                                        binding.tvTime.setText(position);
                                    }

                                }
                            } else {
                                if (!EmptyUtil.isEmpty(position)) {
                                    binding.tvTime.setText((String) dataBean.getPosition());
                                }
                            }
                            //介绍
                            if (!EmptyUtil.isEmpty(produce)) {
                                binding.tvDetail.setVisibility(View.VISIBLE);
                                String str = Util.reMoveLast(produce);
                                if (!EmptyUtil.isEmpty(str)) {
                                    binding.tvDetail.setText(str);
                                }
                            } else {
                                binding.tvDetail.setText("");
                                binding.tvDetail.setVisibility(View.GONE);
                            }
                            //关注
                            if (!EmptyUtil.isEmpty(dataBean.getIsFollow())) {
                                String isFollow = dataBean.getIsFollow();
                                if (isFollow.equals("1")) {
                                    binding.tvAttention.setEnabled(true);
                                    binding.tvAttention.setText("已关注");
                                    binding.tvAttention.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_999999));
                                    binding.tvAttention.setBackground(dynamicDetailActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
                                } else if (isFollow.equals(StatusVariable.UNFOLLOW)) {
                                    binding.tvAttention.setEnabled(true);
                                    binding.tvAttention.setText("关注");
                                    binding.tvAttention.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_FF8F00));
                                    binding.tvAttention.setBackground(dynamicDetailActivity.getResources().getDrawable(R.drawable.attentionbg_no));
                                }
                            }
                            //图片
                            List<DynamicDetailBean.ResultBean.DataBean.PicsBean> pics = dataBean.getPics();
                            if (pics.size() > 0) {
                                if (pics.size() == 1) {
                                    binding.imgRecyclerView.setLayoutManager(new GridLayoutManager(dynamicDetailActivity, 1));
                                } else {
                                    binding.imgRecyclerView.setLayoutManager(new GridLayoutManager(dynamicDetailActivity, 3));
                                }
                                binding.imgRecyclerView.setAdapter(dynamicImageAdapter);
                                dynamicImageAdapter.loadData(pics);
                            } else {
                                binding.imgRecyclerView.setVisibility(View.GONE);
                            }
                            binding.tvCommand.setText("评论" + dataBean.getCommentsCount());
                            binding.tvFavour.setText("点赞" + dataBean.getLikesCount());
                        }
                    }
                }
            }

            @Override
            public DynamicDetailBean parseNetworkResponse(String jsonResult) {

                DynamicDetailBean dynamicDetailBean = JSON.parseObject(jsonResult, DynamicDetailBean.class);

                return dynamicDetailBean;
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
     * 评论
     *
     * @param
     */
    public void showCommentEdit(final String commentType) {

        PopWindowUtil.commandPop(dynamicDetailActivity, new CommandCallback() {
            @Override
            public void releaseCommand(PopupWindow popupWindow, String s, EditText commentEdittext) {
                popWindow = popupWindow;
                editText = commentEdittext;
                if (EmptyUtil.isEmpty(s)) {
                    toastUtil.centerToast(dynamicDetailActivity.getResources().getString(R.string.command_nulltip));
                } else {
                    if (commentType.equals("dynamicComment")) {
                        comment(dynamicId, s);
                    } else {
                        commentReview(s);
                    }
                }
                Util.softBoard(dynamicDetailActivity);
            }
        });
    }

    /**
     * 对评论进行评论
     *
     * @param replyContent
     */
    public void commentReview(String replyContent) {

        HttpRequestUtil.commentReview(dynamicDetailActivity, token, commentId, replyId, replyContent, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        editText.setText("");
                        refreshCommand();
                        toastUtil.centerToast(dynamicDetailActivity.getResources().getString(R.string.command_success));
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
    public void commentClick() {

        if (EmptyUtil.isEmpty(token)) {
            IntentUtil.startActivity(dynamicDetailActivity, LoginActivity.class);
            return;
        }
        showCommentEdit("dynamicComment");

    }

    /**
     * 评论
     */
    public void comment(String id, String content) {

        HttpRequestUtil.comment(dynamicDetailActivity, token, id, content, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        refreshCommand();
                        editText.setText("");
                        toastUtil.centerToast(dynamicDetailActivity.getResources().getString(R.string.command_success));
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
     * 点赞
     */
    public void pariseClick() {

        HttpRequestUtil.favour(dynamicDetailActivity, token, dynamicId, new HttpRequestCallBack() {
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

    }

    /**
     * 分享
     */
    public void shareClick() {

        if (EmptyUtil.isEmpty(token)) {
            IntentUtil.startActivity(dynamicDetailActivity, LoginActivity.class);
            return;
        }
        if (!EmptyUtil.isEmpty(dataBean)) {

            if (!EmptyUtil.isEmpty(userBean)) {
                nickName = userBean.getNickName();
            } else {
                IntentUtil.startActivity(dynamicDetailActivity, LoginActivity.class);
            }
            PopWindowUtil.share(dynamicDetailActivity, new ShareCallBack() {
                @Override
                public void wechatQuanShare(PopupWindow popupWindow) {
                    shareUtil.shareFriendsQuan(dataBean.getShareURl(), dataBean.getContent(), R.mipmap.icon_share, nickName);
                    HttpLog.shareHttp(dynamicDetailActivity, token, dynamicId, new ShareStatusCallback() {
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
                    HttpLog.shareHttp(dynamicDetailActivity, token, dynamicId, new ShareStatusCallback() {
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
     * 关注
     */
    public void attention() {

        HttpRequestUtil.attention(dynamicDetailActivity, token, dataBean.getRegisterID(), new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {


                        if (resultBean.getResultMsg().equals("关注用户成功")) {
                            binding.tvAttention.setEnabled(true);
                            binding.tvAttention.setText("已关注");
                            binding.tvAttention.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_999999));
                            binding.tvAttention.setBackground(dynamicDetailActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
                        } else {
                            binding.tvAttention.setEnabled(true);
                            binding.tvAttention.setText("关注");
                            binding.tvAttention.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_FF8F00));
                            binding.tvAttention.setBackground(dynamicDetailActivity.getResources().getDrawable(R.drawable.attentionbg_no));
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
     * 删除文章
     */
    public void deleteDynamic() {

        if (EmptyUtil.isEmpty(token)) {
            IntentUtil.startActivity(dynamicDetailActivity, LoginActivity.class);
            return;
        }

        HttpRequestUtil.delete(dynamicDetailActivity, token, dynamicId, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        toastUtil.centerToast(dynamicDetailActivity.getResources().getString(R.string.delete_success));
                        dynamicDetailActivity.setResult(StatusVariable.DELEATECODESUCCESS);
                        dynamicDetailActivity.finish();
                    } else {
                        toastUtil.centerToast(dynamicDetailActivity.getResources().getString(R.string.delete_failed));
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
     * 查看大图
     *
     * @param picsBeansList
     * @param pos
     */
    private void showBigImage(List<DynamicDetailBean.ResultBean.DataBean.PicsBean> picsBeansList, int pos) {
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
        PopWindowUtil.lookBigImg(dynamicDetailActivity, picBeansList, pos);

    }

    /**
     * 初始化点击事件
     */
    private void initClick() {

        binding.llCommand.setOnClickListener(this);
        binding.llFavour.setOnClickListener(this);

        // 关注
        binding.tvAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmptyUtil.isEmpty(token)) {
                    IntentUtil.startActivity(dynamicDetailActivity, LoginActivity.class);
                    return;
                }
                attention();
            }
        });

        binding.imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!EmptyUtils.isEmpty(data)) {
//                    intent = new Intent(dynamicDetailActivity, ReleaseActivity.class);
//                    intent.putExtra("registerId", data.getRegisterID());
//                    dynamicDetailActivity.startActivityForResult(intent, 0);
//                }
            }
        });
        dynamicImageAdapter.setLookImgClick(new DynamicImageAdapter.LookImgClick() {
            @Override
            public void lookimgclick(DynamicDetailBean.ResultBean.DataBean.PicsBean picsBean, int pos) {
                if (!EmptyUtil.isEmpty(dataBean)) {
                    List<DynamicDetailBean.ResultBean.DataBean.PicsBean> pics = dataBean.getPics();
                    if (!EmptyUtil.isEmpty(pics) && pics.size() > 0) {
                        showBigImage(pics, pos);
                    }
                }
            }
        });
        dynamicDetailAdapter.setDynamicClick(new DynamicDetailAdapter.DynamicClick() {
            @Override
            public void dynamicclick(CommentFavourBean.ResultBean.DataBean dataBean, int position, String id) {

                if (EmptyUtil.isEmpty(token)) {
                    IntentUtil.startActivity(dynamicDetailActivity, LoginActivity.class);
                    return;
                }
                if (!registerID.equals(registerCode)) {
                    if (registerCode.equals(registerID) || id.equals(registerCode)) {
                        String socialInfoId = dataBean.getId();
                        commentId = socialInfoId;
                        showCommentEdit("reView");
                    } else {
                        toastUtil.centerToast(dynamicDetailActivity.getResources().getString(R.string.forbid_command));
                    }
                } else {
                    String socialInfoId = dataBean.getId();
                    commentId = socialInfoId;
                    showCommentEdit("reView");
                }

            }
        });
//
        dynamicDetailAdapter.setDynamicReplyInfoClick(new DynamicDetailAdapter.DynamicReplyInfoClick() {
            @Override
            public void dynamicReplyInfoClick(CommentFavourBean.ResultBean.DataBean.ReplyInfosBean dataBean, String s, String id) {

                if (EmptyUtil.isEmpty(token)) {
                    IntentUtil.startActivity(dynamicDetailActivity, LoginActivity.class);
                    return;
                }
                if (!registerID.equals(registerCode)) {
                    if (registerCode.equals(id)) {
                        commentId = s;
                        replyId = dataBean.getCommentId();
                        showCommentEdit("reView");
                    } else {
                        toastUtil.centerToast(dynamicDetailActivity.getResources().getString(R.string.forbid_command));
                    }
                } else {
                    String socialInfoId = dataBean.getCommentId();
                    commentId = socialInfoId;
                    showCommentEdit("reView");
                }
            }
        });

    }

    /**
     * 刷新评论
     */
    public void refreshCommand() {
        dynamicDetailAdapter.mapPos.clear();
        binding.smartRefreshlayout.setNoMoreData(false);
        page = 1;
        timeStemp = "";
        initCommentListData(StatusVariable.REFRESH);
    }

    /**
     * 刷新点赞
     */
    public void refreshFavour() {
        dynamicDetailAdapter.mapPos.clear();
        binding.smartRefreshlayout.setNoMoreData(false);
        page = 1;
        timeStemp = "";
        initFavourListData(StatusVariable.REFRESH);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_command:
                dynamicDetailActivity.tabPos = 0;
                tabType = 0;
                binding.llCommand.setClickable(false);
                binding.llFavour.setClickable(true);
                binding.tvCommand.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tabCommand.setVisibility(View.VISIBLE);
                binding.tvFavour.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_333333));
                binding.tabFavour.setVisibility(View.GONE);
                refreshCommand();
                break;
            case R.id.ll_favour:
                tabType = 1;
                dynamicDetailActivity.tabPos = 1;
                binding.llCommand.setClickable(true);
                binding.llFavour.setClickable(false);
                binding.tvFavour.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tabFavour.setVisibility(View.VISIBLE);
                binding.tvCommand.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_333333));
                binding.tabCommand.setVisibility(View.GONE);
                refreshFavour();
                break;
        }

    }

    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {

    }

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
        initHeaderData();
    }
}
