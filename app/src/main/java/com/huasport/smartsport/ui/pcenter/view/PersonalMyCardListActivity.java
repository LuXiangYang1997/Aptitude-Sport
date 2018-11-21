package com.huasport.smartsport.ui.pcenter.view;

import android.support.v7.widget.LinearLayoutManager;
import com.huasport.smartsport.BR;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.PersonalmycardlistLayoutBinding;
import com.huasport.smartsport.ui.pcenter.adapter.PersonalMyCardListAdapter;
import com.huasport.smartsport.ui.pcenter.vm.PersonalMycardListVM;


public class PersonalMyCardListActivity extends BaseActivity<PersonalmycardlistLayoutBinding, PersonalMycardListVM> {


    private PersonalMycardListVM personalMycardListVM;
    private PersonalMyCardListAdapter personalMyCardListAdapter;

    @Override
    public int initContentView() {
        return R.layout.personalmycardlist_layout;
    }

    @Override
    public int initVariableId() {
        return BR.personalMyApplyCardListVM;
    }

    @Override
    public PersonalMycardListVM initViewModel() {

        personalMyCardListAdapter = new PersonalMyCardListAdapter(this);

        personalMycardListVM = new PersonalMycardListVM(this, personalMyCardListAdapter);

        return personalMycardListVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        MyApplication.addActivity(this);

        setTitle("我的报名卡");
        back();
        binding.mycardlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.mycardlistRecyclerView.setAdapter(personalMyCardListAdapter);
    }
}
