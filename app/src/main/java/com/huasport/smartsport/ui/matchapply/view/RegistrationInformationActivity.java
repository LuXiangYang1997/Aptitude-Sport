package com.huasport.smartsport.ui.matchapply.view;


import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityRegistrationInformationBinding;
import com.huasport.smartsport.ui.matchapply.adapter.SignPersonalAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.SignUpAdapter;
import com.huasport.smartsport.ui.matchapply.vm.RegistrationInformationVM;


/**
 * 待完善
 * <p>
 * Created by 陆向阳 on 2018/6/26.
 */

public class RegistrationInformationActivity extends BaseActivity<ActivityRegistrationInformationBinding, RegistrationInformationVM> {


    private RegistrationInformationVM registrationInformationVM;
    private SignUpAdapter signUpAdapter;
    private SignPersonalAdapter signPersonalAdapter;

    @Override
    public int initContentView() {
        return R.layout.activity_registration_information;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public RegistrationInformationVM initViewModel() {
        signUpAdapter = new SignUpAdapter(this);
        signPersonalAdapter = new SignPersonalAdapter(this);
        registrationInformationVM = new RegistrationInformationVM(this,signUpAdapter,signPersonalAdapter);
        return registrationInformationVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("报名信息");
        back();
        binding.signRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.signRecyclerView.setAdapter(signUpAdapter);

        binding.personalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.personalRecyclerView.setAdapter(signPersonalAdapter);
            //分割线
//        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.recycler_line));
//        binding.personalRecyclerView.addItemDecoration(divider);



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
        binding.passportLayout.setVisibility(View.VISIBLE);
        binding.contractImage.setVisibility(View.VISIBLE);
        binding.contrarytext.setVisibility(View.VISIBLE);
        binding.fronttext.setText("请上传身份证正面");
        binding.contrarytext.setText("请上传身份证反面");
    }

    //证件类型是 护照
    public void passPort() {
        binding.contractImage.setVisibility(View.GONE);
        binding.contrarytext.setVisibility(View.GONE);
        binding.passportLayout.setVisibility(View.VISIBLE);
        binding.fronttext.setText("请上传护照");
    }

    //军官证
    public void certificate() {
        binding.contractImage.setVisibility(View.GONE);
        binding.contrarytext.setVisibility(View.GONE);
        binding.passportLayout.setVisibility(View.VISIBLE);
        binding.fronttext.setText("请上传军官证");
    }
}
