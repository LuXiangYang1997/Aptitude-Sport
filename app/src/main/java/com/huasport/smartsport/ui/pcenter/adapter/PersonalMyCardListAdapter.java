package com.huasport.smartsport.ui.pcenter.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.PersonalmycardlistItemlayoutBinding;
import com.huasport.smartsport.ui.pcenter.settings.bean.UserInfoBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyCardListActivity;
import com.huasport.smartsport.util.EmptyUtil;


public class PersonalMyCardListAdapter extends BaseAdapter<UserInfoBean.ResultBean.RegisterBean, BaseViewHolder> {

    private PersonalMyCardListActivity personalMyCardListActivity;
    private PersonalmycardlistItemlayoutBinding binding;

    public PersonalMyCardListAdapter(PersonalMyCardListActivity personalMyCardListActivity) {
        super(personalMyCardListActivity);
        this.personalMyCardListActivity = personalMyCardListActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        PersonalmycardlistItemlayoutBinding personalmycardlistItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(personalMyCardListActivity), R.layout.personalmycardlist_itemlayout, parent, false);

        return new BaseViewHolder(personalmycardlistItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {

        binding = (PersonalmycardlistItemlayoutBinding) baseViewHolder.getBinding();
        //realName名字
        if (!EmptyUtil.isEmpty(mList.get(position).getRealName())) {
            binding.userName.setText(mList.get(position).getRealName());
        }
        //手机号
        if (!EmptyUtil.isEmpty(mList.get(position).getPhone())) {
            binding.userPhone.setText(mList.get(position).getPhone());
        }

    }
}
