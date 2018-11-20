package com.huasport.smartsport.ui.pcenter.approve.view;

import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.huasport.smartsport.MainActivity;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.ApproveresultLayoutBinding;
import com.huasport.smartsport.ui.pcenter.approve.vm.ApproveResultVm;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;

public class ApproveResultActivity extends BaseActivity<ApproveresultLayoutBinding, ApproveResultVm> implements View.OnClickListener {

    private ApproveResultVm approveResultVm;
    private String type;

    @Override
    public int initContentView() {
        return R.layout.approveresult_layout;
    }

    @Override
    public int initVariableId() {
        return BR.approveResultVm;
    }

    @Override
    public ApproveResultVm initViewModel() {

        approveResultVm = new ApproveResultVm(this);

        return approveResultVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        String certType = getIntent().getStringExtra("certType");
        type = getIntent().getStringExtra("type");
        if (certType.equals("enterprise")) {
          setTitle("企业认证");
        } else {
            setTitle("个人认证");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                if (type.equals("approve")) {
                    SharedPreferencesUtil.setParam(this, "ActivityState", true);
                    IntentUtil.finishPage(this, StatusVariable.PERSONALCENTE);
                } else {
                   finish();
                }
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (type.equals("approve")) {
            SharedPreferencesUtil.setParam(this, "ActivityState", true);
            IntentUtil.finishPage(this, StatusVariable.PERSONALCENTE);
        } else {
            finish();
        }
    }
}
