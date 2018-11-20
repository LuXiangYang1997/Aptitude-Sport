package com.huasport.smartsport.ui.matchapply.view;

import android.support.v7.widget.LinearLayoutManager;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityGroupwaitpayBinding;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplyMsgAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplySuccessAdapter;
import com.huasport.smartsport.ui.matchapply.vm.GroupApplyWaitPayVM;


public class GroupApplyWaitPayActivity extends BaseActivity<ActivityGroupwaitpayBinding, GroupApplyWaitPayVM> {

    private GroupApplyWaitPayVM groupApplyWaitPayVM;
    private GroupApplySuccessAdapter groupapplySuccessAdapter;
    private GroupApplyMsgAdapter groupApplyMsgAdapter;

    @Override
    public int initContentView() {
        return R.layout.activity_groupwaitpay;
    }

    @Override
    public int initVariableId() {
        return BR.groupwaitPay;
    }

    @Override
    public GroupApplyWaitPayVM initViewModel() {

        groupapplySuccessAdapter = new GroupApplySuccessAdapter(this);
        groupApplyMsgAdapter = new GroupApplyMsgAdapter(this);
        groupApplyWaitPayVM = new GroupApplyWaitPayVM(this, groupapplySuccessAdapter, groupApplyMsgAdapter);

        return groupApplyWaitPayVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("待支付");
        back();
        binding.waitPayPersonalMessage.setLayoutManager(new LinearLayoutManager(this));
        binding.waitPayPersonalMessage.setAdapter(groupapplySuccessAdapter);

        binding.groupWaitpayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.groupWaitpayRecyclerView.setAdapter(groupApplyMsgAdapter);

    }
}
