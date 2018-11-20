package com.huasport.smartsport.ui.pcenter.approve.view;

import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.PcenterapproveLayoutBinding;
import com.huasport.smartsport.ui.pcenter.approve.vm.PcenterApproveVm;


public class PcenterApproveActivity extends BaseActivity<PcenterapproveLayoutBinding, PcenterApproveVm> {

    private PcenterApproveVm pcenterApproveVm;

    @Override
    public int initContentView() {
        return R.layout.pcenterapprove_layout;
    }

    @Override
    public int initVariableId() {
        return BR.pcenterApproveVm;
    }

    @Override
    public PcenterApproveVm initViewModel() {

        pcenterApproveVm = new PcenterApproveVm(this);

        return pcenterApproveVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("个人认证");
        back();

    }
}
