package com.huasport.smartsport.ui.discover.bean;

import java.io.Serializable;
import java.util.List;

public class DynamicDataBean implements Serializable {

    private String content;
    private List<byte[]> dyImg;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<byte[]> getDyImg() {
        return dyImg;
    }

    public void setDyImg(List<byte[]> dyImg) {
        this.dyImg = dyImg;
    }
}
