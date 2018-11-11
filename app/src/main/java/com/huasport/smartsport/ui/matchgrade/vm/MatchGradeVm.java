package com.huasport.smartsport.ui.matchgrade.vm;


import com.huasport.smartsport.MainActivity;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.ui.matchgrade.view.MatchGradeFragment;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;

public class MatchGradeVm extends BaseViewModel {

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