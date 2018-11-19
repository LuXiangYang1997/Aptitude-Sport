package com.huasport.smartsport.ui.matchapply.bean;

import java.util.List;

/**
 * Created by
 * on 2018/6/8.
 */

public class CompetitionListBean {


    /**
     * result : {"totalRow":8,"totalPage":1,"pageSize":10,"sites":[{"fieldName":"北京\u2014天津 ","siteCode":"field201806281039035695","startTime":"2018-10-02 00:00:00","endTime":"2018-10-02 23:00:00","fieldAddress":"北京\u2014天津 ","lon":"104.0710000","lat":"30.5630000","distance":"1527.26"},{"fieldName":"德州\u2014徐州","siteCode":"field201806281039032711","startTime":"2018-10-04 00:00:00","endTime":"2018-10-04 23:00:00","fieldAddress":"德州\u2014徐州","lon":"104.0710000","lat":"30.5630000","distance":"1527.26"},{"fieldName":"徐州\u2014淮安 ","siteCode":"field201806281039037945","startTime":"2018-10-05 00:00:00","endTime":"2018-10-05 23:00:00","fieldAddress":"徐州\u2014淮安 ","lon":"104.0710000","lat":"30.5630000","distance":"1527.26"},{"fieldName":"扬州\u2014常州","siteCode":"field201806281039039761","startTime":"2018-10-07 00:00:00","endTime":"2018-10-07 23:00:00","fieldAddress":"扬州\u2014常州","lon":"104.0710000","lat":"30.5630000","distance":"1527.26"},{"fieldName":"常州\u2014苏州","siteCode":"field201806281039031436","startTime":"2018-10-08 00:00:00","endTime":"2018-10-08 23:00:00","fieldAddress":"常州\u2014苏州","lon":"104.0710000","lat":"30.5630000","distance":"1527.26"},{"fieldName":"天津\u2014德州","siteCode":"field201806281039031487","startTime":"2018-10-03 00:00:00","endTime":"2018-10-03 23:00:00","fieldAddress":"天津\u2014德州","lon":"104.0620000","lat":"30.5250000","distance":"1530.94"},{"fieldName":"淮安\u2014扬州","siteCode":"field201806281039034118","startTime":"2018-10-06 00:00:00","endTime":"2018-10-06 23:00:00","fieldAddress":"淮安\u2014扬州","lon":"104.0620000","lat":"30.5250000","distance":"1530.94"},{"fieldName":"苏州\u2014杭州","siteCode":"field201806281039034962","startTime":"2018-10-09 00:00:00","endTime":"2018-10-09 23:00:00","fieldAddress":"苏州\u2014杭州","lon":"104.0620000","lat":"30.5250000","distance":"1530.94"}],"currentPage":1}
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
         * totalRow : 8
         * totalPage : 1
         * pageSize : 10
         * sites : [{"fieldName":"北京\u2014天津 ","siteCode":"field201806281039035695","startTime":"2018-10-02 00:00:00","endTime":"2018-10-02 23:00:00","fieldAddress":"北京\u2014天津 ","lon":"104.0710000","lat":"30.5630000","distance":"1527.26"},{"fieldName":"德州\u2014徐州","siteCode":"field201806281039032711","startTime":"2018-10-04 00:00:00","endTime":"2018-10-04 23:00:00","fieldAddress":"德州\u2014徐州","lon":"104.0710000","lat":"30.5630000","distance":"1527.26"},{"fieldName":"徐州\u2014淮安 ","siteCode":"field201806281039037945","startTime":"2018-10-05 00:00:00","endTime":"2018-10-05 23:00:00","fieldAddress":"徐州\u2014淮安 ","lon":"104.0710000","lat":"30.5630000","distance":"1527.26"},{"fieldName":"扬州\u2014常州","siteCode":"field201806281039039761","startTime":"2018-10-07 00:00:00","endTime":"2018-10-07 23:00:00","fieldAddress":"扬州\u2014常州","lon":"104.0710000","lat":"30.5630000","distance":"1527.26"},{"fieldName":"常州\u2014苏州","siteCode":"field201806281039031436","startTime":"2018-10-08 00:00:00","endTime":"2018-10-08 23:00:00","fieldAddress":"常州\u2014苏州","lon":"104.0710000","lat":"30.5630000","distance":"1527.26"},{"fieldName":"天津\u2014德州","siteCode":"field201806281039031487","startTime":"2018-10-03 00:00:00","endTime":"2018-10-03 23:00:00","fieldAddress":"天津\u2014德州","lon":"104.0620000","lat":"30.5250000","distance":"1530.94"},{"fieldName":"淮安\u2014扬州","siteCode":"field201806281039034118","startTime":"2018-10-06 00:00:00","endTime":"2018-10-06 23:00:00","fieldAddress":"淮安\u2014扬州","lon":"104.0620000","lat":"30.5250000","distance":"1530.94"},{"fieldName":"苏州\u2014杭州","siteCode":"field201806281039034962","startTime":"2018-10-09 00:00:00","endTime":"2018-10-09 23:00:00","fieldAddress":"苏州\u2014杭州","lon":"104.0620000","lat":"30.5250000","distance":"1530.94"}]
         * currentPage : 1
         */

        private int totalRow;
        private int totalPage;
        private int pageSize;
        private int currentPage;
        private List<SitesBean> sites;

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

        public List<SitesBean> getSites() {
            return sites;
        }

        public void setSites(List<SitesBean> sites) {
            this.sites = sites;
        }

        public static class SitesBean {
            /**
             * fieldName : 北京—天津
             * siteCode : field201806281039035695
             * startTime : 2018-10-02 00:00:00
             * endTime : 2018-10-02 23:00:00
             * fieldAddress : 北京—天津
             * lon : 104.0710000
             * lat : 30.5630000
             * distance : 1527.26
             */

            private String fieldName;
            private String siteCode;
            private String startTime;
            private String endTime;
            private String fieldAddress;
            private String lon;
            private String lat;
            private String distance;
            private boolean check = false;//checkbox是否选中
            private List<EventsBean> eventsBeanArrayList;

            public List<EventsBean> getEventsBeanArrayList() {
                return eventsBeanArrayList;
            }

            public void setEventsBeanArrayList(List<EventsBean> eventsBeanArrayList) {
                this.eventsBeanArrayList = eventsBeanArrayList;
            }

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }

            public String getSiteCode() {
                return siteCode;
            }

            public void setSiteCode(String siteCode) {
                this.siteCode = siteCode;
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

            public String getFieldAddress() {
                return fieldAddress;
            }

            public void setFieldAddress(String fieldAddress) {
                this.fieldAddress = fieldAddress;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }
    }
}
