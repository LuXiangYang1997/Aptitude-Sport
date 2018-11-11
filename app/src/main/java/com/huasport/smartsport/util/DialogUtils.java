package com.huasport.smartsport.util;

import android.support.annotation.StyleRes;

import com.huasport.smartsport.R;

/**
 * Created by 陆向阳 on 2018/6/28.
 */

public class DialogUtils {


    private static int mStyle = R.style.EasyDialogStyle;

    private static int mListStyle = R.style.EasyListDialogStyle;

    public static void initStyle(@StyleRes int style) {
        mStyle = style;
    }

    public static void initListStyle(@StyleRes int style) {
        mListStyle = style;
    }

    public static int getStyle() {
        return mStyle;
    }

    public static int getListStyle() {
        return mListStyle;
    }

}