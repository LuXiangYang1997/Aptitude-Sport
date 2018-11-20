package com.huasport.smartsport.ui.matchapply.bean;

/**
 * Created by bqj on 2018/7/27.
 */

public class SubmitApplyMessageBean {


    /**
     * result : {"orderCode":"20180727193620yuie","orderStaus":"success"}
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
         * orderCode : 20180727193620yuie
         * orderStaus : success
         */

        private String orderCode;
        private String orderStaus;

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOrderStaus() {
            return orderStaus;
        }

        public void setOrderStaus(String orderStaus) {
            this.orderStaus = orderStaus;
        }
    }
}
