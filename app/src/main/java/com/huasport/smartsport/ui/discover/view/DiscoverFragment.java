package com.huasport.smartsport.ui.discover.view;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseFragment;
import com.huasport.smartsport.databinding.DiscoverLayoutBinding;
import com.huasport.smartsport.ui.discover.vm.DiscoverVm;

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