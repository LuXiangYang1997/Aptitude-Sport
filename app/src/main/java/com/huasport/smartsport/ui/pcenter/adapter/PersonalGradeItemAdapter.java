package com.huasport.smartsport.ui.pcenter.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.PersonalgradeItemlayoutBinding;
import com.huasport.smartsport.ui.pcenter.bean.PersonalPrimordialMyGradeBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalPrimordialMyGradeActivity;
import com.huasport.smartsport.ui.pcenter.view.PersonalScoreCardAvtivity;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;

import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.Util;

public class PersonalGradeItemAdapter extends BaseAdapter<PersonalPrimordialMyGradeBean.ResultBean.ListBean.ComListBean, BaseViewHolder> {

    private PersonalPrimordialMyGradeActivity personalPrimordialMyGradeActivity;

    public PersonalGradeItemAdapter(PersonalPrimordialMyGradeActivity personalPrimordialMyGradeActivity) {
        super(personalPrimordialMyGradeActivity);
        this.personalPrimordialMyGradeActivity = personalPrimordialMyGradeActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        PersonalgradeItemlayoutBinding personalgradeItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(personalPrimordialMyGradeActivity), R.layout.personalgrade_itemlayout, parent, false);

        return new BaseViewHolder(personalgradeItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {

        PersonalgradeItemlayoutBinding binding = (PersonalgradeItemlayoutBinding) baseViewHolder.getBinding();
        //对比赛组合比赛项进行非空判断赋值

        String area = mList.get(position).getCompetition().getAreaName();
        String group = (String) mList.get(position).getCompetition().getGroupName();
        String event = mList.get(position).getCompetition().getEventName();

        if (!EmptyUtil.isEmpty(area) && !EmptyUtil.isEmpty(group) && !EmptyUtil.isEmpty(event)) {

            binding.meName.setText(area + "-" + group + "-" + event);

        } else if (EmptyUtil.isEmpty(area) && !EmptyUtil.isEmpty(group) && !EmptyUtil.isEmpty(event)) {
            binding.meName.setText(group + "-" + event);
        } else if (!EmptyUtil.isEmpty(area) && EmptyUtil.isEmpty(group) && !EmptyUtil.isEmpty(event)) {
            binding.meName.setText(area + "-" + event);
        } else if (!EmptyUtil.isEmpty(area) && EmptyUtil.isEmpty(group) && EmptyUtil.isEmpty(event)) {
            binding.meName.setText(area + "-" + group);
        }
        //日期判断
        if (!EmptyUtil.isEmpty(mList.get(position).getDate())) {

            String timeDate = DateUtil.timeToDate(mList.get(position).getDate());

            binding.matchDate.setText(timeDate);
        }
        //比赛图片的url
        if (!EmptyUtil.isEmpty(mList.get(position).getCompetition().getMatchUrl())) {
            GlideUtil.LoadImage(personalPrimordialMyGradeActivity, mList.get(position).getCompetition().getMatchUrl(), binding.matchImg);
        }
        //成绩
        if (!EmptyUtil.isEmpty(mList.get(position).getScore())) {

            binding.matchscore.setText(mList.get(position).getScore());

        }


        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personalPrimordialMyGradeActivity, PersonalScoreCardAvtivity.class);
                intent.putExtra("CompetitionCode", mList.get(position).getCompetition().getCompetitionCode());
                personalPrimordialMyGradeActivity.startActivity(intent);
            }
        });


    }
}
