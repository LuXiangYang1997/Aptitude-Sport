package com.huasport.smartsport.ui.matchgrade.bean;

import java.util.List;

public class MatchEventListBean {


    /**
     * result : {"resultList":[{"competitionCode":"201806292123011398","areaCode":"field201807171039411710","area":"线上","eventCode":"item201808161626258688","event":"总杆数","groupCode":"","group":""},{"competitionCode":"competition1534405798662","areaCode":"field201807171039411710","area":"线上","eventCode":"item201808161625547131","event":"最近洞","groupCode":null,"group":null}]}
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
        private List<ResultListBean> resultList;

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * competitionCode : 201806292123011398
             * areaCode : field201807171039411710
             * area : 线上
             * eventCode : item201808161626258688
             * event : 总杆数
             * groupCode :
             * group :
             */

            private String competitionCode;
            private String areaCode;
            private String area;
            private String eventCode;
            private String event;
            private String groupCode;
            private String group;
            private String matchCode;
            private String matchName;

            public String getMatchCode() {
                return matchCode;
            }

            public String getMatchName() {
                return matchName;
            }

            public void setMatchName(String matchName) {
                this.matchName = matchName;
            }

            public String getCompetitionCode() {
                return competitionCode;
            }

            public void setCompetitionCode(String competitionCode) {
                this.competitionCode = competitionCode;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getEventCode() {
                return eventCode;
            }

            public void setEventCode(String eventCode) {
                this.eventCode = eventCode;
            }

            public String getEvent() {
                return event;
            }

            public void setEvent(String event) {
                this.event = event;
            }

            public String getGroupCode() {
                return groupCode;
            }

            public void setGroupCode(String groupCode) {
                this.groupCode = groupCode;
            }

            public String getGroup() {
                return group;
            }

            public void setGroup(String group) {
                this.group = group;
            }

            public void setMatchCode(String matchCode) {
                this.matchCode = matchCode;
            }
        }
    }
}
