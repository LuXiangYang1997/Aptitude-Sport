package com.huasport.smartsport.ui.discover.vm;


import android.support.v4.app.FragmentActivity;

import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;

import java.util.HashMap;

public class DiscoverVm extends BaseViewModel implements CounterListener {

    private MyApplication application = MyApplication.getInstance();
    private FragmentActivity activity;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private String token;

    public DiscoverVm(FragmentActivity activity) {
        this.activity = activity;
        init();
    }

    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(activity);
        //初始化加载框
        loadingDialog = new LoadingDialog(activity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this,2);
        //获取token
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)){

            token = userBean.getToken();

        }
    }

    /**
     * 初始化推荐数据
     */
    private void initRecommandData(){

        HashMap params = new HashMap();
        params.put("token",token);



    }

    /**
     * 初始化列表数据
     */
    private void initListData(){



    }

    @Override
    public void countEnd(boolean isEnd) {
        if (!EmptyUtil.isEmpty(loadingDialog)){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)){
            token = userBean.getToken();
        }

    }



}