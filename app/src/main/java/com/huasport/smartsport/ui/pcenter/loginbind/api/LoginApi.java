package com.huasport.smartsport.ui.pcenter.loginbind.api;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.rxbus.RxBus;
import com.huasport.smartsport.ui.pcenter.loginbind.bean.LoginResultBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

public class LoginApi {

    private Context context;
    private ToastUtil toastUtil;

    public LoginApi(Context context) {
        this.context = context;
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

                        LoginResultBean.ResultBean resultBean = loginResultBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            LoginResultBean.ResultBean.RegisterBean registerBean = resultBean.getRegister();
                            if (!EmptyUtil.isEmpty(registerBean)) {

                                if (registerBean.isIsBindPhone()){
                                    saveUserInfo(registerBean,resultBean.getToken());
                                }else{
                                    saveUserInfo(registerBean,resultBean.getToken());
                                    IntentUtil.startActivity(context,BindPhoneActivity.class);
                                }
                                int loginChannel = MyApplication.getInstance().getLoginChannel();
                                if (loginChannel == StatusVariable.THIRDLOGIN){
                                    RxBus.getInstance().post(StatusVariable.FINISHLOGIN);
                                }else{
                                    boolean clickState = MyApplication.getInstance().getClickState();
                                    if (clickState) {
                                        IntentUtil.finishPage(context, StatusVariable.PCENTERCODE);
                                    } else {
                                        IntentUtil.finishPage(context, StatusVariable.NORMALCODE);
                                    }
                                    MyApplication.getInstance().setClickState(false);
                                }
                            }
                        }

                        Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();
                    } else {
//                        if (!EmptyUtil.isEmpty(loginResultBean.getResultMsg())) {
//                            toastUtil.centerToast(loginResultBean.getResultMsg());
//                        }
                        Toast.makeText(context,loginResultBean.getResultMsg(),Toast.LENGTH_SHORT).show();
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

//                if (!EmptyUtil.isEmpty(msg)) {
//
//                    toastUtil.centerToast(msg);
//
//                }

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
        SharedPreferencesUtil.setBean(context,"UserBean",userBean);
    }
}
