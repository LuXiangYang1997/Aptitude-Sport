package com.huasport.smartsport.ui.pcenter.vm;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Handler;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.PersonalApplycardLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.PersonalMyCardBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.settings.bean.UserInfoBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyApplyCardActivity;
import com.huasport.smartsport.ui.pcenter.view.PersonalMyCardListActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PersonalMyApplyCardVM extends BaseViewModel implements CounterListener {

    private PersonalMyApplyCardActivity personalMyApplyCardActivity;
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> code = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");
    public ObservableField<String> sex = new ObservableField<>("");
    public ObservableField<String> codeText = new ObservableField<>("获取验证码");
    public ObservableField<Boolean> codeClick = new ObservableField<>(true);
    private PersonalApplycardLayoutBinding binding;
    private int number;
    private Handler mHandler = new Handler();
    private  String token;
    private Timer timer;
    private String type = "";
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;

    public PersonalMyApplyCardVM(PersonalMyApplyCardActivity personalMyApplyCardActivity) {
        this.personalMyApplyCardActivity = personalMyApplyCardActivity;
        binding = personalMyApplyCardActivity.getBinding();
        init();
        initView();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(personalMyApplyCardActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(personalMyApplyCardActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {

            token = userBean.getToken();

        }
    }

    private void initView() {

        type = personalMyApplyCardActivity.getIntent().getStringExtra(StatusVariable.PERSONALMSG);

        UserInfoBean.ResultBean.RegisterBean cardBean = (UserInfoBean.ResultBean.RegisterBean) personalMyApplyCardActivity.getIntent().getSerializableExtra(StatusVariable.CARDBEAN);
        if (!EmptyUtil.isEmpty(cardBean)) {
            if (!EmptyUtil.isEmpty(cardBean.getRealName())) {
                name.set(cardBean.getRealName());
            }
            if (!EmptyUtil.isEmpty(cardBean.getPhone())) {
                phoneNumber.set(cardBean.getPhone());
            }
            String gender = cardBean.getGender();
            if (!EmptyUtil.isEmpty(gender)) {
                if (gender.equals(StatusVariable.GENDERBOY)) {
                    binding.boy.setChecked(true);
                    sex.set("男");
                } else if (gender.equals(StatusVariable.GENDERGIRL)) {
                    binding.girl.setChecked(true);
                    sex.set("女");
                }
            }
        }

        binding.sexRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.boy:
                        sex.set("男");
                        break;
                    case R.id.girl:
                        sex.set("女");
                        break;
                }
            }
        });
    }

    //保存报名卡
    public void saveApplyCard() {

        if (EmptyUtil.isEmpty(name.get())) {
            toastUtil.showTopSnackBar("真实姓名不能为空");
            return;
        }

        if (EmptyUtil.isEmpty(sex.get())) {
            toastUtil.showTopSnackBar("请选择性别");
            return;
        }
        if (EmptyUtil.isEmpty(phoneNumber.get())) {
            toastUtil.showTopSnackBar("请输入手机号");
            return;
        }
        if (!Util.isPhoneNumber(phoneNumber.get())) {
            toastUtil.showTopSnackBar("请输入正确的11位手机号");
            return;
        }
        if (EmptyUtil.isEmpty(code.get())) {
            toastUtil.showTopSnackBar("请输入验证码");
            return;
        }
        HashMap params = new HashMap();
        params.put("baseMethod", Method.MYAPPLYCARD);
        params.put("realName", name.get());
        if (sex.get().equals("男")) {
            params.put("gender", "1");
        } else if (sex.get().equals("女")) {
            params.put("gender", "2");
        }
        params.put("verifyCode", code.get());
        params.put("phone", phoneNumber.get());
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);


        OkHttpUtil.postRequest(personalMyApplyCardActivity, params, new RequestCallBack<PersonalMyCardBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<PersonalMyCardBean> response) {
                PersonalMyCardBean personalMyCardBean = response.body();
                if (!EmptyUtil.isEmpty(personalMyCardBean)){
                    int resultCode = personalMyCardBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){

                        Intent intent = new Intent(personalMyApplyCardActivity, PersonalMyCardListActivity.class);
                        intent.putExtra(StatusVariable.PERSONALMSG, StatusVariable.PERSONALAPPLYCARD);
                        if (type.equals(StatusVariable.PERSONALCARDLIST)) {
                            personalMyApplyCardActivity.finish();
                        } else {
                            personalMyApplyCardActivity.startActivity(intent);
                            personalMyApplyCardActivity.finish();
                        }
                        if (timer != null) {
                            timer.cancel();
                        }
                        toastUtil.centerToast("修改成功");
                    }else if(resultCode == StatusVariable.NOBIND){
                        IntentUtil.startActivity(personalMyApplyCardActivity, BindPhoneActivity.class);
                    }else if (resultCode == StatusVariable.NOLOGIN){
                        IntentUtil.startActivity(personalMyApplyCardActivity, LoginActivity.class);

                    }
                }
            }

            @Override
            public PersonalMyCardBean parseNetworkResponse(String jsonResult) {
                PersonalMyCardBean personalMyCardBean = JSON.parseObject(jsonResult, PersonalMyCardBean.class);
                return personalMyCardBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }


        });

    }

    /**
     * 获取手机验证码
     */
    public void getCode() {
        number = 60;
        HashMap params = new HashMap();
        //手机号判断
        if (EmptyUtil.isEmpty(phoneNumber.get())) {
            toastUtil.showTopSnackBar("手机号不能为空");
            return;
        }
        if (!Util.isPhoneNumber(phoneNumber.get())) {
            toastUtil.showTopSnackBar("请输入正确的11位手机号");
            return;
        }
        params.put("phoneNum", phoneNumber.get());
        params.put("t", String.valueOf(System.currentTimeMillis()));
        params.put("baseUrl", Config.baseUrl);
        params.put("baseMethod",Method.GETVERTIFYCODE);

        OkHttpUtil.getRequest(personalMyApplyCardActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)){
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){


                        final Timer mTimer = new Timer();

                        TimerTask mTimerTask = new TimerTask() {
                            @Override
                            public void run() {
                                if (number == 0) {
                                    number = 60;
                                    codeClick.set(true);
                                    mTimer.cancel();
                                    codeText.set(personalMyApplyCardActivity.getResources().getString(R.string.login_reset_get));
                                } else {
                                    number--;
                                    codeText.set(personalMyApplyCardActivity.getResources().getString(R.string.login_reset_get) + " " + number);
                                }
                            }
                        };
                        mTimer.schedule(mTimerTask, 1000, 1000);

                    }

                }
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {
                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);
                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                codeText.set(personalMyApplyCardActivity.getResources().getString(R.string.login_reset_get) + " " + number);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == StatusVariable.PERSONALCODE) {
            if (!EmptyUtil.isEmpty(data)) {
                UserInfoBean.ResultBean.RegisterBean cardBean = (UserInfoBean.ResultBean.RegisterBean) data.getSerializableExtra(StatusVariable.CARDBEAN);

                if (!EmptyUtil.isEmpty(cardBean.getRealName())) {
                    name.set(cardBean.getRealName());
                }
                if (!EmptyUtil.isEmpty(cardBean.getPhone())) {
                    phoneNumber.set(cardBean.getPhone());
                }
                String gender = cardBean.getGender();
                if (!EmptyUtil.isEmpty(gender)) {
                    if (gender.equals(StatusVariable.GENDERBOY)) {
                        binding.boy.setChecked(true);
                        sex.set("男");
                    } else if (gender.equals(StatusVariable.GENDERGIRL)) {
                        binding.girl.setChecked(true);
                        sex.set("女");
                    }
                }
            }

        }
    }

    @Override
    public void countEnd(boolean isEnd) {
        if(isEnd){
            if(!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }

    }
}
