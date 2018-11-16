package com.huasport.smartsport.util.popwindow;

import android.widget.PopupWindow;

public interface ReleaseCallBack {

    /**
     * 动态
     * @param popupWindow
     */
    void dynamic(PopupWindow popupWindow);


    /**
     * 文章
     * @param popupWindow
     */
    void article(PopupWindow popupWindow);

}
