package com.huasport.smartsport.ui.discover.bean;

public class ReleaseUploadBean {


    /**
     * result : {"picUrl":"http://devfdfs.zntyydh.com/group1/M00/00/03/CkAillu7Ek6AIK7NAAaGsnA_O_U871.jpg","picId":"9b3fff94c88e4fee85b1c4e5be15a069"}
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
         * picUrl : http://devfdfs.zntyydh.com/group1/M00/00/03/CkAillu7Ek6AIK7NAAaGsnA_O_U871.jpg
         * picId : 9b3fff94c88e4fee85b1c4e5be15a069
         */

        private String picUrl;
        private String picId;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPicId() {
            return picId;
        }

        public void setPicId(String picId) {
            this.picId = picId;
        }
    }
}
