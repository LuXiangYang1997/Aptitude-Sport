package com.huasport.smartsport.ui.matchgrade.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.SelectMatchprogramItemLayoutBinding;
import com.huasport.smartsport.ui.matchgrade.bean.MatchGradeTabBean;
import com.huasport.smartsport.ui.matchgrade.view.SelectMatchProgramActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;

public class SelectMatchProgramAdapter extends BaseAdapter<MatchGradeTabBean.ResultBean.TypesBean, BaseViewHolder> {

    private SelectMatchProgramActivity selectMatchProgramActivity;
    private SelectMatchprogramItemLayoutBinding binding;

    public SelectMatchProgramAdapter(SelectMatchProgramActivity selectMatchProgramActivity) {
        super(selectMatchProgramActivity);
        this.selectMatchProgramActivity = selectMatchProgramActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        SelectMatchprogramItemLayoutBinding selectMatchprogramItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(selectMatchProgramActivity), R.layout.select_matchprogram_item_layout, parent, false);
        return new BaseViewHolder(selectMatchprogramItemLayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        binding = (SelectMatchprogramItemLayoutBinding) baseViewHolder.getBinding();
        //icon
        if (!EmptyUtil.isEmpty(mList.get(position).getGameImg())) {
            GlideUtil.LoadRoundCircleImage(selectMatchProgramActivity, mList.get(position).getGameImg(), binding.matchgradeitemImg);
        }
        //title
        if (!EmptyUtil.isEmpty(mList.get(position).getGameName())) {
            binding.matchgradeitemText.setText(mList.get(position).getGameName());
        }
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick(position);
            }
        });

    }

    public interface ItemClick {

        void itemClick(int position);
    }

    ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }
}
