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
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.PcenterapproveLayoutBinding;
import com.huasport.smartsport.rxpermission.RxPermissionUtil;
import com.huasport.smartsport.rxpermission.RxPermissionUtilCallback;
import com.huasport.smartsport.ui.discover.bean.ApproveBean;
import com.huasport.smartsport.ui.pcenter.approve.bean.PersonalApproveBean;
import com.huasport.smartsport.ui.pcenter.approve.bean.UserRealNameApproveBean;
import com.huasport.smartsport.ui.pcenter.approve.bean.VoucherBean;
import com.huasport.smartsport.ui.pcenter.approve.view.ApproveResultActivity;
import com.huasport.smartsport.ui.pcenter.approve.view.PcenterApproveActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;
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

public class PcenterApproveVm extends BaseViewModel implements CounterListener {

    private PcenterApproveActivity pcenterApproveActivity;
    private PopupWindow popupWindow;
    private int imgtrench;
    private ImageView img;
    private PcenterapproveLayoutBinding binding;
    private boolean hasPassword = false;
    private PersonalApproveBean personalApproveBean;
    private String path;
    private String passwordUrl = "";
    private String token;
    private ApproveBean.ResultBean.AuthBean authBean;
    private Intent intent;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private MyApplication application = MyApplication.getInstance();
    private String registerCode;

    public PcenterApproveVm(PcenterApproveActivity pcenterApproveActivity) {
        this.pcenterApproveActivity = pcenterApproveActivity;
        binding = pcenterApproveActivity.getBinding();

        init();
    }

    private void init() {

        personalApproveBean = (PersonalApproveBean) pcenterApproveActivity.getIntent().getSerializableExtra("personalApproveBean");
        //初始化Toast
        toastUtil = new ToastUtil(pcenterApproveActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(pcenterApproveActivity, R.style.LoadingDialog);
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

    //下一步
    public void nextStep() {

        if (!hasPassword) {
            toastUtil.centerToast("请上传工牌或在职证明");
            return;
        }

        submitMsg();

    }

    /**
     * 提交认证信息
     */
    private void submitMsg() {

        HashMap param = new HashMap();
        param.put("certAtt1", personalApproveBean.getImgurlFront());
        param.put("certAtt2", personalApproveBean.getImgurlContract());
        param.put("certAtt3", personalApproveBean.getImgurlHand());
        param.put("certAtt4", passwordUrl);
        param.put("idCard", personalApproveBean.getPassword());
        param.put("realName", personalApproveBean.getRealName());
        param.put("workUnitName", binding.etUnit.getText().toString().trim());
        param.put("positionName", binding.etPost.getText().toString().trim());
        param.put("terminal", "ANDROID");
        param.put("token", token);
        param.put("baseMethod", Method.PERSONALSUBMITMSG);
        param.put("baseUrl", Config.baseUrl3);

        OkHttpUtil.postRequest(pcenterApproveActivity, param, new RequestCallBack<UserRealNameApproveBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<UserRealNameApproveBean> response) {
                UserRealNameApproveBean userRealNameApproveBean = response.body();
                if (!EmptyUtil.isEmpty(userRealNameApproveBean)) {
                    int resultCode = userRealNameApproveBean.getResultCode();

                    if (userRealNameApproveBean.getResultCode() == StatusVariable.SUCCESS) {
                        getApproveMsg();
                    } else {
                        toastUtil.centerToast(userRealNameApproveBean.getResultMsg());
                    }
                }
            }

            @Override
            public UserRealNameApproveBean parseNetworkResponse(String jsonResult) {
                UserRealNameApproveBean userRealNameApproveBean = JSON.parseObject(jsonResult, UserRealNameApproveBean.class);
                return userRealNameApproveBean;
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
     * 获取实名认证信息
     */
    private void getApproveMsg() {
//
        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.GETCERTIFICATIONINFO);
        params.put("baseUrl", Config.baseUrl3);
        params.put("terminal", "ANDROID");

        OkHttpUtil.getRequest(pcenterApproveActivity, params, new RequestCallBack<ApproveBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ApproveBean> response) {
                ApproveBean approveBean = response.body();
                if (!EmptyUtil.isEmpty(approveBean)) {
                    int resultCode = approveBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        authBean = approveBean.getResult().getAuth();
                        if (!EmptyUtil.isEmpty(authBean)) {

                            SharedPreferencesUtil.setParam(pcenterApproveActivity, registerCode, authBean.getAuthStatus());
                            intent = new Intent(pcenterApproveActivity, ApproveResultActivity.class);
                            intent.putExtra("certType", approveBean.getResult().getAuth().getCertType());
                            intent.putExtra("authStatus", (Serializable) authBean);
                            intent.putExtra("type", "approve");
                            pcenterApproveActivity.startActivity(intent);
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


    //上传职位证明
    public void frontpcenterImg() {

        changeHeader();

    }

    public void changeHeader() {

        PopWindowUtil.selectPicture(pcenterApproveActivity, new SelectPicCallBack() {
            @Override
            public void camera(final PopupWindow popupWindow, final int cameraCode) {

                RxPermissionUtil.getPermission(pcenterApproveActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            imgtrench = 1;
                            getPassWord(StatusVariable.CAMERACODE, cameraCode);
                            popupWindow.dismiss();
                        }
                    }
                });

            }

            @Override
            public void photo(final PopupWindow popupWindow, final int photoCode) {
                RxPermissionUtil.getPermission(pcenterApproveActivity, Manifest.permission.READ_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            imgtrench = 0;
                            getPassWord(StatusVariable.PHOTOALBUM, photoCode);
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

    /**
     * @param type 类型 PHOTOALBUM ：相册获取 CAMERACODE : 拍照获取
     * @param code
     */
    private void getPassWord(int type, int code) {

        if (type == StatusVariable.CAMERACODE) {
            //拍照
            IntentUtil.camera(pcenterApproveActivity, code);

        } else if (type == StatusVariable.PHOTOALBUM) {
            //相册
            IntentUtil.photo(pcenterApproveActivity, code);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (imgtrench == StatusVariable.PHOTOALBUM && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            Uri headerUri = data.getData();
            //uri转换路径
            path = UriTofilePath.getFilePathByUri(pcenterApproveActivity, headerUri);
        } else if (imgtrench == StatusVariable.CAMERACODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            Bundle bundle = data.getExtras();
            // 转换图片的二进制流
            Bitmap bitmap = (Bitmap) bundle.get("data");
            File file = new File(pcenterApproveActivity.getCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
            path = Util.saveMyBitmap(file.getName(), bitmap);

        } else {
            return;
        }
        upload(requestCode, path);
    }

    private void initPhoto(String path) {
        if (!EmptyUtil.isEmpty(path)) {
            GlideUtil.LoadImage(pcenterApproveActivity, path, binding.imgjobfront);
            binding.tvFront.setVisibility(View.GONE);
            hasPassword = true;
        }
    }

    /**
     * 上传职位证明
     */
    private void upload(final int resultCode, final String path) {

        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl3);
        params.put("file", path);
        params.put("baseMethod", Method.FILEUPLOAD);
        OkHttpUtil.uploadFile(pcenterApproveActivity, params, new RequestCallBack<VoucherBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<VoucherBean> response) {
                VoucherBean voucherBean = response.body();
                if (!EmptyUtil.isEmpty(voucherBean)) {
                    int resultCode = voucherBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        passwordUrl = voucherBean.getResult().getUrl();
                        initPhoto(path);
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
    public void countEnd(boolean isEnd) {
        if (isEnd) {
            if (!EmptyUtil.isEmpty(loadingDialog)) {
                loadingDialog.dismiss();
            }
        }

    }
}
