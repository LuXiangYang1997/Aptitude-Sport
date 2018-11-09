package com.example.bqj.aptitude_sport.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {

    private Context context = null;
    private static Toast mToast = null;

    public ToastUtil(Context context) {
        this.context=context;
    }

    /**
     * 普通短Toast
     * @param context
     * @param content 内容
     */
    public static void normalShortTost(Context context,String content){

        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();

    }
    /**
     * 普通长Toast
     * @param context
     * @param content 内容
     */
    public static void normalLongTost(Context context,String content){

        Toast.makeText(context,content,Toast.LENGTH_LONG).show();

    }
    /**
     * 在中间弹出Toast
     * @param content 内容
     */
    public static void centerToast(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

}
