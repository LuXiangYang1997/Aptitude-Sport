package com.example.bqj.aptitude_sport.wxapi.callback;

/**
 * Created by bqj on 2018/6/29.
 */

public interface AlipayCallBack {

    //支付宝支付成功
    void paySuccess(String resultStatus);

    //支付宝支付失败
    void payFailed(String resultStatus);

}
