package com.huasport.smartsport.ui.pcenter.view;

import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityAboutmyBinding;
import com.huasport.smartsport.ui.pcenter.vm.PersonalAboutMyVM;


public class PersionAboutMyActivity extends BaseActivity<ActivityAboutmyBinding, PersonalAboutMyVM> {


    private PersonalAboutMyVM personalAboutMyVM;

    @Override
    public int initContentView() {
        return R.layout.activity_aboutmy;
    }

    @Override
    public int initVariableId() {
        return BR.aboutMyVm;
    }

    @Override
    public PersonalAboutMyVM initViewModel() {

        personalAboutMyVM = new PersonalAboutMyVM(this);

        return personalAboutMyVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("关于我们");

        back();


    }
}
