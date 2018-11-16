package com.huasport.smartsport.ui.discover.view;

import android.support.v7.widget.LinearLayoutManager;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseFragment;
import com.huasport.smartsport.databinding.DiscoverLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.RecommandAdapter;
import com.huasport.smartsport.ui.discover.adapter.SocialAdapter;
import com.huasport.smartsport.ui.discover.vm.DiscoverVm;

public class DiscoverFragment extends BaseFragment<DiscoverLayoutBinding,DiscoverVm> {


    private SocialAdapter socialAdapter;
    private RecommandAdapter recommandAdapter;

    @Override
    public int initContentView() {
        return R.layout.discover_layout;
    }

    @Override
    public int initVariableId() {
        return BR.discoverVm;
    }

    @Override
    public DiscoverVm initViewModel() {

        socialAdapter = new SocialAdapter(getActivity());

        recommandAdapter = new RecommandAdapter(getActivity());

        DiscoverVm discoverVm=new DiscoverVm(this.getActivity(),binding,socialAdapter,recommandAdapter);

        return discoverVm;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        binding.xrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.xrecyclerView.setAdapter(socialAdapter);

    }


}