package com.huasport.smartsport.ui.pcenter.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.PersonalrankingItemlayoutBinding;
import com.huasport.smartsport.ui.pcenter.bean.PersonalCompetitionBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyGradeDetailActivity;
import com.huasport.smartsport.ui.pcenter.view.PersonalScoreCardAvtivity;
import com.huasport.smartsport.util.EmptyUtil;


/**
 * 我的成绩适配器
 */
public class PersonalRankingAdapter extends BaseAdapter<PersonalCompetitionBean.ResultBean.ScoreListBean, BaseViewHolder> {

    private PersonalScoreCardAvtivity personalScoreCardActivity;
    private PersonalrankingItemlayoutBinding personalrankingItemlayoutBinding;

    public PersonalRankingAdapter(PersonalScoreCardAvtivity personalScoreCardActivity) {
        super(personalScoreCardActivity);
        this.personalScoreCardActivity = personalScoreCardActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        personalrankingItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(personalScoreCardActivity), R.layout.personalranking_itemlayout, parent, false);

        return new BaseViewHolder(personalrankingItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        PersonalrankingItemlayoutBinding binding = (PersonalrankingItemlayoutBinding) baseViewHolder.getBinding();
        //对成绩和时间进行判空
        if (!EmptyUtil.isEmpty(mList.get(position).getScore())) {
            binding.rankingScore.setText(mList.get(position).getScore());
        }
        if (!EmptyUtil.isEmpty(mList.get(position).getTimeStr())) {
            binding.rankingTime.setText(mList.get(position).getTimeStr());
        }
        //点击到成绩详情
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(personalScoreCardActivity, PersonalMyGradeDetailActivity.class);
                intent.putExtra("competiotionCode", mList.get(position).getCompetitionCode());
                intent.putExtra("scoreDesc", mList.get(position).getScore());
                personalScoreCardActivity.startActivity(intent);
            }
        });


    }
}
