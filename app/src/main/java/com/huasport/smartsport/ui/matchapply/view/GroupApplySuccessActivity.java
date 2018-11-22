package com.huasport.smartsport.ui.matchapply.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.MainActivity;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.ActivityGroupapplysuccessBinding;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplyCardMsgAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplyMsgAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplySuccessAdapter;
import com.huasport.smartsport.ui.matchapply.vm.GroupApplySuccessVM;
import com.huasport.smartsport.util.IntentUtil;


/**
 * Created by 陆向阳 on 2018/7/26.
 */

public class GroupApplySuccessActivity extends BaseActivity<ActivityGroupapplysuccessBinding, GroupApplySuccessVM> implements View.OnClickListener {
    private GroupApplySuccessVM groupApplySuccessVM;
    private GroupApplySuccessAdapter groupApplySuccessAdapter;
    private GroupApplyMsgAdapter groupApplyMsgAdapter;
    private GroupApplyCardMsgAdapter groupApplyCardMsgAdapter;
    private String orderType;

    @Override
    public int initContentView() {
        return R.layout.activity_groupapplysuccess;
    }

    @Override
    public int initVariableId() {
        return BR.groupSuccessVm;
    }

    @Override
    public GroupApplySuccessVM initViewModel() {

        Intent intent = getIntent();
        orderType = intent.getStringExtra("orderType");

        groupApplySuccessAdapter = new GroupApplySuccessAdapter(this);//个人信息

        groupApplyMsgAdapter = new GroupApplyMsgAdapter(this);//报名信息

        //卡片
        groupApplyCardMsgAdapter = new GroupApplyCardMsgAdapter(this);

        groupApplySuccessVM = new GroupApplySuccessVM(this, groupApplySuccessAdapter, groupApplyMsgAdapter, groupApplyCardMsgAdapter);
        return groupApplySuccessVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("报名成功");
        toolbarBinding.llLeft.setOnClickListener(this);
        binding.groupPersonalMsgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.groupPersonalMsgRecyclerView.setAdapter(groupApplySuccessAdapter);

        binding.groupApplyMsgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.groupApplyMsgRecyclerView.setAdapter(groupApplyMsgAdapter);

        binding.groupCardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.groupCardRecyclerView.setAdapter(groupApplyCardMsgAdapter);
    }

    // 捕获返回键的方法
    @Override
    public void onBackPressed() {
        if (orderType.equals(StatusVariable.SUCCESSAPPLY)) {
            IntentUtil.startActivity(this,MainActivity.class);
            appManager.finishAllActivity();
        } else {
            finish();
        }
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                if (orderType.equals(StatusVariable.SUCCESSAPPLY)) {
                    IntentUtil.startActivity(this,MainActivity.class);
                    appManager.finishAllActivity();
                } else {
                  finish();
                }
                break;
        }


    }
}
