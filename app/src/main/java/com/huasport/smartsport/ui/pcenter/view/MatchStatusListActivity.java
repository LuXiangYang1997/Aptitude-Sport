package com.huasport.smartsport.ui.pcenter.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.ActivityMatchstatuslistBinding;
import com.huasport.smartsport.ui.pcenter.adapter.MyRegistrationAdapter;
import com.huasport.smartsport.ui.pcenter.vm.MatchStatusListVM;
import com.scwang.smartrefresh.layout.impl.ScrollBoundaryDeciderAdapter;

/**
 * Created by 陆向阳 on 2018/7/21.
 */

public class MatchStatusListActivity extends BaseActivity<ActivityMatchstatuslistBinding, MatchStatusListVM> {

    private MyRegistrationAdapter myRegistrationAdapter;
    private MatchStatusListVM matchStatusListVM;
    private String listStatus;

    @Override
    public int initContentView() {
        return R.layout.activity_matchstatuslist;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public MatchStatusListVM initViewModel() {

        Intent intent = getIntent();
        listStatus = intent.getStringExtra("ListStatus");

        myRegistrationAdapter = new MyRegistrationAdapter(this);

        matchStatusListVM = new MatchStatusListVM(this, myRegistrationAdapter);

        return matchStatusListVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        back();
        if (listStatus.isEmpty()) {//全部
           setTitle("全部比赛");
        } else if (listStatus.equals(StatusVariable.WAIT_PAY)) {//待支付
            setTitle("待支付");

        } else if (listStatus.equals(StatusVariable.WAIT_COMPLETE)) {//待完善
            setTitle("待完善");
        } else if (listStatus.equals(StatusVariable.ORDERSUCCESS)) {//成功
            setTitle("已成功");
        }
        binding.matchListXRecyclerView.setItemAnimator(null);//设置动画为null来解决闪烁问题
        binding.matchListXRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.matchListXRecyclerView.setAdapter(myRegistrationAdapter);
        binding.smartRefreshlayout.setScrollBoundaryDecider(new ScrollBoundaryDeciderAdapter());//自定义滚动边界

    }

}
