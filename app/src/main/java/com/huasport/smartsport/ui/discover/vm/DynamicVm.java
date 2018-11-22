package com.huasport.smartsport.ui.discover.vm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.DynamicLayoutBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.rxpermission.RxPermissionUtil;
import com.huasport.smartsport.rxpermission.RxPermissionUtilCallback;
import com.huasport.smartsport.ui.discover.adapter.ReleaseMsgAdapter;
import com.huasport.smartsport.ui.discover.bean.DynamicDataBean;
import com.huasport.smartsport.ui.discover.bean.GetArticleSaveDataBean;
import com.huasport.smartsport.ui.discover.bean.ReleaseBean;
import com.huasport.smartsport.ui.discover.bean.ReleaseUploadBean;
import com.huasport.smartsport.ui.discover.view.DynamicActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.UriTofilePath;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.util.popwindow.SelectPicCallBack;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DynamicVm extends BaseViewModel {

    private DynamicActivity dynamicactivity;
    private ReleaseMsgAdapter releaseMsgAdapter;
    private List<ReleaseBean> releaseBeanList = new ArrayList<>();
    private List<String> ImageIdList = new ArrayList<>();
    private DynamicLayoutBinding binding;
    private String path;
    private int pos = -1;//记录点击的position
    private ReleaseBean releaseBean;
    private DynamicDataBean dynamicBean;
    private byte[] bytes;
    private int uploadCount = 0;
    private String token = "";
    private List list = new ArrayList();
    private File file;
    private int count = 0;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private MyApplication application;
    private String dynamicId;
    private UserBean userBean;
    private String registerCode = "";

    public DynamicVm(DynamicActivity dynamicActivity, ReleaseMsgAdapter releaseMsgAdapter) {
        this.dynamicactivity = dynamicActivity;
        this.releaseMsgAdapter = releaseMsgAdapter;
        binding = dynamicActivity.getBinding();
        binding.edittextRelease.setText("");
        init();
        initAddPic();//添加替换图片
        getEdittextData();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化toast
        toastUtil = new ToastUtil(dynamicactivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(dynamicactivity, R.style.LoadingDialog);
        //初始化Application
        application = MyApplication.getInstance();
        userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
            registerCode = userBean.getRegisterCode();
        } else {
            token = "";
            registerCode = "";
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {

        dynamicBean = (DynamicDataBean) SharedPreferencesUtil.getBean(dynamicactivity, "dynamicBean");
        if (EmptyUtil.isNotEmpty(dynamicBean)) {
            binding.edittextRelease.setText(dynamicBean.getContent());
            List<String> dyImg = dynamicBean.getDyImg();
            if (dyImg.size() <= 9 && dyImg.size() >= 1) {

                for (int i = 0; i < dyImg.size(); i++) {
                    releaseBean = new ReleaseBean();
                    releaseBean.setPath(dyImg.get(i));
                    releaseBeanList.add(releaseBean);
                }
                if (dyImg.size() < 9) {
                    releaseBean = new ReleaseBean();
                    releaseBean.setTypes("default");
                    releaseBeanList.add(releaseBean);
                }
            } else {
                releaseBean = new ReleaseBean();
                releaseBean.setTypes("default");
                releaseBeanList.add(releaseBean);
            }
        } else {
            releaseBean = new ReleaseBean();
            releaseBean.setTypes("default");
            releaseBeanList.add(releaseBean);
        }

        releaseMsgAdapter.loadData(releaseBeanList);
    }

    private void getEdittextData() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.GETDYNAMICARTICLE);
        params.put("baseUrl", Config.baseUrl2);
        params.put("token", token);
        params.put("type", "dynamic");

        OkHttpUtil.postRequest(dynamicactivity, params, new RequestCallBack<GetArticleSaveDataBean>() {
            @Override
            public void onSuccess(Response<GetArticleSaveDataBean> response) {
                GetArticleSaveDataBean getArticleSaveDataBean = response.body();
                if (!EmptyUtil.isEmpty(getArticleSaveDataBean)) {
                    int resultCode = getArticleSaveDataBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        GetArticleSaveDataBean.ResultBean resultBean = getArticleSaveDataBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            GetArticleSaveDataBean.ResultBean.SocialInfoBean socialInfo = resultBean.getSocialInfo();
                            if (!EmptyUtil.isEmpty(socialInfo.getContent()) && !EmptyUtil.isEmpty(socialInfo.getPics())) {
                                initData();
                            } else {
                                SharedPreferencesUtil.remove(dynamicactivity, "dynamicContent");
                                initData();
                            }
                        }
                    }
                }
            }

            @Override
            public GetArticleSaveDataBean parseNetworkResponse(String jsonResult) {
                GetArticleSaveDataBean getArticleSaveDataBean = JSON.parseObject(jsonResult, GetArticleSaveDataBean.class);
                return getArticleSaveDataBean;
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
     * 添加替换动态图片
     */
    private void initAddPic() {

        releaseMsgAdapter.setImgClick(new ReleaseMsgAdapter.ImgClick() {
            @Override
            public void imgClick(int types, ReleaseBean releaseBean, int position) {

                if (types == StatusVariable.NORMAL) {
                    //如果点击的条目类型不是空的则进行添加，pos置-1，否则进行替换图片,pos置点击的那个position
                    if (EmptyUtil.isEmpty(releaseMsgAdapter.mList.get(position).getTypes())) {
                        pos = position;
                    } else {
                        pos = -1;
                    }
                    showSelectPop();
                } else if (types == StatusVariable.DELIMG) {

                    //判断最后一个对象的类型是否是空的，如果是则直接删除点击的那一条，否则先删除对应条目再添加一条默认到集合最后
                    if (EmptyUtil.isEmpty(releaseBeanList.get(releaseBeanList.size() - 1).getTypes())) {
                        releaseBeanList.remove(releaseMsgAdapter.mList.get(position));
                        ReleaseBean releaseBeans = new ReleaseBean();
                        releaseBeans.setTypes("default");
                        releaseBeanList.add(releaseBeans);
                    } else {
                        releaseBeanList.remove(releaseMsgAdapter.mList.get(position));
                    }
                    releaseMsgAdapter.loadData(releaseBeanList);

                }

            }
        });


    }

    /**
     * 发布
     */
    public void initRelease() {
        //发布
        if (EmptyUtil.isEmpty(binding.edittextRelease.getText().toString().trim()) && releaseBeanList.size() == 1) {
            toastUtil.centerToast("写点什么再来发布吧~");
        } else {
            release("release");
        }
    }

    /**
     * 发布动态
     */
    private void release(String type) {


        if (releaseBeanList.size() > 0) {
            for (int i = 0; i < releaseBeanList.size(); i++) {
                if (EmptyUtil.isEmpty(releaseBeanList.get(i).getTypes())) {
                    list.add(releaseBeanList.get(i).getPath());
                }
            }

            if (list.size() > 0) {

                List<ReleaseBean> mList = releaseMsgAdapter.mList;
                //上传图片
                upload(mList, type);
            } else {
                if (type.equals("save")) {
                    saveEditDynamic(binding.edittextRelease.getText().toString(), "");
                } else {
                    if (ImageIdList.size() > 0) {
                        String imageIdStr = Util.strbuilder(ImageIdList);
                        releaseDynamic(imageIdStr);
                    } else {
                        releaseDynamic("");
                    }
                }
            }
        }
    }

    /**
     * 发布
     *
     * @param imageIdStr
     */
    public void releaseDynamic(String imageIdStr) {
        HashMap params = new HashMap();
        params.put("token", token);
        params.put("socialInfoId", "");
        params.put("title", "");
        params.put("content", binding.edittextRelease.getText().toString());
        if (EmptyUtil.isEmpty(imageIdStr)) {
            imageIdStr = "";
        }
        params.put("picIds", imageIdStr);
        params.put("type", "dynamic");
        params.put("baseMethod", Method.RELEASEDYNAMIC);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.postRequest(dynamicactivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        SharedPreferencesUtil.setParam(dynamicactivity, "ActivityState", true);
                        SharedPreferencesUtil.remove(dynamicactivity, "dynamicBean");
                        dynamicactivity.setResult(StatusVariable.RELEASECODESUCCESS);
                        dynamicactivity.finish();
                    } else {
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
     * 弹出选择图片的popwindow
     */
    private void showSelectPop() {
        PopWindowUtil.selectPicture(dynamicactivity, new SelectPicCallBack() {
            @Override
            public void camera(final PopupWindow popupWindow, final int cameraCode) {
                RxPermissionUtil.getPermission(dynamicactivity, Manifest.permission.READ_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            getPhoto(cameraCode);
                            popupWindow.dismiss();
                        }
                    }
                });
            }

            @Override
            public void photo(final PopupWindow popupWindow, final int photoCode) {
                RxPermissionUtil.getPermission(dynamicactivity, Manifest.permission.READ_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
                    @Override
                    public void grand(boolean grand) {
                        if (grand) {
                            getPhoto(photoCode);
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
     * PHOTOALBUM ：相册获取 CAMERACODE : 拍照获取
     *
     * @param code
     */
    private void getPhoto(int code) {

        if (code == StatusVariable.CAMERACODE) {
            //拍照
            IntentUtil.camera(dynamicactivity, code);

        } else if (code == StatusVariable.PHOTOCODE) {
            //相册
            IntentUtil.photo(dynamicactivity, code);
        }

    }

    /**
     * 取消发布
     */
    public void cancelRelease() {

        if (releaseBeanList.size() > 1 || !EmptyUtil.isEmpty(binding.edittextRelease.getText().toString().trim())) {
            BaseDialog.show(dynamicactivity, "提示", "是否保存您的此次编辑?", "保存", "不保存", false, false, dynamicactivity.getResources().getColor(R.color.color_333333), new DialogCallBack() {
                @Override
                public void submit(CustomDialog.Builder customDialog) {
                    Save();
                    release("save");
                }

                @Override
                public void cancel(CustomDialog.Builder customDialog) {
                    SharedPreferencesUtil.remove(dynamicactivity, "dynamicBean");
                    customDialog.dismiss();
                    dynamicactivity.setResult(StatusVariable.RELEASECODESUCCESS);
                    dynamicactivity.finish();
                }
            });
        } else {
            dynamicactivity.setResult(StatusVariable.DISCOVER);
            dynamicactivity.finish();
        }
    }

    /**
     * 保存
     */
    private void Save() {
        DynamicDataBean dynamicDataBean = new DynamicDataBean();
        List<String> imgbytelist = new ArrayList<>();
        if (releaseBeanList.size() > 0) {
            try {
                for (int i = 0; i < releaseBeanList.size(); i++) {
                    if (EmptyUtil.isEmpty(releaseBeanList.get(i).getTypes())) {
                        imgbytelist.add(releaseBeanList.get(i).getPath());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        dynamicDataBean.setContent(binding.edittextRelease.getText().toString().trim());
        dynamicDataBean.setDyImg(imgbytelist);
        SharedPreferencesUtil.setBean(dynamicactivity, "dynamicBean", dynamicDataBean);
    }

    /**
     * 上传图片
     *
     * @param
     * @param
     */
    public void upload(final List<ReleaseBean> releaseBeanList, final String type) {

        HashMap params = new HashMap();
        if (EmptyUtil.isEmpty(releaseBeanList.get(count).getTypes())) {
//            if (!EmptyUtil.isEmpty(releaseBeanList.get(count).getPath())) {
//                file = Util.bytesToImageFile(releaseBeanList.get(count).getImgbyte());
//            }

            params.put("baseUrl", Config.baseUrl2);
            params.put("file", releaseBeanList.get(count).getPath());
            params.put("baseMethod", Method.FILEUPLOAD);

            OkHttpUtil.uploadFile(dynamicactivity, params, new RequestCallBack<ReleaseUploadBean>() {
                @Override
                public void onSuccess(Response<ReleaseUploadBean> response) {
                    ReleaseUploadBean releaseUploadBean = response.body();
                    if (!EmptyUtil.isEmpty(releaseUploadBean.getResult().getPicId())) {
                        ImageIdList.add(releaseUploadBean.getResult().getPicId());
                    }
                    //如果count小于集合长度减一则继续上传，上传如果Types不是空的则判断是保存还是发布
                    if (count < releaseBeanList.size() - 1) {
                        count++;

                        if (EmptyUtil.isEmpty(releaseBeanList.get(count).getTypes())) {
                            upload(releaseBeanList, type);
                        } else {
                            String imageIdStr = Util.strbuilder(ImageIdList);
                            if (type.equals("release")) {
                                releaseDynamic(imageIdStr);
                            } else {
                                saveEditDynamic(binding.edittextRelease.getText().toString().trim(), ImageIdList.toString());
                            }
                        }

                    } else {
                        String imageIdStr = Util.strbuilder(ImageIdList);
                        if (type.equals("release")) {
                            releaseDynamic(imageIdStr);
                        } else {
                            saveEditDynamic(binding.edittextRelease.getText().toString().trim(), ImageIdList.toString());
                        }
                    }

                }

                @Override
                public ReleaseUploadBean parseNetworkResponse(String jsonResult) {

                    ReleaseUploadBean releaseUploadBean = JSON.parseObject(jsonResult, ReleaseUploadBean.class);

                    return releaseUploadBean;
                }

                @Override
                public void onFailed(int code, String msg) {

                }
            });
        }
    }

    /**
     * 保存
     *
     * @param content
     * @param picId
     */
    public void saveEditDynamic(String content, String picId) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("content", content);
        params.put("picIds", picId);
        params.put("type", "dynamic");
        params.put("baseMethod", Method.SAVEARTICLE);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.postRequest(dynamicactivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        dynamicactivity.setResult(StatusVariable.DISCOVER);
                        dynamicactivity.finish();
                        toastUtil.centerToast(dynamicactivity.getResources().getString(R.string.save_success));
                    } else {
                        toastUtil.centerToast(dynamicactivity.getResources().getString(R.string.save_failed));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == StatusVariable.PHOTOCODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            Uri headerUri = data.getData();
            //uri转换路径
            path = UriTofilePath.getFilePathByUri(dynamicactivity, headerUri);
        } else if (requestCode == StatusVariable.CAMERACODE && resultCode == Activity.RESULT_OK && !EmptyUtil.isEmpty(data)) {
            Bundle bundle = data.getExtras();
            // 转换图片的二进制流
            Bitmap bitmap = (Bitmap) bundle.get("data");
            File file = new File(dynamicactivity.getCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
            path = Util.saveMyBitmap(file.getName(), bitmap);

        } else {
            return;
        }

        if (!EmptyUtil.isEmpty(path)) {
            try {
                bytes = Util.readStream(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ReleaseBean releaseBean = new ReleaseBean();
            releaseBean.setPath(path);
//            releaseBean.setImgbyte(bytes);

            if (pos != -1) {
                releaseBeanList.add(pos, releaseBean);
            } else {
                if (releaseBeanList.size() - 1 == 0) {
                    releaseBeanList.add(0, releaseBean);
                } else {
                    releaseBeanList.add(releaseBeanList.size() - 1, releaseBean);
                }
            }
            //如果图片是9张的话则删除加号
            if (releaseBeanList.size() - 1 == 9) {

                releaseBeanList.remove(releaseBeanList.get(releaseBeanList.size() - 1));

            }

            releaseMsgAdapter.loadData(releaseBeanList);
        } else {
            return;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
            registerCode = userBean.getRegisterCode();
        } else {
            token = "";
            registerCode = "";
        }
    }
}
