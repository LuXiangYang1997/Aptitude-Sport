package com.huasport.smartsport.ui.matchapply.vm;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.DynamicTagFlowLayout;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.ActivitySearchBinding;
import com.huasport.smartsport.ui.matchapply.adapter.SearchResultAdapter;
import com.huasport.smartsport.ui.matchapply.bean.SearchResultTestBean;
import com.huasport.smartsport.ui.matchapply.view.SearchActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 陆向阳 on 2018/6/12.
 */
//搜索VM
public class SearchVM extends BaseViewModel implements CounterListener, RefreshLoadMoreListener {

    private SearchActivity searchActivity;
    private final ActivitySearchBinding binding;
    private SearchResultAdapter searchResultAdapter;
    private List<SearchResultTestBean> searchResultTestBeanList = new ArrayList<>();
    private DynamicTagFlowLayout dynamicTagFlowLayout;
    private SharedPreferences.Editor clear_output_editor;
    private List<String> tags = new ArrayList<>();
    private String shared;
    private int page = 1;
    private int totalPage;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private String token;

    public SearchVM(SearchActivity searchActivity, SearchResultAdapter searchResultAdapter, DynamicTagFlowLayout dynamicTagFlowLayout) {
        this.searchActivity = searchActivity;
        this.dynamicTagFlowLayout = dynamicTagFlowLayout;
        this.searchResultAdapter = searchResultAdapter;
        binding = searchActivity.getBinding();
        init();
        initView();
        getSearchHistoryData();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(searchActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(searchActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //初始化加载刷新
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }

    }
    /**
     * 获取搜索历史记录
     */
    private void getSearchHistoryData() {
        clearTag();//清除之前的Tag
        SharedPreferences input = searchActivity.getSharedPreferences("searchHistory", MODE_PRIVATE);
        int value = input.getInt("value", 0);
        for (int i = 1; i <= value; i++) {
            shared = input.getString("" + i, "null");
            tags.add(shared);
        }
        dynamicTagFlowLayout.setTags(tags);

    }
    private void initView() {
        binding.searchEdittext.setOnKeyListener(onKeyListener);

        //标签点击事件
        dynamicTagFlowLayout.setOnTagItemClickListener(new DynamicTagFlowLayout.OnTagItemClickListener() {
            @Override
            public void onClick(View v) {
                TextView textHistory = (TextView) v;
                String text = textHistory.getText().toString();
                binding.searchEdittext.setText(text);
                page = 1;
                goSearch(binding.searchEdittext.getText().toString(), "");
            }
        });

    }

    /**
     * 搜索
     */
    private void goSearch(String string, final String type) {
        //弹出加载框
        loadingDialog.show();
        int a;
        SharedPreferences.Editor output_editor = searchActivity.getSharedPreferences("searchHistory", MODE_PRIVATE).edit();
        SharedPreferences input = searchActivity.getSharedPreferences("searchHistory", MODE_PRIVATE);
        if (!string.equals("")) {
            a = input.getInt("value", 0);
            a++;
            output_editor.putString("" + a, string);
            output_editor.putInt("value", a);
            output_editor.commit();
        }
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("keyword", string);//关键字
        params.put("currentPage", page + "");//当前页数
        params.put("pageSize", "10");//每页大小
        params.put("baseMethod", Method.SEARCH);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(searchActivity, params, new RequestCallBack<SearchResultTestBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<SearchResultTestBean> response) {
                SearchResultTestBean searchResultTestBean = response.body();
                if (!EmptyUtil.isEmpty(searchResultTestBean)){
                    int resultCode = searchResultTestBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        SearchResultTestBean.ResultBean resultBean = searchResultTestBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            totalPage = resultBean.getTotalPage();
                            if (totalPage == 0) {
                                NullStateUtil.setNullState(binding.nulldata,  true);
                                binding.llSearchResult.setVisibility(View.GONE);//搜索结果
                                binding.llHistoryRecord.setVisibility(View.GONE);//搜索历史
                                searchResultTestBeanList.clear();
                                searchResultAdapter.clearData();
                                return;
                            } else {
                                NullStateUtil.setNullState(binding.nulldata, false);
                            }
                            binding.llSearchResult.setVisibility(View.VISIBLE);//搜索结果
                            binding.llHistoryRecord.setVisibility(View.GONE);//搜索历史
                            if (type.equals("loadMore")) {
                                binding.smartRefreshlayout.finishLoadMore();
                                searchResultAdapter.loadMoreData(searchResultTestBean.getResult().getList());
                            } else {
                                binding.smartRefreshlayout.finishRefresh();
                                searchResultAdapter.loadData(searchResultTestBean.getResult().getList());
                            }
                        }
                        page++;
                    }
                }
            }

            @Override
            public SearchResultTestBean parseNetworkResponse(String jsonResult) {
                SearchResultTestBean searchResultTestBean = JSON.parseObject(jsonResult, SearchResultTestBean.class);
                return searchResultTestBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                binding.llSearchResult.setVisibility(View.GONE);//搜索结果
                binding.llHistoryRecord.setVisibility(View.GONE);//搜索历史
                NullStateUtil.setNullState(binding.nulldata, true);
                searchResultTestBeanList.clear();
                searchResultAdapter.clearData();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
            }
        });
    }

    /**
     * 放大镜点击事件
     */
    public void iconSearch() {
        page = 1;
        goSearch(binding.searchEdittext.getText().toString(), "");

    }

    //取消点击事件
    public void cancelClick() {
        searchActivity.finish();
    }

    //清除所有数据
    public void clearClick() {
        getSearchHistoryData();
        binding.searchEdittext.setText("");
        binding.llSearchResult.setVisibility(View.GONE);//搜索结果
        binding.llHistoryRecord.setVisibility(View.VISIBLE);//搜索历史
        NullStateUtil.setNullState(binding.nulldata, true);
        searchResultTestBeanList.clear();
        searchResultAdapter.clearData();
    }

    /**
     * 清除记录
     */
    public void clearRecord() {
        clear_output_editor = searchActivity.getSharedPreferences("searchHistory", MODE_PRIVATE).edit();
        clear_output_editor.clear();
        clear_output_editor.commit();
        clearTag();
    }

    /**
     * 删除dynamicTagFlowLayout数据
     */
    private void clearTag() {
        if (tags.size() > 0) {
            for (int g = 0; g < tags.size(); g++) {
                dynamicTagFlowLayout.removeView(dynamicTagFlowLayout.button_attay[g]);
            }
        }
        tags.clear();
    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                /*隐藏软键盘*/
                InputMethodManager inputMethodManager = (InputMethodManager) searchActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                //  binding.searchEdittext.setText("搜索");
                String text = binding.searchEdittext.getText().toString();
                page = 1;
                goSearch(binding.searchEdittext.getText().toString(), "");//搜索
                return true;
            }
            return false;
        }
    };

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if (!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }

    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        page = 1;
        binding.smartRefreshlayout.setNoMoreData(false);
        goSearch(binding.searchEdittext.getText().toString(), "");
    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {
        if (page <= totalPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            goSearch(binding.searchEdittext.getText().toString(), "loadMore");
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }
    }
}
