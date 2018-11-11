package com.huasport.smartsport.api;

/**
 * Created by 陆向阳 on 2018/5/19.
 */

public class Method {

    //首页列表
    public static final String HOMEPAGELIST = "/api/project/types";
    //首页Banner
    public static final String HOMEPAGEBANNER = "/api/project/banner";
    //赛事列表
    public static final String MATCHLIST = "/api/project/pages/match";
    //赛事详情
    public static final String MATCHINTRODUCE = "/api/project/match/detail";
    //获取分组
    public static final String MATCHGROUP = "/api/project/match/sites";
    //搜索
    public static final String SEARCH = "/api/project/match/search";
    //赛事分组
    public static final String MATCHEVENTS = "/api/project/events";
    //我的报名
    public static final String MYAPPLY = "/api/order/list";
    //报名信息
    public static final String SIGININFO = "/api/order/detail";
    //取消订单
    public static final String CANCELORDER = "/api/cancel/order";
    //获取验证码
    public static final String GETVERTIFYCODE = "/api/verify/send";
    //短信登陆
    public static final String LOGINSMS = "/api/user/login";
    //第三方登陆
    public static final String LOGINTHIRD = "/api/third/login";
    //创建订单
    public static final String CREATEORDER = "/api/create/order";
    //获取填写报名信息
    public static final String GETFORMMSG = "/api/project/player/property";
    //完善信息
    public static final String COMPLETEMSG = "/api/complete/order";
    //保存临时信息
    public static final String SAVEMSG = "/api/save/player";
    //支付宝支付
    public static final String ALIPAY = "/api/alipay/unified";
    //微信支付
    public static final String WECHAT = "/api/weichat/unified";
    //上传文件
    public static final String UPLOAD_FILE = "/api/file/upload";
    //获取版本号
    public static final String VERSION_CODE = "/api/version/info";
    //获取订单信息
    public static final String ORDERMSG = "/api/order/pay";
    //获取省市区列表
    public static final String ADDRESS = "/api/project/match/area";
    //经纬度列表
    public static final String LOCATION = "/api/project/pages/match/sites";
    //域名
    public static final String DOMAINNAME = "/api/check/domian";
    //手机号绑定
    public static final String BINDNUMBER = "/api/phone/bind";
    //获取用户信息
    public static final String GETUSERINFO = "/api/user/get";
    //更新用户信息
    public static final String UPDATAUSERINFO = "/api/user/update";
    //消息列表
    public static final String GETMESSAGELIST = "/api/user/getApplyInfo";
    //获取消息未读个数
    public static final String GETNOREADMESSAGE = "/api/user/unreadMessage";
    //添加队员
    public static final String ADDMEMBER = "/api/apply/add/term";
    //完善团队报名信息
    public static final String PERFEVTMESSAGE = "/api/copmplete/group";
    //删除成员
    public static final String DELUSER = "/api/apply/del/team";
    //保存领队信息
    public static final String SAVELEADERMSG = "/api/apply/save/leader";
    //获取奖章列表
    public static final String MEDALLIST = "/api/cert/getScoreCertList";
    //获取个人中心订单列表
    public static final String GETPERSONALORDERLIST = "/api/shop/user/order/list";
    //上传Base64Bitmap
    public static final String UPLOADBASE64 = "file/upload/base64";
    //奖章详情
    public static final String MEDALDETAIL = "/api/shop/goods/G20180806000001";
    //地址列表
    public static final String ADDRESSLIST = "/api/shop/user/address/list";
    //添加修改地址
    public static final String MODIFYADDRESS = "/api/shop/user/address/edit";
    //我的订单地址
    public static final String MYORDERADDRESS = "/api/shop/user/address";
    //创建订单
    public static final String SHOPCREATEORDER = "/api/shop/user/order/create";
    //订单详情
    public static final String MEDALORDERDETAIL = "/api/shop/user/order";
    //奖章微信支付
    public static final String MEDALEWACHAT = "/api/shop/wechat/pay";
    //奖章支付宝支付
    public static final String MEDALEALIPAY = "/api/shop/alipay/unified";
    //修改证书状态
    public static final String UPDATASTATUS = "/api/cert/updeteCertStatus";
    //首页证书接口
    public static final String HOMEPAGECERT = "/api/cert/getScoreCert";
    //我的成绩列表
    public static final String MYGRADELIST = "/api/score/myRankList";
    //我的报名卡
    public static final String MYAPPLYCARD = "/api/user/save/card";
    //记录日志
    public static final String RECORDLOG = "/api/login/log";
    //我的成绩详情
    public static final String MYGTRADEDETAIL = "/api/score/getRegisterScores";
    //我的成绩详情
    public static final String MYGRADETAIL = "/api/score/getMyScoreInfo";
    //排名
    public static final String RANKINGS = "/api/score/rankList";
    //首页比赛项目列表
    public static final String HOMEPAGEMATCHGRADE = "/api/score/types";
    //首页比赛列表
    public static final String HOMEPAGEMATCHGRADELIST = "/api/score/matchs";
    //小赛事列表
    public static final String HOMEGRADESMALLLIST = "/api/score/match/all/sites";
    //比赛成绩排名列表
    public static final String MATCHSCORELIST = "/api/score/rankList";
    //比赛成绩排名头部
    public static final String MATCHSCOREHEADER = "/api/score/competitionList";
    //用户成绩
    public static final String MATCHUSERSCORE = "/api/score/myRank";
    //比赛成绩搜索
    public static final String MATCHSCORESEARCH = "/api/score/match/search";
    //订单详情
    public static final String PCORDERDETAIL = "/api/shop/user/order/";


    //3.0.1
    //获取个人中心关注，认证，粉丝，发布信息
    public static final String GETUSERCENTERINFO = "/api/usercenter/info";
    public static final String GETUSERCENTERINFO2 = "/api/user/info";
    //文件上传
    public static final String FILEUPLOAD = "/api/file/upload";
    //用户实名认证
    public static final String USERREALAPPROVE = "/api/usercenter/personal/authentication";
    //个人提交认证信息
    public static final String PERSONALSUBMITMSG = "/api/certification/personal/submit";
    //保存个人认证信息
    public static final String SAVEPERSONALMSG = "/api/certification/personal/next";
    //企业实名认证
    public static final String FIRMAPPROVE = "/api/certification/enterprise/submit";
    //关注列表接口
    public static final String ATTENTIONLIST = "/api/usercenter/follow/list";
    //粉丝列表接口
    public static final String FANSLIST = "/api/usercenter/fans/list";
    //关注-取关
    public static final String ATTENTION = "/api/usercenter/follow";
    //获取用户认证信息
    public static final String GETCERTIFICATIONINFO = "/api/certification/info";
    //通讯录列表接口
    public static final String ADDRESSBOOKLIST = "/api/addressbook/all";
    //通讯录上传接口
    public static final String ADDRESSUPLOAD = "/api/addressbook/upload";
    //动态文章列表接口
    public static final String DYNAMICARTICLELIST = "/api/find/info/list";
    //发布动态
    public static final String RELEASEDYNAMIC = "/api/find/info/save";
    //获取置顶文章接口
    public static final String RECOMMANDLIST = "/api/find/stick/list";
    //获取保存动态文章
    public static final String GETDYNAMICARTICLE = "/api/find/draft/get";
    //保存文章动态
    public static final String SAVEARTICLE = "/api/find/draft/save";
    //评论
    public static final String COMMAND = "/api/comment/save";
    //点赞
    public static final String PRAISE = "/api/like/execute";
    //全文检索
    public static final String ALLSEARCH = "/api/find/fulltext";
    //动态文章列表
    public static final String DYNAMICARTICLE = "/api/usercenter/socialInfo/list";
    //动态文章详情页
    public static final String DYNAMICARTICLEDETAIL = "/api/find/info/detail";
    //评论列表
    public static final String COMMENTLIST = "/api/comment/list";
    //点赞列表
    public static final String FAVOURLIST = "/api/like/list";
    //回复评论评论
    public static final String COMMENTREVIEW = "/api/comment/detail/save";
    //回复评论列表
    public static final String REPLYINFOLIST = "/api/comment/replyList";
    //删除动态文章
    public static final String DELETEDYANDART = "/api/find/release/delete";
    //一键关注
    public static final String ONEKEYATTENTION = "/api/addressbook/follow";
    //发送短信邀请
    public static final String SENDMSGINVITE = "/api/send/invite";
    //分享后接口
    public static final String SHAREHTTP="/api/share/add";
}







