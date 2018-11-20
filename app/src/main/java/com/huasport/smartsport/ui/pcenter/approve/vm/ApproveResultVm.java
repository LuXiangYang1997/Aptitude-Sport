package com.huasport.smartsport.ui.pcenter.approve.vm;

import android.content.Intent;
import android.view.View;

import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.ApproveresultLayoutBinding;
import com.huasport.smartsport.ui.discover.bean.ApproveBean;
import com.huasport.smartsport.ui.pcenter.approve.view.ApproveActivity;
import com.huasport.smartsport.ui.pcenter.approve.view.ApproveResultActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;

public class ApproveResultVm extends BaseViewModel  {

    private ApproveResultActivity approveResultActivity;
    private ApproveresultLayoutBinding binding;
    private  String token;
    private ApproveBean.ResultBean.AuthBean auth;
    private  String registerCode;
    private Counter counter;
    private LoadingDialog loadingDialog;
    private ToastUtil toastUtil;

    public ApproveResultVm(ApproveResultActivity approveResultActivity) {
        this.approveResultActivity = approveResultActivity;
        binding = approveResultActivity.getBinding();
        init();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {
        auth = (ApproveBean.ResultBean.AuthBean) approveResultActivity.getIntent().getSerializableExtra("authStatus");
        //初始化Toast
        toastUtil = new ToastUtil(approveResultActivity);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
    }

    private void initData() {


        String authStatus = auth.getAuthStatus();
        if (authStatus.equals(StatusVariable.PASS)) {//已认证
            binding.llApproveFailed.setVisibility(View.GONE);
            binding.llApproveSuccess.setVisibility(View.VISIBLE);
            binding.tvApprove.setText("恭喜您，成功完成认证");
            binding.tvApproveMsg.setText("");
        } else if (authStatus.equals(StatusVariable.REJECT)) {//认证失败
            SharedPreferencesUtil.setParam(approveResultActivity, registerCode, "reject");
            binding.llApproveSuccess.setVisibility(View.GONE);
            binding.llApproveFailed.setEnabled(true);
            binding.llApproveFailed.setVisibility(View.VISIBLE);
            if (!EmptyUtil.isEmpty((String) auth.getRejectReason())) {
                binding.tvReason.setText("原因：" + (String) auth.getRejectReason());
            }
        } else if (authStatus.equals(StatusVariable.WAIT_AUDIT)) {//认证中
            binding.llApproveSuccess.setVisibility(View.VISIBLE);
            binding.llApproveFailed.setVisibility(View.GONE);
            binding.tvApprove.setText("申请提交成功，审核中");
            binding.tvApproveMsg.setText("预计审核时间7个工作日");
        }


    }

    /**
     * 重新认证
     */
    public void resetApprove() {

        Intent intent = new Intent(approveResultActivity, ApproveActivity.class);
        approveResultActivity.startActivity(intent);
        approveResultActivity.finish();

    }

}
