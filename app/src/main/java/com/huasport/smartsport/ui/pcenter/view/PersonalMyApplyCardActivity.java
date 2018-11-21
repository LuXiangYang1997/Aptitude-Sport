package com.huasport.smartsport.ui.pcenter.view;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import com.huasport.smartsport.BR;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.PersonalApplycardLayoutBinding;
import com.huasport.smartsport.ui.pcenter.vm.PersonalMyApplyCardVM;
import com.huasport.smartsport.util.Util;

public class PersonalMyApplyCardActivity extends BaseActivity<PersonalApplycardLayoutBinding, PersonalMyApplyCardVM> {

    private PersonalMyApplyCardVM personalMyApplyCardVM;

    @Override
    public int initContentView() {
        return R.layout.personal_applycard_layout;
    }

    @Override
    public int initVariableId() {
        return BR.personalMyApplyCardVM;
    }

    @Override
    public PersonalMyApplyCardVM initViewModel() {

        personalMyApplyCardVM = new PersonalMyApplyCardVM(this);

        return personalMyApplyCardVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        MyApplication.addActivity(this);

        setTitle("我的报名卡");
        back();
        Util.setEditTextInhibitInputSpace(binding.cardRealName);//空格
        Util.setEditTextInhibitInputSpaChat(binding.cardRealName);//特殊字符
        //对特殊符号做处理，只允许添加文字，数字，英文，长度不能超过12
        binding.cardRealName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = binding.cardRealName.getText().toString();
                String str = Util.stringFilter(editable.toString());

                if (str.length() > 11) {
                    binding.cardRealName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(str.length())});
                }
                if (!editable.equals(str)) {
                    binding.cardRealName.setText(str);
                    //设置新的光标所在位置
                    binding.cardRealName.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
