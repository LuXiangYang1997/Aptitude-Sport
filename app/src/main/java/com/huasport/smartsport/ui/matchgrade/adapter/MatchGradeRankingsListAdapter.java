package com.huasport.smartsport.ui.matchgrade.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.MatchgraderankingsItemlayoutBinding;
import com.huasport.smartsport.ui.matchgrade.bean.MatchScoreListBean;
import com.huasport.smartsport.ui.matchgrade.view.MatchGradeRankingsActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;


public class MatchGradeRankingsListAdapter extends BaseAdapter<MatchScoreListBean.ResultBean.ListBean, BaseViewHolder> {
    private MatchGradeRankingsActivity matchGradeRankingsActivity;
    private MatchgraderankingsItemlayoutBinding binding;
    public MatchGradeRankingsListAdapter(MatchGradeRankingsActivity matchGradeRankingsActivity) {
        super(matchGradeRankingsActivity);
        this.matchGradeRankingsActivity = matchGradeRankingsActivity;
    }
    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        MatchgraderankingsItemlayoutBinding matchgraderankingsItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(matchGradeRankingsActivity), R.layout.matchgraderankings_itemlayout, parent, false);
        return new BaseViewHolder(matchgraderankingsItemlayoutBinding);
    }
    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        binding = (MatchgraderankingsItemlayoutBinding) baseViewHolder.getBinding();
        if (position == mList.size() - 1) {
            binding.lineView.setVisibility(View.GONE);
        }
        if (!EmptyUtil.isEmpty(mList.get(position).getRank())) {
            if (mList.get(position).getRank() == StatusVariable.MATCHFIRST) {
                binding.tvRank.setVisibility(View.GONE);
                binding.imgRankscore.setVisibility(View.VISIBLE);
                binding.viewRank.setVisibility(View.GONE);
                binding.imgRankscore.setImageResource(R.mipmap.icon_jin);
            } else if (mList.get(position).getRank() == StatusVariable.MATCHSECOND) {
                binding.tvRank.setVisibility(View.GONE);
                binding.viewRank.setVisibility(View.GONE);
                binding.imgRankscore.setVisibility(View.VISIBLE);
                binding.imgRankscore.setImageResource(R.mipmap.icon_yin);
            } else if (mList.get(position).getRank() == StatusVariable.MATCHTHIRD) {
                binding.tvRank.setVisibility(View.GONE);
                binding.viewRank.setVisibility(View.GONE);
                binding.imgRankscore.setVisibility(View.VISIBLE);
                binding.imgRankscore.setImageResource(R.mipmap.icon_tong);
            } else {
                binding.viewRank.setVisibility(View.VISIBLE);
                binding.imgRankscore.setVisibility(View.GONE);
                binding.tvRank.setVisibility(View.VISIBLE);
                if (!EmptyUtil.isEmpty(mList.get(position).getRank())) {
                    binding.tvRank.setText(String.valueOf(mList.get(position).getRank()));
                }
            }
            if (!EmptyUtil.isEmpty(mList.get(position).getPlayerName())) {
                binding.tvUsername.setText(mList.get(position).getPlayerName());
            }
            if (!EmptyUtil.isEmpty(mList.get(position).getScore())) {
                binding.tvUserscore.setText(mList.get(position).getScore());
            }
            if (!EmptyUtil.isEmpty(mList.get(position).getPlayerHeaderImg())) {
                GlideUtil.LoadCircleImage(matchGradeRankingsActivity, (String) mList.get(position).getPlayerHeaderImg(), binding.imgHeader);
            } else {
                binding.imgHeader.setImageResource(R.mipmap.icon_defaultheader_yes);
            }
        }
    }
}