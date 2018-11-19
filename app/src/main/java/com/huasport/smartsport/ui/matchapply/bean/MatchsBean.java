package com.huasport.smartsport.ui.matchapply.bean;

import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/7.
 */

public class MatchsBean {

    /**
     * result : {"match":{"matchName":"京杭大运河智能单车挑战赛","matchCode":"match201806281020584755","startTime":"2018-06-01 00:00:00","endTime":"2018-11-25 00:00:00","introduce":"<p>项目概述：<\/p>\r\n\r\n<p>智能骑行台+骑行训练游戏软件的出现，让骑行爱好者多了一种有趣骑行选择和更有效的训练方式，如今甚至已发展成为一种新的自行车竞技模式。真正让游戏场景体育化，让体育场景游戏化。<\/p>\r\n\r\n<p>赛事亮点：<\/p>\r\n\r\n<ol>\r\n\t<li>参赛门槛低，即使不会骑传统自行车也能参赛。<\/li>\r\n\t<li>参与受众广，不分男女老少都能参与。<\/li>\r\n\t<li>比赛周期长，应用线上比赛的智能、自助等特点，使得参赛选手可以在任何时间任何地点随时参赛。<\/li>\r\n\t<li>弘扬运河文化，线下赛均为分布于京杭大运河两端的重要城市。<\/li>\r\n<\/ol>\r\n","poster":"http://zntyfdfs.efida.com.cn/group1/M00/00/01/rBADF1s0RPCAaKW6AAFXa9XMGpI832.png","chapters":[{"type":"pic_desc","name":"比赛装备","content":null,"compositeVos":[{"name":"骑行台","attaUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/01/rBADF1s0S4qAA6a9AAecJnpthK0205.jpg","skipUrl":null}]}],"awards":[{"awardName":"第一名","prizeImg":"","prizeName":"金牌","awardType":"first"},{"awardName":"第二名","prizeImg":"","prizeName":"银牌","awardType":"second"},{"awardName":"第三名","prizeImg":"","prizeName":"铜牌","awardType":"third"}],"canApply":true,"applyStatusDesc":"报名","applyStatus":"5","customerService":null}}
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
         * match : {"matchName":"京杭大运河智能单车挑战赛","matchCode":"match201806281020584755","startTime":"2018-06-01 00:00:00","endTime":"2018-11-25 00:00:00","introduce":"<p>项目概述：<\/p>\r\n\r\n<p>智能骑行台+骑行训练游戏软件的出现，让骑行爱好者多了一种有趣骑行选择和更有效的训练方式，如今甚至已发展成为一种新的自行车竞技模式。真正让游戏场景体育化，让体育场景游戏化。<\/p>\r\n\r\n<p>赛事亮点：<\/p>\r\n\r\n<ol>\r\n\t<li>参赛门槛低，即使不会骑传统自行车也能参赛。<\/li>\r\n\t<li>参与受众广，不分男女老少都能参与。<\/li>\r\n\t<li>比赛周期长，应用线上比赛的智能、自助等特点，使得参赛选手可以在任何时间任何地点随时参赛。<\/li>\r\n\t<li>弘扬运河文化，线下赛均为分布于京杭大运河两端的重要城市。<\/li>\r\n<\/ol>\r\n","poster":"http://zntyfdfs.efida.com.cn/group1/M00/00/01/rBADF1s0RPCAaKW6AAFXa9XMGpI832.png","chapters":[{"type":"pic_desc","name":"比赛装备","content":null,"compositeVos":[{"name":"骑行台","attaUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/01/rBADF1s0S4qAA6a9AAecJnpthK0205.jpg","skipUrl":null}]}],"awards":[{"awardName":"第一名","prizeImg":"","prizeName":"金牌","awardType":"first"},{"awardName":"第二名","prizeImg":"","prizeName":"银牌","awardType":"second"},{"awardName":"第三名","prizeImg":"","prizeName":"铜牌","awardType":"third"}],"canApply":true,"applyStatusDesc":"报名","applyStatus":"5","customerService":null}
         */
        private MatchBean match;
        private String shareUrl;

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public MatchBean getMatch() {
            return match;
        }

        public void setMatch(MatchBean match) {
            this.match = match;
        }

        public static class MatchBean {
            /**
             * matchName : 京杭大运河智能单车挑战赛
             * matchCode : match201806281020584755
             * startTime : 2018-06-01 00:00:00
             * endTime : 2018-11-25 00:00:00
             * introduce : <p>项目概述：</p>
             * <p>智能骑行台+骑行训练游戏软件的出现，让骑行爱好者多了一种有趣骑行选择和更有效的训练方式，如今甚至已发展成为一种新的自行车竞技模式。真正让游戏场景体育化，让体育场景游戏化。</p>
             * <p>赛事亮点：</p>
             * <ol>
             * <li>参赛门槛低，即使不会骑传统自行车也能参赛。</li>
             * <li>参与受众广，不分男女老少都能参与。</li>
             * <li>比赛周期长，应用线上比赛的智能、自助等特点，使得参赛选手可以在任何时间任何地点随时参赛。</li>
             * <li>弘扬运河文化，线下赛均为分布于京杭大运河两端的重要城市。</li>
             * </ol>
             * poster : http://zntyfdfs.efida.com.cn/group1/M00/00/01/rBADF1s0RPCAaKW6AAFXa9XMGpI832.png
             * chapters : [{"type":"pic_desc","name":"比赛装备","content":null,"compositeVos":[{"name":"骑行台","attaUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/01/rBADF1s0S4qAA6a9AAecJnpthK0205.jpg","skipUrl":null}]}]
             * awards : [{"awardName":"第一名","prizeImg":"","prizeName":"金牌","awardType":"first"},{"awardName":"第二名","prizeImg":"","prizeName":"银牌","awardType":"second"},{"awardName":"第三名","prizeImg":"","prizeName":"铜牌","awardType":"third"}]
             * canApply : true
             * applyStatusDesc : 报名
             * applyStatus : 5
             * customerService : null
             */
            private String matchName;
            private String matchCode;
            private String startTime;
            private String endTime;
            private String introduce;
            private String poster;
            private boolean canApply;
            private String applyStatusDesc;
            private String applyStatus;
            private Object customerService;
            private List<ChaptersBean> chapters;
            private List<AwardsBean> awards;

            public String getMatchName() {
                return matchName;
            }

            public void setMatchName(String matchName) {
                this.matchName = matchName;
            }

            public String getMatchCode() {
                return matchCode;
            }

            public void setMatchCode(String matchCode) {
                this.matchCode = matchCode;
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

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }

            public boolean isCanApply() {
                return canApply;
            }

            public void setCanApply(boolean canApply) {
                this.canApply = canApply;
            }

            public String getApplyStatusDesc() {
                return applyStatusDesc;
            }

            public void setApplyStatusDesc(String applyStatusDesc) {
                this.applyStatusDesc = applyStatusDesc;
            }

            public String getApplyStatus() {
                return applyStatus;
            }

            public void setApplyStatus(String applyStatus) {
                this.applyStatus = applyStatus;
            }

            public Object getCustomerService() {
                return customerService;
            }

            public void setCustomerService(Object customerService) {
                this.customerService = customerService;
            }

            public List<ChaptersBean> getChapters() {
                return chapters;
            }

            public void setChapters(List<ChaptersBean> chapters) {
                this.chapters = chapters;
            }

            public List<AwardsBean> getAwards() {
                return awards;
            }

            public void setAwards(List<AwardsBean> awards) {
                this.awards = awards;
            }

            public static class ChaptersBean {
                /**
                 * type : pic_desc
                 * name : 比赛装备
                 * content : null
                 * compositeVos : [{"name":"骑行台","attaUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/01/rBADF1s0S4qAA6a9AAecJnpthK0205.jpg","skipUrl":null}]
                 */
                private String type;
                private String name;
                private Object content;
                private List<CompositeVosBean> compositeVos;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getContent() {
                    return content;
                }

                public void setContent(Object content) {
                    this.content = content;
                }

                public List<CompositeVosBean> getCompositeVos() {
                    return compositeVos;
                }

                public void setCompositeVos(List<CompositeVosBean> compositeVos) {
                    this.compositeVos = compositeVos;
                }

                public static class CompositeVosBean {
                    /**
                     * name : 骑行台
                     * attaUrl : http://zntyfdfs.efida.com.cn/group1/M00/00/01/rBADF1s0S4qAA6a9AAecJnpthK0205.jpg
                     * skipUrl : null
                     */
                    private String name;
                    private String attaUrl;
                    private Object skipUrl;
                    private String type;
                    private String title;

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getAttaUrl() {
                        return attaUrl;
                    }

                    public void setAttaUrl(String attaUrl) {
                        this.attaUrl = attaUrl;
                    }

                    public Object getSkipUrl() {
                        return skipUrl;
                    }

                    public void setSkipUrl(Object skipUrl) {
                        this.skipUrl = skipUrl;
                    }
                }
            }

            public static class AwardsBean {
                /**
                 * awardName : 第一名
                 * prizeImg :
                 * prizeName : 金牌
                 * awardType : first
                 */
                private String awardName;
                private String prizeImg;
                private String prizeName;
                private String awardType;

                public String getAwardName() {
                    return awardName;
                }

                public void setAwardName(String awardName) {
                    this.awardName = awardName;
                }

                public String getPrizeImg() {
                    return prizeImg;
                }

                public void setPrizeImg(String prizeImg) {
                    this.prizeImg = prizeImg;
                }

                public String getPrizeName() {
                    return prizeName;
                }

                public void setPrizeName(String prizeName) {
                    this.prizeName = prizeName;
                }

                public String getAwardType() {
                    return awardType;
                }

                public void setAwardType(String awardType) {
                    this.awardType = awardType;
                }
            }
        }
    }
}
