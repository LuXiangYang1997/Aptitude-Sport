package com.huasport.smartsport.ui.pcenter.settings.view;

import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.SettingsLayoutBinding;
import com.huasport.smartsport.ui.pcenter.settings.vm.SettingsVM;

public class SettingsActivity extends BaseActivity<SettingsLayoutBinding, SettingsVM> implements View.OnClickListener {


    private SettingsVM settingsVM;

    @Override
    public int initContentView() {
        return R.layout.settings_layout;
    }

    @Override
    public int initVariableId() {
        return BR.settingVm;
    }

    @Override
    public SettingsVM initViewModel() {

        settingsVM = new SettingsVM(this);

        return settingsVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle(getResources().getString(R.string.setting));
        toolbarBinding.llLeft.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                setResult(-1);
                finish();
            break;

        }

    }
}
