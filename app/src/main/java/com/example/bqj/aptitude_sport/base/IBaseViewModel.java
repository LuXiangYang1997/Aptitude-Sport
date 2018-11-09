package com.example.bqj.aptitude_sport.base;

/**
 * Created by 刘清林 on 2018/3/3.
 * 邮箱：1586864901@qq.com
 */

public interface IBaseViewModel {
//    void initData();

    /**
     * View的界面创建时回调
     */
    void onCreate();

    /**
     * View的界面销毁时回调
     */
    void onDestroy();

    /**
     * 注册RxBus
     */
    void registerRxBus();

    /**
     * 移除RxBus
     */
    void removeRxBus();

    void onResume();

}
