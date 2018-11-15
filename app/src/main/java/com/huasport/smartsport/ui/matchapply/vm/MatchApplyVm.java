package com.huasport.smartsport.ui.matchapply.vm;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.huasport.smartsport.MainActivity;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.MatchApplyLayoutBinding;
import com.huasport.smartsport.ui.matchapply.adapter.LogoAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.MatchApplyAdapter;
import com.huasport.smartsport.ui.matchapply.bean.BannerListBean;
import com.huasport.smartsport.ui.matchapply.bean.MatchApplyListBean;
import com.huasport.smartsport.ui.matchapply.view.BannerRuleActivity;
import com.huasport.smartsport.ui.matchapply.view.MatchApplyFragment;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.lzy.okgo.model.Response;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class MatchApplyVm extends BaseViewModel implements CounterListener{

    private MatchApplyFragment matchApplyFragment;
    private boolean loginState;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private ToastUtil toastUtil;
    private MatchApplyLayoutBinding binding;
    private LogoAdapter logoAdapter;
    private MatchApplyAdapter matchApplyAdapter;

    public MatchApplyVm(MatchApplyFragment matchApplyFragment, MatchApplyLayoutBinding binding, LogoAdapter logoAdapter, MatchApplyAdapter matchApplyAdapter) {
        this.matchApplyFragment = matchApplyFragment;
        this.binding = binding;
        this.logoAdapter = logoAdapter;
        this.matchApplyAdapter = matchApplyAdapter;
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
                            final List<String> bannerlist = resultBean.getList();
                            if (bannerlist.size()>0){



                             binding.bgaBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                                 @Override
                                 public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                                     Glide.with(matchApplyFragment.getActivity())
                                             .load(model)
                                             .placeholder(R.mipmap.img_placeholder)
                                             .error(R.mipmap.img_placeholder)
                                             .dontAnimate()
                                             .into(itemView);
                                 }
                             });
                                //item点击事件
                                binding.bgaBanner.setDelegate(new BGABanner.Delegate() {
                                    @Override
                                    public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
                                        Intent intent = new Intent(matchApplyFragment.getActivity(), BannerRuleActivity.class);
                                        intent.putExtra(StatusVariable.WEBURL, "http://wx.zntyydh.com/static/regulations");
                                        intent.putExtra(StatusVariable.TITLE,"首届全国智能体育大赛竞赛规程总则");
                                        matchApplyFragment.getActivity().startActivity(intent);
                                    }
                                });
                                if (bannerlist.size()>1){
                                    binding.bgaBanner.setAutoPlayAble(true);
                                    binding.bgaBanner.setAllowUserScrollable(true);
                                }else{
                                    binding.bgaBanner.setAllowUserScrollable(false);
                                    binding.bgaBanner.setAutoPlayAble(false);
                                }
                             binding.bgaBanner.setData(bannerlist,null);
                            }
                        }else{
                            LogUtil.e("ResultBean是空的");
                        }
                    }
                }else{
                    LogUtil.e("BannerListBean是空的");
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
                            if (!EmptyUtil.isEmpty(logos)){
                                logoAdapter.loadData(logos);
                            }
                            if (!EmptyUtil.isEmpty(types)){
                                matchApplyAdapter.loadData(types);
                            }

                        }else{
                            LogUtil.e("ResultBean是空的");
                        }
                    }
                }else{
                    LogUtil.e("MatchListBean是空的");
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