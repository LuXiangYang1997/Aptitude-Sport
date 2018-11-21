package com.huasport.smartsport.ui.pcenter.bean;

public class PersonalMyGradeDetailBean {


    /**
     * result : {"detail":{"groupName":null,"matchName":"全民镭战智能射击挑战赛","siteName":"线上","eventName":"全民镭战积分赛（7月起）"}}
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
         * detail : {"groupName":null,"matchName":"全民镭战智能射击挑战赛","siteName":"线上","eventName":"全民镭战积分赛（7月起）"}
         */

        private DetailBean detail;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            /**
             * groupName : null
             * matchName : 全民镭战智能射击挑战赛
             * siteName : 线上
             * eventName : 全民镭战积分赛（7月起）
             */
            private String keyName;
            private Object groupName;
            private String matchName;
            private String siteName;
            private String eventName;
            private String valueName;

            public String getValueName() {
                return valueName;
            }

            public void setValueName(String valueName) {
                this.valueName = valueName;
            }

            public String getKeyName() {
                return keyName;
            }

            public void setKeyName(String keyName) {
                this.keyName = keyName;
            }

            public Object getGroupName() {
                return groupName;
            }

            public void setGroupName(Object groupName) {
                this.groupName = groupName;
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

            public String getEventName() {
                return eventName;
            }

            public void setEventName(String eventName) {
                this.eventName = eventName;
            }
        }
    }
}
