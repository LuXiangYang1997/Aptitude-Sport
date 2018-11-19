package com.huasport.smartsport.ui.discover.bean;

import java.io.Serializable;
import java.util.List;

public class DynamicDataBean implements Serializable {

    private String content;
    private List<String> dyImg;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getDyImg() {
        return dyImg;
    }

    public void setDyImg(List<String> dyImg) {
        this.dyImg = dyImg;
    }
}
