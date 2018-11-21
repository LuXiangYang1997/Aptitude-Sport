package com.huasport.smartsport.ui.pcenter.view;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.MedalOrdermessageLayoutBinding;
import com.huasport.smartsport.ui.pcenter.vm.PerCenterSuccessOrderDetailVm;


public class PerCenterSuccessOrderDetailActivity extends BaseActivity<MedalOrdermessageLayoutBinding, PerCenterSuccessOrderDetailVm> {

    private PerCenterSuccessOrderDetailVm perCenterSuccessOrderDetailVm;

    @Override
    public int initContentView() {
        return R.layout.medal_ordermessage_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public PerCenterSuccessOrderDetailVm initViewModel() {


        perCenterSuccessOrderDetailVm = new PerCenterSuccessOrderDetailVm(this);

        return perCenterSuccessOrderDetailVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        String orderType = getIntent().getStringExtra("orderType");

        if (orderType.equals(StatusVariable.SUCCESSAPPLY)) {
            binding.tvStatus.setText("已付款");
            binding.tvOrderdecript.setText("预计72小时内发货");
        } else if (orderType.equals(StatusVariable.SHIPPED)) {
            binding.tvStatus.setText("已发货");
            binding.tvOrderdecript.setText("快递小哥努力给您送货中");
        } else if (orderType.equals(StatusVariable.COMOLETED)) {
            binding.tvStatus.setText("已完成");
        }
        setTitle("订单信息");
        back();
    }
}