package com.huasport.smartsport.ui.pcenter.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.PersonalprimordialmygradeItemlayoutBinding;
import com.huasport.smartsport.ui.pcenter.bean.PersonalPrimordialMyGradeBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalPrimordialMyGradeActivity;
import com.huasport.smartsport.util.EmptyUtil;


public class PersonalPrimordialMyGradeAdapter extends BaseAdapter<PersonalPrimordialMyGradeBean.ResultBean.ListBean, BaseViewHolder> {

    private PersonalPrimordialMyGradeActivity personalPrimordialMyGradeActivity;
    private PersonalprimordialmygradeItemlayoutBinding personalprimordialmygradeItemlayoutBinding;

    public PersonalPrimordialMyGradeAdapter(PersonalPrimordialMyGradeActivity personalPrimordialMyGradeActivity) {
        super(personalPrimordialMyGradeActivity);
        this.personalPrimordialMyGradeActivity = personalPrimordialMyGradeActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        personalprimordialmygradeItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(personalPrimordialMyGradeActivity), R.layout.personalprimordialmygrade_itemlayout, parent, false);

        return new BaseViewHolder(personalprimordialmygradeItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {

        PersonalprimordialmygradeItemlayoutBinding personalprimordialmygradeItemlayoutBinding = (PersonalprimordialmygradeItemlayoutBinding) baseViewHolder.getBinding();

        if (!EmptyUtil.isEmpty(mList.get(position).getMatch().getMatchName())) {
            personalprimordialmygradeItemlayoutBinding.matchName.setText(mList.get(position).getMatch().getMatchName());
        }

        PersonalGradeItemAdapter personalGradeItemAdapter = new PersonalGradeItemAdapter(personalPrimordialMyGradeActivity);
        personalprimordialmygradeItemlayoutBinding.gradeRecyclerView.setLayoutManager(new LinearLayoutManager(personalPrimordialMyGradeActivity));
        personalprimordialmygradeItemlayoutBinding.gradeRecyclerView.setAdapter(personalGradeItemAdapter);
        personalGradeItemAdapter.loadData(mList.get(position).getComList());
    }
}
