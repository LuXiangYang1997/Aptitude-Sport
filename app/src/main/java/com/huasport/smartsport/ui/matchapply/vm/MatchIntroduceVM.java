package com.huasport.smartsport.ui.matchapply.vm;

import android.content.Intent;
import android.graphics.Color;
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
import com.huasport.smartsport.databinding.ActivityMatchIntroduceBinding;
import com.huasport.smartsport.ui.matchapply.adapter.MatchAdapter;
import com.huasport.smartsport.ui.matchapply.bean.MatchsBean;
import com.huasport.smartsport.ui.matchapply.view.CompetitionListActivity;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/7.
 */

public class MatchIntroduceVM extends BaseViewModel implements WbShareCallback, CounterListener {

    private MyApplication application = MyApplication.getInstance();
    private MatchIntroduceActivity matchIntroduceActivity;
    private final ActivityMatchIntroduceBinding binding;
    private MatchAdapter matchAdapter;
    private String matchCode;
    private MatchsBean.ResultBean.MatchBean match;
    private ThirdPart mThirdPart;
    private MatchsBean matchsBean;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private String token = "";

    public MatchIntroduceVM(MatchIntroduceActivity matchIntroduceActivity, MatchAdapter matchAdapter) {
        this.matchIntroduceActivity = matchIntroduceActivity;
        this.matchAdapter = matchAdapter;
        binding = matchIntroduceActivity.getBinding();
        init();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {
        //获取上一个界面传来的Matchcode
        Intent intent = matchIntroduceActivity.getIntent();
        matchCode = intent.getStringExtra("matchCode");
        String type = intent.getStringExtra("type");
        if (type.equals("normal")) {
            binding.statusText.setVisibility(View.VISIBLE);
        } else {
            binding.statusText.setVisibility(View.GONE);
        }
        //初始化Toast
        toastUtil = new ToastUtil(matchIntroduceActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(matchIntroduceActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = application.getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        mThirdPart = new ThirdPart(matchIntroduceActivity);
        //弹出加载框
        loadingDialog.show();
    }


    /**
     * 初始化数据
     */
    private void initData() {

        final HashMap params = new HashMap();
        params.put("baseMethod", Method.MATCHINTRODUCE);
        if (EmptyUtil.isEmpty(matchCode)) {
            return;
        }
        params.put("matchCode", matchCode);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(matchIntroduceActivity, params, new RequestCallBack<MatchsBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<MatchsBean> response) {
                MatchsBean matchsBean = response.body();
                if(!EmptyUtil.isEmpty(matchsBean)){
                    int resultCode = matchsBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MatchsBean.ResultBean resultBean = matchsBean.getResult();
                        if(!EmptyUtil.isEmpty(resultBean)){
                            MatchsBean.ResultBean.MatchBean match = resultBean.getMatch();
                            String shareUrl = resultBean.getShareUrl();
                            //判断报名按钮的状态
                            if (match != null) {
                                applyTextJudge(match.getApplyStatus());
                            }
//                            因为adapter中加载的是集合，所以这里进行了存储操作
                            List<MatchsBean.ResultBean.MatchBean> matchList = new ArrayList();
                            if (match != null && !match.equals("null")) {
                                matchList.add(match);
                                Log.e("MList", matchList.size() + "");
                                matchAdapter.loadData(matchList);
                            }
                        }
                    }
                }
            }

            @Override
            public MatchsBean parseNetworkResponse(String jsonResult) {
                matchsBean = JSON.parseObject(jsonResult, MatchsBean.class);
                return matchsBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
            }
        });
    }

    /**
     * 分享
     */
    public void share() {

        PopWindowUtil.allShare(matchIntroduceActivity, new NormalShareCallBack() {
            @Override
            public void qqFriendsShare(PopupWindow popupWindow) {
                mThirdPart.qqShare(matchAdapter.mList.get(0).getMatchName(), "比赛时间 " + DateUtil.getDate(matchAdapter.mList.get(0).getStartTime()) + "-" + DateUtil.getDate(matchAdapter.mList.get(0).getEndTime())
                                , matchsBean.getResult().getShareUrl(), ThirdPart.QQ_SHARE_IAMGE, mQQShareListener, false);
            }

            @Override
            public void qqSpaceShare(PopupWindow popupWindow) {

                        mThirdPart.qqShare(matchAdapter.mList.get(0).getMatchName(), "比赛时间 " + DateUtil.getDate(matchAdapter.mList.get(0).getStartTime()) + "-" + DateUtil.getDate(matchAdapter.mList.get(0).getEndTime())
                                , matchsBean.getResult().getShareUrl(), ThirdPart.QQ_SHARE_IAMGE, mQZONEShareListener, true);
                popupWindow.dismiss();
            }

            @Override
            public void wechatFriendsShare(PopupWindow popupWindow) {
                mThirdPart.wxUrlShare(matchsBean.getResult().getShareUrl(), matchAdapter.mList.get(0).getMatchName()
                                , "比赛时间 " + DateUtil.getDate(matchAdapter.mList.get(0).getStartTime()) + "-" + DateUtil.getDate(matchAdapter.mList.get(0).getEndTime())
                                , R.mipmap.icon_share, ThirdPart.WECHAT_FRIEND);
                popupWindow.dismiss();
            }

            @Override
            public void wechatQuanShare(PopupWindow popupWindow) {
                mThirdPart.wxUrlShare(matchsBean.getResult().getShareUrl(), matchAdapter.mList.get(0).getMatchName()
                                        + "        比赛时间 " + DateUtil.getDate(matchAdapter.mList.get(0).getStartTime()) + "-" + DateUtil.getDate(matchAdapter.mList.get(0).getEndTime())
                                , "比赛时间 " + DateUtil.getDate(matchAdapter.mList.get(0).getStartTime()) + "-" + DateUtil.getDate(matchAdapter.mList.get(0).getEndTime())
                                , R.mipmap.icon_share, ThirdPart.WECHAT_MOMENT);
                popupWindow.dismiss();
            }

            @Override
            public void weiBoShare(PopupWindow popupWindow) {
                mThirdPart.sinaWBShare(matchAdapter.mList.get(0).getMatchName(), R.mipmap.icon_share);
                popupWindow.dismiss();
            }
        });
    }

    /**
     *判断下方按钮的点击事件
     * @param applyStatus
     */
    private void applyTextJudge(String applyStatus) {
        switch (applyStatus) {
            case StatusVariable.MATCH_OVER:
                binding.statusText.setText("比赛已结束");
                binding.statusText.setBackgroundColor(Color.parseColor("#A0A0A0"));
                binding.statusText.setEnabled(false);
                break;
            case StatusVariable.APPLY_ABORT:
                binding.statusText.setText("报名已截止");
                binding.statusText.setBackgroundColor(Color.parseColor("#B0B0B0"));
                binding.statusText.setEnabled(false);
                break;
            case StatusVariable.PEOPLENUM_FULL:
                binding.statusText.setText("人数已满");
                binding.statusText.setBackgroundColor(Color.parseColor("#FFCA00"));
                binding.statusText.setEnabled(false);
                break;
            case StatusVariable.PAUSE_APPLY:
                binding.statusText.setText("暂停报名");
                binding.statusText.setBackgroundColor(Color.parseColor("#C1C1C1"));
                binding.statusText.setEnabled(false);
                break;
            case StatusVariable.APPLY:
                binding.statusText.setText("报名");
                binding.statusText.setBackgroundColor(Color.parseColor("#FF8F00"));
                binding.statusText.setEnabled(true);
                break;
        }
    }

    /**
     * 下一步
     */
    public void nextStepText(){

//        跳转到选择赛事列表
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)){
            String token = userBean.getToken();
            if(EmptyUtil.isEmpty(token)){
                IntentUtil.startActivity(matchIntroduceActivity,LoginActivity.class);
                return;
            }
        }else{
            IntentUtil.startActivity(matchIntroduceActivity,LoginActivity.class);
            return;
        }

        Intent intent = new Intent(matchIntroduceActivity, CompetitionListActivity.class);
        intent.putExtra("MatchName", matchAdapter.mList.get(0).getMatchName());
        intent.putExtra("MatchStartTime", matchAdapter.mList.get(0).getStartTime());
        intent.putExtra("matchEndTime", matchAdapter.mList.get(0).getEndTime());
        if (!EmptyUtil.isEmpty(matchCode)) {
            intent.putExtra("matchCode", matchCode);
        }
        matchIntroduceActivity.startActivity(intent);
    }

    //qq分享好友回调
    private IUiListener mQQShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.e("lwd", "qq 空间分享成功");
            toastUtil.centerToast(   "分享好友成功");
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("lwd", "qq 空间分享异常:" + uiError.errorMessage);
            toastUtil.centerToast(   "分享好友异常");
        }

        @Override
        public void onCancel() {
            toastUtil.centerToast(   "取消分享好友");
        }
    };

    //qq空间分享回调
    private IUiListener mQZONEShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.e("lwd", "qq 分享成功");
            toastUtil.centerToast(   "分享空间成功");
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("lwd", "qq 分享异常:" + uiError.errorMessage);
            toastUtil.centerToast(  "分享空间异常");
        }

        @Override
        public void onCancel() {
            Log.e("lwd", "qq 分享取消");
            toastUtil.centerToast( "取消分享空间");
        }
    };

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
    }

    public void setSinaWbCallBack(Intent intent) {
        mThirdPart.setSinaWbCallBack(intent, this);
    }

    @Override
    public void onWbShareSuccess() {
        toastUtil.centerToast("分享成功");
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
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if(!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
