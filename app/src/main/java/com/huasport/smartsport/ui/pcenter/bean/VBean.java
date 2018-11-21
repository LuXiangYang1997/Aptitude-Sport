package com.huasport.smartsport.ui.pcenter.bean;

/**
 * Created by bqj on 2018/6/28.
 */

public class VBean {


    /**
     * result : {"version":{"version":"2.1.3","downUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/0E/rBADF1uMof2AU5TtAKZmmHjRm5Q343.apk","desc":"1.bug修复","isForcedUpgrade":true,"status":true}}
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
         * version : {"version":"2.1.3","downUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/0E/rBADF1uMof2AU5TtAKZmmHjRm5Q343.apk","desc":"1.bug修复","isForcedUpgrade":true,"status":true}
         */

        private VersionBean version;

        public VersionBean getVersion() {
            return version;
        }

        public void setVersion(VersionBean version) {
            this.version = version;
        }

        public static class VersionBean {
            /**
             * version : 2.1.3
             * downUrl : http://zntyfdfs.efida.com.cn/group1/M00/00/0E/rBADF1uMof2AU5TtAKZmmHjRm5Q343.apk
             * desc : 1.bug修复
             * isForcedUpgrade : true
             * status : true
             */

            private String version;
            private String downUrl;
            private String desc;
            private boolean isForcedUpgrade;
            private boolean status;

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getDownUrl() {
                return downUrl;
            }

            public void setDownUrl(String downUrl) {
                this.downUrl = downUrl;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public boolean isIsForcedUpgrade() {
                return isForcedUpgrade;
            }

            public void setIsForcedUpgrade(boolean isForcedUpgrade) {
                this.isForcedUpgrade = isForcedUpgrade;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }
        }
    }
}
