package com.huasport.smartsport.ui.matchapply.bean;

import java.util.List;

/**
 * Created by bqj on 2018/7/27.
 */

public class AddMemberInitializeBean {


    /**
     * result : {"properties":[{"attributeName":"playerName","cnname":"姓名","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":"playerPhone","cnname":"手机号码","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":"sex","cnname":"性别","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":"imgUrl","cnname":"头像","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"email","cnname":"邮箱","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"playerWeight","cnname":"体重(公斤)","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"playerBirth","cnname":"生日","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"playerNationality","cnname":"国籍","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"playerAddress","cnname":"联系地址","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"playerCerType","cnname":"证件类型","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"playerBloodType","cnname":"血型","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"playerNation","cnname":"民族","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"playerClothSize","cnname":"衣服尺码","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"playerEmergencyName","cnname":"紧急联系电话","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"assettoId","cnname":"神力科萨账户ID","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":"身份证号","cnname":"身份证号","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":"参赛城市","cnname":"参赛城市","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":"联系电话","cnname":"联系电话","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":"QQ","cnname":"QQ","type":null,"isRequired":true,"isShow":true,"params":null}]}
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
        private List<PropertiesBean> properties;

        public List<PropertiesBean> getProperties() {
            return properties;
        }

        public void setProperties(List<PropertiesBean> properties) {
            this.properties = properties;
        }

        public static class PropertiesBean {
            /**
             * attributeName : playerName
             * cnname : 姓名
             * type : null
             * isRequired : true
             * isShow : true
             * params : null
             */

            private String attributeName;
            private String cnname;
            private Object type;
            private boolean isRequired;
            private boolean isShow;
            private Object params;
            private String val;
            private List<AdditionMemberBean.ResultBean.Players> playerBeanList;

            public List<AdditionMemberBean.ResultBean.Players> getPlayerBeanList() {
                return playerBeanList;
            }

            public void setPlayerBeanList(List<AdditionMemberBean.ResultBean.Players> playerBeanList) {
                this.playerBeanList = playerBeanList;
            }

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }

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

            public Object getParams() {
                return params;
            }

            public void setParams(Object params) {
                this.params = params;
            }
        }
    }
}
