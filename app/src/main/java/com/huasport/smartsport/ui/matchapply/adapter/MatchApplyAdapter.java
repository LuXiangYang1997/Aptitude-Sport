package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MatchapplyItemLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.MatchApplyListBean;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;

public class MatchApplyAdapter extends BaseAdapter<MatchApplyListBean.ResultBean.TypesBean,BaseViewHolder>{

    private FragmentActivity activity;
    private MatchapplyItemLayoutBinding binding;

    public MatchApplyAdapter(FragmentActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {


        MatchapplyItemLayoutBinding matchapplyItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.matchapply_item_layout, parent, false);


        return new BaseViewHolder(matchapplyItemLayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {

        binding = (MatchapplyItemLayoutBinding) baseViewHolder.getBinding();

        if (!EmptyUtil.isEmpty(mList.get(position).getGameName())){
                binding.tvName.setText(mList.get(position).getGameName());
        }
        if (!EmptyUtil.isEmpty(mList.get(position).getGameImg())){
            GlideUtil.LoadImage(activity,mList.get(position).getGameImg(),binding.imgLogo);
        }

        binding.llMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }
}
