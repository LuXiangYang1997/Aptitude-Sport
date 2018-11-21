package com.huasport.smartsport.ui.pcenter.medal.bean;

public class MyOrderAddressBean {


    /**
     * result : {"address":{"addressCode":"20180816162647g2an10","realname":"123456","mobile":"15047037232","province":"北京市","city":"北京市","area":"崇文区","address":"回龙观","isdefault":true}}
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
         * address : {"addressCode":"20180816162647g2an10","realname":"123456","mobile":"15047037232","province":"北京市","city":"北京市","area":"崇文区","address":"回龙观","isdefault":true}
         */

        private AddressBean address;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public static class AddressBean {
            /**
             * addressCode : 20180816162647g2an10
             * realname : 123456
             * mobile : 15047037232
             * province : 北京市
             * city : 北京市
             * area : 崇文区
             * address : 回龙观
             * isdefault : true
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
