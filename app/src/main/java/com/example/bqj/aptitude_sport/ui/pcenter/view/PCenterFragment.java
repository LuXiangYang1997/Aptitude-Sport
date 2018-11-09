package com.example.bqj.aptitude_sport.ui.pcenter.view;


import com.example.bqj.aptitude_sport.BR;
import com.example.bqj.aptitude_sport.R;
import com.example.bqj.aptitude_sport.base.BaseFragment;
import com.example.bqj.aptitude_sport.databinding.PcenterLayoutBinding;
import com.example.bqj.aptitude_sport.ui.pcenter.vm.PcenterVm;

/**
 * 个人中心
 */
public class PCenterFragment extends BaseFragment<PcenterLayoutBinding,PcenterVm> {


    @Override
    public int initContentView() {
        return R.layout.pcenter_layout;
    }

    @Override
    public int initVariableId() {
        return BR.pcenterVm;
    }

    @Override
    public PcenterVm initViewModel() {

        PcenterVm pcenterVm=new PcenterVm(this.getActivity());

        return pcenterVm;
    }

    @Override
    protected void loadData() {

    }
}