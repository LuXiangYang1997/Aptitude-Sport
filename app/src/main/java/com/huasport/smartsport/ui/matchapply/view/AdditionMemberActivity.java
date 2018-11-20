package com.huasport.smartsport.ui.matchapply.view;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.AdditionmemberLayoutBinding;
import com.huasport.smartsport.ui.matchapply.adapter.GroupFormAdapter;
import com.huasport.smartsport.ui.matchapply.vm.AdditionMemberVM;


/**
 * Created by 陆向阳 on 2018/7/26.
 */

public class AdditionMemberActivity extends BaseActivity<AdditionmemberLayoutBinding, AdditionMemberVM> {


    private AdditionMemberVM additionMemberVM;
    private GroupFormAdapter formAdapter;

    @Override
    public int initContentView() {
        return R.layout.additionmember_layout;
    }

    @Override
    public int initVariableId() {
        return BR.additionMemberVm;
    }

    @Override
    public AdditionMemberVM initViewModel() {
        formAdapter = new GroupFormAdapter(this);
        additionMemberVM = new AdditionMemberVM(this, formAdapter);

        return additionMemberVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
//        SoftHideKeyBoardUtil.assistActivity(this);


        setTitle("添加团队成员");

        back();

        binding.groupformRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.groupformRecyclerView.setAdapter(formAdapter);

    }

    //证件类型 是身份证
    public void idCard() {
        binding.llGroupOtherMsgLayout.setVisibility(View.VISIBLE);
        binding.groupContraryImg.setVisibility(View.VISIBLE);
        binding.contrarytext.setVisibility(View.VISIBLE);
        binding.fronttext.setText("请上传身份证正面");
        binding.contrarytext.setText("请上传身份证反面");
    }

    //证件类型是 护照
    public void passPort() {
        binding.groupContraryImg.setVisibility(View.GONE);
        binding.contrarytext.setVisibility(View.GONE);
        binding.llGroupOtherMsgLayout.setVisibility(View.VISIBLE);
        binding.fronttext.setText("请上传护照");
    }

    //军官证
    public void certificate() {
        binding.groupContraryImg.setVisibility(View.GONE);
        binding.contrarytext.setVisibility(View.GONE);
        binding.llGroupOtherMsgLayout.setVisibility(View.VISIBLE);
        binding.fronttext.setText("请上传军官证");
    }

}
