package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.ConfirmpaymentMessageitemBinding;
import com.huasport.smartsport.ui.matchapply.bean.OrderMsgBean;
import com.huasport.smartsport.ui.matchapply.view.ConfirmPayMentActivity;
import com.huasport.smartsport.util.EmptyUtil;

/**
 * Created by 陆向阳 on 2018/6/11.
 */
//确认支付—订单信息
public class ConfirmPayMentAdapter extends BaseAdapter<OrderMsgBean.ResultBean.PayinfoBean.ApplysBean, BaseViewHolder> {

    private ConfirmPayMentActivity confirmPayMentActivity;
    private double amountprice = 0d;

    public ConfirmPayMentAdapter(ConfirmPayMentActivity confirmPayMentActivity) {
        super(confirmPayMentActivity);
        this.confirmPayMentActivity = confirmPayMentActivity;
    }


    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        ConfirmpaymentMessageitemBinding confirmpaymentMessageitemBinding = DataBindingUtil.inflate(LayoutInflater.from(confirmPayMentActivity), R.layout.confirmpayment_messageitem, parent, false);

        return new BaseViewHolder(confirmpaymentMessageitemBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        ConfirmpaymentMessageitemBinding confirmpaymentMessageitemBinding = (ConfirmpaymentMessageitemBinding) baseViewHolder.getBinding();

        String matchGroupName = mList.get(position).getMatchGroupName();
        String eventName = mList.get(position).getEventName();

        if (!EmptyUtil.isEmpty(matchGroupName) && !EmptyUtil.isEmpty(eventName)) {
            confirmpaymentMessageitemBinding.matchText.setText(matchGroupName + "|" + eventName);
        } else if (EmptyUtil.isEmpty(matchGroupName) && !EmptyUtil.isEmpty(eventName)) {
            confirmpaymentMessageitemBinding.matchText.setText(eventName);
        } else if (!EmptyUtil.isEmpty(matchGroupName) && EmptyUtil.isEmpty(eventName)) {
            confirmpaymentMessageitemBinding.matchText.setText(matchGroupName);
        }

//        if (matchGroupName != null && !matchGroupName.equals("null") && !matchGroupName.equals("") && eventName != null && !eventName.equals("null") && !eventName.equals("")) {
//            confirmpaymentMessageitemBinding.matchText.setText(matchGroupName + "|" + eventName);
//        } else if (matchGroupName == null && matchGroupName.equals("null") && matchGroupName.equals("") && eventName != null && !eventName.equals("null") && !eventName.equals("")) {
//            confirmpaymentMessageitemBinding.matchText.setText(eventName);
//        } else if (matchGroupName != null && !matchGroupName.equals("null") && !matchGroupName.equals("") && eventName == null && eventName.equals("null") && eventName.equals("")) {
//            confirmpaymentMessageitemBinding.matchText.setText(matchGroupName);
//        }

        if (!EmptyUtil.isEmpty(mList.get(position).getOrderAmountStr())) {
            confirmpaymentMessageitemBinding.tvApplyAmountStr.setText(mList.get(position).getOrderAmountStr());
        }
//
//        if (mList.get(position).getOrderAmountStr() != null) {
//            confirmpaymentMessageitemBinding.orderPrice.setText(mList.get(position).getOrderAmountStr());
//        }

        //判断是否是最后一条，如果是则隐藏线
        if (position == mList.size() - 1) {
            confirmpaymentMessageitemBinding.allpriceLayout.setVisibility(View.VISIBLE);
        }
        //判空，会出现空的情况
//        if (mList.get(position).getMatchGroupName() != null) {
//            if (mList.get(position).getMatchGroupName().equals("null") && mList.get(position).getMatchGroupName().equals("")) {
//                confirmpaymentMessageitemBinding.verticalLine.setVisibility(View.GONE);
//            }
//        } else {
//            confirmpaymentMessageitemBinding.verticalLine.setVisibility(View.GONE);
//        }
        if (EmptyUtil.isNotEmpty(mList.get(position))) {

            for (OrderMsgBean.ResultBean.PayinfoBean.ApplysBean applysBean : mList) {
                String entryFeeStr = applysBean.getApplyAmountStr();
                double v = Double.parseDouble(entryFeeStr);
                amountprice = amountprice + v;
            }
            confirmpaymentMessageitemBinding.orderPrice.setText("¥" + amountprice);
        }

    }
}
