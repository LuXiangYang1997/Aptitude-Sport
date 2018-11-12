package com.huasport.smartsport.util;

import android.widget.PopupWindow;

public interface SelectPicCallBack {

    /**
     * 拍照
     * @param popupWindow
     * @param cameraCode
     */
    void camera(PopupWindow popupWindow,int cameraCode);

    /**
     * 相册
     * @param popupWindow
     * @param photoCode
     */
    void photo(PopupWindow popupWindow,int photoCode);

    /**
     * 取消
     * @param popupWindow
     */
    void cancel(PopupWindow popupWindow);

}
