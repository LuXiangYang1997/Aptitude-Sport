package com.huasport.smartsport.ui.pcenter.medal.vm;

import android.util.Log;

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
import com.huasport.smartsport.databinding.MedaldetailLayoutBinding;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.medal.bean.MedalDetailBean;
import com.huasport.smartsport.ui.pcenter.medal.view.PersonalMedalDetailActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

public class PersonalMedalDetailVM extends BaseViewModel implements CounterListener {

    private PersonalMedalDetailActivity personalMedalDetailActivity;
    private final MedaldetailLayoutBinding binding;
    private String goodsCode;
    private MedalDetailBean.ResultBean.GoodsBean goodsBean;
    private Counter counter;
    private LoadingDialog loadingDialog;
    private ToastUtil toastUtil;
    private String token;

    public PersonalMedalDetailVM(PersonalMedalDetailActivity personalMedalDetailActivity) {
        this.personalMedalDetailActivity = personalMedalDetailActivity;
        binding = personalMedalDetailActivity.getBinding();
        init();
        initData("");
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(personalMedalDetailActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(personalMedalDetailActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        //弹出加载框
        loadingDialog.show();

    }

    private void initData(final String type) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.MEDALDETAIL);
        params.put("baseUrl", Config.baseUrl);
        Log.e("GoodsCode", params.toString());

        OkHttpUtil.getRequest(personalMedalDetailActivity, params, new RequestCallBack<MedalDetailBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<MedalDetailBean> response) {
                MedalDetailBean medalDetailBean = response.body();
                if (!EmptyUtil.isEmpty(medalDetailBean)){
                    int resultCode = medalDetailBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MedalDetailBean.ResultBean resultBean = medalDetailBean.getResult();
                        if(!EmptyUtil.isEmpty(resultBean)){
                            MedalDetailBean.ResultBean.GoodsBean goods = resultBean.getGoods();
                            goodsBean = goods;
                            String goodsPic = goods.getGoodsPic();
                            String description = goods.getDescription();
                            if (!EmptyUtil.isEmpty(goodsPic)) {
                                GlideUtil.LoadImage(personalMedalDetailActivity, goodsPic, binding.orderImg);
                            }
                            if (!EmptyUtil.isEmpty(description)) {
                                binding.webView.getSettings().setJavaScriptEnabled(true);
                                binding.webView.loadData(getNewContent(description), "text/html", null);
                            }
                            double productPrice = (double) goods.getProductPrice() / 100;
                            String productPricedecimal = Util.decimal(productPrice);
                            binding.tvMedalPrice.setText("￥" + productPricedecimal);
                            binding.tvMedalContent.setText(goods.getTitle());
                        }
                    }else if (resultCode == StatusVariable.NOLOGIN){
                        IntentUtil.startActivity(personalMedalDetailActivity,LoginActivity.class);
                    }else if (resultCode == StatusVariable.NOBIND){
                        IntentUtil.startActivity(personalMedalDetailActivity,BindPhoneActivity.class);
                    }

                }
            }

            @Override
            public MedalDetailBean parseNetworkResponse(String jsonResult) {
                MedalDetailBean medalDetailBean = JSON.parseObject(jsonResult, MedalDetailBean.class);
                return medalDetailBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    /*
     * 立即购买
     * */
    public void purchase() {
        if (!EmptyUtil.isEmpty(goodsBean)) {
//            Intent intent = new Intent(personalMedalDetailActivity, PersonalMedalConfirmOrderActivity.class);
//            intent.putExtra("GoodsBean", (Serializable) goodsBean);
//            personalMedalDetailActivity.startActivity(intent);
        }
    }

    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    public static String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
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
