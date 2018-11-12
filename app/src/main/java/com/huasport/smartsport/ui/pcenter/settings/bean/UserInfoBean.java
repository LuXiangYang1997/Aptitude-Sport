package com.huasport.smartsport.ui.pcenter.settings.bean;

import java.io.Serializable;

/**
 * Created by 陆向阳 on 2018/7/26.
 */

public class UserInfoBean implements Serializable{


    /**
     * result : {"register":{"account":"13651335062","nickName":"师师","headimgUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/0C/rBADF1t9Y-2ADvojACDAfdH_6e8113.png","gender":"男","realName":"陆向阳","isBindPhone":true,"isCard":true,"phone":"18001020252"},"token":"E308AF4ACC995663DFE91A3DD6346D13"}
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

    public static class ResultBean  implements Serializable{
        /**
         * register : {"account":"13651335062","nickName":"师师","headimgUrl":"http://zntyfdfs.efida.com.cn/group1/M00/00/0C/rBADF1t9Y-2ADvojACDAfdH_6e8113.png","gender":"男","realName":"陆向阳","isBindPhone":true,"isCard":true,"phone":"18001020252"}
         * token : E308AF4ACC995663DFE91A3DD6346D13
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

        public static class RegisterBean implements Serializable{
            /**
             * account : 13651335062
             * nickName : 师师
             * headimgUrl : http://zntyfdfs.efida.com.cn/group1/M00/00/0C/rBADF1t9Y-2ADvojACDAfdH_6e8113.png
             * gender : 男
             * realName : 陆向阳
             * isBindPhone : true
             * isCard : true
             * phone : 18001020252
             */

            private String account;
            private String nickName;
            private String headimgUrl;
            private String gender;
            private String realName;
            private boolean isBindPhone;
            private boolean isCard;
            private String phone;

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
