package com.huasport.smartsport;


import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.api.DownloadApkManager;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.bean.HomePageCertBean;
import com.huasport.smartsport.bean.LocationBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.ActivityMainBinding;
import com.huasport.smartsport.ui.discover.view.DiscoverFragment;
import com.huasport.smartsport.ui.matchapply.view.MatchApplyFragment;
import com.huasport.smartsport.ui.matchgrade.view.MatchGradeFragment;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.medal.view.PersonalMedalDetailActivity;
import com.huasport.smartsport.ui.pcenter.view.PCenterFragment;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.ILocationCallBack;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.LocationUtils;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.wxapi.ThirdPart;
import com.lzy.okgo.model.Response;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ILocationCallBack,WbShareCallback {

    private ActivityMainBinding homeBinding;
    private int type = 0;
    private ViewPagerAdapter viewPagerAdapter;
    private long exitTime = 0;
    private boolean loginState;
    private LocationUtils locationUtils;
    private String token;
    private PopupWindow popupWindow;
    private View bgicon;
    private RotateAnimation rotateAnimation;
    private Bitmap bitmap;
    private PopupWindow sharePop;
    private ThirdPart mThirdPart;
    private ToastUtil toastUtil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
        initData();
        UpDataVersion();
        initPermission();
        initCert();
        getLocation();
    }


    /**
     * 初始化
     */
    private void init() {
        locationUtils = new LocationUtils(this,this);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MatchApplyFragment());
        fragmentList.add(new MatchGradeFragment());
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new PCenterFragment());
        viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager(), fragmentList);
        homeBinding.vpHome.setAdapter(viewPagerAdapter);
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if(!EmptyUtil.isEmpty(userBean)){
            token = userBean.getToken();
        }
        mThirdPart = new ThirdPart(this);
        toastUtil = new ToastUtil(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {


    }
    /**
     * 首页弹出领取奖章，分享
     */
    private void initCert() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.HOMEPAGECERT);
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(this, params, new RequestCallBack<HomePageCertBean>() {
            @Override
            public void onSuccess(Response<HomePageCertBean> response) {
                HomePageCertBean homePageCertBean = response.body();
                if (!EmptyUtil.isEmpty(homePageCertBean)){
                    int resultCode = homePageCertBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        popwindow(homePageCertBean.getResult().getShare());
                    }
                }
            }

            @Override
            public HomePageCertBean parseNetworkResponse(String jsonResult) {
                HomePageCertBean homePageCertBean = JSON.parseObject(jsonResult, HomePageCertBean.class);
                return homePageCertBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    private void popwindow(final HomePageCertBean.ResultBean.ShareBean sharebean) {

        View popView = LayoutInflater.from(MainActivity.this).inflate(R.layout.success_medal_layout, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(popView);

        Util.backgroundAlpha(MainActivity.this, 0.4f);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);// 点击空白处时，隐藏掉pop窗口

        popupWindow.showAtLocation(popView, 0, 0, 0);

        bgicon = popView.findViewById(R.id.icon_bg);
        //添加动画
        rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1500);//动画执行一次的时间
        rotateAnimation.setFillAfter(false);//回到原始状态
        //让旋转动画一直转，不停顿
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(0);
        bgicon.startAnimation(rotateAnimation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(MainActivity.this, 1.0f);
            }

        });
        final TextView getMedal = popView.findViewById(R.id.getMedal);
        LinearLayout close = popView.findViewById(R.id.close_pop);
        LinearLayout share = popView.findViewById(R.id.share_pop);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        bitmap = Util.decodeUriAsBitmapFromNet(sharebean.getPicUrl());

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                sharePop(sharebean);
                            }
                        });
                    }
                }).start();
            }
        });
        getMedal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, PersonalMedalDetailActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });


    }
    /**
     * 分享
     *
     * @param sharebean
     */
    private void sharePop(final HomePageCertBean.ResultBean.ShareBean sharebean) {

        View shareView = LayoutInflater.from(MainActivity.this).inflate(R.layout.share_layout, null, false);
        sharePop = new PopupWindow(shareView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sharePop.setContentView(shareView);
        sharePop.showAtLocation(shareView, Gravity.BOTTOM, 0, 0);
        sharePop.setOutsideTouchable(true);
        Util.backgroundAlpha(MainActivity.this, 0.5f);
        //微博分享
        shareView.findViewById(R.id.weibo_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        bitmap = Util.decodeUriAsBitmapFromNet(sharebean.getPicUrl());
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if (aBoolean) {
                            mThirdPart.sinaWBShareUrl(sharebean.getTitle() + "\n" + sharebean.getContent(), bitmap);
                        }
                    }
                }.execute();
            }
        });
        //微信好友分享
        shareView.findViewById(R.id.wechat_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        bitmap = Util.decodeUriAsBitmapFromNet(sharebean.getPicUrl());
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if (aBoolean) {
                            mThirdPart.wxShareurl(sharebean.getShareUrl(), sharebean.getTitle()
                                    , sharebean.getContent()
                                    , bitmap, ThirdPart.WECHAT_FRIEND);
                        }
                    }
                }.execute();
            }
        });
        //微信朋友圈分享
        shareView.findViewById(R.id.pengyouquan_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        bitmap = Util.decodeUriAsBitmapFromNet(sharebean.getPicUrl());
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if (aBoolean) {
                            mThirdPart.wxShareurl(sharebean.getShareUrl(), sharebean.getTitle()
                                    , sharebean.getContent()
                                    , bitmap, ThirdPart.WECHAT_MOMENT);
                        }
                    }
                }.execute();

            }
        });
        //qq朋友分享
        shareView.findViewById(R.id.qFriend_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThirdPart.qqShare(sharebean.getTitle()
                        , sharebean.getContent()
                        , sharebean.getShareUrl(), sharebean.getPicUrl(), mQQShareListener, false);
            }
        });

        //qq空间分享
        shareView.findViewById(R.id.qSpase_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThirdPart.qqShare(sharebean.getTitle()
                        , sharebean.getContent()
                        , sharebean.getShareUrl(), sharebean.getPicUrl(), mQZONEShareListener, true);
            }
        });
        shareView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePop.dismiss();
            }
        });
    }


    /**
     * 获取经纬度
     */
    private void getLocation() {

    }
    /**
     * 下载最新安装包，当处于4G网络下给提示，确定则下载安装包，下载完成后如果是强制安装则没有取消按钮
     * 反之有取消按钮
     */
    private void UpDataVersion() {

        DownloadApkManager downloadApkManager = new DownloadApkManager(this);
        downloadApkManager.startDownLoad();
    }
    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_match_apply:
                type = StatusVariable.MATCHAPPLY;
                break;
            case R.id.ll_match_score:
                type = StatusVariable.MATCHSCORE;
                viewPagerAdapter.getItem(1).onResume();
                break;
            case R.id.ll_discover:
                type = StatusVariable.DISCOVER;
                break;
            case R.id.ll_personalcenter:
                //判断是否登录，未登录则到登录界面，否则到个人中心
                if (!loginState){
                    MyApplication.getInstance().setClickState(true);
                    IntentUtil.startActivityForResult(this,LoginActivity.class);
                    return;
                }
                type = StatusVariable.PERSONALCENTE;
                break;

        }
        tabState(type);
    }

    /**
     * 底部tab状态
     *
     * @param type
     */
    public void tabState(int type) {

        homeBinding.imgMatchApply.setImageResource(R.mipmap.icon_match_apply_unselect);
        homeBinding.tvMatchApply.setTextColor(getResources().getColor(R.color.color_333333));
        homeBinding.imgMatchGrade.setImageResource(R.mipmap.icon_match_grade_unselect);
        homeBinding.tvMatchGrade.setTextColor(getResources().getColor(R.color.color_333333));
        homeBinding.imgDiscover.setImageResource(R.mipmap.icon_discover_unselect);
        homeBinding.tvDiscover.setTextColor(getResources().getColor(R.color.color_333333));
        homeBinding.imgPcenter.setImageResource(R.mipmap.icon_pcenter_unselect);
        homeBinding.tvPcenter.setTextColor(getResources().getColor(R.color.color_333333));

        switch (type) {
            case StatusVariable.MATCHAPPLY:
                homeBinding.imgMatchApply.setImageResource(R.mipmap.icon_match_apply_select);
                homeBinding.tvMatchApply.setTextColor(getResources().getColor(R.color.color_FF8F00));
                break;
            case StatusVariable.MATCHSCORE:
                homeBinding.imgMatchGrade.setImageResource(R.mipmap.icon_match_grade_select);
                homeBinding.tvMatchGrade.setTextColor(getResources().getColor(R.color.color_FF8F00));
                break;
            case StatusVariable.DISCOVER:
                homeBinding.imgDiscover.setImageResource(R.mipmap.icon_discover_select);
                homeBinding.tvDiscover.setTextColor(getResources().getColor(R.color.color_FF8F00));
                break;
            case StatusVariable.PERSONALCENTE:
                homeBinding.imgPcenter.setImageResource(R.mipmap.icon_pcenter_select);
                homeBinding.tvPcenter.setTextColor(getResources().getColor(R.color.color_FF8F00));
                break;
        }
        homeBinding.vpHome.setCurrentItem(type);
    }

    /**
     * 点击两次退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == Constants.REQUEST_QQ_SHARE) {
                Log.e("lwd", "qq");
                Tencent.onActivityResultData(requestCode, resultCode, data, mQQShareListener);
            } else if (requestCode == Constants.REQUEST_QZONE_SHARE) {
                Log.e("lwd", "qzone");
                Tencent.onActivityResultData(requestCode, resultCode, data, mQZONEShareListener);
            }
        }
        //根据返回的Code来切换tab
        if (!EmptyUtil.isEmpty(resultCode)){
            if (resultCode==StatusVariable.MATCHAPPLYCODE){
                tabState(StatusVariable.MATCHAPPLY);
                viewPagerAdapter.getItem(StatusVariable.MATCHAPPLYCODE).onActivityResult(requestCode,resultCode,data);
            }else if (resultCode==StatusVariable.MATCHGRADECODE){
                tabState(StatusVariable.MATCHSCORE);
                viewPagerAdapter.getItem(StatusVariable.MATCHGRADECODE).onActivityResult(requestCode,resultCode,data);
            }else if (resultCode==StatusVariable.DISCOVERCODE){
                tabState(StatusVariable.DISCOVER);
                viewPagerAdapter.getItem(StatusVariable.DISCOVER).onActivityResult(requestCode,resultCode,data);
            }else if (resultCode==StatusVariable.PCENTERCODE){
                tabState(StatusVariable.PERSONALCENTE);
                viewPagerAdapter.getItem(StatusVariable.PCENTERCODE).onActivityResult(requestCode,resultCode,data);
            }else if (resultCode == StatusVariable.RELEASE){
                viewPagerAdapter.getItem(StatusVariable.DISCOVERCODE).onActivityResult(requestCode,resultCode,data);
            }else if (resultCode == StatusVariable.RELEASECODESUCCESS){
                viewPagerAdapter.getItem(StatusVariable.DISCOVER).onActivityResult(requestCode,resultCode,data);
            }else if (resultCode == StatusVariable.DELEATECODESUCCESS){
                viewPagerAdapter.getItem(StatusVariable.DISCOVER).onActivityResult(requestCode,resultCode,data);
            }
        }
    }
    //qq分享好友回调
    private IUiListener mQQShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.e("lwd", "qq 空间分享成功");
            toastUtil.centerToast( "分享好友成功");
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("lwd", "qq 空间分享异常:" + uiError.errorMessage);
            toastUtil.centerToast( "分享好友异常");
        }

        @Override
        public void onCancel() {
            toastUtil.centerToast(  "取消分享好友");
        }
    };

    //qq空间分享回调
    private IUiListener mQZONEShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.e("lwd", "qq 分享成功");
            toastUtil.centerToast(  "分享空间成功");
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("lwd", "qq 分享异常:" + uiError.errorMessage);
            toastUtil.centerToast( "分享空间异常");
        }

        @Override
        public void onCancel() {
            Log.e("lwd", "qq 分享取消");
            toastUtil.centerToast( "取消分享空间");
        }
    };

    public void setSinaWbCallBack(Intent intent) {
        mThirdPart.setSinaWbCallBack(intent, this);
    }

    @Override
    public void onWbShareSuccess() {
        toastUtil.centerToast( "分享成功");
    }

    @Override
    public void onWbShareCancel() {
        toastUtil.centerToast( "取消分享");
    }

    @Override
    public void onWbShareFail() {
        toastUtil.centerToast( "分享失败");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginState = MyApplication.getInstance().getLoginState();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setSinaWbCallBack(intent);
    }

    /**
     * 获取经纬度
     * @param location
     */
    @Override
    public void getLocation(Location location) {

        LocationBean locationBean = new LocationBean();
        locationBean.setLatitude(location.getLatitude());
        locationBean.setLongitude(location.getLongitude());
        MyApplication.getInstance().setLocationBean(locationBean);
    }


    /**
     * 申请权限
     */
    private void initPermission() {


        RxPermissions rxPermission = new RxPermissions(this);

        rxPermission.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE)
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean grand) {
                        if (!grand) {
                            toastUtil.centerToast("用户拒绝了权限");
                        }

                    }
                });
    }


}



