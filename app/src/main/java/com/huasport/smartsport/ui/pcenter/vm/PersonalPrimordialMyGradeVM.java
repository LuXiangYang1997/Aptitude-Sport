package com.huasport.smartsport.ui.pcenter.vm;

import android.databinding.ObservableField;

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
import com.huasport.smartsport.databinding.ActivityPersonalPrimordialMyGradeBinding;
import com.huasport.smartsport.ui.pcenter.adapter.PersonalPrimordialMyGradeAdapter;
import com.huasport.smartsport.ui.pcenter.bean.PersonalPrimordialMyGradeBean;
import com.huasport.smartsport.ui.pcenter.view.PersonalPrimordialMyGradeActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.util.HashMap;
import java.util.List;


public class PersonalPrimordialMyGradeVM extends BaseViewModel implements CounterListener, RefreshLoadMoreListener {

    private PersonalPrimordialMyGradeActivity personalPrimordialMyGradeActivity;
    private PersonalPrimordialMyGradeAdapter personalPrimordialMyGradeAdapter;
    private int page = 1;
    private String token;
    ActivityPersonalPrimordialMyGradeBinding binding;
    public ObservableField<Integer> toTalPage = new ObservableField<>(0);
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private int totalPage = 0;

    public PersonalPrimordialMyGradeVM(PersonalPrimordialMyGradeActivity personalPrimordialMyGradeActivity, PersonalPrimordialMyGradeAdapter personalPrimordialMyGradeAdapter) {
        this.personalPrimordialMyGradeActivity = personalPrimordialMyGradeActivity;
        this.personalPrimordialMyGradeAdapter = personalPrimordialMyGradeAdapter;
        binding = personalPrimordialMyGradeActivity.getBinding();
        init();
        initData(StatusVariable.REFRESH);
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(personalPrimordialMyGradeActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(personalPrimordialMyGradeActivity, R.style.LoadingDialog);
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
     * 初始化数据
     * @param loadType
     */
    private void initData(final int loadType) {

        final HashMap params = new HashMap();
        params.put("method", Method.MYGRADELIST);
        params.put("pageSize", 10 + "");
        params.put("token", token);
        params.put("currentPage", page + "");
        params.put("t", String.valueOf(System.currentTimeMillis()));
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(personalPrimordialMyGradeActivity, params, new RequestCallBack<PersonalPrimordialMyGradeBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<PersonalPrimordialMyGradeBean> response) {
                PersonalPrimordialMyGradeBean personalPrimordialMyGradeBean = response.body();
                if (!EmptyUtil.isEmpty(personalPrimordialMyGradeBean)){
                    int resultCode = personalPrimordialMyGradeBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        PersonalPrimordialMyGradeBean.ResultBean resultBean = personalPrimordialMyGradeBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            totalPage = resultBean.getTotalPage();


                            if (totalPage == 0) {
                                NullStateUtil.setNullState(binding.nulldata, true);
                                return;
                            } else {
                                NullStateUtil.setNullState(binding.nulldata,  false);
                            }
                            List<PersonalPrimordialMyGradeBean.ResultBean.ListBean> list = resultBean.getList();
                            for (int i = 0; i < list.size(); i++) {
                                List<PersonalPrimordialMyGradeBean.ResultBean.ListBean.ComListBean> comList = list.get(i).getComList();
                                String matchImg = list.get(i).getMatch().getMatchImg();
                                if (comList.size() > 0) {

                                    for (int x = 0; x < comList.size(); x++) {

                                        comList.get(x).getCompetition().setMatchUrl(matchImg);

                                    }
                                }
                                if (loadType == StatusVariable.LOADMORE) {
                                    binding.smartRefreshlayout.finishLoadMore();
                                    personalPrimordialMyGradeAdapter.loadMoreData(list);
                                } else {
                                    personalPrimordialMyGradeAdapter.loadData(list);
                                    binding.smartRefreshlayout.finishRefresh();
                                }


                        }
                    }
                        page++;
                }
            }
            }

            @Override
            public PersonalPrimordialMyGradeBean parseNetworkResponse(String jsonResult) {
                PersonalPrimordialMyGradeBean personalPrimordialMyGradeBean = JSON.parseObject(jsonResult, PersonalPrimordialMyGradeBean.class);

                return personalPrimordialMyGradeBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
                binding.smartRefreshlayout.finishRefresh();
                binding.smartRefreshlayout.finishLoadMore();
            }
        });

    }

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
