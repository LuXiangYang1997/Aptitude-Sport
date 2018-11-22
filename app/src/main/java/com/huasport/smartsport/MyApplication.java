package com.huasport.smartsport;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.huasport.smartsport.bean.LocationBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    private static MyApplication instance;
    private static Activity activity;
    private boolean clickState = false;//点击事件状态
    private int loginChannel = StatusVariable.SMSLOGIN;
    public LocationBean locationBean = new LocationBean();
    private static List<Activity> lists = new ArrayList<>();
    private  UserBean userBean;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Accept", "application/json;charset=UTF-8");

        //初始化
        OkGo.getInstance().init(this).addCommonHeaders(httpHeaders);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //初始化SmartRefreshLayout
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setReboundDuration(1000);
                return new ClassicsFooter(context);
            }
        });
        //设置smartrefreshlayout加载刷新样式为经典样式
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {

                return new ClassicsHeader(context);
            }
        });

    }

    public static MyApplication getInstance() {
        return instance;
    }

    //添加Activity
    public static void addActivity(Activity activity) {
        lists.add(activity);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserBean getUserBean() {

        if (!EmptyUtil.isEmpty(userBean)) {
            return userBean;
        } else {
            return null;
        }
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    /**
     * 获取用户登录状态
     */
    public boolean getLoginState() {

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

    public LocationBean getLocationBean() {
        return locationBean;
    }

    public void setLocationBean(LocationBean locationBean) {
        this.locationBean = locationBean;
    }

    //获取Activity
    public static Activity getActivity() {
        if (lists != null) {
            activity = lists.get(lists.size() - 1);
        }
        return activity;
    }

    //移除Activity
    public static void removeActivity(Activity activity) {
        if (lists.contains(activity)) {
            lists.remove(activity);
        }
    }


}