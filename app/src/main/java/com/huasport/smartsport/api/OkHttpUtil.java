package com.huasport.smartsport.api;

import android.content.Context;

import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.LogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class OkHttpUtil {

    private static String baseMethod;
    private static String baseUrl;
    private static String filePath = "";
    private static String token;
    private static String url = "";

    /**
     * get请求
     *
     * @param context         上下文对象
     * @param params          参数
     * @param requestCallBack 请求回调
     */
    public static <T> void getRequest(Context context, HashMap params, final RequestCallBack<T> requestCallBack) {
        //判断参数列表是否为空
        if (EmptyUtil.isEmpty(params)) {
            LogUtil.e("参数Map为空");
            return;
        }
        //判断baseUrl是否为空
        if (params.containsKey("baseUrl")) {
            //url
            baseUrl = (String) params.get("baseUrl");
        } else {
            LogUtil.e("缺少baseUrl");
            return;
        }

        //判断baseMethod是否为空
        if (params.containsKey("baseMethod")) {
            //method
            baseMethod = (String) params.get("baseMethod");
        } else {
            LogUtil.e("缺少baseMethod");
            return;
        }
        params.put("t", String.valueOf(System.currentTimeMillis()));

        OkGo.<T>get(baseUrl + baseMethod).tag(baseMethod).params(params).execute(new Callback<T>() {

            //请求开始
            @Override
            public void onStart(Request<T, ? extends Request> request) {
                requestCallBack.onStart(request);
            }

            //请求成功
            @Override
            public void onSuccess(Response<T> response) {
                requestCallBack.onSuccess(response);
            }

            //从缓存中读取成功
            @Override
            public void onCacheSuccess(Response<T> response) {
                requestCallBack.onCacheSuccess(response);
            }

            //请求失败
            @Override
            public void onError(Response<T> response) {
                requestCallBack.onError(response);
            }

            //请求完成
            @Override
            public void onFinish() {
                requestCallBack.onFinish();
            }

            //上传进度
            @Override
            public void uploadProgress(Progress progress) {
                requestCallBack.uploadProgress(progress);
            }

            //下载进度
            @Override
            public void downloadProgress(Progress progress) {
                requestCallBack.downloadProgress(progress);
            }

            //解析数据
            @Override
            public T convertResponse(okhttp3.Response response) throws Throwable {

                if (response.code() == 200) {
                    String jsonResult = response.body().string().trim();

                    LogUtil.e(jsonResult);

                    JSONObject jsonObject = new JSONObject(jsonResult);
                    final String code = jsonObject.optString("resultCode", "");
                    final String message = jsonObject.optString("resultMsg", "");
                    if (code.equals("200") || code.equals("204") || code.equals("205") || code.equals("202")) {
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
    public static <T> void postRequest(Context context, HashMap params, final RequestCallBack<T> requestCallBack) {

        //判断参数列表是否为空
        if (EmptyUtil.isEmpty(params)) {
            LogUtil.e("参数Map为空");
            return;
        }
        //判断baseUrl是否为空
        if (params.containsKey("baseUrl")) {
            //url
            baseUrl = (String) params.get("baseUrl");
        } else {
            LogUtil.e("缺少baseUrl");
            return;
        }

        //判断baseMethod是否为空
        if (params.containsKey("baseMethod")) {
            //method
            baseMethod = (String) params.get("baseMethod");
        } else {
            LogUtil.e("缺少baseMethod");
            return;
        }
        params.put("t", String.valueOf(System.currentTimeMillis()));
        OkGo.<T>post(baseUrl + baseMethod).tag(baseMethod).params(params).execute(new Callback<T>() {

            //请求开始
            @Override
            public void onStart(Request<T, ? extends Request> request) {
                requestCallBack.onStart(request);
            }

            //请求成功
            @Override
            public void onSuccess(Response<T> response) {
                requestCallBack.onSuccess(response);
            }

            //从缓存中读取成功
            @Override
            public void onCacheSuccess(Response<T> response) {
                requestCallBack.onCacheSuccess(response);
            }

            //请求失败
            @Override
            public void onError(Response<T> response) {
                requestCallBack.onError(response);
            }

            //请求完成
            @Override
            public void onFinish() {
                requestCallBack.onFinish();
            }

            //上传进度
            @Override
            public void uploadProgress(Progress progress) {
                requestCallBack.uploadProgress(progress);
            }

            //下载进度
            @Override
            public void downloadProgress(Progress progress) {
                requestCallBack.downloadProgress(progress);
            }

            //解析数据
            @Override
            public T convertResponse(okhttp3.Response response) throws Throwable {

                if (response.code() == 200) {
                    String jsonResult = response.body().string().trim();

                    LogUtil.e(jsonResult);

                    JSONObject jsonObject = new JSONObject(jsonResult);
                    final String code = jsonObject.optString("resultCode", "");
                    final String message = jsonObject.optString("resultMsg", "");
                    if (code.equals("200") || code.equals("204") || code.equals("205") || code.equals("202")) {
                        T t = (T) requestCallBack.parseNetworkResponse(jsonResult);
                        return t;
                    }
                }
                return null;
            }
        });

    }

    /**
     * 上传文件
     */
    public static <T> void uploadFile(Context context, HashMap params, final RequestCallBack<T> requestCallBack) {

        //判断参数列表是否为空
        if (EmptyUtil.isEmpty(params)) {
            LogUtil.e("参数Map为空");
            return;
        }
        //判断baseUrl是否为空
        if (params.containsKey("baseUrl")) {
            //url
            baseUrl = (String) params.get("baseUrl");
        } else {
            LogUtil.e("缺少baseUrl");
            return;
        }

        //判断baseMethod是否为空
        if (params.containsKey("baseMethod")) {
            //method
            baseMethod = (String) params.get("baseMethod");
        } else {
            LogUtil.e("缺少baseMethod");
            return;
        }
        if (params.containsKey("file")) {
            filePath = (String) params.get("file");
        } else {
            LogUtil.e("缺少file");
            return;
        }
        params.put("t", String.valueOf(System.currentTimeMillis()));
        if (!params.containsKey("token")) {
            token = "";
            url = baseUrl + baseMethod;
        } else {
            token = (String) params.get("token");
            url = baseUrl + baseMethod + "?token=" + token;
        }
        OkGo.<T>post(url).params("file", new File(filePath)).params(params).params("file", new File(filePath).getName()).execute(new Callback<T>() {
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

                    LogUtil.e(jsonResult);

                    JSONObject jsonObject = new JSONObject(jsonResult);
                    final String code = jsonObject.optString("resultCode", "");
                    final String message = jsonObject.optString("resultMsg", "");
                    if (code.equals("200") || code.equals("204") || code.equals("205") || code.equals("202")) {
                        T t = (T) requestCallBack.parseNetworkResponse(jsonResult);
                        return t;
                    }
                }
                return null;
            }
        });
    }

    /**
     * 下载文件
     */
    public static void downloadFile() {


    }

    /**
     * 获取openid  accessToken值用于后期操作
     */
    public static <T> void getThirdInfo(Context context, String path, final RequestCallBack<T> requestCallBack) {
        OkGo.<T>get(path).execute(new Callback<T>() {

            //请求开始
            @Override
            public void onStart(Request<T, ? extends Request> request) {
                requestCallBack.onStart(request);
            }

            //请求成功
            @Override
            public void onSuccess(Response<T> response) {
                requestCallBack.onSuccess(response);
            }

            //从缓存中读取成功
            @Override
            public void onCacheSuccess(Response<T> response) {
                requestCallBack.onCacheSuccess(response);
            }

            //请求失败
            @Override
            public void onError(Response<T> response) {
                requestCallBack.onError(response);
            }

            //请求完成
            @Override
            public void onFinish() {
                requestCallBack.onFinish();
            }

            //上传进度
            @Override
            public void uploadProgress(Progress progress) {
                requestCallBack.uploadProgress(progress);
            }

            //下载进度
            @Override
            public void downloadProgress(Progress progress) {
                requestCallBack.downloadProgress(progress);
            }

            //解析数据
            @Override
            public T convertResponse(okhttp3.Response response) throws Throwable {

                if (response.code() == 200) {
                    String jsonResult = response.body().string().trim();
                    T t = (T) requestCallBack.parseNetworkResponse(jsonResult);
                    return t;
                }
                return null;
            }
        });
    }
}
