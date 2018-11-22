package com.huasport.smartsport.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;


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

    public void onNewIntent(Intent intent) {

    }
}
