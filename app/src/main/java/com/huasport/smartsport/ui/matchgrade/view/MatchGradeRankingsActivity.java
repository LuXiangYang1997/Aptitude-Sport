package com.huasport.smartsport.ui.matchgrade.view;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.MatchgraderankingsLayoutBinding;
import com.huasport.smartsport.ui.matchgrade.adapter.MatchGradeRankingsListAdapter;
import com.huasport.smartsport.ui.matchgrade.vm.MatchGradeRankingVM;

public class MatchGradeRankingsActivity extends BaseActivity<MatchgraderankingsLayoutBinding, MatchGradeRankingVM> {


    private MatchGradeRankingVM matchGradeRankingVM;
    private MatchGradeRankingsListAdapter matchGradeRankingsListAdapter;

    @Override
    public int initContentView() {
        return R.layout.matchgraderankings_layout;
    }

    @Override
    public int initVariableId() {
        return BR.matchGradeRankVm;
    }

    @Override
    public MatchGradeRankingVM initViewModel() {

        matchGradeRankingsListAdapter = new MatchGradeRankingsListAdapter(this);

        matchGradeRankingVM = new MatchGradeRankingVM(this, matchGradeRankingsListAdapter);

        return matchGradeRankingVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        toolbarBinding.toolbar.setVisibility(View.GONE);

        binding.llLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        binding.recyclerViewRank.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewRank.setAdapter(matchGradeRankingsListAdapter);

        binding.recyclerViewRank.setPullRefreshEnabled(false);
        binding.recyclerViewRank.setLoadingMoreEnabled(false);

    }
}
