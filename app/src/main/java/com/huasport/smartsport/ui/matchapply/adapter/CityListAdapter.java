package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.CitylistItemBinding;
import com.huasport.smartsport.ui.matchapply.view.CompetitionListActivity;
import com.huasport.smartsport.util.EmptyUtil;

/**
 * Created by bqj on 2018/6/30.
 */

public class CityListAdapter extends BaseAdapter<String, BaseViewHolder> {

    private CompetitionListActivity competitionListActivity;
    private CitylistItemBinding citylistItemBinding;
    public CityListAdapter(CompetitionListActivity competitionListActivity) {
        super(competitionListActivity);
        this.competitionListActivity = competitionListActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        citylistItemBinding = DataBindingUtil.inflate(LayoutInflater.from(competitionListActivity), R.layout.citylist_item, parent, false);

        return new BaseViewHolder(citylistItemBinding);
    }

    @Override
    public void onBindVH(final BaseViewHolder viewHolder, final int position) {

        CitylistItemBinding citylistItemBinding = (CitylistItemBinding) viewHolder.getBinding();

        String cityStr = mList.get(position);
        if(!EmptyUtil.isEmpty(cityStr)){
            citylistItemBinding.tvCity.setText(cityStr);
        }

        //条目点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityItemClick.cityitemClick(viewHolder,position);

            }
        });

    }

    //条目点击事件回调
    public interface CityItemClick {
        void cityitemClick(BaseViewHolder baseViewHolder, int position);
    }
    CityItemClick cityItemClick;

    public void setCityItemClick(CityItemClick cityItemClick) {
        this.cityItemClick = cityItemClick;
    }
}
