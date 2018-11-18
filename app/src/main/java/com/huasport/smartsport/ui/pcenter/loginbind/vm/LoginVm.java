package com.huasport.smartsport.ui.pcenter.loginbind.vm;

import android.content.Intent;
import android.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.LoginLayoutBinding;
import com.huasport.smartsport.rxbus.RxBus;
import com.huasport.smartsport.ui.pcenter.loginbind.api.LoginApi;
import com.huasport.smartsport.ui.pcenter.loginbind.bean.GetVertifyCodeResultBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.wxapi.ThirdApi;
import com.huasport.smartsport.wxapi.ThirdPart;
import com.huasport.smartsport.wxapi.callback.ISinaWbCallBack;
import com.lzy.okgo.model.Response;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import rx.Subscription;
import rx.functions.Action1;

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
    private LoginApi loginApi;
    private boolean loginState;
    private ThirdPart thirdPart;
    private ThirdApi mThirdApi;
    private SsoHandler mSinaWbHandler;
    private Subscription subscribe;

    public LoginVm(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        binding = loginActivity.getBinding();
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        loginApi = new LoginApi(loginActivity);

        toastUtil = new ToastUtil(loginActivity);

        thirdPart = new ThirdPart(loginActivity);

        mThirdApi = new ThirdApi(loginActivity);

        subscribe = RxBus.getInstance().toObserverable(String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (s.equals(StatusVariable.FINISHLOGIN)) {
                    boolean clickState = MyApplication.getInstance().getClickState();
                    if (clickState) {
                        IntentUtil.finishPage(loginActivity, StatusVariable.PCENTERCODE);
                    } else {
                        IntentUtil.finishPage(loginActivity, StatusVariable.NORMALCODE);
                    }
                    MyApplication.getInstance().setClickState(false);
                }
            }
        });
    }


    /**
     * 获取验证码
     */
    public void getVertifyCode() {

        phoneNumber = phoneNum.get().trim();

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
                if (!EmptyUtil.isEmpty(getVertifyCodeResultBean)) {
                    int resultCode = getVertifyCodeResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {

                        final Timer mTimer = new Timer();

                        TimerTask mTimerTask = new TimerTask() {
                            @Override
                            public void run() {
                                if (countTime == 0) {
                                    countTime = 60;
                                    isEnableVerifyCode.set(true);
                                    mTimer.cancel();
                                    vertifyCodeText.set(loginActivity.getResources().getString(R.string.login_reset_get));
                                } else {
                                    countTime--;
                                    vertifyCodeText.set(loginActivity.getResources().getString(R.string.login_reset_get) + " " + countTime);
                                }
                            }
                        };
                        mTimer.schedule(mTimerTask, 1000, 1000);

                    } else {
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

                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.centerToast(msg);
                }


            }
        });


    }

    /**
     * 验证码登录
     */
    public void login() {
        phoneNumber = phoneNum.get().trim();
        verifyCodeStr = verifyCode.get().trim();

        if (EmptyUtil.isEmpty(phoneNumber) || EmptyUtil.isEmpty(verifyCodeStr)) {
            toastUtil.centerToast(loginActivity.getResources().getString(R.string.login_empty));
            return;
        }
        if (!Util.isPhoneNumber(phoneNumber)) {
            toastUtil.centerToast(loginActivity.getResources().getString(R.string.login_phonenum_error));
            return;
        }

        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl);
        params.put("baseMethod", Method.LOGINSMS);
        params.put("phoneNum", phoneNumber);
        params.put("verifyCode", verifyCodeStr);
        params.put("regTerminal", "ANDROID");
        MyApplication.getInstance().setLoginChannel(StatusVariable.SMSLOGIN);
        loginApi.login(params);
    }

    /**
     * 微信登录
     */
    public void weChatLogin() {

        if (!thirdPart.isAppAvilible(ThirdPart.PACKAGE_WX)) {
            toastUtil.centerToast(loginActivity.getResources().getString(R.string.wx_no_install));
            return;
        }
        MyApplication.getInstance().setLoginChannel(StatusVariable.THIRDLOGIN);
        thirdPart.wxLogin();
    }

    /**
     * 微博登录
     */
    public void weiBoLogin() {

        if (thirdPart.isAppAvilible(ThirdPart.PACKAGE_SINAWB)) {
            MyApplication.getInstance().setLoginChannel(StatusVariable.THIRDLOGIN);
            thirdPart.sinaWbLogin(mISinaWbCallBack);
        } else {
            toastUtil.centerToast(loginActivity.getResources().getString(R.string.sinawb_no_install));
        }

    }

    /**
     * qq登录
     */
    public void qqLogin() {
        MyApplication.getInstance().setLoginChannel(StatusVariable.THIRDLOGIN);
        thirdPart.qqLogin(mQQLoginListener);

    }

    /**
     * 返回事件
     * 判断是否登录，没有登录直接返回首页
     */
    public void back() {

        if (!EmptyUtil.isEmpty(loginState)) {

            if (!loginState) {
                IntentUtil.finishPage(loginActivity, StatusVariable.MATCHAPPLYCODE);
            }else{
                loginActivity.finish();
            }

        }
    }

    //新浪微博回调
    private ISinaWbCallBack mISinaWbCallBack = new ISinaWbCallBack() {
        @Override
        public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
            if (oauth2AccessToken.isSessionValid()) {//授权成功
                toastUtil.centerToast(loginActivity.getResources().getString(R.string.sina_login_success));
                mThirdApi.sinaWbServerLogin(oauth2AccessToken.getToken(), oauth2AccessToken.getUid());
            } else {
                toastUtil.centerToast(loginActivity.getResources().getString(R.string.sina_login_failed));
            }

        }

        @Override
        public void cancel() {
            toastUtil.centerToast(loginActivity.getResources().getString(R.string.sina_login_cancle));
        }

        @Override
        public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
            toastUtil.centerToast(loginActivity.getResources().getString(R.string.sina_login_failed)
                    + "," + wbConnectErrorMessage.getErrorMessage());
        }
    };
    //qq登陆回调
    private IUiListener mQQLoginListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            if (o == null) {
                toastUtil.centerToast(loginActivity.getResources().getString(R.string.qq_login_failed));
                return;
            }
            try {
                JSONObject jo = (JSONObject) o;
                int ret = jo.getInt("ret");
                if (ret == 0) {
                    toastUtil.centerToast(loginActivity.getResources().getString(R.string.qq_login_success));
                    String openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    thirdPart.getTencent().setOpenId(openID);
                    thirdPart.getTencent().setAccessToken(accessToken, expires);
                    mThirdApi.qqServerLogin(thirdPart.getTencent());
                }

            } catch (Exception e) {
                e.printStackTrace();
                toastUtil.centerToast(loginActivity.getResources().getString(R.string.qq_login_failed));
            }
        }

        @Override
        public void onError(UiError uiError) {
            toastUtil.centerToast(loginActivity.getResources().getString(R.string.qq_login_failed) + uiError.errorMessage);
        }

        @Override
        public void onCancel() {
            toastUtil.centerToast(loginActivity.getResources().getString(R.string.qq_login_cancel));
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSinaWbHandler = thirdPart.getSinaWbHandler();
        if (mSinaWbHandler != null) {
            mSinaWbHandler.authorizeCallBack(requestCode, resultCode, data);
        } else if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mQQLoginListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loginState = MyApplication.getInstance().getLoginState();
    }

    @Override
    public void onDestroy() {
        if (!subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
        super.onDestroy();
    }
}
