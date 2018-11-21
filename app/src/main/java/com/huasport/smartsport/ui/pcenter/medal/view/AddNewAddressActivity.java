package com.huasport.smartsport.ui.pcenter.medal.view;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.AddnewaddressLayoutBinding;
import com.huasport.smartsport.ui.pcenter.medal.vm.AddNewAddressVM;

import org.json.JSONException;

public class AddNewAddressActivity extends BaseActivity<AddnewaddressLayoutBinding,AddNewAddressVM> {


    private AddNewAddressVM addNewAddressVM;

    @Override
    public int initContentView() {
        return R.layout.addnewaddress_layout;
    }

    @Override
    public int initVariableId() {
        return BR.addnewAddressVm;
    }

    @Override
    public AddNewAddressVM initViewModel(){
        try {
            addNewAddressVM = new AddNewAddressVM(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return addNewAddressVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        String addressType = getIntent().getStringExtra("addressType");
        if (addressType.equals("modifyAddress")) {
           setTitle("修改新地址");
        } else {
            setTitle("添加新地址");
        }
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
        back();
    }
}
