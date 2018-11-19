package com.huasport.smartsport.ui.matchapply.bean;


import java.io.Serializable;

/**
 * Created by 刘成龙 on 2018/6/28.
 */

public class PersonalInfoBean implements Serializable{

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

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
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
