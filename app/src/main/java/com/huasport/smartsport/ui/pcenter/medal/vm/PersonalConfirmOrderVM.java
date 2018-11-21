package com.huasport.smartsport.ui.pcenter.medal.vm;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

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
import com.huasport.smartsport.databinding.ConfirmorderLayoutBinding;
import com.huasport.smartsport.ui.pcenter.attention.bean.AddressBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.medal.bean.MedalDetailBean;
import com.huasport.smartsport.ui.pcenter.medal.bean.MedalOrderBean;
import com.huasport.smartsport.ui.pcenter.medal.bean.MyOrderAddressBean;
import com.huasport.smartsport.ui.pcenter.medal.view.AddAddressActivity;
import com.huasport.smartsport.ui.pcenter.medal.view.ImmediatelyPayActivity;
import com.huasport.smartsport.ui.pcenter.medal.view.PersonalMedalConfirmOrderActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;

import java.util.HashMap;

public class PersonalConfirmOrderVM extends BaseViewModel implements CounterListener {

    private PersonalMedalConfirmOrderActivity personalMedalConfirmOrderActivity;
    private MedalDetailBean.ResultBean.GoodsBean goodsBean;
    private ConfirmorderLayoutBinding confirmorderLayoutBinding;
    public ObservableField<String> leavingMsg = new ObservableField<>("");//买家留言
    private String token;
    public ObservableField<String> addressCodeStr = new ObservableField<>("");
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;

    public PersonalConfirmOrderVM(PersonalMedalConfirmOrderActivity personalMedalConfirmOrderActivity) {
        this.personalMedalConfirmOrderActivity = personalMedalConfirmOrderActivity;
        confirmorderLayoutBinding = personalMedalConfirmOrderActivity.getBinding();
        init();
        initAddress();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(personalMedalConfirmOrderActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(personalMedalConfirmOrderActivity, R.style.LoadingDialog);
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

    /**
     * 初始化地址
     */
    public void initAddress() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.MYORDERADDRESS);
        params.put("addressCode", "");
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(personalMedalConfirmOrderActivity, params, new RequestCallBack<MyOrderAddressBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<MyOrderAddressBean> response) {
                MyOrderAddressBean myOrderAddressBean = response.body();
                if (!EmptyUtil.isEmpty(myOrderAddressBean)) {
                    int resultCode = myOrderAddressBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        MyOrderAddressBean.ResultBean resultBean = myOrderAddressBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {

                            MyOrderAddressBean.ResultBean.AddressBean address =resultBean.getAddress();
                            if (address == null) {
                                confirmorderLayoutBinding.defaultAddress.setVisibility(View.GONE);
                                confirmorderLayoutBinding.noAddress.setVisibility(View.VISIBLE);

                            } else {
                                String realname = resultBean.getAddress().getRealname();
                                String province = resultBean.getAddress().getProvince();
                                String area = resultBean.getAddress().getArea();
                                String address1 = resultBean.getAddress().getAddress();
                                String city = resultBean.getAddress().getCity();
                                if (EmptyUtil.isEmpty(realname)) {
                                    confirmorderLayoutBinding.Addressee.setText("收件人:" + "");
                                }
                                if (EmptyUtil.isEmpty(realname) && EmptyUtil.isEmpty(province) && EmptyUtil.isEmpty(area) && EmptyUtil.isEmpty(address1) && EmptyUtil.isEmpty(city)) {
                                    confirmorderLayoutBinding.defaultAddress.setVisibility(View.GONE);
                                    confirmorderLayoutBinding.noAddress.setVisibility(View.VISIBLE);
                                    return;
                                }
                                addressCodeStr.set(address.getAddressCode());
                                confirmorderLayoutBinding.defaultAddress.setVisibility(View.VISIBLE);
                                confirmorderLayoutBinding.noAddress.setVisibility(View.GONE);
                                if (myOrderAddressBean.getResult().getAddress().getRealname().length() > 6) {
                                    String getrealname = resultBean.getAddress().getRealname().substring(0, 6);
                                    confirmorderLayoutBinding.Addressee.setText("收件人:" + getrealname + "...");
                                } else {
                                    confirmorderLayoutBinding.Addressee.setText("收件人:" +resultBean.getAddress().getRealname());
                                }
                                confirmorderLayoutBinding.userNumber.setText(resultBean.getAddress().getMobile());
                                confirmorderLayoutBinding.orderAddress.setText("收货地址:" + resultBean.getAddress().getProvince() + "-" + resultBean.getAddress().getCity() + "-" + resultBean.getAddress().getArea() + "-" + resultBean.getAddress().getAddress());
                            }
                        } else if (resultCode == StatusVariable.NOBIND) {
                            IntentUtil.startActivity(personalMedalConfirmOrderActivity, BindPhoneActivity.class);
                        } else if (resultCode == StatusVariable.NOLOGIN) {
                            IntentUtil.startActivity(personalMedalConfirmOrderActivity, LoginActivity.class);
                        }
                    }
                }
            }

            @Override
            public MyOrderAddressBean parseNetworkResponse(String jsonResult) {
                MyOrderAddressBean myOrderAddressBean = JSON.parseObject(jsonResult, MyOrderAddressBean.class);
                return myOrderAddressBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        Intent intent = personalMedalConfirmOrderActivity.getIntent();
        goodsBean = (MedalDetailBean.ResultBean.GoodsBean) intent.getSerializableExtra("GoodsBean");

        if (goodsBean != null) {
            if (goodsBean.getTitle() != null && !goodsBean.getTitle().equals("null")) {
                confirmorderLayoutBinding.cfirmTvTitle.setText(goodsBean.getTitle());
            }
            double productMedalPrice = (double) goodsBean.getProductPrice() / 100; //奖章金额
            double extraMoneyPrice = (double) goodsBean.getExtraMoney() / 100;//快递费

            String productMedalPricedecimal = Util.decimal(productMedalPrice);
            String extraMoneyPricedecimal = Util.decimal(extraMoneyPrice);


            confirmorderLayoutBinding.cfirmTvPrice.setText("￥ " + productMedalPricedecimal);
            confirmorderLayoutBinding.cfirmTvFee.setText("快递 (快递费:" + "￥" + extraMoneyPricedecimal + ")");

            int productPrice = goodsBean.getProductPrice() * (Integer.parseInt(confirmorderLayoutBinding.purchaseAccount.getText().toString()));
            int extraMoney = goodsBean.getExtraMoney();
            double allAcount = (double) (productPrice + extraMoney) / 100;

            double productPriceDouble = (double) productPrice / 100;
            double extraMoneyDouble = (double) extraMoney / 100;

            confirmorderLayoutBinding.tvTotal.setText("共计" + confirmorderLayoutBinding.purchaseAccount.getText().toString() + "件商品");

            String decimalproductPriceDouble = Util.decimal(productPriceDouble);
            String decimalextraMoneyDouble = Util.decimal(extraMoneyDouble);

            confirmorderLayoutBinding.cfirmTotalAccount.setText("小计：" + "￥" + decimalproductPriceDouble + "+" + "￥" + decimalextraMoneyDouble);

            String decimalallAcount = Util.decimal(allAcount);

            confirmorderLayoutBinding.cfirmPrice.setText("合计：￥" + decimalallAcount);

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (!EmptyUtil.isEmpty(s.toString())) {
//
                        if (Integer.parseInt(s.toString()) > 200) {
                            toastUtil.centerToast("最大可订购200件，超出请分批购买");
                            confirmorderLayoutBinding.purchaseAccount.setText("200");
                        } else if (Integer.parseInt(s.toString()) <= 0) {
                            toastUtil.centerToast( "购买数量不能少于1");
                            confirmorderLayoutBinding.purchaseAccount.setText("1");
                        }
                        confirmorderLayoutBinding.purchaseAccount.setSelection(confirmorderLayoutBinding.purchaseAccount.getText().toString().length());
                        double productprice = (double) goodsBean.getProductPrice() / 100;
                        double productPrice = (double) productprice * (Integer.parseInt(confirmorderLayoutBinding.purchaseAccount.getText().toString()));
                        double extraMoney = (double) goodsBean.getExtraMoney() / 100;
                        double allAcount = (double) (productPrice + extraMoney);


                        confirmorderLayoutBinding.tvTotal.setText("共计" + confirmorderLayoutBinding.purchaseAccount.getText().toString() + "件商品");

                        String decimalAcount = Util.decimal(allAcount);

                        confirmorderLayoutBinding.cfirmPrice.setText("合计：￥" + decimalAcount);

                        String decimalproductPrice = Util.decimal(productPrice);
                        String decimalextraMoney = Util.decimal(extraMoney);

                        confirmorderLayoutBinding.cfirmTotalAccount.setText("小计：" + "￥" + decimalproductPrice + "+" + "￥" + decimalextraMoney);
                    }
                }
            };

            confirmorderLayoutBinding.purchaseAccount.addTextChangedListener(textWatcher);
        }

    }

    //添加地址
    public void addAddress() {
        Intent intent = new Intent(personalMedalConfirmOrderActivity, AddAddressActivity.class);

        personalMedalConfirmOrderActivity.startActivityForResult(intent, StatusVariable.ADDRESSCODE);

    }

    //修改地址
    public void orderAddAddress() {
        Intent intent = new Intent(personalMedalConfirmOrderActivity, AddAddressActivity.class);
        intent.putExtra("addressCode", addressCodeStr.get());
        personalMedalConfirmOrderActivity.startActivityForResult(intent, StatusVariable.ADDRESSCODE);
    }

    /*
     * 提交订单
     * */
    public void applyOrder() {
        if (EmptyUtil.isEmpty(confirmorderLayoutBinding.purchaseAccount.getText().toString())) {
            toastUtil.centerToast("商品数量不能少于1");
            return;
        }
        if ((Integer.parseInt(confirmorderLayoutBinding.purchaseAccount.getText().toString())) == 0) {
            toastUtil.centerToast( "商品数量不能少于1");
            confirmorderLayoutBinding.purchaseAccount.setText(1 + "");
            return;
        }

        if (EmptyUtil.isEmpty(confirmorderLayoutBinding.orderAddress.getText().toString())) {
            toastUtil.centerToast( "请添加收货地址");
            return;
        }

        HashMap params = new HashMap();
        params.put("baseMethod", Method.CREATEORDER);
        params.put("goodsCode", goodsBean.getGoodsCode());
        params.put("addressCode", addressCodeStr.get());
        params.put("remark", leavingMsg.get());
        params.put("token", token);
        params.put("num", confirmorderLayoutBinding.purchaseAccount.getText().toString());
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.postRequest(personalMedalConfirmOrderActivity, params, new RequestCallBack<MedalOrderBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<MedalOrderBean> response) {
                MedalOrderBean medalOrderBean = response.body();
                if (!EmptyUtil.isEmpty(medalOrderBean)){
                    int resultCode = medalOrderBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                            Intent intent = new Intent(personalMedalConfirmOrderActivity, ImmediatelyPayActivity.class);
                            intent.putExtra("orderCode", medalOrderBean.getResult().getOrderCode());
                            personalMedalConfirmOrderActivity.startActivity(intent);
                    }else if (resultCode == StatusVariable.NOLOGIN){
                        IntentUtil.startActivity(personalMedalConfirmOrderActivity,LoginActivity.class);
                    }else if (resultCode == StatusVariable.NOBIND){
                        IntentUtil.startActivity(personalMedalConfirmOrderActivity,BindPhoneActivity.class);
                    }
                }
            }

            @Override
            public MedalOrderBean parseNetworkResponse(String jsonResult) {
                MedalOrderBean medalOrderBean = JSON.parseObject(jsonResult, MedalOrderBean.class);
                return medalOrderBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == StatusVariable.ADDRESSCODE) {
            if (data != null) {
                AddressBean.ResultBean.ListBean addressBean = (AddressBean.ResultBean.ListBean) data.getSerializableExtra("addressBean");
                addressCodeStr.set(addressBean.getAddressCode());
                confirmorderLayoutBinding.defaultAddress.setVisibility(View.VISIBLE);
                confirmorderLayoutBinding.noAddress.setVisibility(View.GONE);
                if (addressBean.getRealname().length() > 6) {
                    String realname = addressBean.getRealname().substring(0, 6);
                    confirmorderLayoutBinding.Addressee.setText("收件人:" + realname + "...");
                } else {
                    confirmorderLayoutBinding.Addressee.setText("收件人:" + addressBean.getRealname());
                }
                confirmorderLayoutBinding.userNumber.setText(addressBean.getMobile());
                confirmorderLayoutBinding.orderAddress.setText("收货地址:" + addressBean.getProvince() + "-" + addressBean.getCity() + "-" + addressBean.getArea() + "-" + addressBean.getAddress());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if(!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }

        }
    }
}