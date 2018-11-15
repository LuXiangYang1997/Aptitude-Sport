package com.huasport.smartsport.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.huasport.smartsport.R;
import com.huasport.smartsport.bean.PayResult;
import com.huasport.smartsport.util.ImageUtil;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.wxapi.callback.AlipayCallBack;
import com.huasport.smartsport.wxapi.callback.ISinaWbCallBack;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 第三方集成类
 * Created by lwd on 2018/6/20.
 */

public class ThirdPart {
    //微信appid
    public static final String WX_APPID = "wx7570f2e03d6d90f5";
    //微信appSecret
    public static final String WX_APPSECRET = "24bdc86540878d88dd9c20af87bb4744";
    //微信
    public static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
    public static final int RETURN_MSG_TYPE_SHARE = 2; //分享
    //微信包名
    public static final String PACKAGE_WX = "com.tencent.mm";
    //微信分享至好友
    public static final int WECHAT_FRIEND = 0;
    //微信分享至朋友圈
    public static final int WECHAT_MOMENT = 1;
    //微信分享url（官网地址）
    public static final String WX_SHAREURL = "http://huasports.com";

    //新浪微博包名
    public static final String PACKAGE_SINAWB = "com.sina.weibo";
    //新浪微博appkey
    public static final String SINAWB_APPKEY = "3830962555";
    //新浪微博appSecret
    public static final String SINAWB_APPSECRET = "3ec510aa894194f7bb64e48e2a2e5975";
    //新浪微博默认回调页
    public static final String REDIRECT_URL = "http://www.weibo.com";
    //新浪微博授权的功能
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";


    //支付宝appid
    public static final String ALIPAY_APPID = "2018061360368311";
    //支付宝私钥
    public static final String ALIPAY_RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOgtD1MQllxtxO4+\n" +
            "4YGdgAYuqhGwYQm/cwvwQgf7PbZApJ3F3xXQH4xpibOxy11MTMAKAVFMWEDhPA8z\n" +
            "23u2MsIpGDzIKURa7QNxGVietuT24X/6jP8OPpo2dvyWapQyVAq/4iiawIVurd5S\n" +
            "7ivTgESErv7AFmD2EjkWWPL9tPWPAgMBAAECgYA2hY5ZtUV6AxUB1aZjJ7dOvFqX\n" +
            "uNGw1lKP9SWsdiVxzRBbNIJEvxEJiicaGaBmEmrblf3lN4ZqBfpyn3zlOk4Es7xo\n" +
            "1QXdqkZCCLf2rOjjxzunlC/qMjHS63rGHYiQlG+Tbg4YrpOtaa0ElhTGDD9t0Fkv\n" +
            "8If3eUkAi/RilvJOcQJBAP+REjFXc+OsA7pASb0hibn9m3+HX7Q6r6jRtBKiEYK8\n" +
            "wTpmBV1xeSkk+/e9NRqKx8YyAwbNWfZS+YO25nhrfGMCQQDokdYHyY1k/aiZ+JLG\n" +
            "k6X036dnCT4KNEUWjM3y9Gw/EyXDmtUDrphGjxFYm2MYA+y6pUy8pZzzsi0524cP\n" +
            "ItvlAkEAnXAMShM61FWikjmKc5XWl5pxEbYeshO5JQHSsevfZ6/KRaSVx4PhBQTA\n" +
            "qFSKA0jdWu5ySN12fzWMeqTRA/wLSwJBAJg4c90u96PlPshgYTkmBJZtGLWg4AFv\n" +
            "ytSCnn3pqzvxfWM04T2CtlpgY9saAVcDoptbfAlxYHRE58MZw6GNkF0CQQDyWBIk\n" +
            "qfm16Ir/L4K0JVMXKMEgh+I37ZsV5ZuLy65kyCx6+C8/pdaYfXEfeHoO6U51LEyd\n" +
            "Lp7h4hcBblIDe0wC";


    //QQ APPKEY
    public static final String QQ_APPKEY = "be005fd5a722eff99d4911260a16f720";
    //QQ APPID
    public static final String QQ_APPID = "101496667";//101496667
    public static final String QQ_PACKAGENAME = "com.tencent.mqq";
    //QQ分享图片地址
    public static final String QQ_SHARE_IAMGE = "http://static.appapi.zntyydh.com:81/share@2x.png";

    private Context mContext;
    private IWXAPI mWXApi;
    private SsoHandler mSsoHandler;
    private WbShareHandler mShareHandler;
    private Tencent mTencent;
    private Bitmap bitmap;
    private final ToastUtil toastUtil;

    public ThirdPart(Context context) {
        this.mContext = context;
        toastUtil = new ToastUtil(mContext);
    }

    //微信接入初始化
    public void initWX() {
        mWXApi = WXAPIFactory.createWXAPI(mContext, ThirdPart.WX_APPID, true);
        mWXApi.registerApp(ThirdPart.WX_APPID);
    }

    //微信登录
    public void wxLogin() {
        if (mWXApi == null) {
            LogUtil.e( "wxLogin init");
            initWX();
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        //像微信发送请求
        mWXApi.sendReq(req);
    }

    //判断用户是否安装应用
    public boolean isAppAvilible(String mPackage) {
        PackageManager packageManager = mContext.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(mPackage)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 微信支付
     *
     * @param appId
     * @param partnerId 商户id
     * @param prepayId  预支付交易会话id
     * @param nonceStr  随机字符串
     * @param timeStamp 时间戳
     * @param sign      随机生成签名
     */
    public void wxPay(final String appId, final String partnerId, final String prepayId, final String nonceStr,
                      final String timeStamp, final String sign) {
        if (mWXApi == null) {
            LogUtil.e("wxPay init");
            initWX();
        }
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = appId;
                //商户id
                request.partnerId = partnerId;
                //预支付交易会话id
                request.prepayId = prepayId;
                request.packageValue = "Sign=WXPay";
                //随机字符串
                request.nonceStr = nonceStr;
                //时间戳
                request.timeStamp = timeStamp;
                //随机生成签名
                request.sign = sign;
                mWXApi.sendReq(request);//发送调起微信的请求
                e.onNext("success");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.e("wxPay s:" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                       toastUtil.centerToast(mContext.getResources().getString(R.string.wx_pay_failed));
                    }
                });
    }

    /**
     * 微信分享
     *
     * @param url         地址
     * @param title       标题
     * @param description 描述
     * @param imgUrl
     * @param judge       类型选择 好友-WECHAT_FRIEND 朋友圈-WECHAT_MOMENT
     */
    public void wxUrlShare(String url, String title, String description, int imgUrl, int judge) {
        if (!isAppAvilible(PACKAGE_WX)) {
            toastUtil.centerToast(mContext.getResources().getString(R.string.wx_no_install));
            return;
        }
        if (mWXApi == null) {
            LogUtil.e( "wxPay init");
            initWX();
        }
        Bitmap bitmap = getBitMBitmap(imgUrl);
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = url;
        WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
        wxMediaMessage.title = title;
        wxMediaMessage.description = description;
        LogUtil.e("WXShare description:" + description);
        Bitmap thunmpBmp = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
        bitmap.recycle();
        wxMediaMessage.thumbData = ImageUtil.bmpToByteArray(thunmpBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;
        req.scene = judge;
        mWXApi.sendReq(req);
    }

    /**
     * 图片转bitmap
     */
    private Bitmap getBitMBitmap(int id) {

        return BitmapFactory.decodeResource(mContext.getResources(), id);
    }

    /**
     * 微信分享
     *
     * @param url         地址
     * @param title       标题
     * @param description 描述
     * @param bitmap
     * @param judge       类型选择 好友-WECHAT_FRIEND 朋友圈-WECHAT_MOMENT
     */
    public void wxShareurl(String url, String title, String description, Bitmap bitmap, int judge) {
        if (!isAppAvilible(PACKAGE_WX)) {
            toastUtil.centerToast(mContext.getResources().getString(R.string.wx_no_install));
            return;
        }
        if (mWXApi == null) {
            LogUtil.e("wxPay init");
            initWX();
        }
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = url;
        WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
        wxMediaMessage.title = title;
        wxMediaMessage.description = description;
        LogUtil.e("WXShare description:" + description);
        Bitmap thunmpBmp = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
        wxMediaMessage.thumbData = ImageUtil.bmpToByteArray(thunmpBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;
        req.scene = judge;
        mWXApi.sendReq(req);
    }


    /**
     * 初始化微博
     */
    public void initSinaWb() {
        WbSdk.install(mContext, new AuthInfo(mContext, SINAWB_APPKEY, REDIRECT_URL,
                SCOPE));
        //初始化SsoHandler对象
        mSsoHandler = new SsoHandler((Activity) mContext);
        //初始化ShareHandler对象
        mShareHandler = new WbShareHandler((Activity) mContext);
        mShareHandler.registerApp();
    }

    /**
     * 设置新浪微博回调
     *
     * @param intent
     * @param callback
     */
    public void setSinaWbCallBack(Intent intent, WbShareCallback callback) {
        if (mShareHandler != null) {
            mShareHandler.doResultIntent(intent, callback);
        }
    }

    /**
     * 获取微博handler
     *
     * @return
     */
    public SsoHandler getSinaWbHandler() {
        return mSsoHandler;
    }

    /**
     * 新浪微博登陆
     */
    public void sinaWbLogin(final ISinaWbCallBack iSinaWbCallBack) {
        if (mSsoHandler == null) {
            LogUtil.e("sinaWbLogin init");
            initSinaWb();
        }
        mSsoHandler.authorizeClientSso(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                LogUtil.e("loginSinaWb onSuccess");
                iSinaWbCallBack.onSuccess(oauth2AccessToken);
            }

            @Override
            public void cancel() {
                LogUtil.e("loginSinaWb cancel");
                iSinaWbCallBack.cancel();
            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                LogUtil.e("loginSinaWb onFailure");
                iSinaWbCallBack.onFailure(wbConnectErrorMessage);
            }
        });
    }


    /**
     * 微博分享
     *
     * @param title
     * @param bitmapId
     */
    public void sinaWBShare(String title, int bitmapId) {
        if (!isAppAvilible(PACKAGE_SINAWB)) {
        toastUtil.centerToast(mContext.getResources().getString(R.string.sinawb_no_install));
        }
        if (mShareHandler == null) {
            Log.e("lwd", "sinaWbLogin init");
            initSinaWb();
        }
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
        TextObject textObject = new TextObject();
        textObject.text = title;
        weiboMessage.textObject = textObject;
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), bitmapId);
        imageObject.setImageObject(bitmap);
        weiboMessage.imageObject = imageObject;
        mShareHandler.shareMessage(weiboMessage, false);
    }


    /**
     * 支付宝支付
     */
    public void aliPay(final String infoSign, final AlipayCallBack alipayCallBack) {
        Observable.create(new ObservableOnSubscribe<HashMap<String, String>>() {
            @Override
            public void subscribe(ObservableEmitter<HashMap<String, String>> e) throws Exception {
                //秘钥验证的类型 true:RSA2 false:RSA
//                boolean rsa = false;
                //构造支付订单参数列表
//                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(ALIPAY_APPID, rsa, content, timestamp, version);
                //构造支付订单参数信息
//                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                //对支付参数信息进行签名
//                String sign = OrderInfoUtil2_0.getSign(params, ALIPAY_RSA_PRIVATE, rsa);
                //订单信息
//                final String orderInfo = orderParam + "&" + sign;

                PayTask alipay = new PayTask((Activity) mContext);
                //获取支付结果
                Map<String, String> result = alipay.payV2(infoSign, true);
                e.onNext((HashMap<String, String>) result);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HashMap<String, String>>() {
                    @Override
                    public void accept(HashMap<String, String> stringStringHashMap) throws Exception {
                        PayResult payResult = new PayResult(stringStringHashMap);
                        //同步获取结果
                        String resultInfo = payResult.getResult();
                        Log.i("lwd", "aliPay result:" + resultInfo);
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                        toastUtil.centerToast(mContext.getResources().getString(R.string.pay_success));
                            alipayCallBack.paySuccess(resultStatus);

                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            toastUtil.centerToast(mContext.getResources().getString(R.string.pay_cancel));
                            alipayCallBack.payFailed(resultStatus);
                        } else {
                            toastUtil.centerToast(mContext.getResources().getString(R.string.pay_failed));
                            alipayCallBack.payFailed(resultStatus);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        toastUtil.centerToast( mContext.getResources().getString(R.string.pay_failed));
                    }
                });
    }


    /**
     * 初始化QQ對象
     */
    private void initQQ() {
        mTencent = Tencent.createInstance(QQ_APPID, mContext);
    }

    /**
     * 获取QQ对象
     *
     * @return
     */
    public Tencent getTencent() {
        return mTencent;
    }

    /**
     * QQ登陆
     *
     * @param listener
     */
    public void qqLogin(IUiListener listener) {
        initQQ();
//        if (!isAppAvilible(QQ_PACKAGENAME)){
//            ToastUtils.toast(mContext,mContext.getResources().getString(R.string.qq_no_install));
//        }
        if (!mTencent.isSessionValid()) {
            Log.e("lwd", "qq登陆");
            mTencent.login((Activity) mContext, "all", listener);
        } else {
            Log.e("lwd", "qq注销");
            mTencent.logout(mContext);
            mTencent.login((Activity) mContext, "all", listener);
        }
    }

    /**
     * qq分享
     *
     * @param title
     * @param summary
     * @param url
     * @param imageurl
     * @param listener
     * @param isQzone  true:false   空间：个人
     */
    public void qqShare(String title, String summary, String url, String imageurl, IUiListener listener, boolean isQzone) {
        initQQ();
//        if (!isAppAvilible(QQ_PACKAGENAME)){
//            ToastUtils.toast(mContext,mContext.getResources().getString(R.string.qq_no_install));
//        }

        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);

        if (isQzone) {
            ArrayList<String> imageUrls = new ArrayList<String>();
            imageUrls.add(imageurl);
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            mTencent.shareToQzone((Activity) mContext, params, listener);

        } else {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageurl);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            mTencent.shareToQQ((Activity) mContext, params, listener);
        }
    }

    /**
     * 微博分享
     *
     * @param title
     * @param bitmap
     */
    public void sinaWBShareUrl(String title, Bitmap bitmap) {
        if (!isAppAvilible(PACKAGE_SINAWB)) {
          toastUtil.centerToast( mContext.getResources().getString(R.string.sinawb_no_install));
        }
        if (mShareHandler == null) {
            Log.e("lwd", "sinaWbLogin init");
            initSinaWb();
        }
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
        TextObject textObject = new TextObject();
        textObject.text = title;
        weiboMessage.textObject = textObject;
        ImageObject imageObject = new ImageObject();
//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), bitmapId);
        imageObject.setImageObject(bitmap);
        bitmap.recycle();
        weiboMessage.imageObject = imageObject;
        mShareHandler.shareMessage(weiboMessage, false);
    }

    /**
     * @param bitmap 图片的Url
     * @param judge  分享平台
     */
    public void wechatimgShare(Bitmap bitmap, int judge) {

        if (!isAppAvilible(PACKAGE_WX)) {
           toastUtil.centerToast(mContext.getResources().getString(R.string.wx_no_install));
            return;
        }
        if (mWXApi == null) {
            Log.e("lwd", "wxPay init");
            initWX();
        }
//        Bitmap getbitmap = Util.getbitmap(Imageurl);
        WXImageObject imageObject = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = judge;
        mWXApi.sendReq(req);
    }

    /**
     * 微博分享
     *
     * @param bitmap 图片的Url
     */
    public void sinaWBShareImg(Bitmap bitmap) {
        if (!isAppAvilible(PACKAGE_SINAWB)) {
            toastUtil.centerToast( mContext.getResources().getString(R.string.sinawb_no_install));
        }
        if (mShareHandler == null) {
            Log.e("lwd", "sinaWbLogin init");
            initSinaWb();
        }
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
        TextObject textObject = new TextObject();
        weiboMessage.textObject = textObject;
        ImageObject imageObject = new ImageObject();
//        Bitmap bitmap = Util.getbitmap(imgUrl);
        imageObject.setImageObject(bitmap);
        bitmap.recycle();
        weiboMessage.imageObject = imageObject;
        mShareHandler.shareMessage(weiboMessage, false);
    }

    /**
     * 只分享图片
     *
     * @param imageurl url
     * @param listener 监听
     * @param isQzone  true：false 空间：朋友
     */
    public void qqShareImage(String imageurl, IUiListener listener, boolean isQzone) {

        initQQ();
//        if (!isAppAvilible(QQ_PACKAGENAME)){
//            ToastUtils.toast(mContext,mContext.getResources().getString(R.string.qq_no_install));
//        }
        Bundle params = new Bundle();

        if (isQzone) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageurl);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            mTencent.shareToQQ((Activity) mContext, params, listener);
        } else {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageurl);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            mTencent.shareToQQ((Activity) mContext, params, listener);
        }


    }

}
