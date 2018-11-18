package com.huasport.smartsport.api;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.Util;

import java.util.HashMap;
public class HttpLog {


    public static void recordLog(Context context, String token) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("appVersion", Util.getVersionName(context));
        params.put("regTerminal", "ANDROID");
        params.put("baseMethod", Method.RECORDLOG);
        params.put("baseUrl", Config.baseUrl3);

        OkHttpUtil.postRequest(context, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)){
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){

                    }else{
                        LogUtil.e("失败");
                    }
                }
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                LogUtil.e(msg);
            }
        });

    }

    //分享成功后接口
    public static void shareHttp(Context context, String token, String id, final ShareStatusCallback shareStatusCallback) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.SHAREHTTP);
        params.put("baseUrl", Config.baseUrl2);
        params.put("socialInfoId", id);

        OkHttpUtil.postRequest(context, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)){
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        shareStatusCallback.shareSuccess();
                        LogUtil.e("成功");
                    }else{
                        shareStatusCallback.shareFailed();
                        LogUtil.e("失败");
                    }
                }
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                shareStatusCallback.shareFailed();
                LogUtil.e(msg);
        }
        });

    }



}
