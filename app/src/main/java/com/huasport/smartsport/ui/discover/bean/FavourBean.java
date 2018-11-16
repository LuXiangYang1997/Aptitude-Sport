package com.huasport.smartsport.ui.discover.bean;

import java.util.List;

public class FavourBean {


    /**
     * result : {"total":3,"pageNo":1,"totalPage":1,"pageSize":10,"list":[{"registerId":"2859846872909824","registerNickName":"师师","registerPhoto":"http://devfdfs.zntyydh.com/group1/M00/00/04/CkAillvFsj6AJ3TpACWTtMY0_mk927.jpg","createDate":"2018-10-18","isAuth":"1","position":"测试企业名称"},{"registerId":"2762268565080064","registerNickName":"  西厢墨","registerPhoto":"http://zntyfdfs.efida.com.cn/group1/M00/00/08/rBADF1tr5_KAZZAlAAAnPTqQltU7587.25","createDate":"2018-10-18","isAuth":"0","position":null},{"registerId":"2814770682218496","registerNickName":null,"registerPhoto":null,"createDate":"2018-10-18","isAuth":"0","position":null}]}
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
         * total : 3
         * pageNo : 1
         * totalPage : 1
         * pageSize : 10
         * list : [{"registerId":"2859846872909824","registerNickName":"师师","registerPhoto":"http://devfdfs.zntyydh.com/group1/M00/00/04/CkAillvFsj6AJ3TpACWTtMY0_mk927.jpg","createDate":"2018-10-18","isAuth":"1","position":"测试企业名称"},{"registerId":"2762268565080064","registerNickName":"  西厢墨","registerPhoto":"http://zntyfdfs.efida.com.cn/group1/M00/00/08/rBADF1tr5_KAZZAlAAAnPTqQltU7587.25","createDate":"2018-10-18","isAuth":"0","position":null},{"registerId":"2814770682218496","registerNickName":null,"registerPhoto":null,"createDate":"2018-10-18","isAuth":"0","position":null}]
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
             * registerId : 2859846872909824
             * registerNickName : 师师
             * registerPhoto : http://devfdfs.zntyydh.com/group1/M00/00/04/CkAillvFsj6AJ3TpACWTtMY0_mk927.jpg
             * createDate : 2018-10-18
             * isAuth : 1
             * position : 测试企业名称
             */

            private String registerId;
            private String registerNickName;
            private String registerPhoto;
            private String createDate;
            private String isAuth;
            private String position;

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

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getIsAuth() {
                return isAuth;
            }

            public void setIsAuth(String isAuth) {
                this.isAuth = isAuth;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }
        }
    }
}
