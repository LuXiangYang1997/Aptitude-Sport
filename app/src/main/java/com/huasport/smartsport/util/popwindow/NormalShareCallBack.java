package com.huasport.smartsport.util.popwindow;

import android.widget.PopupWindow;

public interface NormalShareCallBack {
    /**
     * QQ好友分享
     * @param popupWindow
     */
    void qqFriendsShare(PopupWindow popupWindow);

    /**
     * QQ空间分享
     * @param popupWindow
     */
    void qqSpaceShare(PopupWindow popupWindow);

    /**
     * 微信好友分享
     * @param popupWindow
     */
    void wechatFriendsShare(PopupWindow popupWindow);

    /**
     * 微信朋友圈分享
     * @param popupWindow
     */
    void wechatQuanShare(PopupWindow popupWindow);

    /**
     * 微博分享
     * @param popupWindow
     */
    void weiBoShare(PopupWindow popupWindow);


}
