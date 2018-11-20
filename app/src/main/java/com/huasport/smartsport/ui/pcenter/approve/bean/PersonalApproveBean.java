package com.huasport.smartsport.ui.pcenter.approve.bean;

import java.io.Serializable;

public class PersonalApproveBean implements Serializable {

    private String realName;
    private String password;
    private String imgurlFront;
    private String imgurlContract;
    private String imgurlHand;


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgurlFront() {
        return imgurlFront;
    }

    public void setImgurlFront(String imgurlFront) {
        this.imgurlFront = imgurlFront;
    }

    public String getImgurlContract() {
        return imgurlContract;
    }

    public void setImgurlContract(String imgurlContract) {
        this.imgurlContract = imgurlContract;
    }

    public String getImgurlHand() {
        return imgurlHand;
    }

    public void setImgurlHand(String imgurlHand) {
        this.imgurlHand = imgurlHand;
    }
}
