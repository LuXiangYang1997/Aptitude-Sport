package com.example.bqj.aptitude_sport;

import android.app.Application;

import com.example.bqj.aptitude_sport.bean.UserBean;
import com.example.bqj.aptitude_sport.util.EmptyUtil;
import com.example.bqj.aptitude_sport.util.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;

public class MyApplication extends Application {

    private static MyApplication instance;
    private boolean clickState = false;//点击事件状态

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化
        OkGo.getInstance().init(this);

    }

    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserBean getUserBean() {

        UserBean userBean = (UserBean) SharedPreferencesUtil.getBean(this, "UserBean");

        if (!EmptyUtil.isEmpty(userBean)) {
            return userBean;
        } else {
            return null;
        }
    }

    /**
     * 获取用户登录状态
     */
    public boolean getLoginState() {
        UserBean userBean = (UserBean) SharedPreferencesUtil.getBean(this, "UserBean");
        if (!EmptyUtil.isEmpty(userBean)) {
            String token = userBean.getToken();
            if (!EmptyUtil.isEmpty(token)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * 修改点击事件状态 true ：首页、成绩页、底部点击个人中心页状态 false ：普通事件 默认false
     */
    public void setClickState(boolean clickState) {
        this.clickState = clickState;
    }
    /**
     * 获取点击事件状态 true ：首页、成绩页、底部点击个人中心页状态 false ：普通事件
     */
    public boolean getClickState() {

        return clickState;

    }
}