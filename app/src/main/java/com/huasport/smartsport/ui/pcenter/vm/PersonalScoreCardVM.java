package com.huasport.smartsport.ui.pcenter.vm;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.PersonalscoreLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.UploadBean;
import com.huasport.smartsport.ui.matchgrade.view.MatchGradeRankingsActivity;
import com.huasport.smartsport.ui.pcenter.adapter.PersonalRankingAdapter;
import com.huasport.smartsport.ui.pcenter.bean.PersonalCompetitionBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.view.PersonalScoreCardAvtivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.ShareUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.popwindow.NormalShareCallBack;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.wxapi.ThirdPart;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class PersonalScoreCardVM extends BaseViewModel implements WbShareCallback, CounterListener {

    private static final Object TAG = "zhinengtiyu";
    private PersonalScoreCardAvtivity personalScoreCardactivity;
    private PersonalscoreLayoutBinding binding;
    private Handler handler = new Handler();
    private PopupWindow cardPop;
    private PopupWindow sharePop;
    private ThirdPart mThirdPart;
    private String competitionCode;
    private String token;
    public ObservableField<String> competitionDate = new ObservableField<>("");
    public ObservableField<String> matchName = new ObservableField<>("");
    public ObservableField<String> matchCode = new ObservableField<>("");
    private PersonalRankingAdapter personalRankingAdapter;
    private Bitmap bitmap;
    private Counter counter;
    private LoadingDialog loadingDialog;
    private ToastUtil toastUtil;
    private ShareUtil shareUtil;


    public PersonalScoreCardVM(PersonalScoreCardAvtivity personalScoreCardactivity, PersonalRankingAdapter personalRankingAdapter) {
        this.personalScoreCardactivity = personalScoreCardactivity;
        this.personalRankingAdapter = personalRankingAdapter;
        binding = personalScoreCardactivity.getBinding();
        init();
        initView();
        initData();

    }
    /**
     * 初始化
     */
    private void init() {
        competitionCode = personalScoreCardactivity.getIntent().getStringExtra("CompetitionCode");
        //初始化Toast
        toastUtil = new ToastUtil(personalScoreCardactivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(personalScoreCardactivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        //初始化分享
        shareUtil = new ShareUtil(personalScoreCardactivity);
        mThirdPart = new ThirdPart(personalScoreCardactivity);
        //弹出加载框
        loadingDialog.show();
    }

    /**
     * 成绩详情
     */
    private void initData() {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("competitionCode", competitionCode);
        params.put("baseMethod", Method.MYGTRADEDETAIL);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(personalScoreCardactivity, params, new RequestCallBack<PersonalCompetitionBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<PersonalCompetitionBean> response) {
                PersonalCompetitionBean personalCompetitionBean = response.body();
                if (!EmptyUtil.isEmpty(personalCompetitionBean)){
                    int resultCode = personalCompetitionBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        PersonalCompetitionBean.ResultBean resultBean = personalCompetitionBean.getResult();
                       if(!EmptyUtil.isEmpty(resultBean)){
                           List<PersonalCompetitionBean.ResultBean.ScoreListBean> scoreList = resultBean.getScoreList();
                           PersonalCompetitionBean.ResultBean.BestScoreBean bestScore = resultBean.getBestScore();
                           if (!EmptyUtil.isEmpty(bestScore.getMatchCode())) {
                               matchCode.set((String) bestScore.getMatchCode());
                           }
                           //用户头像
                           if (!EmptyUtil.isEmpty(resultBean.getHeadimgUrl())) {
                               GlideUtil.LoadCircleImage(personalScoreCardactivity, resultBean.getHeadimgUrl(), binding.userheader);
                           } else {
                               binding.userheader.setImageResource(R.mipmap.icon_defaultheader_yes);
                           }
                           //nickname
                           if (!EmptyUtil.isEmpty(bestScore.getPalyerName())) {
                               binding.userName.setText(bestScore.getPalyerName());
                           }
                           //赛事名称
                           if (!EmptyUtil.isEmpty(bestScore.getMatchName())) {
                               matchName.set(bestScore.getMatchName());
                               binding.cardMatchName.setText(bestScore.getMatchName());
                           }
                           //成绩
                           if (!EmptyUtil.isEmpty(bestScore.getScoreDesc())) {
                               binding.cardCount.setText(bestScore.getScoreDesc());
                           }
                           //时间日期
                           if (!EmptyUtil.isEmpty(bestScore.getPartTime())) {
                               Calendar calendar = Calendar.getInstance();
                               calendar.setTimeInMillis(bestScore.getPartTime());
                               SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式
                               String date = sf.format(calendar.getTime());
                               binding.shareCardTime.setText(date);
                           }
                           //赛事名称
                           String groupName = (String) bestScore.getGroupName();
                           String eventName = bestScore.getEventName();

                           if (!EmptyUtil.isEmpty(groupName) && !EmptyUtil.isEmpty(eventName)) {
                               binding.groupName.setText(groupName + "-" + eventName);
                           } else if (!EmptyUtil.isEmpty(groupName) && EmptyUtil.isEmpty(eventName)) {
                               binding.groupName.setText(groupName);
                           } else if (EmptyUtil.isEmpty(groupName) && !EmptyUtil.isEmpty(eventName)) {
                               binding.groupName.setText(eventName);
                           }
                           personalRankingAdapter.loadData(scoreList);
                       }
                    }else if(resultCode == StatusVariable.NOBIND){
                        IntentUtil.startActivity(personalScoreCardactivity,BindPhoneActivity.class);
                    }else if(resultCode == StatusVariable.NOLOGIN){
                        IntentUtil.startActivity(personalScoreCardactivity,LoginActivity.class);
                    }
                }
            }

            @Override
            public PersonalCompetitionBean parseNetworkResponse(String jsonResult) {
                PersonalCompetitionBean personalCompetitionBean = JSON.parseObject(jsonResult, PersonalCompetitionBean.class);
                return personalCompetitionBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
            }
        });
    }

    private void initView() {
        //长按保存图片
        binding.rlScoreCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Bitmap bitmap = saveScreenImage(v);//生成Bitmap
                Util.saveImageToGallery(personalScoreCardactivity, bitmap);

                return false;
            }
        });


    }

    /**
     * 分享点击事件
     */
    public void shareCard(){

        Bitmap bitmap = saveScreenImage(binding.rlScoreCard);//生成Bitmap
        String s = uploadImg(bitmap);//拿到地址
        uploadImg(s);//上传分享

    }


    /**
     * 排名点击事件
     */
    public void scoreRankings() {

        Intent intent = new Intent(personalScoreCardactivity, MatchGradeRankingsActivity.class);
        intent.putExtra("comptitionCode", competitionCode);
        intent.putExtra("comptitionDate", competitionDate.get());
        intent.putExtra("matchName", matchName.get());
        intent.putExtra("matchCode", matchCode.get());
        personalScoreCardactivity.startActivity(intent);
    }

    /**
     * 将bitmap转成路径,传给后台
     * */
    public String uploadImg(Bitmap temBitmap) {
        // 首先保存图片路径
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "zhinengtiyu");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        //当前时间来命名图片
        String fileName = System.currentTimeMillis() + ".jpg";
        final File file = new File(appDir, fileName);

        try {
            FileOutputStream foStream = new FileOutputStream(file);
            temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.flush();
            foStream.close();
            Util.insertImageAlbum(personalScoreCardactivity, file.getAbsolutePath());
        } catch (Exception e) {
            Log.i("Show", e.toString());
            e.getStackTrace();
        }
        return file.getAbsolutePath();

    }

    /**
     * 上传操作，返回的是一个图片的url
     *
     * @param filepath
     */
    private void uploadImg(final String filepath) {

        HashMap uploadParams = new HashMap();
        uploadParams.put("baseMethod", Method.UPLOAD_FILE);
        uploadParams.put("file", filepath);
        uploadParams.put("t", String.valueOf(System.currentTimeMillis()));
        uploadParams.put("baseUrl", Config.baseUrl);

        OkHttpUtil.uploadFile(personalScoreCardactivity, uploadParams, new RequestCallBack<UploadBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<UploadBean> response) {
                UploadBean uploadBean = response.body();
                if (!EmptyUtil.isEmpty(uploadBean)){
                    int resultCode = uploadBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        sharePop(uploadBean.getResult().getUrl(), filepath);
                    }
                }

            }

            @Override
            public UploadBean parseNetworkResponse(String jsonResult) {
                UploadBean uploadBean = JSON.parseObject(jsonResult, UploadBean.class);
                return uploadBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });

    }

    /**
     * 分享
     * @param url
     * @param filepath
     */
    private void sharePop(final String url, final String filepath) {

        PopWindowUtil.allShare(personalScoreCardactivity, new NormalShareCallBack() {
            @Override
            public void qqFriendsShare(PopupWindow popupWindow) {
                mThirdPart.qqShareImage(filepath, mQQShareListener, false);
            }

            @Override
            public void qqSpaceShare(PopupWindow popupWindow) {
                mThirdPart.qqShareImage(filepath, mQZONEShareListener, true);
            }

            @Override
            public void wechatFriendsShare(PopupWindow popupWindow) {
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        bitmap = Util.decodeUriAsBitmapFromNet(url);
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if (aBoolean) {

                            mThirdPart.wechatimgShare(bitmap
                                    , ThirdPart.WECHAT_FRIEND);
                        }
                    }
                }.execute();
            }

            @Override
            public void wechatQuanShare(PopupWindow popupWindow) {
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        bitmap = Util.decodeUriAsBitmapFromNet(url);
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if (aBoolean) {
                            mThirdPart.wechatimgShare(bitmap
                                    , ThirdPart.WECHAT_MOMENT);
                        }
                    }
                }.execute();
            }

            @Override
            public void weiBoShare(PopupWindow popupWindow) {
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        bitmap = Util.decodeUriAsBitmapFromNet(url);
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if (aBoolean) {
                            mThirdPart.sinaWBShareImg(bitmap);
                        }
                    }
                }.execute();
            }
        });
    }

    //qq分享好友回调
    private IUiListener mQQShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.e("lwd", "qq 空间分享成功");
            toastUtil.centerToast("分享好友成功");
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("lwd", "qq 空间分享异常:" + uiError.errorMessage);
            toastUtil.centerToast("分享好友异常");
        }

        @Override
        public void onCancel() {
            toastUtil.centerToast("取消分享好友");
        }
    };

    //qq空间分享回调
    private IUiListener mQZONEShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.e("lxy", "qq 分享成功");
            toastUtil.centerToast( "分享空间成功");
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("lxy", "qq 分享异常:" + uiError.errorMessage);
            toastUtil.centerToast( "分享空间异常");
        }

        @Override
        public void onCancel() {
            Log.e("lxy", "qq 分享取消");
            toastUtil.centerToast( "取消分享空间");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == Constants.REQUEST_QQ_SHARE) {
                LogUtil.e( "qq");
                Tencent.onActivityResultData(requestCode, resultCode, data, mQQShareListener);
            } else if (requestCode == Constants.REQUEST_QZONE_SHARE) {
                LogUtil.e("qzone");
                Tencent.onActivityResultData(requestCode, resultCode, data, mQZONEShareListener);
            }
        }
    }

    public void setSinaWbCallBack(Intent intent) {
        mThirdPart.setSinaWbCallBack(intent, this);
    }

    @Override
    public void onWbShareSuccess() {
        toastUtil.centerToast( "分享成功");
    }

    @Override
    public void onWbShareCancel(){ toastUtil.centerToast( "取消分享");
    }

    @Override
    public void onWbShareFail() {
        toastUtil.centerToast("分享失败");
    }

    //对ScrollView内部的Imageview进行截图
    private Bitmap saveScreenImage(View view) {
        //宽度是ScrollView的宽度，高度是RelativeLayout的高度+130
        Bitmap bmp = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        binding.rlScoreCard.draw(canvas);
        return bmp;
    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if (!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }
}
