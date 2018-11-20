package com.huasport.smartsport.constant;

/**
 * Created by 陆向阳 on 2018/6/21.
 */
//常量
public class StatusVariable {

    public static final int REQUESTSUCCESS = 200;//请求成功

    public static final int MATCHAPPLY = 0;//大赛报名
    public static final int MATCHSCORE = 1;//比赛成绩
    public static final int DISCOVER = 2;//发现
    public static final int PERSONALCENTE = 3;//个人中心

    public static final int INTENTCODE = 0;//跳转携带的Code

    public static final int MATCHAPPLYCODE = 0;//首页Code 用于返回首页
    public static final int MATCHGRADECODE = 1;//比赛成绩Code 用于返回到比赛成绩页面
    public static final int DISCOVERCODE = 2;//发现Code 用于返回到发现页面
    public static final int PCENTERCODE = 3;//个人中心Code 用于返回到个人中心页面
    public static final int NORMALCODE = 4;//普通Code 用于不做任何操作直接返回
    public static final int RELEASE = 1000;//普通Code 用于不做任何操作直接返回

    public static final String PAYSUCCESS = "paysuccess";//支付成功
    public static final String PAYFAILED = "payfailed";//支付失败

    public static final int SMSLOGIN = 0;//短信
    public static final int THIRDLOGIN = 1;//第三方

    public static final String FINISHLOGIN = "finish_login_page";

    public static final int CAMERACODE = 0;//拍照Code
    public static final int PHOTOCODE = 1;//相册Code

    public static final String WAIT_AUTH = "wait_auth";//未认证
    public static final String WAIT_AUDIT = "wait_audit";//申请中
    public static final String PASS = "pass";//已认证
    public static final String REJECT = "reject";//认证失败

    public static final String WEBURL = "webUrl";//intent跳转携带参数的key
    public static final String TITLE = "title";//intent跳转携带参数的key

    public static final int REFRESH = 0;//刷新
    public static final int LOADMORE = 1;//加载

    public static final int NORMALLIST = 0;//普通列表
    public static final int SEARCHLIST = 1;//搜索列表

    public static final int MATCHFIRST = 1;
    public static final int MATCHSECOND = 2;
    public static final int MATCHTHIRD = 3;

    public static final int DYNAMIC = 0;//动态
    public static final int ARTICLE = 1;//文章
    public static final int USER = 2;//用户

    public static final int SHARE = 0;//分享
    public static final int COMMENT = 1;//评论
    public static final int FAVOUR = 3;//赞

    public static final String YMD = "yyyy-MM-dd HH:mm";//年月日时分秒
    public static final String HM = "HH:mm";//时分

    public static final String SPLENDIDSTATIC = "1";//是精品

    public static final String NOAUTH = "0";//未认证
    public static final String YESAUTH = "1";//已认证

    public static final String SHEAD = "<html><head><meta name=\"viewport\" content=\"width=device-width, " +
            "initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />" +
            "<style>img{display:block;max-width:100% !important;height:auto !important;}</style>"
            + "<style>body{max-width:100% !important;}</style>" + "</head><body>";

    public static final String UNFOLLOW = "unfollow";//未关注

    public static final String DYNAMICSTR = "dynamic";
    public static final String ARTICLESTR = "article";
    public static final String USERSTR = "user";

    public static final int NORMAL = 0;//普通事件
    public static final int DELIMG = 1;//删除图片

    public static final String MATCH_OVER = "1";//比赛已结束
    public static final String APPLY_ABORT = "2";//报名截止
    public static final String PEOPLENUM_FULL = "3";//人数已满
    public static final String PAUSE_APPLY = "4";//暂停报名
    public static final String APPLY = "5";//报名

    public static final int PROVINCECODE = 0;//省
    public static final int CITYCODE = 1;//市
    public static final int DISTRICTCODE = 2;//区

    public static final int NOLOGIN = 204;//未登录
    public static final int SUCCESS = 200;//请求成功
    public static final int NOBIND = 205;//未绑定手机号

    public static final int MSG_TYPE_SEX = 1;//性别
    public static final int MSG_TYPE_BTN = 2;//选择框
    public static final int MSG_TYPE_PHONENUM = 3;//手机号
    public static final int MSG_TYPE_NORMAL = 4;//Edittext输入框
    public static final int MSG_TYPE_HEAD = 5;//头像

    public static final String BOY = "m";//男
    public static final String GIRL = "f";//女

    public static final int BIRDTHCLICK = 1;//生日
    public static final int CERTIFICATE = 2;//证件
    public static final int CODEGET = 3;

    public static final String IDCARD = "1";//身份证
    public static final String PASSCARD = "2";//护照
    public static final String CERTIFICATECARD = "4";//军官证

    public static final String GENDERBOY = "1";//男
    public static final String GENDERGIRL = "2";//女

    public static final int FRONT_CODE = 1;//intent回传值正面身份证
    public static final int CONTRARY_CODE = 2;//反面身份证

    public static final String WECHAT = "weChat";//微信支付
    public static final String ALIPAY = "aliPay";//支付宝支付

    public static final String WAIT_PAY = "WAIT_PAY";//待支付
    public static final String WAIT_COMPLETE = "WAIT_COMPLETE";//待完善
    public static final String ORDERSUCCESS = "SUCCESS";//成功
    public static final String SHIPPEDSTRS = "SHIPPED";//已发货
    public static final String COMPLETED = "COMPLETED";//已完成

    public static final String WAITPAY = "wait_pay";
    public static final String SUCCESSAPPLY = "success";
    public static final String SHIPPED = "shipped";
    public static final String COMOLETED = "completed";

    public static final String APPLYSUCCESS = "success";//提交订单成功

    public static final int ADDITIONMEMBER = 1;//添加队员
    public static final int MODIFYMEMMBER = 2;//修改成员信息

    //个人中心区分四种点击事件
    public static final int PERSONALCENTER_WAITPAY = 0;//待支付
    public static final int PERSONALCENTER_PERFECT = 1;//待完善
    public static final int PERSONALCENTER_ALREADYSUCCESS = 2;//已成功
    public static final int PERSONALCENTER_ALLMATCH = 3;//全部赛事


}
