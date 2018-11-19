package com.huasport.smartsport.ui.matchapply.vm;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

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
import com.huasport.smartsport.databinding.ComfirmPaymentLayoutBinding;
import com.huasport.smartsport.ui.matchapply.adapter.PayMentOrderAdapter;
import com.huasport.smartsport.ui.matchapply.bean.GroupEventsBean;
import com.huasport.smartsport.ui.matchapply.bean.RegistrationInfoBean;
import com.huasport.smartsport.ui.matchapply.view.ConfirmPayMentActivity;
import com.huasport.smartsport.ui.matchapply.view.PayMentOrderActivty;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/10.
 */

public class PayMentOrderVM extends BaseViewModel implements CounterListener {

    private PayMentOrderActivty payMentOrderActivty;
    private PayMentOrderAdapter payMentOrderAdapter;
    private List<GroupEventsBean.ResultBean.GroupsBean.EventsBean> eventList;
    private final ComfirmPaymentLayoutBinding binding;
    private String payType = "";//支付类型
    private String orderCode;
    private double amountprice = 0;
    private String token = "";
    private List<RegistrationInfoBean.ResultBean.OrderDetailBean.ApplysBean> applys;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private MyApplication application = MyApplication.getInstance();

    public PayMentOrderVM(PayMentOrderActivty payMentOrderActivty, PayMentOrderAdapter payMentOrderAdapter) {
        this.payMentOrderActivty = payMentOrderActivty;
        this.payMentOrderAdapter = payMentOrderAdapter;
        binding = payMentOrderActivty.getBinding();
        init();
        initOnClick();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {
        Intent intent = payMentOrderActivty.getIntent();
        orderCode = intent.getStringExtra("orderCode");
        eventList = (List<GroupEventsBean.ResultBean.GroupsBean.EventsBean>) intent.getSerializableExtra("eventList");
        //初始化Toast
        toastUtil = new ToastUtil(payMentOrderActivty);
        //初始化加载框
        loadingDialog = new LoadingDialog(payMentOrderActivty, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
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

        HashMap params = new HashMap<String, String>();
        params.put("token", token);//token
        params.put("orderCode", orderCode);//订单状、态
        params.put("baseMethod", Method.SIGININFO);
        params.put("baseUrl", Config.baseUrl);
        Log.e("报名成功params====>>", params.toString());

        OkHttpUtil.getRequest(payMentOrderActivty, params, new RequestCallBack<RegistrationInfoBean>() {
            @Override
            public void onSuccess(Response<RegistrationInfoBean> response) {
                RegistrationInfoBean registrationInfoBean = response.body();
                if (!EmptyUtil.isEmpty(registrationInfoBean)) {
                    int resultCode = registrationInfoBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        RegistrationInfoBean.ResultBean resultBean = registrationInfoBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            applys = resultBean.getOrderDetail().getApplys();
                            payMentOrderAdapter.loadData(applys);
                            if (applys != null) {
                                for (RegistrationInfoBean.ResultBean.OrderDetailBean.ApplysBean applysBean : applys) {
                                    String entryFeeStr = applysBean.getApplyAmountStr();
                                    double v = Double.parseDouble(entryFeeStr);
                                    amountprice += v;
                                }
                                binding.amountPrice.setText("总金额:" + amountprice);
                            }
                        }
                    } else if (resultCode == StatusVariable.NOLOGIN) {
                        IntentUtil.startActivity(payMentOrderActivty, LoginActivity.class);
                    } else if (resultCode == StatusVariable.NOBIND) {
                        IntentUtil.startActivity(payMentOrderActivty, BindPhoneActivity.class);
                    }
                }
            }

            @Override
            public RegistrationInfoBean parseNetworkResponse(String jsonResult) {
                RegistrationInfoBean registrationInfoBean = JSON.parseObject(jsonResult, RegistrationInfoBean.class);
                return registrationInfoBean;
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
     * 初始化点击事件
     */
    private void initOnClick() {
        binding.alipayRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean select) {
                if (select) {
                    binding.wechatRb.setChecked(false);
                    payType = StatusVariable.ALIPAY;
                }
            }
        });
        binding.wechatRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean select) {
                if (select) {
                    binding.alipayRb.setChecked(false);
                    payType = StatusVariable.WECHAT;
                }
            }
        });
        binding.confirmpaymentNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EmptyUtil.isEmpty(payType)) {
                    Intent intent = new Intent(payMentOrderActivty, ConfirmPayMentActivity.class);
                    intent.putExtra("payType", payType);
                    intent.putExtra("orderAmountStr", amountprice);
                    intent.putExtra("orderCode", orderCode);
                    payMentOrderActivty.startActivity(intent);
                } else {
                    toastUtil.showTopSnackBar("请选择一种支付方式");
                }
            }
        });
    }


    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd) {
            if (!EmptyUtil.isEmpty(loadingDialog)) {
                loadingDialog.dismiss();
            }
        }
    }
}
