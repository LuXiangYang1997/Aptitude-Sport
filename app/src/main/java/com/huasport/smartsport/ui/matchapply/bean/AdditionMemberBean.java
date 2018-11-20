package com.huasport.smartsport.ui.matchapply.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bqj on 2018/7/26.
 */

public class AdditionMemberBean implements Serializable {

    /**
     * result : {"players":[{"attributeName":"playerName","cnname":"姓名","type":null,"isRequired":true,"isShow":true,"val":"陆向阳","params":null},{"attributeName":"playerPhone","cnname":"手机号码","type":null,"isRequired":true,"isShow":true,"val":"15047037232","params":null},{"attributeName":"sex","cnname":"性别","type":null,"isRequired":true,"isShow":true,"val":"m","params":null},{"attributeName":"imgUrl","cnname":"头像","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"email","cnname":"邮箱","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerWeight","cnname":"体重(公斤)","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerBirth","cnname":"生日","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerNationality","cnname":"国籍","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerAddress","cnname":"联系地址","type":null,"isRequired":true,"isShow":true,"val":"5566","params":null},{"attributeName":"playerCerType","cnname":"证件类型","type":null,"isRequired":true,"isShow":true,"val":"1","params":null},{"attributeName":"playerCerNo","cnname":"证件号码","type":null,"isRequired":true,"isShow":true,"val":"182224199711067058","params":null},{"attributeName":"playerBloodType","cnname":"血型","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerNation","cnname":"民族","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerClothSize","cnname":"衣服尺码","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerEmergencyName","cnname":"紧急联系电话","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"assettoId","cnname":"神力科萨账户ID","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"动态属性码","cnname":"动态属性码","type":null,"isRequired":true,"isShow":true,"val":"12580","params":null},{"attributeName":"attOne","cnname":"身份证正面","type":null,"isRequired":false,"isShow":true,"val":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tdtfCAa8icAAE8CaBUTb0370.jpg","params":null},{"attributeName":"attTwo","cnname":"身份证反面","type":null,"isRequired":false,"isShow":true,"val":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tSp82AIn9YAAGxgWfuqLw831.jpg","params":null}],"playerCode":"20180730160538xgk7"}
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

    public static class ResultBean implements Serializable{
        /**
         * players : [{"attributeName":"playerName","cnname":"姓名","type":null,"isRequired":true,"isShow":true,"val":"陆向阳","params":null},{"attributeName":"playerPhone","cnname":"手机号码","type":null,"isRequired":true,"isShow":true,"val":"15047037232","params":null},{"attributeName":"sex","cnname":"性别","type":null,"isRequired":true,"isShow":true,"val":"m","params":null},{"attributeName":"imgUrl","cnname":"头像","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"email","cnname":"邮箱","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerWeight","cnname":"体重(公斤)","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerBirth","cnname":"生日","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerNationality","cnname":"国籍","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerAddress","cnname":"联系地址","type":null,"isRequired":true,"isShow":true,"val":"5566","params":null},{"attributeName":"playerCerType","cnname":"证件类型","type":null,"isRequired":true,"isShow":true,"val":"1","params":null},{"attributeName":"playerCerNo","cnname":"证件号码","type":null,"isRequired":true,"isShow":true,"val":"182224199711067058","params":null},{"attributeName":"playerBloodType","cnname":"血型","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerNation","cnname":"民族","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerClothSize","cnname":"衣服尺码","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"playerEmergencyName","cnname":"紧急联系电话","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"assettoId","cnname":"神力科萨账户ID","type":null,"isRequired":false,"isShow":false,"val":"","params":null},{"attributeName":"动态属性码","cnname":"动态属性码","type":null,"isRequired":true,"isShow":true,"val":"12580","params":null},{"attributeName":"attOne","cnname":"身份证正面","type":null,"isRequired":false,"isShow":true,"val":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tdtfCAa8icAAE8CaBUTb0370.jpg","params":null},{"attributeName":"attTwo","cnname":"身份证反面","type":null,"isRequired":false,"isShow":true,"val":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tSp82AIn9YAAGxgWfuqLw831.jpg","params":null}]
         * playerCode : 20180730160538xgk7
         */

        private String playerCode;
        private List<Players> players;

        public String getPlayerCode() {
            return playerCode;
        }

        public void setPlayerCode(String playerCode) {
            this.playerCode = playerCode;
        }

        public List<Players> getPlayers() {
            return players;
        }

        public void setPlayers(List<Players> players) {
            this.players = players;
        }

        public static class Players implements Serializable{
            /**
             * attributeName : playerName
             * cnname : 姓名
             * type : null
             * isRequired : true
             * isShow : true
             * val : 陆向阳
             * params : null
             */
            private String attributeName;
            private String cnname;
            private Object type;
            private boolean isRequired;
            private boolean isShow;
            private String val;
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

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
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
