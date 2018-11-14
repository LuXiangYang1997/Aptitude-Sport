package com.huasport.smartsport.util;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huasport.smartsport.R;


public class NullStateUtil {
    /**
     * 空数据情况下展示的界面
     * @param visible 是否显示空状态图
     */
    public static void setNullState(View view, boolean visible) {

        LinearLayout nulldatalayout = view.findViewById(R.id.nulldata);
        TextView tv_msgOne = nulldatalayout.findViewById(R.id.tv_msgTwo);
        TextView tv_msgTwo = nulldatalayout.findViewById(R.id.tv_msgTwo);

        tv_msgOne.setText("~空空如也~");
        tv_msgTwo.setText("");

        if (visible) {
            nulldatalayout.setVisibility(View.VISIBLE);
        } else {
            nulldatalayout.setVisibility(View.GONE);
        }
    }
}
