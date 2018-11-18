package com.huasport.smartsport.ui.discover.view;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.DynamicLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.ReleaseMsgAdapter;
import com.huasport.smartsport.ui.discover.vm.DynamicVm;

public class DynamicActivity extends BaseActivity<DynamicLayoutBinding, DynamicVm> implements View.OnClickListener {

    private ReleaseMsgAdapter releaseMsgAdapter;
    private DynamicVm dynamicVm;
    private ItemTouchHelper itemTouchHelper;

    @Override
    public int initContentView() {
        return R.layout.dynamic_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public DynamicVm initViewModel() {

        releaseMsgAdapter = new ReleaseMsgAdapter(this);

        dynamicVm = new DynamicVm(this, releaseMsgAdapter);

        return dynamicVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        toolbarBinding.tvRight.setText(getResources().getString(R.string.release));
        toolbarBinding.tvLeft.setText(getResources().getString(R.string.cancel));
        toolbarBinding.tvLeft.setCompoundDrawables(null, null, null, null);
        toolbarBinding.llLeft.setOnClickListener(this);
        toolbarBinding.tvRight.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        binding.addRecyclerView.setLayoutManager(gridLayoutManager);
        //实例化callback
        SimpleItemTouchHelperCallback simpleItemTouchHelperCallback = new SimpleItemTouchHelperCallback(releaseMsgAdapter);
        //构造touchHelper
        itemTouchHelper = new ItemTouchHelper(simpleItemTouchHelperCallback);
        //和RecyclerView建立联系
        itemTouchHelper.attachToRecyclerView(binding.addRecyclerView);
        binding.addRecyclerView.setAdapter(releaseMsgAdapter);


    }

    //手机返回键
    @Override
    public void onBackPressed() {

        dynamicVm.cancelRelease();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                dynamicVm.cancelRelease();
                break;
            case R.id.tv_right:
                dynamicVm.initRelease();
                break;
        }

    }


}
