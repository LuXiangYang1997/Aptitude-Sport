package com.huasport.smartsport.base;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.huasport.smartsport.R;
import com.huasport.smartsport.databinding.ToolbarLayoutBinding;
import com.huasport.smartsport.util.EmptyUtil;


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

    public V getBinding() {
        return binding;
    }

    /**
     * 设置title
     */
    public void setTitle(String title) {
        if (!EmptyUtil.isEmpty(toolbarBinding)) {
            toolbarBinding.tvTitle.setText(title);
        }
    }

    /**
     * 设置左边img
     */
    public void setLeftImg(int imgId) {
        if (!EmptyUtil.isEmpty(toolbarBinding)) {
            toolbarBinding.imgLeft.setImageResource(imgId);
        }
    }

    /**
     * 设置右边img
     */
    public void setRightImg(int imgId) {
        if (!EmptyUtil.isEmpty(toolbarBinding)) {
            toolbarBinding.imgRight.setImageResource(imgId);
        }
    }

    /**
     * 设置右边text
     */
    public void setLeftText(String leftText) {
        if (!EmptyUtil.isEmpty(toolbarBinding)) {
            toolbarBinding.tvLeft.setText(leftText);
        }
    }

    /**
     * 设置右边text
     */
    public void setRightText(String rightText) {
        if (!EmptyUtil.isEmpty(toolbarBinding)) {
            toolbarBinding.tvRight.setText(rightText);
        }
    }

    /**
     * 普通返回事件
     */
    public void back() {
        if (!EmptyUtil.isEmpty(toolbarBinding)) {
            toolbarBinding.llLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        viewModel.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        viewModel.onRestart();
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
