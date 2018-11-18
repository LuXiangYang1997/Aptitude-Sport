package com.huasport.smartsport.ui.discover.vm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

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
import com.huasport.smartsport.custom.RichTextEditor;
import com.huasport.smartsport.databinding.ArticleLayoutBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.rxpermission.RxPermissionUtil;
import com.huasport.smartsport.rxpermission.RxPermissionUtilCallback;
import com.huasport.smartsport.ui.discover.bean.ArticleDataBean;
import com.huasport.smartsport.ui.discover.bean.GetArticleSaveDataBean;
import com.huasport.smartsport.ui.discover.bean.ReleaseUploadBean;
import com.huasport.smartsport.ui.discover.view.ArticleActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GetPathFromUri;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.SoftKeyBoardListener;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.popwindow.PopWindowUtil;
import com.huasport.smartsport.util.popwindow.SelectPicCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleVm extends BaseViewModel {

    private ArticleActivity articleActivity;
    private ArticleLayoutBinding binding;
    private View view;
    private String path;
    private PopupWindow popupWindow;
    private List<String> imgList = new ArrayList<>();
    private List<RichTextEditor.EditData> editDataList;
    private int uploadCount = 0;
    private String token;
    private String str;
    private List<String> inputStrList = new ArrayList<>();
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private MyApplication application;
    private String registerCode;


    public ArticleVm(ArticleActivity articleActivity) {
        this.articleActivity = articleActivity;
        binding = articleActivity.getBinding();
        init();
        initView();
        getEdittextData();
    }

    /**
     * 初始化
     */
    private void initView() {
        //初始化Toast
        toastUtil = new ToastUtil(articleActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(articleActivity, R.style.LoadingDialog);
//        //初始化Counter
//        counter = new Counter(this, 2);
        //获取token
        application = MyApplication.getInstance();
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
            registerCode = userBean.getRegisterCode();
        }
        //监听软键盘显示隐藏状态
        SoftKeyBoardListener.setListener(articleActivity, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                if (!binding.tvTitle.isFocused()) {
                    showCommentEdit();
                }

            }

            @Override
            public void keyBoardHide(int height) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void init() {

        popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ArticleDataBean articleContent = (ArticleDataBean) SharedPreferencesUtil.getBean(articleActivity, "articleContent");
        if (!EmptyUtil.isEmpty(articleContent)) {
            List<RichTextEditor.EditData> dataStr = articleContent.getDataStr();
            if (dataStr.size() > 0) {
                if (!EmptyUtil.isEmpty(dataStr.get(0).getArticleTitle())) {
                    binding.tvTitle.setText(dataStr.get(0).getArticleTitle());
                }

                for (int i = 0; i < dataStr.size(); i++) {
                    if (!EmptyUtil.isEmpty(dataStr.get(i).getImagePath()) && EmptyUtil.isEmpty(dataStr.get(i).getInputStr())) {
                        String imagePath = dataStr.get(i).getImagePath();

                        binding.tvTextPart.insertImage(imagePath, 300);
                    } else if (EmptyUtil.isEmpty(dataStr.get(i).getImagePath()) && !EmptyUtil.isEmpty(dataStr.get(i).getInputStr())) {
                        int childCount = binding.tvTextPart.getChildCount();
                        if (childCount >= 0) {
                            binding.tvTextPart.addEditTextAtIndex(childCount, dataStr.get(i).inputStr, "");
                        }
                    }
                }

                if (dataStr.size() == 1) {
                    if (!EmptyUtil.isEmpty(dataStr.get(0).getImagePath()) || !EmptyUtil.isEmpty(dataStr.get(0).getImageUrl()) || !EmptyUtil.isEmpty(dataStr.get(0).getInputStr())) {
                        EditText viewById = binding.tvTextPart.getChildAt(0).findViewById(0);
                        if (viewById.getHint().equals("输入正文...")) {
                            binding.tvTextPart.removeViews();
                        }
                    }
                }

            }
        }
    }

    /**
     * 获取是否需要初始化数据
     */
    private void getEdittextData() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.GETDYNAMICARTICLE);
        params.put("baseUrl", Config.baseUrl2);
        params.put("token", token);
        params.put("type", "article");

        OkHttpUtil.postRequest(articleActivity, params, new RequestCallBack<GetArticleSaveDataBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<GetArticleSaveDataBean> response) {
                GetArticleSaveDataBean getArticleSaveDataBean = response.body();
                if (!EmptyUtil.isEmpty(getArticleSaveDataBean)) {
                    int resultCode = getArticleSaveDataBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        GetArticleSaveDataBean.ResultBean resultBean = getArticleSaveDataBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            GetArticleSaveDataBean.ResultBean.SocialInfoBean socialInfo = resultBean.getSocialInfo();
                            if (!EmptyUtil.isEmpty(socialInfo.getContent()) || !EmptyUtil.isEmpty(socialInfo.getTitle())) {
                                init();
                            } else {
                                SharedPreferencesUtil.remove(articleActivity, "articleContent");
                                init();
                            }
                        } else {
                            toastUtil.centerToast(getArticleSaveDataBean.getResultMsg());
                        }
                    }
                }
            }

            @Override
            public GetArticleSaveDataBean parseNetworkResponse(String jsonResult) {
                return null;
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
     * 弹出框
     */
    public void showCommentEdit() {

        view = LayoutInflater.from(articleActivity).inflate(R.layout.article_insertimgpop, null);
        RelativeLayout rl_selectImage = view.findViewById(R.id.rl_selectImage);
        RelativeLayout rl_cancelBoard = view.findViewById(R.id.rl_cancelBoard);
        rl_selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPop();
                Util.hideSoftKeyboard(articleActivity);
                popupWindow.dismiss();
            }
        });
        rl_cancelBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Util.hideSoftKeyboard(articleActivity);

            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable());

        popupWindow.setOutsideTouchable(false);

        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);//防止软键盘遮挡布局

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setContentView(view);

        popupWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);

    }

    /**
     * 选择图片
     */
    private void showSelectPop() {
        PopWindowUtil.selectPicture(articleActivity, new SelectPicCallBack() {
            @Override
            public void camera(final PopupWindow popupWindow, final int cameraCode) {
                RxPermissionUtil.getPermission(articleActivity, Manifest.permission.READ_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
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
                RxPermissionUtil.getPermission(articleActivity, Manifest.permission.READ_EXTERNAL_STORAGE, new RxPermissionUtilCallback() {
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
            IntentUtil.camera(articleActivity, code);

        } else if (code == StatusVariable.PHOTOCODE) {
            //相册
            IntentUtil.photo(articleActivity, code);
        }
    }

    /**
     * 保存提示框
     */
    public void tipDialog() {
        BaseDialog.show(articleActivity, "提示", "是否保存您的此次编辑?", "保存", "不保存", false, false, articleActivity.getResources().getColor(R.color.color_333333), new DialogCallBack() {
            @Override
            public void submit(CustomDialog.Builder customDialog) {
                saveLocalArticle();
                releaseArticle("save");
            }

            @Override
            public void cancel(CustomDialog.Builder customDialog) {
                customDialog.dismiss();
                SharedPreferencesUtil.remove(articleActivity, "articleContent");
                articleActivity.finish();
            }
        });
    }

    /**
     * 保存数据
     */
    private void saveLocalArticle() {
        List<RichTextEditor.EditData> articleDataBeanList = new ArrayList<>();
        List<RichTextEditor.EditData> editDataList = binding.tvTextPart.buildEditData();
        for (RichTextEditor.EditData editData : editDataList) {
            RichTextEditor.EditData articleDataBean = new RichTextEditor.EditData();
            articleDataBean.setArticleTitle(binding.tvTitle.getText().toString());//存储标题
            try {
                if (!EmptyUtil.isEmpty(editData.imagePath) && EmptyUtil.isEmpty(editData.inputStr)) {
                    byte[] bytes = editData.getImagePath().getBytes();
                    articleDataBean.setImagePath(new String(bytes));
                } else if (EmptyUtil.isEmpty(editData.imagePath) && !EmptyUtil.isEmpty(editData.inputStr)) {
                    articleDataBean.setInputStr(editData.getInputStr());
                }
                articleDataBeanList.add(articleDataBean);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        ArticleDataBean articleDataBean = new ArticleDataBean();
        articleDataBean.setDataStr(articleDataBeanList);
        SharedPreferencesUtil.setBean(articleActivity, "articleContent", articleDataBean);

    }

    /**
     * 发布文章
     *
     * @param type
     */
    public void releaseArticle(String type) {
        List<RichTextEditor.EditData> editData = binding.tvTextPart.buildEditData();
        if (type.equals("release")) {
            if (EmptyUtil.isEmpty(binding.tvTitle.getText().toString().trim())) {
                toastUtil.centerToast("标题不能为空~");
                return;
            }
            if (editData.size() == 1 && EmptyUtil.isEmpty(editData.get(0).getInputStr()) && EmptyUtil.isEmpty(editData.get(0).getImagePath())) {
                toastUtil.centerToast("写点什么再来发布吧~");
                return;
            }
            uploadData(editData, type);
        } else {
            if (!EmptyUtil.isEmpty(binding.tvTitle.getText().toString().trim()) || editData.size() > 0) {
                for (int i = 0; i < editData.size(); i++) {

                    if (!EmptyUtil.isEmpty(editData.get(i).imagePath) && EmptyUtil.isEmpty(editData.get(i).inputStr)) {
                        imgList.add(editData.get(i).imagePath);
                    }
                }
                if (imgList.size() > 0) {
                    uploadData(editData, type);
                } else {
                    List<RichTextEditor.EditData> eddata = binding.tvTextPart.buildEditData();
                    String ed = getEditData(eddata);
                    saveEditArticle(binding.tvTitle.getText().toString().trim(), ed);
                }
            }
        }
    }
    /**
     * 处理数据
     * @param editData
     * @param type
     */
    public void uploadData(List<RichTextEditor.EditData> editData, String type) {


        editDataList = new ArrayList<>();
        editDataList.clear();
        imgList.clear();

        //拿出所有数据，不能修改editdata
        for (int i = 0; i < editData.size(); i++) {
            editDataList.add(editData.get(i));
        }
        //拿出所有图片
        for (int i = 0; i < editDataList.size(); i++) {

            if (!EmptyUtil.isEmpty(editData.get(i).imagePath) && EmptyUtil.isEmpty(editData.get(i).inputStr)) {
                imgList.add(editData.get(i).imagePath);
            }
        }
        if (imgList.size() > 0) {

            //上传图片
            for (int i = 0; i < editDataList.size(); i++) {
                if (!EmptyUtil.isEmpty(editDataList.get(i).imagePath) && EmptyUtil.isEmpty(editDataList.get(i).inputStr)) {
                    upload(i, editDataList, type);
                }
            }
        } else {
            String webContent = getEditData(editDataList);
            release(webContent);
        }

    }

    /**
     * 发布文章
     *
     * @param string
     */
    private void release(String string) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("socialInfoId", "");
        params.put("title", binding.tvTitle.getText().toString().trim());
        params.put("content", string);
        params.put("picIds", "");
        params.put("type", "article");
        params.put("baseMethod", Method.RELEASEDYNAMIC);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.postRequest(articleActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        articleActivity.setResult(1000);
                        articleActivity.finish();
                    } else {
                        toastUtil.centerToast(resultBean.getResultMsg());
                    }
                }
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {
                ResultBean releaseDynamicResultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return releaseDynamicResultBean;
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
     * 上传
     *
     * @param pos
     * @param editDataList
     * @param type
     */
    private int upload(final int pos, final List<RichTextEditor.EditData> editDataList, final String type) {

        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl2);
        params.put("file", editDataList.get(pos).imagePath);
        params.put("baseMethod", Method.FILEUPLOAD);

        OkHttpUtil.uploadFile(articleActivity, params, new RequestCallBack<ReleaseUploadBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ReleaseUploadBean> response) {
                ReleaseUploadBean releaseUploadBean = response.body();
                if (!EmptyUtil.isEmpty(releaseUploadBean)) {
                    int resultCode = releaseUploadBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        ReleaseUploadBean.ResultBean resultBean = releaseUploadBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            uploadCount++;
                            String picUrl = releaseUploadBean.getResult().getPicUrl();
                            RichTextEditor.EditData editData = new RichTextEditor.EditData();
                            editData.setImagePath(picUrl);
                            editDataList.remove(editDataList.get(pos));
                            editDataList.add(pos, editData);
                            if (uploadCount == imgList.size()) {
                                if (type.equals("release")) {
                                    // String webContent = "<html><header>" + StatusVariable.LINK_CSS + "</header><body>" + getEditData(editDataList) + "</body></html>";
                                    String webContent = getEditData(editDataList);
                                    release(webContent);
                                } else {
                                    String saveContentStr = getEditData(editDataList);
                                    saveEditArticle(binding.tvTitle.getText().toString().trim(), saveContentStr);
                                }
                            }
                        }

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
                uploadCount++;
                if (uploadCount == imgList.size()) {
                    if (type.equals("release")) {
//                        String webContent = "<html><header>" + StatusVariable.LINK_CSS + "</header><body>" + getEditData(editDataList) + "</body></html>";
                        String webContent = getEditData(editDataList);
                        release(webContent);
                    } else {
                        String saveContentStr = getEditData(editDataList);

                        saveEditArticle(binding.tvTitle.getText().toString().trim(), saveContentStr);
                    }

                }
            }
        });
        return uploadCount;
    }

    /**
     * 插入图片
     */
    private void insertImage(String imgPath) {
        try {
            binding.tvTextPart.insertImage(imgPath, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转Html文本
     *
     * @param editList
     * @return
     */
    private String getEditData(List<RichTextEditor.EditData> editList) {

        StringBuilder content = new StringBuilder();
        if (editList.size() > 0) {
//            content.append("<div class=\"content\">");
            for (RichTextEditor.EditData itemData : editList) {
                if (itemData.inputStr != null) {
                    //将EditText中的换行符、空格符转换成html
//                    String inputStr = itemData.inputStr.replace("\n", "</p><p>").replace(" ", "&nbsp");
                    content.append(itemData.inputStr);
                } else if (itemData.imagePath != null) {
                    // content.append("<p style=\"text-align:center\"><img width=\"100%\" src=\"").append(itemData.imagePath).append("\"/></p>");
                    content.append("<img src=\"").append(itemData.imagePath).append("\">");
                }
            }
        }
        return content.toString();
    }

    /**
     * 保存文章
     * @param title
     * @param content
     */
    public void saveEditArticle(String title, String content) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("title", title);
        params.put("content", content);
        params.put("type", "article");
        params.put("baseMethod", Method.SAVEARTICLE);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.postRequest(articleActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        toastUtil.centerToast(articleActivity.getResources().getString(R.string.save_success));
                        articleActivity.finish();
                    } else {
                        toastUtil.centerToast(articleActivity.getResources().getString(R.string.save_failed));
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
     * 返回事件
     */
    public void back() {
        List<RichTextEditor.EditData> editDataList = binding.tvTextPart.buildEditData();
        if (!EmptyUtil.isEmpty(binding.tvTitle.getText().toString().trim())) {
            tipDialog();
        } else {
            if (EmptyUtil.isEmpty(editDataList.get(0).getImagePath()) && EmptyUtil.isEmpty(editDataList.get(0).getInputStr())) {
                SharedPreferencesUtil.remove(articleActivity, "articleContent");
                articleActivity.setResult(StatusVariable.DISCOVERCODE);
                articleActivity.finish();
            } else {
                tipDialog();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == StatusVariable.PHOTOCODE && resultCode == Activity.RESULT_OK && data != null) {

            Uri headerUri = data.getData();
            //uri转换路径
            path = GetPathFromUri.getPath(articleActivity, headerUri);
        } else if (requestCode == StatusVariable.CAMERACODE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            // 转换图片的二进制流
            Bitmap bitmap = (Bitmap) bundle.get("data");
            File file = new File(articleActivity.getCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
            path = Util.saveMyBitmap(file.getName(), bitmap);
        } else {
            return;
        }
        if (!EmptyUtil.isEmpty(path)) {
            imgList.add(path);
            insertImage(path);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)){
            token = userBean.getToken();
            registerCode = userBean.getRegisterCode();
        }
    }
}
