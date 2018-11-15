package com.huasport.smartsport.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.bean.WXLoginBean;
import com.huasport.smartsport.bean.WeChatMsgBean;
import com.huasport.smartsport.ui.pcenter.loginbind.api.LoginApi;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;

/**
 * 微信回掉界面（包名不可改）
 * Created by lwd on 2018/6/20.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private LoginApi mLoginApi;
    private ToastUtil toastUtil;
    //    private WaitDialog mWaitDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toastUtil = new ToastUtil(this);
        api = WXAPIFactory.createWXAPI(this, ThirdPart.WX_APPID, true);
        api.handleIntent(this.getIntent(), this);
        api.registerApp(ThirdPart.WX_APPID);
        mLoginApi = new LoginApi(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);//必须调用此句话
    }

    /**
     * 微信发送的请求将回调此方法
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("lwd", baseReq.toString());
        finish();
    }

    /**
     * 发送到微信请求的相应结果
     *
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {
        LogUtil.e("WXEntryActivity resp:" + resp.errCode);
        String tip = "";
        if (resp.getType() == ThirdPart.RETURN_MSG_TYPE_SHARE) {//微信分享
            LogUtil.e("WXEntryActivity share");
            switch (resp.errCode) {
                //发送成功
                case BaseResp.ErrCode.ERR_OK:
                    tip = "分享成功";
                    break;
                //发送取消
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    tip = "分享失败";
                    break;
                //发送拒绝
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    tip = "分享被拒绝";
                    break;
                default:
                    //发送返回
                    tip = "分享异常";
                    break;
            }
            finish();
        } else {//微信登陆
            switch (resp.errCode) {
                //发送成功
                case BaseResp.ErrCode.ERR_OK:
                    SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                    if (sendResp != null) {
                        LogUtil.e("WXEntryActivity sendResp not null");
                        String code = sendResp.code;
                        getAccess_token(WXEntryActivity.this, code);
                    }
                    LogUtil.e("WXEntryActivity sendResp null");
                    break;
                //发送取消
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    tip = "发送取消";
                    finish();
                    break;
                //发送拒绝
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    tip = "发送拒绝";
                    finish();
                    break;
                default:
                    //发送返回
                    tip = "登陆异常";
                    finish();
                    break;
            }
        }

        if (!TextUtils.isEmpty(tip)) {
//            ToastUtils.toast(this, tip);
            LogUtil.e("WXEntryActivity toast:" + tip);
        }
    }

    /**
     * 获取openid  accessToken值用于后期操作
     *
     * @param wxEntryActivity
     * @param code            请求码
     */
    private void getAccess_token(final WXEntryActivity wxEntryActivity, String code) {
        LogUtil.e("WXEntryActivity getAccess_token");
        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + ThirdPart.WX_APPID
                + "&secret="
                + ThirdPart.WX_APPSECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

//        if (mWaitDialog == null) {
//            mWaitDialog = new WaitDialog(WXEntryActivity.this);
//        }
//        mWaitDialog.show();

        OkHttpUtil.getThirdInfo(this, path, new RequestCallBack<WeChatMsgBean>() {
            @Override
            public void onSuccess(Response<WeChatMsgBean> response) {
                try {
                    WeChatMsgBean weChatMsgBean = response.body();
                    if (!EmptyUtil.isEmpty(weChatMsgBean)) {
                        String access_token = weChatMsgBean.getAccess_token();
                        String openid = weChatMsgBean.getOpenid();
                        getUserMsg(wxEntryActivity, access_token, openid);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    finishAll(true);
                }
            }

            @Override
            public WeChatMsgBean parseNetworkResponse(String jsonResult) {

                WeChatMsgBean weChatMsgBean = JSON.parseObject(jsonResult, WeChatMsgBean.class);

                return weChatMsgBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                finishAll(true);
            }
        });
    }

    /**
     * 获取微信个人信息
     *
     * @param wxEntryActivity
     * @param access_token
     * @param openid
     */
    private void getUserMsg(final WXEntryActivity wxEntryActivity, final String access_token, final String openid) {
        LogUtil.e("WXEntryActivity getUserMsg");
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
        OkHttpUtil.getThirdInfo(this, path, new RequestCallBack<WXLoginBean>() {
            @Override
            public void onSuccess(Response<WXLoginBean> response) {
                WXLoginBean wxLoginBean = response.body();
                if (!EmptyUtil.isEmpty(wxLoginBean)) {
                    HashMap params = new HashMap();
                    params.put("nickName", wxLoginBean.nickname);
                    params.put("platform", "WEICHAT");
                    params.put("appId", ThirdPart.WX_APPID);
                    params.put("regTerminal", "ANDROID");
                    params.put("openId", wxLoginBean.openid);
                    params.put("headimgUrl", wxLoginBean.headimgurl);
                    params.put("gender", wxLoginBean.sex);
                    params.put("province", wxLoginBean.province);
                    params.put("city", wxLoginBean.city);
                    params.put("country", wxLoginBean.country);
                    params.put("baseMethod", Method.LOGINTHIRD);
                    params.put("appVersion", Util.getVersionName(wxEntryActivity));
                    params.put("baseUrl", Config.baseUrl);
                    mLoginApi.login(params);
                    finishAll(false);
                }

            }

            @Override
            public WXLoginBean parseNetworkResponse(String jsonResult) {

                WXLoginBean wxLoginBean = JSON.parseObject(jsonResult, WXLoginBean.class);

                return wxLoginBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                finishAll(true);
            }
        });

    }

    private void finishAll(boolean isToast) {
//        if (mWaitDialog != null && mWaitDialog.isShowing()) {
//            mWaitDialog.dismiss();
//        }
        if (isToast) {
            toastUtil.centerToast(this.getResources().getString(R.string.wechat_authorizationlogin_failed));
        }
        WXEntryActivity.this.finish();
    }

}
