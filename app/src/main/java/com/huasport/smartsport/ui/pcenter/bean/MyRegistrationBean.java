package com.huasport.smartsport.ui.pcenter.bean;

import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/13.
 */

public class MyRegistrationBean {


    /**
     * result : {"totalRow":70,"totalPage":7,"pageSize":10,"currentPage":1,"list":[{"orderCode":"20180805135229vn9w30","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"乐雪杯智能滑雪联赛预赛","gameName":"智能滑雪","orderTime":"2018-08-05 13:52:29","itemName":"男子组（9-11岁）_双板滑雪比...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/03/CkAihlte5WWAaD8DAACgsWIwX4c830.jpg","eventType":"personal"},{"orderCode":"20180805135215k1ig27","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-05 13:52:16","itemName":"专业组_Bowling miss...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"personal"},{"orderCode":"2018080513520837n725","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-05 13:52:08","itemName":"专业组_Bowling miss...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"personal"},{"orderCode":"2018080513232708zd23","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-05 13:23:27","itemName":"专业组_Soccer  足球赛 ...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"group"},{"orderCode":"20180805131652v9iz21","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-05 13:16:52","itemName":"专业组_Soccer  足球赛 ...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"group"},{"orderCode":"20180803154140nkh047","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能足球预赛","gameName":"智能足球","orderTime":"2018-08-03 15:41:40","itemName":"挑战赛_\u201c一球成名\u201d智能足球挑战赛","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1tZiuOABYSgAACM6pHQYXs839.jpg","eventType":"personal"},{"orderCode":"201808021127157vnu93","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-02 11:27:16","itemName":"专业组_Creative Des...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"group"},{"orderCode":"2018080211234515ah80","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-02 11:23:46","itemName":"专业组_Creative Des...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"group"},{"orderCode":"2018080211033863bc01","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能跳绳全国挑战赛","gameName":"智能跳绳","orderTime":"2018-08-02 11:03:38","itemName":"30秒交互跳","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/03/CkAih1tfDAKAb3_tAACdInzCblQ804.jpg","eventType":"group"},{"orderCode":"20180802105444b35789","orderStatus":"success","orderStatusDesc":"成功","matchName":"智能跳绳全国挑战赛","gameName":"智能跳绳","orderTime":"2018-08-02 10:55:07","itemName":"30秒单摇跳","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/03/CkAih1tfDAKAb3_tAACdInzCblQ804.jpg","eventType":"personal"}]}
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
         * totalRow : 70
         * totalPage : 7
         * pageSize : 10
         * currentPage : 1
         * list : [{"orderCode":"20180805135229vn9w30","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"乐雪杯智能滑雪联赛预赛","gameName":"智能滑雪","orderTime":"2018-08-05 13:52:29","itemName":"男子组（9-11岁）_双板滑雪比...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/03/CkAihlte5WWAaD8DAACgsWIwX4c830.jpg","eventType":"personal"},{"orderCode":"20180805135215k1ig27","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-05 13:52:16","itemName":"专业组_Bowling miss...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"personal"},{"orderCode":"2018080513520837n725","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-05 13:52:08","itemName":"专业组_Bowling miss...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"personal"},{"orderCode":"2018080513232708zd23","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-05 13:23:27","itemName":"专业组_Soccer  足球赛 ...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"group"},{"orderCode":"20180805131652v9iz21","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-05 13:16:52","itemName":"专业组_Soccer  足球赛 ...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"group"},{"orderCode":"20180803154140nkh047","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能足球预赛","gameName":"智能足球","orderTime":"2018-08-03 15:41:40","itemName":"挑战赛_\u201c一球成名\u201d智能足球挑战赛","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1tZiuOABYSgAACM6pHQYXs839.jpg","eventType":"personal"},{"orderCode":"201808021127157vnu93","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-02 11:27:16","itemName":"专业组_Creative Des...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"group"},{"orderCode":"2018080211234515ah80","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能运动青少年机器人竞技赛（预赛）","gameName":"智能运动机器人","orderTime":"2018-08-02 11:23:46","itemName":"专业组_Creative Des...","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/02/CkAih1taV4iAVl8VAAC4n5kI0QQ712.jpg","eventType":"group"},{"orderCode":"2018080211033863bc01","orderStatus":"wait_complete","orderStatusDesc":"待完善","matchName":"智能跳绳全国挑战赛","gameName":"智能跳绳","orderTime":"2018-08-02 11:03:38","itemName":"30秒交互跳","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/03/CkAih1tfDAKAb3_tAACdInzCblQ804.jpg","eventType":"group"},{"orderCode":"20180802105444b35789","orderStatus":"success","orderStatusDesc":"成功","matchName":"智能跳绳全国挑战赛","gameName":"智能跳绳","orderTime":"2018-08-02 10:55:07","itemName":"30秒单摇跳","matchImg":"http://fdfs.zntyydh.com:81/group1/M00/00/03/CkAih1tfDAKAb3_tAACdInzCblQ804.jpg","eventType":"personal"}]
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

        public static class ListBean {
            /**
             * orderCode : 20180805135229vn9w30
             * orderStatus : wait_complete
             * orderStatusDesc : 待完善
             * matchName : 乐雪杯智能滑雪联赛预赛
             * gameName : 智能滑雪
             * orderTime : 2018-08-05 13:52:29
             * itemName : 男子组（9-11岁）_双板滑雪比...
             * matchImg : http://fdfs.zntyydh.com:81/group1/M00/00/03/CkAihlte5WWAaD8DAACgsWIwX4c830.jpg
             * eventType : personal
             */

            private String orderCode;
            private String orderStatus;
            private String orderStatusDesc;
            private String matchName;
            private String gameName;
            private String orderTime;
            private String itemName;
            private String matchImg;
            private String eventType;

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

            public String getMatchName() {
                return matchName;
            }

            public void setMatchName(String matchName) {
                this.matchName = matchName;
            }

            public String getGameName() {
                return gameName;
            }

            public void setGameName(String gameName) {
                this.gameName = gameName;
            }

            public String getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getMatchImg() {
                return matchImg;
            }

            public void setMatchImg(String matchImg) {
                this.matchImg = matchImg;
            }

            public String getEventType() {
                return eventType;
            }

            public void setEventType(String eventType) {
                this.eventType = eventType;
            }
        }
    }
}
