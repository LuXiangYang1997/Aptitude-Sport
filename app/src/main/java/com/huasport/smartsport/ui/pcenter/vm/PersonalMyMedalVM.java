package com.huasport.smartsport.ui.pcenter.vm;

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
import com.huasport.smartsport.databinding.ActivityMymedalBinding;
import com.huasport.smartsport.ui.pcenter.adapter.PersonalMedalAdapter;
import com.huasport.smartsport.ui.pcenter.bean.PersonalMedalBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyMedalActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.huasport.smartsport.wxapi.ThirdPart;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.util.HashMap;
import java.util.List;


public class PersonalMyMedalVM extends BaseViewModel implements CounterListener, RefreshLoadMoreListener {

    private PersonalMyMedalActivity personalMyMedalActivity;
    private String token;
    private PersonalMedalAdapter personalMedalAdapter;
    private ActivityMymedalBinding binding;
    private PersonalMedalBean.ResultBean.ShareBean share;
    private ThirdPart mThirdPart;
    private PopupWindow popupWindow;
    private int page = 1;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private int totalPage;

    public PersonalMyMedalVM(PersonalMyMedalActivity personalMyMedalActivity, PersonalMedalAdapter personalMedalAdapter) {
        this.personalMyMedalActivity = personalMyMedalActivity;
        this.personalMedalAdapter = personalMedalAdapter;

        binding = personalMyMedalActivity.getBinding();
        init();
        initData(StatusVariable.REFRESH);
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(personalMyMedalActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(personalMyMedalActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //初始化加载刷新
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {

            token = userBean.getToken();

        }
        //弹出加载框
        loadingDialog.show();
    }


    /**
     * 初始化数据
     * */
    private void initData(final int loadType) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.MEDALLIST);
        params.put("token", token);
        params.put("currentPage", page + "");
        params.put("pageSize", "10");
        params.put("baseUrl", Config.baseUrl);


        OkHttpUtil.getRequest(personalMyMedalActivity, params, new RequestCallBack<PersonalMedalBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<PersonalMedalBean> response) {
                PersonalMedalBean personalMedalBean = response.body();
                if (!EmptyUtil.isEmpty(personalMedalBean)){
                    int resultCode = personalMedalBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        PersonalMedalBean.ResultBean resultBean = personalMedalBean.getResult();
                        if(!EmptyUtil.isEmpty(resultBean)){
                            totalPage = resultBean.getTotalPage();
                            if (totalPage == 0) {
                                NullStateUtil.setNullState(binding.nulldata, true);
                                return;
                            } else {
                                NullStateUtil.setNullState(binding.nulldata,  false);
                            }

                            //集合
                            List<PersonalMedalBean.ResultBean.ScoreCertBean> scoreCert = personalMedalBean.getResult().getScoreCert();

                            share = personalMedalBean.getResult().getShare();

                            if (loadType == StatusVariable.LOADMORE) {
                                binding.smartRefreshlayout.finishLoadMore();
                                personalMedalAdapter.loadMoreData(scoreCert);
                            } else {
                                binding.smartRefreshlayout.finishRefresh();
                                personalMedalAdapter.loadData(scoreCert);
                            }

                        }
                        page++;
                    }else if (resultCode == StatusVariable.NOLOGIN){
                        IntentUtil.startActivity(personalMyMedalActivity,LoginActivity.class);
                    }else if (resultCode == StatusVariable.NOBIND){
                        IntentUtil.startActivity(personalMyMedalActivity,BindPhoneActivity.class);

                    }

                }
            }

            @Override
            public PersonalMedalBean parseNetworkResponse(String jsonResult) {
                PersonalMedalBean personalMedalBean = JSON.parseObject(jsonResult, PersonalMedalBean.class);
                return personalMedalBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
                binding.smartRefreshlayout.finishLoadMore();
                binding.smartRefreshlayout.finishRefresh();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        initData(StatusVariable.REFRESH);
    }

    @Override
    public void countEnd(boolean isEnd) {

    }

    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        page = 1;
        binding.smartRefreshlayout.setNoMoreData(false);
        initData(type);
    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {
        if (page <= totalPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            initData(type);
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }
    }
}
