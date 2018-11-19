package com.huasport.smartsport.ui.matchapply.view;

import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityProtocolBinding;
import com.huasport.smartsport.ui.matchapply.vm.ProtocolVM;


public class ProtocolActivity extends BaseActivity<ActivityProtocolBinding, ProtocolVM> implements View.OnClickListener {


    private ProtocolVM protocolVM;

    @Override
    public int initContentView() {
        return R.layout.activity_protocol;
    }

    @Override
    public int initVariableId() {
        return BR.protocolVm;
    }

    @Override
    public ProtocolVM initViewModel() {

        protocolVM = new ProtocolVM(this);
        return protocolVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("免责声明");

        toolbarBinding.llLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
