package com.huasport.smartsport.ui.pcenter.medal.view;

import android.content.Intent;
import android.view.View;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.databinding.MedalPaysuccessLayoutBinding;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyOrderActivity;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;

public class MedalPaySuccessActivity extends BaseActivity<MedalPaysuccessLayoutBinding, BaseViewModel> implements View.OnClickListener {

    private Intent intent;
    private String type;
    private String successType;

    @Override
    public int initContentView() {
        return R.layout.medal_paysuccess_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public BaseViewModel initViewModel() {

        return new BaseViewModel();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("支付成功");
        successType = (String) SharedPreferencesUtil.getParam(this, "successType", "");
        toolbarBinding.llLeft.setOnClickListener(this);
        binding.lookupMoreOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(MedalPaySuccessActivity.this,PersonalMyOrderActivity.class);
                finish();
            }
        });

    }

    // 捕获返回键的方法2
    @Override
    public void onBackPressed() {
        if (successType.equals("ordercenter")) {
            intent = new Intent(this, PersonalMyOrderActivity.class);
        } else if (successType.equals("mymedal")){
            intent = new Intent(this, PersonalMyMedalActivity.class);
        }
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                if (successType.equals("ordercenter")) {
                    intent = new Intent(this, PersonalMyOrderActivity.class);
                } else {
                    intent = new Intent(this, PersonalMyMedalActivity.class);
                }
                startActivity(intent);
                finish();
                break;
        }

    }
}
