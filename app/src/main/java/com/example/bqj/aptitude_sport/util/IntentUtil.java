package com.example.bqj.aptitude_sport.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntentUtil {

    private static File tempFile;

    /**
     * 跳转页面
     * @param context
     * @param tClass
     */
    public static void startActivity(Context context,Class<?> tClass){

        context.startActivity(new Intent(context,tClass));

    }

    /**
     * 注册广播
     *
     * @param context
     * @param action
     * @param broadcastReceiver
     */
    public static void registerBrocastReceiver(Context context, String action, BroadcastReceiver broadcastReceiver) {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action);
        context.registerReceiver(broadcastReceiver, intentFilter);

    }

    /**
     * 打开相机拍照
     */
    public static void openCamera(Activity activity, int code) {

        //获取系统版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        //激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 19) {
// 从文件中创建uri
                Uri uri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
        activity.startActivityForResult(intent, code);

    }


    /**
     * 拍照获取图片
     */
    public static void camera(Activity activity, int code) {
        Intent intent;

        if (Build.VERSION.SDK_INT < 19) {
            // 从文件中创建uri  
            Uri uri = Uri.fromFile(tempFile);
            intent = new Intent(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        activity.startActivityForResult(intent, code);

    }

    /**
     * 相册获取图片
     */
    public static void photoalbum(Activity activity, int code) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        activity.startActivityForResult(intent, code);
    }

    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}
