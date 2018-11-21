package com.huasport.smartsport.ui.pcenter.vm;

import android.databinding.ObservableField;
import android.support.design.widget.TabLayout;

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
import com.huasport.smartsport.databinding.PersonalMyorderLayoutBinding;
import com.huasport.smartsport.ui.pcenter.adapter.OrderCenterListAdapter;
import com.huasport.smartsport.ui.pcenter.bean.OrderCenterListBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyOrderActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ShareUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

public class PersonalMyOrderVm extends BaseViewModel implements CounterListener, RefreshLoadMoreListener {

    private PersonalMyOrderActivity personalMyOrderActivity;
    private final PersonalMyorderLayoutBinding binding;
    private String token;
    private OrderCenterListAdapter orderCenterListAdapter;
    private int page = 1;
    private String orderType = "";
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private int totalPage = 0;

    public PersonalMyOrderVm(PersonalMyOrderActivity personalMyOrderActivity, OrderCenterListAdapter orderCenterListAdapter) {
        this.personalMyOrderActivity = personalMyOrderActivity;
        this.orderCenterListAdapter = orderCenterListAdapter;
        binding = personalMyOrderActivity.getBinding();
        init();
        initView();
        initData(StatusVariable.REFRESH);
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(personalMyOrderActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(personalMyOrderActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //初始化加载刷新
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {

            token = userBean.getToken();

        }
        //弹出加载框
        loadingDialog.show();
    }

    private void initView() {

        binding.myorderTablayout.addTab(binding.myorderTablayout.newTab().setText(StatusVariable.ALLORDER));
        binding.myorderTablayout.addTab(binding.myorderTablayout.newTab().setText(StatusVariable.WAITPAYORDER));
        binding.myorderTablayout.addTab(binding.myorderTablayout.newTab().setText(StatusVariable.ALLREADYPAY));
        binding.myorderTablayout.addTab(binding.myorderTablayout.newTab().setText(StatusVariable.SHIPPEDSTR));
        binding.myorderTablayout.addTab(binding.myorderTablayout.newTab().setText(StatusVariable.OMPLETED));

        //添加选择监听
        binding.myorderTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabText = tab.getText().toString();
                page = 1;
                if (tabText.equals(StatusVariable.ALLORDER)) {//全部
                    orderType = "";
                } else if (tabText.equals(StatusVariable.WAITPAYORDER)) {//待支付
                    orderType = StatusVariable.WAIT_PAY;
                } else if (tabText.equals(StatusVariable.ALLREADYPAY)) {//成功
                    orderType = StatusVariable.ORDERSUCCESS;
                } else if (tabText.equals(StatusVariable.SHIPPEDSTR)) {
                    orderType = StatusVariable.SHIPPEDSTR;
                } else if (tabText.equals(StatusVariable.OMPLETED)) {
                    orderType = StatusVariable.OMPLETED;
                }
                initData(StatusVariable.REFRESH);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    /**
     *初始化数据
     * @param loadType
     */
    private void initData(final int loadType) {
        HashMap params = new HashMap();
        params.put("orderStatus", orderType);
        params.put("pageSize", "10");
        params.put("currentPage", page + "");
        params.put("token", token);
        params.put("baseMethod", Method.GETPERSONALORDERLIST);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(personalMyOrderActivity, params, new RequestCallBack<OrderCenterListBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<OrderCenterListBean> response) {
                OrderCenterListBean orderCenterListBean = response.body();
                if(!EmptyUtil.isEmpty(orderCenterListBean)){
                    int resultCode = orderCenterListBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        OrderCenterListBean.ResultBean resultBean = orderCenterListBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            totalPage = resultBean.getTotalPage();
                            if (totalPage == 0) {
                                NullStateUtil.setNullState(binding.nulldata,true);
                                return;
                            } else {
                                NullStateUtil.setNullState(binding.nulldata,false);
                            }

                            List<OrderCenterListBean.ResultBean.ListBean> list = orderCenterListBean.getResult().getList();
                            if (loadType == StatusVariable.LOADMORE) {//判断是加载还是加载更多
                                orderCenterListAdapter.loadMoreData(list);
                                binding.smartRefreshlayout.finishLoadMore();
                            } else {
                                orderCenterListAdapter.loadData(list);
                                binding.smartRefreshlayout.finishRefresh();
                            }
                        }
                        page++;
                    }
                }
            }

            @Override
            public OrderCenterListBean parseNetworkResponse(String jsonResult) {
                OrderCenterListBean orderCenterListBean = JSON.parseObject(jsonResult, OrderCenterListBean.class);
                return orderCenterListBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
                binding.smartRefreshlayout.finishRefresh();
                binding.smartRefreshlayout.finishLoadMore();
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

    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        page = 1;
        binding.smartRefreshlayout.setNoMoreData(false);
        initData(type);

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
}