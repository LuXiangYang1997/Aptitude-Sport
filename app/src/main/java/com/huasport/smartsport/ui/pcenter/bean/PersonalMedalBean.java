package com.huasport.smartsport.ui.pcenter.bean;

import java.util.List;

public class PersonalMedalBean {


    /**
     * result : {"allRow":1,"totalPage":1,"pageSize":10,"share":{"picUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/10/rBADF1ugs8SATDpNAABNjjKZHYo219.png","shareUrl":"http://wx.zntyydh.com/game/type","title":"首届全国智能体育大赛","content":"我已成功完成首届全国智能体育大赛项目，快来和我一起领取奖章吧。"},"scoreCert":[{"scoreCertCode":"20180911181224f9xc13","scoreCertSn":"201800000044","certPicUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/0F/rBADF1uXlQ-AeVnrAAiuEWsYbWU082.png","certShortPicuUrl":null,"matchName":"衡泰信城市高尔夫联赛","comtitionName":null,"eventName":"城市高尔夫联赛-七月","partTime":1536652120000,"partTimeStr":"2018-09-11 15:48:40"}],"currentPage":1}
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
         * allRow : 1
         * totalPage : 1
         * pageSize : 10
         * share : {"picUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/10/rBADF1ugs8SATDpNAABNjjKZHYo219.png","shareUrl":"http://wx.zntyydh.com/game/type","title":"首届全国智能体育大赛","content":"我已成功完成首届全国智能体育大赛项目，快来和我一起领取奖章吧。"}
         * scoreCert : [{"scoreCertCode":"20180911181224f9xc13","scoreCertSn":"201800000044","certPicUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/0F/rBADF1uXlQ-AeVnrAAiuEWsYbWU082.png","certShortPicuUrl":null,"matchName":"衡泰信城市高尔夫联赛","comtitionName":null,"eventName":"城市高尔夫联赛-七月","partTime":1536652120000,"partTimeStr":"2018-09-11 15:48:40"}]
         * currentPage : 1
         */

        private int allRow;
        private int totalPage;
        private int pageSize;
        private ShareBean share;
        private int currentPage;
        private List<ScoreCertBean> scoreCert;

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

        public ShareBean getShare() {
            return share;
        }

        public void setShare(ShareBean share) {
            this.share = share;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<ScoreCertBean> getScoreCert() {
            return scoreCert;
        }

        public void setScoreCert(List<ScoreCertBean> scoreCert) {
            this.scoreCert = scoreCert;
        }

        public static class ShareBean {
            /**
             * picUrl : http://zntyfdfs.efida.com.cn/group1/M00/00/10/rBADF1ugs8SATDpNAABNjjKZHYo219.png
             * shareUrl : http://wx.zntyydh.com/game/type
             * title : 首届全国智能体育大赛
             * content : 我已成功完成首届全国智能体育大赛项目，快来和我一起领取奖章吧。
             */

            private String picUrl;
            private String shareUrl;
            private String title;
            private String content;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class ScoreCertBean {
            /**
             * scoreCertCode : 20180911181224f9xc13
             * scoreCertSn : 201800000044
             * certPicUrl : http://zntyfdfs.efida.com.cn/group1/M00/00/0F/rBADF1uXlQ-AeVnrAAiuEWsYbWU082.png
             * certShortPicuUrl : null
             * matchName : 衡泰信城市高尔夫联赛
             * comtitionName : null
             * eventName : 城市高尔夫联赛-七月
             * partTime : 1536652120000
             * partTimeStr : 2018-09-11 15:48:40
             */

            private String scoreCertCode;
            private String scoreCertSn;
            private String certPicUrl;
            private Object certShortPicuUrl;
            private String matchName;
            private Object comtitionName;
            private String eventName;
            private long partTime;
            private String partTimeStr;

            public String getScoreCertCode() {
                return scoreCertCode;
            }

            public void setScoreCertCode(String scoreCertCode) {
                this.scoreCertCode = scoreCertCode;
            }

            public String getScoreCertSn() {
                return scoreCertSn;
            }

            public void setScoreCertSn(String scoreCertSn) {
                this.scoreCertSn = scoreCertSn;
            }

            public String getCertPicUrl() {
                return certPicUrl;
            }

            public void setCertPicUrl(String certPicUrl) {
                this.certPicUrl = certPicUrl;
            }

            public Object getCertShortPicuUrl() {
                return certShortPicuUrl;
            }

            public void setCertShortPicuUrl(Object certShortPicuUrl) {
                this.certShortPicuUrl = certShortPicuUrl;
            }

            public String getMatchName() {
                return matchName;
            }

            public void setMatchName(String matchName) {
                this.matchName = matchName;
            }

            public Object getComtitionName() {
                return comtitionName;
            }

            public void setComtitionName(Object comtitionName) {
                this.comtitionName = comtitionName;
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
        }
    }
}
