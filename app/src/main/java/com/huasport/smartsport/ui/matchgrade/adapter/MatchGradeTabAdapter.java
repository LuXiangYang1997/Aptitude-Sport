package com.huasport.smartsport.ui.matchgrade.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MatchgradeTablayoutBinding;
import com.huasport.smartsport.ui.matchgrade.bean.MatchGradeTabBean;
import com.huasport.smartsport.util.EmptyUtil;

public class MatchGradeTabAdapter extends BaseAdapter<MatchGradeTabBean.ResultBean.TypesBean,BaseViewHolder>{

    private FragmentActivity activity;

    public MatchGradeTabAdapter(FragmentActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        MatchgradeTablayoutBinding matchgradeTablayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.matchgrade_tablayout, parent, false);

        return new BaseViewHolder(matchgradeTablayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        MatchgradeTablayoutBinding binding = (MatchgradeTablayoutBinding) baseViewHolder.getBinding();

        //更改tab的选中状态
        if (mList.get(position).isCheck()) {
            binding.tvTabName.setTextColor(activity.getResources().getColor(R.color.color_FF8F00));
            binding.indicator.setVisibility(View.VISIBLE);
        } else {
            binding.tvTabName.setTextColor(activity.getResources().getColor(R.color.color_333333));
            binding.indicator.setVisibility(View.GONE);
        }
        //title
        if (!EmptyUtil.isEmpty(mList.get(position).getGameName())){
            binding.tvTabName.setText(mList.get(position).getGameName());
        }
        //条目点击事件
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClick.itemClick(mList.get(position),position);

            }
        });
    }

    public interface ItemClick{

        void itemClick(MatchGradeTabBean.ResultBean.TypesBean typesBean,int position);

    }
    ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }
}
