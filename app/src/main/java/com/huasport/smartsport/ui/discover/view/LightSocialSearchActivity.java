package com.huasport.smartsport.ui.discover.view;

import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.LightsocialsearchLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.LightSocialSearchAdapter;
import com.huasport.smartsport.ui.discover.vm.LightSocailSearchVm;

public class LightSocialSearchActivity extends BaseActivity<LightsocialsearchLayoutBinding, LightSocailSearchVm> {

    private LightSocailSearchVm lightSocailSearchVm;
    private LightSocialSearchAdapter lightSocialSearchAdapter;
    public ObservableField<String> refreshStatus=new ObservableField<>("normal");

    @Override
    public int initContentView() {
        return R.layout.lightsocialsearch_layout;
    }

    @Override
    public int initVariableId() {
        return BR.lightsocialsearch;
    }

    @Override
    public LightSocailSearchVm initViewModel() {
        lightSocialSearchAdapter = new LightSocialSearchAdapter(this);
        lightSocailSearchVm = new LightSocailSearchVm(this, lightSocialSearchAdapter);
        return lightSocailSearchVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        toolbarBinding.toolbar.setVisibility(View.GONE);
        binding.lightsocialSearch.setLayoutManager(new LinearLayoutManager(this));
        binding.lightsocialSearch.setAdapter(lightSocialSearchAdapter);
    }

}
