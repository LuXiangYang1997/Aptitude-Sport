package com.huasport.smartsport.ui.matchapply.vm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.ActivityFillRegistrationFormBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.rxpermission.RxPermissionUtil;
import com.huasport.smartsport.rxpermission.RxPermissionUtilCallback;
import com.huasport.smartsport.ui.discover.adapter.FormAdapter;
import com.huasport.smartsport.ui.discover.model.ParamsData;
import com.huasport.smartsport.ui.matchapply.bean.AthletesMessageBean;
import com.huasport.smartsport.ui.matchapply.bean.CompleteMessageBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupEventsBean;
import com.huasport.smartsport.ui.matchapply.bean.PersonalMyCardBean;
import com.huasport.smartsport.ui.matchapply.bean.SaveResultBean;
import com.huasport.smartsport.ui.matchapply.bean.UploadBean;
import com.huasport.smartsport.ui.matchapply.view.FillRegistrationFormActivity;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.ui.matchapply.view.PayMentOrderActivty;
import com.huasport.smartsport.ui.matchapply.view.ProtocolActivity;
import com.huasport.smartsport.ui.matchapply.view.SuccessPaymentInfoActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.bean.GetVertifyCodeResultBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.settings.bean.UserInfoBean;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GetPathFromUriUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.util.popwindow.SelectPicCallBack;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 陆向阳 on 2018/6/9.
 */

public class FillRegistrationFormVM extends BaseViewModel implements CounterListener {

    private FillRegistrationFormActivity fillRegistrationFormActivity;
    //上传证明
    public ObservableField<String> frontImgstr = new ObservableField<>("");
    //上传个人照片
    public ObservableField<String> contraryImgstr = new ObservableField<>("");
    //封装一个界面发生改变的观察者
    private final ActivityFillRegistrationFormBinding binding;
    private List<AthletesMessageBean.ResultBean.PropertiesBean> properties;
    private String orderCode;
    private List<GroupEventsBean.ResultBean.GroupsBean.EventsBean> eventList;
    private String matchCode;
    private FormAdapter formAdapter;
    private int number;
    private List<AthletesMessageBean.ResultBean.PropertiesBean> personaList;
    private String matchstatus;
    private boolean params;
    private RelativeLayout rootView;
    private String matchName;
    private String matchStartTime;
    private String matchEndTime;
    private String token;
    private ToastUtil toastUtil;
    private MyApplication application = MyApplication.getInstance();
    public ObservableField<String> formRealName = new ObservableField<>("");//realName
    public ObservableField<String> formPhoneNumber = new ObservableField<>("");//手机号
    public ObservableField<String> formCode = new ObservableField<>("");//验证码
    public ObservableField<Boolean> getCodeClick = new ObservableField<>(true);//获取验证码能否点击
    public ObservableField<String> getCodeText = new ObservableField<>("获取验证码");//获取验证码文本
    public ObservableField<String> sexStr = new ObservableField<>("");//性别
    public ObservableField<Boolean> saveStatus = new ObservableField<>(false);//是否保存为卡片
    public ObservableField<UserInfoBean.ResultBean.RegisterBean> registerBeanObservableField = new ObservableField<>();
    private UserInfoBean.ResultBean.RegisterBean register;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private ParamsData paramsData;
    private int picType = 1;//获取图片类型
    private String picturePath = "";
    private double countTime = 60;

    public FillRegistrationFormVM(FillRegistrationFormActivity fillRegistrationFormActivity, FormAdapter formAdapter) {
        this.fillRegistrationFormActivity = fillRegistrationFormActivity;
        binding = fillRegistrationFormActivity.getBinding();
        this.formAdapter = formAdapter;
        init();
        getUserInfo();
        initView();//点击事件
        getFormMsg(matchCode);
    }

    /**
     * 初始化
     */
    private void init() {
        Intent formIntent = fillRegistrationFormActivity.getIntent();
        eventList = (List<GroupEventsBean.ResultBean.GroupsBean.EventsBean>) formIntent.getSerializableExtra("eventList");
        personaList = (List<AthletesMessageBean.ResultBean.PropertiesBean>) formIntent.getSerializableExtra("players");
        matchCode = formIntent.getStringExtra("matchCode");
        orderCode = formIntent.getStringExtra("orderCode");
        matchstatus = formIntent.getStringExtra("matchstatus");
        matchName = formIntent.getStringExtra("matchName");
        matchStartTime = formIntent.getStringExtra("matchStartTime");
        matchEndTime = formIntent.getStringExtra("matchEndTime");
        rootView = (RelativeLayout) fillRegistrationFormActivity.findViewById(R.id.rl_layout);//布局rootView

        //初始化Toast
        toastUtil = new ToastUtil(fillRegistrationFormActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(fillRegistrationFormActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        //初始化ParamData
        paramsData = new ParamsData(fillRegistrationFormActivity);
//        初始化分享
//        shareUtil = new ShareUtil(activity);
        //弹出加载框
        /*        loadingDialog.show();*/

    }

    /**
     * 初始化数据信息
     */
    private void initView() {
        //格式化日期
        if (!EmptyUtil.isEmpty(matchStartTime) && !EmptyUtil.isEmpty(matchEndTime)) {
            String time = DateUtil.dateConvert(matchStartTime, matchEndTime);
            binding.registationformTime.setText(time);
        }
        if (!EmptyUtil.isEmpty(matchName)) {
            binding.registationformName.setText(matchName + "," + "查看详情");
        }

        //选择生日
        formAdapter.setClick(new FormAdapter.Click() {
            @Override
            public void birdthClick(RecyclerView.ViewHolder viewHolder, final int position, int type) {
                TimePickerView optionsPickerView = new TimePickerView.Builder(fillRegistrationFormActivity, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        formAdapter.mList.get(position).setVal(Util.getTime(date));
                        formAdapter.notifyDataSetChanged();
                    }
                }).setSubmitColor(Color.parseColor("#FF8F00")).setCancelColor(Color.parseColor("#FF8F00"))
                        .setSubmitText("确定").setSubCalSize(14)
                        .setCancelText("取消").setSubCalSize(14)
                        .setTitleText("选择日期").setTitleSize(14)
                        .setTitleColor(Color.parseColor("#333333"))
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setLineSpacingMultiplier(1.2f)
                        .build();
                optionsPickerView.show();
            }

            //选择证件类型
            @Override
            public void certificateClick(RecyclerView.ViewHolder viewHolder, final int position, int type) {
                final List<String> namelist = new ArrayList<>();
                namelist.add("身份证");
                namelist.add("护照");
                namelist.add("军官证");

                final OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(fillRegistrationFormActivity, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {

                        String type = namelist.get(options1);
//                        if (){
//
//                        }
                        formAdapter.mList.get(position).setVal(type);
                        if (type.equals("身份证")) {
                            fillRegistrationFormActivity.idCard();
                        } else {
                            if (type.equals("护照")) {
                                binding.fronttext.setText("请上传护照");
                                fillRegistrationFormActivity.passPort();
                            } else if (type.equals("军官证")) {
                                binding.fronttext.setText("请上传军官证");
                                fillRegistrationFormActivity.certificate();
                            }
                        }
                        formAdapter.notifyDataSetChanged();
                    }
                }).setSubmitColor(Color.BLACK).setCancelColor(Color.BLACK).build();
                optionsPickerView.setPicker(namelist);
                optionsPickerView.show();
            }

            //获取验证码
            @Override
            public void codeGet(RecyclerView.ViewHolder viewHolder, int position, int type) {
            }
        });

        //判断卡片是否选中
        binding.cbCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectCb();
                } else {
                    noselectCb();
                }
            }
        });
        //性别
        binding.sexRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.boy:
                        sexStr.set("m");
                        break;
                    case R.id.girl:
                        sexStr.set("f");
                        break;
                }
            }
        });

        //是否保存为报名卡
        binding.cbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveStatus.set(true);
                } else {
                    saveStatus.set(false);
                }
            }
        });
        binding.formPhonenumber.setSelection(binding.formPhonenumber.getText().length());
        binding.formRealName.setSelection(binding.formRealName.getText().length());
    }

    /**
     * 保存报名信息不做任何限制
     */
    public void saveMsg() {

        HashMap params = formAdapter.getParams();
        params.put("baseMethod", Method.SAVEMSG);
        params.put("orderCode", orderCode);
        params.put("token", token);
        params.put("playerPhone", formPhoneNumber.get());
        params.put("playerName", formRealName.get());
        params.put("sex", sexStr.get());
        params.put("baseUrl", Config.baseUrl);
//                Log.e("ForMAdapter", saveMsgParams.toString());

        OkHttpUtil.postRequest(fillRegistrationFormActivity, params, new RequestCallBack<SaveResultBean>() {
            @Override
            public void onSuccess(Response<SaveResultBean> response) {
                SaveResultBean saveResultBean = response.body();
                if (!EmptyUtil.isEmpty(saveResultBean)) {
                    int resultCode = saveResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        toastUtil.showTopSnackBar(fillRegistrationFormActivity.getResources().getString(R.string.save_success));

                    } else if (resultCode == StatusVariable.NOBIND) {
                        IntentUtil.startActivity(fillRegistrationFormActivity, BindPhoneActivity.class);
                    } else {
                        toastUtil.showTopSnackBar(saveResultBean.getResultMsg());
                    }
                }
            }

            @Override
            public SaveResultBean parseNetworkResponse(String jsonResult) {
                SaveResultBean saveResultBean = JSON.parseObject(jsonResult, SaveResultBean.class);
                return saveResultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.showTopSnackBar(msg);
                }
            }
        });
    }

    /**
     * 正面身份证
     */
    public void frontImg() {
        picType = 1;
        modifyHeader();
    }

    /**
     * 反面身份证
     */
    public void contraryImg() {
        picType = 2;
        modifyHeader();
    }

    /**
     * 头部点击事件
     */
    public void headerDetail() {
        Intent detailIntent = new Intent(fillRegistrationFormActivity, MatchIntroduceActivity.class);
        detailIntent.putExtra("matchCode", matchCode);
        detailIntent.putExtra("type", "other");
        fillRegistrationFormActivity.startActivity(detailIntent);
    }

    /**
     * 提交报名信息
     */
    private void submitApplyMsg() {

        final HashMap completeOrderParams = formAdapter.getParams();

        if (EmptyUtil.isEmpty(formRealName.get())) {
            toastUtil.showTopSnackBar("真实姓名不能为空");
            return;
        } else {
            completeOrderParams.put("playerName", formRealName.get());
        }

        if (EmptyUtil.isEmpty(sexStr.get())) {
            toastUtil.showTopSnackBar("请选择您的性别");
            return;
        } else {
            completeOrderParams.put("sex", sexStr.get());
        }
        if (EmptyUtil.isEmpty(formPhoneNumber.get())) {
            toastUtil.showTopSnackBar("请输入手机号");
            return;
        }
        if (!Util.isPhoneNumber(formPhoneNumber.get())) {
            toastUtil.showTopSnackBar("请输入正确的11位手机号");
            return;
        } else {
            completeOrderParams.put("playerPhone", formPhoneNumber.get());
        }
        if (!binding.cbCheckbox.isChecked()) {
            completeOrderParams.put("isCard", "0");
            if (EmptyUtil.isEmpty(formCode.get())) {
                toastUtil.showTopSnackBar("请输入验证码");
                return;
            } else {
                completeOrderParams.put("verifyCode", formCode.get());
            }
        } else {
            completeOrderParams.put("isCard", "1");
            formCode.set("0");
        }

        if (matchstatus.equals("wait_pay")) {//待支付
            if (personaList.size() > 0) {
                params = paramsData.getParams(fillRegistrationFormActivity, personaList, rootView, FillRegistrationFormVM.this);
                if (!params) {
                    return;
                }
            }
        } else if (matchstatus.equals("normal_apply")) {
            if (formAdapter.mList.size() > 0) {
                params = paramsData.getParams(fillRegistrationFormActivity, formAdapter.mList, rootView, FillRegistrationFormVM.this);
                if (!params) {
                    return;
                }
            }
        }

        //获取参数列表

        //下方Checkbox判断协议
        if (!binding.formCheckbox.isChecked()) {
            toastUtil.showTopSnackBar("请确认免责声明");
            return;
        }

        BaseDialog.show(fillRegistrationFormActivity, "提示", "请您确认信息无误后提交报名", "确定", "取消", false, false,
                0, new DialogCallBack() {
                    @Override
                    public void submit(CustomDialog.Builder customDialog) {
                        customDialog.dismiss();
                        submitsure(completeOrderParams);
                    }

                    @Override
                    public void cancel(CustomDialog.Builder customDialog) {
                        customDialog.dismiss();
                    }
                });
    }

    /**
     * 提交报名
     *
     * @param completeOrderParams
     */
    public void submitsure(HashMap completeOrderParams) {

        if (saveStatus.get()) {

            HashMap params = new HashMap();
            params.put("method", Method.MYAPPLYCARD);
            params.put("realName", formRealName.get());
            if (sexStr.get().equals("m")) {
                params.put("gender", "1");
            } else if (sexStr.get().equals("f")) {
                params.put("gender", "2");
            }
            params.put("verifyCode", formCode.get());
            params.put("phone", formPhoneNumber.get());
            params.put("token", token);
            params.put("baseUrl", Config.baseUrl);

            OkHttpUtil.postRequest(fillRegistrationFormActivity, params, new RequestCallBack<PersonalMyCardBean>() {
                @Override
                public void onSuccess(Response<PersonalMyCardBean> response) {

                }

                @Override
                public PersonalMyCardBean parseNetworkResponse(String jsonResult) {
                    PersonalMyCardBean personalMyCardBean = JSON.parseObject(jsonResult, PersonalMyCardBean.class);
                    return personalMyCardBean;
                }

                @Override
                public void onFailed(int code, String msg) {
                    if (!EmptyUtil.isEmpty(msg)) {
                        toastUtil.showTopSnackBar(msg);
                    }
                }
            });

        }

        if (orderCode.equals("null") && orderCode.equals("null") && orderCode.equals("")) {
            return;
        }
        completeOrderParams.put("baseMethod", Method.COMPLETEMSG);
        completeOrderParams.put("verifyCode", formCode.get());
        completeOrderParams.put("orderCode", orderCode);
        completeOrderParams.put("token", token);
        completeOrderParams.put("baseUrl", Config.baseUrl);
//        Log.e("Params", completeOrderParams.toString());

        OkHttpUtil.postRequest(fillRegistrationFormActivity, completeOrderParams, new RequestCallBack<CompleteMessageBean>() {
            @Override
            public void onSuccess(Response<CompleteMessageBean> response) {
                CompleteMessageBean completeMessageBean = response.body();
                if (!EmptyUtil.isEmpty(completeMessageBean)) {
                    int resultCode = completeMessageBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        CompleteMessageBean.ResultBean resultBean = completeMessageBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            String orderStaus = resultBean.getOrderStaus();
                                    if (orderStaus.equals("success")) {
                            Intent intent = new Intent(fillRegistrationFormActivity, SuccessPaymentInfoActivity.class);
                            intent.putExtra("orderCode", orderCode);
                            intent.putExtra("orderType", StatusVariable.APPLYSUCCESS);
                            intent.putExtra("orderStatus", StatusVariable.ORDERSUCCESS);
                            fillRegistrationFormActivity.startActivity(intent);
                        } else {
                            Intent intent = new Intent(fillRegistrationFormActivity, PayMentOrderActivty.class);//支付
                            intent.putExtra("orderCode", orderCode);//orderCode
                            fillRegistrationFormActivity.startActivity(intent);
                        }
                        }

                    } else if (resultCode == StatusVariable.NOLOGIN) {
                        IntentUtil.startActivity(fillRegistrationFormActivity, LoginActivity.class);
                    } else if (resultCode == StatusVariable.NOBIND) {
                        IntentUtil.startActivity(fillRegistrationFormActivity, BindPhoneActivity.class);
                    }
                }
            }

            @Override
            public CompleteMessageBean parseNetworkResponse(String jsonResult) {
                CompleteMessageBean completeMessageBean = JSON.parseObject(jsonResult, CompleteMessageBean.class);
                return completeMessageBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    /**
     * 加载上传成功的图片
     */
    public void loadImg() {
        //加载选中的图片，并将Text隐藏
        if (!frontImgstr.get().isEmpty()) {
            binding.fronttext.setVisibility(View.GONE);
            GlideUtil.LoadImage(fillRegistrationFormActivity, frontImgstr.get().toString(), binding.frontImg);
        }
        if (!contraryImgstr.get().isEmpty()) {
            GlideUtil.LoadImage(fillRegistrationFormActivity, contraryImgstr.get().toString(), binding.contraryImg);
            binding.contrarytext.setVisibility(View.GONE);
        }
    }

    /**
     * 获取验证码
     */
    public void getVertifyCode() {

        if (EmptyUtil.isEmpty(formPhoneNumber.get())) {
            toastUtil.showTopSnackBar(fillRegistrationFormActivity.getResources().getString(R.string.login_phonenum_empty));
            return;
        }
        if (!Util.isPhoneNumber(formPhoneNumber.get())) {
            toastUtil.showTopSnackBar(fillRegistrationFormActivity.getResources().getString(R.string.login_phonenum_error));
            return;
        }
        //设置获取验证码不可点击
        getCodeClick.set(false);
        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl);
        params.put("baseMethod", Method.GETVERTIFYCODE);
        params.put("phoneNum", formPhoneNumber.get());

        OkHttpUtil.getRequest(fillRegistrationFormActivity, params, new RequestCallBack<GetVertifyCodeResultBean>() {
            @Override
            public void onSuccess(Response<GetVertifyCodeResultBean> response) {
                GetVertifyCodeResultBean getVertifyCodeResultBean = response.body();
                if (!EmptyUtil.isEmpty(getVertifyCodeResultBean)) {
                    int resultCode = getVertifyCodeResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {

                        final Timer mTimer = new Timer();

                        TimerTask mTimerTask = new TimerTask() {
                            @Override
                            public void run() {
                                if (countTime == 0) {
                                    countTime = 60;
                                    getCodeClick.set(true);
                                    mTimer.cancel();
                                    getCodeText.set(fillRegistrationFormActivity.getResources().getString(R.string.login_reset_get));
                                } else {
                                    countTime--;
                                    getCodeText.set(fillRegistrationFormActivity.getResources().getString(R.string.login_reset_get) + " " + countTime);
                                }
                            }
                        };
                        mTimer.schedule(mTimerTask, 1000, 1000);

                    } else {
                        toastUtil.centerToast(getVertifyCodeResultBean.getResultMsg());
                        getCodeClick.set(true);
                        getCodeText.set(fillRegistrationFormActivity.getResources().getString(R.string.login_reset_get));
                    }
                }

            }

            @Override
            public GetVertifyCodeResultBean parseNetworkResponse(String jsonResult) {

                GetVertifyCodeResultBean getVertifyCodeResultBean = JSON.parseObject(jsonResult, GetVertifyCodeResultBean.class);

                return getVertifyCodeResultBean;
            }

            @Override
            public void onFailed(int code, String msg) {

                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    /**
     * 上传证件
     *
     * @param filepath
     * @param type
     */
    private void uploadFile(String filepath, final String type) {
        HashMap params = new HashMap();
        params.put("baseMethod", Method.UPLOAD_FILE);
        params.put("file", filepath);
        params.put("t", String.valueOf(System.currentTimeMillis()));
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.uploadFile(fillRegistrationFormActivity, params, new RequestCallBack<UploadBean>() {
            @Override
            public void onSuccess(Response<UploadBean> response) {
                UploadBean uploadBean = response.body();
                if (!EmptyUtil.isEmpty(uploadBean)) {
                    int resultCode = uploadBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        HashMap<String, String> params = formAdapter.getParams();
                        if (uploadBean.getResult().getUrl() != null && !EmptyUtil.isEmpty(uploadBean.getResult().getUrl())) {
                            if (type.equals("front")) {//第一个证件
                                if (!frontImgstr.get().isEmpty()) {
                                    binding.fronttext.setVisibility(View.GONE);
                                    GlideUtil.LoadImage(fillRegistrationFormActivity, frontImgstr.get().toString(), binding.frontImg);
                                }
                                params.put("attOne", uploadBean.getResult().getUrl());//放入HashMap中
                            } else if (type.equals("contract")) {//第二个证件
                                params.put("attTwo", uploadBean.getResult().getUrl());
                                if (!contraryImgstr.get().isEmpty()) {
                                    GlideUtil.LoadImage(fillRegistrationFormActivity, contraryImgstr.get().toString(), binding.contraryImg);
                                    binding.contrarytext.setVisibility(View.GONE);
                                }
                            }
                        }
                        toastUtil.showTopSnackBar("上传成功");
                    } else {
                        toastUtil.showTopSnackBar("上传失败");
                    }
                }
            }

            @Override
            public UploadBean parseNetworkResponse(String jsonResult) {

                UploadBean uploadBean = JSON.parseObject(jsonResult, UploadBean.class);

                return uploadBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    /**
     * 获取填写运动员信息，从待支付界面过来
     *
     * @param matchCode
     */
    private void getFormMsg(String matchCode) {

        final List<AthletesMessageBean.ResultBean.PropertiesBean> mList = new ArrayList<>();

        if (eventList.size() == 0 && eventList == null) {
            return;
        }
        //过滤掉空数据
        if (personaList != null) {
            LogUtil.e(personaList.toString());
            for (int i = 0; i < personaList.size(); i++) {
                if (personaList.get(i).getCnname() != null && !personaList.get(i).getCnname().equals("null") && !personaList.get(i).getCnname().equals("")) {

                    if (personaList.get(i).getVal() != null && !personaList.get(i).getVal().equals("null") && !personaList.get(i).getVal().equals("")) {

                        if (personaList.get(i).getCnname().equals("姓名")) {
                            binding.formRealName.setText(personaList.get(i).getVal());
                            formRealName.set(personaList.get(i).getVal());
                        }
                        if (personaList.get(i).getCnname().equals("性别")) {

                            if (personaList.get(i).getVal().equals("m")) {
                                binding.boy.setChecked(true);
                                sexStr.set("m");
                            } else if (personaList.get(i).getVal().equals("f")) {
                                binding.girl.setChecked(true);
                                sexStr.set("f");
                            }
                        }

                        if (personaList.get(i).getCnname().equals("手机号码")) {
                            binding.formPhonenumber.setText(personaList.get(i).getVal());
                            formPhoneNumber.set(personaList.get(i).getVal());
                        }


                        if (personaList.get(i).getCnname().equals("证件类型")) {
                            if (personaList.get(i).isIsShow()) {

                                if (personaList.get(i).getVal().equals(StatusVariable.IDCARD) || personaList.get(i).getVal().equals("身份证")) {
                                    fillRegistrationFormActivity.idCard();
                                    personaList.get(i).setVal("身份证");
                                } else if (personaList.get(i).getVal().equals(StatusVariable.PASSCARD) || personaList.get(i).getVal().equals("护照")) {
                                    personaList.get(i).setVal("护照");
                                    fillRegistrationFormActivity.passPort();
                                } else if (personaList.get(i).getVal().equals(StatusVariable.CERTIFICATECARD) || personaList.get(i).getVal().equals("军官证")) {
                                    personaList.get(i).setVal("军官证");
                                    fillRegistrationFormActivity.certificate();
                                }

                            } else {
                                binding.otherMsgLayout.setVisibility(View.GONE);
                            }

                        }
                        if (personaList.get(i).getCnname().equals("身份证正面")) {
                            fillRegistrationFormActivity.idCard();
                            frontImgstr.set(personaList.get(i).getVal());
                        } else if (personaList.get(i).getCnname().equals("身份证反面")) {
                            fillRegistrationFormActivity.idCard();
                            contraryImgstr.set(personaList.get(i).getVal());
                        } else if (personaList.get(i).getCnname().equals("护照")) {
                            fillRegistrationFormActivity.passPort();
                            frontImgstr.set(personaList.get(i).getVal());
                        } else if (personaList.get(i).getCnname().equals("军官证")) {
                            fillRegistrationFormActivity.certificate();
                            frontImgstr.set(personaList.get(i).getVal());
                        }
                        loadImg();
                    }

                    //如果isshow是true 则判断条件，满足加入集合，不满足则不加入
                    if (personaList.get(i).isIsShow()) {
                        if (!personaList.get(i).getCnname().equals("头像") && !personaList.get(i).getCnname().equals("身份证正面") && !personaList.get(i).getCnname().equals("身份证反面") && !personaList.get(i).getCnname().equals("护照") && !personaList.get(i).getCnname().equals("军官证")
                                && !personaList.get(i).getCnname().equals("姓名") && !personaList.get(i).getCnname().equals("性别") && !personaList.get(i).getCnname().equals("手机号码")) {
                            mList.add(personaList.get(i));
                        }
                    }

                }

                if (mList.size() > 0) {
                    formAdapter.loadData(mList);
                }
            }
            return;
        }
        HashMap params = new HashMap();
        params.put("baseMethod", Method.GETFORMMSG);
        if (EmptyUtil.isEmpty(matchCode)) {//判断matchCode是否为空
            return;
        }
        params.put("matchCode", matchCode);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(fillRegistrationFormActivity, params, new RequestCallBack<AthletesMessageBean>() {
            @Override
            public void onSuccess(Response<AthletesMessageBean> response) {
                AthletesMessageBean athletesMessageBean = response.body();
                if (!EmptyUtil.isEmpty(athletesMessageBean)) {
                    int resultCode = athletesMessageBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        AthletesMessageBean.ResultBean resultBean = athletesMessageBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            List<AthletesMessageBean.ResultBean.PropertiesBean> properties = resultBean.getProperties();
                            for (int i = 0; i < properties.size(); i++) {
                                LogUtil.e(i + "");
                                LogUtil.e(properties.size() + "");

                                if (properties.get(i).getCnname() != null && !properties.get(i).getCnname().equals("null") && !properties.get(i).getCnname().equals("")) {

                                    if (properties.get(i).getVal() != null && !properties.get(i).getVal().equals("null") && !properties.get(i).getVal().equals("")) {

                                        if (properties.get(i).getCnname().equals("生日")) {
                                            properties.get(i).setVal("请选择生日");
                                        }
                                    }
                                    //如果isshow是true 则判断条件，满足加入集合，不满足则不加入
                                    if (properties.get(i).isIsShow()) {
                                        if (!properties.get(i).getCnname().equals("头像") && !properties.get(i).getCnname().equals("身份证正面") && !properties.get(i).getCnname().equals("身份证反面") && !properties.get(i).getCnname().equals("护照") && !properties.get(i).getCnname().equals("军官证")
                                                && !properties.get(i).getCnname().equals("姓名") && !properties.get(i).getCnname().equals("性别") && !properties.get(i).getCnname().equals("手机号码")) {
                                            mList.add(properties.get(i));
                                        }
                                    }
                                }

                            }
                            LogUtil.e(properties.size() + "");
                            formAdapter.loadData(mList);
                            formAdapter.notifyDataSetChanged();
                        }
                    }
                }

            }


            @Override
            public AthletesMessageBean parseNetworkResponse(String jsonResult) {
                AthletesMessageBean athletesMessageBean = JSON.parseObject(jsonResult, AthletesMessageBean.class);
                return athletesMessageBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.showTopSnackBar(msg);
                }
            }
        });
    }

    /**
     * 获取证件信息
     */
    public void modifyHeader() {

        PopWindowUtil.selectPicture(fillRegistrationFormActivity, new SelectPicCallBack() {
            @Override
            public void camera(final PopupWindow popupWindow, final int cameraCode) {
                RxPermissionUtil.getPermission(fillRegistrationFormActivity, Manifest.permission.CAMERA, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            IntentUtil.camera(fillRegistrationFormActivity, cameraCode);
                            popupWindow.dismiss();
                        } else {
                            toastUtil.showTopSnackBar(fillRegistrationFormActivity.getResources().getString(R.string.refuse_permission));
                        }
                    }
                });
            }

            @Override
            public void photo(final PopupWindow popupWindow, final int photoCode) {
                RxPermissionUtil.getPermission(fillRegistrationFormActivity, Manifest.permission.READ_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            IntentUtil.photo(fillRegistrationFormActivity, photoCode);
                            popupWindow.dismiss();
                        } else {
                            toastUtil.showTopSnackBar(fillRegistrationFormActivity.getResources().getString(R.string.refuse_permission));
                        }
                    }
                });
            }

            @Override
            public void cancel(PopupWindow popupWindow) {
                popupWindow.dismiss();
            }
        });

    }

    /**
     * 跳转协议界面
     */
    public void form_agreement() {
        IntentUtil.startActivity(fillRegistrationFormActivity,ProtocolActivity.class);
    }

    /**
     * 提交报名信息
     */
    public void nextStep() {
        submitApplyMsg();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.GETUSERINFO);
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl3);

        OkHttpUtil.getRequest(fillRegistrationFormActivity, params, new RequestCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(Response<UserInfoBean> response) {
                UserInfoBean userInfoBean = response.body();
                if (!EmptyUtil.isEmpty(userInfoBean)) {
                    int resultCode = userInfoBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        UserInfoBean.ResultBean resultBean = userInfoBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            register = resultBean.getRegister();
                            registerBeanObservableField.set(register);
                            //真实姓名
                            if (!EmptyUtil.isEmpty(register.getRealName())) {
                                binding.formuserName.setText(register.getRealName());
                            }
                            //手机号
                            if (!EmptyUtil.isEmpty(register.getPhone())) {
                                binding.formuserPhone.setText(register.getPhone());
                            }

                            binding.formuserPhone.setText(register.getPhone());
                            if (register.isIsCard()) {
                                binding.cbCheckbox.setChecked(true);
                                binding.cbSave.setChecked(false);
                                saveStatus.set(false);
                                selectCb();
                                binding.formCard.setVisibility(View.VISIBLE);
                                binding.formUsermessage.setVisibility(View.GONE);
                                if (!EmptyUtil.isEmpty(matchstatus)) {
                                    if (matchstatus.equals("wait_pay")) {//wait_pay
                                        binding.cbCheckbox.setChecked(false);
                                        noselectCb();
                                        binding.formCard.setVisibility(View.GONE);
                                    } else {
                                        binding.cbCheckbox.setChecked(true);
                                        selectCb();
                                    }

                                }
                            } else {
                                binding.cbCheckbox.setChecked(false);
                                noselectCb();
                                binding.cbSave.setChecked(true);
                                saveStatus.set(true);
                                binding.formCard.setVisibility(View.GONE);
                                binding.rlSaveapplycard.setVisibility(View.VISIBLE);
                                binding.formUsermessage.setVisibility(View.VISIBLE);
                            }
                            getFormMsg(matchCode);//获取初始化信息
                        }
                    } else if (resultCode == StatusVariable.NOLOGIN) {
                        IntentUtil.startActivity(fillRegistrationFormActivity, LoginActivity.class);
                    } else if (resultCode == StatusVariable.NOBIND) {
                        IntentUtil.startActivity(fillRegistrationFormActivity, BindPhoneActivity.class);
                    } else {
                        toastUtil.showTopSnackBar(userInfoBean.getResultMsg());
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
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.showTopSnackBar(msg);
                }
            }
        });
    }

    /**
     * 选中时候的逻辑
     */
    public void selectCb() {
        binding.formapplycardBg.setImageResource(R.mipmap.icon_mycard_bg);
        binding.formUsermessage.setVisibility(View.GONE);
        formCode.set("0");
        if (EmptyUtil.isNotEmpty(register)) {
            //真实姓名
            if (!EmptyUtil.isEmpty(register.getRealName())) {
                formRealName.set(registerBeanObservableField.get().getRealName());
            }
            //手机号
            if (!EmptyUtil.isEmpty(registerBeanObservableField.get().getPhone())) {
                formPhoneNumber.set(register.getPhone());
            }
            //性别
            String gender = registerBeanObservableField.get().getGender();
            if (!EmptyUtil.isEmpty(gender)) {
                if (gender.equals(StatusVariable.GENDERBOY)) {
                    sexStr.set("m");
                } else if (gender.equals(StatusVariable.GENDERGIRL)) {
                    sexStr.set("f");
                }
            }
        }
    }

    /**
     * 未选中的逻辑
     */
    public void noselectCb() {
        binding.formapplycardBg.setImageResource(R.mipmap.icon_cardlight);
        binding.formUsermessage.setVisibility(View.VISIBLE);
        formRealName.set("");
        formPhoneNumber.set("");
        formCode.set("");
        sexStr.set("");
    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd) {
            if (!EmptyUtil.isEmpty(loadingDialog)) {
                loadingDialog.dismiss();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == StatusVariable.PHOTOCODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            Uri uriData = data.getData();
            String path = GetPathFromUriUtil.getPath(fillRegistrationFormActivity, uriData);
            picturePath = path;
        } else if (requestCode == StatusVariable.CAMERACODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            String pathForData = Util.getPathForData(fillRegistrationFormActivity, data);
            picturePath = pathForData;
        }
        if (picType == 1) {
            uploadFile(picturePath, "front");//第一个证件
            frontImgstr.set(picturePath);
        } else {
            uploadFile(picturePath, "contract");//第二个证件
            contraryImgstr.set(picturePath);
        }
    }

}