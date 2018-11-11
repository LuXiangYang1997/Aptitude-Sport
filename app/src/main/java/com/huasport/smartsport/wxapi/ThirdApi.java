package com.huasport.smartsport.wxapi;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.ui.pcenter.loginbind.api.LoginApi;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 第三方接口访问类
 * Created by lwd on 2018/6/21.
 */

public class ThirdApi {
    //新浪微博获取用户信息url
    private String sinaUserUrl = "https://api.weibo.com/2/users/show.json?access_token=%s&uid=%s";
    private Context mContext;
    private final LoginApi mLoginApi;
    private Tencent mTencent;
    private final ToastUtil toastUtil;

    public ThirdApi(Context context) {
        this.mContext = context;
        mLoginApi = new LoginApi(context);
        toastUtil = new ToastUtil(context);
    }


    /**
     * 新浪微博授权成功后通知后台登陆
     *
     * @param token
     * @param uid
     */
    public void sinaWbServerLogin(String token, final String uid) {
        String mSinaUserUrl = String.format(this.sinaUserUrl, token, uid);
        Log.e("lwd", "sinaWbServerLogin  mSinaUserUrl:" + mSinaUserUrl);

        OkHttpUtil.getThirdInfo(mContext, mSinaUserUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                String jsonStr = response.body();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonStr);

                    if (!EmptyUtil.isEmpty(jsonStr)) {
                        String province = jsonObject.getString("province");
                        String nickName = jsonObject.getString("name");
                        String platform = "SINA";
                        String regTerminal = "ANDROID";
                        String appId = ThirdPart.SINAWB_APPKEY;
                        String openId = uid;
                        String headimgUrl = jsonObject.getString("avatar_hd");
                        String gender = jsonObject.getString("gender");
                        if (!TextUtils.isEmpty(gender)) {
                            if (gender.equals("m")) {
                                gender = "1";
                            } else if (gender.equals("w")) {
                                gender = "2";
                            } else {
                                gender = "3";
                            }
                        }
                        String city = jsonObject.getString("city");
                        String country = "";
                        HashMap params = new HashMap();
                        params.put("nickName", nickName);
                        params.put("platform", platform);
                        params.put("appId", appId);
                        params.put("regTerminal", regTerminal);
                        params.put("openId", openId);
                        params.put("headimgUrl", headimgUrl);
                        params.put("gender", gender);
                        params.put("province", province);
                        params.put("city", city);
                        params.put("country", country);
                        params.put("appVersion", Util.getVersionName(mContext));
                        params.put("baseMethod", Method.LOGINTHIRD);
                        params.put("baseUrl", Config.baseUrl3);
                        mLoginApi.login(params);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String parseNetworkResponse(String jsonResult) {
                return jsonResult;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });


//        OkHttpUtils.get(mSinaUserUrl).execute(new AbsCallback<String>() {
//            @Override
//            public String parseNetworkResponse(Response response) throws Exception {
//                return null;
//            }
//
//            @Override
//            public void onSuccess(String s, Call call, Response response) {
//                try {
//                    if (response.code() == 200) {
//                        String wbJson = response.body().string();
//                        JSONObject jsonObject = new JSONObject(wbJson);
//                        if (jsonObject != null) {
//                            String province = jsonObject.getString("province");
//                            String nickName = jsonObject.getString("name");
//                            String platform = "SINA";
//                            String regTerminal = "ANDROID";
//                            String appId = ThirdPart.SINAWB_APPKEY;
//                            String openId = uid;
//                            String headimgUrl = jsonObject.getString("avatar_hd");
//                            String gender = jsonObject.getString("gender");
//                            if (!TextUtils.isEmpty(gender)) {
//                                if (gender.equals("m")) {
//                                    gender = "1";
//                                } else if (gender.equals("w")) {
//                                    gender = "2";
//                                } else {
//                                    gender = "3";
//                                }
//                            }
//                            String city = jsonObject.getString("city");
//                            String country = "";
//                            HashMap params = new HashMap();
//                            params.put("nickName", nickName);
//                            params.put("platform", platform);
//                            params.put("appId", appId);
//                            params.put("regTerminal", regTerminal);
//                            params.put("openId", openId);
//                            params.put("headimgUrl", headimgUrl);
//                            params.put("gender", gender);
//                            params.put("province", province);
//                            params.put("city", city);
//                            params.put("country", country);
//                            params.put("appVersion", Util.getVersionName(mContext));
//                            params.put("method", Method.LOGINTHIRD);
//                            params.put("baseUrl",Config.baseUrl3);
//                            mLoginApi.login(params);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Call call, Response response, Exception e) {
//                super.onError(call, response, e);
//                toastUtil.centerToast(mContext.getResources().getString(R.string.sina_login_failed));
//            }
//        });
    }

    /**
     * qq登陆获取用户信息
     *
     * @param tencent
     */
    public void qqServerLogin(Tencent tencent) {
        this.mTencent = tencent;
        UserInfo info = new UserInfo(mContext, tencent.getQQToken());
        info.getUserInfo(mQQUserListener);
    }

    /**
     * 返回用户信息样例
     *
     * {"is_yellow_year_vip":"0","ret":0,
     * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/40",
     * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
     * "nickname":"攀爬←蜗牛","yellow_vip_level":"0","is_lost":0,"msg":"",
     * "city":"黄冈","
     * figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/50",
     * "vip":"0","level":"0",
     * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
     * "province":"湖北",
     * "is_yellow_vip":"0","gender":"男",
     * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/30"}
     */
    /**
     * QQ获取用户信息监听
     */
    private IUiListener mQQUserListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            try {
                JSONObject jo = (JSONObject) o;
                int ret = jo.getInt("ret");
                System.out.println("json=" + String.valueOf(jo));
                String nickName = jo.getString("nickname");
                String gender = jo.getString("gender");
                if (gender.equals("男")) {
                    gender = "1";
                } else if (gender.equals("女")) {
                    gender = "2";
                } else {
                    gender = "3";
                }
                String platform = "QQ";
                String regTerminal = "ANDROID";
                String openId = mTencent == null ? "" : mTencent.getOpenId();
                String headimgUrl = jo.getString("figureurl_2");
                String province = jo.getString("province");
                String city = jo.getString("city");
                String country = "";
                HashMap params = new HashMap();
                params.put("nickName", nickName);
                params.put("platform", platform);
                params.put("appId", ThirdPart.QQ_APPID);
                params.put("regTerminal", regTerminal);
                params.put("openId", openId);
                params.put("headimgUrl", headimgUrl);
                params.put("gender", gender);
                params.put("province", province);
                params.put("city", city);
                params.put("country", country);
                params.put("baseMethod", Method.LOGINTHIRD);
                params.put("appVersion", Util.getVersionName(mContext));
                params.put("baseUrl", Config.baseUrl);
                mLoginApi.login(params);
            } catch (Exception e) {
                e.printStackTrace();
                toastUtil.centerToast(mContext.getResources().getString(R.string.qq_login_failed));
            }
        }

        @Override
        public void onError(UiError uiError) {
            toastUtil.centerToast(mContext.getResources().getString(R.string.qq_login_failed) + uiError.errorMessage);
        }

        @Override
        public void onCancel() {
            toastUtil.centerToast(mContext.getResources().getString(R.string.qq_login_cancel));
        }
    };

}

