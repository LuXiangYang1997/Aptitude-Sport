package com.huasport.smartsport.ui.matchapply.view;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityGroupwaitperfectBinding;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplyMsgAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.GroupMemberMsgAdapter;
import com.huasport.smartsport.ui.matchapply.vm.GroupApplyWaitPerfectVM;


public class GroupApplyWaitPerfectActivity extends BaseActivity<ActivityGroupwaitperfectBinding, GroupApplyWaitPerfectVM> {

    private GroupApplyWaitPerfectVM groupApplyWaitPerfectVM;
    private GroupApplyMsgAdapter groupApplyMsgAdapter;
    private GroupMemberMsgAdapter groupMemberMsgAdapter;

    @Override
    public int initContentView() {
        return R.layout.activity_groupwaitperfect;
    }

    @Override
    public int initVariableId() {
        return BR.groupapplyfectVM;
    }


    @Override
    public GroupApplyWaitPerfectVM initViewModel() {

        groupApplyMsgAdapter = new GroupApplyMsgAdapter(this);

        groupMemberMsgAdapter = new GroupMemberMsgAdapter(this);

        groupApplyWaitPerfectVM = new GroupApplyWaitPerfectVM(this, groupApplyMsgAdapter, groupMemberMsgAdapter);
        return groupApplyWaitPerfectVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("待完善");
        back();

        binding.groupWaitperfectapplyMsgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.groupWaitperfectapplyMsgRecyclerView.setAdapter(groupApplyMsgAdapter);

        binding.groupWaitperfectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.groupWaitperfectRecyclerView.setAdapter(groupMemberMsgAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.recycler_line));
        binding.groupWaitperfectRecyclerView.addItemDecoration(divider);



    }

    //更新删除数据
    public void update() {

        binding.member.setText(groupMemberMsgAdapter.mList.size() + "");

    }
}
