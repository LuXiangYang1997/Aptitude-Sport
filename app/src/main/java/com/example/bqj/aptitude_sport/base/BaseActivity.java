package com.example.bqj.aptitude_sport.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.example.bqj.aptitude_sport.R;
import com.example.bqj.aptitude_sport.databinding.ToolbarLayoutBinding;


/**
 * Created by 刘清林 on 2018/3/3.
 * 邮箱：1586864901@qq.com
 */

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements IBaseActivity {
    protected V binding;
    protected VM viewModel;
    public ToolbarLayoutBinding toolbarBinding;
    private View decorView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decorView = getWindow().getDecorView();
        initParam();
        initData();
        initViewDataBinding();
        initViewObservable();
        viewModel.onCreate();
        viewModel.registerRxBus();
    }


    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, initContentView());
        binding.setVariable(initVariableId(), viewModel = initViewModel());
        View root = binding.getRoot();
        if (root instanceof LinearLayout) {
            View publicView = LayoutInflater.from(this).inflate(R.layout.toolbar_layout, null);
            ((LinearLayout) root).addView(publicView, 0);
            toolbarBinding = DataBindingUtil.bind(publicView);
        }
    }
    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(initVariableId(), viewModel);
        }
    }

    public void startActivity2(Class<?> view) {
        Intent intent = new Intent(this, view);
        startActivity(intent);
    }

    public void startActivity3(Intent intent) {
        startActivity(intent);
    }


    public void finish2() {
        finish();
    }

    @Override
    public void initParam() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView();

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public abstract VM initViewModel();

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        viewModel.onRestart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.removeRxBus();
        viewModel.onDestroy();
        viewModel = null;
    }
}
