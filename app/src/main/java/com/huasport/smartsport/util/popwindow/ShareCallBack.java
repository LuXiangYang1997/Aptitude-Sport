package com.huasport.smartsport.util.popwindow;

import android.widget.PopupWindow;

/**
 * 分享
 */
public interface ShareCallBack {

    /**
     * 微信朋友圈分享
     * @param popupWindow
     */
    void wechatQuanShare(PopupWindow popupWindow);


    /**
     * 微信好友分享
     * @param popupWindow
     */
    void weChatFriendsShare(PopupWindow popupWindow);

}
