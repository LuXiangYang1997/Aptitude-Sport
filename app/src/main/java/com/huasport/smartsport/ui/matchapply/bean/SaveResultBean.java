package com.huasport.smartsport.ui.matchapply.bean;

/**
 * Created by bqj on 2018/6/26.
 */

public class SaveResultBean {


    /**
     * result : {"orderCode":"201806260221315942","orderStaus":"wait_pay"}
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
         * orderCode : 201806260221315942
         * orderStaus : wait_pay
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
