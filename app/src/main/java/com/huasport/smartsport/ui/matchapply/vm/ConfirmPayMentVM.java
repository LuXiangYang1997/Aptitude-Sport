package com.huasport.smartsport.ui.matchapply.vm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableField;
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
import com.huasport.smartsport.databinding.ConfirmpaymentLayoutBinding;
import com.huasport.smartsport.rxbus.RxBus;
import com.huasport.smartsport.ui.matchapply.adapter.ConfirmPayMentAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.OrderMessageAdapter;
import com.huasport.smartsport.ui.matchapply.bean.OrderMsgBean;
import com.huasport.smartsport.ui.matchapply.bean.PayResultBean;
import com.huasport.smartsport.ui.matchapply.bean.RegistrationInfoBean;
import com.huasport.smartsport.ui.matchapply.bean.WeChatBean;
import com.huasport.smartsport.ui.matchapply.view.ConfirmPayMentActivity;
import com.huasport.smartsport.ui.matchapply.view.GroupApplySuccessActivity;
import com.huasport.smartsport.ui.matchapply.view.SuccessPaymentInfoActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.wxapi.ThirdPart;
import com.huasport.smartsport.wxapi.callback.AlipayCallBack;

import java.util.HashMap;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by 陆向阳 on 2018/6/11.
 */

public class ConfirmPayMentVM extends BaseViewModel implements CounterListener {

    private ConfirmPayMentActivity confirmPayMentActivity;
    private ConfirmPayMentAdapter confirmPayMentAdapter;
    private OrderMessageAdapter orderMessageAdapter;
    private final ConfirmpaymentLayoutBinding binding;
    private PayResultBean alipayMsgBean;
    private String payType;
    private WeChatBean weChatBean;
    private String orderCode;
    private RxBus mRxBus;
    private boolean isStopTime = false;
    private List<RegistrationInfoBean.ResultBean.OrderDetailBean.ApplysBean> applys;
    private String token = "";
    private double amountprice = 0d;
    private String matchType = "";
    private Intent intent;
    private OrderMsgBean orderMsgBean;
    public ObservableField<Boolean> success = new ObservableField<>(false);
    private Subscription subscribe;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private MyApplication application = MyApplication.getInstance();
    private ThirdPart thirdPart;

    public ConfirmPayMentVM(ConfirmPayMentActivity confirmPayMentActivity, ConfirmPayMentAdapter confirmPayMentAdapter, OrderMessageAdapter orderMessageAdapter) {
        this.confirmPayMentActivity = confirmPayMentActivity;
        this.confirmPayMentAdapter = confirmPayMentAdapter;
        this.orderMessageAdapter = orderMessageAdapter;
        binding = confirmPayMentActivity.getBinding();
        init();
        initIntent();
        countDownTime();
        initData();
        orderMsg();
        orderSum();
    }

    /**
     * 初始化
     */
    private void init() {
        matchType = (String) SharedPreferencesUtil.getParam(confirmPayMentActivity, "matchType", "");
        //初始化Toast
        toastUtil = new ToastUtil(confirmPayMentActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(confirmPayMentActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        thirdPart = new ThirdPart(confirmPayMentActivity);
        //弹出加载框
        loadingDialog.show();
    }

    private void initIntent() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("WXPaySuccess");
        MyBrocastReceiver myBrocastReceiver = new MyBrocastReceiver();
        confirmPayMentActivity.registerReceiver(myBrocastReceiver, intentFilter);

    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd) {
            if (!EmptyUtil.isEmpty(loadingDialog)) {
                loadingDialog.dismiss();
            }

        }
    }

    public class MyBrocastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String paystatus = intent.getStringExtra(StatusVariable.PAYSUCCESS);
            if (paystatus.equals("WXPaySuccess")) {
                if (matchType.equals("group")) {
                    intent = new Intent(confirmPayMentActivity, GroupApplySuccessActivity.class);
                    intent.putExtra("orderCode", orderCode);
                    intent.putExtra("orderType", StatusVariable.SUCCESSAPPLY);
                } else {
                    intent = new Intent(confirmPayMentActivity, SuccessPaymentInfoActivity.class);
                    intent.putExtra("orderCode", orderCode);
                    intent.putExtra("orderStatus", StatusVariable.SUCCESSAPPLY);
                    intent.putExtra("orderType", StatusVariable.SUCCESSAPPLY);
                }
                if (!success.get()) {
                    success.set(true);
                    confirmPayMentActivity.startActivity(intent);
                    confirmPayMentActivity.finish();
                }
            }
        }
    }


    private void orderSum() {

        if (applys != null) {

            for (OrderMsgBean.ResultBean.PayinfoBean.ApplysBean applysBean : orderMsgBean.getResult().getPayinfo().getApplys()) {
                String entryFeeStr = applysBean.getApplyAmountStr();
                double v = Double.parseDouble(entryFeeStr);
                amountprice += v;
            }
            binding.orderPrice.setText(amountprice + "");
        }

    }


    private void initData() {

        Intent intent = confirmPayMentActivity.getIntent();
        orderCode = intent.getStringExtra("orderCode");//订单号
        String payType = intent.getStringExtra("payType");

        if (!EmptyUtil.isEmpty(payType)) {
            if (payType.equals(StatusVariable.WECHAT)) {
                binding.payImg.setImageResource(R.mipmap.icon_paywechat);
                binding.payText.setText("微信支付");
            } else if (payType.equals(StatusVariable.ALIPAY)) {
                binding.payImg.setImageResource(R.mipmap.icon_alipay);
                binding.payText.setText("支付宝支付");
            }
        }

        this.payType = intent.getStringExtra("payType");
        if (this.payType.equals(StatusVariable.WECHAT)) {
            weChatBean = (WeChatBean) intent.getSerializableExtra("WeChatMsg");
        } else if (this.payType.equals(StatusVariable.ALIPAY)) {
            alipayMsgBean = (PayResultBean) intent.getSerializableExtra("alipayMsg");
        }
    }

    //获取订单信息
    private void orderMsg() {

        HashMap params = new HashMap();
        params.put("orderCode", orderCode);
        params.put("token", token);
        params.put("baseMethod", Method.ORDERMSG);
        params.put("t", String.valueOf(System.currentTimeMillis()));
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(confirmPayMentActivity, params, new RequestCallBack<OrderMsgBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<OrderMsgBean> response) {
                OrderMsgBean orderMsgBean = response.body();
                if (!EmptyUtil.isEmpty(orderMsgBean)) {
                    int resultCode = orderMsgBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        OrderMsgBean.ResultBean resultBean = orderMsgBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            OrderMsgBean.ResultBean.PayinfoBean payinfo = resultBean.getPayinfo();
                            if (!EmptyUtil.isEmpty(payinfo)) {
                                List<OrderMsgBean.ResultBean.PayinfoBean.ApplysBean> applys = payinfo.getApplys();
                                for (int i = 0; i < applys.size(); i++) {
                                    applys.get(i).setOrderAmountStr(orderMsgBean.getResult().getPayinfo().getOrderAmountStr());
                                }
                                confirmPayMentAdapter.loadData(orderMsgBean.getResult().getPayinfo().getApplys());
                                binding.orderNum.setText(orderMsgBean.getResult().getPayinfo().getOrderCode());
                                binding.orderDate.setText(orderMsgBean.getResult().getPayinfo().getOrderTime());
                                binding.orderPrice.setText(orderMsgBean.getResult().getPayinfo().getOrderAmountStr());
                                binding.applyFee.setText(orderMsgBean.getResult().getPayinfo().getRemark());

                            }
                        }
                    } else if (resultCode == StatusVariable.NOBIND) {
                        IntentUtil.startActivity(confirmPayMentActivity, BindPhoneActivity.class);
                    } else if (resultCode == StatusVariable.NOLOGIN) {
                        IntentUtil.startActivity(confirmPayMentActivity, LoginActivity.class);
                    }
                }
            }

            @Override
            public OrderMsgBean parseNetworkResponse(String jsonResult) {
                orderMsgBean = JSON.parseObject(jsonResult, OrderMsgBean.class);
                return orderMsgBean;
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
     * 支付倒计时
     */
    private void countDownTime() {
        new Thread() {
            @Override
            public void run() {
                long remaingTime = 600;
                StringBuilder sb = new StringBuilder();
                while (remaingTime > 0 && !isStopTime) {
                    sb.delete(0, sb.length());
                    remaingTime--;
                    try {
                        int hour = (int) (remaingTime / (60 * 60));
                        int minute = (int) ((remaingTime / 60) - hour * 60);
                        int second = (int) (remaingTime - hour * 60 - minute * 60);

                        if (minute > 0) {
                            sb.append(minute + "分" + second + "秒");
                        } else {
                            sb.append(second + "秒");
                        }
                        confirmPayMentActivity.setTime(sb.toString(), remaingTime);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }

    /**
     * 立即支付
     */
    public void applyment(){
        if (payType.equals(StatusVariable.WECHAT)) {
            weChat();
        } else if (payType.equals(StatusVariable.ALIPAY)) {
            payData();
        }
    }

    //支付宝
    private void payData() {
        HashMap params = new HashMap();
        params.put("baseMethod", Method.ALIPAY);
        params.put("orderCode", orderCode);
        params.put("token",token);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(confirmPayMentActivity, params, new RequestCallBack<PayResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<PayResultBean> response) {
                PayResultBean payResultBean = response.body();
                if (!EmptyUtil.isEmpty(payResultBean)){
                    int resultCode = payResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        thirdPart.aliPay(payResultBean.getResult().getSign(), alipayCallBack);
                    }
                }
            }

            @Override
            public PayResultBean parseNetworkResponse(String jsonResult) {
                PayResultBean payResultBean = JSON.parseObject(jsonResult, PayResultBean.class);
                return payResultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    //微信
    private void weChat() {

        HashMap params = new HashMap();
       params.put("baseMethod", Method.WECHAT);
       params.put("orderCode", orderCode);
       params.put("token", token);
       params.put("baseUrl", Config.baseUrl);

       OkHttpUtil.getRequest(confirmPayMentActivity, params, new RequestCallBack<WeChatBean>() {
           @Override
           public void onSuccess(com.lzy.okgo.model.Response<WeChatBean> response) {
               WeChatBean weChatBean = response.body();
               if (!EmptyUtil.isEmpty(weChatBean)){
                   int resultCode = weChatBean.getResultCode();
                   if (resultCode == StatusVariable.REQUESTSUCCESS){
                       WeChatBean.ResultBean resultBean = weChatBean.getResult();
                       if (!EmptyUtil.isEmpty(resultBean)){
                           thirdPart.wxPay(resultBean.getAppId(), resultBean.getPartnerId(), resultBean.getPrepayId(), weChatBean.getResult().getNonceStr(), resultBean.getTimeStamp(), resultBean.getSign());
                       }
                   }

               }
           }

           @Override
           public WeChatBean parseNetworkResponse(String jsonResult) {
               WeChatBean weChatBean = JSON.parseObject(jsonResult, WeChatBean.class);
               return weChatBean;
           }

           @Override
           public void onFailed(int code, String msg) {
               if (!EmptyUtil.isEmpty(msg)){
                   toastUtil.centerToast(msg);
               }
           }
       });
    }

    //支付回调
    AlipayCallBack alipayCallBack = new AlipayCallBack() {
        @Override
        public void paySuccess(String resultStatus) {
            if (matchType != null) {
                if (matchType.equals("personal")) {
                    intent = new Intent(confirmPayMentActivity, SuccessPaymentInfoActivity.class);
                    intent.putExtra("orderCode", orderCode);
                    intent.putExtra("orderStatus", StatusVariable.SUCCESSAPPLY);
                    intent.putExtra("orderType", StatusVariable.SUCCESSAPPLY);
                } else {
                    intent = new Intent(confirmPayMentActivity, GroupApplySuccessActivity.class);
                    intent.putExtra("orderCode", orderCode);
                    intent.putExtra("orderType", StatusVariable.SUCCESSAPPLY);
                }
                if (!success.get()) {
                    success.set(true);
                    confirmPayMentActivity.startActivity(intent);
                    confirmPayMentActivity.finish();
                }
            }
        }

        @Override
        public void payFailed(String resultStatus) {
//            ToastUtils.toast(confirmPayMentActivity, resultStatus);
        }
    };

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        subscribe = RxBus.getInstance().toObserverable(String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String payStatusBean) {
                if (payStatusBean.equals(StatusVariable.PAYSUCCESS)) {
                    if (matchType.equals("group")) {
                        intent = new Intent(confirmPayMentActivity, GroupApplySuccessActivity.class);
                        intent.putExtra("orderCode", orderCode);
                        intent.putExtra("orderType", StatusVariable.SUCCESSAPPLY);
                    } else {
                        intent = new Intent(confirmPayMentActivity, SuccessPaymentInfoActivity.class);
                        intent.putExtra("orderCode", orderCode);
                        intent.putExtra("orderStatus", StatusVariable.SUCCESSAPPLY);
                        intent.putExtra("orderType", StatusVariable.SUCCESSAPPLY);
                    }
                    if (!success.get()) {
                        success.set(true);
                        confirmPayMentActivity.startActivity(intent);
                        confirmPayMentActivity.finish();
                    }
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        isStopTime = true;
    }
}
