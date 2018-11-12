package com.huasport.smartsport.bean;

public class UploadResultBean {

    /**
     * result : {"url":"http://zntyfdfs.efida.com.cn/group1/M00/00/01/rBADF1s0kt2AKqZ6AAX8RZSJIbg150.png"}
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
         * url : http://zntyfdfs.efida.com.cn/group1/M00/00/01/rBADF1s0kt2AKqZ6AAX8RZSJIbg150.png
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }



}
