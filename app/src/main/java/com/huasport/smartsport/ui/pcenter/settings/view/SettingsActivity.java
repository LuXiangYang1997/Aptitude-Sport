package com.huasport.smartsport.ui.pcenter.settings.view;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.SettingsLayoutBinding;
import com.huasport.smartsport.ui.pcenter.settings.vm.SettingsVM;

public class SettingsActivity extends BaseActivity<SettingsLayoutBinding, SettingsVM> {


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
        back();
    }
}
