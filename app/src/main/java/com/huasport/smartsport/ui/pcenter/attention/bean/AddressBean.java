package com.huasport.smartsport.ui.pcenter.attention.bean;

import java.io.Serializable;
import java.util.List;

public class AddressBean implements Serializable {


    /**
     * result : {"totalRow":2,"totalPage":1,"pageSize":10,"currentPage":1,"list":[{"addressCode":"201808151146334z1b01","realname":"测试人员","mobile":"13568022175","province":"四川省","city":"成都市","area":"双流区","address":"益州国际广场","isdefault":false},{"addressCode":"2018081415584500z435","realname":"测试","mobile":"13568099878","province":"四川省","city":"成都市","area":"双流区","address":"天府软件园G5二楼","isdefault":true}]}
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

    public static class ResultBean implements Serializable{
        /**
         * totalRow : 2
         * totalPage : 1
         * pageSize : 10
         * currentPage : 1
         * list : [{"addressCode":"201808151146334z1b01","realname":"测试人员","mobile":"13568022175","province":"四川省","city":"成都市","area":"双流区","address":"益州国际广场","isdefault":false},{"addressCode":"2018081415584500z435","realname":"测试","mobile":"13568099878","province":"四川省","city":"成都市","area":"双流区","address":"天府软件园G5二楼","isdefault":true}]
         */

        private int totalRow;
        private int totalPage;
        private int pageSize;
        private int currentPage;
        private List<ListBean> list;

        public int getTotalRow() {
            return totalRow;
        }

        public void setTotalRow(int totalRow) {
            this.totalRow = totalRow;
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

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * addressCode : 201808151146334z1b01
             * realname : 测试人员
             * mobile : 13568022175
             * province : 四川省
             * city : 成都市
             * area : 双流区
             * address : 益州国际广场
             * isdefault : false
             */

            private String addressCode;
            private String realname;
            private String mobile;
            private String province;
            private String city;
            private String area;
            private String address;
            private boolean isdefault;

            public String getAddressCode() {
                return addressCode;
            }

            public void setAddressCode(String addressCode) {
                this.addressCode = addressCode;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public boolean isIsdefault() {
                return isdefault;
            }

            public void setIsdefault(boolean isdefault) {
                this.isdefault = isdefault;
            }
        }
    }
}
