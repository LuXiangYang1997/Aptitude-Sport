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






}
