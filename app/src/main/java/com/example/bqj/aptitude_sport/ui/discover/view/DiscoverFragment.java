package com.example.bqj.aptitude_sport.ui.discover.view;


import com.example.bqj.aptitude_sport.R;
import com.example.bqj.aptitude_sport.base.BaseFragment;
import com.example.bqj.aptitude_sport.databinding.DiscoverLayoutBinding;
import com.example.bqj.aptitude_sport.ui.discover.vm.DiscoverVm;

public class DiscoverFragment extends BaseFragment<DiscoverLayoutBinding,DiscoverVm> {


    @Override
    public int initContentView() {
        return R.layout.discover_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public DiscoverVm initViewModel() {

        DiscoverVm discoverVm=new DiscoverVm();

        return discoverVm;
    }

    @Override
    protected void loadData() {

    }
}