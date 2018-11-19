package com.huasport.smartsport.util;

import android.location.Location;

/**
 * gps位置信息回调
 * Created by lwd on 2018/6/30.
 */

public interface ILocationCallBack {

    /**
     * 获取位置
     * @param location
     */
    void getLocation(Location location);

}
