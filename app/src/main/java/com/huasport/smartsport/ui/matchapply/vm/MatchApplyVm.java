package com.huasport.smartsport.ui.matchapply.vm;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MainActivity;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.ui.matchapply.bean.BannerListBean;
import com.huasport.smartsport.ui.matchapply.bean.MatchApplyListBean;
import com.huasport.smartsport.ui.matchapply.view.MatchApplyFragment;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.Counter;
import com.huasport.smartsport.util.CounterListener;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MatchApplyVm extends BaseViewModel implements CounterListener{

    private MatchApplyFragment matchApplyFragment;
    private boolean loginState;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private ToastUtil toastUtil;

    public MatchApplyVm(MatchApplyFragment matchApplyFragment) {
        this.matchApplyFragment = matchApplyFragment;
        init();
        initBannerData();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {
        //网络加载框
        loadingDialog = new LoadingDialog(matchApplyFragment.getActivity(), R.style.LoadingDialog);
        //网络计数器
        counter = new Counter(this,2);
        //Toast
        toastUtil = new ToastUtil(matchApplyFragment.getActivity());

    }

    /**
     * 初始化banner数据
     */
    private void initBannerData() {

        loadingDialog.show();

        HashMap params = new HashMap();
        params.put("baseMethod", Method.HOMEPAGEBANNER);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(matchApplyFragment.getActivity(), params, new RequestCallBack<BannerListBean>() {
            @Override
            public void onSuccess(Response<BannerListBean> response) {

                BannerListBean bannerListBean = response.body();
                if (!EmptyUtil.isEmpty(bannerListBean)){
                    int resultCode = bannerListBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        BannerListBean.ResultBean resultBean = bannerListBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            List<String> bannerlist = resultBean.getList();
                        }else{
                            Log.e("lxy-banner-bean","ResultBean是空的");
                        }
                    }
                }else{
                    Log.e("lxy-banner-bean","BannerListBean是空的");
                }

            }

            @Override
            public BannerListBean parseNetworkResponse(String jsonResult) {

                BannerListBean bannerListBean = JSON.parseObject(jsonResult, BannerListBean.class);

                return bannerListBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
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
     * 初始化列表数据
     */
    private void initData() {

       HashMap params = new HashMap();
       params.put("baseUrl",Config.baseUrl);
       params.put("baseMethod",Method.HOMEPAGELIST);
       
       OkHttpUtil.getRequest(matchApplyFragment.getActivity(), params, new RequestCallBack<MatchApplyListBean>() {
           @Override
           public void onSuccess(Response<MatchApplyListBean> response) {
               MatchApplyListBean matchApplyListBean = response.body();
                if (!EmptyUtil.isEmpty(matchApplyListBean)){
                    int resultCode = matchApplyListBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MatchApplyListBean.ResultBean resultBean = matchApplyListBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){

                            List<MatchApplyListBean.ResultBean.LogosBean> logos = resultBean.getLogos();
                            List<MatchApplyListBean.ResultBean.TypesBean> types = resultBean.getTypes();

                        }else{
                            Log.e("lxy-result-Bean","ResultBean是空的");
                        }
                    }
                }else{
                    Log.e("lxy-matchList-Bean","MatchListBean是空的");
                }


           }

           @Override
           public MatchApplyListBean parseNetworkResponse(String jsonResult) {

               MatchApplyListBean matchApplyListBean = JSON.parseObject(jsonResult, MatchApplyListBean.class);

               return matchApplyListBean;
           }

           @Override
           public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
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
     * 头像点击事件
     */
    public void headerView() {

        if (!loginState){
            MyApplication.getInstance().setClickState(true);
           IntentUtil.startActivityForResult(matchApplyFragment.getActivity(),LoginActivity.class);
           return;
        }
        MainActivity activity = (MainActivity) matchApplyFragment.getActivity();
        if (!EmptyUtil.isEmpty(activity)){
            activity.tabState(StatusVariable.PERSONALCENTE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loginState = MyApplication.getInstance().getLoginState();
        matchApplyFragment.initUserInfo();
    }
    //网络请求状态监听
    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            loadingDialog.dismiss();
        }
    }
}