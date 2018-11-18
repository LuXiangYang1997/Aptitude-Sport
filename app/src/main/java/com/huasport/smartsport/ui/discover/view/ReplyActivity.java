package com.huasport.smartsport.ui.discover.view;

import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ReplyLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.ReplyAdapter;
import com.huasport.smartsport.ui.discover.vm.ReplyVm;

public class ReplyActivity extends BaseActivity<ReplyLayoutBinding, ReplyVm> implements View.OnClickListener {

    private ReplyVm replyVm;
    private ReplyAdapter replyAdapter;
    public ObservableField<String> isOneSelf = new ObservableField<>("");
    public ObservableField<String> dynamicRegisterId = new ObservableField<>("");
    private boolean activityState;

    @Override
    public int initContentView() {
        return R.layout.reply_layout;
    }

    @Override
    public int initVariableId() {
        return BR.replyVm;
    }

    @Override
    public ReplyVm initViewModel() {

        replyAdapter = new ReplyAdapter(this);

        replyVm = new ReplyVm(this, replyAdapter);

        return replyVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        toolbarBinding.llLeft.setOnClickListener(this);

        binding.replyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.replyRecyclerView.setAdapter(replyAdapter);
        binding.replyRecyclerView.setPullRefreshEnabled(false);
        binding.replyRecyclerView.setLoadingMoreEnabled(false);
        binding.smartRefreshlayout.setEnableRefresh(false);
    }

    /**
     * 设置title
     *
     * @param s
     */
    public void setTitleText(String s) {
        toolbarBinding.tvTitle.setText(s);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
//                activityState = (boolean) SharedPreferencesUtils.getParam(this, "ActivityState", false);
//                if (activityState) {
//                    SharedPreferencesUtils.setParam(this,"BackState",true);
//                } else {
//                    SharedPreferencesUtils.setParam(this,"BackState",true);
//                }
                this.finish();
                break;
        }
    }

}
