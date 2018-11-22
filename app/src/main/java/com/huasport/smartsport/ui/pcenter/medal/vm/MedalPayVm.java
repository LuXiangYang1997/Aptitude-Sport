package com.huasport.smartsport.ui.pcenter.medal.vm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableField;
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
import com.huasport.smartsport.databinding.MedalPayLayoutBinding;
import com.huasport.smartsport.rxbus.RxBus;
import com.huasport.smartsport.ui.pcenter.medal.bean.MedalAliPayBean;
import com.huasport.smartsport.ui.pcenter.medal.bean.MedalOrderDetailBean;
import com.huasport.smartsport.ui.pcenter.medal.bean.MedalWechatPayBean;
import com.huasport.smartsport.ui.pcenter.medal.bean.PayStatusBean;
import com.huasport.smartsport.ui.pcenter.medal.view.ImmediatelyPayActivity;
import com.huasport.smartsport.ui.pcenter.medal.view.MedalPaySuccessActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.wxapi.ThirdPart;
import com.huasport.smartsport.wxapi.callback.AlipayCallBack;

import java.util.HashMap;

import rx.Subscription;
import rx.functions.Action1;

public class MedalPayVm extends BaseViewModel implements CounterListener {

    private ImmediatelyPayActivity immediatelyPayActivity;
    private String token;
    private MedalPayLayoutBinding medalPayLayoutBinding;
    private String payType = "";//支付类型
    private String orderCode;
    public ObservableField<String> payOrderCode = new ObservableField<>("");
    private RxBus mRxBus;
    private Subscription subscribe;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;

    public MedalPayVm(ImmediatelyPayActivity immediatelyPayActivity) {
        this.immediatelyPayActivity = immediatelyPayActivity;
        medalPayLayoutBinding = immediatelyPayActivity.getBinding();
        init();
        initIntent();
        initData();
        initView();
    }

    /**
     * 初始化
     */
    private void init() {

        //初始化Toast
        toastUtil = new ToastUtil(immediatelyPayActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(immediatelyPayActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        //弹出加载框
        loadingDialog.show();
    }

    private void initIntent() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("WXPaySuccess");
        MedalBroadCast medalBroadCast = new MedalBroadCast();
        immediatelyPayActivity.registerReceiver(medalBroadCast, intentFilter);

    }

    private void initView() {
        //支付宝
        medalPayLayoutBinding.alipayRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean select) {
                if (select) {
                    medalPayLayoutBinding.wechatRb.setChecked(false);
                    payType = StatusVariable.ALIPAY;
                }
            }
        });
        //微信
        medalPayLayoutBinding.wechatRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean select) {
                if (select) {
                    medalPayLayoutBinding.alipayRb.setChecked(false);
                    payType = StatusVariable.WECHAT;
                }
            }
        });

        medalPayLayoutBinding.tvMedalPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EmptyUtil.isEmpty(payType)) {
                    counter.setCount(1);
                    loadingDialog.show();
                    if (payType == StatusVariable.WECHAT) {
                        wechat();
                    } else {
                        alipay();
                    }
                } else {
                    toastUtil.centerToast("请选择一种支付方式");
                }
            }
        });

    }

    //支付宝支付
    private void alipay() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.MEDALEALIPAY);
        params.put("orderCode", payOrderCode.get());
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(immediatelyPayActivity, params, new RequestCallBack<MedalAliPayBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<MedalAliPayBean> response) {
                MedalAliPayBean medalAliPayBean = response.body();
                if (!EmptyUtil.isEmpty(medalAliPayBean)){
                    int resultCode = medalAliPayBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        ThirdPart thirdPart = new ThirdPart(immediatelyPayActivity);
                        thirdPart.aliPay(medalAliPayBean.getResult().getSign(), alipayCallBack);
                    }
                }
            }

            @Override
            public MedalAliPayBean parseNetworkResponse(String jsonResult) {
                MedalAliPayBean medalAliPayBean = JSON.parseObject(jsonResult, MedalAliPayBean.class);
                return medalAliPayBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
            }
        });
    }

    /**
     * 立即支付
     */
    private void wechat() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.MEDALEWACHAT);
        params.put("orderCode", payOrderCode.get());
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(immediatelyPayActivity, params, new RequestCallBack<MedalWechatPayBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<MedalWechatPayBean> response) {
                MedalWechatPayBean medalWechatPayBean = response.body();
                if (!EmptyUtil.isEmpty(medalWechatPayBean)){
                    int resultCode = medalWechatPayBean.getResultCode();
                    if(resultCode == StatusVariable.REQUESTSUCCESS){
                        ThirdPart thirdPart = new ThirdPart(immediatelyPayActivity);
                        thirdPart.wxPay(medalWechatPayBean.getResult().getAppId(), medalWechatPayBean.getResult().getPartnerId(), medalWechatPayBean.getResult().getPrepayId(), medalWechatPayBean.getResult().getNonceStr(), medalWechatPayBean.getResult().getTimeStamp(), medalWechatPayBean.getResult().getSign());
                    }
                }
            }

            @Override
            public MedalWechatPayBean parseNetworkResponse(String jsonResult) {
                MedalWechatPayBean medalWechatPayBean = JSON.parseObject(jsonResult, MedalWechatPayBean.class);
                return medalWechatPayBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
            }
        });
    }


    //支付回调
    AlipayCallBack alipayCallBack = new AlipayCallBack() {
        @Override
        public void paySuccess(String resultStatus) {
            IntentUtil.startActivity(immediatelyPayActivity,MedalPaySuccessActivity.class);
        }

        @Override
        public void payFailed(String resultStatus) {
        }
    };


    private void initData() {
        orderCode = immediatelyPayActivity.getIntent().getStringExtra("orderCode");

        HashMap params = new HashMap();
        params.put("baseMethod", Method.MEDALORDERDETAIL + "/" + orderCode);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(immediatelyPayActivity, params, new RequestCallBack<MedalOrderDetailBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<MedalOrderDetailBean> response) {
                MedalOrderDetailBean medalOrderDetailBean = response.body();
                if (!EmptyUtil.isEmpty(medalOrderDetailBean)){
                    int resultCode = medalOrderDetailBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MedalOrderDetailBean.ResultBean resultBean = medalOrderDetailBean.getResult();
                        if(!EmptyUtil.isEmpty(resultBean)){
                            payOrderCode.set(resultBean.getOrder().getPayOrderCode());
                            medalPayLayoutBinding.commodityNum.setText(resultBean.getOrder().getGoodsCode() + "");
                            medalPayLayoutBinding.commodityName.setText(resultBean.getOrder().getGoodsTitle() + "");
                            medalPayLayoutBinding.orderAddressee.setText(resultBean.getOrder().getRealname() + "");
                            medalPayLayoutBinding.ContactInformation.setText(resultBean.getOrder().getMobile() + "");
                            medalPayLayoutBinding.ReceivingAddress.setText(resultBean.getOrder().getProvince() + "-" +
                                    resultBean.getOrder().getCity() + "-" +resultBean.getOrder().getArea() + "-" +
                                    resultBean.getOrder().getAddress()
                            );
                            medalPayLayoutBinding.tvOrderNum.setText(resultBean.getOrder().getOrderCode() + "");
                            medalPayLayoutBinding.orderDate.setText(resultBean.getOrder().getOrderTime() + "");
                            int orderPrice = resultBean.getOrder().getOrderPrice();
                            Double price = (double) orderPrice / 100;
                            String priceDecimal = Util.decimal(price);
                            medalPayLayoutBinding.orderPrice.setText("￥" + priceDecimal + "");
                        }
                    }
                }
            }

            @Override
            public MedalOrderDetailBean parseNetworkResponse(String jsonResult) {
                MedalOrderDetailBean medalOrderDetailBean = JSON.parseObject(jsonResult, MedalOrderDetailBean.class);
                return medalOrderDetailBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
            }
        });

    }

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        subscribe = RxBus.getInstance().toObserverable(PayStatusBean.class).subscribe(new Action1<PayStatusBean>() {
            @Override
            public void call(PayStatusBean payStatusBean) {
                if (payStatusBean.getPayStatus().equals(StatusVariable.PAYSUCCESS)) {
                    IntentUtil.startActivity(immediatelyPayActivity,MedalPaySuccessActivity.class);
                }
            }
        });
    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if (!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }
    public class MedalBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String paystatus = intent.getStringExtra(StatusVariable.PAYSUCCESS);
            if (paystatus.equals("WXPaySuccess")) {
                IntentUtil.startActivity(immediatelyPayActivity,MedalPaySuccessActivity.class);
            }
        }
    }
}
