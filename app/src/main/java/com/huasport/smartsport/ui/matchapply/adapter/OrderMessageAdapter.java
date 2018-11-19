package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.OrdermessageItemBinding;
import com.huasport.smartsport.ui.matchapply.bean.OrderMsgBean;
import com.huasport.smartsport.ui.matchapply.view.ConfirmPayMentActivity;
import com.huasport.smartsport.util.EmptyUtil;

/**
 * Created by 陆向阳 on 2018/6/11.
 */
//订单编号及其他
public class OrderMessageAdapter extends BaseAdapter<OrderMsgBean.ResultBean.PayinfoBean, BaseViewHolder> {

    private ConfirmPayMentActivity confirmPayMentActivity;

    public OrderMessageAdapter(ConfirmPayMentActivity confirmPayMentActivity) {
        super(confirmPayMentActivity);
        this.confirmPayMentActivity = confirmPayMentActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        OrdermessageItemBinding ordermessageItemBinding = DataBindingUtil.inflate(LayoutInflater.from(confirmPayMentActivity), R.layout.ordermessage_item, parent, false);

        return new BaseViewHolder(ordermessageItemBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        OrdermessageItemBinding binding = (OrdermessageItemBinding) baseViewHolder.getBinding();

        String orderTime = mList.get(position).getOrderTime();
        if (!EmptyUtil.isEmpty(orderTime)){
            binding.orderTime.setText(orderTime);
        }


    }
}
