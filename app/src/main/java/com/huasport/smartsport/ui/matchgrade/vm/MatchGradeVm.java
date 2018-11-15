package com.huasport.smartsport.ui.matchgrade.vm;


import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MainActivity;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.MatchGradeLayoutBinding;
import com.huasport.smartsport.ui.matchgrade.adapter.MatchGradeListAdapter;
import com.huasport.smartsport.ui.matchgrade.adapter.MatchGradeTabAdapter;
import com.huasport.smartsport.ui.matchgrade.bean.MatchGradeListBean;
import com.huasport.smartsport.ui.matchgrade.bean.MatchGradeTabBean;
import com.huasport.smartsport.ui.matchgrade.bean.MatchListSearchBean;
import com.huasport.smartsport.ui.matchgrade.view.MatchGradeFragment;
import com.huasport.smartsport.ui.matchgrade.view.SelectMatchProgramActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.edittext.EdittextListener;
import com.huasport.smartsport.util.edittext.EdittextUtil;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.huasport.smartsport.util.ToastUtil;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchGradeVm extends BaseViewModel implements CounterListener,RefreshLoadMoreListener,EdittextListener{

    private MatchGradeFragment matchGradeFragment;
    private boolean loginState;
    private ToastUtil toastUtil;
    private Counter counter;
    private LoadingDialog loadingDialog;
    private MatchGradeTabAdapter matchGradeTabAdapter;
    private MatchGradeListAdapter matchGradeListAdapter;
    private MatchGradeLayoutBinding binding;
    private int page = 1;
    private int totalPage = 0;//页数
    private String gameCode = "all";
    private RefreshLoadMore refreshLoadMore;
    private int listType = StatusVariable.NORMALLIST;//当前列表的类型 0：普通的列表 1:搜索列表
    private List<MatchGradeTabBean.ResultBean.TypesBean> typesList;


    public MatchGradeVm(MatchGradeFragment matchGradeFragment, MatchGradeLayoutBinding binding, MatchGradeTabAdapter matchGradeTabAdapter, MatchGradeListAdapter matchGradeListAdapter){
        this.matchGradeFragment = matchGradeFragment;
        this.matchGradeTabAdapter = matchGradeTabAdapter;
        this.matchGradeListAdapter = matchGradeListAdapter;
        this.binding = binding;
        init();//初始化
        click();//点击事件
        topList();
        listData(StatusVariable.REFRESH);
    }



    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(matchGradeFragment.getActivity());
        //初始化计数器
        counter = new Counter(this,1);
        //初始化刷新加载监听
        refreshLoadMore = new RefreshLoadMore(binding.smartRefreshlayout,this);
        //初始化Edittext
        new EdittextUtil(binding.editMatchGradeSearch,this);
        //软键盘搜索
        binding.editMatchGradeSearch.setOnKeyListener(onKeyListener);
        //初始化radioButton状态
        binding.radiobuttonAll.setTextColor(matchGradeFragment.getActivity().getResources().getColor(R.color.color_FF8F00));
        binding.indicatorView.setVisibility(View.VISIBLE);
        //初始化加载框
        loadingDialog = new LoadingDialog(matchGradeFragment.getActivity(), R.style.LoadingDialog);
        loadingDialog.show();

        //设置radioButton默认选中
        binding.radiobuttonAll.setChecked(true);
    }

    /**
     * 初始化顶部列表数据
     */
    public void topList(){


        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl);
        params.put("baseMethod", Method.HOMEPAGEMATCHGRADE);

        OkHttpUtil.getRequest(matchGradeFragment.getActivity(), params, new RequestCallBack<MatchGradeTabBean>() {
            @Override
            public void onSuccess(Response<MatchGradeTabBean> response) {
                MatchGradeTabBean matchGradeTabBean = response.body();
                if (!EmptyUtil.isEmpty(matchGradeTabBean)){
                    int resultCode = matchGradeTabBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MatchGradeTabBean.ResultBean resultBean = matchGradeTabBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            typesList = resultBean.getTypes();
                            if (!EmptyUtil.isEmpty(typesList)){
                                matchGradeTabAdapter.loadData(typesList);
                            }
                        }else{
                            Log.e("lxy-types-Bean","resultBean是空的");
                        }

                    }else{
                        toastUtil.centerToast(matchGradeTabBean.getResultMsg());
                    }

                }

            }

            @Override
            public MatchGradeTabBean parseNetworkResponse(String jsonResult) {

                MatchGradeTabBean matchGradeTabBean = JSON.parseObject(jsonResult, MatchGradeTabBean.class);

                return matchGradeTabBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
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
    /**
     * 初始化列表数据
     * @param loadtype
     */
    public void listData(final int loadtype){

        listType = StatusVariable.NORMALLIST;

        HashMap params = new HashMap();
        params.put("baseMethod",Method.HOMEPAGEMATCHGRADELIST);
        params.put("currentPage",page+"");
        params.put("pageSize","10");
        params.put("baseUrl",Config.baseUrl);
        params.put("code",gameCode);

        OkHttpUtil.getRequest(matchGradeFragment.getActivity(), params, new RequestCallBack<MatchGradeListBean>() {
            @Override
            public void onSuccess(Response<MatchGradeListBean> response) {
                MatchGradeListBean matchGradeListBean = response.body();
                if (!EmptyUtil.isEmpty(matchGradeListBean)){
                    int resultCode = matchGradeListBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MatchGradeListBean.ResultBean resultBean = matchGradeListBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            totalPage = resultBean.getTotalPage();
                            //空数据
                            if(totalPage > 0){
                                NullStateUtil.setNullState(binding.nulldata,false);
                            }else{
                                NullStateUtil.setNullState(binding.nulldata,true);
                            }

                            List<MatchGradeListBean.ResultBean.MatchsBean> matchs = resultBean.getMatchs();
                            if (!EmptyUtil.isEmpty(matchs)){
                                if (loadtype == StatusVariable.REFRESH){
                                    matchGradeListAdapter.loadData(matchs);
                                    binding.smartRefreshlayout.finishRefresh();
                                }else{
                                    matchGradeListAdapter.loadMoreData(matchs);
                                    binding.smartRefreshlayout.finishLoadMore();
                                }

                            }
                        }else{
                            Log.e("lxy-types-Bean","resultBean是空的");
                        }
                        page++;
                    }
                }
            }

            @Override
            public MatchGradeListBean parseNetworkResponse(String jsonResult) {

                MatchGradeListBean matchGradeListBean = JSON.parseObject(jsonResult, MatchGradeListBean.class);

                return matchGradeListBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
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

    /**
     * 搜索
     */
    private void searchListData(String editStr, final int loadType) {

        //设置radioButton为不选中
        binding.radiobuttonAll.setChecked(false);

        //把状态改为搜索状态
        listType = StatusVariable.SEARCHLIST;

        HashMap params = new HashMap();
        params.put("currentPage",page+"");
        params.put("pageSize","10");
        params.put("baseMethod",Method.MATCHSCORESEARCH);
        params.put("baseUrl",Config.baseUrl);
        params.put("keyword",editStr);


        OkHttpUtil.getRequest(matchGradeFragment.getActivity(), params, new RequestCallBack<MatchListSearchBean>() {
            @Override
            public void onSuccess(Response<MatchListSearchBean> response) {
                MatchListSearchBean matchListSearchBean = response.body();
                if (!EmptyUtil.isEmpty(matchListSearchBean)){
                    int resultCode = matchListSearchBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MatchListSearchBean.ResultBean resultBean = matchListSearchBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){

                            totalPage = resultBean.getTotalPage();

                            //空数据
                            if(totalPage > 0){
                                NullStateUtil.setNullState(binding.nulldata,false);
                            }else{
                                NullStateUtil.setNullState(binding.nulldata,true);
                            }

                            List<MatchListSearchBean.ResultBean.ListBean> list = resultBean.getList();

                            List<MatchGradeListBean.ResultBean.MatchsBean> matchList = new ArrayList<>();

                            for (int i = 0; i < list.size(); i++) {
                                MatchGradeListBean.ResultBean.MatchsBean matchsBean = new MatchGradeListBean.ResultBean.MatchsBean();
                                matchsBean.setEndTime(list.get(i).getEndTime());
                                matchsBean.setMatchCode(list.get(i).getMatchCode());
                                matchsBean.setMatchImg(list.get(i).getMatchImg());
                                matchsBean.setMatchName(list.get(i).getMatchName());
                                matchsBean.setStartTime(list.get(i).getStartTime());
                                matchList.add(matchsBean);
                            }
                            if (loadType == StatusVariable.REFRESH){
                                matchGradeListAdapter.loadData(matchList);
                                binding.smartRefreshlayout.finishRefresh();
                            }else{
                                matchGradeListAdapter.loadMoreData(matchList);
                                binding.smartRefreshlayout.finishLoadMore();
                            }

                        }
                        page++;
                    }else{
                        toastUtil.centerToast(matchListSearchBean.getResultMsg());
                    }
                }
            }

            @Override
            public MatchListSearchBean parseNetworkResponse(String jsonResult) {

                MatchListSearchBean matchListSearchBean = JSON.parseObject(jsonResult, MatchListSearchBean.class);

                return matchListSearchBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });



    }


    /**
     * 头像点击事件
     */
    public void matchGradeHeader(){

        if (!loginState){
            MyApplication.getInstance().setClickState(true);
            IntentUtil.startActivityForResult(matchGradeFragment.getActivity(),LoginActivity.class);
            return;
        }
        MainActivity activity = (MainActivity) matchGradeFragment.getActivity();
        if (!EmptyUtil.isEmpty(activity)){
            activity.tabState(StatusVariable.PERSONALCENTE);
        }

    }

    /**
     * 图片点击搜索
     */
    public void imgSearch(){

        page = 1;

        String edittextStr = binding.editMatchGradeSearch.getText().toString();

        searchListData(edittextStr,StatusVariable.REFRESH);

    }

    /**
     * 清除搜索框
     * @param
     */
    public void clearEdit(){

        binding.editMatchGradeSearch.setText("");

    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            loadingDialog.dismiss();
        }
    }

    /**
     * 键盘搜索点击事件
     */
    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                /*隐藏软键盘*/
                InputMethodManager inputMethodManager = (InputMethodManager) matchGradeFragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                //  binding.searchEdittext.setText("搜索");
                String text = binding.editMatchGradeSearch.getText().toString().trim();
                page = 1;
                searchListData(text, StatusVariable.REFRESH);//搜索

                return true;
            }
            return false;
        }
    };

    /**
     * 列表点击事件
     */
    private void click() {


        matchGradeTabAdapter.setItemClick(new MatchGradeTabAdapter.ItemClick() {
            @Override
            public void itemClick(MatchGradeTabBean.ResultBean.TypesBean typesBean, int position) {
                //设置当前点击的item为选中状态
                typesBean.setCheck(true);
                binding.recyclviewTab.smoothScrollToPosition(position);
                gameCode = typesBean.getGameCode();
                page = 1;
                loadingDialog.show();//显示加载框
                listData(StatusVariable.REFRESH);//加载数据
                clearTab(position);//清除tab状态
                //如果全部是选中状态则不选中
                if (binding.radiobuttonAll.isChecked()){
                    binding.radiobuttonAll.setChecked(false);
                }
                //刷新适配器
                matchGradeTabAdapter.notifyDataSetChanged();
            }
        });

        binding.radiobuttonAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //如果radioButton选中则去重新请求所有数据，并选中全部
                if (binding.radiobuttonAll.isChecked()){
                    binding.radiobuttonAll.setTextColor(matchGradeFragment.getActivity().getResources().getColor(R.color.color_FF8F00));
                    binding.indicatorView.setVisibility(View.VISIBLE);
                    gameCode = "all";
                    clearAllTab();
                    page = 1;
                    loadingDialog.show();
                    listData(StatusVariable.REFRESH);
                    binding.recyclviewTab.smoothScrollToPosition(0);
                }else{
                    binding.radiobuttonAll.setTextColor(matchGradeFragment.getActivity().getResources().getColor(R.color.color_333333));
                    binding.indicatorView.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * 查看全部赛事点击事件
     */
    public void lookallMatch(){

        Intent intent = new Intent(matchGradeFragment.getActivity(), SelectMatchProgramActivity.class);
        intent.putExtra("typesList", (Serializable) typesList);
        matchGradeFragment.startActivityForResult(intent, StatusVariable.INTENTCODE);


    }


    /**
     * 清除所有tab的选中状态
     *
     */
    private void clearAllTab() {

        for (int i = 0; i < matchGradeTabAdapter.mList.size(); i++) {

            matchGradeTabAdapter.mList.get(i).setCheck(false);

        }
        matchGradeTabAdapter.notifyDataSetChanged();

    }

    /**
     * 清除选中状态
     * @param position
     */
    private void clearTab(int position) {

        for (int i = 0; i < matchGradeTabAdapter.mList.size(); i++) {
            if (position != i) {
                matchGradeTabAdapter.mList.get(i).setCheck(false);
            }
        }

    }

    /**
     * 返回传处理逻辑
     *
     * @param typesList
     */
    private void selectList(List<MatchGradeTabBean.ResultBean.TypesBean> typesList) {

        //for循环所有title数据
        for (int i = 0; i < typesList.size(); i++) {
            if (typesList.get(i).isCheck()) {
                gameCode = typesList.get(i).getGameCode();//更新code
                page = 1;//第一页
                loadingDialog.show();
                listData(StatusVariable.REFRESH);//重新请求下方列表数据
                binding.recyclviewTab.smoothScrollToPosition(i);//定位到下标位置
            }
        }

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String searchStr = s.toString();
        if (searchStr.length() > 0){
            binding.imgClear.setVisibility(View.VISIBLE);
        }else{
            binding.imgClear.setVisibility(View.GONE);
        }
    }

    /**
     * 刷新
     * @param refreshLayout
     */
    @Override
    public void refresh(RefreshLayout refreshLayout,int loadtype) {
        page = 1;
        if (listType == StatusVariable.NORMALLIST){
            listData(loadtype);
        }else{
            searchListData(binding.editMatchGradeSearch.getText().toString().trim(),loadtype);
        }

    }

    /**
     * 加载
     * @param refreshLayout
     */
    @Override
    public void loadMore(RefreshLayout refreshLayout,int loadtype) {
        if (!EmptyUtil.isEmpty(totalPage)){

            if (page <= totalPage){
                if(listType == StatusVariable.NORMALLIST){
                    //普通
                    listData(loadtype);
                }else{
                    //搜索
                    searchListData(binding.editMatchGradeSearch.getText().toString(),loadtype);
                }
            }else{
                refreshLayout.finishLoadMoreWithNoMoreData();
                refreshLayout.setNoMoreData(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == StatusVariable.MATCHGRADECODE) {
            if (data != null) {
                binding.radiobuttonAll.setChecked(false);
                typesList = (List<MatchGradeTabBean.ResultBean.TypesBean>) data.getSerializableExtra("typesList");
                matchGradeTabAdapter.loadData(typesList);
                selectList(typesList);
            }
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        loginState = MyApplication.getInstance().getLoginState();
        matchGradeFragment.initUserInfo();
    }
}