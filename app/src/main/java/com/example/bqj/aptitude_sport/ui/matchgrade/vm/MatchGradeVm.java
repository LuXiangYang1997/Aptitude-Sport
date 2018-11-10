package com.example.bqj.aptitude_sport.ui.matchgrade.vm;

import com.example.bqj.aptitude_sport.MainActivity;
import com.example.bqj.aptitude_sport.MyApplication;
import com.example.bqj.aptitude_sport.base.BaseViewModel;
import com.example.bqj.aptitude_sport.constant.StatusVariable;
import com.example.bqj.aptitude_sport.ui.matchgrade.view.MatchGradeFragment;
import com.example.bqj.aptitude_sport.ui.pcenter.loginbind.view.LoginActivity;
import com.example.bqj.aptitude_sport.util.EmptyUtil;
import com.example.bqj.aptitude_sport.util.IntentUtil;

public class MatchGradeVm extends BaseViewModel{

    private MatchGradeFragment matchGradeFragment;
    private boolean loginState;

    public MatchGradeVm(MatchGradeFragment matchGradeFragment) {
        this.matchGradeFragment=matchGradeFragment;
    }

    /**
     * 头像点击事件
     */
    public void matchGradeHeader(){

        if (!loginState){
            MyApplication.getInstance().setClickState(true);
            IntentUtil.startActivityForResult(matchGradeFragment.getActivity(),LoginActivity.class);
            return;
        }
        MainActivity activity = (MainActivity) matchGradeFragment.getActivity();
        if (!EmptyUtil.isEmpty(activity)){
            activity.tabState(StatusVariable.PERSONALCENTE);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        loginState = MyApplication.getInstance().getLoginState();
        matchGradeFragment.initUserInfo();
    }
}