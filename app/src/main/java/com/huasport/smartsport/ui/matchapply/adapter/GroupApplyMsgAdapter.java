package com.huasport.smartsport.ui.matchapply.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.GroupapplymessageLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.GroupOrderMsgBean;

public class GroupApplyMsgAdapter extends BaseAdapter<GroupOrderMsgBean.ResultBean.OrderDetailBean.ApplysBean, BaseViewHolder> {

    private Context mContext;

    public GroupApplyMsgAdapter(Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

       GroupapplymessageLayoutBinding groupapplymessageLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.groupapplymessage_layout,parent,false);

        return new BaseViewHolder(groupapplymessageLayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        GroupapplymessageLayoutBinding groupapplymessageLayoutBinding= (GroupapplymessageLayoutBinding) baseViewHolder.getBinding();
        groupapplymessageLayoutBinding.setVariable(BR.position,position);
        groupapplymessageLayoutBinding.setVariable(BR.applysBean,mList.get(position));
    }
}
