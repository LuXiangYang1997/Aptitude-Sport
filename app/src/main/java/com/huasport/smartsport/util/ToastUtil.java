package com.huasport.smartsport.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.huasport.smartsport.R;

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
    public void normalShortTost(Context context,String content){

        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();

    }
    /**
     * 普通长Toast
     * @param context
     * @param content 内容
     */
    public void normalLongTost(Context context,String content){

        Toast.makeText(context,content,Toast.LENGTH_LONG).show();

    }
    /**
     * 在中间弹出Toast
     * @param content 内容
     */
    public void centerToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * 自定义Toast
     * @param
     * @param msg
     */
    public void showTopSnackBar(String msg) {

        View toastView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView msgText = toastView.findViewById(R.id.tv_msg);
        msgText.setText(msg);
        mToast.setView(toastView);
        mToast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 120);
        mToast.show();

    }

}
