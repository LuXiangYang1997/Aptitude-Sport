package com.huasport.smartsport.ui.pcenter.medal.view;

import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.SelectAddressLayoutBinding;
import com.huasport.smartsport.ui.pcenter.medal.adapter.AddressAdapter;
import com.huasport.smartsport.ui.pcenter.medal.vm.SelectAddressVM;
import com.huasport.smartsport.util.EmptyUtil;
import com.scwang.smartrefresh.layout.impl.ScrollBoundaryDeciderAdapter;

public class AddAddressActivity extends BaseActivity<SelectAddressLayoutBinding, SelectAddressVM> implements View.OnTouchListener, View.OnClickListener {

    private SelectAddressVM selectAddressVM;
    private AddressAdapter addressAdapter;
    private int x_down = 0;    //mListView选中Item按下时的x坐标
    private int x_up = 0;    //mListView选中Item松开时的x坐标
    public ObservableField<String> addressCode = new ObservableField<>();

    @Override
    public int initContentView() {
        return R.layout.select_address_layout;
    }

    @Override
    public int initVariableId() {
        return BR.selectaddressVm;
    }

    @Override
    public SelectAddressVM initViewModel() {

        addressAdapter = new AddressAdapter(this);

        selectAddressVM = new SelectAddressVM(this, addressAdapter);

        return selectAddressVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("选择收货地址");
        toolbarBinding.llLeft.setOnClickListener(this);
        binding.addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.addressRecyclerView.setAdapter(addressAdapter);
        binding.smartRefreshlayout.setScrollBoundaryDecider(new ScrollBoundaryDeciderAdapter());//自定义滚动边界
        binding.addressRecyclerView.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO 自动生成的方法存根
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x_down = (int) event.getX();
                break;

            case MotionEvent.ACTION_UP:
                x_up = (int) event.getX();
                break;

            default:
                break;
        }
        if ((x_up - x_down) >= 0) {
            addressAdapter.item.set(true);
        } else {
            addressAdapter.item.set(false);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                String addressCode = getIntent().getStringExtra("addressCode");
                for (int i = 0; i < addressAdapter.mList.size(); i++) {
                    if (EmptyUtil.isEmpty(addressCode)) {
                        finish();
                    } else {
                        if (addressCode.equals(addressAdapter.mList.get(i).getAddressCode())) {
                            Intent intent = getIntent();
                            intent.putExtra("addressBean", addressAdapter.mList.get(i));
                            setResult(StatusVariable.ADDRESSCODE, intent);
                            finish();
                        }
                    }
                }
                break;
        }
    }

}
