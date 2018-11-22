package com.huasport.smartsport.ui.pcenter.vm;

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
import com.huasport.smartsport.databinding.PersonalmygradedetailLayoutBinding;
import com.huasport.smartsport.ui.pcenter.adapter.MatchGradeDetailAdapter;
import com.huasport.smartsport.ui.pcenter.bean.PersonalMyGradeDetailBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyGradeDetailActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;

import java.util.HashMap;

public class PersonalMyGradeDetailVM extends BaseViewModel implements CounterListener {

    private PersonalMyGradeDetailActivity personalMyGradeDetailActivity;
    private final PersonalmygradedetailLayoutBinding binding;
    private String token;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private String competiotionCode;
    private String scoreDesc;


    public PersonalMyGradeDetailVM(PersonalMyGradeDetailActivity personalMyGradeDetailActivity, MatchGradeDetailAdapter matchGradeDetailAdapter) {
        this.personalMyGradeDetailActivity = personalMyGradeDetailActivity;
        binding = personalMyGradeDetailActivity.getBinding();
        init();
        initData();
    }
    /**
     * 初始化
     */
    private void init() {
        competiotionCode = personalMyGradeDetailActivity.getIntent().getStringExtra("competiotionCode");
        scoreDesc = personalMyGradeDetailActivity.getIntent().getStringExtra("scoreDesc");
        //初始化Toast
        toastUtil = new ToastUtil(personalMyGradeDetailActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(personalMyGradeDetailActivity, R.style.LoadingDialog);
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
        params.put("baseMethod", Method.MYGRADETAIL);
        params.put("competitionCode", competiotionCode);
        params.put("scoreDesc", scoreDesc);
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(personalMyGradeDetailActivity, params, new RequestCallBack<PersonalMyGradeDetailBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<PersonalMyGradeDetailBean> response) {
                PersonalMyGradeDetailBean personalMyGradeDetailBean = response.body();
                if(!EmptyUtil.isEmpty(personalMyGradeDetailBean)){
                    int resultCode = personalMyGradeDetailBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        PersonalMyGradeDetailBean.ResultBean resultBean = personalMyGradeDetailBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            PersonalMyGradeDetailBean.ResultBean.DetailBean detail = resultBean.getDetail();

                            String eventName = detail.getEventName();
                            String groupName = (String) detail.getGroupName();
                            String siteName = detail.getSiteName();
                            String matchName =detail.getMatchName();
                            //比赛项名称
                            if (!EmptyUtil.isEmpty(eventName)) {
                                binding.matchprogramName.setText(eventName);
                            } else {
                                binding.matchprogramName.setText("无");
                            }
                            //分组名称
                            if (!EmptyUtil.isEmpty(groupName)) {
                                binding.matchGroupname.setText(groupName);
                            } else {
                                binding.matchGroupname.setText("无");
                            }
                            //赛场名称
                            if (!EmptyUtil.isEmpty(siteName)) {
                                binding.matchlocationName.setText(siteName);
                            } else {
                                binding.matchlocationName.setText("无");
                            }
                            //赛事名称
                            if (!EmptyUtil.isEmpty(matchName)) {
                                binding.matchName.setText(matchName);
                            } else {
                                binding.matchName.setText("无");
                            }
                        }
                    }
                }

            }

            @Override
            public PersonalMyGradeDetailBean parseNetworkResponse(String jsonResult) {
                PersonalMyGradeDetailBean personalMyGradeDetailBean = JSON.parseObject(jsonResult, PersonalMyGradeDetailBean.class);
                return personalMyGradeDetailBean;
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

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if(!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }
}
