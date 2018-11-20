package com.huasport.smartsport.ui.matchapply.bean;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityGroupapplyBinding;
import com.huasport.smartsport.ui.matchapply.adapter.AccretionMemberAdapter;
import com.huasport.smartsport.ui.matchapply.vm.GroupApplyVM;


/**
 * Created by 陆向阳 on 2018/7/26.
 */

public class GroupApplyActivity extends BaseActivity<ActivityGroupapplyBinding, GroupApplyVM> implements View.OnClickListener {

    private AccretionMemberAdapter accretionMemberAdapter;
    private GroupApplyVM groupApplyVM;


    @Override
    public int initContentView() {
        return R.layout.activity_groupapply;
    }

    @Override
    public int initVariableId() {
        return BR.groupapplyVM;
    }

    @Override
    public GroupApplyVM initViewModel() {

        accretionMemberAdapter = new AccretionMemberAdapter(this);

        groupApplyVM = new GroupApplyVM(this, accretionMemberAdapter);

        return groupApplyVM;
    }

    @SuppressLint("InflateParams")
    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("填写报名表");
        setRightText("保存");
        toolbarBinding.llLeft.setOnClickListener(this);
        toolbarBinding.tvRight.setOnClickListener(this);


        binding.groupXrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.groupXrecyclerView.setAdapter(accretionMemberAdapter);
        binding.groupXrecyclerView.setLoadingMoreEnabled(false);
        binding.groupXrecyclerView.setPullRefreshEnabled(false);

        binding.scrollView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                groupApplyVM.SaveMsg();
                break;
        }
    }

    //更新删除数据
    public void update() {
        groupApplyVM.memberStatus.set(false);
        binding.groupMember.setText(accretionMemberAdapter.mList.size() + "");
    }
}
