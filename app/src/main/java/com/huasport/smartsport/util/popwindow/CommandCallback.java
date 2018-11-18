package com.huasport.smartsport.util.popwindow;

import android.widget.EditText;
import android.widget.PopupWindow;

public interface CommandCallback {

    /**
     * 发布评论
     * @param popupWindow
     * @param s
     * @param commentEdittext
     */
    void releaseCommand(PopupWindow popupWindow, String s, EditText commentEdittext);


}
