package com.huasport.smartsport.ui.matchapply.view;

import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.WebviewLayoutBinding;

/**
 * Created by 陆向阳 on 2018/8/21.
 */

public class BannerRuleActivity extends BaseActivity<WebviewLayoutBinding, BaseViewModel> {

    private String webUrl = "";
    private String title = "";

    @Override
    public int initContentView() {
        return R.layout.webview_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public BaseViewModel initViewModel() {
        return new BaseViewModel();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        webUrl = getIntent().getStringExtra(StatusVariable.WEBURL);
        title = getIntent().getStringExtra(StatusVariable.TITLE);

        setTitle(title);
        back();

        binding.webview.setWebViewClient(new WebViewClient());// 设置 WebViewClient
        binding.webview.setWebChromeClient(new WebChromeClient());// 设置 WebChromeClient

        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.loadUrl(webUrl);

    }
}