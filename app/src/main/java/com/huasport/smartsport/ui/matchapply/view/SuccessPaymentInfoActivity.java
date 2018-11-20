package com.huasport.smartsport.ui.matchapply.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.ActivitySuccessPaymentBinding;
import com.huasport.smartsport.ui.matchapply.adapter.RegitrationSuccessAdapter;
import com.huasport.smartsport.ui.matchapply.vm.SuccessPaymentInfoViewModel;
import com.huasport.smartsport.util.IntentUtil;


/**
 * 待支付、支付成功
 * <p>
 * Created by 陆向阳 on 2018/6/28.
 */

public class SuccessPaymentInfoActivity extends BaseActivity<ActivitySuccessPaymentBinding, SuccessPaymentInfoViewModel> implements View.OnClickListener {

    private SuccessPaymentInfoViewModel mViewModel;
    private RegitrationSuccessAdapter regitrationSuccessAdapter;
    private String orderStatus;

    @Override
    public int initContentView() {
        return R.layout.activity_success_payment;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SuccessPaymentInfoViewModel initViewModel() {

        regitrationSuccessAdapter = new RegitrationSuccessAdapter(this);

        mViewModel = new SuccessPaymentInfoViewModel(this, binding.signRecyclerView, binding.personalRecyclerView, regitrationSuccessAdapter);

        return mViewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("报名信息");
        toolbarBinding.llLeft.setOnClickListener(this);

        binding.applyCardRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.applyCardRecyclerview.setAdapter(regitrationSuccessAdapter);

        Intent intent = getIntent();
        orderStatus = intent.getStringExtra("orderType");

    }

    /**
     * 待支付
     */
    public void stayPaymentStatus() {
        binding.llInfoRegistrationCard.setVisibility(View.GONE);//报名卡
        binding.llInfoBottom.setVisibility(View.VISIBLE);//底部按钮
        binding.successlayout.setVisibility(View.GONE);
    }

    /**
     * 已成功
     */
    public void successStatus() {
        binding.llInfoRegistrationCard.setVisibility(View.VISIBLE);//报名卡
        binding.llInfoBottom.setVisibility(View.GONE);//底部按钮
        binding.successlayout.setVisibility(View.VISIBLE);
    }

    //证件类型 是身份证
    public void idCard() {
        binding.otherMsgLayout.setVisibility(View.VISIBLE);
        binding.contraryImgPer.setVisibility(View.VISIBLE);
        binding.contrarytext.setVisibility(View.VISIBLE);
        binding.fronttext.setText("");
        binding.contrarytext.setText("");
    }

    //证件类型是 护照
    public void passPort() {
        binding.contraryImgPer.setVisibility(View.GONE);
        binding.contrarytext.setVisibility(View.GONE);
        binding.otherMsgLayout.setVisibility(View.VISIBLE);
        binding.fronttext.setText("");
    }

    //军官证
    public void certificate() {
        binding.contraryImgPer.setVisibility(View.GONE);
        binding.contrarytext.setVisibility(View.GONE);
        binding.otherMsgLayout.setVisibility(View.VISIBLE);
        binding.fronttext.setText("");
    }

    // 捕获返回键的方法
    @Override
    public void onBackPressed() {
        if (orderStatus.equals("success")) {
            IntentUtil.finishPage(this, StatusVariable.MATCHAPPLYCODE);
        } else {
            finish();
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                if (orderStatus.equals("success")) {
                    IntentUtil.finishPage(this, StatusVariable.MATCHAPPLYCODE);
                } else {
                   finish();
                }
                break;
        }
    }

}
