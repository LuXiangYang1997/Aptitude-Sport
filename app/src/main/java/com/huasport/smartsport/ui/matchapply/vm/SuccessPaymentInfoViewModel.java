package com.huasport.smartsport.ui.matchapply.vm;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;

import com.huasport.smartsport.api.Method;

import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.ActivitySuccessPaymentBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.ui.matchapply.adapter.PersonalAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.RegitrationSuccessAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.SignUpAdapter;
import com.huasport.smartsport.ui.matchapply.bean.AthletesMessageBean;
import com.huasport.smartsport.ui.matchapply.bean.CancelResultBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupEventsBean;
import com.huasport.smartsport.ui.matchapply.bean.ProgramMessageBean;
import com.huasport.smartsport.ui.matchapply.bean.RegistrationInfoBean;
import com.huasport.smartsport.ui.matchapply.view.PayMentOrderActivty;
import com.huasport.smartsport.ui.matchapply.view.SuccessPaymentInfoActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.zhouyou.recyclerview.swipemenu.SwipeMenuRecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/28.
 */

public class SuccessPaymentInfoViewModel extends BaseViewModel implements CounterListener {

    private SuccessPaymentInfoActivity mActivity;
    private RecyclerView mSignRecyclerView;//报名项目
    private SignUpAdapter mAdapter;//报名项目适配器
    private RecyclerView mPersonalRecyclerView;//个人信息
    private List<AthletesMessageBean.ResultBean.PropertiesBean> propertiesBeanList = new ArrayList<>();
    List<AthletesMessageBean.ResultBean.PropertiesBean> infoList = new ArrayList<>();
    private PersonalAdapter mPersonalAdapter;
    private List<GroupEventsBean.ResultBean.GroupsBean.EventsBean> eventList = new ArrayList<>();//跳支付需要带的参数
    private RegitrationSuccessAdapter regitrationSuccessAdapter;
    public ObservableField<String> frontUrl = new ObservableField<>("");
    public ObservableField<String> contractUrl = new ObservableField<>("");
    private HashMap imgUrl = new HashMap();
    private String orderCode, orderStatus;
    private RegistrationInfoBean.ResultBean resultBean = new RegistrationInfoBean.ResultBean();
    private ActivitySuccessPaymentBinding binding;
    private List<RegistrationInfoBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> players;
    public ObservableField<String> orderStatusStr = new ObservableField<>("");
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private MyApplication application = MyApplication.getInstance();
    private String token = "";


    public SuccessPaymentInfoViewModel(SuccessPaymentInfoActivity mActivity, RecyclerView mSignRecyclerView, RecyclerView mPersonalRecyclerView, RegitrationSuccessAdapter regitrationSuccessAdapter) {
        this.mActivity = mActivity;
        this.mSignRecyclerView = mSignRecyclerView;
        this.mPersonalRecyclerView = mPersonalRecyclerView;
        this.regitrationSuccessAdapter = regitrationSuccessAdapter;
        binding = mActivity.getBinding();
        init();
        initView();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {
        orderCode = mActivity.getIntent().getStringExtra("orderCode");//订单ID
        orderStatus = mActivity.getIntent().getStringExtra("orderStatus");//订单状态
        //初始化Toast
        toastUtil = new ToastUtil(mActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(mActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
//        //弹出加载框
//        loadingDialog.show();
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    private void initView() {
        //个人信息
        mPersonalAdapter = new PersonalAdapter(mActivity);
        mPersonalRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mPersonalRecyclerView.setAdapter(mPersonalAdapter);
        //报名项目
        mAdapter = new SignUpAdapter(mActivity);
        mSignRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mSignRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 取消报名
     */
    public void cancelOrder(){


        BaseDialog.show(mActivity, "提示", "您确定要取消报名吗？", "确定", "取消", false, false,
                0, new DialogCallBack() {
                    @Override
                    public void submit(CustomDialog.Builder customDialog) {

                        CancelApply();
                    }

                    @Override
                    public void cancel(CustomDialog.Builder customDialog) {
                        customDialog.dismiss();
                    }
                });
    }

    /**
     * 取消报名
     */
    private void CancelApply() {

        HashMap params = new HashMap<String, String>();
        params.put("token", MyApplication.getInstance().getUserBean().getToken());//token
        params.put("orderCode", orderCode);//订单ID
        params.put("baseMethod", Method.CANCELORDER);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(mActivity, params, new RequestCallBack<CancelResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<CancelResultBean> response) {
                CancelResultBean cancelResultBean = response.body();
                if (!EmptyUtil.isEmpty(cancelResultBean)){
                    int resultCode = cancelResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        toastUtil.centerToast("取消报名成功");
                        mActivity.finish();
                    }else{
                        toastUtil.centerToast("取消报名失败");
                    }
                }
            }

            @Override
            public CancelResultBean parseNetworkResponse(String jsonResult) {
                CancelResultBean cancelResultBean = JSON.parseObject(jsonResult, CancelResultBean.class);
                return cancelResultBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    //数据转化，为了跳转到报名表
    private void conversionData(List<RegistrationInfoBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> players) {

        for (RegistrationInfoBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean playerVosBean : players) {
            AthletesMessageBean.ResultBean.PropertiesBean propertiesBean = new AthletesMessageBean.ResultBean.PropertiesBean();
            propertiesBean.setVal((String) playerVosBean.getVal());
            propertiesBean.setAttributeName(playerVosBean.getAttributeName());
            propertiesBean.setCnname(playerVosBean.getCnname());
            propertiesBean.setIsRequired(playerVosBean.isIsRequired());
            propertiesBean.setIsShow(playerVosBean.isIsShow());
            propertiesBean.setParams(playerVosBean.getParams());
            propertiesBean.setType(playerVosBean.getType());
            if (!propertiesBean.getCnname().equals("头像")) {
                propertiesBeanList.add(propertiesBean);
            }
        }

    }
    /**
     * 初始化数据
     */
    private void initData() {

        switch (orderStatus) {
            case StatusVariable.WAITPAY://待支付
                mActivity.stayPaymentStatus();
                break;
            case StatusVariable.SUCCESSAPPLY://成功
                mActivity.successStatus();
                break;
        }

        HashMap params = new HashMap<String, String>();
        params.put("token", token);//token
        params.put("orderCode", orderCode);//订单状、态
        params.put("baseMethod", Method.SIGININFO);
        params.put("baseUrl", Config.baseUrl);
        Log.e("报名成功params====>>", params.toString());
        
        
        OkHttpUtil.getRequest(mActivity, params, new RequestCallBack<RegistrationInfoBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<RegistrationInfoBean> response) {
                RegistrationInfoBean registrationInfoBean = response.body();
                if (!EmptyUtil.isEmpty(registrationInfoBean)){
                    int resultCode = registrationInfoBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        RegistrationInfoBean.ResultBean resultBean = registrationInfoBean.getResult();
                        if(!EmptyUtil.isEmpty(resultBean)){
                            orderStatusStr.set(resultBean.getApplyStatus());

                        if (orderStatus.equals(StatusVariable.WAITPAY)) {
                            initOrderStatus();
                        }

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.valueOf(resultBean.getOrderDetail().getOrderTime()));
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式
                        String date = sf.format(calendar.getTime());

                        binding.orderTime.setText(date);

                        if (!EmptyUtil.isEmpty(resultBean.getOrderDetail())) {
                            RegistrationInfoBean.ResultBean.OrderDetailBean orderDetail = resultBean.getOrderDetail();
                            String remark = orderDetail.getRemark();
                            String orderCode = orderDetail.getOrderCode();
                            if(!EmptyUtil.isEmpty(remark)){
                                binding.tvRemark.setText(remark);
                            }
                            if(!EmptyUtil.isEmpty(orderCode)){
                                binding.tvOrderCode.setText(orderCode);
                            }
                            binding.tvSignAmount.setText("" + resultBean.getOrderDetail().getOrderAmountStr());//订单总金额
                            mAdapter.loadMoreData(resultBean.getOrderDetail().getApplys());//报名项目数据
                            resultBean = resultBean;
                        }
//                        resultBean.getOrderDetail().getTeams().get();
                        /**提交信息成功后，跳转支付需要带过去的参数*/
                        List<RegistrationInfoBean.ResultBean.OrderDetailBean.ApplysBean> applyList = resultBean.getOrderDetail().getApplys();
                        for (RegistrationInfoBean.ResultBean.OrderDetailBean.ApplysBean applysBean : applyList) {
                            applysBean.setTitle(resultBean.getTitle());
                        }

                        if (applyList.size() > 0) {
                            binding.erweima.setVisibility(View.VISIBLE);
                            regitrationSuccessAdapter.loadData(applyList);
                        } else {
                            binding.erweima.setVisibility(View.GONE);
                        }

                        List<RegistrationInfoBean.ResultBean.OrderDetailBean.TeamsBean> teams = resultBean.getOrderDetail().getTeams();

                        for (int a = 0; a < applyList.size(); a++) {
                            GroupEventsBean.ResultBean.GroupsBean.EventsBean eventBean = new GroupEventsBean.ResultBean.GroupsBean.EventsBean();
                            ProgramMessageBean programMessageBean = new ProgramMessageBean();
                            eventBean.setEntryFeeStr(applyList.get(a).getApplyAmountStr());
                            programMessageBean.setGroupName((String) applyList.get(a).getMatchName());
                            programMessageBean.setProgramName(applyList.get(a).getEventName());
                            eventBean.setProgramMessageBean(programMessageBean);
                            eventList.add(eventBean);
                        }
                        /**
                         * 判断数据，如果数据里面"val"不为空的话，就添加进集合，反之则不添加
                         * */
                        RegistrationInfoBean.ResultBean.OrderDetailBean.TeamsBean teamsBean = teams.get(0);
                        players = teamsBean.getPlayers();

                        for (int i = 0; i < players.size(); i++) {
                            String key = (String) players.get(i).getVal();
                            String val = players.get(i).getCnname();
                            //显示身份证
                            if (val.equals("身份证正面") || val.equals("身份证反面") || val.equals("军官证") || val.equals("护照")) {
                                if (players.get(i).getVal() != null && !((String) players.get(i).getVal()).isEmpty()) {
                                   binding.otherMsgLayout.setVisibility(View.VISIBLE);
                                }
                            }
                            if (players.get(i) != null) {
                                if (players.get(i).getCnname() != null && !players.get(i).getCnname().equals("")) {
                                    if (players.get(i).getCnname().equals("证件类型")) {
                                        if (players.get(i).getVal() != null && !players.get(i).getVal().equals("")) {
                                            if (players.get(i).getVal().equals(StatusVariable.IDCARD)) {
                                                mActivity.idCard();
                                            } else if (players.get(i).getVal().equals(StatusVariable.PASSCARD)) {
                                                mActivity.passPort();
                                            } else if (players.get(i).getVal().equals(StatusVariable.CERTIFICATECARD)) {
                                                mActivity.certificate();
                                            }
                                        }
                                    }
                                }
                            }
                            if (players.get(i).getCnname().equals("身份证正面")) {
                                if (!((String) players.get(i).getVal()).isEmpty() || players.get(i).getVal() != null) {
                                    GlideUtil.LoadImage(mActivity, (String) players.get(i).getVal(),binding.frontImgPer);
                                   binding.fronttext.setVisibility(View.GONE);
                                }
                            } else if (players.get(i).getCnname().equals("身份证反面")) {
                                if (!((String) players.get(i).getVal()).isEmpty() || players.get(i).getVal() != null) {
                                    GlideUtil.LoadImage(mActivity, (String) players.get(i).getVal(),binding.contraryImgPer);
                                   binding.contrarytext.setVisibility(View.GONE);
                                }
                            }
                            if (players.get(i).getCnname().equals("军官证")) {
                                if (!((String) players.get(i).getVal()).isEmpty() || players.get(i).getVal() != null) {
                                    GlideUtil.LoadImage(mActivity, (String) players.get(i).getVal(),binding.frontImgPer);
                                   binding.fronttext.setVisibility(View.GONE);
                                }
                            }
                            if (players.get(i).getCnname().equals("护照")) {
                                if (!((String) players.get(i).getVal()).isEmpty() || players.get(i).getVal() != null) {
                                    GlideUtil.LoadImage(mActivity, (String) players.get(i).getVal(),binding.frontImgPer);
                                   binding.fronttext.setVisibility(View.GONE);
                                }
                            }

                            if (!EmptyUtil.isEmpty(key)) {
                                AthletesMessageBean.ResultBean.PropertiesBean info = new AthletesMessageBean.ResultBean.PropertiesBean();
                                if (!players.get(i).getCnname().equals("身份证正面")) {
                                    if (!players.get(i).getCnname().equals("身份证反面")) {
                                        if (players.get(i).getCnname().equals("证件类型")) {
                                            if (players.get(i).getVal().equals("1")) {
                                                players.get(i).setVal("身份证");
                                            } else if (players.get(i).getVal().equals("2")) {
                                                players.get(i).setVal("护照");
                                            } else if (players.get(i).equals("4")) {
                                                players.get(i).setVal("军官证");
                                            }
                                        }
                                        if (players.get(i).isIsShow()) {
                                            if (!players.get(i).getCnname().equals("军官证") && !players.get(i).getCnname().equals("头像")) {
                                                if (!players.get(i).getCnname().equals("护照") && !players.get(i).getCnname().equals("身份证正面") && !players.get(i).getCnname().equals("身份证反面")) {
                                                    info.setAttributeName(players.get(i).getAttributeName());
                                                    info.setCnname(players.get(i).getCnname());
                                                    info.setParams(players.get(i).getParams());
                                                    info.setIsRequired(players.get(i).isIsRequired());
                                                    info.setIsShow(players.get(i).isIsShow());
                                                    info.setType(players.get(i).getType());
                                                    info.setVal((String) players.get(i).getVal());
                                                    if (!EmptyUtil.isEmpty(players.get(i).getVal())) {
                                                        infoList.add(info);
                                                    }

                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }


                        if (infoList.size() != 0) {
                            mPersonalAdapter.loadData(infoList);//个人信息数据
                        }
                    }
                }
                }
            }

            @Override
            public RegistrationInfoBean parseNetworkResponse(String jsonResult) {
                RegistrationInfoBean registrationInfoBean = JSON.parseObject(jsonResult, RegistrationInfoBean.class);
                return registrationInfoBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    /*
     * 订单详情状态
     * */
    private void initOrderStatus() {

        switch (orderStatusStr.get()) {
            case StatusVariable.MATCH_OVER:
                binding.llInfoBottom.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("比赛已结束");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#A0A0A0"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.APPLY_ABORT:
                binding.llInfoBottom.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("报名已截止");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#B0B0B0"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.PEOPLENUM_FULL:
                binding.llInfoBottom.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("人数已满");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#FFCA00"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.PAUSE_APPLY:
                binding.llInfoBottom.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("暂停报名");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#C1C1C1"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.APPLY:
                binding.llInfoBottom.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setText("报名");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#FF8F00"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
        }


    }
    /**
     * 继续支付
     */
    public void continuePayMent(){

        Intent intent = new Intent(mActivity, PayMentOrderActivty.class);
        intent.putExtra("orderCode", orderCode);
        mActivity.startActivity(intent);
    }


    @Override
    public void countEnd(boolean isEnd) {
        if(isEnd){
            if (!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }

    }
}
