package com.huasport.smartsport.ui.pcenter.vm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.databinding.ActivityPersonalcenterwebBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.view.PersonalCenterWebActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created by 陆向阳 on 2018/7/21.
 */

public class PersonalCenterWebVM extends BaseViewModel {

    private PersonalCenterWebActivity personalCenterWebActivity;
    private final ActivityPersonalcenterwebBinding binding;
    private String token = "";
    private String version;
    private ToastUtil toastUtil;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PersonalCenterWebVM(PersonalCenterWebActivity personalCenterWebActivity) {
        this.personalCenterWebActivity = personalCenterWebActivity;
        binding = personalCenterWebActivity.getBinding();

        init();
        initData();
        initWebView();
    }

    /**
     * 初始化
     */
    private void init() {
        toastUtil = new ToastUtil(personalCenterWebActivity);
        version = Util.getVersionName(personalCenterWebActivity);
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if(!EmptyUtil.isEmpty(userBean)){
           token =  userBean.getToken();
        }

    }

    /*
     * 初始化WebView
     * */
    private void initWebView() {

        binding.webView.setWebViewClient(new WebViewClient() {
            //页面加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                super.onReceivedError(view, request, error);

                BaseDialog.show(personalCenterWebActivity, "退出提示", "您确定退出当前账号吗", "知道了", "取消", false, true,
                        0, new DialogCallBack() {
                            @Override
                            public void submit(CustomDialog.Builder customDialog) {
                                personalCenterWebActivity.finish();
                            }

                            @Override
                            public void cancel(CustomDialog.Builder customDialog) {
                                customDialog.dismiss();
                            }
                        });
            }
        });

    }

    @SuppressLint("WrongConstant")
    private void initData() {
        Intent intent = personalCenterWebActivity.getIntent();
        String webUrl = intent.getStringExtra("WebUrl");
        binding.webView.addJavascriptInterface(new AndroidtoJs(), "android");
        //加载web页
        //file:///android_asset/versionandback.html
        String myGradeUrl = webUrl + "?platform=Android&appToken=" + token + "&version=" + "v" + version+"&navHeader=1";
        Log.e("MyGrade", myGradeUrl);

        binding.webView.loadUrl(myGradeUrl);
        WebSettings settings = binding.webView.getSettings();
        settings.setJavaScriptEnabled(true);//支持js
        settings.setUseWideViewPort(true);//将图片调整到合适的大小
        settings.setLoadWithOverviewMode(true);//缩放至屏幕大小
        settings.setLoadsImagesAutomatically(true);//支持自动加载图片
        settings.setUserAgentString("ANDROIDAPP");
        binding.webView.canGoBack();//支持回退
        binding.webView.canGoForward();//支持进行跳转
        binding.webView.requestFocus();
        settings.setDomStorageEnabled(true);
        binding.webView.setScrollBarStyle(0);
        binding.webView.setWebChromeClient(new WebChromeClient());

    }

    /**
     * 必须在onResume和onPause中加入这两句，防止WebView一直执行
     */
    @Override
    public void onResume() {
        super.onResume();
        binding.webView.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.webView.pauseTimers();
    }

    //继承自Object类
    public class AndroidtoJs extends Object {

        @JavascriptInterface
        public void upTelephone(final String phone) {

            RxPermissions rxPermission = new RxPermissions(personalCenterWebActivity);

            rxPermission.request(Manifest.permission.CALL_PHONE)
                    .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean grand) {
                            if (!grand) {
                                toastUtil.centerToast("用户拒绝了权限");
                            } else {

                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:" + phone);
                                intent.setData(data);
                                personalCenterWebActivity.startActivity(intent);
                            }
                        }
                    });
        }

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void back(String msg) {
            personalCenterWebActivity.finish();
        }
    }
}

