package com.huasport.smartsport.ui.matchapply.bean;

import java.util.List;

public class GroupOrderWaitPerfectMsgBean {


    /**
     * result : {"matchCode":"match201807161555550532","matchName":"测试-篮球挑战赛","orderDetail":{"orderCode":"20180729053354a0b1","applys":[{"applyCode":"20180729053355ubj4","gameName":"测试篮球项目","matchName":"测试-篮球挑战赛","siteName":"济南分赛场","matchGroupName":null,"eventName":"60秒团体投篮","applyAmount":1,"applyAmountStr":"0.01","eventStartTime":"2018-07-31 00:00:00","eventEndTime":"2018-08-30 00:00:00","address":"山东省济南市历城区"}],"teams":[{"playerPhone":null,"playerName":null,"playerCode":null,"players":[{"attributeName":"playerName","cnname":"姓名","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerPhone","cnname":"手机号码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"sex","cnname":"性别","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"imgUrl","cnname":"头像","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"email","cnname":"邮箱","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerWeight","cnname":"体重(公斤)","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerBirth","cnname":"生日","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerNationality","cnname":"国籍","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerAddress","cnname":"联系地址","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerCerType","cnname":"证件类型","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerCerNo","cnname":"证件号码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerBloodType","cnname":"血型","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerNation","cnname":"民族","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerClothSize","cnname":"衣服尺码","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerEmergencyName","cnname":"紧急联系电话","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"assettoId","cnname":"神力科萨账户ID","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"动态属性码","cnname":"动态属性码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null}]}],"leader":{"applyInfoCode":"20180729053355ubj4","leaderCode":"2802645803534336","registerCode":"2791833390417920","teamName":"陆向阳","leaderName":"123465","leaderPhone":"15047037232"},"playerVos":null,"orderAmount":1,"orderAmountStr":"0.01","orderTime":1532813635000,"remark":"报名费用","orderStatus":"wait_complete","orderStatusDesc":"待完善","personGroup":"2","eventType":"group","canAddTeam":true,"addTeamNum":"2"},"matchStartTime":"2018-07-10 00:00:00","title":"2018全国首届智能体育运动会","matchEndTime":"2018-08-31 00:00:00"}
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
         * matchCode : match201807161555550532
         * matchName : 测试-篮球挑战赛
         * orderDetail : {"orderCode":"20180729053354a0b1","applys":[{"applyCode":"20180729053355ubj4","gameName":"测试篮球项目","matchName":"测试-篮球挑战赛","siteName":"济南分赛场","matchGroupName":null,"eventName":"60秒团体投篮","applyAmount":1,"applyAmountStr":"0.01","eventStartTime":"2018-07-31 00:00:00","eventEndTime":"2018-08-30 00:00:00","address":"山东省济南市历城区"}],"teams":[{"playerPhone":null,"playerName":null,"playerCode":null,"players":[{"attributeName":"playerName","cnname":"姓名","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerPhone","cnname":"手机号码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"sex","cnname":"性别","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"imgUrl","cnname":"头像","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"email","cnname":"邮箱","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerWeight","cnname":"体重(公斤)","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerBirth","cnname":"生日","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerNationality","cnname":"国籍","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerAddress","cnname":"联系地址","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerCerType","cnname":"证件类型","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerCerNo","cnname":"证件号码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerBloodType","cnname":"血型","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerNation","cnname":"民族","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerClothSize","cnname":"衣服尺码","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerEmergencyName","cnname":"紧急联系电话","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"assettoId","cnname":"神力科萨账户ID","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"动态属性码","cnname":"动态属性码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null}]}],"leader":{"applyInfoCode":"20180729053355ubj4","leaderCode":"2802645803534336","registerCode":"2791833390417920","teamName":"陆向阳","leaderName":"123465","leaderPhone":"15047037232"},"playerVos":null,"orderAmount":1,"orderAmountStr":"0.01","orderTime":1532813635000,"remark":"报名费用","orderStatus":"wait_complete","orderStatusDesc":"待完善","personGroup":"2","eventType":"group","canAddTeam":true,"addTeamNum":"2"}
         * matchStartTime : 2018-07-10 00:00:00
         * title : 2018全国首届智能体育运动会
         * matchEndTime : 2018-08-31 00:00:00
         */

        private String matchCode;
        private String matchName;
        private OrderDetailBean orderDetail;
        private String matchStartTime;
        private String title;
        private String matchEndTime;

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

        public OrderDetailBean getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(OrderDetailBean orderDetail) {
            this.orderDetail = orderDetail;
        }

        public String getMatchStartTime() {
            return matchStartTime;
        }

        public void setMatchStartTime(String matchStartTime) {
            this.matchStartTime = matchStartTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMatchEndTime() {
            return matchEndTime;
        }

        public void setMatchEndTime(String matchEndTime) {
            this.matchEndTime = matchEndTime;
        }

        public static class OrderDetailBean {
            /**
             * orderCode : 20180729053354a0b1
             * applys : [{"applyCode":"20180729053355ubj4","gameName":"测试篮球项目","matchName":"测试-篮球挑战赛","siteName":"济南分赛场","matchGroupName":null,"eventName":"60秒团体投篮","applyAmount":1,"applyAmountStr":"0.01","eventStartTime":"2018-07-31 00:00:00","eventEndTime":"2018-08-30 00:00:00","address":"山东省济南市历城区"}]
             * teams : [{"playerPhone":null,"playerName":null,"playerCode":null,"players":[{"attributeName":"playerName","cnname":"姓名","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerPhone","cnname":"手机号码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"sex","cnname":"性别","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"imgUrl","cnname":"头像","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"email","cnname":"邮箱","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerWeight","cnname":"体重(公斤)","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerBirth","cnname":"生日","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerNationality","cnname":"国籍","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerAddress","cnname":"联系地址","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerCerType","cnname":"证件类型","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerCerNo","cnname":"证件号码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerBloodType","cnname":"血型","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerNation","cnname":"民族","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerClothSize","cnname":"衣服尺码","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerEmergencyName","cnname":"紧急联系电话","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"assettoId","cnname":"神力科萨账户ID","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"动态属性码","cnname":"动态属性码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null}]}]
             * leader : {"applyInfoCode":"20180729053355ubj4","leaderCode":"2802645803534336","registerCode":"2791833390417920","teamName":"陆向阳","leaderName":"123465","leaderPhone":"15047037232"}
             * playerVos : null
             * orderAmount : 1
             * orderAmountStr : 0.01
             * orderTime : 1532813635000
             * remark : 报名费用
             * orderStatus : wait_complete
             * orderStatusDesc : 待完善
             * personGroup : 2
             * eventType : group
             * canAddTeam : true
             * addTeamNum : 2
             */

            private String orderCode;
            private LeaderBean leader;
            private Object playerVos;
            private int orderAmount;
            private String orderAmountStr;
            private long orderTime;
            private String remark;
            private String orderStatus;
            private String orderStatusDesc;
            private String personGroup;
            private String eventType;
            private boolean canAddTeam;
            private String addTeamNum;
            private List<ApplysBean> applys;
            private List<TeamsBean> teams;

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public LeaderBean getLeader() {
                return leader;
            }

            public void setLeader(LeaderBean leader) {
                this.leader = leader;
            }

            public Object getPlayerVos() {
                return playerVos;
            }

            public void setPlayerVos(Object playerVos) {
                this.playerVos = playerVos;
            }

            public int getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(int orderAmount) {
                this.orderAmount = orderAmount;
            }

            public String getOrderAmountStr() {
                return orderAmountStr;
            }

            public void setOrderAmountStr(String orderAmountStr) {
                this.orderAmountStr = orderAmountStr;
            }

            public long getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(long orderTime) {
                this.orderTime = orderTime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderStatusDesc() {
                return orderStatusDesc;
            }

            public void setOrderStatusDesc(String orderStatusDesc) {
                this.orderStatusDesc = orderStatusDesc;
            }

            public String getPersonGroup() {
                return personGroup;
            }

            public void setPersonGroup(String personGroup) {
                this.personGroup = personGroup;
            }

            public String getEventType() {
                return eventType;
            }

            public void setEventType(String eventType) {
                this.eventType = eventType;
            }

            public boolean isCanAddTeam() {
                return canAddTeam;
            }

            public void setCanAddTeam(boolean canAddTeam) {
                this.canAddTeam = canAddTeam;
            }

            public String getAddTeamNum() {
                return addTeamNum;
            }

            public void setAddTeamNum(String addTeamNum) {
                this.addTeamNum = addTeamNum;
            }

            public List<ApplysBean> getApplys() {
                return applys;
            }

            public void setApplys(List<ApplysBean> applys) {
                this.applys = applys;
            }

            public List<TeamsBean> getTeams() {
                return teams;
            }

            public void setTeams(List<TeamsBean> teams) {
                this.teams = teams;
            }

            public static class LeaderBean {
                /**
                 * applyInfoCode : 20180729053355ubj4
                 * leaderCode : 2802645803534336
                 * registerCode : 2791833390417920
                 * teamName : 陆向阳
                 * leaderName : 123465
                 * leaderPhone : 15047037232
                 */

                private String applyInfoCode;
                private String leaderCode;
                private String registerCode;
                private String teamName;
                private String leaderName;
                private String leaderPhone;

                public String getApplyInfoCode() {
                    return applyInfoCode;
                }

                public void setApplyInfoCode(String applyInfoCode) {
                    this.applyInfoCode = applyInfoCode;
                }

                public String getLeaderCode() {
                    return leaderCode;
                }

                public void setLeaderCode(String leaderCode) {
                    this.leaderCode = leaderCode;
                }

                public String getRegisterCode() {
                    return registerCode;
                }

                public void setRegisterCode(String registerCode) {
                    this.registerCode = registerCode;
                }

                public String getTeamName() {
                    return teamName;
                }

                public void setTeamName(String teamName) {
                    this.teamName = teamName;
                }

                public String getLeaderName() {
                    return leaderName;
                }

                public void setLeaderName(String leaderName) {
                    this.leaderName = leaderName;
                }

                public String getLeaderPhone() {
                    return leaderPhone;
                }

                public void setLeaderPhone(String leaderPhone) {
                    this.leaderPhone = leaderPhone;
                }
            }

            public static class ApplysBean {
                /**
                 * applyCode : 20180729053355ubj4
                 * gameName : 测试篮球项目
                 * matchName : 测试-篮球挑战赛
                 * siteName : 济南分赛场
                 * matchGroupName : null
                 * eventName : 60秒团体投篮
                 * applyAmount : 1
                 * applyAmountStr : 0.01
                 * eventStartTime : 2018-07-31 00:00:00
                 * eventEndTime : 2018-08-30 00:00:00
                 * address : 山东省济南市历城区
                 */

                private String applyCode;
                private String gameName;
                private String matchName;
                private String siteName;
                private Object matchGroupName;
                private String eventName;
                private int applyAmount;
                private String applyAmountStr;
                private String eventStartTime;
                private String eventEndTime;
                private String address;

                public String getApplyCode() {
                    return applyCode;
                }

                public void setApplyCode(String applyCode) {
                    this.applyCode = applyCode;
                }

                public String getGameName() {
                    return gameName;
                }

                public void setGameName(String gameName) {
                    this.gameName = gameName;
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

                public Object getMatchGroupName() {
                    return matchGroupName;
                }

                public void setMatchGroupName(Object matchGroupName) {
                    this.matchGroupName = matchGroupName;
                }

                public String getEventName() {
                    return eventName;
                }

                public void setEventName(String eventName) {
                    this.eventName = eventName;
                }

                public int getApplyAmount() {
                    return applyAmount;
                }

                public void setApplyAmount(int applyAmount) {
                    this.applyAmount = applyAmount;
                }

                public String getApplyAmountStr() {
                    return applyAmountStr;
                }

                public void setApplyAmountStr(String applyAmountStr) {
                    this.applyAmountStr = applyAmountStr;
                }

                public String getEventStartTime() {
                    return eventStartTime;
                }

                public void setEventStartTime(String eventStartTime) {
                    this.eventStartTime = eventStartTime;
                }

                public String getEventEndTime() {
                    return eventEndTime;
                }

                public void setEventEndTime(String eventEndTime) {
                    this.eventEndTime = eventEndTime;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }
            }

            public static class TeamsBean {
                /**
                 * playerPhone : null
                 * playerName : null
                 * playerCode : null
                 * players : [{"attributeName":"playerName","cnname":"姓名","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerPhone","cnname":"手机号码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"sex","cnname":"性别","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"imgUrl","cnname":"头像","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"email","cnname":"邮箱","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerWeight","cnname":"体重(公斤)","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerBirth","cnname":"生日","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerNationality","cnname":"国籍","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerAddress","cnname":"联系地址","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerCerType","cnname":"证件类型","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerCerNo","cnname":"证件号码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null},{"attributeName":"playerBloodType","cnname":"血型","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerNation","cnname":"民族","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerClothSize","cnname":"衣服尺码","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"playerEmergencyName","cnname":"紧急联系电话","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"assettoId","cnname":"神力科萨账户ID","type":null,"isRequired":false,"isShow":false,"val":null,"params":null},{"attributeName":"动态属性码","cnname":"动态属性码","type":null,"isRequired":true,"isShow":true,"val":null,"params":null}]
                 */

                private Object playerPhone;
                private Object playerName;
                private Object playerCode;
                private List<PlayersBean> players;

                public Object getPlayerPhone() {
                    return playerPhone;
                }

                public void setPlayerPhone(Object playerPhone) {
                    this.playerPhone = playerPhone;
                }

                public Object getPlayerName() {
                    return playerName;
                }

                public void setPlayerName(Object playerName) {
                    this.playerName = playerName;
                }

                public Object getPlayerCode() {
                    return playerCode;
                }

                public void setPlayerCode(Object playerCode) {
                    this.playerCode = playerCode;
                }

                public List<PlayersBean> getPlayers() {
                    return players;
                }

                public void setPlayers(List<PlayersBean> players) {
                    this.players = players;
                }

                public static class PlayersBean {
                    /**
                     * attributeName : playerName
                     * cnname : 姓名
                     * type : null
                     * isRequired : true
                     * isShow : true
                     * val : null
                     * params : null
                     */

                    private String attributeName;
                    private String cnname;
                    private Object type;
                    private boolean isRequired;
                    private boolean isShow;
                    private Object val;
                    private Object params;

                    public String getAttributeName() {
                        return attributeName;
                    }

                    public void setAttributeName(String attributeName) {
                        this.attributeName = attributeName;
                    }

                    public String getCnname() {
                        return cnname;
                    }

                    public void setCnname(String cnname) {
                        this.cnname = cnname;
                    }

                    public Object getType() {
                        return type;
                    }

                    public void setType(Object type) {
                        this.type = type;
                    }

                    public boolean isIsRequired() {
                        return isRequired;
                    }

                    public void setIsRequired(boolean isRequired) {
                        this.isRequired = isRequired;
                    }

                    public boolean isIsShow() {
                        return isShow;
                    }

                    public void setIsShow(boolean isShow) {
                        this.isShow = isShow;
                    }

                    public Object getVal() {
                        return val;
                    }

                    public void setVal(Object val) {
                        this.val = val;
                    }

                    public Object getParams() {
                        return params;
                    }

                    public void setParams(Object params) {
                        this.params = params;
                    }
                }
            }
        }
    }
}
