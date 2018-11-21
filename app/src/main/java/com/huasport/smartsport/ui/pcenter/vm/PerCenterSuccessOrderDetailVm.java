package com.huasport.smartsport.ui.pcenter.vm;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.MedalOrdermessageLayoutBinding;
import com.huasport.smartsport.ui.pcenter.bean.PerCentersuccessOrderBean;
import com.huasport.smartsport.ui.pcenter.view.PerCenterSuccessOrderDetailActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.Util;

import java.util.HashMap;

public class PerCenterSuccessOrderDetailVm extends BaseViewModel {

    private PerCenterSuccessOrderDetailActivity perCenterSuccessOrderDetailActivity;
    private MedalOrdermessageLayoutBinding binding;

    public PerCenterSuccessOrderDetailVm(PerCenterSuccessOrderDetailActivity perCenterSuccessOrderDetailActivity) {
        this.perCenterSuccessOrderDetailActivity = perCenterSuccessOrderDetailActivity;
        binding = perCenterSuccessOrderDetailActivity.getBinding();
        initData();
    }

    private void initData() {
        //订单详情
        String orderCode = perCenterSuccessOrderDetailActivity.getIntent().getStringExtra("orderCode");

        HashMap params = new HashMap();
        params.put("baseMethod", Method.PCORDERDETAIL + orderCode);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(perCenterSuccessOrderDetailActivity, params, new RequestCallBack<PerCentersuccessOrderBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<PerCentersuccessOrderBean> response) {
                PerCentersuccessOrderBean perCentersuccessOrderBean = response.body();
                if (!EmptyUtil.isEmpty(perCentersuccessOrderBean)) {
                    if (perCentersuccessOrderBean.getResultCode() == StatusVariable.SUCCESS) {
                        PerCentersuccessOrderBean.ResultBean.OrderBean order = perCentersuccessOrderBean.getResult().getOrder();
                        if (!EmptyUtil.isEmpty(order)) {
                            String realname = order.getRealname();
                            int realnamelength = realname.length();
                            if (realnamelength > 6) {
                                String substring = realname.substring(0, 6);
                                binding.tvName.setText(substring + "...");
                            } else {
                                binding.tvName.setText(realname);
                            }
                            binding.tvPhoneNumber.setText(order.getMobile());
                            binding.tvAddress.setText("地址：" + order.getProvince() + order.getCity() + order.getArea() + order.getAddress());
                            binding.orderDetailTvTitle.setText(order.getGoodsTitle());
                            binding.tvOrderDetailMedalCount.setText("x" + order.getGoodsNum() + "");

                            double orderPrice = (double) order.getOrderPrice() / 100;
                            double goodsPrice = (double) order.getGoodsPrice() / 100;
                            double extramoney = (double) order.getExtraMoney() / 100;
                            //总价-运费=商品金额
                            double price = orderPrice - extramoney;
                            String decimalPrice = Util.decimal(price);
                            binding.orderDetailTvPrice.setText("￥" + goodsPrice + "");
                            binding.tvAllPrice.setText("￥" + orderPrice + "");
                            binding.tvOrderMedalPrice.setText("￥" + decimalPrice + "");
                            binding.tvFreight.setText("￥" + extramoney + "");
                            binding.tvMedalorderDetailNum.setText(order.getOrderCode() + "");
                            binding.tvMedalorderDetailTime.setText(order.getPayTime());
                            binding.tvPayWay.setText(order.getPayType());
                        }
                    }
                }
            }
            @Override
            public PerCentersuccessOrderBean parseNetworkResponse(String jsonResult) {
                PerCentersuccessOrderBean perCentersuccessOrderBean = JSON.parseObject(jsonResult, PerCentersuccessOrderBean.class);
                return perCentersuccessOrderBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

}