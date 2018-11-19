package com.huasport.smartsport.ui.matchapply.bean;

import java.io.Serializable;

/**
 * Created by bqj on 2018/6/26.
 */

public class WeChatBean implements Serializable{


    /**
     * result : {"timeStamp":"20180626013327","package":"wx26013327222860e1dc1d8f612390596596","appId":"wx7570f2e03d6d90f5","sign":"0711C86E75A1B081FC6D0AD03AA0C9DF","orderCode":"201806260132245872","prepayId":"prepay_id=wx26013327222860e1dc1d8f612390596596","partnerId":"1508180271","nonceStr":"1198768109"}
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
         * timeStamp : 20180626013327
         * package : wx26013327222860e1dc1d8f612390596596
         * appId : wx7570f2e03d6d90f5
         * sign : 0711C86E75A1B081FC6D0AD03AA0C9DF
         * orderCode : 201806260132245872
         * prepayId : prepay_id=wx26013327222860e1dc1d8f612390596596
         * partnerId : 1508180271
         * nonceStr : 1198768109
         */

        private String timeStamp;
        private String packageX;
        private String appId;
        private String sign;
        private String orderCode;
        private String prepayId;
        private String partnerId;
        private String nonceStr;

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }
    }
}
