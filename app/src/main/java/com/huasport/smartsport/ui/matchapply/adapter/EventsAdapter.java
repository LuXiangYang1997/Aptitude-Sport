package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.ProgramGroupItemBinding;
import com.huasport.smartsport.ui.matchapply.bean.EventsBean;
import com.huasport.smartsport.ui.matchapply.view.CompetitionListActivity;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 陆向阳 on 2018/7/31.
 */

public class EventsAdapter extends BaseAdapter<EventsBean, BaseViewHolder> {

    ObservableField<String> itemType = new ObservableField<>("");//记录itemType
    private CompetitionListActivity competitionListActivity;
    //    private List<GroupEventsBean.ResultBean.GroupsBean.EventsBean> mList = new ArrayList<>();
    private int expandPosition = -1;
    private GroupItemRecyclerViewAdapter groupItemRecyclerViewAdapter;
    private CompetitionListAdapter competitionListAdapter;
    private List<EventsBean> eventsBeanArrayList = new ArrayList<>();
    private String groupName;
    private final ToastUtil toastUtƒil;
    private static  String starttime="yyyy-MM-dd HH:mm:ss";

    public EventsAdapter(CompetitionListActivity competitionListActivity, CompetitionListAdapter competitionListAdapter) {
        super(competitionListActivity);
        this.competitionListActivity = competitionListActivity;
        this.competitionListAdapter = competitionListAdapter;
        toastUtƒil = new ToastUtil(competitionListActivity);
    }


    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        ProgramGroupItemBinding groupItemBinding = DataBindingUtil.inflate(LayoutInflater.from(competitionListActivity), R.layout.program_group_item, parent, false);

        return new BaseViewHolder(groupItemBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        final ProgramGroupItemBinding programGroupItemBinding = (ProgramGroupItemBinding) baseViewHolder.getBinding();
        //分组Name
        String groupName = mList.get(position).getGroupName();
        if(!EmptyUtil.isEmpty(groupName)){
            programGroupItemBinding.groupName.setText(groupName);
        }
        //时间
        String startTime = mList.get(position).getStartTime();
        if(!EmptyUtil.isEmpty(startTime)){
            String strtime = DateUtil.StrToDate(startTime, starttime);
            programGroupItemBinding.programTime.setText(strtime);
        }
        //金额
        String entryFeeStr = mList.get(position).getEntryFeeStr();
        if(!EmptyUtil.isEmpty(entryFeeStr)){
            programGroupItemBinding.programPrice.setText(entryFeeStr+"元");
        }

        if (position != 0) {
            this.groupName = mList.get(position - 1).getGroupName();
        } else {
            this.groupName = mList.get(position).getGroupName();
        }
        //判断是否显示头布局
        if (position == 0) {
            programGroupItemBinding.llHeader.setVisibility(View.VISIBLE);
        } else {
            if (mList.get(position).getGroupName().equals(this.groupName)) {
                programGroupItemBinding.llHeader.setVisibility(View.GONE);
            } else {
                programGroupItemBinding.llHeader.setVisibility(View.VISIBLE);
            }
        }

        if (mList.get(position).getItemType().equals("group")) {
            if (position != expandPosition) {
                programGroupItemBinding.groupItemCheck.setChecked(false);
            } else {
                programGroupItemBinding.groupItemCheck.setChecked(mList.get(position).isCheckbox());
            }
        } else {
            programGroupItemBinding.groupItemCheck.setChecked(mList.get(position).isCheckbox());
        }

        //设置颜色
        if (!EmptyUtil.isEmpty(mList.get(position).getItemType())) {

            String surplusQuota = mList.get(position).getSurplusQuota();
            programGroupItemBinding.programQuota.setText(surplusQuota);
            if (mList.get(position).getItemType().equals("group")) {
                //设置部分字颜色
                programGroupItemBinding.programName.setText("团体赛" + mList.get(position).getItemName());
                SpannableStringBuilder spannableStringBuilder = Util.setSpan(programGroupItemBinding.programName.getText().toString(), Color.parseColor("#FF8F00"), 0, 3);
                programGroupItemBinding.programName.setText(spannableStringBuilder);
            } else if (mList.get(position).getItemType().equals("personal")) {
                programGroupItemBinding.programName.setText("个人赛" + mList.get(position).getItemName());
                SpannableStringBuilder spannableStringBuilder = Util.setSpan(programGroupItemBinding.programName.getText().toString(), Color.parseColor("#FF8F00"), 0, 3);
                programGroupItemBinding.programName.setText(spannableStringBuilder);
            }
        }


        //点击事件
        programGroupItemBinding.llMatchitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (programGroupItemBinding.groupItemCheck.isChecked()) {
                    programGroupItemBinding.groupItemCheck.setChecked(false);
                } else {
                    programGroupItemBinding.groupItemCheck.setChecked(true);
                }
                //判断itemType是否是group还是personal，组的话则是切换状态，personal则是多选状态
                if (itemType.get().equals(mList.get(position).getItemType()) || EmptyUtil.isEmpty(itemType.get())) {
                    if (mList.get(position).getItemType().equals("group")) {
                        if (programGroupItemBinding.groupItemCheck.isChecked()) {
                            expandPosition = position;
                            mList.get(position).setCheckbox(true);
                            competitionListAdapter.addEventBean(mList.get(position));
                            itemType.set("group");
                        } else {
                            mList.get(position).setCheckbox(false);
                            expandPosition = -1;
                            itemType.set("");
                            competitionListAdapter.deleteEventBean(mList.get(position));
                        }
                        notifyDataSetChanged();
                    } else {
                        if (programGroupItemBinding.groupItemCheck.isChecked()) {
                            eventsBeanArrayList.add(mList.get(position));
                            competitionListAdapter.addEventBean(mList.get(position));
                            mList.get(position).setCheckbox(true);
                            itemType.set("personal");
                        } else {
                            eventsBeanArrayList.remove(mList.get(position));
                            mList.get(position).setCheckbox(false);
                            competitionListAdapter.deleteEventBean(mList.get(position));
                            itemType.set("");
                        }
                        notifyItemChanged(position);
                    }
                } else {
                    mList.get(position).setCheckbox(false);
                    toastUtƒil.showTopSnackBar("不能同时报名团体和个人赛哦!");
                    notifyItemChanged(position);
                }

            }
        });

    }


}
