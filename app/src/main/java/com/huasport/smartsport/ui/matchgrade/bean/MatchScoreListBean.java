package com.huasport.smartsport.ui.matchgrade.bean;

import java.util.List;

public class MatchScoreListBean {


    /**
     * result : {"allRow":17,"totalPage":2,"pageSize":10,"list":[{"playerName":"马鑫涛","competitionCode":"competition1535681737622","rankCode":"20180831110258xv7z01","playerCode":"20180824235519k9u316","competitionDate":1535455073000,"rank":1,"playerHeaderImg":null,"score":"6373s","matchName":"全民镭战智能射击挑战赛"},{"playerName":"马鑫涛","competitionCode":"competition1535681737622","rankCode":"20180831110500vemx41","playerCode":"20180824235519k9u316","competitionDate":1535455073000,"rank":1,"playerHeaderImg":null,"score":"6373s","matchName":"全民镭战智能射击挑战赛"},{"playerName":"胡栋磊","competitionCode":"competition1535681737622","rankCode":"20180831110258gy6d01","playerCode":"201808171001501mf710","competitionDate":1535514141000,"rank":5,"playerHeaderImg":null,"score":"2313s","matchName":"全民镭战智能射击挑战赛"},{"playerName":"胡栋磊","competitionCode":"competition1535681737622","rankCode":"20180831110500j0b937","playerCode":"201808171001501mf710","competitionDate":1535514141000,"rank":5,"playerHeaderImg":null,"score":"2313s","matchName":"全民镭战智能射击挑战赛"},{"playerName":"程序","competitionCode":"competition1535681737622","rankCode":"20180831110258166501","playerCode":"201806251611099255","competitionDate":1529915098000,"rank":6,"playerHeaderImg":null,"score":"镭战test","matchName":"全民镭战智能射击大赛"},{"playerName":"程序(合作方）","competitionCode":"competition1535681737622","rankCode":"20180831110500ym5038","playerCode":"201806251611099255","competitionDate":1529915098000,"rank":6,"playerHeaderImg":null,"score":"镭战test","matchName":"全民镭战智能射击大赛"},{"playerName":"赵丽","competitionCode":"competition1535681737622","rankCode":"20180831110258bz3001","playerCode":"20180717141029bdz919","competitionDate":1532597685000,"rank":7,"playerHeaderImg":null,"score":"1515s","matchName":"全民镭战智能射击大赛"},{"playerName":"赵丽","competitionCode":"competition1535681737622","rankCode":"20180831110500x6fn39","playerCode":"20180717141029bdz919","competitionDate":1532597685000,"rank":7,"playerHeaderImg":null,"score":"1515s","matchName":"全民镭战智能射击大赛"},{"playerName":"小师","competitionCode":"competition1535681737622","rankCode":"20180831110258ib3v05","playerCode":"20180818145128u8w201","competitionDate":1534575344000,"rank":8,"playerHeaderImg":null,"score":"1422s","matchName":"全民镭战智能射击挑战赛"},{"playerName":"小师","competitionCode":"competition1535681737622","rankCode":"20180831110258ib3v06","playerCode":"20180818145128u8w201","competitionDate":1534575344000,"rank":8,"playerHeaderImg":null,"score":"1422s","matchName":"全民镭战智能射击挑战赛"}],"currentPage":1}
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
         * allRow : 17
         * totalPage : 2
         * pageSize : 10
         * list : [{"playerName":"马鑫涛","competitionCode":"competition1535681737622","rankCode":"20180831110258xv7z01","playerCode":"20180824235519k9u316","competitionDate":1535455073000,"rank":1,"playerHeaderImg":null,"score":"6373s","matchName":"全民镭战智能射击挑战赛"},{"playerName":"马鑫涛","competitionCode":"competition1535681737622","rankCode":"20180831110500vemx41","playerCode":"20180824235519k9u316","competitionDate":1535455073000,"rank":1,"playerHeaderImg":null,"score":"6373s","matchName":"全民镭战智能射击挑战赛"},{"playerName":"胡栋磊","competitionCode":"competition1535681737622","rankCode":"20180831110258gy6d01","playerCode":"201808171001501mf710","competitionDate":1535514141000,"rank":5,"playerHeaderImg":null,"score":"2313s","matchName":"全民镭战智能射击挑战赛"},{"playerName":"胡栋磊","competitionCode":"competition1535681737622","rankCode":"20180831110500j0b937","playerCode":"201808171001501mf710","competitionDate":1535514141000,"rank":5,"playerHeaderImg":null,"score":"2313s","matchName":"全民镭战智能射击挑战赛"},{"playerName":"程序","competitionCode":"competition1535681737622","rankCode":"20180831110258166501","playerCode":"201806251611099255","competitionDate":1529915098000,"rank":6,"playerHeaderImg":null,"score":"镭战test","matchName":"全民镭战智能射击大赛"},{"playerName":"程序(合作方）","competitionCode":"competition1535681737622","rankCode":"20180831110500ym5038","playerCode":"201806251611099255","competitionDate":1529915098000,"rank":6,"playerHeaderImg":null,"score":"镭战test","matchName":"全民镭战智能射击大赛"},{"playerName":"赵丽","competitionCode":"competition1535681737622","rankCode":"20180831110258bz3001","playerCode":"20180717141029bdz919","competitionDate":1532597685000,"rank":7,"playerHeaderImg":null,"score":"1515s","matchName":"全民镭战智能射击大赛"},{"playerName":"赵丽","competitionCode":"competition1535681737622","rankCode":"20180831110500x6fn39","playerCode":"20180717141029bdz919","competitionDate":1532597685000,"rank":7,"playerHeaderImg":null,"score":"1515s","matchName":"全民镭战智能射击大赛"},{"playerName":"小师","competitionCode":"competition1535681737622","rankCode":"20180831110258ib3v05","playerCode":"20180818145128u8w201","competitionDate":1534575344000,"rank":8,"playerHeaderImg":null,"score":"1422s","matchName":"全民镭战智能射击挑战赛"},{"playerName":"小师","competitionCode":"competition1535681737622","rankCode":"20180831110258ib3v06","playerCode":"20180818145128u8w201","competitionDate":1534575344000,"rank":8,"playerHeaderImg":null,"score":"1422s","matchName":"全民镭战智能射击挑战赛"}]
         * currentPage : 1
         */

        private int allRow;
        private int totalPage;
        private int pageSize;
        private int currentPage;
        private List<ListBean> list;

        public int getAllRow() {
            return allRow;
        }

        public void setAllRow(int allRow) {
            this.allRow = allRow;
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
             * playerName : 马鑫涛
             * competitionCode : competition1535681737622
             * rankCode : 20180831110258xv7z01
             * playerCode : 20180824235519k9u316
             * competitionDate : 1535455073000
             * rank : 1
             * playerHeaderImg : null
             * score : 6373s
             * matchName : 全民镭战智能射击挑战赛
             */

            private String playerName;
            private String competitionCode;
            private String rankCode;
            private String playerCode;
            private long competitionDate;
            private int rank;
            private Object playerHeaderImg;
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

            public Object getPlayerHeaderImg() {
                return playerHeaderImg;
            }

            public void setPlayerHeaderImg(Object playerHeaderImg) {
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
