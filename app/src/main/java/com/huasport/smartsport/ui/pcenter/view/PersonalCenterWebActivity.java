package com.huasport.smartsport.ui.pcenter.view;

import android.os.Build;
import android.support.annotation.RequiresApi;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityPersonalcenterwebBinding;
import com.huasport.smartsport.ui.pcenter.vm.PersonalCenterWebVM;


/**
 * Created by 陆向阳 on 2018/7/21.
 */

public class PersonalCenterWebActivity extends BaseActivity<ActivityPersonalcenterwebBinding, PersonalCenterWebVM> {

    private PersonalCenterWebVM personalCenterWebVM;

    @Override
    public int initContentView() {
        return R.layout.activity_personalcenterweb;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public PersonalCenterWebVM initViewModel() {

        personalCenterWebVM = new PersonalCenterWebVM(this);

        return personalCenterWebVM;
    }

    // 捕获返回键的方法
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("帮助与反馈");
        back();
    }

}
