package com.huasport.smartsport.ui.pcenter.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import com.android.databinding.library.baseAdapters.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.PersonalscoreLayoutBinding;
import com.huasport.smartsport.ui.pcenter.adapter.PersonalRankingAdapter;
import com.huasport.smartsport.ui.pcenter.vm.PersonalScoreCardVM;


public class PersonalScoreCardAvtivity extends BaseActivity<PersonalscoreLayoutBinding, PersonalScoreCardVM> {

    private PersonalScoreCardVM personalScoreCardVM;
    private PersonalRankingAdapter personalRankingAdapter;

    @Override
    public int initContentView() {
        return R.layout.personalscore_layout;
    }

    @Override
    public int initVariableId() {
        return BR.personalscoreVm;
    }

    @Override
    public PersonalScoreCardVM initViewModel() {

        personalRankingAdapter = new PersonalRankingAdapter(this);

        personalScoreCardVM = new PersonalScoreCardVM(this, personalRankingAdapter);

        return personalScoreCardVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("我的成绩");

        binding.gradecardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.gradecardRecyclerView.setAdapter(personalRankingAdapter);
    }
    //新浪微博回调
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        personalScoreCardVM.setSinaWbCallBack(intent);
    }
}
