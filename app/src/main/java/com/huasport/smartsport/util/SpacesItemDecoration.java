package com.huasport.smartsport.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 陆向阳 on 2018/6/8.
 */
//给item设置间距辅助类
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
//        outRect.left = space;//左间距
//        outRect.right = space;//右间距
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) != 0)
            outRect.bottom = space;
    }
}