package com.huasport.smartsport.ui.discover.vm;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
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
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.DiscoverLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.RecommandAdapter;
import com.huasport.smartsport.ui.discover.adapter.SocialAdapter;
import com.huasport.smartsport.ui.discover.bean.CommandPraiseBean;
import com.huasport.smartsport.ui.discover.bean.PicBean;
import com.huasport.smartsport.ui.discover.bean.RecommandBean;
import com.huasport.smartsport.ui.discover.bean.SocialBean;
import com.huasport.smartsport.ui.discover.view.ArticleActivity;
import com.huasport.smartsport.ui.discover.view.DynamicActivity;
import com.huasport.smartsport.ui.discover.view.LightSocialSearchActivity;
import com.huasport.smartsport.ui.discover.view.RecommandActvity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ShareUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.util.popwindow.ReleaseCallBack;
import com.huasport.smartsport.util.popwindow.ShareCallBack;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiscoverVm extends BaseViewModel implements CounterListener, RefreshLoadMoreListener, View.OnClickListener {

    private MyApplication application = MyApplication.getInstance();
    private List<SocialBean.ResultBean.DataBean> dataList = new ArrayList();
    private List<SocialBean.ResultBean.DataBean> allDataList = new ArrayList();
    private List<SocialBean.ResultBean.DataBean> followDataList = new ArrayList();
    private List<SocialBean.ResultBean.DataBean> dynamicDataList = new ArrayList();
    private List<SocialBean.ResultBean.DataBean> articleDataList = new ArrayList();
    private List<RecommandBean.ResultBean.DataBean> commanddataList = new ArrayList();
    private int totalPage = 0;
    private int page = 1;
    private int allTotalPage = 0;
    private int followTotalPage = 0;
    private int dynamicTotalPage = 0;
    private int articleTotalPage = 0;
    private int allPage = 1;
    private int followPage = 1;
    private int dynamicPage = 1;
    private int articlePage = 1;
    private String allTimeStamp = "";
    private String followTimeStamp = "";
    private String dynamicTimeStamp = "";
    private String articleTimeStamp = "";
    private FragmentActivity activity;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private DiscoverLayoutBinding binding;
    private String token;
    private int recommandTotalPage = 0;
    private String loadtype = "all";
    private String timestamp = "";
    private TextView tv_recommand;
    private RecyclerView recyclerViewRecommand;
    private SocialAdapter socialAdapter;
    private View recommandView;
    private RecommandAdapter recommandAdapter;
    private ShareUtil shareUtil;
    private Intent intent;
    private int tabPos = 0;


    public DiscoverVm(FragmentActivity activity, DiscoverLayoutBinding binding, SocialAdapter socialAdapter, RecommandAdapter recommandAdapter) {
        this.activity = activity;
        this.socialAdapter = socialAdapter;
        this.recommandAdapter = recommandAdapter;
        this.binding = binding;
        init();
        initHeader();
        tabData(0);
        initClick();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(activity);
        //初始化加载框
        loadingDialog = new LoadingDialog(activity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //初始化加载刷新
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //获取token
        UserBean userBean = application.getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {

            token = userBean.getToken();

        }
        //初始化分享
        shareUtil = new ShareUtil(activity);
        //弹出加载框
        loadingDialog.show();
    }

    /**
     * 初始化头View
     */
    private void initHeader() {

        recommandView = LayoutInflater.from(activity).inflate(R.layout.recommend_layout, null);

        tv_recommand = recommandView.findViewById(R.id.tv_recommend);
        recyclerViewRecommand = recommandView.findViewById(R.id.recyclervie_recommand);

        binding.xrecyclerView.addHeaderView(recommandView);

        recyclerViewRecommand.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewRecommand.setAdapter(recommandAdapter);

    }

    /**
     * 初始化推荐数据
     */
    private void initRecommandData() {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.RECOMMANDLIST);
        params.put("baseUrl", Config.baseUrl2);
        params.put("currentPage", "1");
        params.put("pageSize", "5");

        OkHttpUtil.getRequest(activity, params, new RequestCallBack<RecommandBean>() {

            @Override
            public void onSuccess(Response<RecommandBean> response) {
                RecommandBean recommandBean = response.body();
                if (!EmptyUtil.isEmpty(recommandBean)) {
                    int resultCode = recommandBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        RecommandBean.ResultBean resultBean = recommandBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            recommandTotalPage = resultBean.getTotalPage();
                            List<RecommandBean.ResultBean.DataBean> data = resultBean.getData();
                            if (!EmptyUtil.isEmpty(data)) {
                                recommandAdapter.loadData(data);
                                commanddataList.clear();
                                commanddataList.addAll(data);
                            } else {
                                binding.xrecyclerView.removeHeaderView(recommandView);
                            }
                        }
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

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });

    }

    /**
     * 初始化列表数据
     */
    private void initListData(final int loadStatus) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.DYNAMICARTICLELIST);
        params.put("pageSize", "10");
        params.put("currentPage", page + "");
        params.put("baseUrl", Config.baseUrl2);
        params.put("type", loadtype);
        params.put("timestamp", timestamp);

        OkHttpUtil.getRequest(activity, params, new RequestCallBack<SocialBean>() {
            @Override
            public void onSuccess(Response<SocialBean> response) {
                SocialBean socialBean = response.body();
                if (!EmptyUtil.isEmpty(socialBean)) {
                    int resultCode = socialBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        SocialBean.ResultBean resultBean = socialBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            totalPage = resultBean.getTotalPage();
                            timestamp = resultBean.getTimestamp();

                            if (totalPage == 0) {
                                NullStateUtil.setNullState(binding.nulldata, true);
                            } else {
                                NullStateUtil.setNullState(binding.nulldata, false);
                            }

                            List<SocialBean.ResultBean.DataBean> data = resultBean.getData();
                            if (!EmptyUtil.isEmpty(data)) {

                                if (loadStatus == StatusVariable.REFRESH) {
                                    dataList.clear();
                                    dataList.addAll(data);
                                    socialAdapter.loadData(data);
                                    binding.smartRefreshlayout.finishRefresh();
                                } else {
                                    dataList.addAll(data);
                                    socialAdapter.loadMoreData(data);
                                    binding.smartRefreshlayout.finishLoadMore();
                                }

                                if (loadtype.equals("all")) {
                                    allPage = page;
                                    allTimeStamp = timestamp;
                                    allTotalPage = totalPage;
                                    allDataList.addAll(dataList);
                                } else if (loadtype.equals("follow")) {
                                    followPage = page;
                                    followTimeStamp = timestamp;
                                    followTotalPage = totalPage;
                                    followDataList.addAll(dataList);
                                } else if (loadtype.equals("dynamic")) {
                                    dynamicPage = page;
                                    dynamicTimeStamp = timestamp;
                                    dynamicTotalPage = totalPage;
                                    dynamicDataList.addAll(dataList);
                                } else if (loadtype.equals("article")) {
                                    articlePage = page;
                                    articleTimeStamp = timestamp;
                                    articleTotalPage = totalPage;
                                    articleDataList.addAll(dataList);
                                }
                            }

                        }
                        page++;
                    } else {
                        if (loadtype.equals("follow")) {
                            if (socialBean.getResultMsg().equals("用户未登录")) {
                                socialAdapter.mList.clear();
                                socialAdapter.notifyDataSetChanged();
                                IntentUtil.startActivity(activity, LoginActivity.class);
                            }
                        }
                    }
                }
            }

            @Override
            public SocialBean parseNetworkResponse(String jsonResult) {

                SocialBean socialBean = JSON.parseObject(jsonResult, SocialBean.class);

                return socialBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.centerToast(msg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
            }
        });
    }

    /**
     * 点赞
     */
    private void dianLike(String id, final int position) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("socialInfoId", id);
        params.put("baseMethod", Method.PRAISE);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.postRequest(activity, params, new RequestCallBack<CommandPraiseBean>() {
            @Override
            public void onSuccess(Response<CommandPraiseBean> response) {
                CommandPraiseBean commandPraiseBean = response.body();
                if (!EmptyUtil.isEmpty(commandPraiseBean)) {
                    int resultCode = commandPraiseBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        String resultMsg = commandPraiseBean.getResultMsg();
                        if (resultMsg.equals(activity.getResources().getString(R.string.success_zan))) {

                            if (!EmptyUtil.isEmpty(dataList.get(position))) {
                                dataList.get(position).setIsLike("1");
                                dataList.get(position).setLikesCount(dataList.get(position).getLikesCount() + 1);
                                socialAdapter.loadData(dataList);
                            }

                        } else if (resultMsg.equals(activity.getResources().getString(R.string.cancel_zan))) {

                            if (!EmptyUtil.isEmpty(dataList.get(position))) {
                                dataList.get(position).setIsLike("0");
                                if (dataList.get(position).getLikesCount() != 0) {
                                    dataList.get(position).setLikesCount(dataList.get(position).getLikesCount() - 1);
                                }
                                socialAdapter.loadData(dataList);
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
    }

    /**
     * 分享
     *
     * @param dataBean
     */
    private void share(final SocialBean.ResultBean.DataBean dataBean, final int position) {

        final String nickName = application.getUserBean().getNickName();

        PopWindowUtil.share(activity, new ShareCallBack() {
            @Override
            public void wechatQuanShare(PopupWindow popupWindow) {

                shareUtil.shareFriendsQuan(dataBean.getShareURl(), "", R.mipmap.icon_share, nickName);
                HttpLog.shareHttp(activity, token, dataBean.getId(), new ShareStatusCallback() {
                    @Override
                    public void shareSuccess() {
                        dataList.get(position).setShareCount(dataList.get(position).getShareCount() + 1);
                        socialAdapter.loadData(dataList);
                    }

                    @Override
                    public void shareFailed() {

                    }
                });
            }

            @Override
            public void weChatFriendsShare(PopupWindow popupWindow) {

                shareUtil.shareFriends(dataBean.getShareURl(), dataBean.getContent(), R.mipmap.icon_share, nickName);
                HttpLog.shareHttp(activity, token, dataBean.getId(), new ShareStatusCallback() {
                    @Override
                    public void shareSuccess() {
                        dataList.get(position).setShareCount(dataList.get(position).getShareCount() + 1);
                        socialAdapter.loadData(dataList);
                    }

                    @Override
                    public void shareFailed() {

                    }
                });
            }
        });

    }

    /**
     * 发布点击事件
     */
    public void release() {

        PopWindowUtil.releaseClick(activity, binding.tvDiscoverRelease, new ReleaseCallBack() {
            @Override
            public void dynamic(PopupWindow popupWindow) {
                IntentUtil.startActivityForResult(activity, DynamicActivity.class);
                popupWindow.dismiss();
            }

            @Override
            public void article(PopupWindow popupWindow) {
                IntentUtil.startActivityForResult(activity, ArticleActivity.class);
                popupWindow.dismiss();
            }
        });

    }

    /**
     * 初始化点击事件
     */
    private void initClick() {

        //顶部tab切换
        binding.llAll.setOnClickListener(this);
        binding.llFollow.setOnClickListener(this);
        binding.llDynamic.setOnClickListener(this);
        binding.llArticle.setOnClickListener(this);

        //查看更多推荐
        tv_recommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(activity, RecommandActvity.class);
            }
        });

        socialAdapter.setCommentClick(new SocialAdapter.CommentClick() {
            @Override
            public void commentclick(int type, SocialBean.ResultBean.DataBean dataBean, int position) {
                boolean login = isLogin();
                if (login) {
                    if (type == StatusVariable.SHARE) {
                        share(dataBean, position);
                    } else if (type == StatusVariable.FAVOUR) {
                        dianLike(dataBean.getId(), position);
                    }
                } else {
                    IntentUtil.startActivity(activity, LoginActivity.class);
                }
            }
        });

        //查看大图
        socialAdapter.setLookImgClick(new SocialAdapter.LookImgClick() {
            @Override
            public void lookimgClick(List<SocialBean.ResultBean.DataBean.PicsBean> picsBeansList, int position) {

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
                PopWindowUtil.lookBigImg(activity, picBeansList, position);


            }
        });


    }

    /**
     * 搜索
     */
    public void search() {

        IntentUtil.startActivity(activity, LightSocialSearchActivity.class);

    }

    /**
     * 顶部Tab切换
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_all:
                binding.tvAll.setTextColor(activity.getResources().getColor(R.color.color_FF8F00));
                binding.tabAll.setVisibility(View.VISIBLE);
                binding.tvFollow.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabFollow.setVisibility(View.GONE);
                binding.tvDynamic.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabDynamic.setVisibility(View.GONE);
                binding.tvArticle.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabArticle.setVisibility(View.GONE);
                tabData(0);
                break;
            case R.id.ll_follow:
                UserBean userBean = MyApplication.getInstance().getUserBean();
                if (EmptyUtil.isEmpty(userBean)) {
                    IntentUtil.startActivity(activity, LoginActivity.class);
                    return;
                } else {
                    String token = userBean.getToken();
                    if (EmptyUtil.isEmpty(token)) {
                        IntentUtil.startActivity(activity, LoginActivity.class);
                        return;
                    }
                }
                binding.tvAll.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabAll.setVisibility(View.GONE);
                binding.tvFollow.setTextColor(activity.getResources().getColor(R.color.color_FF8F00));
                binding.tabFollow.setVisibility(View.VISIBLE);
                binding.tvDynamic.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabDynamic.setVisibility(View.GONE);
                binding.tvArticle.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabArticle.setVisibility(View.GONE);
                tabData(1);
                break;
            case R.id.ll_dynamic:
                binding.tvAll.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabAll.setVisibility(View.GONE);
                binding.tvFollow.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabFollow.setVisibility(View.GONE);
                binding.tvDynamic.setTextColor(activity.getResources().getColor(R.color.color_FF8F00));
                binding.tabDynamic.setVisibility(View.VISIBLE);
                binding.tvArticle.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabArticle.setVisibility(View.GONE);
                tabData(2);
                break;
            case R.id.ll_article:
                binding.tvAll.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabAll.setVisibility(View.GONE);
                binding.tvFollow.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabFollow.setVisibility(View.GONE);
                binding.tvDynamic.setTextColor(activity.getResources().getColor(R.color.color_333333));
                binding.tabDynamic.setVisibility(View.GONE);
                binding.tvArticle.setTextColor(activity.getResources().getColor(R.color.color_FF8F00));
                binding.tabArticle.setVisibility(View.VISIBLE);
                tabData(3);
                break;

        }
    }

    /**
     * 处理数据
     *
     * @param position
     */
    public void tabData(int position) {

        tabPos = position;

        if (position == 0) {
            loadtype = "all";
            if (commanddataList.size() > 0) {
                if (binding.xrecyclerView.getHeadersCount() <= 0) {
                    binding.xrecyclerView.addHeaderView(recommandView);
                }
                recommandAdapter.loadData(commanddataList);
            } else {
                page = 1;
                initRecommandData();
            }
            if (allDataList.size() > 0) {
                page = allPage;
                timestamp = allTimeStamp;
                totalPage = allTotalPage;
                socialAdapter.loadData(allDataList);
            } else {
                page = 1;
                timestamp = "";
                totalPage = 0;
                loadingDialog.show();
                counter.setCount(1);
                initListData(StatusVariable.REFRESH);
            }
        } else if (position == 1) {
            binding.xrecyclerView.removeHeaderView(recommandView);
            loadtype = "follow";
            if (followDataList.size() > 0) {
                page = followPage;
                timestamp = followTimeStamp;
                totalPage = followTotalPage;
                socialAdapter.loadData(followDataList);
            } else {
                page = 1;
                timestamp = "";
                totalPage = 0;
                loadingDialog.show();
                counter.setCount(1);
                initListData(StatusVariable.REFRESH);
            }
        } else if (position == 2) {
            binding.xrecyclerView.removeHeaderView(recommandView);
            loadtype = "dynamic";
            if (dynamicDataList.size() > 0) {
                page = dynamicPage;
                timestamp = dynamicTimeStamp;
                totalPage = dynamicTotalPage;
                socialAdapter.loadData(dynamicDataList);
            } else {
                page = 1;
                timestamp = "";
                totalPage = 0;
                loadingDialog.show();
                counter.setCount(1);
                initListData(StatusVariable.REFRESH);
            }
        } else if (position == 3) {
            binding.xrecyclerView.removeHeaderView(recommandView);
            loadtype = "article";
            if (articleDataList.size() > 0) {
                page = articlePage;
                timestamp = articleTimeStamp;
                totalPage = articleTotalPage;
                socialAdapter.loadData(articleDataList);
            } else {
                page = 1;
                timestamp = "";
                totalPage = 0;
                loadingDialog.show();
                counter.setCount(1);
                initListData(StatusVariable.REFRESH);
            }
        }
    }

    /**
     * 刷新推荐卡片数据
     */
    public void refreCommand() {

        if (binding.xrecyclerView.getHeadersCount() == 0) {
            binding.xrecyclerView.addHeaderView(recommandView);
        }
        initRecommandData();
    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd) {
            if (!EmptyUtil.isEmpty(loadingDialog)) {
                loadingDialog.dismiss();
            }
        }
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
        timestamp = "";
        if (loadtype.equals("all")) {
            refreCommand();
        }
        initListData(type);
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
            initListData(type);
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }

    }

    /**
     * 是否登录
     *
     * @return
     */
    private boolean isLogin() {

        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {

            String token = userBean.getToken();
            if (!EmptyUtil.isEmpty(token)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 刷新数据，并切换到全部
     */
    private void refreTab() {
        page = 1;
        allPage = 1;
        dynamicPage = 1;
        articlePage = 1;
        followPage = 1;
        allTimeStamp = "";
        dynamicTimeStamp = "";
        articleTimeStamp = "";
        followTimeStamp = "";
        allDataList.clear();
        dynamicDataList.clear();
        articleDataList.clear();
        followDataList.clear();
        dataList.clear();
        binding.tvAll.setTextColor(activity.getResources().getColor(R.color.color_FF8F00));
        binding.tabAll.setVisibility(View.VISIBLE);
        binding.tvFollow.setTextColor(activity.getResources().getColor(R.color.color_333333));
        binding.tabFollow.setVisibility(View.GONE);
        binding.tvDynamic.setTextColor(activity.getResources().getColor(R.color.color_333333));
        binding.tabDynamic.setVisibility(View.GONE);
        binding.tvArticle.setTextColor(activity.getResources().getColor(R.color.color_333333));
        binding.tabArticle.setVisibility(View.GONE);
        tabData(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == StatusVariable.RELEASECODESUCCESS) {
            refreTab();
        } else if (resultCode == StatusVariable.DELEATECODESUCCESS) {
            deleateData();
        }

    }

    /**
     * 删除后刷新
     */
    private void deleateData() {
        page = 1;
        allPage = 1;
        dynamicPage = 1;
        articlePage = 1;
        followPage = 1;
        allTimeStamp = "";
        dynamicTimeStamp = "";
        articleTimeStamp = "";
        followTimeStamp = "";
        allDataList.clear();
        dynamicDataList.clear();
        articleDataList.clear();
        followDataList.clear();
        dataList.clear();
        tabData(tabPos);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
    }
}