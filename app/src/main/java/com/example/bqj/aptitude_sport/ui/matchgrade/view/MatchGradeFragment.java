package com.example.bqj.aptitude_sport.ui.matchgrade.view;


import com.example.bqj.aptitude_sport.R;

import com.example.bqj.aptitude_sport.base.BaseFragment;
import com.example.bqj.aptitude_sport.databinding.MatchGradeLayoutBinding;
import com.example.bqj.aptitude_sport.ui.matchgrade.vm.MatchGradeVm;

public class MatchGradeFragment extends BaseFragment<MatchGradeLayoutBinding, MatchGradeVm> {


    @Override
    public int initContentView() {
        return R.layout.match_grade_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public MatchGradeVm initViewModel() {

        MatchGradeVm matchGradeVm=new MatchGradeVm();

        return matchGradeVm;
    }

    @Override
    protected void loadData() {

    }
}