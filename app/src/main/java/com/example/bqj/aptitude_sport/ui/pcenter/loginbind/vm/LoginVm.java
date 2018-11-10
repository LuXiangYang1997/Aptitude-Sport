package com.example.bqj.aptitude_sport.ui.pcenter.loginbind.vm;

import android.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.example.bqj.aptitude_sport.R;
import com.example.bqj.aptitude_sport.api.Method;
import com.example.bqj.aptitude_sport.api.OkHttpUtil;
import com.example.bqj.aptitude_sport.api.RequestCallBack;
import com.example.bqj.aptitude_sport.base.BaseViewModel;
import com.example.bqj.aptitude_sport.constant.StatusVariable;
import com.example.bqj.aptitude_sport.databinding.LoginLayoutBinding;
import com.example.bqj.aptitude_sport.ui.pcenter.loginbind.bean.GetVertifyCodeResultBean;
import com.example.bqj.aptitude_sport.ui.pcenter.loginbind.bean.LoginBean;
import com.example.bqj.aptitude_sport.ui.pcenter.loginbind.view.LoginActivity;
import com.example.bqj.aptitude_sport.util.Config;
import com.example.bqj.aptitude_sport.util.EmptyUtil;
import com.example.bqj.aptitude_sport.util.ToastUtil;
import com.example.bqj.aptitude_sport.util.Util;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class LoginVm extends BaseViewModel {

    private LoginActivity loginActivity;
    //手机号码
    public ObservableField<String> phoneNum = new ObservableField<>("");
    //验证码
    public ObservableField<String> verifyCode = new ObservableField<>("");
    //获取验证码按钮是否可以点击
    public ObservableField<Boolean> isEnableVerifyCode = new ObservableField<>(true);
    //获取验证码文本倒计时
    public ObservableField<String> vertifyCodeText = new ObservableField<>("获取验证码");
    private LoginLayoutBinding binding;
    private int countTime = 60;//倒计时间
    private String phoneNumber;
    private String verifyCodeStr;
    private ToastUtil toastUtil;

    public LoginVm(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        binding = loginActivity.getBinding();
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        toastUtil = new ToastUtil(loginActivity);

    }


    /**
     * 获取验证码
     */
    public void getVertifyCode() {

        phoneNumber = phoneNum.get().trim();
        verifyCodeStr = verifyCode.get().trim();

        if (EmptyUtil.isEmpty(phoneNumber)) {
            toastUtil.centerToast(loginActivity.getResources().getString(R.string.login_phonenum_empty));
            return;
        }
        if (!Util.isPhoneNumber(phoneNumber)) {
            toastUtil.centerToast(loginActivity.getResources().getString(R.string.login_phonenum_error));
            return;
        }
        //设置获取验证码不可点击
        isEnableVerifyCode.set(false);

        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl);
        params.put("baseMethod", Method.GETVERTIFYCODE);
        params.put("phoneNum", phoneNumber);

        OkHttpUtil.getRequest(loginActivity, params, new RequestCallBack<GetVertifyCodeResultBean>() {
            @Override
            public void onSuccess(Response<GetVertifyCodeResultBean> response) {
                GetVertifyCodeResultBean getVertifyCodeResultBean = response.body();
                if (!EmptyUtil.isEmpty(getVertifyCodeResultBean)){
                    int resultCode = getVertifyCodeResultBean.getResultCode();
                    if (resultCode==StatusVariable.REQUESTSUCCESS){

                        final Timer mTimer = new Timer();

                        TimerTask mTimerTask = new TimerTask() {
                            @Override
                            public void run() {
                                if (countTime == 0){
                                    countTime = 60;
                                    isEnableVerifyCode.set(true);
                                    mTimer.cancel();
                                    vertifyCodeText.set(loginActivity.getResources().getString(R.string.login_reset_get));
                                }else{
                                    countTime--;
                                    vertifyCodeText.set(loginActivity.getResources().getString(R.string.login_reset_get)+" "+countTime);
                                }
                            }
                        };
                        mTimer.schedule(mTimerTask,1000,1000);

                    }else{
                        toastUtil.centerToast(getVertifyCodeResultBean.getResultMsg());
                        isEnableVerifyCode.set(true);
                        vertifyCodeText.set(loginActivity.getResources().getString(R.string.login_reset_get));
                    }
                }

            }

            @Override
            public GetVertifyCodeResultBean parseNetworkResponse(String jsonResult) {

                GetVertifyCodeResultBean getVertifyCodeResultBean = JSON.parseObject(jsonResult, GetVertifyCodeResultBean.class);

                return getVertifyCodeResultBean;
            }

            @Override
            public void onFailed(int code, String msg) {

                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }


            }
        });


    }

    /**
     * 验证码登录
     */
    public void login() {

        if (EmptyUtil.isEmpty(phoneNum) || EmptyUtil.isEmpty(verifyCodeStr)) {
          toastUtil.centerToast(loginActivity.getResources().getString(R.string.login_empty));
            return;
        }


    }

    /**
     * 微信登录
     */
    public void weChatLogin() {


    }

    /**
     * 微博登录
     */
    public void weiBoLogin() {


    }

    /**
     * qq登录
     */
    public void qqLogin() {


    }


}
