package com.huasport.smartsport.ui.matchapply.view;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityFillRegistrationFormBinding;
import com.huasport.smartsport.ui.discover.adapter.FormAdapter;
import com.huasport.smartsport.ui.matchapply.vm.FillRegistrationFormVM;


public class FillRegistrationFormActivity extends BaseActivity<ActivityFillRegistrationFormBinding, FillRegistrationFormVM> implements View.OnClickListener {


    private FillRegistrationFormVM fillRegistrationFormVM;
    private FormAdapter formAdapter;

    @Override
    public int initContentView() {
        return R.layout.activity_fill_registration_form;
    }

    @Override
    public int initVariableId() {
        return BR.fillregistrationformVM;
    }

    @Override
    public FillRegistrationFormVM initViewModel() {

        formAdapter = new FormAdapter(this);

        fillRegistrationFormVM = new FillRegistrationFormVM(this, formAdapter);

        return fillRegistrationFormVM;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("填写报名表");
        setRightText("保存");

        toolbarBinding.tvRight.setOnClickListener(this);
        toolbarBinding.llLeft.setOnClickListener(this);
        binding.formRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.formRecyclerView.setAdapter(formAdapter);
        //解决滑动问题
        binding.scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        binding.scrollView.setFocusable(true);
        binding.scrollView.setFocusableInTouchMode(true);
        binding.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });

    }

    //证件类型 是身份证
    public void idCard() {
        binding.otherMsgLayout.setVisibility(View.VISIBLE);
        binding.contraryImg.setVisibility(View.VISIBLE);
        binding.contrarytext.setVisibility(View.VISIBLE);
        binding.fronttext.setText("请上传身份证正面");
        binding.contrarytext.setText("请上传身份证反面");
    }

    //证件类型是 护照
    public void passPort() {
        binding.contraryImg.setVisibility(View.GONE);
        binding.contrarytext.setVisibility(View.GONE);
        binding.otherMsgLayout.setVisibility(View.VISIBLE);
        binding.fronttext.setText("请上传护照");
    }

    //军官证
    public void certificate() {
        binding.contraryImg.setVisibility(View.GONE);
        binding.contrarytext.setVisibility(View.GONE);
        binding.otherMsgLayout.setVisibility(View.VISIBLE);
        binding.fronttext.setText("请上传军官证");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                fillRegistrationFormVM.saveMsg();
                break;
        }
    }


}