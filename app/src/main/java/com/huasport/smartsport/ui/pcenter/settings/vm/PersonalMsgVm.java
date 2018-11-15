package com.huasport.smartsport.ui.pcenter.settings.vm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UploadResultBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.PersonalMsgLayoutBinding;
import com.huasport.smartsport.rxpermission.RxPermissionUtil;
import com.huasport.smartsport.rxpermission.RxPermissionUtilCallback;
import com.huasport.smartsport.ui.pcenter.settings.bean.UserInfoBean;
import com.huasport.smartsport.ui.pcenter.settings.view.PersonalMsgActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GetPathFromUriUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.PopWindowUtil;
import com.huasport.smartsport.util.SelectPicCallBack;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

public class PersonalMsgVm extends BaseViewModel {

    private PersonalMsgActivity personalMsgActivity;
    private ToastUtil toastUtil;
    private PersonalMsgLayoutBinding binding;
    private String picturePath = "";//图片路径
    private String headerUrl = "";//上传成功后的头像的Url
    private String token;

    public PersonalMsgVm(PersonalMsgActivity personalMsgActivity) {
        this.personalMsgActivity = personalMsgActivity;
        binding = personalMsgActivity.getBinding();
        init();
        initUserInfo();
    }

    /**
     * 初始化
     */
    private void init() {
        toastUtil = new ToastUtil(personalMsgActivity);
        token = MyApplication.getInstance().getUserBean().getToken();
    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {

        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl3);
        params.put("baseMethod", Method.GETUSERINFO);
        params.put("token", token);

        OkHttpUtil.getRequest(personalMsgActivity, params, new RequestCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(Response<UserInfoBean> response) {
                UserInfoBean userInfoBean = response.body();

                if (!EmptyUtil.isEmpty(userInfoBean)) {
                    if (userInfoBean.getResultCode() == StatusVariable.REQUESTSUCCESS) {
                        UserInfoBean.ResultBean resultBean = userInfoBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            UserInfoBean.ResultBean.RegisterBean registerBean = resultBean.getRegister();
                            if (!EmptyUtil.isEmpty(registerBean)) {

                                //设置头像
                                if (!EmptyUtil.isEmpty(registerBean.getHeadimgUrl())) {
                                    setImageResource(registerBean.getHeadimgUrl());
                                } else {
                                    binding.imgHeader.setImageResource(R.mipmap.icon_defaultheader_yes);
                                }
                                if (!EmptyUtil.isEmpty(registerBean.getNickName())) {
                                    binding.editNickname.setText(registerBean.getNickName());
                                } else {
                                    if (!EmptyUtil.isEmpty(registerBean.getAccount())) {
                                        binding.editNickname.setText(registerBean.getAccount());
                                    } else {
                                        binding.editNickname.setText("");
                                    }
                                }

                            }
                        }
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
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    /**
     * 修改用户头像
     */
    public void modifyHeader() {

        PopWindowUtil.selectPicture(personalMsgActivity, new SelectPicCallBack() {
            @Override
            public void camera(final PopupWindow popupWindow, final int cameraCode) {
                RxPermissionUtil.getPermission(personalMsgActivity, Manifest.permission.CAMERA, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            IntentUtil.camera(personalMsgActivity, cameraCode);
                            popupWindow.dismiss();
                        } else {
                            toastUtil.centerToast(personalMsgActivity.getResources().getString(R.string.refuse_permission));
                        }
                    }
                });
            }

            @Override
            public void photo(final PopupWindow popupWindow, final int photoCode) {
                RxPermissionUtil.getPermission(personalMsgActivity, Manifest.permission.READ_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            IntentUtil.photo(personalMsgActivity, photoCode);
                            popupWindow.dismiss();
                        } else {
                            toastUtil.centerToast(personalMsgActivity.getResources().getString(R.string.refuse_permission));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == StatusVariable.PHOTOCODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            Uri uriData = data.getData();
            String path = GetPathFromUriUtil.getPath(personalMsgActivity, uriData);
            picturePath = path;
        } else if (requestCode == StatusVariable.CAMERACODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            String pathForData = Util.getPathForData(personalMsgActivity, data);
            picturePath = pathForData;
        }
        setImageResource(picturePath);
    }

    /**
     * 设置圆形头像
     *
     * @param picturePath
     */
    private void setImageResource(String picturePath) {

        GlideUtil.LoadCircleImage(personalMsgActivity, picturePath, binding.imgHeader);

    }

    /**
     * 保存修改用户信息
     */
    public void saveInfo() {
        if (EmptyUtil.isEmpty(picturePath)) {
            saveModifyUserInfo();
        } else {
            //上传头像，然后保存信息
            uploadFile(picturePath);
        }


    }

    /**
     * 上传头像
     *
     * @param filepath
     */
    private void uploadFile(String filepath) {
        HashMap params = new HashMap();
        params.put("baseMethod", Method.UPLOAD_FILE);
        params.put("baseUrl", Config.baseUrl);
        params.put("file", picturePath);

        OkHttpUtil.uploadFile(personalMsgActivity, params, new RequestCallBack<UploadResultBean>() {
            @Override
            public void onSuccess(Response<UploadResultBean> response) {
                UploadResultBean uploadResultBean = response.body();
                if (!EmptyUtil.isEmpty(uploadResultBean)) {

                    int resultCode = uploadResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        UploadResultBean.ResultBean resultBean = uploadResultBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            headerUrl = resultBean.getUrl();
                            saveModifyUserInfo();
                        } else {
                            LogUtil.e("缺少上传返回的url");
                        }
                        toastUtil.centerToast(personalMsgActivity.getResources().getString(R.string.upload_success));
                    } else {
                        toastUtil.centerToast(uploadResultBean.getResultMsg());
                    }
                } else {
                    LogUtil.e("bean是空的");
                }
            }

            @Override
            public UploadResultBean parseNetworkResponse(String jsonResult) {

                UploadResultBean uploadResultBean = JSON.parseObject(jsonResult, UploadResultBean.class);

                return uploadResultBean;
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
     * 保存修改的用户信息
     */
    private void saveModifyUserInfo() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.UPDATAUSERINFO);
        params.put("headimgUrl", headerUrl);
        params.put("nickName", binding.editNickname.getText().toString().trim());
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl3);

        OkHttpUtil.postRequest(personalMsgActivity, params, new RequestCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(Response<UserInfoBean> response) {
                UserInfoBean userInfoBean = response.body();

                if (!EmptyUtil.isEmpty(userInfoBean)) {
                    UserInfoBean.ResultBean resultBean = userInfoBean.getResult();
                    if (!EmptyUtil.isEmpty(resultBean)) {
                        UserInfoBean.ResultBean.RegisterBean registerBean = resultBean.getRegister();
                        if (!EmptyUtil.isEmpty(registerBean)) {
                            //更新用户信息
                            UserBean userBean = MyApplication.getInstance().getUserBean();
                            userBean.setAccount(registerBean.getAccount());
                            userBean.setGender(registerBean.getGender());
                            userBean.setHeadimgUrl(registerBean.getHeadimgUrl());
                            userBean.setNickName(registerBean.getNickName());
                            userBean.setAccount(registerBean.getPhone());
                            userBean.setAccount(registerBean.getRealName());
                            userBean.setToken(resultBean.getToken());
                            SharedPreferencesUtil.setBean(personalMsgActivity,"UserBean",userBean);
                        }
                    }
                    toastUtil.centerToast(personalMsgActivity.getResources().getString(R.string.save_success));
                } else {
                    LogUtil.e("bean是空的");
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
                    toastUtil.centerToast(msg);
                }
            }
        });


    }
}
