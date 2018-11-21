package com.huasport.smartsport.ui.pcenter.vm;

import android.content.Intent;
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
import com.huasport.smartsport.ui.pcenter.adapter.PersonalMyCardListAdapter;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.settings.bean.UserInfoBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyApplyCardActivity;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyCardListActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PersonalMycardListVM extends BaseViewModel implements CounterListener {

    private PersonalMyCardListActivity personalMyCardListActivity;
    private String token;
    private PersonalMyCardListAdapter personalMyCardListAdapter;
    private List<UserInfoBean.ResultBean.RegisterBean> registerBeanList;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;

    public PersonalMycardListVM(PersonalMyCardListActivity personalMyCardListActivity, PersonalMyCardListAdapter personalMyCardListAdapter) {
        this.personalMyCardListActivity = personalMyCardListActivity;
        this.personalMyCardListAdapter = personalMyCardListAdapter;
        init();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(personalMyCardListActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(personalMyCardListActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
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
     */
    private void initData() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.GETUSERINFO);
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl3);


        OkHttpUtil.getRequest(personalMyCardListActivity, params, new RequestCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<UserInfoBean> response) {
                UserInfoBean userInfoBean = response.body();
                if(!EmptyUtil.isEmpty(userInfoBean)){
                    int resultCode = userInfoBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        UserInfoBean.ResultBean resultBean = userInfoBean.getResult();
                        if(!EmptyUtil.isEmpty(resultBean)){

                            registerBeanList = new ArrayList<>();

                            UserInfoBean.ResultBean.RegisterBean register = resultBean.getRegister();

                            registerBeanList.add(register);

                            personalMyCardListAdapter.loadData(registerBeanList);

                        }


                    }else if (resultCode == StatusVariable.NOLOGIN){

                        IntentUtil.startActivity(personalMyCardListActivity,LoginActivity.class);

                    }else if (resultCode == StatusVariable.NOBIND){
                        IntentUtil.startActivity(personalMyCardListActivity, BindPhoneActivity.class);
                    }
                }
            }

            @Override
            public UserInfoBean parseNetworkResponse(String jsonResult) {
                UserInfoBean userInfoBean = JSON.parseObject(jsonResult, UserInfoBean.class);
                return userInfoBean;
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

    /**
     * 修改报名卡
     */
    public void modifyApplyCard() {

//        String msg = personalMyCardListActivity.getIntent().getStringExtra(StatusVariable.PERSONALMSG);
        Intent intent = new Intent(personalMyCardListActivity, PersonalMyApplyCardActivity.class);
        intent.putExtra(StatusVariable.CARDBEAN, (Serializable) registerBeanList.get(0));
        intent.putExtra(StatusVariable.PERSONALMSG, StatusVariable.PERSONALCARDLIST);
        personalMyCardListActivity.startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
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
