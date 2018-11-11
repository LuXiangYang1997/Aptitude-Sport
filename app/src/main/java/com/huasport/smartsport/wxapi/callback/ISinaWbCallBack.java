package com.huasport.smartsport.wxapi.callback;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;

/**
 * 新浪微博回掉接口
 * Created by lwd on 2018/6/21.
 */

public interface ISinaWbCallBack {
    /**
     * 成功回调
     * @param oauth2AccessToken
     */
    void onSuccess(Oauth2AccessToken oauth2AccessToken);

    /**
     * 取消回调
     */
    void cancel();

    /**
     * 失败回调
     * @param wbConnectErrorMessage
     */
    void onFailure(WbConnectErrorMessage wbConnectErrorMessage);
}
