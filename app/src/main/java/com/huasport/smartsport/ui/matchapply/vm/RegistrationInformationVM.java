package com.huasport.smartsport.ui.matchapply.vm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.ActivityRegistrationInformationBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.rxpermission.RxPermissionUtil;
import com.huasport.smartsport.rxpermission.RxPermissionUtilCallback;
import com.huasport.smartsport.ui.discover.model.ParamsData;
import com.huasport.smartsport.ui.matchapply.adapter.SignPersonalAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.SignUpAdapter;
import com.huasport.smartsport.ui.matchapply.bean.CancelResultBean;
import com.huasport.smartsport.ui.matchapply.bean.CompleteMessageBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupEventsBean;
import com.huasport.smartsport.ui.matchapply.bean.PersonalInfoBean;
import com.huasport.smartsport.ui.matchapply.bean.RegistrationInfoBean;
import com.huasport.smartsport.ui.matchapply.bean.UploadBean;
import com.huasport.smartsport.ui.matchapply.view.PayMentOrderActivty;
import com.huasport.smartsport.ui.matchapply.view.RegistrationInformationActivity;
import com.huasport.smartsport.ui.matchapply.view.SuccessPaymentInfoActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GetPathFromUriUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.util.popwindow.SelectPicCallBack;
import com.lzy.okgo.model.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 陆向阳 on 2018/6/26.
 */
public class RegistrationInformationVM extends BaseViewModel implements CounterListener {

    private MyApplication application = MyApplication.getInstance();
    private RegistrationInformationActivity mActivity;
    private List<PersonalInfoBean> infoList = new ArrayList<>();
    private List<GroupEventsBean.ResultBean.GroupsBean.EventsBean> eventList = new ArrayList<>();
    private String orderCode, orderStatus;
    private SignUpAdapter signUpAdapter;
    private SignPersonalAdapter signPersonalAdapter;
    //上传证明
    public ObservableField<String> frontImgstr = new ObservableField<>("");
    //上传个人照片
    public ObservableField<String> contraryImgstr = new ObservableField<>("");
    private String token;
    private ActivityRegistrationInformationBinding binding;
    public ObservableField<String> orderStatusStr = new ObservableField<>("");//存储订单状态
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private int picType = 1;
    private String picturePath;
    private ParamsData paramsData;

    //更新UI
    private Handler mHandler = new Handler();


    public RegistrationInformationVM(RegistrationInformationActivity mActivity, SignUpAdapter signUpAdapter, SignPersonalAdapter signPersonalAdapter) {
        this.mActivity = mActivity;
        this.signUpAdapter = signUpAdapter;
        this.signPersonalAdapter = signPersonalAdapter;
        binding = mActivity.getBinding();
        init();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(mActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(mActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        paramsData = new ParamsData(mActivity);
        //弹出加载框
        loadingDialog.show();


    }

    @Override
    public void onCreate() {
        super.onCreate();
        initView();
        initData();
    }

    /**
     * 提交报名信息
     */
    public void submit() {
        submitInfo();
    }


    /**
     * 取消订单
     */
    public void cancelApplyOrder(){

        BaseDialog.show(mActivity, "提示", "您确定要取消报名吗?", "确定", "取消", false, false,
                0, new DialogCallBack() {
                    @Override
                    public void submit(CustomDialog.Builder customDialog) {
                        cancelGroupApply();
                        customDialog.dismiss();
                    }

                    @Override
                    public void cancel(CustomDialog.Builder customDialog) {
                        customDialog.dismiss();
                    }
                });
    }


    private void initView() {

        signPersonalAdapter.setOnClickListener(new SignPersonalAdapter.onClick() {

            /**
             * 生日点击事件
             * */
            @Override
            public void birthdayOnClick(RecyclerView.ViewHolder viewHolder, final int position, final int type) {
                TimePickerView optionsPickerView = new TimePickerView.Builder(mActivity, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        signPersonalAdapter.mList.get(position).setVal(Util.getTime(date));
                        signPersonalAdapter.notifyDataSetChanged();
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

            /**
             * 证件类型点击事件
             * */
            @Override
            public void certificateClick(RecyclerView.ViewHolder viewHolder, final int position, final int type) {
                final List<String> namelist = new ArrayList<>();
                namelist.add("身份证");
                namelist.add("护照");
                namelist.add("军官证");

                final OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {

                        String type = namelist.get(options1);

                        if (type.equals("身份证")) {
                            mActivity.idCard();
                        } else {
                            if (type.equals("护照")) {
                                mActivity.passPort();
                            } else if (type.equals("军官证")) {
                                mActivity.certificate();
                            }
                        }
                        signPersonalAdapter.mList.get(position).setVal(type);
                        signPersonalAdapter.notifyDataSetChanged();
                    }
                }).setSubmitColor(Color.BLACK).setCancelColor(Color.BLACK).build();
                optionsPickerView.setPicker(namelist);
                optionsPickerView.setSelectOptions(0);
                optionsPickerView.show();
            }

            /**手机验证码*/
            @Override
            public void codeGet(RecyclerView.ViewHolder viewHolder, int position, int type) {
                getCode((TextView) viewHolder.itemView.findViewById(R.id.send_Authcode), position);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        orderCode = mActivity.getIntent().getStringExtra("orderCode");//订单ID
        orderStatus = mActivity.getIntent().getStringExtra("orderStatus");//订单状态
        HashMap params = new HashMap<String, String>();
        params.put("token", token);//token
        params.put("orderCode", orderCode);//订单ID
        params.put("baseMethod", Method.SIGININFO);
        params.put("baseUrl", Config.baseUrl);
        params.put("t", String.valueOf(System.currentTimeMillis()));
        Log.e("报名待完善params====>>", params.toString());

        OkHttpUtil.getRequest(mActivity, params, new RequestCallBack<RegistrationInfoBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<RegistrationInfoBean> response) {
                RegistrationInfoBean registrationInfoBean = response.body();
                if (!EmptyUtil.isEmpty(registrationInfoBean)){
                    int resultCode = registrationInfoBean.getResultCode();
                    if (resultCode ==StatusVariable.REQUESTSUCCESS){
                        RegistrationInfoBean.ResultBean resultBean = registrationInfoBean.getResult();
                            if (!EmptyUtil.isEmpty(resultBean)){

                                orderStatusStr.set(registrationInfoBean.getResult().getApplyStatus());

                                initOrderStatus();


                              binding.setOrderBean(registrationInfoBean.getResult().getOrderDetail());
                                long orderTime = registrationInfoBean.getResult().getOrderDetail().getOrderTime();
                                Date date = new Date(orderTime);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String format = simpleDateFormat.format(date);
                                mActivity.getBinding().applyOrderDate.setText(format);

                                mActivity.getBinding().tvSignAmount.setText("" + registrationInfoBean.getResult().getOrderDetail().getOrderAmountStr());//订单总金额
                                signUpAdapter.loadMoreData(registrationInfoBean.getResult().getOrderDetail().getApplys());//报名项目数据

                                /**提交信息成功，跳转支付需要带过去的参数*/
                                //   List<RegistrationInfoBean.ResultBean.OrderDetailBean.ApplysBean> applyList = registrationInfoBean.getResult().getOrderDetail().getApplys();
//                        for (int a = 0; a < applyList.size(); a++) {
//                            GroupEventsBean.ResultBean.GroupsBean.EventsBean eventBean = new GroupEventsBean.ResultBean.GroupsBean.EventsBean();
//                            ProgramMessageBean programMessageBean = new ProgramMessageBean();
//                            eventBean.setEntryFeeStr(applyList.get(a).getApplyAmountStr());
//                            SharedPreferencesUtils.setParam(mActivity, "feestr", applyList.get(a).getApplyAmountStr());
//                            programMessageBean.setGroupName((String) applyList.get(a).getMatchGroupName());
//                            programMessageBean.setProgramName(applyList.get(a).getEventName());
//                            eventBean.setProgramMessageBean(programMessageBean);
//                            eventList.add(eventBean);
//                        }

                                /**
                                 * 个人信息数据：
                                 * */
                                List<RegistrationInfoBean.ResultBean.OrderDetailBean.TeamsBean> personaList = registrationInfoBean.getResult().getOrderDetail().getTeams();
                                //身份证图片
                                for (int i = 0; i < personaList.size(); i++) {
                                    List<RegistrationInfoBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> players = personaList.get(i).getPlayers();
                                    for (int j = 0; j < players.size(); j++) {
                                        if (!EmptyUtil.isEmpty(players.get(j))) {
                                            if (!EmptyUtil.isEmpty(players.get(j).getVal())) {
                                                if (!EmptyUtil.isEmpty(players.get(j).getVal())) {

                                                    if (players.get(j).getCnname().equals("证件类型")) {
                                                        if (players.get(j).getVal() != null) {
                                                            if (players.get(j).getVal().equals("1")) {
                                                                players.get(j).setVal("身份证");
                                                                mActivity.idCard();
                                                            } else if (players.get(j).getVal().equals("2")) {
                                                                players.get(j).setVal("护照");
                                                                mActivity.passPort();
                                                            } else if (players.get(j).getVal().equals("4")) {
                                                                players.get(j).setVal("军官证");
                                                                mActivity.certificate();
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    players.get(j).setVal("");
                                                }
                                            } else {
                                                players.get(j).setVal("");
                                            }
                                        }

                                        if (players.get(j).getCnname().equals("护照") || players.get(j).getCnname().equals("军官证")) {
                                            if (!EmptyUtil.isEmpty(players.get(j).getVal().toString())) {
                                                // if (!players.get(j).getVal().toString().isEmpty() || players.get(j).getVal() != null) {
                                                GlideUtil.LoadImage(mActivity, (String) players.get(j).getVal(), mActivity.getBinding().frontImageview);
                                                mActivity.getBinding().fronttext.setVisibility(View.GONE);
                                                frontImgstr.set((String) players.get(j).getVal());
                                            }
                                        }
                                        if (players.get(j).getCnname().equals("身份证正面")) {
                                            if (!EmptyUtil.isEmpty(players.get(j).getVal().toString())) {
                                                // if (!players.get(j).getVal().toString().isEmpty() || players.get(j).getVal() != null) {
                                                GlideUtil.LoadImage(mActivity, (String) players.get(j).getVal(), mActivity.getBinding().frontImageview);
                                                mActivity.getBinding().fronttext.setVisibility(View.GONE);
                                                frontImgstr.set((String) players.get(j).getVal());
                                            }
                                        } else if (players.get(j).getCnname().equals("身份证反面")) {
                                            if (!EmptyUtil.isEmpty(players.get(j).getVal().toString())) {
                                                // if (!players.get(j).getVal().toString().isEmpty() || players.get(j).getVal() != null) {
                                                GlideUtil.LoadImage(mActivity, (String) players.get(j).getVal(), mActivity.getBinding().contractImage);
                                                mActivity.getBinding().contrarytext.setVisibility(View.GONE);
                                                contraryImgstr.set((String) players.get(j).getVal());
                                            }
                                        }
                                        boolean isShow = players.get(j).isIsShow();
                                        if (isShow == true) {
                                            PersonalInfoBean info = new PersonalInfoBean();
                                            info.setAttributeName(players.get(j).getAttributeName());
                                            info.setCnname(players.get(j).getCnname());
                                            info.setParams(players.get(j).getParams());
                                            info.setRequired(players.get(j).isIsRequired());
                                            info.setShow(players.get(j).isIsShow());
                                            info.setType(players.get(j).getType());
                                            info.setVal((String) players.get(j).getVal());

                                            if (!players.get(j).getCnname().equals("身份证正面") && !players.get(j).getCnname().equals("身份证反面") && !players.get(j).getCnname().equals("护照") && !players.get(j).getCnname().equals("军官证") && !players.get(j).getCnname().equals("头像")) {
                                                infoList.add(info);
                                            }
                                        }
                                    }

                                }
                                signPersonalAdapter.loadData(infoList);//个人信息数据
                            }
                    }else if (resultCode == StatusVariable.NOLOGIN){
                        IntentUtil.startActivity(mActivity,LoginActivity.class);
                    }else if (resultCode == StatusVariable.NOBIND){
                        IntentUtil.startActivity(mActivity,BindPhoneActivity.class);
                    }


                }
            }

            @Override
            public RegistrationInfoBean parseNetworkResponse(String jsonResult) {
                RegistrationInfoBean registrationInfoBean = JSON.parseObject(jsonResult, RegistrationInfoBean.class);
                return registrationInfoBean;
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
     * 提交个人信息
     */

    private void submitInfo() {
        boolean params = paramsData.getParam(infoList, this);
        if (!params) {
            return;
        }

        if (!Util.isPhoneNumber(signPersonalAdapter.getPhoneNum())) {
            toastUtil.centerToast( "请输入正确的手机号码");
            return;
        }

        if (EmptyUtil.isEmpty(signPersonalAdapter.getCode())) {
            toastUtil.centerToast( "请输入验证码");
            return;
        }
        final HashMap completeOrderParams = signPersonalAdapter.getParam();

        if (!completeOrderParams.containsKey("attOne")) {
            completeOrderParams.put("attOne", frontImgstr.get());
        }
        if (!completeOrderParams.containsKey("attTwo")) {
            completeOrderParams.put("attTwo", contraryImgstr.get());
        }
        completeOrderParams.put("baseMethod", Method.COMPLETEMSG);
        completeOrderParams.put("orderCode", orderCode);
        completeOrderParams.put("verifyCode", signPersonalAdapter.getCode().trim());
        completeOrderParams.put("token", token);
        completeOrderParams.put("baseUrl", Config.baseUrl);
        completeOrderParams.put("t", String.valueOf(System.currentTimeMillis()));
        Log.e("提交个人信息Params===>>> ", completeOrderParams.toString());
        OkHttpUtil.postRequest(mActivity, completeOrderParams, new RequestCallBack<CompleteMessageBean>() {
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
                                Intent intent = new Intent(mActivity, SuccessPaymentInfoActivity.class);
                                intent.putExtra("orderCode", orderCode);
                                intent.putExtra("orderType", StatusVariable.APPLYSUCCESS);
                                intent.putExtra("orderStatus", StatusVariable.ORDERSUCCESS);
                                mActivity.startActivity(intent);
                            } else {
                                Intent intent = new Intent(mActivity, PayMentOrderActivty.class);//支付
                                intent.putExtra("orderCode", orderCode);//orderCode
                                mActivity.startActivity(intent);
                            }
                        }

                    } else if (resultCode == StatusVariable.NOLOGIN) {
                        IntentUtil.startActivity(mActivity, LoginActivity.class);
                    } else if (resultCode == StatusVariable.NOBIND) {
                        IntentUtil.startActivity(mActivity, BindPhoneActivity.class);
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
     * 取消订单
     */
    private void cancelGroupApply() {

        HashMap params = new HashMap<>();
        params.put("token", token);//token
        params.put("orderCode", orderCode);//订单ID
        params.put("baseMethod", Method.CANCELORDER);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(mActivity, params, new RequestCallBack<CancelResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<CancelResultBean> response) {
                CancelResultBean cancelResultBean = response.body();
                if (!EmptyUtil.isEmpty(cancelResultBean)){
                    int resultCode = cancelResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        toastUtil.centerToast("取消报名成功");
                        mActivity.finish();
                    }else{
                        toastUtil.centerToast("取消报名失败");
                    }
                }
            }

            @Override
            public CancelResultBean parseNetworkResponse(String jsonResult) {
                CancelResultBean cancelResultBean = JSON.parseObject(jsonResult, CancelResultBean.class);
                return cancelResultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    private int number;

    /**
     * 个人信息验证码
     */
    private void getCode(final TextView textView, int position) {
        number = 60;
        HashMap params = new HashMap();


        if (EmptyUtil.isEmpty(signPersonalAdapter.getPhoneNum())) {
            toastUtil.centerToast("手机号不能为空");
            return;
        }

        if (!Util.isPhoneNumber(signPersonalAdapter.getPhoneNum())) {
            toastUtil.centerToast("请输入正确的手机号码");
            return;
        }

        params.put("phoneNum", signPersonalAdapter.getPhoneNum().trim());
        params.put("baseMethod", Method.GETVERTIFYCODE);
        params.put("baseUrl", Config.baseUrl);
        Log.e("Params", params.toString());

        OkHttpUtil.getRequest(mActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        //60秒倒计时
                        final Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                if (number == 0) {
                                    number = 60;
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            textView.setText("获取验证码");
                                            textView.setClickable(true);
                                            timer.cancel();
                                        }
                                    });
                                } else {
                                    number--;
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            textView.setText(number + "秒");
                                            textView.setClickable(false);
                                        }
                                    });

                                }
                            }
                        };
                        timer.schedule(timerTask, 1000, 1000);
                    }else{
                       toastUtil.centerToast(resultBean.getResultMsg());
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

        OkHttpUtil.uploadFile(mActivity, params, new RequestCallBack<UploadBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<UploadBean> response) {
                UploadBean uploadBean = response.body();
                if (!EmptyUtil.isEmpty(uploadBean)) {
                    int resultCode = uploadBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        HashMap<String, String> params = signPersonalAdapter.getParam();
                        if (uploadBean.getResult().getUrl() != null && !EmptyUtil.isEmpty(uploadBean.getResult().getUrl())) {
                            if (type.equals("front")) {//第一个证件
                                if (!frontImgstr.get().isEmpty()) {
                                    binding.fronttext.setVisibility(View.GONE);
                                    GlideUtil.LoadImage(mActivity, frontImgstr.get().toString(), binding.frontImageview);
                                }
                                params.put("attOne", uploadBean.getResult().getUrl());//放入HashMap中
                            } else if (type.equals("contract")) {//第二个证件
                                params.put("attTwo", uploadBean.getResult().getUrl());
                                if (!contraryImgstr.get().isEmpty()) {
                                    GlideUtil.LoadImage(mActivity, contraryImgstr.get().toString(), binding.contractImage);
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

    //加载图片
    public void loadImg() {
        //加载选中的图片，并将Text隐藏
        if (!frontImgstr.get().isEmpty()) {
            mActivity.getBinding().fronttext.setVisibility(View.GONE);
            GlideUtil.LoadImage(mActivity, frontImgstr.get().toString(), mActivity.getBinding().frontImageview);
        }
        if (!contraryImgstr.get().isEmpty()) {
            GlideUtil.LoadImage(mActivity, contraryImgstr.get().toString(), mActivity.getBinding().contractImage);
            mActivity.getBinding().contrarytext.setVisibility(View.GONE);
        }
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
     * 获取证件信息
     */
    public void modifyHeader() {

        PopWindowUtil.selectPicture(mActivity, new SelectPicCallBack() {
            @Override
            public void camera(final PopupWindow popupWindow, final int cameraCode) {
                RxPermissionUtil.getPermission(mActivity, Manifest.permission.CAMERA, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            IntentUtil.camera(mActivity, cameraCode);
                            popupWindow.dismiss();
                        } else {
                            toastUtil.showTopSnackBar(mActivity.getResources().getString(R.string.refuse_permission));
                        }
                    }
                });
            }

            @Override
            public void photo(final PopupWindow popupWindow, final int photoCode) {
                RxPermissionUtil.getPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            IntentUtil.photo(mActivity, photoCode);
                            popupWindow.dismiss();
                        } else {
                            toastUtil.showTopSnackBar(mActivity.getResources().getString(R.string.refuse_permission));
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

    /*
     * 订单详情状态
     * */
    private void initOrderStatus() {

        switch (orderStatusStr.get()) {
            case StatusVariable.MATCH_OVER:
                binding.llInfoBottom.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("比赛已结束");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#A0A0A0"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.APPLY_ABORT:
                binding.llInfoBottom.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("报名已截止");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#B0B0B0"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.PEOPLENUM_FULL:
                binding.llInfoBottom.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("人数已满");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#FFCA00"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.PAUSE_APPLY:
                binding.llInfoBottom.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("暂停报名");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#C1C1C1"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.APPLY:
                binding.llInfoBottom.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setText("报名");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#FF8F00"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
        }

    }


    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if (!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == StatusVariable.PHOTOCODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            Uri uriData = data.getData();
            String path = GetPathFromUriUtil.getPath(mActivity, uriData);
            picturePath = path;
        } else if (requestCode == StatusVariable.CAMERACODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            String pathForData = Util.getPathForData(mActivity, data);
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
