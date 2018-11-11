package com.huasport.smartsport.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;


/**
 * BaseViewModel
 */
public class BaseViewModel implements IBaseViewModel {
    protected Context context;

    public BaseViewModel() {
    }

    public BaseViewModel(Context context) {
        this.context = context;
    }

    public BaseViewModel(Fragment fragment) {
        this(fragment.getContext());
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void registerRxBus() {
    }

    @Override
    public void removeRxBus() {
    }

    @Override
    public void onResume() {
    }

    /**
     * <p>
     * 跳转回显
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    public void onPause() {

    }

    //懒加载，Fragment中用
    public void loadData() {

    }

    public void onRestart() {

    }
}
