package com.huasport.smartsport.ui.pcenter.bean;

import java.util.List;

public class PersonalCompetitionBean {


    /**
     * result : {"scoreList":[{"score":"1:08","competitionCode":"competition1535640382732","timeStr":"2018-07-15 09:29:05","time":1531618145000}],"phone":"15882007879","bestScore":{"gender":"m","genderStr":"男","matchName":"模拟赛车中国冠军杯","matchCode":null,"siteName":"线上赛","groupName":null,"eventName":"线上赛","partTime":1531618016000,"partTimeStr":"2018-07-15 09:26:56","score":40,"scoreUnit":null,"scoreType":null,"scoreDesc":"0:40","palyerCode":"201807131013502401","palyerName":"程序3","competitionCode":"competition1535640382732","ranking":"-"},"headimgUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/0C/rBADF1uGIRuASZRwAB6tsHjsHk846.jpeg"}
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
         * scoreList : [{"score":"1:08","competitionCode":"competition1535640382732","timeStr":"2018-07-15 09:29:05","time":1531618145000}]
         * phone : 15882007879
         * bestScore : {"gender":"m","genderStr":"男","matchName":"模拟赛车中国冠军杯","matchCode":null,"siteName":"线上赛","groupName":null,"eventName":"线上赛","partTime":1531618016000,"partTimeStr":"2018-07-15 09:26:56","score":40,"scoreUnit":null,"scoreType":null,"scoreDesc":"0:40","palyerCode":"201807131013502401","palyerName":"程序3","competitionCode":"competition1535640382732","ranking":"-"}
         * headimgUrl : http://zntyfdfs.efida.com.cn/group1/M00/00/0C/rBADF1uGIRuASZRwAB6tsHjsHk846.jpeg
         */

        private String phone;
        private BestScoreBean bestScore;
        private String headimgUrl;
        private List<ScoreListBean> scoreList;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public BestScoreBean getBestScore() {
            return bestScore;
        }

        public void setBestScore(BestScoreBean bestScore) {
            this.bestScore = bestScore;
        }

        public String getHeadimgUrl() {
            return headimgUrl;
        }

        public void setHeadimgUrl(String headimgUrl) {
            this.headimgUrl = headimgUrl;
        }

        public List<ScoreListBean> getScoreList() {
            return scoreList;
        }

        public void setScoreList(List<ScoreListBean> scoreList) {
            this.scoreList = scoreList;
        }

        public static class BestScoreBean {
            /**
             * gender : m
             * genderStr : 男
             * matchName : 模拟赛车中国冠军杯
             * matchCode : null
             * siteName : 线上赛
             * groupName : null
             * eventName : 线上赛
             * partTime : 1531618016000
             * partTimeStr : 2018-07-15 09:26:56
             * score : 40.0
             * scoreUnit : null
             * scoreType : null
             * scoreDesc : 0:40
             * palyerCode : 201807131013502401
             * palyerName : 程序3
             * competitionCode : competition1535640382732
             * ranking : -
             */

            private String gender;
            private String genderStr;
            private String matchName;
            private Object matchCode;
            private String siteName;
            private Object groupName;
            private String eventName;
            private long partTime;
            private String partTimeStr;
            private double score;
            private Object scoreUnit;
            private Object scoreType;
            private String scoreDesc;
            private String palyerCode;
            private String palyerName;
            private String competitionCode;
            private String ranking;

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getGenderStr() {
                return genderStr;
            }

            public void setGenderStr(String genderStr) {
                this.genderStr = genderStr;
            }

            public String getMatchName() {
                return matchName;
            }

            public void setMatchName(String matchName) {
                this.matchName = matchName;
            }

            public Object getMatchCode() {
                return matchCode;
            }

            public void setMatchCode(Object matchCode) {
                this.matchCode = matchCode;
            }

            public String getSiteName() {
                return siteName;
            }

            public void setSiteName(String siteName) {
                this.siteName = siteName;
            }

            public Object getGroupName() {
                return groupName;
            }

            public void setGroupName(Object groupName) {
                this.groupName = groupName;
            }

            public String getEventName() {
                return eventName;
            }

            public void setEventName(String eventName) {
                this.eventName = eventName;
            }

            public long getPartTime() {
                return partTime;
            }

            public void setPartTime(long partTime) {
                this.partTime = partTime;
            }

            public String getPartTimeStr() {
                return partTimeStr;
            }

            public void setPartTimeStr(String partTimeStr) {
                this.partTimeStr = partTimeStr;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public Object getScoreUnit() {
                return scoreUnit;
            }

            public void setScoreUnit(Object scoreUnit) {
                this.scoreUnit = scoreUnit;
            }

            public Object getScoreType() {
                return scoreType;
            }

            public void setScoreType(Object scoreType) {
                this.scoreType = scoreType;
            }

            public String getScoreDesc() {
                return scoreDesc;
            }

            public void setScoreDesc(String scoreDesc) {
                this.scoreDesc = scoreDesc;
            }

            public String getPalyerCode() {
                return palyerCode;
            }

            public void setPalyerCode(String palyerCode) {
                this.palyerCode = palyerCode;
            }

            public String getPalyerName() {
                return palyerName;
            }

            public void setPalyerName(String palyerName) {
                this.palyerName = palyerName;
            }

            public String getCompetitionCode() {
                return competitionCode;
            }

            public void setCompetitionCode(String competitionCode) {
                this.competitionCode = competitionCode;
            }

            public String getRanking() {
                return ranking;
            }

            public void setRanking(String ranking) {
                this.ranking = ranking;
            }
        }

        public static class ScoreListBean {
            /**
             * score : 1:08
             * competitionCode : competition1535640382732
             * timeStr : 2018-07-15 09:29:05
             * time : 1531618145000
             */

            private String score;
            private String competitionCode;
            private String timeStr;
            private long time;

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getCompetitionCode() {
                return competitionCode;
            }

            public void setCompetitionCode(String competitionCode) {
                this.competitionCode = competitionCode;
            }

            public String getTimeStr() {
                return timeStr;
            }

            public void setTimeStr(String timeStr) {
                this.timeStr = timeStr;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }
        }
    }
}
