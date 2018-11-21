package com.huasport.smartsport.ui.pcenter.attention.view;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.databinding.AttentionLayoutBinding;
import com.huasport.smartsport.rxpermission.RxPermissionUtil;
import com.huasport.smartsport.rxpermission.RxPermissionUtilCallback;
import com.huasport.smartsport.ui.pcenter.attention.adapter.AttentionAdapter;
import com.huasport.smartsport.ui.pcenter.attention.vm.AttentionVm;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;


public class AttentionActivity extends BaseActivity<AttentionLayoutBinding, AttentionVm> implements View.OnClickListener {

    private AttentionVm attentionVm;
    private AttentionAdapter attentionAdapter;
    private boolean activityState;
    private String registerCode;
    private Intent intent;

    @Override
    public int initContentView() {
        return R.layout.attention_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public AttentionVm initViewModel() {

        attentionAdapter = new AttentionAdapter(this);

        binding.attentionTablayout.addTab(binding.attentionTablayout.newTab().setText("关注"));
        binding.attentionTablayout.addTab(binding.attentionTablayout.newTab().setText("粉丝"));

        attentionVm = new AttentionVm(this, attentionAdapter);

        return attentionVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle(getString(R.string.linkman));
        setRightImg(R.mipmap.icon_addfriend);

        toolbarBinding.llLeft.setOnClickListener(this);
        toolbarBinding.imgRight.setOnClickListener(this);

        binding.attentionXrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.attentionXrecyclerView.setAdapter(attentionAdapter);

        binding.smartRefreshlayout.setEnableRefresh(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                activityState = (boolean) SharedPreferencesUtil.getParam(this, "ActivityState", false);
                if (activityState) {
                    setResult(0);
                    this.finish();
                } else {
                    this.setResult(1);
                    this.finish();
                }
                break;
            case R.id.img_right:
                RxPermissionUtil.getPermission(this, Manifest.permission.READ_CONTACTS, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {

                            String addressRegisterCode = (String) SharedPreferencesUtil.getParam(AttentionActivity.this, "addressRegisterCode", "");
                            registerCode = (String) SharedPreferencesUtil.getParam(AttentionActivity.this, "registerCode", "");
                            if (!EmptyUtil.isEmpty(addressRegisterCode)) {

                                if (!addressRegisterCode.equals(registerCode)) {
                                    final CustomDialog.Builder customdialog = new CustomDialog.Builder(AttentionActivity.this);

                                    customdialog.setCancelable(false);
                                    customdialog.setGravity(Gravity.CENTER).
                                            setContentView(R.layout.get_jurisdiction).
                                            setOnClickListener(R.id.submit, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    SharedPreferencesUtil.setParam(AttentionActivity.this, "addressRegisterCode", registerCode);
                                                    intent = new Intent(AttentionActivity.this, AddFriendsActivity.class);
                                                    startActivityForResult(intent, 0);

                                                }
                                            }).setOnClickListener(R.id.cancel, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            customdialog.dismiss();

                                        }
                                    });
                                    customdialog.setWidth(0.8f).show();
                                } else {
                                    SharedPreferencesUtil.setParam(AttentionActivity.this, "addressRegisterCode", registerCode);
                                    intent = new Intent(AttentionActivity.this, AddFriendsActivity.class);
                                    startActivityForResult(intent, 0);
                                }
                            } else {
                                SharedPreferencesUtil.setParam(AttentionActivity.this, "addressRegisterCode", registerCode);
                                intent = new Intent(AttentionActivity.this, AddFriendsActivity.class);
                                startActivityForResult(intent, 0);
                            }

                        } else {
                            return;
                        }
                    }
                });


                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activityState = (boolean) SharedPreferencesUtil.getParam(this, "ActivityState", false);
        if (activityState) {
            setResult(0);
            this.finish();
        } else {
            this.setResult(1);
            this.finish();
        }
    }
}
