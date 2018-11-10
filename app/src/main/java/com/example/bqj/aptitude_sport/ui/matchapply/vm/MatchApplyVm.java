package com.example.bqj.aptitude_sport.ui.matchapply.vm;

import android.content.Intent;

import com.example.bqj.aptitude_sport.MainActivity;
import com.example.bqj.aptitude_sport.MyApplication;
import com.example.bqj.aptitude_sport.base.BaseViewModel;
import com.example.bqj.aptitude_sport.constant.StatusVariable;
import com.example.bqj.aptitude_sport.ui.matchapply.view.MatchApplyFragment;
import com.example.bqj.aptitude_sport.ui.pcenter.loginbind.view.LoginActivity;
import com.example.bqj.aptitude_sport.util.EmptyUtil;
import com.example.bqj.aptitude_sport.util.IntentUtil;

public class MatchApplyVm extends BaseViewModel {

    private MatchApplyFragment matchApplyFragment;
    private boolean loginState;

    public MatchApplyVm(MatchApplyFragment matchApplyFragment) {
        this.matchApplyFragment = matchApplyFragment;
    }


    /**
     * 头像点击事件
     */
    public void headerView() {

        if (!loginState){
            MyApplication.getInstance().setClickState(true);
           IntentUtil.startActivityForResult(matchApplyFragment.getActivity(),LoginActivity.class);
           return;
        }
        MainActivity activity = (MainActivity) matchApplyFragment.getActivity();
        if (!EmptyUtil.isEmpty(activity)){
            activity.tabState(StatusVariable.PERSONALCENTE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loginState = MyApplication.getInstance().getLoginState();
        matchApplyFragment.initUserInfo();

    }
}