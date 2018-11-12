package com.huasport.smartsport.ui.pcenter.settings.view;

import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.PersonalMsgLayoutBinding;
import com.huasport.smartsport.ui.pcenter.settings.vm.PersonalMsgVm;

public class PersonalMsgActivity extends BaseActivity<PersonalMsgLayoutBinding, PersonalMsgVm> implements View.OnClickListener {

    private PersonalMsgVm personalMsgVm;

    @Override
    public int initContentView() {
        return R.layout.personal_msg_layout;
    }

    @Override
    public int initVariableId() {

        return BR.personalMessageVm;
    }

    @Override
    public PersonalMsgVm initViewModel() {

        personalMsgVm = new PersonalMsgVm(this);

        return personalMsgVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle(getResources().getString(R.string.personal_msg));
        setRightText(getResources().getString(R.string.save));
        back();
        toolbarBinding.tvRight.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                personalMsgVm.saveInfo();
                break;
        }
    }
}
