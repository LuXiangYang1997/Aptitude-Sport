package com.huasport.smartsport.ui.pcenter.view;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.PersonalmygradedetailLayoutBinding;
import com.huasport.smartsport.ui.pcenter.adapter.MatchGradeDetailAdapter;
import com.huasport.smartsport.ui.pcenter.vm.PersonalMyGradeDetailVM;

public class PersonalMyGradeDetailActivity extends BaseActivity<PersonalmygradedetailLayoutBinding, PersonalMyGradeDetailVM> {

    private PersonalMyGradeDetailVM personalMyGradeDetailVM;
    private MatchGradeDetailAdapter matchGradeDetailAdapter;

    @Override
    public int initContentView() {
        return R.layout.personalmygradedetail_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public PersonalMyGradeDetailVM initViewModel() {

        matchGradeDetailAdapter = new MatchGradeDetailAdapter(this);

        personalMyGradeDetailVM = new PersonalMyGradeDetailVM(this, matchGradeDetailAdapter);

        return personalMyGradeDetailVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("成绩详情");
        back();
    }
}
