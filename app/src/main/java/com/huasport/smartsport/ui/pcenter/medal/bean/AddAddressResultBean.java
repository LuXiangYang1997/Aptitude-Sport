package com.huasport.smartsport.ui.pcenter.medal.bean;

public class AddAddressResultBean {

    /**
     * result : {"addressCode":"20180816162103zyaw01"}
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
         * addressCode : 20180816162103zyaw01
         */
        private String addressCode;

        public String getAddressCode() {
            return addressCode;
        }

        public void setAddressCode(String addressCode) {
            this.addressCode = addressCode;
        }
    }
}
