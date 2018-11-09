package com.example.bqj.aptitude_sport.api;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.bqj.aptitude_sport.util.EmptyUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

import java.util.HashMap;

public class OkHttpUtils {

    private static String baseMethod;
    private static String baseUrl;

    /**
     * get请求
     * @param context 上下文对象
     * @param params 参数
     * @param requestCallBack 请求回调
     */
    public static <T> void getRequest(Context context, HashMap params, final RequestCallBack<T> requestCallBack){
        //判断参数列表是否为空
        if (EmptyUtils.isEmpty(params)){
            Log.e("lxy-http","参数Map为空");
            return;
        }
        //判断baseUrl是否为空
        if (params.containsKey("baseUrl")){
            //url
            baseUrl = (String) params.get("baseUrl");
        }else{
            Log.e("lxy-http","缺少baseUrl");
            return;
        }
        //判断baseMethod是否为空
        if (params.containsKey("baseMethod")){
            //method
            baseMethod = (String)params.get("baseMethod");
        }else{
            Log.e("lxy-http","缺少baseMethod");
            return;
        }

        OkGo.<T>get(baseUrl+baseMethod).tag(baseMethod).execute(new Callback<T>() {
            @Override
            public void onStart(Request<T, ? extends Request> request) {
                requestCallBack.onStart(request);
            }

            @Override
            public void onSuccess(Response<T> response) {
                requestCallBack.onSuccess(response);
            }

            @Override
            public void onCacheSuccess(Response<T> response) {
                requestCallBack.onCacheSuccess(response);
            }

            @Override
            public void onError(Response<T> response) {
                requestCallBack.onError(response);
            }

            @Override
            public void onFinish() {
                requestCallBack.onFinish();
            }

            @Override
            public void uploadProgress(Progress progress) {
                requestCallBack.uploadProgress(progress);
            }

            @Override
            public void downloadProgress(Progress progress) {
                requestCallBack.downloadProgress(progress);
            }

            @Override
            public T convertResponse(okhttp3.Response response) throws Throwable {

                if (response.code() == 200) {
                    String jsonResult = response.body().string().trim();
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    final String code = jsonObject.optString("resultCode", "");
                    final String message = jsonObject.optString("resultMsg", "");
                    if (code.equals("200") || code.equals("204") || code.equals("205")||code.equals("202")) {
                        T t = (T) requestCallBack.parseNetworkResponse(jsonResult);
                        return t;
                    }
                }
                return null;
            }
        });
    }

    /**
     * post请求
     */
    public static void postRequest(){



    }

    /**
     * 上传文件
     */
    public static void uploadFile(){




    }
    /**
     * 下载文件
     */
    public static void downloadFile(){




    }
}
