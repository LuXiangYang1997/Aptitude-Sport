package com.huasport.smartsport.ui.pcenter.attention.bean;

import java.util.List;

public class AttentionBean {


    /**
     * result : {"total":4,"pageNo":1,"totalPage":1,"pageSize":10,"list":[{"registerId":"20180917153740xjf001","registerNickName":null,"registerPhoto":null,"isAddressList":"1","status":"mutualfollow","isAuth":"0","position":null},{"registerId":"2018083113373870gu73","registerNickName":null,"registerPhoto":null,"isAddressList":"0","status":"mutualfollow","isAuth":"0","position":null},{"registerId":"20180921115339z75k01","registerNickName":null,"registerPhoto":null,"isAddressList":"1","status":"follow","isAuth":"0","position":null},{"registerId":"2863861314177024","registerNickName":"\u201c怪咖\u201d","registerPhoto":"http://qzapp.qlogo.cn/qzapp/101496667/EA00AC1400AB688EF07376DB2F6BDA6B/100","isAddressList":"0","status":"follow","isAuth":"0","position":null}]}
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
         * total : 4
         * pageNo : 1
         * totalPage : 1
         * pageSize : 10
         * list : [{"registerId":"20180917153740xjf001","registerNickName":null,"registerPhoto":null,"isAddressList":"1","status":"mutualfollow","isAuth":"0","position":null},{"registerId":"2018083113373870gu73","registerNickName":null,"registerPhoto":null,"isAddressList":"0","status":"mutualfollow","isAuth":"0","position":null},{"registerId":"20180921115339z75k01","registerNickName":null,"registerPhoto":null,"isAddressList":"1","status":"follow","isAuth":"0","position":null},{"registerId":"2863861314177024","registerNickName":"\u201c怪咖\u201d","registerPhoto":"http://qzapp.qlogo.cn/qzapp/101496667/EA00AC1400AB688EF07376DB2F6BDA6B/100","isAddressList":"0","status":"follow","isAuth":"0","position":null}]
         */

        private int total;
        private int pageNo;
        private int totalPage;
        private int pageSize;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * registerId : 20180917153740xjf001
             * registerNickName : null
             * registerPhoto : null
             * isAddressList : 1
             * status : mutualfollow
             * isAuth : 0
             * position : null
             */

            private String registerId;
            private Object registerNickName;
            private Object registerPhoto;
            private String isAddressList;
            private String status;
            private String isAuth;
            private Object position;

            public String getRegisterId() {
                return registerId;
            }

            public void setRegisterId(String registerId) {
                this.registerId = registerId;
            }

            public Object getRegisterNickName() {
                return registerNickName;
            }

            public void setRegisterNickName(Object registerNickName) {
                this.registerNickName = registerNickName;
            }

            public Object getRegisterPhoto() {
                return registerPhoto;
            }

            public void setRegisterPhoto(Object registerPhoto) {
                this.registerPhoto = registerPhoto;
            }

            public String getIsAddressList() {
                return isAddressList;
            }

            public void setIsAddressList(String isAddressList) {
                this.isAddressList = isAddressList;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIsAuth() {
                return isAuth;
            }

            public void setIsAuth(String isAuth) {
                this.isAuth = isAuth;
            }

            public Object getPosition() {
                return position;
            }

            public void setPosition(Object position) {
                this.position = position;
            }
        }
    }
}
