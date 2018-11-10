package com.example.bqj.aptitude_sport.ui.matchapply.vm;

import android.content.Intent;

import com.example.bqj.aptitude_sport.base.BaseViewModel;
import com.example.bqj.aptitude_sport.ui.matchapply.view.MatchApplyFragment;
import com.example.bqj.aptitude_sport.ui.pcenter.loginbind.view.LoginActivity;
import com.example.bqj.aptitude_sport.util.IntentUtil;

public class MatchApplyVm extends BaseViewModel {

    private MatchApplyFragment matchApplyFragment;

    public MatchApplyVm(MatchApplyFragment matchApplyFragment) {
        this.matchApplyFragment = matchApplyFragment;
    }


    /**
     * 头像点击事件
     */
    public void headerView() {

        IntentUtil.startActivity(matchApplyFragment.getActivity(),LoginActivity.class);

    }


}