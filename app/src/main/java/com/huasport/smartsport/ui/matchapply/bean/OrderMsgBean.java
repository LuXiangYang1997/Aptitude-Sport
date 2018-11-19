package com.huasport.smartsport.ui.matchapply.bean;

import java.util.List;

/**
 * Created by bqj on 2018/6/29.
 */

public class OrderMsgBean {

    /**
     * result : {"payinfo":{"orderCode":"201806291824480007","orderStatus":"wait_pay","orderStatusDesc":"待支付","orderTime":"2018-06-29 18:25:32","remark":"报名费用","orderAmount":100,"orderAmountStr":"1.00","expiresTime":"2018-06-29 18:35:34","applys":[{"applyCode":"201806291824480606","gameName":"射击大赛","matchName":"全民镭战智能射击大赛","siteName":"苏州中心商场","matchGroupName":"镭战预赛","eventName":"海选","applyAmount":100,"applyAmountStr":"1.00","eventStartTime":"2018-06-10 00:00:00","eventEndTime":"2018-06-30 00:00:00"}]}}
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
         * payinfo : {"orderCode":"201806291824480007","orderStatus":"wait_pay","orderStatusDesc":"待支付","orderTime":"2018-06-29 18:25:32","remark":"报名费用","orderAmount":100,"orderAmountStr":"1.00","expiresTime":"2018-06-29 18:35:34","applys":[{"applyCode":"201806291824480606","gameName":"射击大赛","matchName":"全民镭战智能射击大赛","siteName":"苏州中心商场","matchGroupName":"镭战预赛","eventName":"海选","applyAmount":100,"applyAmountStr":"1.00","eventStartTime":"2018-06-10 00:00:00","eventEndTime":"2018-06-30 00:00:00"}]}
         */

        private PayinfoBean payinfo;

        public PayinfoBean getPayinfo() {
            return payinfo;
        }

        public void setPayinfo(PayinfoBean payinfo) {
            this.payinfo = payinfo;
        }

        public static class PayinfoBean {
            /**
             * orderCode : 201806291824480007
             * orderStatus : wait_pay
             * orderStatusDesc : 待支付
             * orderTime : 2018-06-29 18:25:32
             * remark : 报名费用
             * orderAmount : 100
             * orderAmountStr : 1.00
             * expiresTime : 2018-06-29 18:35:34
             * applys : [{"applyCode":"201806291824480606","gameName":"射击大赛","matchName":"全民镭战智能射击大赛","siteName":"苏州中心商场","matchGroupName":"镭战预赛","eventName":"海选","applyAmount":100,"applyAmountStr":"1.00","eventStartTime":"2018-06-10 00:00:00","eventEndTime":"2018-06-30 00:00:00"}]
             */

            private String orderCode;
            private String orderStatus;
            private String orderStatusDesc;
            private String orderTime;
            private String remark;
            private int orderAmount;
            private String orderAmountStr;
            private String expiresTime;
            private List<ApplysBean> applys;

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderStatusDesc() {
                return orderStatusDesc;
            }

            public void setOrderStatusDesc(String orderStatusDesc) {
                this.orderStatusDesc = orderStatusDesc;
            }

            public String getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(int orderAmount) {
                this.orderAmount = orderAmount;
            }

            public String getOrderAmountStr() {
                return orderAmountStr;
            }

            public void setOrderAmountStr(String orderAmountStr) {
                this.orderAmountStr = orderAmountStr;
            }

            public String getExpiresTime() {
                return expiresTime;
            }

            public void setExpiresTime(String expiresTime) {
                this.expiresTime = expiresTime;
            }

            public List<ApplysBean> getApplys() {
                return applys;
            }

            public void setApplys(List<ApplysBean> applys) {
                this.applys = applys;
            }

            public static class ApplysBean {
                /**
                 * applyCode : 201806291824480606
                 * gameName : 射击大赛
                 * matchName : 全民镭战智能射击大赛
                 * siteName : 苏州中心商场
                 * matchGroupName : 镭战预赛
                 * eventName : 海选
                 * applyAmount : 100
                 * applyAmountStr : 1.00
                 * eventStartTime : 2018-06-10 00:00:00
                 * eventEndTime : 2018-06-30 00:00:00
                 */

                private String applyCode;
                private String gameName;
                private String matchName;
                private String siteName;
                private String matchGroupName;
                private String eventName;
                private int applyAmount;
                private String applyAmountStr;
                private String eventStartTime;
                private String eventEndTime;
                private String orderAmountStr;

                public String getOrderAmountStr() {
                    return orderAmountStr;
                }

                public void setOrderAmountStr(String orderAmountStr) {
                    this.orderAmountStr = orderAmountStr;
                }

                public String getApplyCode() {
                    return applyCode;
                }

                public void setApplyCode(String applyCode) {
                    this.applyCode = applyCode;
                }

                public String getGameName() {
                    return gameName;
                }

                public void setGameName(String gameName) {
                    this.gameName = gameName;
                }

                public String getMatchName() {
                    return matchName;
                }

                public void setMatchName(String matchName) {
                    this.matchName = matchName;
                }

                public String getSiteName() {
                    return siteName;
                }

                public void setSiteName(String siteName) {
                    this.siteName = siteName;
                }

                public String getMatchGroupName() {
                    return matchGroupName;
                }

                public void setMatchGroupName(String matchGroupName) {
                    this.matchGroupName = matchGroupName;
                }

                public String getEventName() {
                    return eventName;
                }

                public void setEventName(String eventName) {
                    this.eventName = eventName;
                }

                public int getApplyAmount() {
                    return applyAmount;
                }

                public void setApplyAmount(int applyAmount) {
                    this.applyAmount = applyAmount;
                }

                public String getApplyAmountStr() {
                    return applyAmountStr;
                }

                public void setApplyAmountStr(String applyAmountStr) {
                    this.applyAmountStr = applyAmountStr;
                }

                public String getEventStartTime() {
                    return eventStartTime;
                }

                public void setEventStartTime(String eventStartTime) {
                    this.eventStartTime = eventStartTime;
                }

                public String getEventEndTime() {
                    return eventEndTime;
                }

                public void setEventEndTime(String eventEndTime) {
                    this.eventEndTime = eventEndTime;
                }
            }
        }
    }

}
