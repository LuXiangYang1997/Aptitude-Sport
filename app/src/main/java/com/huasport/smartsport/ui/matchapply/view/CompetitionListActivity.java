package com.huasport.smartsport.ui.matchapply.view;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.MotionEvent;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityCompetitionListBinding;
import com.huasport.smartsport.ui.matchapply.adapter.CompetitionListAdapter;
import com.huasport.smartsport.ui.matchapply.vm.CompetitionListVM;
import com.huasport.smartsport.util.SpacesItemDecoration;


public class CompetitionListActivity extends BaseActivity<ActivityCompetitionListBinding, CompetitionListVM> implements View.OnClickListener {

    private CompetitionListVM competitionListVM;
    private CompetitionListAdapter competitionListAdapter;
    private int space = 30;//Recyclerview间距

    @Override
    public int initContentView() {
        return R.layout.activity_competition_list;
    }

    @Override
    public int initVariableId() {
        return BR.competitionListViewModel;
    }

    @Override
    public CompetitionListVM initViewModel() {

        competitionListAdapter = new CompetitionListAdapter(this);

        competitionListVM = new CompetitionListVM(this, competitionListAdapter);

        return competitionListVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle(getResources().getString(R.string.select_match_group));

        //组头recylerView
        binding.selectSiteRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.selectSiteRecycler.addItemDecoration(new SpacesItemDecoration(space));//设置间距
        binding.selectSiteRecycler.setAdapter(competitionListAdapter);
        binding.selectSiteRecycler.setLoadingMoreEnabled(false);
        binding.selectSiteRecycler.setPullRefreshEnabled(false);
        binding.selectSiteRecycler.setItemAnimator(null);//设置Item的动画为空

        RecyclerView.ItemAnimator animator = binding.selectSiteRecycler.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        binding.smartRefreshlayout.setEnableRefresh(true);

        binding.linearlayout.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                binding.linearlayout.setFocusable(true);
                binding.linearlayout.setFocusableInTouchMode(true);
                binding.linearlayout.requestFocus();
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

}
