package com.huasport.smartsport.ui.matchapply.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ConfirmpaymentLayoutBinding;
import com.huasport.smartsport.ui.matchapply.adapter.ConfirmPayMentAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.OrderMessageAdapter;
import com.huasport.smartsport.ui.matchapply.vm.ConfirmPayMentVM;


public class ConfirmPayMentActivity extends BaseActivity<ConfirmpaymentLayoutBinding, ConfirmPayMentVM> implements View.OnClickListener {


    private ConfirmPayMentVM confirmPayMentVM;
    private ConfirmPayMentAdapter confirmPayMentAdapter;
    private OrderMessageAdapter orderMessageAdapter;
    private static final int MESSAGE_TIME = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_TIME:
                    binding.tvConfirmpayTime.setText("还剩" + (String) msg.obj + "订单将被取消，请尽快完成支付");
                    if (msg.arg1 == 0) {
                        binding.timer.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    @Override
    public int initContentView() {
        return R.layout.confirmpayment_layout;
    }

    @Override
    public int initVariableId() {
        return BR.confirmPaymentVM;
    }

    @Override
    public ConfirmPayMentVM initViewModel() {
        Log.e("lwd", "ConfirmPayMentActivity  initViewModel");
        confirmPayMentAdapter = new ConfirmPayMentAdapter(this);

        //订单编号及其他
        orderMessageAdapter = new OrderMessageAdapter(this);

        confirmPayMentVM = new ConfirmPayMentVM(this, confirmPayMentAdapter, orderMessageAdapter);

        return confirmPayMentVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle(getResources().getString(R.string.sure_pay));
        toolbarBinding.llLeft.setOnClickListener(this);
        //订单信息
        binding.orderInformationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.orderInformationRecyclerView.setAdapter(confirmPayMentAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;

        }
    }

    /**
     * 设置倒计时
     *
     * @param time
     */
    public void setTime(String time, long num) {
        Message message = new Message();
        message.what = MESSAGE_TIME;
        message.obj = time;
        message.arg1 = (int) num;
        mHandler.sendMessage(message);
    }
}
