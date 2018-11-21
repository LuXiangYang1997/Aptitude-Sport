package com.huasport.smartsport.ui.pcenter.attention.vm;

import android.content.Intent;
import android.support.design.widget.TabLayout;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.AttentionLayoutBinding;
import com.huasport.smartsport.ui.pcenter.attention.adapter.AttentionAdapter;
import com.huasport.smartsport.ui.pcenter.attention.bean.AttentionBean;
import com.huasport.smartsport.ui.pcenter.attention.view.AttentionActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.httprequest.HttpRequestCallBack;
import com.huasport.smartsport.util.httprequest.HttpRequestUtil;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.util.HashMap;
import java.util.List;


public class AttentionVm extends BaseViewModel implements CounterListener, RefreshLoadMoreListener {

    private AttentionActivity attentionActivity;
    private AttentionAdapter attentionAdapter;
    private int page = 1;
    private AttentionLayoutBinding binding;
    private String tabText = "";
    private int totalPage = -1;
    private boolean isLoad;
    private String token;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;

    public AttentionVm(AttentionActivity attentionActivity, AttentionAdapter attentionAdapter) {
        this.attentionActivity = attentionActivity;
        this.attentionAdapter = attentionAdapter;
        binding = attentionActivity.getBinding();
        init();
        initView();
        initData(StatusVariable.REFRESH);
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(attentionActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(attentionActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //初始化加载刷新
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {

            token = userBean.getToken();

        }
        //弹出加载框
        loadingDialog.show();
    }

    /**
     * 初始化tabLayout
     */
    private void initView() {

        String types = attentionActivity.getIntent().getStringExtra("types");
        if (types.equals("attention")) {
            binding.attentionTablayout.getTabAt(0).select();
            tabText = "关注";
        } else {
            binding.attentionTablayout.getTabAt(1).select();
            tabText = "粉丝";
        }
        //tablayout切换
        binding.attentionTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!EmptyUtil.isEmpty(tab.getText().toString().trim())) {
                    tabText = tab.getText().toString().trim();
                    page = 1;
                    initData(StatusVariable.REFRESH);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (tabText.equals("关注")) {
            binding.tvAttentionCount.setText("共" + 0 + "个关注");
        } else if (tabText.equals("粉丝")) {
            binding.tvAttentionCount.setText("共" + 0 + "个粉丝");
        }
        //关注点击事件
        attentionAdapter.setAttentionClick(new AttentionAdapter.AttentionClick() {
            @Override
            public void attentionClick(BaseViewHolder baseViewHolder, int position) {
                cancelAttention(position);
            }
        });

    }

    /**
     * 初始化数据
     * @param types
     */
    private void initData(final int types) {


        if (EmptyUtil.isEmpty(token)){
            IntentUtil.startActivity(attentionActivity,LoginActivity.class);
            return;
        }

        HashMap params = new HashMap();
        if (tabText.equals("关注")) {
            params.put("baseMethod", Method.ATTENTIONLIST);
        } else {
            params.put("baseMethod", Method.FANSLIST);
        }
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl2);
        params.put("currentPage", page + "");
        params.put("pageSize", "10");

        OkHttpUtil.getRequest(attentionActivity, params, new RequestCallBack<AttentionBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<AttentionBean> response) {
                AttentionBean attentionBean = response.body();
                if (!EmptyUtil.isEmpty(attentionBean)){
                    int resultCode = attentionBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        AttentionBean.ResultBean resultBean = attentionBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            totalPage = resultBean.getTotalPage();
                            if (totalPage == 0) {
                                if (tabText.equals("关注")) {
                                    binding.tvAttentionCount.setText("共" + 0 + "个关注");
                                    NullStateUtil.setNullState(binding.nulldata,  true);
                                } else {
                                    binding.tvAttentionCount.setText("共" + 0 + "个粉丝");
                                    NullStateUtil.setNullState(binding.nulldata,  true);
                                }
                                return;
                            } else {
                                NullStateUtil.setNullState(binding.nulldata, false);
                            }
                            if (!EmptyUtil.isEmpty(resultBean.getTotal())) {
                                if (tabText.equals("关注")) {
                                    binding.tvAttentionCount.setText("共" + resultBean.getTotal() + "个关注");
                                } else if (tabText.equals("粉丝")) {
                                    binding.tvAttentionCount.setText("共" + resultBean.getTotal() + "个粉丝");
                                }
                            }

                            List<AttentionBean.ResultBean.ListBean> list = resultBean.getList();

                            if (types == StatusVariable.LOADMORE) {
                                binding.smartRefreshlayout.finishLoadMore();
                                attentionAdapter.loadMoreData(list);
                            } else {
                                binding.smartRefreshlayout.finishRefresh();
                                attentionAdapter.loadData(list);
                            }
                            page++;
                        }

                    }
                }
            }

            @Override
            public AttentionBean parseNetworkResponse(String jsonResult) {
                AttentionBean attentionBean = JSON.parseObject(jsonResult, AttentionBean.class);
                return attentionBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });

    }

    /**
     * 取消关注
     */
    public void cancelAttention(final int position) {


        HttpRequestUtil.attention(attentionActivity, token, attentionAdapter.mList.get(position).getRegisterId(), new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                int resultCode = resultBean.getResultCode();
                if (resultCode == StatusVariable.REQUESTSUCCESS){
                    String status = attentionAdapter.mList.get(position).getStatus();
                    page = 1;
                    initData(StatusVariable.REFRESH);
                    SharedPreferencesUtil.setParam(attentionActivity, "ActivityState", true);
                }
                toastUtil.centerToast(resultBean.getResultMsg());
            }

            @Override
            public void httpFailed(int code, String msg) {

            }

            @Override
            public void httpFinish() {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            page = 1;
            initData(StatusVariable.REFRESH);
        }
    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if(!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }

    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        binding.smartRefreshlayout.setNoMoreData(false);
        page = 1;
        initData(type);
    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {
        if (page <= totalPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            initData(type);
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }
    }
}
