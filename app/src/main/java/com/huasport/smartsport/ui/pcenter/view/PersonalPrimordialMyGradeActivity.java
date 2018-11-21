package com.huasport.smartsport.ui.pcenter.view;


import android.support.v7.widget.LinearLayoutManager;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivityPersonalPrimordialMyGradeBinding;
import com.huasport.smartsport.ui.pcenter.adapter.PersonalPrimordialMyGradeAdapter;
import com.huasport.smartsport.ui.pcenter.vm.PersonalPrimordialMyGradeVM;


public class PersonalPrimordialMyGradeActivity extends BaseActivity<ActivityPersonalPrimordialMyGradeBinding, PersonalPrimordialMyGradeVM> {


    private PersonalPrimordialMyGradeVM personalPrimordialMyGradeVM;
    private PersonalPrimordialMyGradeAdapter personalPrimordialMyGradeAdapter;

    @Override
    public int initContentView() {
        return R.layout.activity_personal_primordial_my_grade;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public PersonalPrimordialMyGradeVM initViewModel() {

        personalPrimordialMyGradeAdapter = new PersonalPrimordialMyGradeAdapter(this);

        personalPrimordialMyGradeVM = new PersonalPrimordialMyGradeVM(this, personalPrimordialMyGradeAdapter);

        return personalPrimordialMyGradeVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("我的成绩");

        back();

        binding.myGradeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.myGradeRecyclerView.setAdapter(personalPrimordialMyGradeAdapter);

    }
}
