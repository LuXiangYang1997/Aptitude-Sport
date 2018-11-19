package com.huasport.smartsport.ui.matchapply.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/9.
 */

public class GroupEventsBean implements Serializable {


    /**
     * result : {"groups":[{"sortIndex":0,"groupCode":null,"groupName":"项目名称","events":[{"itemCode":"itemcode201807251451077908","itemName":"接力跑团体赛","startTime":"2018-07-24 14:00:00","endTime":"2018-07-31 05:00:00","entryFee":0,"entryFeeStr":"0.00","surplusQuota":"无人员限制","canApply":true,"personLimit":0,"itemType":"group","groupLimit":5,"isValid":true}]}]}
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

    public static class ResultBean implements Serializable{
        private List<GroupsBean> groups;

        public List<GroupsBean> getGroups() {
            return groups;
        }

        public void setGroups(List<GroupsBean> groups) {
            this.groups = groups;
        }

        public static class GroupsBean {
            /**
             * sortIndex : 0
             * groupCode : null
             * groupName : 项目名称
             * events : [{"itemCode":"itemcode201807251451077908","itemName":"接力跑团体赛","startTime":"2018-07-24 14:00:00","endTime":"2018-07-31 05:00:00","entryFee":0,"entryFeeStr":"0.00","surplusQuota":"无人员限制","canApply":true,"personLimit":0,"itemType":"group","groupLimit":5,"isValid":true}]
             */

            private int sortIndex;
            private Object groupCode;
            private String groupName;
            private List<EventsBean> events;

            public int getSortIndex() {
                return sortIndex;
            }

            public void setSortIndex(int sortIndex) {
                this.sortIndex = sortIndex;
            }

            public Object getGroupCode() {
                return groupCode;
            }

            public void setGroupCode(Object groupCode) {
                this.groupCode = groupCode;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public List<EventsBean> getEvents() {
                return events;
            }

            public void setEvents(List<EventsBean> events) {
                this.events = events;
            }

            public static class EventsBean implements Serializable{
                /**
                 * itemCode : itemcode201807251451077908
                 * itemName : 接力跑团体赛
                 * startTime : 2018-07-24 14:00:00
                 * endTime : 2018-07-31 05:00:00
                 * entryFee : 0
                 * entryFeeStr : 0.00
                 * surplusQuota : 无人员限制
                 * canApply : true
                 * personLimit : 0
                 * itemType : group
                 * groupLimit : 5
                 * isValid : true
                 */

                private String itemCode;
                private String itemName;
                private String startTime;
                private String endTime;
                private int entryFee;
                private String entryFeeStr;
                private String surplusQuota;
                private boolean canApply;
                private int personLimit;
                private String itemType;
                private int groupLimit;
                private boolean isValid;
                private boolean checkbox = false;
                private ProgramMessageBean programMessageBean;

                public ProgramMessageBean getProgramMessageBean() {
                    return programMessageBean;
                }

                public void setProgramMessageBean(ProgramMessageBean programMessageBean) {
                    this.programMessageBean = programMessageBean;
                }

                public boolean isCheckbox() {
                    return checkbox;
                }

                public void setCheckbox(boolean checkbox) {
                    this.checkbox = checkbox;
                }
                public String getItemCode() {
                    return itemCode;
                }

                public void setItemCode(String itemCode) {
                    this.itemCode = itemCode;
                }

                public String getItemName() {
                    return itemName;
                }

                public void setItemName(String itemName) {
                    this.itemName = itemName;
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

                public int getEntryFee() {
                    return entryFee;
                }

                public void setEntryFee(int entryFee) {
                    this.entryFee = entryFee;
                }

                public String getEntryFeeStr() {
                    return entryFeeStr;
                }

                public void setEntryFeeStr(String entryFeeStr) {
                    this.entryFeeStr = entryFeeStr;
                }

                public String getSurplusQuota() {
                    return surplusQuota;
                }

                public void setSurplusQuota(String surplusQuota) {
                    this.surplusQuota = surplusQuota;
                }

                public boolean isCanApply() {
                    return canApply;
                }

                public void setCanApply(boolean canApply) {
                    this.canApply = canApply;
                }

                public int getPersonLimit() {
                    return personLimit;
                }

                public void setPersonLimit(int personLimit) {
                    this.personLimit = personLimit;
                }

                public String getItemType() {
                    return itemType;
                }

                public void setItemType(String itemType) {
                    this.itemType = itemType;
                }

                public int getGroupLimit() {
                    return groupLimit;
                }

                public void setGroupLimit(int groupLimit) {
                    this.groupLimit = groupLimit;
                }

                public boolean isIsValid() {
                    return isValid;
                }

                public void setIsValid(boolean isValid) {
                    this.isValid = isValid;
                }
            }
        }
    }
}
