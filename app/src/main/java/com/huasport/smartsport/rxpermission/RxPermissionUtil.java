package com.huasport.smartsport.rxpermission;

import android.app.Activity;

import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by 陆向阳 on 2018/7/23.
 */

public class RxPermissionUtil {
    /**
     * 获取权限
     * @param context
     * @param permission
     * @param basePermissionCallback
     */
    public static void getPermission(final Activity context, String permission, final RxPermissionUtilCallback basePermissionCallback) {

        RxPermissions rxPermissions = new RxPermissions(context);
        rxPermissions.requestEach(permission).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.granted) {
                    basePermissionCallback.grand(true);
                } else {
                    basePermissionCallback.grand(false);
                }
            }
        });

    }


}
