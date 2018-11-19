package com.huasport.smartsport.ui.matchapply.view;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ComfirmPaymentLayoutBinding;
import com.huasport.smartsport.ui.matchapply.adapter.PayMentOrderAdapter;
import com.huasport.smartsport.ui.matchapply.vm.PayMentOrderVM;


//付费报名订单
public class PayMentOrderActivty extends BaseActivity<ComfirmPaymentLayoutBinding, PayMentOrderVM> implements View.OnClickListener {


    private PayMentOrderVM payMentOrderVM;
    private PayMentOrderAdapter payMentOrderAdapter;

    @Override
    public int initContentView() {
        return R.layout.comfirm_payment_layout;
    }

    @Override
    public int initVariableId() {
        return BR.confirPaymentVM;
    }

    @Override
    public PayMentOrderVM initViewModel() {
        //订单信息
        payMentOrderAdapter = new PayMentOrderAdapter(this);

        payMentOrderVM = new PayMentOrderVM(this, payMentOrderAdapter);

        return payMentOrderVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("付费报名订单");
        toolbarBinding.llLeft.setOnClickListener(this);

        binding.paymentOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.paymentOrderRecyclerView.setAdapter(payMentOrderAdapter);
        binding.paymentOrderRecyclerView.setLoadingMoreEnabled(false);//禁止加载
        binding.paymentOrderRecyclerView.setPullRefreshEnabled(false);//禁止刷新
        binding.paymentOrderRecyclerView.setEnabledScroll(false);//禁止滑动
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
