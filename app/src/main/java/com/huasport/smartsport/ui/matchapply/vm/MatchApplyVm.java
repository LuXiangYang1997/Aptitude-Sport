package com.huasport.smartsport.ui.matchapply.vm;


import com.huasport.smartsport.MainActivity;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.ui.matchapply.view.MatchApplyFragment;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;

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