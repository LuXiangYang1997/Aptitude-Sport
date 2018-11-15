package com.huasport.smartsport.ui.matchgrade.bean;

import java.util.List;

public class MatchGradeScoreHeaderBean {


    /**
     * result : {"list":[{"competitionCode":"competition1535640382732","competitionName":"智能赛车模拟赛","indexSort":0,"competitionDate":null,"events":"[\"item201807131112036352\"]","relationEvents":null,"groups":"[]","relationGroups":null,"areas":"[\"field201807131111090286\"]","relationAreas":null}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * competitionCode : competition1535640382732
             * competitionName : 智能赛车模拟赛
             * indexSort : 0
             * competitionDate : null
             * events : ["item201807131112036352"]
             * relationEvents : null
             * groups : []
             * relationGroups : null
             * areas : ["field201807131111090286"]
             * relationAreas : null
             */

            private String competitionCode;
            private String competitionName;
            private int indexSort;
            private Object competitionDate;
            private String events;
            private Object relationEvents;
            private String groups;
            private Object relationGroups;
            private String areas;
            private Object relationAreas;

            public String getCompetitionCode() {
                return competitionCode;
            }

            public void setCompetitionCode(String competitionCode) {
                this.competitionCode = competitionCode;
            }

            public String getCompetitionName() {
                return competitionName;
            }

            public void setCompetitionName(String competitionName) {
                this.competitionName = competitionName;
            }

            public int getIndexSort() {
                return indexSort;
            }

            public void setIndexSort(int indexSort) {
                this.indexSort = indexSort;
            }

            public Object getCompetitionDate() {
                return competitionDate;
            }

            public void setCompetitionDate(Object competitionDate) {
                this.competitionDate = competitionDate;
            }

            public String getEvents() {
                return events;
            }

            public void setEvents(String events) {
                this.events = events;
            }

            public Object getRelationEvents() {
                return relationEvents;
            }

            public void setRelationEvents(Object relationEvents) {
                this.relationEvents = relationEvents;
            }

            public String getGroups() {
                return groups;
            }

            public void setGroups(String groups) {
                this.groups = groups;
            }

            public Object getRelationGroups() {
                return relationGroups;
            }

            public void setRelationGroups(Object relationGroups) {
                this.relationGroups = relationGroups;
            }

            public String getAreas() {
                return areas;
            }

            public void setAreas(String areas) {
                this.areas = areas;
            }

            public Object getRelationAreas() {
                return relationAreas;
            }

            public void setRelationAreas(Object relationAreas) {
                this.relationAreas = relationAreas;
            }
        }
    }
}
