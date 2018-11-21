package com.huasport.smartsport.bean;

public class HomePageCertBean {


    /**
     * result : {"share":{"picUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/08/rBADF1tyetOATnyDAABN-HdmivM279.png","shareUrl":"http://zntywx.efida.com.cn/game/type","title":"首届全国智能体育大赛","content":"我已成功完成首届全国智能体育大赛项目，快来和我一起领取奖章吧。"},"scoreCert":{"scoreCertCode":"201808081349051ne101","scoreCertSn":"201800000002","certPicUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/07/rBADF1tqzbmAas8wAAirNwmLUDM670.png","certShortPicuUrl":null,"matchName":"衡泰信最近洞超级大奖赛","eventName":"两人组","partTime":1532586477000,"partTimeStr":"2018-07-26 14:27:57"}}
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
         * share : {"picUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/08/rBADF1tyetOATnyDAABN-HdmivM279.png","shareUrl":"http://zntywx.efida.com.cn/game/type","title":"首届全国智能体育大赛","content":"我已成功完成首届全国智能体育大赛项目，快来和我一起领取奖章吧。"}
         * scoreCert : {"scoreCertCode":"201808081349051ne101","scoreCertSn":"201800000002","certPicUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/07/rBADF1tqzbmAas8wAAirNwmLUDM670.png","certShortPicuUrl":null,"matchName":"衡泰信最近洞超级大奖赛","eventName":"两人组","partTime":1532586477000,"partTimeStr":"2018-07-26 14:27:57"}
         */

        private ShareBean share;
        private ScoreCertBean scoreCert;

        public ShareBean getShare() {
            return share;
        }

        public void setShare(ShareBean share) {
            this.share = share;
        }

        public ScoreCertBean getScoreCert() {
            return scoreCert;
        }

        public void setScoreCert(ScoreCertBean scoreCert) {
            this.scoreCert = scoreCert;
        }

        public static class ShareBean {
            /**
             * picUrl : http://zntyfdfs.efida.com.cn/group1/M00/00/08/rBADF1tyetOATnyDAABN-HdmivM279.png
             * shareUrl : http://zntywx.efida.com.cn/game/type
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
             * scoreCertCode : 201808081349051ne101
             * scoreCertSn : 201800000002
             * certPicUrl : http://zntyfdfs.efida.com.cn/group1/M00/00/07/rBADF1tqzbmAas8wAAirNwmLUDM670.png
             * certShortPicuUrl : null
             * matchName : 衡泰信最近洞超级大奖赛
             * eventName : 两人组
             * partTime : 1532586477000
             * partTimeStr : 2018-07-26 14:27:57
             */

            private String scoreCertCode;
            private String scoreCertSn;
            private String certPicUrl;
            private Object certShortPicuUrl;
            private String matchName;
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
