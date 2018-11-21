package com.huasport.smartsport.ui.pcenter.bean;

import java.util.List;

public class PersonalPrimordialMyGradeBean {


    /**
     * result : {"allRow":2,"totalPage":1,"pageSize":10,"list":[{"match":{"matchCode":"match201807171039123728","matchName":"衡泰信最近洞超级大奖赛","matchImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNTXKAK-BEAAwB0VksMIQ837.jpg","startTime":1530979200000,"endTime":1532966400000},"comList":[{"date":1535531847000,"score":"3300s","competition":{"groupName":null,"competitionCode":"competition1535681737622","areaName":"线上","competitionName":"智能射击大赛刘琳测试","eventName":"全民镭战积分赛（7月起）"}}]},{"match":{"matchCode":"match201807161226021190","matchName":"全民镭战智能射击大赛测试","matchImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/02/rBADF1tL_diAPVZtAA0H_HXeXWk369.png","startTime":1530374400000,"endTime":1532966400000},"comList":[{"date":1535531847000,"score":"3300s","competition":{"groupName":null,"competitionCode":"competition1535681737622","areaName":"线上","competitionName":"智能射击大赛刘琳测试","eventName":"全民镭战积分赛（7月起）"}}]}],"currentPage":1}
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
         * allRow : 2
         * totalPage : 1
         * pageSize : 10
         * list : [{"match":{"matchCode":"match201807171039123728","matchName":"衡泰信最近洞超级大奖赛","matchImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNTXKAK-BEAAwB0VksMIQ837.jpg","startTime":1530979200000,"endTime":1532966400000}},{"match":{"matchCode":"match201807161226021190","matchName":"全民镭战智能射击大赛测试","matchImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/02/rBADF1tL_diAPVZtAA0H_HXeXWk369.png","startTime":1530374400000,"endTime":1532966400000},"comList":[{"date":1535531847000,"score":"3300s","competition":{"groupName":null,"competitionCode":"competition1535681737622","areaName":"线上","competitionName":"智能射击大赛刘琳测试","eventName":"全民镭战积分赛（7月起）"}}]}]
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
             * match : {"matchCode":"match201807171039123728","matchName":"衡泰信最近洞超级大奖赛","matchImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNTXKAK-BEAAwB0VksMIQ837.jpg","startTime":1530979200000,"endTime":1532966400000}
             * comList : [{"date":1535531847000,"score":"3300s","competition":{"groupName":null,"competitionCode":"competition1535681737622","areaName":"线上","competitionName":"智能射击大赛刘琳测试","eventName":"全民镭战积分赛（7月起）"}}]
             */

            private MatchBean match;
            private List<ComListBean> comList;

            public MatchBean getMatch() {
                return match;
            }

            public void setMatch(MatchBean match) {
                this.match = match;
            }

            public List<ComListBean> getComList() {
                return comList;
            }

            public void setComList(List<ComListBean> comList) {
                this.comList = comList;
            }

            public static class MatchBean {
                /**
                 * matchCode : match201807171039123728
                 * matchName : 衡泰信最近洞超级大奖赛
                 * matchImg : http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNTXKAK-BEAAwB0VksMIQ837.jpg
                 * startTime : 1530979200000
                 * endTime : 1532966400000
                 */

                private String matchCode;
                private String matchName;
                private String matchImg;
                private long startTime;
                private long endTime;

                public String getMatchCode() {
                    return matchCode;
                }

                public void setMatchCode(String matchCode) {
                    this.matchCode = matchCode;
                }

                public String getMatchName() {
                    return matchName;
                }

                public void setMatchName(String matchName) {
                    this.matchName = matchName;
                }

                public String getMatchImg() {
                    return matchImg;
                }

                public void setMatchImg(String matchImg) {
                    this.matchImg = matchImg;
                }

                public long getStartTime() {
                    return startTime;
                }

                public void setStartTime(long startTime) {
                    this.startTime = startTime;
                }

                public long getEndTime() {
                    return endTime;
                }

                public void setEndTime(long endTime) {
                    this.endTime = endTime;
                }
            }

            public static class ComListBean {
                /**
                 * date : 1535531847000
                 * score : 3300s
                 * competition : {"groupName":null,"competitionCode":"competition1535681737622","areaName":"线上","competitionName":"智能射击大赛刘琳测试","eventName":"全民镭战积分赛（7月起）"}
                 */

                private long date;
                private String score;
                private CompetitionBean competition;


                public long getDate() {
                    return date;
                }

                public void setDate(long date) {
                    this.date = date;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public CompetitionBean getCompetition() {
                    return competition;
                }

                public void setCompetition(CompetitionBean competition) {
                    this.competition = competition;
                }

                public static class CompetitionBean {
                    /**
                     * groupName : null
                     * competitionCode : competition1535681737622
                     * areaName : 线上
                     * competitionName : 智能射击大赛刘琳测试
                     * eventName : 全民镭战积分赛（7月起）
                     */

                    private Object groupName;
                    private String competitionCode;
                    private String areaName;
                    private String competitionName;
                    private String eventName;
                    private String matchUrl;


                    public String getMatchUrl() {
                        return matchUrl;
                    }

                    public void setMatchUrl(String matchUrl) {
                        this.matchUrl = matchUrl;
                    }
                    public Object getGroupName() {
                        return groupName;
                    }

                    public void setGroupName(Object groupName) {
                        this.groupName = groupName;
                    }

                    public String getCompetitionCode() {
                        return competitionCode;
                    }

                    public void setCompetitionCode(String competitionCode) {
                        this.competitionCode = competitionCode;
                    }

                    public String getAreaName() {
                        return areaName;
                    }

                    public void setAreaName(String areaName) {
                        this.areaName = areaName;
                    }

                    public String getCompetitionName() {
                        return competitionName;
                    }

                    public void setCompetitionName(String competitionName) {
                        this.competitionName = competitionName;
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
    }
}
