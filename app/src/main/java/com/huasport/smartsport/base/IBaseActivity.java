package com.huasport.smartsport.base;

/**
 * Created by 刘清林 on 2018/3/3.
 * 邮箱：1586864901@qq.com
 */

public interface IBaseActivity {
    /**
     * 初始化界面传递参数
     */
    void initParam();
    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();

}
