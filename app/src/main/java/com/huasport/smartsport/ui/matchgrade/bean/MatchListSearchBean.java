package com.huasport.smartsport.ui.matchgrade.bean;

import java.util.List;

public class MatchListSearchBean {


    /**
     * result : {"totalRow":1,"totalPage":1,"pageSize":10,"currentPage":1,"list":[{"matchCode":"match201807131110248047","matchName":"智能模拟赛车竞技赛","matchImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tO0r2AXY9AAAUsa5Repjg633.png","startTime":1531584000000,"endTime":1540915200000}]}
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
         * totalRow : 1
         * totalPage : 1
         * pageSize : 10
         * currentPage : 1
         * list : [{"matchCode":"match201807131110248047","matchName":"智能模拟赛车竞技赛","matchImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tO0r2AXY9AAAUsa5Repjg633.png","startTime":1531584000000,"endTime":1540915200000}]
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
             * matchCode : match201807131110248047
             * matchName : 智能模拟赛车竞技赛
             * matchImg : http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tO0r2AXY9AAAUsa5Repjg633.png
             * startTime : 1531584000000
             * endTime : 1540915200000
             */

            private String matchCode;
            private String matchName;
            private String matchImg;
            private long startTime;
            private long endTime;

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
