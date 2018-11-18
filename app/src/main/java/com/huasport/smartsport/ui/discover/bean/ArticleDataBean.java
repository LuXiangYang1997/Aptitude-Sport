package com.huasport.smartsport.ui.discover.bean;


import com.huasport.smartsport.custom.RichTextEditor;
import java.io.Serializable;
import java.util.List;


public class ArticleDataBean implements Serializable{

    private List<RichTextEditor.EditData> dataStr;

    public List<RichTextEditor.EditData> getDataStr() {
        return dataStr;
    }

    public void setDataStr(List<RichTextEditor.EditData> dataStr) {
        this.dataStr = dataStr;
    }
}
