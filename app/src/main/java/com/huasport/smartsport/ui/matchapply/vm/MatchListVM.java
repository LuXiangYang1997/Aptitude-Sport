package com.huasport.smartsport.ui.matchapply.vm;


import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.ActivityMatchListBinding;
import com.huasport.smartsport.ui.matchapply.adapter.MatchListRecyclerViewAdapter;
import com.huasport.smartsport.ui.matchapply.bean.MatchListBeans;
import com.huasport.smartsport.ui.matchapply.view.MatchListActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/7.
 */

public class MatchListVM extends BaseViewModel implements CounterListener, RefreshLoadMoreListener {

    private MyApplication application = MyApplication.getInstance();
    private MatchListActivity matchListActivity;
    private MatchListRecyclerViewAdapter matchListRecyclerViewAdapter;
    public int page = 1;
    private ActivityMatchListBinding binding;
    private String gameCode;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private String token = "";
    private int totalPage = 0;

    public MatchListVM(MatchListActivity matchListActivity, MatchListRecyclerViewAdapter matchListRecyclerViewAdapter) {
        this.matchListActivity = matchListActivity;
        this.matchListRecyclerViewAdapter = matchListRecyclerViewAdapter;
        binding = matchListActivity.getBinding();
        init();
        initData(StatusVariable.REFRESH);
    }

    /**
     * 初始化
     */
    private void init() {
        //获取Code
        gameCode = matchListActivity.getIntent().getStringExtra("gameCode");
        //初始化Toast
        toastUtil = new ToastUtil(matchListActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(matchListActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //初始化加载刷新
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //获取token
        UserBean userBean = application.getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        //弹出加载框
        loadingDialog.show();
    }

    /**
     * 初始化赛事列表
     * @param type
     */
    private void initData(final int type) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.MATCHLIST);
        params.put("gameCode", gameCode);//分类编号
        params.put("currentPage", page+"");//页数
        params.put("pageSize", "10");//每页的个数
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(matchListActivity, params, new RequestCallBack<MatchListBeans>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<MatchListBeans> response) {
                MatchListBeans matchListBeans = response.body();
                if (!EmptyUtil.isEmpty(matchListBeans)){
                    int resultCode = matchListBeans.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MatchListBeans.ResultBean resultBean = matchListBeans.getResult();
                        if(!EmptyUtil.isEmpty(resultBean)){
                            totalPage = resultBean.getTotalPage();
                            //空
                            if (totalPage > 0){
                                NullStateUtil.setNullState(binding.nulldata,false);
                            }else{
                                NullStateUtil.setNullState(binding.nulldata,true);
                            }
                            List<MatchListBeans.ResultBean.ListBean> matchs = resultBean.getList();
                            if (type == StatusVariable.LOADMORE) {
                                matchListRecyclerViewAdapter.loadMoreData(matchs);
                                binding.smartRefreshlayout.finishLoadMore();
                            } else {
                                matchListRecyclerViewAdapter.loadData(matchs);
                                binding.smartRefreshlayout.finishRefresh();
                            }
                            page++;
                        }
                    }
                }
            }

            @Override
            public MatchListBeans parseNetworkResponse(String jsonResult) {

                MatchListBeans matchListBean = JSON.parseObject(jsonResult, MatchListBeans.class);

                return matchListBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
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


    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if(!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }

    }
    /**
     * 刷新数据
     */
    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        page = 1;
        binding.smartRefreshlayout.setNoMoreData(false);
        initData(type);
    }
    /**
     * 加载数据
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
}
