package com.huasport.smartsport.ui.matchgrade.bean;

import java.util.List;

public class MatchGradeListBean {


    /**
     * result : {"total":1,"matchs":[{"matchCode":"match201807171039123728","matchName":"衡泰信最近洞超级大奖赛","matchImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNTXKAK-BEAAwB0VksMIQ837.jpg","startTime":1530979200000,"endTime":1532966400000}],"totalPage":1,"pageSize":10,"currentPage":1}
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
         * total : 1
         * matchs : [{"matchCode":"match201807171039123728","matchName":"衡泰信最近洞超级大奖赛","matchImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNTXKAK-BEAAwB0VksMIQ837.jpg","startTime":1530979200000,"endTime":1532966400000}]
         * totalPage : 1
         * pageSize : 10
         * currentPage : 1
         */

        private int total;
        private int totalPage;
        private int pageSize;
        private int currentPage;
        private List<MatchsBean> matchs;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
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

        public List<MatchsBean> getMatchs() {
            return matchs;
        }

        public void setMatchs(List<MatchsBean> matchs) {
            this.matchs = matchs;
        }

        public static class MatchsBean {
            /**
             * matchCode : match201807171039123728
             * matchName : 衡泰信最近洞超级大奖赛
             * matchImg : http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNTXKAK-BEAAwB0VksMIQ837.jpg
             * startTime : 1530979200000
             * endTime : 1532966400000
             */

            private String matchCode;
            private String matchName;
            private String matchImg;
            private long startTime;
            private long endTime;
            private boolean select;

            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
            }

            public String getMatchCode() {
                return matchCode;
            }

            public void setMatchCode(String matchCode) {
                this.matchCode = matchCode;
            }

            public String getMatchName() {
                return matchName;
            }

            public void setMatchName(String matchName) {
                this.matchName = matchName;
            }

            public String getMatchImg() {
                return matchImg;
            }

            public void setMatchImg(String matchImg) {
                this.matchImg = matchImg;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }
        }
    }
}
