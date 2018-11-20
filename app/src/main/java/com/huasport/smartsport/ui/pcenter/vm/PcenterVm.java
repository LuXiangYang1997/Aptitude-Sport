package com.huasport.smartsport.ui.pcenter.vm;


import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.PcenterLayoutBinding;
import com.huasport.smartsport.ui.discover.view.ReleaseActivity;
import com.huasport.smartsport.ui.pcenter.bean.UserCenterInfo;
import com.huasport.smartsport.ui.pcenter.bean.UserCertStatusBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.settings.bean.UserInfoBean;
import com.huasport.smartsport.ui.pcenter.settings.view.PersonalMsgActivity;
import com.huasport.smartsport.ui.pcenter.settings.view.SettingsActivity;
import com.huasport.smartsport.ui.pcenter.view.MatchStatusListActivity;
import com.huasport.smartsport.ui.pcenter.view.PCenterFragment;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

import okhttp3.Call;

public class PcenterVm extends BaseViewModel implements CounterListener {

    private PCenterFragment fragment;
    private String token = "";
    private ToastUtil toastUtil;
    private PcenterLayoutBinding binding;
    private String registerCode = "";
    private Counter counter;
    private Intent intent;

    public PcenterVm(PCenterFragment fragment, PcenterLayoutBinding binding) {
        this.fragment = fragment;
        this.binding=binding;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(fragment.getActivity());
        //初始化Counter
        counter = new Counter(this,2);

    }


    /**
     * 设置
     */
    public void setting() {

        IntentUtil.startActivityForResult(fragment.getActivity(), SettingsActivity.class);

    }

    /**
     * 修改个人信息
     */
    public void personalMessage() {

        IntentUtil.startActivity(fragment.getActivity(), PersonalMsgActivity.class);

    }

    /**
     * 认证
     */
    public void approve() {


    }

    /**
     * 比赛列表
     *
     * @param type 0:待支付 1:待完善 2:已成功 3:全部比赛
     */
    public void matchList(final int type) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.GETUSERINFO);
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl3);
        OkHttpUtil.getRequest(fragment.getActivity(), params, new RequestCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(Response<UserInfoBean> response) {
                UserInfoBean userInfoBean = response.body();
                if (!EmptyUtil.isEmpty(userInfoBean)){
                    int resultCode = userInfoBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        UserInfoBean.ResultBean resultBean = userInfoBean.getResult();
                        if(!EmptyUtil.isEmpty(resultBean)){
                            intent = new Intent(fragment.getActivity(), MatchStatusListActivity.class);
                            switch (type) {
                                case StatusVariable.PERSONALCENTER_WAITPAY:
                                    intent.putExtra("ListStatus", StatusVariable.WAIT_PAY);
                                    break;
                                case StatusVariable.PERSONALCENTER_PERFECT:
                                    intent.putExtra("ListStatus", StatusVariable.WAIT_COMPLETE);
                                    break;
                                case StatusVariable.PERSONALCENTER_ALREADYSUCCESS:
                                    intent.putExtra("ListStatus", StatusVariable.ORDERSUCCESS);
                                    break;
                                case StatusVariable.PERSONALCENTER_ALLMATCH:
                                    intent.putExtra("ListStatus", "");
                                    break;
                            }
                            if (intent != null) {
                                fragment.getActivity().startActivity(intent);
                            }
                        }
                    }else if (resultCode == StatusVariable.NOBIND){
                        IntentUtil.startActivity(fragment.getActivity(),LoginActivity.class);
                    }else if (resultCode == StatusVariable.NOLOGIN){
                        IntentUtil.startActivity(fragment.getActivity(), BindPhoneActivity.class);
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
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    /**
     * 发布
     */
    public void release() {

        IntentUtil.startActivity(fragment.getActivity(), ReleaseActivity.class);

    }

    /**
     * 关注
     */
    public void follow() {


    }

    /**
     * 粉丝
     */
    public void fans() {


    }

    /**
     * 我的报名卡
     */
    public void applycard() {


    }

    /**
     * 我的成绩
     */
    public void grade() {


    }

    /**
     * 我的奖章
     */
    public void medal() {


    }

    /**
     * 我的订单
     */
    public void order() {


    }

    /**
     * 帮助与反馈
     */
    public void help() {


    }

    /**
     * 关于我们
     */
    public void about() {


    }

    /**
     * 隐私政策
     */
    public void privacy() {


    }

    /**
     * 获取认证状态
     */
    private void getCertStatus(){

        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl3);
        params.put("baseMethod", Method.GETCERTIFICATIONINFO);
        params.put("terminal","ANDROID");
        params.put("token",token);

        OkHttpUtil.getRequest(fragment.getActivity(), params, new RequestCallBack<UserCertStatusBean>() {
            @Override
            public void onSuccess(Response<UserCertStatusBean> response) {

                UserCertStatusBean userCertStatusBean = response.body();
                if (!EmptyUtil.isEmpty(userCertStatusBean)){
                    int resultCode = userCertStatusBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        UserCertStatusBean.ResultBean resultBean = userCertStatusBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            String authStatus = resultBean.getAuthStatus();
                            if (!EmptyUtil.isEmpty(authStatus)){

                                if (authStatus.equals(StatusVariable.WAIT_AUTH)){
                                    binding.tvApproveStatus.setText(fragment.getActivity().getResources().getString(R.string.wait_auth));
                                }else if (authStatus.equals(StatusVariable.PASS)){
                                    binding.tvApproveStatus.setText(fragment.getActivity().getResources().getString(R.string.pass));
                                }else if (authStatus.equals(StatusVariable.REJECT)){
                                    binding.tvApproveStatus.setText(fragment.getActivity().getResources().getString(R.string.wait_auth));
                                }else if (authStatus.equals(StatusVariable.WAIT_AUDIT)){
                                    binding.tvApproveStatus.setText(fragment.getActivity().getResources().getString(R.string.wait_auth));
                                }

                            }

                        }else{
                            LogUtil.e("UserCertStatusBeam是空的");
                        }

                    }

                }

            }

            @Override
            public UserCertStatusBean parseNetworkResponse(String jsonResult) {

                UserCertStatusBean userCertStatusBean = JSON.parseObject(jsonResult, UserCertStatusBean.class);

                return userCertStatusBean;
            }

            @Override
            public void onFailed(int code, String msg) {

                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });



    }

    /**
     * 获取用户认证、发布、关注、粉丝信息
     */
    private void getUserInfo(){
        
        HashMap params = new HashMap();
        params.put("baseUrl",Config.baseUrl2);
        params.put("token",token);
        params.put("baseMethod",Method.GETUSERCENTERINFO);
        params.put("registerId",registerCode);
        
        OkHttpUtil.getRequest(fragment.getActivity(), params, new RequestCallBack<UserCenterInfo>() {
            @Override
            public void onSuccess(Response<UserCenterInfo> response) {
                UserCenterInfo userCenterInfo = response.body();
                if (!EmptyUtil.isEmpty(userCenterInfo)){
                    int resultCode = userCenterInfo.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        UserCenterInfo.ResultBean resultBean = userCenterInfo.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            UserCenterInfo.ResultBean.UserBean userBean = resultBean.getUser();
                            if (!EmptyUtil.isEmpty(userBean)){
                                int fansNumber = userBean.getFansNumber();
                                int releaseNumber = userBean.getReleaseNumber();
                                int followNumber = userBean.getFollowNumber();
                                if (!EmptyUtil.isEmpty(fansNumber)){
                                    binding.tvFansCount.setText(fansNumber+"");
                                }
                                if (!EmptyUtil.isEmpty(releaseNumber)){
                                    binding.tvReleaseCount.setText(releaseNumber+"");
                                }
                                if (!EmptyUtil.isEmpty(followNumber)){
                                    binding.tvAttentionCount.setText(followNumber+"");
                                }
                            }
                        }else{
                            LogUtil.e("UserCenterInfoBean是空的");

                        }
                    }
                }

            }

            @Override
            public UserCenterInfo parseNetworkResponse(String jsonResult) {

                UserCenterInfo userCenterInfo = JSON.parseObject(jsonResult, UserCenterInfo.class);

                return userCenterInfo;
            }

            @Override
            public void onFailed(int code, String msg) {

                if(!EmptyUtil.isEmpty(msg)){

                    toastUtil.centerToast(msg);

                }
            }
        });
    }


    /**
     * 更新Token、registerCode
     */
    private void upData() {
        if (!EmptyUtil.isEmpty(MyApplication.getInstance().getUserBean())){
            if (!EmptyUtil.isEmpty(MyApplication.getInstance().getUserBean().getToken())){
                token = MyApplication.getInstance().getUserBean().getToken();
            }else{
                token = "";
            }
           if (!EmptyUtil.isEmpty(MyApplication.getInstance().getUserBean().getRegisterCode())){
                registerCode = MyApplication.getInstance().getUserBean().getRegisterCode();
           }else{
                registerCode = "";
           }
        }else{
            token = "";
            registerCode = "";
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        upData();
        fragment.initUserInfo();
        getCertStatus();
        getUserInfo();
    }


    @Override
    public void countEnd(boolean isEnd) {

    }

}