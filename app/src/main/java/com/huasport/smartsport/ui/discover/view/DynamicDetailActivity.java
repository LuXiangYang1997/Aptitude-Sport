package com.huasport.smartsport.ui.discover.view;

import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.DynamicDetailLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.DynamicDetailAdapter;
import com.huasport.smartsport.ui.discover.vm.DynamicDetailVm;

public class DynamicDetailActivity extends BaseActivity<DynamicDetailLayoutBinding, DynamicDetailVm> implements View.OnClickListener {

    private DynamicDetailVm dynamicDetailVm;
    private DynamicDetailAdapter dynamicDetailAdapter;
    public int tabPos = 0;
    public ObservableField<String> isOneSelf = new ObservableField<>("");
    public ObservableField<String> dynamicRegisterId = new ObservableField<>("");

    @Override
    public int initContentView() {
        return R.layout.dynamic_detail_layout;
    }

    @Override
    public int initVariableId() {
        return BR.dynamicDetailVm;
    }

    @Override
    public DynamicDetailVm initViewModel() {

        dynamicDetailAdapter = new DynamicDetailAdapter(this);

        dynamicDetailVm = new DynamicDetailVm(this, dynamicDetailAdapter);

        return dynamicDetailVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        back();

        binding.dynamicDteailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.dynamicDteailRecyclerView.setAdapter(dynamicDetailAdapter);

        //删除点击事件
        toolbarBinding.tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dynamicDetailVm.deleteDynamic();

            }
        });

    }

    /**
     * 设置删除按钮
     */
    public void setRightText() {

        toolbarBinding.tvRight.setText(getResources().getString(R.string.deleate));


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                this.finish();
                break;
        }
    }
}
