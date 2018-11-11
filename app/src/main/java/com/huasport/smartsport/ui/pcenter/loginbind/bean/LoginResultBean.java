package com.huasport.smartsport.ui.pcenter.loginbind.bean;

public class LoginResultBean {


    /**
     * result : {"register":{"registerCode":"2770585792251904","account":"17611717709","nickName":"怪咖","headimgUrl":"http://devfdfs.zntyydh.com/group1/M00/00/09/CkAillvfrOiAV58OAAH_ZgplXmc637.jpg","gender":"","realName":"陆向阳","isBindPhone":true,"isCard":true,"phone":"18001020252"},"token":"4016CCCDF24596DA246920AFB4FF6678"}
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
         * register : {"registerCode":"2770585792251904","account":"17611717709","nickName":"怪咖","headimgUrl":"http://devfdfs.zntyydh.com/group1/M00/00/09/CkAillvfrOiAV58OAAH_ZgplXmc637.jpg","gender":"","realName":"陆向阳","isBindPhone":true,"isCard":true,"phone":"18001020252"}
         * token : 4016CCCDF24596DA246920AFB4FF6678
         */

        private RegisterBean register;
        private String token;

        public RegisterBean getRegister() {
            return register;
        }

        public void setRegister(RegisterBean register) {
            this.register = register;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class RegisterBean {
            /**
             * registerCode : 2770585792251904
             * account : 17611717709
             * nickName : 怪咖
             * headimgUrl : http://devfdfs.zntyydh.com/group1/M00/00/09/CkAillvfrOiAV58OAAH_ZgplXmc637.jpg
             * gender :
             * realName : 陆向阳
             * isBindPhone : true
             * isCard : true
             * phone : 18001020252
             */

            private String registerCode;
            private String account;
            private String nickName;
            private String headimgUrl;
            private String gender;
            private String realName;
            private boolean isBindPhone;
            private boolean isCard;
            private String phone;

            public String getRegisterCode() {
                return registerCode;
            }

            public void setRegisterCode(String registerCode) {
                this.registerCode = registerCode;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getHeadimgUrl() {
                return headimgUrl;
            }

            public void setHeadimgUrl(String headimgUrl) {
                this.headimgUrl = headimgUrl;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public boolean isIsBindPhone() {
                return isBindPhone;
            }

            public void setIsBindPhone(boolean isBindPhone) {
                this.isBindPhone = isBindPhone;
            }

            public boolean isIsCard() {
                return isCard;
            }

            public void setIsCard(boolean isCard) {
                this.isCard = isCard;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
