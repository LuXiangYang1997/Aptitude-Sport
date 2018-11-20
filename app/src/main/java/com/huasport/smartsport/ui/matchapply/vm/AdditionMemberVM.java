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
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.huasport.smartsport.BR;
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
import com.huasport.smartsport.databinding.AdditionmemberLayoutBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.rxpermission.RxPermissionUtil;
import com.huasport.smartsport.rxpermission.RxPermissionUtilCallback;
import com.huasport.smartsport.ui.discover.model.ParamsData;
import com.huasport.smartsport.ui.matchapply.adapter.GroupFormAdapter;
import com.huasport.smartsport.ui.matchapply.bean.AddMemberInitializeBean;
import com.huasport.smartsport.ui.matchapply.bean.AdditionMemberBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupOrderMsgBean;
import com.huasport.smartsport.ui.matchapply.bean.UploadBean;
import com.huasport.smartsport.ui.matchapply.view.AdditionMemberActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GetPathFromUri;
import com.huasport.smartsport.util.GetPathFromUriUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ShareUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.util.popwindow.SelectPicCallBack;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.lzy.okgo.callback.AbsCallback;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 陆向阳 on 2018/7/26.
 */
public class AdditionMemberVM extends BaseViewModel implements CounterListener {

    private MyApplication application = MyApplication.getInstance();
    private AdditionMemberActivity additionMemberActivity;
    private final AdditionmemberLayoutBinding binding;
    private GroupFormAdapter groupFormAdapter;
    private final List<AddMemberInitializeBean.ResultBean.PropertiesBean> mList = new ArrayList<>();
    private String matchCode;
    public ObservableField<String> imgOne = new ObservableField<>("");//正面
    public ObservableField<String> imgTwo = new ObservableField<>("");//反面
    private String token;
    private String orderCode;
    private String playerCode;
    private int number;
    private List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> playerBeanList;
    private String type;
    private Counter counter;
    private LoadingDialog loadingDialog;
    private ToastUtil toastUtil;
    private ParamsData paramsData;
    private int picType;
    private String picturePath;
    private boolean params;

    public AdditionMemberVM(AdditionMemberActivity additionMemberActivity, GroupFormAdapter groupformAdapter) {
        this.additionMemberActivity = additionMemberActivity;
        this.groupFormAdapter = groupformAdapter;
        binding = additionMemberActivity.getBinding();
        View view = new View(additionMemberActivity);
        binding.setVariable(BR.view, view);
        init();
        initData();
        initClick();
    }
    /**
     * 初始化
     */
    private void init() {

        matchCode = additionMemberActivity.getIntent().getStringExtra("matchCode");
        orderCode = additionMemberActivity.getIntent().getStringExtra("orderCode");
        playerCode = additionMemberActivity.getIntent().getStringExtra("playerCode");
//        token = (String) SharedPreferencesUtils.getParam(additionMemberActivity, "token", "");
        type = additionMemberActivity.getIntent().getStringExtra("type");
        playerBeanList = (List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean>) additionMemberActivity.getIntent().getSerializableExtra("MotifyBean");

        //初始化Toast
        toastUtil = new ToastUtil(additionMemberActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(additionMemberActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = application.getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {

            token = userBean.getToken();

        }
        paramsData = new ParamsData(additionMemberActivity);

        //弹出加载框
        loadingDialog.show();

    }


    /**
     * 初始化点击事件
     */
    private void initClick() {

        groupFormAdapter.setClick(new GroupFormAdapter.GroupFormClick() {
            @Override
            public void birdthClick(RecyclerView.ViewHolder viewHolder, final int position, int type) {
                TimePickerView optionsPickerView = new TimePickerView.Builder(additionMemberActivity, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        groupFormAdapter.mList.get(position).setVal(Util.getTime(date));
                        groupFormAdapter.notifyDataSetChanged();
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

            @Override
            public void certificateClick(RecyclerView.ViewHolder viewHolder, final int position, int type) {
                final List<String> namelist = new ArrayList<>();
                namelist.add("身份证");
                namelist.add("护照");
                namelist.add("军官证");

                final OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(additionMemberActivity, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {

                        String type = namelist.get(options1);
                        groupFormAdapter.mList.get(position).setVal(type);
                        if (type.equals("身份证")) {
                            additionMemberActivity.idCard();
                        } else {
                            if (type.equals("护照")) {
                                binding.fronttext.setText("请上传护照");
                                additionMemberActivity.passPort();
                            } else if (type.equals("军官证")) {
                                binding.fronttext.setText("请上传军官证");
                                additionMemberActivity.certificate();
                            }

                        }
                        imgOne.set("");
                        imgTwo.set("");
                        groupFormAdapter.notifyDataSetChanged();
                    }
                }).setSubmitColor(Color.BLACK).setCancelColor(Color.BLACK).build();
                optionsPickerView.setPicker(namelist);
                optionsPickerView.show();
            }

            @Override
            public void codeGet(RecyclerView.ViewHolder viewHolder, int position, int type) {

            }
        });


    }

    /**
     * 初始化数据
     */
    private void initData() {

        if (type.equals("motify")) {//修改成员信息
            binding.statusText.setText("保存");
            List propertiesBeanList = new ArrayList();


            for (int i = 0; i < playerBeanList.size(); i++) {
                GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean playersBean = playerBeanList.get(i);

                AddMemberInitializeBean.ResultBean.PropertiesBean propertiesBean = new AddMemberInitializeBean.ResultBean.PropertiesBean();
                propertiesBean.setCnname(playersBean.getCnname());
                propertiesBean.setVal(playersBean.getVal());
                propertiesBean.setType(playersBean.getType());
                propertiesBean.setParams(playersBean.getParams());
                propertiesBean.setIsShow(playersBean.isIsShow());
                propertiesBean.setIsRequired(playersBean.isIsRequired());
                propertiesBean.setAttributeName(playersBean.getAttributeName());

                if (propertiesBean.getCnname() != null && !propertiesBean.getCnname().equals("") && !propertiesBean.getCnname().equals("null")) {

                    if (propertiesBean.getCnname().equals("证件类型")) {
                        if (propertiesBean.getVal().equals(StatusVariable.IDCARD)) {
                            propertiesBean.setVal("身份证");
                            additionMemberActivity.idCard();
                        } else if (propertiesBean.getVal().equals(StatusVariable.PASSCARD)) {
                            propertiesBean.setVal("护照");
                            binding.fronttext.setText("请上传护照");
                            additionMemberActivity.passPort();
                        } else if (propertiesBean.getVal().equals(StatusVariable.CERTIFICATECARD)) {
                            propertiesBean.setVal("军官证");
                            additionMemberActivity.certificate();
                        }
                    }

                    if (propertiesBean.getCnname().equals("身份证正面")) {
                        if (!((String) propertiesBean.getVal()).isEmpty() || propertiesBean.getVal() != null) {
                            GlideUtil.LoadImage(additionMemberActivity, (String) propertiesBean.getVal(), binding.groupFrontImg);
                            additionMemberActivity.getBinding().fronttext.setVisibility(View.GONE);
                            imgOne.set(propertiesBean.getVal());
                        }
                    } else if (propertiesBean.getCnname().equals("身份证反面")) {
                        if (!((String) propertiesBean.getVal()).isEmpty() || propertiesBean.getVal() != null) {
                            GlideUtil.LoadImage(additionMemberActivity, (String) propertiesBean.getVal(), binding.groupContraryImg);
                            additionMemberActivity.getBinding().contrarytext.setVisibility(View.GONE);
                            imgTwo.set(propertiesBean.getVal());
                        }
                    }
                    if (propertiesBean.getCnname().equals("军官证")) {
                        if (!((String) propertiesBean.getVal()).isEmpty() || propertiesBean.getVal() != null) {
                            GlideUtil.LoadImage(additionMemberActivity, (String) propertiesBean.getVal(), binding.groupFrontImg);
                            additionMemberActivity.getBinding().fronttext.setVisibility(View.GONE);
                            imgOne.set(propertiesBean.getVal());

                        }
                    }
                    if (propertiesBean.getCnname().equals("护照")) {
                        if (!((String) propertiesBean.getVal()).isEmpty() || propertiesBean.getVal() != null) {
                            GlideUtil.LoadImage(additionMemberActivity, (String) propertiesBean.getVal(), binding.groupFrontImg);
                            binding.fronttext.setVisibility(View.GONE);
                            imgOne.set(propertiesBean.getVal());
                        }
                    }
                    if (!propertiesBean.getCnname().equals("null") && propertiesBean.getCnname() != null && !propertiesBean.getCnname().equals("头像") && propertiesBean.isIsShow()) {
                        if (!propertiesBean.getCnname().equals("头像") && !propertiesBean.getCnname().equals("身份证正面") && !propertiesBean.getCnname().equals("身份证反面") && !propertiesBean.getCnname().equals("护照") && !propertiesBean.getCnname().equals("军官证")) {
                            propertiesBeanList.add(propertiesBean);
                        }
                    }
                }
            }
            groupFormAdapter.loadData(propertiesBeanList);
            groupFormAdapter.notifyDataSetChanged();
            counter.countDown();
        } else {
            HashMap params = new HashMap();
            params.put("baseMethod", Method.GETFORMMSG);
            if (EmptyUtil.isEmpty(matchCode)) {//判断matchCode是否为空
                return;
            }
            params.put("matchCode", matchCode);
            params.put("t", String.valueOf(System.currentTimeMillis()));
            params.put("baseUrl", Config.baseUrl);


            OkHttpUtil.getRequest(additionMemberActivity, params, new RequestCallBack<AddMemberInitializeBean>() {
                @Override
                public void onSuccess(com.lzy.okgo.model.Response<AddMemberInitializeBean> response) {
                    AddMemberInitializeBean addMemberInitializeBean = response.body();
                    if (!EmptyUtil.isEmpty(addMemberInitializeBean)) {
                        int resultCode = addMemberInitializeBean.getResultCode();
                        if (resultCode == StatusVariable.REQUESTSUCCESS) {
                            AddMemberInitializeBean.ResultBean resultBean = addMemberInitializeBean.getResult();
                            if (!EmptyUtil.isEmpty(resultBean)) {
                                List<AddMemberInitializeBean.ResultBean.PropertiesBean> properties = resultBean.getProperties();
                                for (int i = 0; i < properties.size(); i++) {

                                    if (properties.get(i).getCnname() != null && !properties.get(i).getCnname().equals("") && !properties.get(i).getCnname().equals("null")) {
                                        if (properties.get(i).getVal() != null && !properties.get(i).getVal().equals("null") && !properties.get(i).getVal().equals("")) {
                                            if (properties.get(i).getCnname().equals("证件类型")) {
                                                if (properties.get(i).getVal().equals(StatusVariable.IDCARD)) {
                                                    properties.get(i).setVal("身份证");
                                                    additionMemberActivity.idCard();
                                                } else if (properties.get(i).getVal().equals(StatusVariable.PASSCARD)) {
                                                    properties.get(i).setVal("护照");
                                                    binding.fronttext.setText("请上传护照");
                                                    additionMemberActivity.passPort();
                                                } else if (properties.get(i).getVal().equals(StatusVariable.CERTIFICATECARD)) {
                                                    properties.get(i).setVal("军官证");
                                                    additionMemberActivity.certificate();
                                                }
                                            }

                                            if (properties.get(i).getCnname().equals("身份证正面")) {
                                                if (!((String) properties.get(i).getVal()).isEmpty() || properties.get(i).getVal() != null) {
                                                    GlideUtil.LoadImage(additionMemberActivity, (String) properties.get(i).getVal(), binding.groupFrontImg);
                                                    additionMemberActivity.getBinding().fronttext.setVisibility(View.GONE);
                                                }
                                            } else if (properties.get(i).getCnname().equals("身份证反面")) {
                                                if (!((String) properties.get(i).getVal()).isEmpty() || properties.get(i).getVal() != null) {
                                                    GlideUtil.LoadImage(additionMemberActivity, (String) properties.get(i).getVal(), binding.groupContraryImg);
                                                    additionMemberActivity.getBinding().contrarytext.setVisibility(View.GONE);
                                                }
                                            }
                                            if (properties.get(i).getCnname().equals("军官证")) {
                                                if (!((String) properties.get(i).getVal()).isEmpty() || properties.get(i).getVal() != null) {
                                                    GlideUtil.LoadImage(additionMemberActivity, (String) properties.get(i).getVal(), binding.groupFrontImg);
                                                    additionMemberActivity.getBinding().fronttext.setVisibility(View.GONE);
                                                }
                                            }
                                            if (properties.get(i).getCnname().equals("护照")) {
                                                if (!((String) properties.get(i).getVal()).isEmpty() || properties.get(i).getVal() != null) {
                                                    GlideUtil.LoadImage(additionMemberActivity, (String) properties.get(i).getVal(), binding.groupFrontImg);
                                                    binding.fronttext.setVisibility(View.GONE);
                                                }
                                            }
                                        }

                                        //如果是true，判断cName是否为空如果为空则直接删除,false也直接删除
                                        if (!properties.get(i).getCnname().equals("null") && properties.get(i).getCnname() != null && !properties.get(i).getCnname().equals("头像") && properties.get(i).isIsShow()) {
                                            if (!properties.get(i).getCnname().equals("头像")) {
                                                mList.add(properties.get(i));
                                            }
                                        }
                                    }

                                }
                                Log.e("PropertiesSize", properties.size() + "");
                                groupFormAdapter.loadData(mList);
                                groupFormAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                }

                @Override
                public AddMemberInitializeBean parseNetworkResponse(String jsonResult) {
                    AddMemberInitializeBean addMemberInitializeBean = JSON.parseObject(jsonResult, AddMemberInitializeBean.class);
                    return addMemberInitializeBean;
                }

                @Override
                public void onFailed(int code, String msg) {
                    if (!EmptyUtil.isEmpty(msg)) {
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

    }

    /**
     * 确认
     */
    private void submitsure() {

        HashMap<String, String> params = groupFormAdapter.getParams();
        if (!params.containsKey("attOne")) {
            params.put("attOne", imgOne.get().equals("") ? "" : imgOne.get());
        }
        if (!params.containsKey("attTwo")) {
            params.put("attTwo", imgTwo.get().equals("") ? "" : imgTwo.get());
        }
        params.put("baseMethod", Method.ADDMEMBER);
        params.put("matchCode", matchCode);
        params.put("orderCode", orderCode);
        params.put("playerCode", playerCode);
//        params.put("verifyCode", groupFormAdapter.getCode() == null ? "" : groupFormAdapter.getCode());
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.postRequest(additionMemberActivity, params, new RequestCallBack<AdditionMemberBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<AdditionMemberBean> response) {
                AdditionMemberBean additionMemberBean = response.body();
                if (!EmptyUtil.isEmpty(additionMemberBean)){
                    int resultCode = additionMemberBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        Intent intent = new Intent();
                        intent.putExtra("MemberBean", (Serializable) additionMemberBean);
                        if (type.equals("motify")) {
                            additionMemberActivity.setResult(StatusVariable.MODIFYMEMMBER, intent);
                        } else {
                            additionMemberActivity.setResult(StatusVariable.ADDITIONMEMBER, intent);
                        }
                        additionMemberActivity.finish();
                    }
                }
            }

            @Override
            public AdditionMemberBean parseNetworkResponse(String jsonResult) {
                AdditionMemberBean additionMemberBean = JSON.parseObject(jsonResult, AdditionMemberBean.class);
                return additionMemberBean;
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
     * 获取图片
     */
    public void front(){
        picType = 1;
        modifyPicMsg();
    }

    /**
     * 获取图片
     */
    public void behind(){

        picType = 2;
        modifyPicMsg();
        
        
    }

    /**
     * 获取图片提示框
     */
    private void modifyPicMsg() {

        PopWindowUtil.selectPicture(additionMemberActivity, new SelectPicCallBack() {
            @Override
            public void camera(final PopupWindow popupWindow, final int cameraCode) {
                RxPermissionUtil.getPermission(additionMemberActivity, Manifest.permission.CAMERA, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            IntentUtil.camera(additionMemberActivity, cameraCode);
                            popupWindow.dismiss();
                        } else {
                            toastUtil.showTopSnackBar(additionMemberActivity.getResources().getString(R.string.refuse_permission));
                        }
                    }
                });
            }

            @Override
            public void photo(final PopupWindow popupWindow, final int photoCode) {
                RxPermissionUtil.getPermission(additionMemberActivity, Manifest.permission.READ_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            IntentUtil.photo(additionMemberActivity, photoCode);
                            popupWindow.dismiss();
                        } else {
                            toastUtil.showTopSnackBar(additionMemberActivity.getResources().getString(R.string.refuse_permission));
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
     * 提交
     */
    public void submit(){

        if (!type.equals("motify")) {
            params = paramsData.groupParams(groupFormAdapter.mList, AdditionMemberVM.this);

            if (!params) {
                return;
            }

            BaseDialog.show(additionMemberActivity, "提示", "请您确认信息无误后提交报名", "确定", "取消", false, false,
                    0, new DialogCallBack() {
                        @Override
                        public void submit(CustomDialog.Builder customDialog) {
                            submitsure();
                        }

                        @Override
                        public void cancel(CustomDialog.Builder customDialog) {
                            customDialog.dismiss();
                        }
                    });

        } else {
            submitsure();
        }

    }

    /**
     * 上传证件
     * @param filepath
     * @param type
     */
    private void uploadFile(String filepath, final String type) {
        HashMap params = new HashMap();
        params.put("baseMethod", Method.UPLOAD_FILE);
        params.put("file", filepath);
        params.put("t", String.valueOf(System.currentTimeMillis()));
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.uploadFile(additionMemberActivity, params, new RequestCallBack<UploadBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<UploadBean> response) {
                UploadBean uploadBean = response.body();
                if (!EmptyUtil.isEmpty(uploadBean)) {
                    int resultCode = uploadBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        HashMap<String, String> params = groupFormAdapter.getParams();
                        if (uploadBean.getResult().getUrl() != null && !EmptyUtil.isEmpty(uploadBean.getResult().getUrl())) {
                            if (type.equals("front")) {//第一个证件
                              imgOne.set(uploadBean.getResult().getUrl());
                              binding.fronttext.setVisibility(View.GONE);
                              GlideUtil.LoadImage(additionMemberActivity, imgOne.get().toString(), binding.groupFrontImg);
                              params.put("attOne", uploadBean.getResult().getUrl());//放入HashMap中
                            } else if (type.equals("contract")) {//第二个证件
                                params.put("attTwo", uploadBean.getResult().getUrl());
                                    imgTwo.set(uploadBean.getResult().getUrl());
                                    GlideUtil.LoadImage(additionMemberActivity, imgTwo.get().toString(), binding.groupContraryImg);
                                    binding.contrarytext.setVisibility(View.GONE);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == StatusVariable.PHOTOCODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            Uri uriData = data.getData();
            String path = GetPathFromUriUtil.getPath(additionMemberActivity, uriData);
            picturePath = path;
        } else if (requestCode == StatusVariable.CAMERACODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            String pathForData = Util.getPathForData(additionMemberActivity, data);
            picturePath = pathForData;
        }
        if (picType == 1) {
            uploadFile(picturePath, "front");//第一个证件
        } else {
            uploadFile(picturePath, "contract");//第二个证件
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
}
