package com.huasport.smartsport.ui.matchapply.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.ItemSignUpInfoBinding;
import com.huasport.smartsport.ui.matchapply.bean.RegistrationInfoBean;
import com.huasport.smartsport.util.EmptyUtil;


/**
 * Created by 陆向阳 on 2018/6/27.
 */

public class SignUpAdapter extends BaseAdapter<RegistrationInfoBean.ResultBean.OrderDetailBean.ApplysBean, BaseViewHolder> {

    private Context mContext;

    public SignUpAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ItemSignUpInfoBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.item_sign_up_info, parent, false);
        return new BaseViewHolder(itemBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        ItemSignUpInfoBinding binding = (ItemSignUpInfoBinding) baseViewHolder.getBinding();

        String matchGroupName = mList.get(position).getMatchGroupName();

        String eventName = mList.get(position).getEventName();
        if(!EmptyUtil.isEmpty(matchGroupName)&&!EmptyUtil.isEmpty(eventName)){
            binding.tvMatchGroupName.setText(matchGroupName+" | "+eventName);
        }else if (!EmptyUtil.isEmpty(matchGroupName)&&EmptyUtil.isEmpty(eventName)){
            binding.tvMatchGroupName.setText(matchGroupName);
        }else if (EmptyUtil.isEmpty(matchGroupName)&&!EmptyUtil.isEmpty(eventName)){
            binding.tvMatchGroupName.setText(eventName);
        }
        String eventStartTime = mList.get(position).getEventStartTime();
        if(!EmptyUtil.isEmpty(eventStartTime)){
            binding.tvEventStartTime.setText(eventStartTime);
        }

        String applyAmountStr = mList.get(position).getApplyAmountStr();
        if(!EmptyUtil.isEmpty(applyAmountStr)){
            binding.tvApplyAmountStr.setText(applyAmountStr);
        }


    }
}
