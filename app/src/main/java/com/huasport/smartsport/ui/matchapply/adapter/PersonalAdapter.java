package com.huasport.smartsport.ui.matchapply.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.ItemPersonalInfoBinding;
import com.huasport.smartsport.ui.matchapply.bean.AthletesMessageBean;
import com.huasport.smartsport.util.EmptyUtil;


/**
 * Created by 陆向阳 on 2018/6/28.
 */

public class PersonalAdapter extends BaseAdapter<AthletesMessageBean.ResultBean.PropertiesBean, BaseViewHolder> {

    private Context mContext;

    public PersonalAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ItemPersonalInfoBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.item_personal_info, parent, false);
        return new BaseViewHolder(itemBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        ItemPersonalInfoBinding itemBinding = (ItemPersonalInfoBinding) baseViewHolder.getBinding();

        if (!EmptyUtil.isEmpty(mList.get(position).getCnname())){

            itemBinding.cName.setText(mList.get(position).getCnname());

      //  if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("") && !mList.get(position).getVal().equals("null")) {

            if (!EmptyUtil.isEmpty(mList.get(position).getVal())){
            //if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("") && !mList.get(position).getVal().equals("null")) {

                //对性别特殊处理
                if (mList.get(position).getCnname().equals("性别")) {

                    if (mList.get(position).getVal().equals("f")) {
                        itemBinding.content.setText("女");
                    } else if (mList.get(position).getVal().equals("m")) {
                        itemBinding.content.setText("男");
                    }
                } else {
                    if (mList.get(position).getVal().equals("null") || EmptyUtil.isEmpty(mList.get(position).getVal()) || mList.get(position) == null) {
                        itemBinding.content.setText("");

                    } else {

                        if (mList.get(position).getCnname().equals("证件类型")) {
                            if (mList.get(position).getVal().equals("2")) {
                                mList.get(position).setVal("护照");
                            } else if (mList.get(position).getVal().equals("1")) {
                                mList.get(position).setVal("身份证");
                            } else if (mList.get(position).getVal().equals("4")) {
                                mList.get(position).setVal("军官证");
                            }
                        }
                        itemBinding.content.setText(mList.get(position).getVal());
                    }

                }
            }
        }
    }
}
