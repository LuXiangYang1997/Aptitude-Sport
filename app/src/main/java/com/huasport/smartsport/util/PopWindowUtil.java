package com.huasport.smartsport.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huasport.smartsport.R;
import com.huasport.smartsport.constant.StatusVariable;

public class PopWindowUtil {

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


}
