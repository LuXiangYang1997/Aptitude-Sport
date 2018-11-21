package com.huasport.smartsport.ui.pcenter.medal.vm;

import android.content.Intent;

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
import com.huasport.smartsport.databinding.SelectAddressLayoutBinding;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.medal.adapter.AddressAdapter;
import com.huasport.smartsport.ui.pcenter.medal.bean.AddressBean;
import com.huasport.smartsport.ui.pcenter.medal.view.AddAddressActivity;
import com.huasport.smartsport.ui.pcenter.medal.view.AddNewAddressActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.HashMap;
import java.util.List;

public class SelectAddressVM extends BaseViewModel implements CounterListener, RefreshLoadMoreListener {

    private AddAddressActivity addAddressActivity;
    private String token;
    private int page = 1;
    private AddressAdapter addressAdapter;
    private SelectAddressLayoutBinding binding;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private int totalPage = 0;


    public SelectAddressVM(AddAddressActivity addAddressActivity, AddressAdapter addressAdapter) {
        this.addAddressActivity = addAddressActivity;
        this.addressAdapter = addressAdapter;
        binding = addAddressActivity.getBinding();
        init();
        initdata(StatusVariable.REFRESH);
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(addAddressActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(addAddressActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        new RefreshLoadMore(binding.smartRefreshlayout,this);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        //弹出加载框
        loadingDialog.show();
    }

    private void initdata(final int loadType) {

        HashMap params = new HashMap();
        params.put("currentPage", page + "");
        params.put("pageSize", "10");
        params.put("token", token);
        params.put("method", Method.ADDRESSLIST);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(addAddressActivity, params, new RequestCallBack<AddressBean>() {

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<AddressBean> response) {
                AddressBean addressBean = response.body();
                if (!EmptyUtil.isEmpty(addressBean)) {
                    int resultCode = addressBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        AddressBean.ResultBean resultBean = addressBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            totalPage = resultBean.getTotalPage();
                            List<AddressBean.ResultBean.ListBean> list = resultBean.getList();
                            if (loadType == StatusVariable.REFRESH) {
                                binding.smartRefreshlayout.finishLoadMore();
                                addressAdapter.loadMoreData(list);
                            } else {
                                addressAdapter.loadData(list);
                                binding.smartRefreshlayout.finishRefresh();
                            }
                        }
                        page++;
                    } else if (resultCode == StatusVariable.NOBIND) {
                        IntentUtil.startActivity(addAddressActivity, BindPhoneActivity.class);
                    } else if (resultCode == StatusVariable.NOLOGIN) {
                        IntentUtil.startActivity(addAddressActivity, LoginActivity.class);
                    }
                }
            }
            @Override
            public AddressBean parseNetworkResponse(String jsonResult) {
                AddressBean addressBean = JSON.parseObject(jsonResult, AddressBean.class);
                return addressBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    //添加收货地址
    public void addAddress() {

        Intent intent = new Intent(addAddressActivity, AddNewAddressActivity.class);
        intent.putExtra("addressType", "addAddress");//添加地址
        intent.putExtra("AddressBean", "");
        addAddressActivity.startActivity(intent);

    }

    //刷新数据
    public void refresh() {

    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        initdata(StatusVariable.REFRESH);

    }

    @Override
    public void countEnd(boolean isEnd) {
        if(isEnd){
            if (!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }

    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        page = 1;
        binding.smartRefreshlayout.setNoMoreData(false);
        initdata(type);
    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {
        if (page <= totalPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            initdata(type);
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }
    }
}
