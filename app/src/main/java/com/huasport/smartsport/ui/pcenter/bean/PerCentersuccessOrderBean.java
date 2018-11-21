package com.huasport.smartsport.ui.pcenter.bean;

import com.huasport.smartsport.util.EmptyUtil;

public class PerCentersuccessOrderBean {


    /**
     * result : {"order":{"orderCode":"20180919210127hkm487","goodsCode":"G20180806000001","goodsTitle":"2018年全国智能体育大赛参赛奖牌","goodsPic":null,"goodsNum":200,"goodsPrice":1990,"extraMoney":0,"orderPrice":398000,"orderStatus":"success","orderTime":"2018-09-19 21:01:27","payTime":"2018-09-19 21:31:38","realname":"这么投放广告哈哈哈哈哈哈","mobile":"13651335062","province":"甘肃省","city":"北京市","area":"玄武区","address":"发广告哈哈哈哈","payOrderCode":"20180919210127juai88"}}
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
         * order : {"orderCode":"20180919210127hkm487","goodsCode":"G20180806000001","goodsTitle":"2018年全国智能体育大赛参赛奖牌","goodsPic":null,"goodsNum":200,"goodsPrice":1990,"extraMoney":0,"orderPrice":398000,"orderStatus":"success","orderTime":"2018-09-19 21:01:27","payTime":"2018-09-19 21:31:38","realname":"这么投放广告哈哈哈哈哈哈","mobile":"13651335062","province":"甘肃省","city":"北京市","area":"玄武区","address":"发广告哈哈哈哈","payOrderCode":"20180919210127juai88"}
         */

        private OrderBean order;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public static class OrderBean {
            /**
             * orderCode : 20180919210127hkm487
             * goodsCode : G20180806000001
             * goodsTitle : 2018年全国智能体育大赛参赛奖牌
             * goodsPic : null
             * goodsNum : 200
             * goodsPrice : 1990
             * extraMoney : 0
             * orderPrice : 398000
             * orderStatus : success
             * orderTime : 2018-09-19 21:01:27
             * payTime : 2018-09-19 21:31:38
             * realname : 这么投放广告哈哈哈哈哈哈
             * mobile : 13651335062
             * province : 甘肃省
             * city : 北京市
             * area : 玄武区
             * address : 发广告哈哈哈哈
             * payOrderCode : 20180919210127juai88
             */

            private String orderCode;
            private String goodsCode;
            private String goodsTitle;
            private Object goodsPic;
            private int goodsNum;
            private int goodsPrice;
            private int extraMoney;
            private int orderPrice;
            private String orderStatus;
            private String orderTime;
            private String payTime;
            private String realname;
            private String mobile;
            private String province;
            private String city;
            private String area;
            private String address;
            private String payOrderCode;
            private String payType;

            public String getPayType() {
                if (EmptyUtil.isEmpty(payType)) {
                    return "";
                }
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getOrderCode() {
                if (EmptyUtil.isEmpty(orderCode)) {
                    return "";
                }
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getGoodsCode() {
                if (EmptyUtil.isEmpty(goodsCode)) {
                    return "";
                }
                return goodsCode;
            }

            public void setGoodsCode(String goodsCode) {
                this.goodsCode = goodsCode;
            }

            public String getGoodsTitle() {
                if (EmptyUtil.isEmpty(goodsTitle)) {
                    return "";
                }
                return goodsTitle;
            }

            public void setGoodsTitle(String goodsTitle) {
                this.goodsTitle = goodsTitle;
            }

            public Object getGoodsPic() {
                if (EmptyUtil.isEmpty(goodsPic)) {
                    return "";
                }
                return goodsPic;
            }

            public void setGoodsPic(Object goodsPic) {
                this.goodsPic = goodsPic;
            }

            public int getGoodsNum() {

                return goodsNum;
            }

            public void setGoodsNum(int goodsNum) {
                this.goodsNum = goodsNum;
            }

            public int getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(int goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public int getExtraMoney() {
                return extraMoney;
            }

            public void setExtraMoney(int extraMoney) {
                this.extraMoney = extraMoney;
            }

            public int getOrderPrice() {
                return orderPrice;
            }

            public void setOrderPrice(int orderPrice) {
                this.orderPrice = orderPrice;
            }

            public String getOrderStatus() {
                if (EmptyUtil.isEmpty(orderStatus)) {
                    return "";
                }
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderTime() {
                if (EmptyUtil.isEmpty(orderTime)) {
                    return "";
                }
                return orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
            }

            public String getPayTime() {
                if (EmptyUtil.isEmpty(payTime)) {
                    return "";
                }
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public String getRealname() {
                if (EmptyUtil.isEmpty(realname)) {
                    return "";
                }
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getMobile() {
                if (EmptyUtil.isEmpty(mobile)) {
                    return "";
                }
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getProvince() {
                if (EmptyUtil.isEmpty(province)) {
                    return "";
                }
                return province;
            }

            public void setProvince(String province) {

                this.province = province;
            }

            public String getCity() {
                if (EmptyUtil.isEmpty(city)) {
                    return "";
                }
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                if (EmptyUtil.isEmpty(area)) {
                    return "";
                }
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                if (EmptyUtil.isEmpty(address)) {
                    return "";
                }
                return address;
            }

            public void setAddress(String address) {

                this.address = address;
            }

            public String getPayOrderCode() {
                if (EmptyUtil.isEmpty(payOrderCode)) {
                    return "";
                }
                return payOrderCode;
            }

            public void setPayOrderCode(String payOrderCode) {
                this.payOrderCode = payOrderCode;
            }
        }
    }
}
