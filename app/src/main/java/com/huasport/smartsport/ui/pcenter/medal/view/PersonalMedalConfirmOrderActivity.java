package com.huasport.smartsport.ui.pcenter.medal.view;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ConfirmorderLayoutBinding;
import com.huasport.smartsport.ui.pcenter.medal.vm.PersonalConfirmOrderVM;

public class PersonalMedalConfirmOrderActivity extends BaseActivity<ConfirmorderLayoutBinding, PersonalConfirmOrderVM> {
    @Override
    public int initContentView() {
        return R.layout.confirmorder_layout;

    }

    @Override
    public int initVariableId() {

        return BR.confirmorderVm;
    }

    @Override
    public PersonalConfirmOrderVM initViewModel() {

        PersonalConfirmOrderVM personalConfirmOrderVM = new PersonalConfirmOrderVM(this);

        return personalConfirmOrderVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("确认订单");
        back();
        //解决滑动问题
        binding.cfirmScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        binding.cfirmScrollView.setFocusable(true);
        binding.cfirmScrollView.setFocusableInTouchMode(true);
        binding.cfirmScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }


}
