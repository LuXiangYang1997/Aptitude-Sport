package com.huasport.smartsport.ui.matchapply.bean;

import java.io.Serializable;

/**
 * Created by 陆向阳 on 2018/6/25.
 */

public class ProgramMessageBean implements Serializable{

    private String groupName="";
    private String programName="";
    private String allPrice="";


    public String getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
}
