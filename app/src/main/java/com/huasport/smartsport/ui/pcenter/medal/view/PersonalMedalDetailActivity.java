package com.huasport.smartsport.ui.pcenter.medal.view;

import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.MedaldetailLayoutBinding;
import com.huasport.smartsport.ui.pcenter.medal.vm.PersonalMedalDetailVM;


public class PersonalMedalDetailActivity extends BaseActivity<MedaldetailLayoutBinding, PersonalMedalDetailVM> {


    @Override
    public int initContentView() {
        return R.layout.medaldetail_layout;
    }

    @Override
    public int initVariableId() {
        return BR.medaldetailVm;
    }

    @Override
    public PersonalMedalDetailVM initViewModel() {

        PersonalMedalDetailVM personalMedalDetailVM = new PersonalMedalDetailVM(this);

        return personalMedalDetailVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("奖章详情");
        back();
    }
}
