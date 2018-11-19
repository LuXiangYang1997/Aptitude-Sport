package com.huasport.smartsport.ui.matchapply.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/24.
 */

public class AthletesMessageBean implements Serializable {


    /**
     * result : {"properties":[{"attributeName":null,"cnname":"姓名","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":null,"cnname":"手机号码","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":null,"cnname":"性别","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":null,"cnname":"头像","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":null,"cnname":"邮箱","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":null,"cnname":"体重","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":null,"cnname":"生日","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":null,"cnname":"国籍","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":null,"cnname":"联系方式","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":null,"cnname":"证件类型","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":null,"cnname":"血型","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":null,"cnname":"民族","type":null,"isRequired":true,"isShow":true,"params":null},{"attributeName":null,"cnname":"衣服尺码","type":null,"isRequired":false,"isShow":false,"params":null},{"attributeName":null,"cnname":"紧急联系人","type":null,"isRequired":true,"isShow":true,"params":null}]}
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

        public static class PropertiesBean implements Serializable {
            /**
             * attributeName : null
             * cnname : 姓名
             * type : null
             * isRequired : true
             * isShow : true
             * params : null
             */

            private Object attributeName;
            private String cnname;
            private Object type;
            private boolean isRequired;
            private boolean isShow;
            private Object params;
            private String edittextData;
            private String val;

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }

            public Object getAttributeName() {
                return attributeName;
            }

            public void setAttributeName(Object attributeName) {
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
