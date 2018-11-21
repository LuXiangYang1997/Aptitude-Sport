package com.huasport.smartsport.ui.pcenter.medal.bean;

import com.google.gson.annotations.SerializedName;

public class MedalWechatPayBean {


    /**
     * result : {"timeStamp":"1534422139","package":"Sign=WXPay","appId":"wx7570f2e03d6d90f5","sign":"C4A4179AC577C915EF0905096029B083","orderCode":"20180816202159yj5a16","prepayId":"wx16202218962282114122419c1236643305","partnerId":"1508180271","nonceStr":"1013738903"}
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

    public static class ResultBean {
        /**
         * timeStamp : 1534422139
         * package : Sign=WXPay
         * appId : wx7570f2e03d6d90f5
         * sign : C4A4179AC577C915EF0905096029B083
         * orderCode : 20180816202159yj5a16
         * prepayId : wx16202218962282114122419c1236643305
         * partnerId : 1508180271
         * nonceStr : 1013738903
         */

        private String timeStamp;
        @SerializedName("package")
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
