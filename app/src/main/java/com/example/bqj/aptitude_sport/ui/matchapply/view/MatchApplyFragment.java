package com.example.bqj.aptitude_sport.ui.matchapply.view;


import com.example.bqj.aptitude_sport.R;
import com.example.bqj.aptitude_sport.base.BaseFragment;
import com.example.bqj.aptitude_sport.databinding.MatchApplyLayoutBinding;
import com.example.bqj.aptitude_sport.ui.matchapply.vm.MatchApplyVm;

public class MatchApplyFragment extends BaseFragment<MatchApplyLayoutBinding,MatchApplyVm> {


    @Override
    public int initContentView() {
        return R.layout.match_apply_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public MatchApplyVm initViewModel() {

        MatchApplyVm matchApplyVm=new MatchApplyVm();

        return matchApplyVm;
    }

    @Override
    protected void loadData() {

    }
}