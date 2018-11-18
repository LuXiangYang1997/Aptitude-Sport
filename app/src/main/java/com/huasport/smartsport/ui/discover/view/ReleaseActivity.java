package com.huasport.smartsport.ui.discover.view;

import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.PcreleaseLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.ReleaseAdapter;
import com.huasport.smartsport.ui.discover.vm.ReleaseVm;

public class ReleaseActivity extends BaseActivity<PcreleaseLayoutBinding, ReleaseVm> implements View.OnClickListener {

    private ReleaseVm releaseVm;
    private ReleaseAdapter releaseAdapter;
    public int tabPosition = 0;
    public ObservableField<String> refreshStatus = new ObservableField<>("normal");


    @Override
    public int initContentView() {
        return R.layout.pcrelease_layout;
    }

    @Override
    public int initVariableId() {

        return 0;
    }

    @Override
    public ReleaseVm initViewModel() {

        releaseAdapter = new ReleaseAdapter(this);

        releaseVm = new ReleaseVm(this, releaseAdapter);
        return releaseVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle(getResources().getString(R.string.my_release));

        toolbarBinding.llLeft.setOnClickListener(this);

        binding.releaseXrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.releaseXrecyclerView.setAdapter(releaseAdapter);
        binding.releaseXrecyclerView.setPullRefreshEnabled(false);
        binding.releaseXrecyclerView.setLoadingMoreEnabled(false);

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
