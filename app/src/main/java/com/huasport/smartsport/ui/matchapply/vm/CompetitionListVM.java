package com.huasport.smartsport.ui.matchapply.vm;

import android.content.Intent;
import android.databinding.ObservableField;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.ActivityCompetitionListBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.ui.matchapply.adapter.CityListAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.CompetitionListAdapter;
import com.huasport.smartsport.ui.matchapply.bean.CityListBean;
import com.huasport.smartsport.ui.matchapply.bean.CompetitionListBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupEventsBean;
import com.huasport.smartsport.ui.matchapply.bean.OrderBean;
import com.huasport.smartsport.ui.matchapply.view.CompetitionListActivity;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/8.
 */
//选择赛事列表VM
public class CompetitionListVM extends BaseViewModel implements CounterListener, RefreshLoadMoreListener {

    private MyApplication application = MyApplication.getInstance();
    private CompetitionListActivity competitionListActivity;
    private CompetitionListAdapter competitionListAdapter;
    private final ActivityCompetitionListBinding binding;
    private List<CompetitionListBean.ResultBean.SitesBean> sites;
    private Intent intent;
    private String matchName;
    private String matchStartTime;
    private String matchEndTime;
    private int totalPage = 0;
    public ObservableField<String> district = new ObservableField<>();//区
    public ObservableField<String> province = new ObservableField<>();//省
    public ObservableField<String> citys = new ObservableField<>();//市
    public ObservableField<String> matchState = new ObservableField<>("");
    public ObservableField<Integer> groupLimit = new ObservableField<>(0);
    private TabLayout mTablayout;
    private TextView addressText;
    private int page = 1;
    private double longitude;
    private double latitude;
    private boolean cbSelectStatus = false;
    private LinearLayout address_layout;
    private List<GroupEventsBean.ResultBean.GroupsBean.EventsBean> matchList = new ArrayList();
    private String gameCode = "";
    private String matchCode = "";
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private String token = "";
    private PopupWindow popupWindow;
    private List<String> provinceList;

    public CompetitionListVM(CompetitionListActivity competitionListActivity, CompetitionListAdapter competitionListAdapter) {
        this.competitionListActivity = competitionListActivity;
        this.competitionListAdapter = competitionListAdapter;
        binding = competitionListActivity.getBinding();
        init();
        initHeaderView();
        locationData(StatusVariable.REFRESH);
    }

    /**
     * 初始化
     */
    private void init() {

        intent = competitionListActivity.getIntent();
        matchCode = intent.getStringExtra("matchCode");
        //拿到数据
        matchName = intent.getStringExtra("MatchName");
        matchStartTime = intent.getStringExtra("MatchStartTime");
        matchEndTime = intent.getStringExtra("matchEndTime");

        //获取Code
        gameCode = competitionListActivity.getIntent().getStringExtra("gameCode");
        //初始化Toast
        toastUtil = new ToastUtil(competitionListActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(competitionListActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //初始化加载刷新
        new RefreshLoadMore(binding.smartRefreshlayout, this);
        //获取token
        UserBean userBean = application.getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        //弹出加载框
        loadingDialog.show();
    }

    /**
     * 初始化header
     */
    private void initHeaderView() {

        //添加Header
        View group_header = LayoutInflater.from(competitionListActivity).inflate(R.layout.matchgroup_heeader_layout, null);
        binding.selectSiteRecycler.addHeaderView(group_header);

        //格式化日期
        String time = DateUtil.dateConvert(matchStartTime, matchEndTime);

        TextView name = group_header.findViewById(R.id.program_name);//名字
        TextView date = group_header.findViewById(R.id.program_date);//日期
        LinearLayout detailLinearlayout = group_header.findViewById(R.id.matchDetail_linear);
        //跳转到详情
        detailLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailintent = new Intent(competitionListActivity, MatchIntroduceActivity.class);
                detailintent.putExtra("matchCode", matchCode);
                detailintent.putExtra("type", "other");
                competitionListActivity.startActivity(detailintent);
            }
        });

        addressText = group_header.findViewById(R.id.addressText);
        address_layout = group_header.findViewById(R.id.address_layout);
        name.setText(matchName + ",查看详情");
        date.setText(time);
        //点击弹出PopWindown
        address_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAddressList(view);
            }
        });
    }

    /**
     * 选择省市区
     * @param view
     */
    private void selectedAddressList(View view) {

        province.set("");
        citys.set("");
        district.set("");

        if (cbSelectStatus) {
            toastUtil.showTopSnackBar("至少选择一个分组");
            return;
        }
        if (competitionListAdapter.selectStatus.get()) {
            toastUtil.showTopSnackBar("请取消分赛场后，重新选择地区");
            return;
        }
        //地区选择PopWindown
        final View popView = LayoutInflater.from(competitionListActivity).inflate(R.layout.citylist_pop_layout, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(popView);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);

        Util.backgroundAlpha(competitionListActivity, 0.5f);

        mTablayout = popView.findViewById(R.id.cityList_tablayout);
        RecyclerView cityListRecyclerView = popView.findViewById(R.id.cityList_recyclerView);

        View cancel = popView.findViewById(R.id.cancel);
        View submit = popView.findViewById(R.id.submit);

        //添加分割线
        LinearLayout linearLayout = (LinearLayout) mTablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(competitionListActivity, R.drawable.divider_vertical_bg));
        //设置Adapter
        final CityListAdapter cityListAdapter = new CityListAdapter(competitionListActivity);
        cityListRecyclerView.setLayoutManager(new LinearLayoutManager(competitionListActivity));
        cityListRecyclerView.setAdapter(cityListAdapter);

        address("province", "", cityListAdapter);
        //recyclerview点击事件
        cityListAdapter.setCityItemClick(new CityListAdapter.CityItemClick() {
            @Override
            public void cityitemClick(BaseViewHolder baseViewHolder, int position) {

                String strName = provinceList.get(position);

                if (mTablayout.getSelectedTabPosition() == StatusVariable.PROVINCECODE) {
                    province.set(strName);
                    mTablayout.getTabAt(StatusVariable.PROVINCECODE).setText(province.get().toString());
                    mTablayout.getTabAt(StatusVariable.CITYCODE).select();
                } else if (mTablayout.getSelectedTabPosition() == StatusVariable.CITYCODE) {
                    citys.set(strName);
                    mTablayout.getTabAt(StatusVariable.CITYCODE).setText(citys.get().toString());
                    mTablayout.getTabAt(StatusVariable.DISTRICTCODE).select();
                } else {
                    district.set(strName);
                    mTablayout.getTabAt(StatusVariable.DISTRICTCODE).setText(district.get().toString());
                }

            }
        });
        //切换Tablayout：如果是选中的第0个tab则默认请求省，2:市 3: 区
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == StatusVariable.PROVINCECODE) {
                    address("province", "", cityListAdapter);
                } else if (tab.getPosition() == StatusVariable.CITYCODE) {
                    if (province.get() == null || province.get().toString().isEmpty()) {
                        toastUtil.showTopSnackBar("请选择省");
                    } else {
                        address("city", province.get().toString(), cityListAdapter);
                    }
                } else {
                    if (province.get() == null || province.get().toString().isEmpty()) {
                        toastUtil.showTopSnackBar("请选择市");
                    } else {
                        address("area", citys.get().toString(), cityListAdapter);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (province.get() != null && !province.get().toString().isEmpty()) {
                    if (citys.get() != null && !citys.get().toString().isEmpty()) {
                        if (district.get() != null && !province.get().toString().isEmpty()) {
                            addressText.setText(province.get().toString() + "-" + citys.get().toString() + "-" + district.get().toString());
                        } else {
                            addressText.setText(province.get().toString() + "-" + citys.get().toString());
                        }
                    } else {
                        addressText.setText(province.get().toString().trim());
                    }
                }
                popupWindow.dismiss();
                competitionListAdapter.clearData();
                locationData(StatusVariable.REFRESH);
            }
        });
        //消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(competitionListActivity, 1.0f);
            }
        });

    }

    /**
     * 获取省市区数据
     * @param areaType
     * @param parentArea
     * @param cityListAdapter
     */
    private void address(String areaType, String parentArea, final CityListAdapter cityListAdapter) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.ADDRESS);
        params.put("matchCode", matchCode);
        params.put("areaType", areaType);
        params.put("parentArea", parentArea);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(competitionListActivity, params, new RequestCallBack<CityListBean>() {
            @Override
            public void onSuccess(Response<CityListBean> response) {
                CityListBean cityListBean = response.body();
                if(!EmptyUtil.isEmpty(cityListBean)){
                    int resultCode = cityListBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        CityListBean.ResultBean resultBean = cityListBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            provinceList = resultBean.getList();
                            if (provinceList.size() == 0) {
                                toastUtil.centerToast("暂无地区");
                            }
                            //设置数据
                            cityListAdapter.loadData(provinceList);

                        }
                    }


                }
            }

            @Override
            public CityListBean parseNetworkResponse(String jsonResult) {
                CityListBean cityListBean = JSON.parseObject(jsonResult, CityListBean.class);

                return cityListBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.showTopSnackBar(msg);
                }
            }
        });
    }

    /**
     *重置数据的状态
     */
    public void reSetDataStatus() {
        locationData(StatusVariable.REFRESH);
        for (int i = 0; i < sites.size(); i++) {
            sites.get(i).setCheck(false);
        }
    }

    /**
     * 下一步
     */
    public void nextStep() {
        binding.statusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchList.clear();
                List<String> itemCodelist = competitionListAdapter.getItemCodelist();//存储itemCode
                List<String> itemTypeList = competitionListAdapter.getItemTypeList();//存储报名类型的集合
                String itemCode = Util.strbuilder(itemCodelist);
                if (itemCodelist.size() > 0) {
                    //团队和个人同时包含
                    if (itemTypeList.contains("group") && itemTypeList.contains("personal")) {

                        toastUtil.showTopSnackBar("不能同时报名团体赛和个人赛");
                        //只包含团队
                    } else if (itemTypeList.contains("group") && !itemTypeList.contains("personal")) {

                        matchState.set("group");

                        SharedPreferencesUtil.setParam(competitionListActivity, "matchType", "group");

                        List<String> siteCode = competitionListAdapter.getSiteCode();

                        createOrder(itemCode, siteCode.get(0));
                        //只包含个人
                    } else if (!itemTypeList.contains("group") && itemTypeList.contains("personal")) {

                        SharedPreferencesUtil.setParam(competitionListActivity, "matchType", "personal");

                        matchState.set("personal");

                        List<String> siteCode = competitionListAdapter.getSiteCode();

                        createOrder(itemCode, siteCode.get(0));
                    }
                } else {
                    toastUtil.showTopSnackBar("请选择比赛项");
                }
            }
        });

    }


    /**
     * 创建订单
     *
     * @param itemCode
     * @param siteCode
     */
    private void createOrder(String itemCode, String siteCode) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.CREATEORDER);
        params.put("itemCodes", itemCode);
        params.put("siteCode", siteCode);
        params.put("terminal", "ANDROID");//终端类型
        params.put("token", token);
        params.put("t", String.valueOf(System.currentTimeMillis()));
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.postRequest(competitionListActivity, params, new RequestCallBack<OrderBean>() {
            @Override
            public void onSuccess(Response<OrderBean> response) {
                OrderBean orderBean = response.body();
                if(!EmptyUtil.isEmpty(orderBean)){
                    int resultCode = orderBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        OrderBean.ResultBean resultBean = orderBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            String orderCode = resultBean.getOrderCode();

                            //团队报名
                    if (matchState.get().equals("group")) {
//                        Intent intent = new Intent(competitionListActivity, GroupApplyActivity.class);
//                        intent.putExtra("orderCode", orderBean.getResult().getOrderCode());
//                        intent.putExtra("matchCode", matchCode);
//                        intent.putExtra("matchName", matchName);
//                        intent.putExtra("matchStartTime", matchStartTime);
//                        intent.putExtra("matchEndTime", matchEndTime);
//                        intent.putExtra("groupLimit", competitionListAdapter.limit.get());
//                        intent.putExtra("type", "normal_apply");
//                        competitionListActivity.startActivity(intent);
                        //个人报名
                    } else if (matchState.get().equals("personal")) {
//                        Intent orderintent = new Intent(competitionListActivity, FillRegistrationFormActivity.class);
//                        orderintent.putExtra("orderCode", orderBean.getResult().getOrderCode());
//                        orderintent.putExtra("matchCode", matchCode);
//                        orderintent.putExtra("matchName", matchName);
//                        orderintent.putExtra("matchStartTime", matchStartTime);
//                        orderintent.putExtra("matchEndTime", matchEndTime);
//                        orderintent.putExtra("matchimg", matchimg);
//                        orderintent.putExtra("matchstatus", "normal_apply");//正常流程
//                        orderintent.putExtra("eventList", (Serializable) matchList);
//                        competitionListActivity.startActivity(orderintent);
//                        matchList.clear();

                    } else {
                        toastUtil.showTopSnackBar(orderBean.getResultMsg());
                    }
                    }
                    }else if (resultCode == StatusVariable.NOLOGIN){
                        IntentUtil.startActivity(competitionListActivity, LoginActivity.class);
                        return;
                    }else if (resultCode == StatusVariable.NOBIND){

                        BaseDialog.show(competitionListActivity, "提示", "您还未绑定手机号", "去绑定", "取消", false, false, 0, new DialogCallBack() {
                            @Override
                            public void submit(CustomDialog.Builder customDialog) {
                                customDialog.dismiss();//退出登录
                                IntentUtil.startActivity(competitionListActivity, BindPhoneActivity.class);
                            }

                            @Override
                            public void cancel(CustomDialog.Builder customDialog) {
                                customDialog.dismiss();
                            }
                        });
                        return;
                    }
                }
            }

            @Override
            public OrderBean parseNetworkResponse(String jsonResult) {
                OrderBean orderBean = JSON.parseObject(jsonResult, OrderBean.class);
                return orderBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.showTopSnackBar(msg);
                }
            }
        });


//        OkhttpUtils.postRequest(competitionListActivity, createorder, new BaseHttpCallBack<OrderBean>(competitionListActivity, true) {
//
//
//            @Override
//            public OrderBean parseNetworkResponse(String jsonResult) throws Exception {
//                OrderBean orderBean = JSON.parseObject(jsonResult, OrderBean.class);
//                return orderBean;
//            }
//
//            @Override
//            public void onSuccess(OrderBean orderBean, Call call, Response response) {
//
//                if (orderBean != null) {
//                    if (orderBean.getResultCode() == StatusVariable.NO_LOGIN) {//未登录
//                        competitionListActivity.startActivity2(LoginActivity.class);
//                        return;
//                    }
//
//                    if (orderBean.getResultCode() == StatusVariable.NOBIND) {
//
//                        BaseDialog.show(competitionListActivity, "提示", "您还未绑定手机号", "去绑定", "取消", false, false, 0, new DialogCallBack() {
//                            @Override
//                            public void submit(CustomDialog.Builder customDialog) {
//                                customDialog.dismiss();//退出登录
//                                competitionListActivity.startActivity2(BindActivity.class);
//                            }
//
//                            @Override
//                            public void cancel(CustomDialog.Builder customDialog) {
//                                customDialog.dismiss();
//                            }
//                        });
//                        return;
//                    }
//                    //团队报名
//                    if (matchState.get().equals("group")) {
//                        Intent intent = new Intent(competitionListActivity, GroupApplyActivity.class);
//                        intent.putExtra("orderCode", orderBean.getResult().getOrderCode());
//                        intent.putExtra("matchCode", matchCode);
//                        intent.putExtra("matchName", matchName);
//                        intent.putExtra("matchStartTime", matchStartTime);
//                        intent.putExtra("matchEndTime", matchEndTime);
//                        intent.putExtra("groupLimit", competitionListAdapter.limit.get());
//                        intent.putExtra("type", "normal_apply");
//                        competitionListActivity.startActivity(intent);
//                        //个人报名
//                    } else if (matchState.get().equals("personal")) {
//                        Intent orderintent = new Intent(competitionListActivity, FillRegistrationFormActivity.class);
//                        orderintent.putExtra("orderCode", orderBean.getResult().getOrderCode());
//                        orderintent.putExtra("matchCode", matchCode);
//                        orderintent.putExtra("matchName", matchName);
//                        orderintent.putExtra("matchStartTime", matchStartTime);
//                        orderintent.putExtra("matchEndTime", matchEndTime);
//                        orderintent.putExtra("matchimg", matchimg);
//                        orderintent.putExtra("matchstatus", "normal_apply");//正常流程
//                        orderintent.putExtra("eventList", (Serializable) matchList);
//                        competitionListActivity.startActivity(orderintent);
//                        matchList.clear();
//
//                    } else {
//                        TopSnackbarUtils.showTopSnackBar(competitionListActivity, orderBean.getResultMsg());
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                if (code.equals("204")) {
//                    competitionListActivity.startActivity2(LoginActivity.class);
//
//                }
//            }
//        });

    }

    /**
     * 获取指定位置的数据
     * @param type
     */
    public void locationData(final int type) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.LOCATION);
        if (!EmptyUtil.isEmpty(district.get())) {
            params.put("areaType", "area");
        } else if (!EmptyUtil.isEmpty(citys.get())) {
            params.put("areaType", "city");
        } else if (!EmptyUtil.isEmpty(province.get())) {
            params.put("areaType", "province");
        } else {
            params.put("areType", "");
        }
        if (!EmptyUtil.isEmpty(district.get())) {
            params.put("areaName", district.get().toString());
        } else if (!EmptyUtil.isEmpty(citys.get())) {
            params.put("areaName", citys.get().toString());
        } else if (!EmptyUtil.isEmpty(province.get())) {
            params.put("areaName", province.get().toString());
        } else {
            params.put("areaName", "");
        }
        if (!EmptyUtil.isEmpty(MyApplication.getInstance().getLocationBean())) {
            //纬度
            latitude = MyApplication.getInstance().getLocationBean().getLatitude();
            //经度
            longitude = MyApplication.getInstance().getLocationBean().getLongitude();
        }
        params.put("matchCode", matchCode);
        params.put("lon", latitude + "");
        params.put("lat", longitude + "");
        params.put("currentPage", page + "");
        params.put("pageSize", "10");
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(competitionListActivity, params, new RequestCallBack<CompetitionListBean>() {
            @Override
            public void onSuccess(Response<CompetitionListBean> response) {
                CompetitionListBean competitionListBean = response.body();
                if(!EmptyUtil.isEmpty(competitionListBean)){
                    int resultCode = competitionListBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        CompetitionListBean.ResultBean resultBean = competitionListBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            totalPage = competitionListBean.getResult().getTotalPage();
                                sites = competitionListBean.getResult().getSites();
                                if (type == StatusVariable.LOADMORE) {
                                    binding.smartRefreshlayout.finishLoadMore();
                                    competitionListAdapter.loadMoreData(competitionListBean.getResult().getSites());
                                } else {
                                    binding.smartRefreshlayout.finishRefresh();
                                    competitionListAdapter.loadData(competitionListBean.getResult().getSites());
                                }
                        }
                        page++;
                    }
                }

            }

            @Override
            public CompetitionListBean parseNetworkResponse(String jsonResult) {
                CompetitionListBean competitionListBean = JSON.parseObject(jsonResult, CompetitionListBean.class);

                return competitionListBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.showTopSnackBar(msg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
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

    /**
     * 刷新
     * @param refreshLayout
     * @param type
     */
    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        page = 1;
        binding.smartRefreshlayout.setNoMoreData(false);
        locationData(type);
    }

    /**
     * 加载
     * @param refreshLayout
     * @param type
     */
    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {
        if (page <= totalPage) {
            binding.smartRefreshlayout.setNoMoreData(false);
            locationData(type);
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }
    }
}


