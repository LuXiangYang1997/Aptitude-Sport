package com.huasport.smartsport.ui.matchgrade.bean;

public class UserRankBean {


    /**
     * result : {"rank":{"playerName":"小师","competitionCode":"competition1535681737622","rankCode":"20180831110258ib3v05","playerCode":"20180818145128u8w201","competitionDate":1534575344000,"rank":8,"playerHeaderImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/0C/rBADF1t9Y-2ADvojACDAfdH_6e8113.png","score":"1422s","matchName":"全民镭战智能射击挑战赛"}}
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
         * rank : {"playerName":"小师","competitionCode":"competition1535681737622","rankCode":"20180831110258ib3v05","playerCode":"20180818145128u8w201","competitionDate":1534575344000,"rank":8,"playerHeaderImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/0C/rBADF1t9Y-2ADvojACDAfdH_6e8113.png","score":"1422s","matchName":"全民镭战智能射击挑战赛"}
         */

        private RankBean rank;

        public RankBean getRank() {
            return rank;
        }

        public void setRank(RankBean rank) {
            this.rank = rank;
        }

        public static class RankBean {
            /**
             * playerName : 小师
             * competitionCode : competition1535681737622
             * rankCode : 20180831110258ib3v05
             * playerCode : 20180818145128u8w201
             * competitionDate : 1534575344000
             * rank : 8
             * playerHeaderImg : http://zntyfdfs.efida.com.cn/group1/M00/00/0C/rBADF1t9Y-2ADvojACDAfdH_6e8113.png
             * score : 1422s
             * matchName : 全民镭战智能射击挑战赛
             */

            private String playerName;
            private String competitionCode;
            private String rankCode;
            private String playerCode;
            private long competitionDate;
            private int rank;
            private String playerHeaderImg;
            private String score;
            private String matchName;

            public String getPlayerName() {
                return playerName;
            }

            public void setPlayerName(String playerName) {
                this.playerName = playerName;
            }

            public String getCompetitionCode() {
                return competitionCode;
            }

            public void setCompetitionCode(String competitionCode) {
                this.competitionCode = competitionCode;
            }

            public String getRankCode() {
                return rankCode;
            }

            public void setRankCode(String rankCode) {
                this.rankCode = rankCode;
            }

            public String getPlayerCode() {
                return playerCode;
            }

            public void setPlayerCode(String playerCode) {
                this.playerCode = playerCode;
            }

            public long getCompetitionDate() {
                return competitionDate;
            }

            public void setCompetitionDate(long competitionDate) {
                this.competitionDate = competitionDate;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public String getPlayerHeaderImg() {
                return playerHeaderImg;
            }

            public void setPlayerHeaderImg(String playerHeaderImg) {
                this.playerHeaderImg = playerHeaderImg;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getMatchName() {
                return matchName;
            }

            public void setMatchName(String matchName) {
                this.matchName = matchName;
            }
        }
    }
}
