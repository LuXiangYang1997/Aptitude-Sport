package com.huasport.smartsport.ui.pcenter.bean;

public class UserCenterInfo {


    /**
     * result : {"isOneself":"0","user":{"registerId":"2859846872909824","registerNickName":"师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","releaseNumber":15,"followNumber":1,"fansNumber":2,"dynamicNumber":12,"articleNumber":3,"status":"pass","certType":"enterprise","isFollow":"0"}}
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
         * isOneself : 0
         * user : {"registerId":"2859846872909824","registerNickName":"师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","releaseNumber":15,"followNumber":1,"fansNumber":2,"dynamicNumber":12,"articleNumber":3,"status":"pass","certType":"enterprise","isFollow":"0"}
         */

        private String isOneself;
        private UserBean user;

        public String getIsOneself() {
            return isOneself;
        }

        public void setIsOneself(String isOneself) {
            this.isOneself = isOneself;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * registerId : 2859846872909824
             * registerNickName : 师师
             * registerPhoto : http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132
             * releaseNumber : 15
             * followNumber : 1
             * fansNumber : 2
             * dynamicNumber : 12
             * articleNumber : 3
             * status : pass
             * certType : enterprise
             * isFollow : 0
             */

            private String registerId;
            private String registerNickName;
            private String registerPhoto;
            private int releaseNumber;
            private int followNumber;
            private int fansNumber;
            private int dynamicNumber;
            private int articleNumber;
            private String status;
            private String certType;
            private String isFollow;

            public String getRegisterId() {
                return registerId;
            }

            public void setRegisterId(String registerId) {
                this.registerId = registerId;
            }

            public String getRegisterNickName() {
                return registerNickName;
            }

            public void setRegisterNickName(String registerNickName) {
                this.registerNickName = registerNickName;
            }

            public String getRegisterPhoto() {
                return registerPhoto;
            }

            public void setRegisterPhoto(String registerPhoto) {
                this.registerPhoto = registerPhoto;
            }

            public int getReleaseNumber() {
                return releaseNumber;
            }

            public void setReleaseNumber(int releaseNumber) {
                this.releaseNumber = releaseNumber;
            }

            public int getFollowNumber() {
                return followNumber;
            }

            public void setFollowNumber(int followNumber) {
                this.followNumber = followNumber;
            }

            public int getFansNumber() {
                return fansNumber;
            }

            public void setFansNumber(int fansNumber) {
                this.fansNumber = fansNumber;
            }

            public int getDynamicNumber() {
                return dynamicNumber;
            }

            public void setDynamicNumber(int dynamicNumber) {
                this.dynamicNumber = dynamicNumber;
            }

            public int getArticleNumber() {
                return articleNumber;
            }

            public void setArticleNumber(int articleNumber) {
                this.articleNumber = articleNumber;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCertType() {
                return certType;
            }

            public void setCertType(String certType) {
                this.certType = certType;
            }

            public String getIsFollow() {
                return isFollow;
            }

            public void setIsFollow(String isFollow) {
                this.isFollow = isFollow;
            }
        }
    }
}
