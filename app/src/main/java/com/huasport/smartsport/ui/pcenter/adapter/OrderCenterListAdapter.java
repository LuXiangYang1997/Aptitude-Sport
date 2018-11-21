package com.huasport.smartsport.ui.pcenter.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.OrdercenterItemlayoutBinding;
import com.huasport.smartsport.ui.pcenter.bean.OrderCenterListBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyOrderActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.Util;

public class OrderCenterListAdapter extends BaseAdapter<OrderCenterListBean.ResultBean.ListBean, BaseViewHolder> {

    private PersonalMyOrderActivity personalMyOrderActivity;

    public OrderCenterListAdapter(PersonalMyOrderActivity personalMyOrderActivity) {
        super(personalMyOrderActivity);
        this.personalMyOrderActivity = personalMyOrderActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        OrdercenterItemlayoutBinding ordercenterItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(personalMyOrderActivity), R.layout.ordercenter_itemlayout, parent, false);

        return new BaseViewHolder(ordercenterItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {

        OrdercenterItemlayoutBinding ordercenterItemlayoutBinding = (OrdercenterItemlayoutBinding) baseViewHolder.getBinding();
        if (!EmptyUtil.isEmpty(mList.get(position))) {
            // if (mList.get(position) != null) {
            ordercenterItemlayoutBinding.orderCode.setText("订单编号 ：" + mList.get(position).getOrderCode());
            ordercenterItemlayoutBinding.orderName.setText(mList.get(position).getGoodsTitle());
            double price = (double) mList.get(position).getOrderPrice() / 100;
            String priceDecimal = Util.decimal(price);
            ordercenterItemlayoutBinding.orderPrice.setText("￥" + priceDecimal + "元");
            if (!EmptyUtil.isEmpty(mList.get(position).getGoodsPic())) {
                // if (mList.get(position).getGoodsPic() != null && !mList.get(position).getGoodsPic().equals("null") && !mList.get(position).getGoodsPic().equals("")) {
                Glide.with(personalMyOrderActivity).load(mList.get(position).getGoodsPic()).into(ordercenterItemlayoutBinding.orderImg);
            } else {
                ordercenterItemlayoutBinding.orderImg.setImageResource(R.mipmap.orderimg);
            }
            final String orderStatus = mList.get(position).getOrderStatus();

            if (orderStatus.equals(StatusVariable.WAITPAY)) {
                ordercenterItemlayoutBinding.nextStep.setVisibility(View.VISIBLE);
                ordercenterItemlayoutBinding.orderStatus.setText("待支付");
                ordercenterItemlayoutBinding.orderStatus.setTextColor(personalMyOrderActivity.getResources().getColor(R.color.color_f44444));
            } else if (orderStatus.equals(StatusVariable.SUCCESSAPPLY)) {
                ordercenterItemlayoutBinding.nextStep.setVisibility(View.VISIBLE);
//                ordercenterItemlayoutBinding.
                ordercenterItemlayoutBinding.orderStatus.setText("已支付");
                ordercenterItemlayoutBinding.orderStatus.setTextColor(personalMyOrderActivity.getResources().getColor(R.color.color_28f57a));
            } else if (orderStatus.equals(StatusVariable.SHIPPED)) {
                ordercenterItemlayoutBinding.nextStep.setVisibility(View.VISIBLE);
//                ordercenterItemlayoutBinding.
                ordercenterItemlayoutBinding.orderStatus.setText("已发货");
                ordercenterItemlayoutBinding.orderStatus.setTextColor(personalMyOrderActivity.getResources().getColor(R.color.color_FF8F00));
            } else if (orderStatus.equals(StatusVariable.COMOLETED)) {
                ordercenterItemlayoutBinding.nextStep.setVisibility(View.VISIBLE);
//                ordercenterItemlayoutBinding.
                ordercenterItemlayoutBinding.orderStatus.setText("已完成");
                ordercenterItemlayoutBinding.orderStatus.setTextColor(personalMyOrderActivity.getResources().getColor(R.color.color_333333));
            }

            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (orderStatus.equals(StatusVariable.WAITPAY)) {
//                        SharedPreferencesUtil.setParam(personalMyOrderActivity, "successType", "ordercenter");
//                        Intent intent = new Intent(personalMyOrderActivity, ImmediatelyPayActivity.class);
//                        intent.putExtra("orderCode", mList.get(position).getOrderCode());
//                        personalMyOrderActivity.startActivity(intent);

                    } else if (orderStatus.equals(StatusVariable.SUCCESSAPPLY) || orderStatus.equals(StatusVariable.SHIPPED) || orderStatus.equals(StatusVariable.COMOLETED)) {
//                        Intent intent = new Intent(personalMyOrderActivity, PerCenterSuccessOrderDetailActivity.class);
//                        intent.putExtra("orderCode", mList.get(position).getOrderCode());
//                        intent.putExtra("orderType", orderStatus);
//                        personalMyOrderActivity.startActivity(intent);
                    }
                }
            });
        }

    }
}