package com.example.bqj.aptitude_sport.ui.pcenter.loginbind.view;

import android.view.View;

import com.example.bqj.aptitude_sport.BR;
import com.example.bqj.aptitude_sport.R;
import com.example.bqj.aptitude_sport.base.BaseActivity;
import com.example.bqj.aptitude_sport.databinding.LoginLayoutBinding;
import com.example.bqj.aptitude_sport.ui.pcenter.loginbind.vm.LoginVm;

public class LoginActivity extends BaseActivity<LoginLayoutBinding, LoginVm> implements View.OnClickListener {


    private LoginVm loginVm;

    @Override
    public int initContentView() {
        return R.layout.login_layout;
    }

    @Override
    public int initVariableId() {
        return BR.loginVm;
    }

    @Override
    public LoginVm initViewModel() {

        loginVm = new LoginVm(this);

        return loginVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle(getResources().getString(R.string.login_str));
        toolbarBinding.llLeft.setOnClickListener(this);

    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                loginVm.back();
                break;
        }
    }
}
