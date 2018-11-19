package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.EventsItemLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.GroupEventsBean;
import com.huasport.smartsport.ui.matchapply.view.CompetitionListActivity;
import com.huasport.smartsport.util.EmptyUtil;

import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/9.
 */

public class GroupItemRecyclerViewAdapter extends BaseAdapter<GroupEventsBean.ResultBean.GroupsBean, BaseViewHolder> {
    private CompetitionListActivity competitionListActivity;
    private CompetitionListAdapter competitionListAdapter;

    public GroupItemRecyclerViewAdapter(CompetitionListActivity competitionListActivity, CompetitionListAdapter competitionListAdapter) {
        super(competitionListActivity);
        this.competitionListActivity = competitionListActivity;
        this.competitionListAdapter = competitionListAdapter;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        EventsItemLayoutBinding eventsItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(competitionListActivity), R.layout.events_item_layout, parent, false);

        return new BaseViewHolder(eventsItemLayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        EventsItemLayoutBinding binding = (EventsItemLayoutBinding) baseViewHolder.getBinding();
        GroupEventsBean.ResultBean.GroupsBean groupsBean = mList.get(position);
        List<GroupEventsBean.ResultBean.GroupsBean.EventsBean> events = groupsBean.getEvents();
        if (!EmptyUtil.isEmpty(groupsBean.getGroupName())) {
            binding.groupName.setText(groupsBean.getGroupName());
        }
    }

}
