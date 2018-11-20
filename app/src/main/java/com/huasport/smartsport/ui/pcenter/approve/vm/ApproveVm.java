package com.huasport.smartsport.ui.pcenter.approve.vm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import com.huasport.smartsport.databinding.PersonalApprovelayoutBinding;
import com.huasport.smartsport.rxpermission.RxPermissionUtil;
import com.huasport.smartsport.rxpermission.RxPermissionUtilCallback;
import com.huasport.smartsport.ui.discover.bean.ApproveBean;
import com.huasport.smartsport.ui.pcenter.approve.bean.PersonalApproveBean;
import com.huasport.smartsport.ui.pcenter.approve.bean.VoucherBean;
import com.huasport.smartsport.ui.pcenter.approve.view.ApproveActivity;
import com.huasport.smartsport.ui.pcenter.approve.view.ApproveResultActivity;
import com.huasport.smartsport.ui.pcenter.approve.view.PcenterApproveActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.RegexUtils;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.UriTofilePath;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.util.popwindow.SelectPicCallBack;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class ApproveVm extends BaseViewModel implements CounterListener {

    private ApproveActivity approveActivity;
    private PopupWindow popupWindow;
    private PersonalApprovelayoutBinding binding;
    private ImageView img;
    private int imgtrench;//图片来源
    private boolean passwordfront = false;//身份证正面
    private boolean passwordcontract = false;//身份证反面
    private boolean passwordhand = false;//手持身份证照
    private boolean firmImg = false;//企业照
    private String path;
    private String passwordFrontId = "";//身份证正面Id
    private String passwordcontractId = "";//身份证反面Id
    private String passwordhandId = "";//手持身份证照Id
    private String firmImgId = "";//企业照Id
    private String token;
    private ApproveBean.ResultBean.AuthBean authBean;
    private Intent intent;
    private MyApplication application = MyApplication.getInstance();
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private String registerCode;


    public ApproveVm(ApproveActivity approveActivity) {
        this.approveActivity = approveActivity;
        binding = approveActivity.getBinding();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(approveActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(approveActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = application.getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {

            token = userBean.getToken();
            registerCode = userBean.getRegisterCode();
        }
        //弹出加载框
        loadingDialog.show();

    }

    //个人身份证正面
    public void frontImg() {
        changeHeader(StatusVariable.PCENTERAPPROVEFRONT);
    }

    //个人身份证反面
    public void contractImg() {
        changeHeader(StatusVariable.PCENTERAPPROVECONTRACT);

    }

    //手持身份证
    public void handImg() {
        changeHeader(StatusVariable.PCENTERAPPROVEHAND);

    }

    //企业照
    public void firmImg() {

        changeHeader(StatusVariable.FIRMAPPROVEIMG);
    }

    //弹出框
    public void changeHeader(final int code) {

        PopWindowUtil.selectPicture(approveActivity, new SelectPicCallBack() {
            @Override
            public void camera(final PopupWindow popupWindow, int cameraCode) {

                RxPermissionUtil.getPermission(approveActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            imgtrench = 1;
                            getPassWord(StatusVariable.CAMERACODE, code);
                            popupWindow.dismiss();
                        }
                    }
                });

            }

            @Override
            public void photo(final PopupWindow popupWindow, int photoCode) {
                RxPermissionUtil.getPermission(approveActivity, Manifest.permission.READ_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            imgtrench = 0;
                            getPassWord(StatusVariable.PHOTOALBUM, code);
                            popupWindow.dismiss();
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
     * 获取证件照
     * */
    private void getPassWord(int type, int code) {
        if (type == StatusVariable.CAMERACODE) {
            //拍照
            IntentUtil.openCamera(approveActivity, code);

        } else if (type == StatusVariable.PHOTOALBUM) {
            //相册
            IntentUtil.photo(approveActivity, code);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (imgtrench == StatusVariable.PHOTOALBUM && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            Uri headerUri = data.getData();
            //uri转换路径
            path = UriTofilePath.getFilePathByUri(approveActivity, headerUri);
        } else if (imgtrench == StatusVariable.CAMERACODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            Bundle bundle = data.getExtras();
            // 转换图片的二进制流
            Bitmap bitmap = (Bitmap) bundle.get("data");
            File file = new File(approveActivity.getCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
            path = Util.saveMyBitmap(file.getName(), bitmap);
        } else {
            return;
        }
        upload(requestCode, path);
    }

    //上传照片和展示
    private void initPhoto(int resultCode, String path) {

        if (resultCode == StatusVariable.PCENTERAPPROVEFRONT) {
            img = binding.imgfront;
            binding.tvFront.setVisibility(View.GONE);
            binding.tvFronttip.setVisibility(View.GONE);
            passwordfront = true;
        } else if (resultCode == StatusVariable.PCENTERAPPROVECONTRACT) {
            img = binding.imgContrary;
            binding.tvContrary.setVisibility(View.GONE);
            binding.tvContracttip.setVisibility(View.GONE);
            passwordcontract = true;
        } else if (resultCode == StatusVariable.PCENTERAPPROVEHAND) {
            img = binding.imgHand;
            binding.tvHandtip.setVisibility(View.GONE);
            passwordhand = true;
        } else if (resultCode == StatusVariable.FIRMAPPROVEIMG) {
            img = binding.imgFirm;
            binding.tvFirmtip.setVisibility(View.GONE);
            firmImg = true;
        }
        GlideUtil.LoadImage(approveActivity, path, img);
    }

    //下一步
    public void nextStep() {
        int selectedTabPosition = binding.approveTablayout.getSelectedTabPosition();
        if (binding.approveTablayout.getTabAt(selectedTabPosition).getText().equals(StatusVariable.PCENTERAPPROVE)) {
            PersonalApproveBean personalApproveBean = new PersonalApproveBean();
            if (EmptyUtil.isEmpty(binding.etRealName.getText().toString())) {
                toastUtil.centerToast("真实姓名不能为空");
                return;
            } else {
                personalApproveBean.setRealName(binding.etRealName.getText().toString());
            }
            if (EmptyUtil.isEmpty(binding.etPassword.getText().toString())) {
                toastUtil.centerToast("身份证号不能为空");
                return;
            }
            if (!RegexUtils.isIDCard18(binding.etPassword.getText())) {
                toastUtil.centerToast("请输入正确的身份证号");
                return;
            } else {
                personalApproveBean.setPassword(binding.etPassword.getText().toString());
            }

            if (!passwordfront) {
                toastUtil.centerToast("请上传身份证正面照片");
                return;
            } else {
                personalApproveBean.setImgurlFront(passwordFrontId);
            }

            if (!passwordcontract) {
                toastUtil.centerToast("请上传身份证反面照片");
                return;
            } else {
                personalApproveBean.setImgurlContract(passwordcontractId);
            }
            if (!passwordhand) {
                toastUtil.centerToast("请上传手持身份证照片");
                return;
            } else {
                personalApproveBean.setImgurlHand(passwordhandId);
            }
            Intent intent = new Intent(approveActivity, PcenterApproveActivity.class);
            intent.putExtra("personalApproveBean", (Serializable) personalApproveBean);
            approveActivity.startActivity(intent);
        } else if (binding.approveTablayout.getTabAt(selectedTabPosition).getText().equals(StatusVariable.FIRMAPPROVE)) {

            if (EmptyUtil.isEmpty(binding.etFirmName.getText().toString())) {
                toastUtil.centerToast("企业名称不能为空");
                return;
            }
            if (EmptyUtil.isEmpty(binding.etFirmlegalperson.getText().toString())) {
                toastUtil.centerToast("企业法人不能为空");
                return;
            }
            if (!firmImg) {
                toastUtil.centerToast("请上传营业执照");
                return;
            }
            firmApprove();
        }

    }

    /**
     * 获取实名认证信息
     */
    private void getApproveMsg() {
//
        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.GETCERTIFICATIONINFO);
        params.put("baseUrl", Config.baseUrl3);
        params.put("terminal", "ANDROID");


        OkHttpUtil.getRequest(approveActivity, params, new RequestCallBack<ApproveBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ApproveBean> response) {
                ApproveBean approveBean = response.body();
                if (!EmptyUtil.isEmpty(approveBean)) {
                    int resultCode = approveBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        authBean = approveBean.getResult().getAuth();
                        if (!EmptyUtil.isEmpty(authBean)) {

                            SharedPreferencesUtil.setParam(approveActivity, registerCode, authBean.getAuthStatus());
                            intent = new Intent(approveActivity, ApproveResultActivity.class);
                            intent.putExtra("certType", approveBean.getResult().getAuth().getCertType());
                            intent.putExtra("authStatus", (Serializable) authBean);
                            intent.putExtra("type", "approve");
                            approveActivity.startActivity(intent);
                        }


                    }
                }
            }

            @Override
            public ApproveBean parseNetworkResponse(String jsonResult) {
                ApproveBean approveBean = JSON.parseObject(jsonResult, ApproveBean.class);

                return approveBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });

    }

    /**
     * 企业实名认证
     */
    private void firmApprove() {
        HashMap params = new HashMap();
        params.put("token", token);
        params.put("enterpriseName", binding.etFirmName.getText().toString().trim());
        params.put("enterprisePerson", binding.etFirmlegalperson.getText().toString().trim());
        params.put("certAtt1", firmImgId);
        params.put("baseUrl", Config.baseUrl3);
        params.put("baseMethod", Method.FIRMAPPROVE);
        params.put("terminal", "ANDROID");

        OkHttpUtil.postRequest(approveActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        getApproveMsg();
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
     * 上传证件接口
     *
     * @param reCode 上传证件正反面
     * @param path       文件路径
     */
    public void upload(final int reCode, final String path) {

        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl3);
        params.put("file", path);
        params.put("baseMethod", Method.FILEUPLOAD);

        OkHttpUtil.uploadFile(approveActivity, params, new RequestCallBack<VoucherBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<VoucherBean> response) {
                VoucherBean voucherBean = response.body();
                if (!EmptyUtil.isEmpty(voucherBean)) {
                    int resultCode = voucherBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        VoucherBean.ResultBean result = voucherBean.getResult();
                        String url = result.getUrl();
                        if (reCode == StatusVariable.PCENTERAPPROVEFRONT) {
                            passwordfront = true;
                            passwordFrontId = url;
                        } else if (resultCode == StatusVariable.PCENTERAPPROVECONTRACT) {
                            passwordcontract = true;
                            passwordcontractId = url;
                        } else if (resultCode == StatusVariable.PCENTERAPPROVEHAND) {
                            passwordhand = true;
                            passwordhandId = url;
                        } else if (resultCode == StatusVariable.FIRMAPPROVEIMG) {
                            firmImg = true;
                            firmImgId = url;
                        }
                        initPhoto(resultCode, path);
                        toastUtil.centerToast("上传成功");
                    } else {
                        toastUtil.centerToast("上传失败");
                    }

                }
            }

            @Override
            public VoucherBean parseNetworkResponse(String jsonResult) {
                VoucherBean voucherBean = JSON.parseObject(jsonResult, VoucherBean.class);
                return voucherBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

        @Override
        public void countEnd ( boolean isEnd){
            if (isEnd) {
                if (!EmptyUtil.isEmpty(loadingDialog)) {
                    loadingDialog.dismiss();
                }
            }

        }
    }
