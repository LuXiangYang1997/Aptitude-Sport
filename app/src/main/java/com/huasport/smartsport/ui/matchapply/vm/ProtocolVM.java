package com.huasport.smartsport.ui.matchapply.vm;

import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.databinding.ActivityProtocolBinding;
import com.huasport.smartsport.ui.matchapply.view.ProtocolActivity;

/**
 * Created by 陆向阳 on 2018/6/30.
 */

public class ProtocolVM extends BaseViewModel {

    private ProtocolActivity protocolActivity;
    private final ActivityProtocolBinding binding;

    public ProtocolVM(ProtocolActivity protocolActivity) {
        this.protocolActivity=protocolActivity;
        binding = protocolActivity.getBinding();
        initData();
    }

    private void initData() {
        WebSettings settings = binding.webView.getSettings();
        //设置WebView支持JS
        settings.setJavaScriptEnabled(true);
        settings.setTextSize(WebSettings.TextSize.LARGEST);
        //如果不设置WebViewClient，请求会跳转系统浏览器
        binding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242

                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242

                return false;
            }

        });
        binding.webView.loadUrl("http://static.appapi.zntyydh.com:81/disclaimer.html");
    }


}
