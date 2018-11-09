package com.example.bqj.aptitude_sport.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 刘清林 on 2018/3/3.
 * 邮箱：1586864901@qq.com
 */
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements IBaseActivity {
    protected V binding;
    protected VM viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        boolean b = initStatus();
        super.onCreate(savedInstanceState);
        initParam();
    }

    /*
     * 是否支持沉浸式状态栏
     * */
    public boolean initStatus() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(), container, false);
        binding.setVariable(initVariableId(), viewModel = initViewModel());

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

        initViewObservable();

    }

    @Override
    public void initParam() {

    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(initVariableId(), viewModel);
        }
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

    public boolean onBackPressed() {
        return false;
    }

    protected abstract void loadData();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        viewModel.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
