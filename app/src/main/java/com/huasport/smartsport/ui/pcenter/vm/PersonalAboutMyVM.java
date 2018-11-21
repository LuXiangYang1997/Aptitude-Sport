package com.huasport.smartsport.ui.pcenter.vm;

import android.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.api.DownloadApkManager;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.ActivityAboutmyBinding;
import com.huasport.smartsport.ui.pcenter.bean.VBean;
import com.huasport.smartsport.ui.pcenter.view.PersionAboutMyActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.lzy.okgo.model.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PersonalAboutMyVM extends BaseViewModel {

    private PersionAboutMyActivity persionAboutMyActivity;
    private String version;
    private ActivityAboutmyBinding binding;
    private ObservableField<String> versionCode = new ObservableField<>();
    private ToastUtil toastUtil;

    public PersonalAboutMyVM(PersionAboutMyActivity persionAboutMyActivity) {
        this.persionAboutMyActivity = persionAboutMyActivity;
        init();
    }

    private void init() {
        binding = persionAboutMyActivity.getBinding();
        version = Util.getVersionName(persionAboutMyActivity);
        binding.appversion.setText("智体运动 " +"v"+version);
        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式
        String date = sf.format(calendar.getTime());
        binding.Copyright.setText("Copyright@" + date);
        //初始化Toast
        toastUtil = new ToastUtil(persionAboutMyActivity);
        updataVersion();
    }

    /**
     * 线上版本
     */
    private void updataVersion() {

        final HashMap params = new HashMap();
        params.put("baseMethod", Method.VERSION_CODE);
        params.put("platform", "android");
        params.put("baseUrl", Config.baseUrl);


        OkHttpUtil.getRequest(persionAboutMyActivity, params, new RequestCallBack<VBean>() {
            @Override
            public void onSuccess(Response<VBean> response) {
                VBean vBean = response.body();
                if(!EmptyUtil.isEmpty(vBean)){
                    int resultCode = vBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        versionCode.set(vBean.getResult().getVersion().getVersion());
                        binding.tvUpdataVersion.setText("更新版本 " + "(" + "v" + vBean.getResult().getVersion().getVersion() + ")");
                    }
                }

            }

            @Override
            public VBean parseNetworkResponse(String jsonResult) {
                VBean vBean = JSON.parseObject(jsonResult, VBean.class);
                return vBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    /**
     * 点击更新
     */
    public void updata() {
        if (!EmptyUtil.isEmpty(versionCode.get())) {
            int version = Util.getVersion(persionAboutMyActivity);
            String vStr = versionCode.get().replace(".", "");
            if (Integer.parseInt(vStr) > version) {
                DownloadApkManager downloadApkManager = new DownloadApkManager(persionAboutMyActivity);
                downloadApkManager.startDownLoad();
            } else {
                toastUtil.centerToast("当前已经是最新版本");
            }

        }

    }

}
