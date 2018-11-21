package com.huasport.smartsport.api;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.ui.pcenter.bean.VBean;
import com.huasport.smartsport.util.ApkUtils;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class DownloadApkManager extends Service {


    private File file;
    private int vision;
    private final ToastUtil toastUtil;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Context context;

    public DownloadApkManager(Context context) {
        this.context = context;
        toastUtil = new ToastUtil(context);
    }

    //通过Handler发送消息进行操作
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                VBean.ResultBean.VersionBean vBean = (VBean.ResultBean.VersionBean) msg.obj;
                initVersion(vBean.getDownUrl(), vBean.getDesc(), vBean.isIsForcedUpgrade(), file);
            } else if (msg.what == 1) {//判断是否处于移动网络状态下
                final VBean vBean = (VBean) msg.obj;

                BaseDialog.show(MyApplication.getActivity(), "更新", "当前处于移动网络下，是否继续下载更新", "确定", "取消", false, false, 0, new DialogCallBack() {
                    @Override
                    public void submit(CustomDialog.Builder customDialog) {
                        toastUtil.centerToast("正在下载");
                        downLoadApk(vBean);
                    }

                    @Override
                    public void cancel(CustomDialog.Builder customDialog) {
                        if (vBean.getResult().getVersion().isIsForcedUpgrade()) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        } else {
                            customDialog.dismiss();
                        }
                    }
                });


//                final CustomDialog.Builder customdialog = new CustomDialog.Builder(MyApplication.getActivity());
//                customdialog.setCancelable(false);
//
//                customdialog.setGravity(Gravity.CENTER)
//                        .setContentView(R.layout.updata_noqiangzhi)
//                        .setText(R.id.content, "更新")
//                        .setText(R.id.detailMsg, "当前处于移动网络下，是否继续下载更新")
//                        .setOnClickListener(R.id.submit, "确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                downLoadApk(vBean);
//                            }
//                        }).setOnClickListener(R.id.cancel, "取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (customdialog != null) {
//                            if (vBean.getResult().getVersion().isIsForcedUpgrade()) {
//                                android.os.Process.killProcess(android.os.Process.myPid());
//                            } else {
//                                customdialog.dismiss();
//                            }
//
//                        }
//                    }
//                }).setWidth(0.8f).show();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void startDownLoad() {
//  获取线上版本号与当前版本号比对，是否有更新版本
        int version = Util.getVersion(context);
        Log.e("Version", version + "");
        HashMap versionParams = new HashMap();
        versionParams.put("baseMethod", Method.VERSION_CODE);
        versionParams.put("platform", "android");
        versionParams.put("t", String.valueOf(System.currentTimeMillis()));
        versionParams.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(context, versionParams, new RequestCallBack<VBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<VBean> response) {
                VBean vBean = response.body();
                if (!EmptyUtil.isEmpty(vBean)){
                    int resultCode = vBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        VBean.ResultBean resultBean = vBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            VBean.ResultBean.VersionBean versionBean = resultBean.getVersion();
                            String vCodeStr = versionBean.getVersion();
                            String vStr = vCodeStr.replace(".", "");
                            int vCode = Integer.parseInt(vStr);
                            if (vCode > Util.getVersion(context)) {
                                // initVersion(vBean.getResult().getVersion().getDownUrl(), vBean.getResult().getVersion().getDesc(), vBean.getResult().getVersion().isIsForcedUpgrade());
                                if (Util.isWifiConnected(context)) {
                                    toastUtil.centerToast("正在下载");
                                    downLoadApk(vBean);
                                    //DownAPKService.startService(MainActivity.this, "ztyds", vBean.getResult().getVersion().getDownUrl());
                                } else {
                                    Message message = new Message();
                                    message.obj = vBean;
                                    message.what = 1;
                                    handler.sendMessage(message);
                                }
                            }
                        }

                    }
                }
            }

            @Override
            public VBean parseNetworkResponse(String jsonResult) {
                VBean vBean = JSON.parseObject(jsonResult, VBean.class);
                return vBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });

    }

    private void downLoadApk(final VBean vBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // "http://yapkwww.cdn.anzhi.com/data4/apk/201808/29/79d6474eb951ef979dab3ca3d6ceb005_15874100.apk"
                file = downLoadFile(vBean.getResult().getVersion().getDownUrl());
                Message message = new Message();
                message.obj = vBean.getResult().getVersion();
                message.what = 0;
                handler.sendMessage(message);
            }
        }).start();
    }

    //通过url下载apk文件
    protected File downLoadFile(String httpUrl) {
// TODO Auto-generated method stub
        final String fileName = "ztyd.apk";//文件名称
        File tmpFile = new File("/sdcard/update");//创建文件夹存储文件
        if (!tmpFile.exists()) {//判断是否存在这个文件
            tmpFile.mkdir();
        }
        final File file = new File("/sdcard/update/" + fileName);
        //下载
        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[256];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {
                    Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    while (count <= 100) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }
                        } else {
                            break;
                        }

                    }
                }

                conn.disconnect();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }

        return file;
    }

    /*
     * @params desc 描述
     * @params downUrl 下载地址
     * 是否强制更新
     * */
    private void initVersion(final String downUrl, final String desc, boolean isForcedUpgrade, final File file) {
        vision = Util.getVersion(context);//系统版本
//        if (isForcedUpgrade) {//强制更新

        BaseDialog.show(MyApplication.getActivity(), "更新", desc, "确定", "取消", false, isForcedUpgrade, 0, new DialogCallBack() {
            @Override
            public void submit(CustomDialog.Builder customDialog) {
                SharedPreferencesUtil.clear(context);
                ApkUtils.installApk(MyApplication.getActivity(), file);
            }

            @Override
            public void cancel(CustomDialog.Builder customDialog) {
                customDialog.dismiss();
            }
        });
    }
}
