package com.huasport.smartsport.ui.pcenter.vm;

import android.content.Intent;
import android.util.Log;
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
import com.huasport.smartsport.databinding.ActivityMatchstatuslistBinding;
import com.huasport.smartsport.ui.pcenter.adapter.MyRegistrationAdapter;
import com.huasport.smartsport.ui.pcenter.bean.MyRegistrationBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.view.MatchStatusListActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.util.HashMap;
import java.util.List;


/**
 * Created by 陆向阳 on 2018/7/21.
 */

public class MatchStatusListVM extends BaseViewModel implements CounterListener, RefreshLoadMoreListener {

    private MatchStatusListActivity matchStatusListActivity;
    private ActivityMatchstatuslistBinding binding;

    public static final String ALL = "";
    public static final String UNPAID = "WAIT_PAY";
    public static final String CORVIDAE = "WAIT_COMPLETE";
    public static final String SUCCESS = "SUCCESS";
    private int page = 1;
    private String mOrderType = "";
    private MyRegistrationAdapter myRegistrationAdapter;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private MyApplication application = MyApplication.getInstance();
    private String token;
    private int totalPage = 0;

    public MatchStatusListVM(MatchStatusListActivity matchStatusListActivity, MyRegistrationAdapter myRegistrationAdapter) {
        this.matchStatusListActivity = matchStatusListActivity;
        this.myRegistrationAdapter = myRegistrationAdapter;
        binding = matchStatusListActivity.getBinding();
        init();
        initData();
    }


    private void init() {

        //初始化Toast
        toastUtil = new ToastUtil(matchStatusListActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(matchStatusListActivity, R.style.LoadingDialog);
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
     * 初始化数据
     */
    private void initData() {

        Intent intent = matchStatusListActivity.getIntent();
        String listStatus = intent.getStringExtra("ListStatus");

        if (listStatus.isEmpty()) {//全部
            mOrderType = ALL;
        } else if (listStatus.equals(StatusVariable.WAIT_PAY)) {//待支付
            mOrderType = UNPAID;
        } else if (listStatus.equals(StatusVariable.WAIT_COMPLETE)) {//待完善
            mOrderType = CORVIDAE;
        } else if (listStatus.equals(StatusVariable.ORDERSUCCESS)) {//成功
            mOrderType = SUCCESS;
        }
        loadData(StatusVariable.REFRESH);
    }

    private void loadData(final int loadType) {

        HashMap params = new HashMap<String, String>();
        params.put("orderStatus", mOrderType);//订单状态
        params.put("token", token);//token
        params.put("currentPage", page + "");//当前页数
        params.put("pageSize", "10");//每页大小
        params.put("baseMethod", Method.MYAPPLY);
        params.put("baseUrl", Config.baseUrl);
        Log.e("Params", params.toString());

        OkHttpUtil.getRequest(matchStatusListActivity, params, new RequestCallBack<MyRegistrationBean>() {
            @Override
            public void onSuccess(Response<MyRegistrationBean> response) {
                MyRegistrationBean myRegistrationBean = response.body();
                if (!EmptyUtil.isEmpty(myRegistrationBean)){
                    int resultCode = myRegistrationBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MyRegistrationBean.ResultBean resultBean = myRegistrationBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            totalPage = resultBean.getTotalPage();
                            if (totalPage == 0) {
                                NullStateUtil.setNullState(binding.nulldata,true);
                            return;
                             } else {
                                NullStateUtil.setNullState(binding.nulldata,false);
                            }
                        List<MyRegistrationBean.ResultBean.ListBean> list = myRegistrationBean.getResult().getList();

                        if (loadType == StatusVariable.LOADMORE) {//判断是加载还是加载更多
                            myRegistrationAdapter.loadMoreData(list);
                            binding.smartRefreshlayout.finishLoadMore();
                        } else {
                            myRegistrationAdapter.loadData(list);
                            binding.smartRefreshlayout.finishRefresh();
                        }
                    }
                    page++;
                    }else if (resultCode == StatusVariable.LOADMORE){
                        IntentUtil.startActivity(matchStatusListActivity, LoginActivity.class);
                    }else if (resultCode == StatusVariable.NOBIND){
                        IntentUtil.startActivity(matchStatusListActivity, BindPhoneActivity.class);
                    }

                }
            }

            @Override
            public MyRegistrationBean parseNetworkResponse(String jsonResult) {
                MyRegistrationBean myRegistrationBean = JSON.parseObject(jsonResult, MyRegistrationBean.class);
                return myRegistrationBean;
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

//        OkhttpUtils.getRequest(matchStatusListActivity, params, new BaseHttpCallBack<MyRegistrationBean>(matchStatusListActivity, isLoad) {
//
//            @Override
//            public MyRegistrationBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                MyRegistrationBean myRegistrationBean = JSON.parseObject(jsonResult, MyRegistrationBean.class);
//
//                return myRegistrationBean;
//            }
//
//            @Override
//            public void onSuccess(MyRegistrationBean myRegistrationBean, Call call, Response response) {
//                if (myRegistrationBean != null) {
//                    if (myRegistrationBean.getResultCode() == 204) {//204则是未登录
//                        SharedPreferencesUtils.setParam(matchStatusListActivity, "loginstate", true);
//                        matchStatusListActivity.startActivity2(LoginActivity.class);
//                        return;
//                    }
//
//                    if (myRegistrationBean.getResultCode() == StatusVariable.NOBIND) {
//                        matchStatusListActivity.startActivity2(BindActivity.class);
//                        return;
//                    }
//                    if (myRegistrationBean.getResultCode() == 200) {
//
//                        totalpage.set(myRegistrationBean.getResult().getTotalPage());
//
//                        if (totalpage.get() == 0) {
//                            matchStatusListActivity.noData();
//                            return;
//                        } else {
//                            matchStatusListActivity.haveData();
//                        }
//                        List<MyRegistrationBean.ResultBean.ListBean> list = myRegistrationBean.getResult().getList();
//                        Log.e("lwd", "订单列表:" + list.size() + ",订单类型:" + orderStatus);
//
//                        if (loadType.equals("load")) {//判断是加载还是加载更多
//                            myRegistrationAdapter.loadMoreData(list);
//                            binding.smartRefreshlayout.finishLoadMore();
//                        } else {
//                            myRegistrationAdapter.loadData(list);
//                            binding.smartRefreshlayout.finishRefresh();
//                        }
//                    } else {
//                        matchStatusListActivity.noData();//无数据
//                        myRegistrationAdapter.clearData();//清空数据
//                    }
//                }
//                page++;
//
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
////                myHealthActivity.noData();
//                myRegistrationAdapter.clearData();
//                ToastUtils.toast(matchStatusListActivity, "网络异常，请下拉刷新");
//                binding.smartRefreshlayout.finishLoadMore();
//            }
//
//            @Override
//            public void onError(Call call, Response response, Exception e) {
//                super.onError(call, response, e);
//                // myHealthActivity.noData();
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        initData();
    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if (!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }

    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        page = 1;
        binding.smartRefreshlayout.setNoMoreData(false);
        loadData(type);
    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {

        if (page <= totalPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            loadData(type);
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }
    }
}
