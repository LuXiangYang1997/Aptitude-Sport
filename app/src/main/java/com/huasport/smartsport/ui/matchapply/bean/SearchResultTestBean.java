package com.huasport.smartsport.ui.matchapply.bean;

import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/12.
 */

public class SearchResultTestBean{


    /**
     * result : {"totalRow":1,"totalPage":1,"pageSize":10,"currentPage":1,"list":[{"gameCode":"project201806141710317965","matchCode":"match201806141712392297","matchName":"成都游泳大赛","poster":"http://zntyfdfs.efida.com.cn/group1/M00/00/00/rBADF1siMQaAOsVzAAL63rqDJ_c84.jpeg","regTime":"2018-06-21 00:00:00","startTime":"2018-06-15 00:00:00","endTime":"2018-06-28 00:00:00"}]}
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
         * list : [{"gameCode":"project201806141710317965","matchCode":"match201806141712392297","matchName":"成都游泳大赛","poster":"http://zntyfdfs.efida.com.cn/group1/M00/00/00/rBADF1siMQaAOsVzAAL63rqDJ_c84.jpeg","regTime":"2018-06-21 00:00:00","startTime":"2018-06-15 00:00:00","endTime":"2018-06-28 00:00:00"}]
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
             * gameCode : project201806141710317965
             * matchCode : match201806141712392297
             * matchName : 成都游泳大赛
             * poster : http://zntyfdfs.efida.com.cn/group1/M00/00/00/rBADF1siMQaAOsVzAAL63rqDJ_c84.jpeg
             * regTime : 2018-06-21 00:00:00
             * startTime : 2018-06-15 00:00:00
             * endTime : 2018-06-28 00:00:00
             */


            private String gameCode;

            private String matchCode;

            private String matchName;

            private String poster;

            private String regTime;

            private String startTime;

            private String endTime;

            public String getGameCode() {
                return gameCode;
            }

            public void setGameCode(String gameCode) {
                this.gameCode = gameCode;
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

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }

            public String getRegTime() {
                return regTime;
            }

            public void setRegTime(String regTime) {
                this.regTime = regTime;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }
        }
    }
}
