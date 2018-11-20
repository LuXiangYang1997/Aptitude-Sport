package com.huasport.smartsport.ui.pcenter.approve.view;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.PersonalApprovelayoutBinding;
import com.huasport.smartsport.ui.pcenter.approve.vm.ApproveVm;


public class ApproveActivity extends BaseActivity<PersonalApprovelayoutBinding, ApproveVm> {


    private ApproveVm approveVm;

    @Override
    public int initContentView() {
        return R.layout.personal_approvelayout;
    }

    @Override
    public int initVariableId() {
        return BR.approveVm;
    }

    @Override
    public ApproveVm initViewModel() {

        approveVm = new ApproveVm(this);

        return approveVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("认证");

        back();

        binding.approveTablayout.addTab(binding.approveTablayout.newTab().setText("个人"));
        binding.approveTablayout.addTab(binding.approveTablayout.newTab().setText("企业"));

        binding.approveTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tv_tab = tab.getText().toString();
                switch (tv_tab) {
                    //个人
                    case StatusVariable.PCENTERAPPROVE:
                        binding.tvNextstep.setText("下一步");
                        binding.llPcenterapprove.setVisibility(View.VISIBLE);
                        binding.llFirmapprove.setVisibility(View.GONE);
                        break;
                    //企业
                    case StatusVariable.FIRMAPPROVE:
                        binding.tvNextstep.setText("提交");
                        binding.llFirmapprove.setVisibility(View.VISIBLE);
                        binding.llPcenterapprove.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


}
