package com.huasport.smartsport.ui.matchapply.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.GroupapplysuccessItemlayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.GroupOrderMsgBean;
import com.huasport.smartsport.util.EmptyUtil;


public class GroupPersonalMsgAdapter extends BaseAdapter<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean, BaseViewHolder> {

    private Context context;
    private String name;

    public GroupPersonalMsgAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        GroupapplysuccessItemlayoutBinding groupapplysuccessItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.groupapplysuccess_itemlayout, parent, false);
        return new BaseViewHolder(groupapplysuccessItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        GroupapplysuccessItemlayoutBinding groupapplysuccessItemlayoutBindin = (GroupapplysuccessItemlayoutBinding) baseViewHolder.getBinding();
        groupapplysuccessItemlayoutBindin.setVariable(BR.ApplaysBean, mList.get(position));
        groupapplysuccessItemlayoutBindin.setVariable(BR.position, position);

//        if (mList.get(position).getVal() == null || mList.get(position).getVal().equals("null") || mList.get(position).getVal().equals("")) {
//            groupapplysuccessItemlayoutBindin.llGroupapplysuccessItem.setVisibility(View.GONE);
//        }
        if (EmptyUtil.isEmpty(mList.get(position).getVal())) {
            groupapplysuccessItemlayoutBindin.llGroupapplysuccessItem.setVisibility(View.GONE);
        }

        if (!EmptyUtil.isEmpty(mList.get(position).getCnname())) {


            if (!EmptyUtil.isEmpty(mList.get(position).getVal())) {

                groupapplysuccessItemlayoutBindin.tvGroupsuccessCname.setText(mList.get(position).getCnname());
                groupapplysuccessItemlayoutBindin.tvGroupsuccessVal.setText(mList.get(position).getVal());
                if (!EmptyUtil.isEmpty(mList.get(position).getCnname())) {

                    if (!EmptyUtil.isEmpty(mList.get(position).getVal())) {


                        if (mList.get(position).getCnname().equals("证件类型")) {
                            if (mList.get(position).getVal().equals(StatusVariable.PASSCARD)) {
                                mList.get(position).setVal("护照");
                            } else if (mList.get(position).getVal().equals(StatusVariable.IDCARD)) {
                                mList.get(position).setVal("身份证");
                            } else if (mList.get(position).getVal().equals(StatusVariable.CERTIFICATECARD)) {
                                mList.get(position).setVal("军官证");
                            }
                        }

                        if (mList.get(position).getCnname().equals("性别")) {
                            if (mList.get(position).getVal().equals("m")) {
                                mList.get(position).setVal("男");
                            } else {
                                mList.get(position).setVal("女");
                            }
                        }


                    }

                } else {
//                groupapplysuccessItemlayoutBindin.llPassportImg.setVisibility(View.GONE);
                    baseViewHolder.itemView.setVisibility(View.GONE);
                }

            }
        }
    }
}
