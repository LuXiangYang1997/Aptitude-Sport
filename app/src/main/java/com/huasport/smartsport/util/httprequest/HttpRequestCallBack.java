package com.huasport.smartsport.util.httprequest;

import com.huasport.smartsport.bean.ResultBean;

public interface HttpRequestCallBack {
    /**
     * 请求成功
     */
    void httpSuccess(ResultBean resultBean);

    /**
     * 请求失败
     * @param code
     * @param msg
     */
    void httpFailed(int code, String msg);

    /**
     * 请求完成
     */
    void httpFinish();

}
