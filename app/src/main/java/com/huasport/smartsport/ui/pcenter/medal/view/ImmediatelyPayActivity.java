package com.huasport.smartsport.ui.pcenter.medal.view;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.MedalPayLayoutBinding;
import com.huasport.smartsport.ui.pcenter.medal.vm.MedalPayVm;


public class ImmediatelyPayActivity extends BaseActivity<MedalPayLayoutBinding, MedalPayVm> {


    private MedalPayVm medalPayVm;

    @Override
    public int initContentView() {
        return R.layout.medal_pay_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public MedalPayVm initViewModel() {

        medalPayVm = new MedalPayVm(this);

        return medalPayVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("立即支付");
        back();
    }
}
