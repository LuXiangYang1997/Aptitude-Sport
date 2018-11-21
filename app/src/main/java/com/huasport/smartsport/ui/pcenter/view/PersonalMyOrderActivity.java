package com.huasport.smartsport.ui.pcenter.view;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.PersonalMyorderLayoutBinding;
import com.huasport.smartsport.ui.pcenter.adapter.OrderCenterListAdapter;
import com.huasport.smartsport.ui.pcenter.vm.PersonalMyOrderVm;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;

public class PersonalMyOrderActivity extends BaseActivity<PersonalMyorderLayoutBinding, PersonalMyOrderVm> implements View.OnClickListener {

    private OrderCenterListAdapter orderCenterListAdapter;
    private PersonalMyOrderVm personalMyOrderVm;
    private String successType;

    @Override
    public int initContentView() {
        return R.layout.personal_myorder_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public PersonalMyOrderVm initViewModel() {

        orderCenterListAdapter = new OrderCenterListAdapter(this);
        personalMyOrderVm = new PersonalMyOrderVm(this, orderCenterListAdapter);

        return personalMyOrderVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("订单中心");

        toolbarBinding.llLeft.setOnClickListener(this);

        binding.pesonalOrderxrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.pesonalOrderxrecyclerView.setAdapter(orderCenterListAdapter);
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
