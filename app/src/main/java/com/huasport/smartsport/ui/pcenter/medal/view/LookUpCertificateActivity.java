package com.huasport.smartsport.ui.pcenter.medal.view;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.databinding.LookupCrtificateBinding;
import com.huasport.smartsport.ui.pcenter.medal.bean.UpDataStatusBean;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import java.util.HashMap;

public class LookUpCertificateActivity extends BaseActivity<LookupCrtificateBinding, BaseViewModel> {


    private String certCode;

    @Override
    public int initContentView() {
        return R.layout.lookup_crtificate;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public BaseViewModel initViewModel() {
        return new BaseViewModel();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        setTitle("参赛证书");
        back();
        String cerficateUrl = getIntent().getStringExtra("cerficateUrl");
        certCode = getIntent().getStringExtra("certCode");

        if (!EmptyUtil.isEmpty(cerficateUrl)) {
            GlideUtil.LoadImage(this,cerficateUrl,binding.cerficateImg);
        }
        updataStatus();
    }

    /**
     * 更新查看证书状态
     */
    private void updataStatus() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.UPDATASTATUS);
        params.put("certCode", certCode);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.postRequest(this, params, new RequestCallBack<UpDataStatusBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<UpDataStatusBean> response) {

            }

            @Override
            public UpDataStatusBean parseNetworkResponse(String jsonResult) {
                UpDataStatusBean upDataStatusBean = JSON.parseObject(jsonResult, UpDataStatusBean.class);
                return upDataStatusBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }
}
