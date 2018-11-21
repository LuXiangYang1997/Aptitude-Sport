package com.huasport.smartsport.ui.pcenter.view;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.ActivityMymedalBinding;
import com.huasport.smartsport.ui.pcenter.adapter.PersonalMedalAdapter;
import com.huasport.smartsport.ui.pcenter.vm.PersonalMyMedalVM;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;


public class PersonalMyMedalActivity extends BaseActivity<ActivityMymedalBinding, PersonalMyMedalVM> implements View.OnClickListener {

    private PersonalMedalAdapter personalMedalAdapter;
    private PersonalMyMedalVM personalMyMedalVM;
    private String successType;

    @Override
    public int initContentView() {
        return R.layout.activity_mymedal;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public PersonalMyMedalVM initViewModel() {

        personalMedalAdapter = new PersonalMedalAdapter(this);
        personalMyMedalVM = new PersonalMyMedalVM(this, personalMedalAdapter);
        return personalMyMedalVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("我的奖章");
        toolbarBinding.llLeft.setOnClickListener(this);

        //设置适配器
        binding.myMedalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.myMedalRecyclerView.setAdapter(personalMedalAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        successType = (String) SharedPreferencesUtil.getParam(this, "successType", "");

        if (successType.equals("ordercenter")) {
            IntentUtil.finishPage(this, StatusVariable.PERSONALCENTE);
        } else if(successType.equals("mymedal")){
            IntentUtil.finishPage(this, StatusVariable.PERSONALCENTE);
        }else{
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                successType = (String) SharedPreferencesUtil.getParam(this, "successType", "");

                if (successType.equals("ordercenter")) {
                    IntentUtil.finishPage(this, StatusVariable.PERSONALCENTE);
                } else if(successType.equals("mymedal")){
                    IntentUtil.finishPage(this, StatusVariable.PERSONALCENTE);
                }else{
                    finish();
                }
                break;
        }


    }

}
