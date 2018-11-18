package com.huasport.smartsport.util.httprequest;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.util.Config;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

public class HttpRequestUtil {

    /**
     * 点赞
     * @param context
     * @param token
     * @param id
     * @param httpRequestCallBack
     */
    public static void favour(Context context, String token, String id, final HttpRequestCallBack httpRequestCallBack) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("socialInfoId", id);
        params.put("baseMethod", Method.PRAISE);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.postRequest(context, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                httpRequestCallBack.httpSuccess(resultBean);
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                httpRequestCallBack.httpFailed(code, msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpRequestCallBack.httpFinish();
            }
        });

    }

    /**
     * 评论
     * @param context
     * @param token
     * @param id
     * @param httpRequestCallBack
     * @param content
     */
    public static void comment(Context context, String token, String id, String content, final HttpRequestCallBack httpRequestCallBack) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.COMMAND);
        params.put("id", id);
        params.put("content", content);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.postRequest(context, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                httpRequestCallBack.httpSuccess(resultBean);
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                httpRequestCallBack.httpFailed(code, msg);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpRequestCallBack.httpFinish();
            }
        });

    }

    /**
     * 对评论再评论
     * @param context
     * @param token
     * @param commentId
     * @param id
     * @param content
     * @param httpRequestCallBack
     */
    public static void commentReview(Context context, String token, String commentId, String id, String content, final HttpRequestCallBack httpRequestCallBack) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.COMMENTREVIEW);
        params.put("baseUrl", Config.baseUrl2);
        params.put("replyId", id);
        params.put("commentId", commentId);
        params.put("replyContent", content);

        OkHttpUtil.postRequest(context, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                httpRequestCallBack.httpSuccess(resultBean);
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                httpRequestCallBack.httpFailed(code, msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpRequestCallBack.httpFinish();
            }
        });


    }

    /**
     * 关注
     * @param context
     * @param token
     * @param id
     * @param httpRequestCallBack
     */
    public static void attention(Context context, String token, String id, final HttpRequestCallBack httpRequestCallBack) {

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.ATTENTION);
        params.put("userId", id);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.postRequest(context, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                httpRequestCallBack.httpSuccess(resultBean);
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                httpRequestCallBack.httpFailed(code,msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpRequestCallBack.httpFinish();
            }
        });

    }

    /**
     * 删除
     * @param context
     * @param token
     * @param id
     * @param httpRequestCallBack
     */
    public static void delete(final Context context, final String token, final String id, final HttpRequestCallBack httpRequestCallBack){

        BaseDialog.show(context, "提示", "确认删除吗？", "删除", "取消", true, false, context.getResources().getColor(R.color.color_333333), new DialogCallBack() {
            @Override
            public void submit(CustomDialog.Builder customDialog) {
                HashMap params = new HashMap();
                params.put("baseMethod", Method.DELETEDYANDART);
                params.put("token", token);
                params.put("id", id);
                params.put("baseUrl", Config.baseUrl2);

                OkHttpUtil.getRequest(context, params, new RequestCallBack<ResultBean>() {
                    @Override
                    public void onSuccess(Response<ResultBean> response) {
                        ResultBean resultBean = response.body();
                        httpRequestCallBack.httpSuccess(resultBean);
                    }

                    @Override
                    public ResultBean parseNetworkResponse(String jsonResult) {

                        ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                        return resultBean;
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        httpRequestCallBack.httpFailed(code,msg);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        httpRequestCallBack.httpFinish();
                    }
                });
            }

            @Override
            public void cancel(CustomDialog.Builder customDialog) {
                customDialog.dismiss();
            }
        });



    }

}