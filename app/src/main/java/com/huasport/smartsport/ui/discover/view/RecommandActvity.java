package com.huasport.smartsport.ui.discover.view;

import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.RecommandLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.RecommandDetailAdapter;

public class RecommandActvity extends BaseActivity<RecommandLayoutBinding, RecommandVm> implements View.OnClickListener {

    private RecommandVm recommandVm;
    private RecommandDetailAdapter recommandDetailAdapter;
    private boolean activityState;
    public ObservableField<String> refreshStatus = new ObservableField<>("normal");

    @Override
    public int initContentView() {
        return R.layout.recommand_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public RecommandVm initViewModel() {

        recommandDetailAdapter = new RecommandDetailAdapter(this);

        recommandVm = new RecommandVm(this, recommandDetailAdapter);

        return recommandVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle(getResources().getString(R.string.recommand));
        toolbarBinding.llLeft.setOnClickListener(this);
        binding.recommandXrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recommandXrecyclerView.setAdapter(recommandDetailAdapter);

        binding.recommandXrecyclerView.setPullRefreshEnabled(false);
        binding.recommandXrecyclerView.setLoadingMoreEnabled(false);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }

    }
}
