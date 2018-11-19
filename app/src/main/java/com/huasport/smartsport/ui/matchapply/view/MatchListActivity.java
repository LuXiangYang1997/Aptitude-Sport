package com.huasport.smartsport.ui.matchapply.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.base.IBaseView;
import com.huasport.smartsport.databinding.ActivityMatchListBinding;
import com.huasport.smartsport.ui.matchapply.adapter.MatchListRecyclerViewAdapter;
import com.huasport.smartsport.ui.matchapply.vm.MatchListVM;
import com.huasport.smartsport.util.NullStateUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by 陆向阳 on 2018/6/7.
 */

public class MatchListActivity extends BaseActivity<ActivityMatchListBinding, MatchListVM> implements View.OnClickListener{

    private MatchListRecyclerViewAdapter matchListRecyclerViewAdapter;
    private MatchListVM matchListVM;

    @Override
    public int initContentView() {
        return R.layout.activity_match_list;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public MatchListVM initViewModel() {

        matchListRecyclerViewAdapter = new MatchListRecyclerViewAdapter(this);

        matchListVM = new MatchListVM(this, matchListRecyclerViewAdapter);

        return matchListVM;
    }

    @SuppressLint("InflateParams")
    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle(getResources().getString(R.string.select_match));
        toolbarBinding.llLeft.setOnClickListener(this);
        //设置适配器
        binding.matchListXRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.matchListXRecyclerView.setAdapter(matchListRecyclerViewAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
