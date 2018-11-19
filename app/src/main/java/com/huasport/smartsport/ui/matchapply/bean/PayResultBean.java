package com.huasport.smartsport.ui.matchapply.bean;

import java.io.Serializable;

/**
 * Created by bqj on 2018/6/26.
 */

public class PayResultBean implements Serializable {


    /**
     * result : {"alipay_sdk":"alipay-sdk-java-dynamicVersionNo","charset":"utf-8","biz_content":"%7B%22body%22%3A%22%E5%85%A8%E5%9B%BD%E6%99%BA%E8%83%BD%E4%BD%93%E8%82%B2%E6%8A%A5%E5%90%8D%E8%B4%B9%22%2C%22out_trade_no%22%3A%22201806260049177676%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%8A%A5%E5%90%8D%E8%B4%B9%E7%94%A8%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%221.00%22%7D","method":"alipay.trade.app.pay","format":"json","sign":"ShB4RjJ4sc6OWzFLFeFnEedrP4vkYFLaNn%2FHn8VADmbHOHYKPkAYQLXAHyMzolPYRHOJNh7Ej214ZFda8ZvhCSGSBtLJI8m6jJx7egOcvgCvBHeLGwpy7V6aQ07nbMs%2Fdsodd6aALnyY8YP2L6PnvO175fWMMVZo15GFfPOV13o%3D","notify_url":"http%3A%2F%2Fzntyapi.efida.com.cn%2Forder%2Falipay%2Fpay%2Forderback","app_id":"2018061360368311","sign_type":"RSA2","version":"1.0","timestamp":"2018-06-26+00%3A49%3A57"}
     * resultCode : 200
     * resultMsg : 请求操作成功
     */

    private ResultBean result;
    private int resultCode;
    private String resultMsg;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public static class ResultBean implements Serializable{
        /**
         * alipay_sdk : alipay-sdk-java-dynamicVersionNo
         * charset : utf-8
         * biz_content : %7B%22body%22%3A%22%E5%85%A8%E5%9B%BD%E6%99%BA%E8%83%BD%E4%BD%93%E8%82%B2%E6%8A%A5%E5%90%8D%E8%B4%B9%22%2C%22out_trade_no%22%3A%22201806260049177676%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%8A%A5%E5%90%8D%E8%B4%B9%E7%94%A8%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%221.00%22%7D
         * method : alipay.trade.app.pay
         * format : json
         * sign : ShB4RjJ4sc6OWzFLFeFnEedrP4vkYFLaNn%2FHn8VADmbHOHYKPkAYQLXAHyMzolPYRHOJNh7Ej214ZFda8ZvhCSGSBtLJI8m6jJx7egOcvgCvBHeLGwpy7V6aQ07nbMs%2Fdsodd6aALnyY8YP2L6PnvO175fWMMVZo15GFfPOV13o%3D
         * notify_url : http%3A%2F%2Fzntyapi.efida.com.cn%2Forder%2Falipay%2Fpay%2Forderback
         * app_id : 2018061360368311
         * sign_type : RSA2
         * version : 1.0
         * timestamp : 2018-06-26+00%3A49%3A57
         */

        private String alipay_sdk;
        private String charset;
        private String biz_content;
        private String method;
        private String format;
        private String sign;
        private String notify_url;
        private String app_id;
        private String sign_type;
        private String version;
        private String timestamp;

        public String getAlipay_sdk() {
            return alipay_sdk;
        }

        public void setAlipay_sdk(String alipay_sdk) {
            this.alipay_sdk = alipay_sdk;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getBiz_content() {
            return biz_content;
        }

        public void setBiz_content(String biz_content) {
            this.biz_content = biz_content;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
