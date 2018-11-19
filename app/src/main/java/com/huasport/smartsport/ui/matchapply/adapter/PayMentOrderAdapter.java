package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.ConfirmListItemlayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.RegistrationInfoBean;
import com.huasport.smartsport.ui.matchapply.view.PayMentOrderActivty;
import com.huasport.smartsport.util.EmptyUtil;


/**
 * Created by 陆向阳 on 2018/6/10.
 */

public class PayMentOrderAdapter extends BaseAdapter<RegistrationInfoBean.ResultBean.OrderDetailBean.ApplysBean, BaseViewHolder> {

    private PayMentOrderActivty payMentOrderActivty;
    private double amountPrice = 0;
    public ObservableField<String> amountStr = new ObservableField<>();

    public PayMentOrderAdapter(PayMentOrderActivty payMentOrderActivty) {
        super(payMentOrderActivty);
        this.payMentOrderActivty = payMentOrderActivty;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        ConfirmListItemlayoutBinding confirmListItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(payMentOrderActivty), R.layout.confirm_list_itemlayout, parent, false);

        return new BaseViewHolder(confirmListItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        ConfirmListItemlayoutBinding binding = (ConfirmListItemlayoutBinding) baseViewHolder.getBinding();

        String applyAmountStr = mList.get(position).getApplyAmountStr();
        if (!EmptyUtil.isEmpty(applyAmountStr)) {
            binding.applyAmountStr.setText(applyAmountStr);
        }
        String matchGroupName = mList.get(position).getMatchGroupName();
        String eventName = mList.get(position).getEventName();

        if (!EmptyUtil.isEmpty(matchGroupName) && !EmptyUtil.isEmpty(eventName)) {
            binding.matchText.setText(matchGroupName + "|" + eventName);
        } else if (EmptyUtil.isEmpty(matchGroupName) && !EmptyUtil.isEmpty(eventName)) {
            binding.matchText.setText(eventName);
        } else if (!EmptyUtil.isEmpty(matchGroupName) && EmptyUtil.isEmpty(eventName)) {
            binding.matchText.setText(matchGroupName);
        }

        if (position == mList.size() - 1) {
            binding.lineView.setVisibility(View.GONE);
        }
        double v = Double.parseDouble(mList.get(position).getApplyAmountStr());
        amountPrice += v;
        amountStr.set(amountPrice + "");
    }
}
