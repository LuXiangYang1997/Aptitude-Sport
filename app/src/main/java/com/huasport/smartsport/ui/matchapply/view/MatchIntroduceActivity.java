package com.huasport.smartsport.ui.matchapply.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityMatchIntroduceBinding;
import com.huasport.smartsport.ui.matchapply.adapter.MatchAdapter;
import com.huasport.smartsport.ui.matchapply.vm.MatchIntroduceVM;


public class MatchIntroduceActivity extends BaseActivity<ActivityMatchIntroduceBinding, MatchIntroduceVM> implements View.OnClickListener {

    private MatchIntroduceVM matchIntroduceVM;
    private MatchAdapter matchAdapter;

    @Override
    public int initContentView() {
        return R.layout.activity_match_introduce;
    }

    @Override
    public int initVariableId() {
        return BR.matchIntroduceViewModel;
    }

    @Override
    public MatchIntroduceVM initViewModel() {

        matchAdapter = new MatchAdapter(this);

        matchIntroduceVM = new MatchIntroduceVM(this, matchAdapter);
        return matchIntroduceVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle(getResources().getString(R.string.match_introduce));
        toolbarBinding.imgRight.setImageResource(R.mipmap.icon_match_share);
        toolbarBinding.llLeft.setOnClickListener(this);
        toolbarBinding.imgRight.setOnClickListener(this);
        binding.matchIndroduceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.matchIndroduceRecyclerView.setAdapter(matchAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.img_right:
                matchIntroduceVM.share();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        matchIntroduceVM.setSinaWbCallBack(intent);
    }
}
