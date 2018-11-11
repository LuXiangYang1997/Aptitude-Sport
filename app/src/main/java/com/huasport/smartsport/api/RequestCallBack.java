package com.huasport.smartsport.api;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;


public abstract class RequestCallBack<T>{

    /**
     * 请求开始
     * @param request
     */
    public void onStart(Request<T, ? extends Request> request){}
    /**
     *  UI 线程，请求成功后回调
     * @param response
     */
    public abstract void onSuccess(com.lzy.okgo.model.Response<T> response);

    /**
     * UI 线程，缓存读取成功后回调
     * @param response
     */
    public void onCacheSuccess(com.lzy.okgo.model.Response<T> response){}

    /**
     * UI 线程，请求失败后回调
     * @param response
     */
    public void onError(com.lzy.okgo.model.Response<T> response){}

    /**
     * 请求结束
     */
    public void onFinish(){

    }

    /**
     * 上传文件进度
     * @param progress
     */
    public void uploadProgress(Progress progress){

    }


    /**
     * 下载进度
     * @param progress
     */
    public void downloadProgress(Progress progress){

    }

    /**
     *  子线程，可以做耗时操作
     *  根据传递进来的 response 对象，把数据解析成需要的 Model 类型并返回
     *  可以根据自己的需要，抛出异常，在onError中处理
     * @param jsonResult
     * @return
     * @throws Throwable
     */
    public abstract T parseNetworkResponse(String jsonResult);

    /**
     * Error
     */
    public abstract void onFailed(int code,String msg);

}
