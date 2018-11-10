package com.example.bqj.aptitude_sport.ui.pcenter.loginbind.api;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.bqj.aptitude_sport.MyApplication;
import com.example.bqj.aptitude_sport.R;
import com.example.bqj.aptitude_sport.api.OkHttpUtil;
import com.example.bqj.aptitude_sport.api.RequestCallBack;
import com.example.bqj.aptitude_sport.bean.UserBean;
import com.example.bqj.aptitude_sport.constant.StatusVariable;
import com.example.bqj.aptitude_sport.ui.pcenter.loginbind.bean.LoginResultBean;
import com.example.bqj.aptitude_sport.ui.pcenter.loginbind.view.LoginActivity;
import com.example.bqj.aptitude_sport.util.EmptyUtil;
import com.example.bqj.aptitude_sport.util.IntentUtil;
import com.example.bqj.aptitude_sport.util.SharedPreferencesUtil;
import com.example.bqj.aptitude_sport.util.ToastUtil;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

public class LoginApi {

    private Context context;
    private ToastUtil mToast;

    public LoginApi(Context context) {
        this.context = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        mToast = new ToastUtil(context);

    }

    /**
     * 用户登录 (短信和第三方登录)
     *
     * @param params 登录需要的参数
     */
    public void login(HashMap params) {

        OkHttpUtil.postRequest(context, params, new RequestCallBack<LoginResultBean>() {
            @Override
            public void onSuccess(Response<LoginResultBean> response) {

                LoginResultBean loginResultBean = response.body();

                if (!EmptyUtil.isEmpty(loginResultBean)) {
                    int resultCode = loginResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {

                        mToast.centerToast(context.getResources().getString(R.string.login_success));

                        LoginResultBean.ResultBean resultBean = loginResultBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            LoginResultBean.ResultBean.RegisterBean registerBean = resultBean.getRegister();
                            if (!EmptyUtil.isEmpty(registerBean)) {

                                saveUserInfo(registerBean, resultBean.getToken());

                            }
                        }

                    } else {
                        if (!EmptyUtil.isEmpty(loginResultBean.getResultMsg())) {
                            mToast.centerToast(loginResultBean.getResultMsg());
                        }
                    }
                }
            }

            @Override
            public LoginResultBean parseNetworkResponse(String jsonResult) {

                LoginResultBean loginResultBean = JSON.parseObject(jsonResult, LoginResultBean.class);

                return loginResultBean;
            }

            @Override
            public void onFailed(int code, String msg) {

                if (!EmptyUtil.isEmpty(msg)) {

                    mToast.centerToast(msg);

                }

            }
        });
    }

    /**
     * 存储用户信息
     *
     * @param registerBean
     */
    private void saveUserInfo(LoginResultBean.ResultBean.RegisterBean registerBean, String token) {

        UserBean userBean = new UserBean();
        userBean.setAccount(registerBean.getAccount());
        userBean.setBindPhone(registerBean.isIsBindPhone());
        userBean.setCard(registerBean.isIsCard());
        userBean.setGender(registerBean.getGender());
        userBean.setHeadimgUrl(registerBean.getHeadimgUrl());
        userBean.setRealName(registerBean.getRealName());
        userBean.setNickName(registerBean.getNickName());
        userBean.setPhone(registerBean.getPhone());
        userBean.setRegisterCode(registerBean.getRegisterCode());
        userBean.setToken(token);
        //存储用户信息
        SharedPreferencesUtil.setBean(context, "UserBean", userBean);
        boolean clickState = MyApplication.getInstance().getClickState();
        if (clickState) {
            IntentUtil.finishPage(context, StatusVariable.PCENTERCODE);
        } else {
            IntentUtil.finishPage(context, StatusVariable.NORMALCODE);
        }

    }
}
