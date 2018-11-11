package com.huasport.smartsport;

import android.app.Application;

import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;

public class MyApplication extends Application {

    private static MyApplication instance;
    private boolean clickState = false;//点击事件状态
    private int loginChannel = StatusVariable.SMSLOGIN;

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

    /**
     * 获取登录渠道 0：短信验证码登录 1：三方登录
     * @return
     */
    public int getLoginChannel() {
        return loginChannel;
    }

    /**
     * 更改登录渠道
     * @param loginChannel
     */
    public void setLoginChannel(int loginChannel) {
        this.loginChannel = loginChannel;
    }
}