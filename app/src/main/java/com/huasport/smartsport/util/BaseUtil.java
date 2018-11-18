package com.huasport.smartsport.util;

import com.huasport.smartsport.MyApplication;

/**
 * Created by litong on 2018/2/23.
 * 工具类
 */

public class BaseUtil
{
    private static BaseUtil utils;
    private final MyApplication context;

    private BaseUtil()
    {
        context = MyApplication.getInstance();
    }

    public static synchronized BaseUtil getInstance()
    {
        if (utils == null)
        {
            utils = new BaseUtil();
        }
        return utils;
    }

    /**
     * dp转px
     **/
    public int dip2px(int dipValue)
    {
        float reSize = context.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    public int dip2px(float dipValue)
    {
        float reSize = context.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public int sp2px(float spValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
