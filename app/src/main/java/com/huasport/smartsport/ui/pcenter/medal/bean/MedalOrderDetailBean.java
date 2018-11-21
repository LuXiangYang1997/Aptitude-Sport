package com.huasport.smartsport.ui.pcenter.medal.bean;

public class MedalOrderDetailBean {


    /**
     * result : {"order":{"orderCode":"20180816184317902u19","goodsCode":"G20180806000001","goodsTitle":"2018年全国智能体育大赛参赛奖牌","goodsPic":null,"goodsNum":1,"goodsPrice":1,"extraMoney":1,"orderPrice":2,"orderStatus":"wait_pay","orderTime":"2018-08-16 18:43:18","payTime":"","realname":"123456","mobile":"15047037232","province":"北京市","city":"北京市","area":"崇文区","address":"回龙观","payOrderCode":"20180816184317k94a20"}}
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
         * order : {"orderCode":"20180816184317902u19","goodsCode":"G20180806000001","goodsTitle":"2018年全国智能体育大赛参赛奖牌","goodsPic":null,"goodsNum":1,"goodsPrice":1,"extraMoney":1,"orderPrice":2,"orderStatus":"wait_pay","orderTime":"2018-08-16 18:43:18","payTime":"","realname":"123456","mobile":"15047037232","province":"北京市","city":"北京市","area":"崇文区","address":"回龙观","payOrderCode":"20180816184317k94a20"}
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
             * orderCode : 20180816184317902u19
             * goodsCode : G20180806000001
             * goodsTitle : 2018年全国智能体育大赛参赛奖牌
             * goodsPic : null
             * goodsNum : 1
             * goodsPrice : 1
             * extraMoney : 1
             * orderPrice : 2
             * orderStatus : wait_pay
             * orderTime : 2018-08-16 18:43:18
             * payTime :
             * realname : 123456
             * mobile : 15047037232
             * province : 北京市
             * city : 北京市
             * area : 崇文区
             * address : 回龙观
             * payOrderCode : 20180816184317k94a20
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

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getGoodsCode() {
                return goodsCode;
            }

            public void setGoodsCode(String goodsCode) {
                this.goodsCode = goodsCode;
            }

            public String getGoodsTitle() {
                return goodsTitle;
            }

            public void setGoodsTitle(String goodsTitle) {
                this.goodsTitle = goodsTitle;
            }

            public Object getGoodsPic() {
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
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
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

            public String getPayOrderCode() {
                return payOrderCode;
            }

            public void setPayOrderCode(String payOrderCode) {
                this.payOrderCode = payOrderCode;
            }
        }
    }
}
