package com.huasport.smartsport.ui.pcenter.vm;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.PersonalscoreLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.UploadBean;
import com.huasport.smartsport.ui.matchgrade.view.MatchGradeRankingsActivity;
import com.huasport.smartsport.ui.pcenter.adapter.PersonalRankingAdapter;
import com.huasport.smartsport.ui.pcenter.bean.PersonalCompetitionBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.view.PersonalScoreCardAvtivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.wxapi.ThirdPart;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

public class PersonalScoreCardVM extends BaseViewModel implements WbShareCallback {

    private static final Object TAG = "zhinengtiyu";
    private PersonalScoreCardAvtivity personalScoreCardactivity;
    private PersonalscoreLayoutBinding binding;
    private Handler handler = new Handler();
    private PopupWindow cardPop;
    private PopupWindow sharePop;
    private ThirdPart mThirdPart;
    private String competitionCode;
    private final String token;
    public ObservableField<String> competitionDate = new ObservableField<>("");
    public ObservableField<String> matchName = new ObservableField<>("");
    public ObservableField<String> matchCode = new ObservableField<>("");
    private PersonalRankingAdapter personalRankingAdapter;
    private Bitmap bitmap;


    public PersonalScoreCardVM(PersonalScoreCardAvtivity personalScoreCardactivity, PersonalRankingAdapter personalRankingAdapter) {
        this.personalScoreCardactivity = personalScoreCardactivity;
        this.personalRankingAdapter = personalRankingAdapter;
        binding = personalScoreCardactivity.getBinding();
        token = MyApplication.getToken(personalScoreCardactivity);
        initIntent();
        initView();
        initData();
        mThirdPart = new ThirdPart(personalScoreCardactivity);
    }

    //成绩详情
    private void initData() {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("competitionCode", competitionCode);
        params.put("method", Method.MYGTRADEDETAIL);
        params.put("baseUrl", Config.baseUrl);
        OkhttpUtils.getRequest(personalScoreCardactivity, params, new BaseHttpCallBack<PersonalCompetitionBean>(personalScoreCardactivity, true) {
            @Override
            public PersonalCompetitionBean parseNetworkResponse(String jsonResult) throws Exception {

                PersonalCompetitionBean personalCompetitionBean = JSON.parseObject(jsonResult, PersonalCompetitionBean.class);

                return personalCompetitionBean;
            }

            @Override
            public void onSuccess(PersonalCompetitionBean perosonalCompetitionBean, Call call, Response response) {
                if (perosonalCompetitionBean != null) {

                    if (perosonalCompetitionBean.getResultCode() == StatusVariable.NO_LOGIN) {
                        personalScoreCardactivity.startActivity2(LoginActivity.class);
                        return;
                    }
                    if (perosonalCompetitionBean.getResultCode() == StatusVariable.NOBIND) {
                        personalScoreCardactivity.startActivity2(BindActivity.class);
                        return;
                    }
                    if (perosonalCompetitionBean.getResultCode() == StatusVariable.SUCCESS) {
                        if (!EmptyUtils.isEmpty(perosonalCompetitionBean.getResult().getBestScore().getMatchCode())) {
                            matchCode.set((String) perosonalCompetitionBean.getResult().getBestScore().getMatchCode());
                        }
                        //用户头像
                        if (!EmptyUtils.isEmpty(perosonalCompetitionBean.getResult().getHeadimgUrl())) {
                            GlideUtils.LoadCircleImage(personalScoreCardactivity, perosonalCompetitionBean.getResult().getHeadimgUrl(), binding.userheader);
                        } else {
                            binding.userheader.setImageResource(R.mipmap.icon_header_yes);
                        }
                        //nickname
                        if (!EmptyUtils.isEmpty(perosonalCompetitionBean.getResult().getBestScore().getPalyerName())) {
                            binding.userName.setText(perosonalCompetitionBean.getResult().getBestScore().getPalyerName());
                        }
                        //赛事名称
                        if (!EmptyUtils.isEmpty(perosonalCompetitionBean.getResult().getBestScore().getMatchName())) {
                            matchName.set(perosonalCompetitionBean.getResult().getBestScore().getMatchName());
                            binding.cardMatchName.setText(perosonalCompetitionBean.getResult().getBestScore().getMatchName());
                        }
                        //成绩
                        if (!EmptyUtils.isEmpty(perosonalCompetitionBean.getResult().getBestScore().getScoreDesc())) {
                            binding.cardCount.setText(perosonalCompetitionBean.getResult().getBestScore().getScoreDesc());
                        }
                        //时间日期
                        if (!EmptyUtils.isEmpty(perosonalCompetitionBean.getResult().getBestScore().getPartTime())) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(perosonalCompetitionBean.getResult().getBestScore().getPartTime());
                            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式
                            String date = sf.format(calendar.getTime());
                            binding.shareCardTime.setText(date);
                        }
                        //
                        //赛事名称

                        String groupName = (String) perosonalCompetitionBean.getResult().getBestScore().getGroupName();
                        String eventName = perosonalCompetitionBean.getResult().getBestScore().getEventName();

                        if (!EmptyUtils.isEmpty(groupName) && !EmptyUtils.isEmpty(eventName)) {
                            binding.groupName.setText(groupName + "-" + eventName);
                        } else if (!EmptyUtils.isEmpty(groupName) && EmptyUtils.isEmpty(eventName)) {
                            binding.groupName.setText(groupName);
                        } else if (EmptyUtils.isEmpty(groupName) && !EmptyUtils.isEmpty(eventName)) {
                            binding.groupName.setText(eventName);
                        }
                        List<PersonalCompetitionBean.ResultBean.ScoreListBean> scoreList = perosonalCompetitionBean.getResult().getScoreList();
                        personalRankingAdapter.loadData(scoreList);
                    }
                }


            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.toast(personalScoreCardactivity, msg);
            }
        });

    }

    private void initIntent() {

        competitionCode = personalScoreCardactivity.getIntent().getStringExtra("CompetitionCode");

    }

    private void initView() {
        //长按保存图片
        binding.rlScoreCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Bitmap bitmap = saveScreenImage();//生成Bitmap
                Util.saveImageToGallery(personalScoreCardactivity, bitmap);

                return false;
            }
        });

        //分享图片
        binding.imggradeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = saveScreenImage();//生成Bitmap
                String s = uploadImg(bitmap);//拿到地址
                uploadImg(s);//上传分享
            }
        });

    }

    //排名点击事件
    public void scoreRankings() {

        Intent intent = new Intent(personalScoreCardactivity, MatchGradeRankingsActivity.class);
        intent.putExtra("comptitionCode", competitionCode);
        intent.putExtra("comptitionDate", competitionDate.get());
        intent.putExtra("matchName", matchName.get());
        intent.putExtra("matchCode", matchCode.get());
        personalScoreCardactivity.startActivity(intent);
    }

    /*
     * 将bitmap转成路径,传给后台
     * */
    public String uploadImg(Bitmap temBitmap) {
        // 首先保存图片路径
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "zhinengtiyu");
        if (!appDir.exists()) {
            appDir.mkdir();
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
        uploadParams.put("method", Method.UPLOAD_FILE);
        uploadParams.put("file", filepath);
        uploadParams.put("t", String.valueOf(System.currentTimeMillis()));
        uploadParams.put("baseUrl", Config.baseUrl);

        OkhttpUtils.uploadHttp(uploadParams, new StringCallBack(personalScoreCardactivity, true) {

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    String uploadresultStr = response.body().string();
                    Log.e("UploadStr", uploadresultStr);
                    final UploadBean uploadBean = JSON.parseObject(uploadresultStr, UploadBean.class);

                    if (uploadBean.getResultCode() == 200) {
                        sharePop(uploadBean.getResult().getUrl(), filepath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, personalScoreCardactivity, false);

    }

    private void sharePop(final String url, final String filepath) {

        View shareView = LayoutInflater.from(personalScoreCardactivity).inflate(R.layout.share_layout, null, false);
        sharePop = new PopupWindow(shareView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sharePop.setContentView(shareView);
        sharePop.showAtLocation(personalScoreCardactivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        sharePop.setOutsideTouchable(false);

        //Util.backgroundAlpha(myGradeWebActivity, 0.5f);
        //微博分享
        shareView.findViewById(R.id.weibo_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        //微信好友分享
        shareView.findViewById(R.id.wechat_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
        });
        //微信朋友圈分享
        shareView.findViewById(R.id.pengyouquan_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        //qq朋友分享
        shareView.findViewById(R.id.qFriend_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThirdPart.qqShareImage(filepath, mQQShareListener, false);
            }
        });

        //qq空间分享
        shareView.findViewById(R.id.qSpase_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThirdPart.qqShareImage(filepath, mQZONEShareListener, true);
            }
        });
        shareView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePop.dismiss();
            }
        });

    }


    //qq分享好友回调
    private IUiListener mQQShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.e("lwd", "qq 空间分享成功");
            ToastUtils.toast(personalScoreCardactivity, "分享好友成功");
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("lwd", "qq 空间分享异常:" + uiError.errorMessage);
            ToastUtils.toast(personalScoreCardactivity, "分享好友异常");
        }

        @Override
        public void onCancel() {
            ToastUtils.toast(personalScoreCardactivity, "取消分享好友");
        }
    };

    //qq空间分享回调
    private IUiListener mQZONEShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.e("lxy", "qq 分享成功");
            ToastUtils.toast(personalScoreCardactivity, "分享空间成功");
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("lxy", "qq 分享异常:" + uiError.errorMessage);
            ToastUtils.toast(personalScoreCardactivity, "分享空间异常");
        }

        @Override
        public void onCancel() {
            Log.e("lxy", "qq 分享取消");
            ToastUtils.toast(personalScoreCardactivity, "取消分享空间");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == Constants.REQUEST_QQ_SHARE) {
                Log.e("lxyQQ", "qq");
                Tencent.onActivityResultData(requestCode, resultCode, data, mQQShareListener);
            } else if (requestCode == Constants.REQUEST_QZONE_SHARE) {
                Log.e("lxyQZONE", "qzone");
                Tencent.onActivityResultData(requestCode, resultCode, data, mQZONEShareListener);
            }
        }
    }

    public void setSinaWbCallBack(Intent intent) {
        mThirdPart.setSinaWbCallBack(intent, this);
    }

    @Override
    public void onWbShareSuccess() {
        ToastUtils.toast(personalScoreCardactivity, "分享成功");
    }

    @Override
    public void onWbShareCancel() {
        ToastUtils.toast(personalScoreCardactivity, "取消分享");
    }

    @Override
    public void onWbShareFail() {
        ToastUtils.toast(personalScoreCardactivity, "分享失败");
    }

    //对ScrollView内部的Imageview进行截图
    private Bitmap saveScreenImage() {
        //宽度是ScrollView的宽度，高度是RelativeLayout的高度+130
        Bitmap bmp = Bitmap.createBitmap(binding.rlScoreCard.getWidth(), binding.rlScoreCard.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        binding.rlScoreCard.draw(canvas);
        return bmp;
    }

}
