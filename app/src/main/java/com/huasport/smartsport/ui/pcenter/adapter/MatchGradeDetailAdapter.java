package com.huasport.smartsport.ui.pcenter.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MatchgradedetailItemlayoutBinding;
import com.huasport.smartsport.ui.pcenter.bean.PersonalMyGradeDetailBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyGradeDetailActivity;
import com.huasport.smartsport.util.EmptyUtil;


public class MatchGradeDetailAdapter extends BaseAdapter<PersonalMyGradeDetailBean.ResultBean.DetailBean, BaseViewHolder> {

    private PersonalMyGradeDetailActivity personalMyGradeDetailActivity;
    private MatchgradedetailItemlayoutBinding binding;

    public MatchGradeDetailAdapter(PersonalMyGradeDetailActivity personalMyGradeDetailActivity) {
        super(personalMyGradeDetailActivity);
        this.personalMyGradeDetailActivity = personalMyGradeDetailActivity;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        MatchgradedetailItemlayoutBinding matchgradedetailItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(personalMyGradeDetailActivity), R.layout.matchgradedetail_itemlayout, parent, false);
        return new BaseViewHolder(matchgradedetailItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        binding = (MatchgradedetailItemlayoutBinding) baseViewHolder.getBinding();
        if (!EmptyUtil.isEmpty(mList.get(position).getKeyName())) {
            binding.matchName.setText(mList.get(position).getKeyName());
        }
        if (!EmptyUtil.isEmpty(mList.get(position).getValueName())) {
            binding.matchDetail.setText(mList.get(position).getValueName());
        }

    }
}
