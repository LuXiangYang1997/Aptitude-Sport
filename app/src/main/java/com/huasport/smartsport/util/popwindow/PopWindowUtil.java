package com.huasport.smartsport.util.popwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.wxapi.ThirdPart;

public class PopWindowUtil {

    private static String nickName;

    /**
     * 选择图片弹出框
     */
    public static void selectPicture(final Context context, final SelectPicCallBack selectPicCallBack) {

        View popView = LayoutInflater.from(context).inflate(R.layout.select_picture_layout, null);

        final PopupWindow popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Util.backgroundAlpha(context, 0.5f);

        popupWindow.setOutsideTouchable(true);//点击外部消失
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);

        TextView tv_camera = popView.findViewById(R.id.tv_camera);//拍照
        TextView tv_photo = popView.findViewById(R.id.tv_photo);//相册
        TextView tv_cancel = popView.findViewById(R.id.tv_cancel);//取消

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPicCallBack.camera(popupWindow, StatusVariable.CAMERACODE);

            }
        });
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPicCallBack.photo(popupWindow,StatusVariable.PHOTOCODE);

            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPicCallBack.cancel(popupWindow);

            }
        });

        //popwindow消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                Util.backgroundAlpha(context,1f);

            }
        });
    }

    /**
     * 发现-发布点击弹出框
     */
    public static void releaseClick(final Context context, TextView textView, final ReleaseCallBack releaseCallBack){

        View releaseView = LayoutInflater.from(context).inflate(R.layout.release_layout, null);

        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setBackgroundDrawable(new ColorDrawable());

        popupWindow.setFocusable(true);

        popupWindow.setTouchable(true);

        popupWindow.setOutsideTouchable(true);

        popupWindow.setContentView(releaseView);

        popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int width = popupWindow.getContentView().getMeasuredWidth();

        popupWindow.showAsDropDown(textView, textView.getWidth() - width, 0);

        Util.backgroundAlpha(context, 0.5f);

        //动态
        releaseView.findViewById(R.id.tv_dynamic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseCallBack.dynamic(popupWindow);
            }
        });
        //文章
        releaseView.findViewById(R.id.tv_article).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseCallBack.article(popupWindow);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(context, 1.0f);
            }
        });

    }

    /**
     * 分享
     */
    public static void share(final Context context, final ShareCallBack shareCallBack){

        View shareView = LayoutInflater.from(context).inflate(R.layout.discover_share_layout, null, false);
        final PopupWindow sharePop = new PopupWindow(shareView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sharePop.setContentView(shareView);
        sharePop.showAtLocation(shareView, Gravity.BOTTOM, 0, 0);
        sharePop.setOutsideTouchable(false);
        Util.backgroundAlpha(context, 0.5f);

        //微信好友分享
        shareView.findViewById(R.id.ll_social_shareWechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareCallBack.weChatFriendsShare(sharePop);

            }
        });


        //微信朋友圈分享
        shareView.findViewById(R.id.ll_social_sharefriend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareCallBack.wechatQuanShare(sharePop);

            }
        });


        shareView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePop.dismiss();
            }
        });

        sharePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(context, 1f);
            }
        });

    }

}
