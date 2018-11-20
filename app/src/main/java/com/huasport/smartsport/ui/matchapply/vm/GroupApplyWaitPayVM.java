package com.huasport.smartsport.ui.matchapply.vm;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.util.Log;
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
import com.huasport.smartsport.databinding.ActivityGroupwaitpayBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplyMsgAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplySuccessAdapter;
import com.huasport.smartsport.ui.matchapply.bean.CancelResultBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupMemberBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupOrderMsgBean;
import com.huasport.smartsport.ui.matchapply.view.GroupApplyWaitPayActivity;
import com.huasport.smartsport.ui.matchapply.view.PayMentOrderActivty;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class GroupApplyWaitPayVM extends BaseViewModel implements CounterListener {

    private String orderCode;
    private GroupApplyWaitPayActivity groupApplyWaitPayActivity;
    private GroupApplySuccessAdapter groupApplyMsgAdapter;
    private String token;
    public ObservableField<String> waitPayleaderName = new ObservableField<>("");
    public ObservableField<String> waitPaygroupName = new ObservableField<>("");
    public ObservableField<String> waitPayphoneNum = new ObservableField<>("");
    public ObservableField<String> orderStatus = new ObservableField<>("");
    public ObservableField<String> matchNameStr = new ObservableField<>("");//比赛名称
    public ObservableField<String> matchStartTime = new ObservableField<>("");//开始时间
    public ObservableField<String> matchEndTime = new ObservableField<>("");//结束时间
    public ObservableField<String> matchCodeStr = new ObservableField<>("");//Code
    public ObservableField<String> limit = new ObservableField<>("");//个数
    private GroupApplyMsgAdapter applyMsgAdapter;
    private ActivityGroupwaitpayBinding groupwaitpayBinding;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private MyApplication application = MyApplication.getInstance();

    public GroupApplyWaitPayVM(GroupApplyWaitPayActivity groupApplyWaitPayActivity, GroupApplySuccessAdapter groupApplySuccessAdapter, GroupApplyMsgAdapter applyMsgAdapter) {
        this.groupApplyWaitPayActivity = groupApplyWaitPayActivity;
        this.groupApplyMsgAdapter = groupApplySuccessAdapter;
        this.applyMsgAdapter = applyMsgAdapter;
        groupwaitpayBinding = groupApplyWaitPayActivity.getBinding();
        init();
        initData();
        initOrderStatus();
    }

    /**
     * 初始化
     */
    private void init() {
        orderCode = groupApplyWaitPayActivity.getIntent().getStringExtra("orderCode");
        //初始化Toast
        toastUtil = new ToastUtil(groupApplyWaitPayActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(groupApplyWaitPayActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        //弹出加载框
        loadingDialog.show();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.SIGININFO);
        params.put("token", token);
        params.put("orderCode", orderCode);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(groupApplyWaitPayActivity, params, new RequestCallBack<GroupOrderMsgBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<GroupOrderMsgBean> response) {
                GroupOrderMsgBean groupOrderMsgBean = response.body();
                if (!EmptyUtil.isEmpty(groupOrderMsgBean)) {
                    int resultCode = groupOrderMsgBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        GroupOrderMsgBean.ResultBean resultBean = groupOrderMsgBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            GroupOrderMsgBean.ResultBean.OrderDetailBean orderDetail = resultBean.getOrderDetail();

                            matchNameStr.set(resultBean.getMatchName());
                            matchStartTime.set(resultBean.getMatchStartTime());
                            matchEndTime.set(resultBean.getMatchEndTime());
                            matchCodeStr.set(resultBean.getMatchCode());

                            if (!EmptyUtil.isEmpty(orderDetail)) {

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.valueOf(orderDetail.getOrderTime()));
                                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式
                                String date = sf.format(calendar.getTime());
                                groupwaitpayBinding.groupWaitpayOrderTime.setText(date);
                                groupwaitpayBinding.groupWaitpayOrderAmount.setText(orderDetail.getOrderAmountStr());
                                groupwaitpayBinding.groupWaitpayOrderRemark.setText(orderDetail.getRemark());
                                groupwaitpayBinding.groupWaitpayOrderCode.setText(orderDetail.getOrderCode());


                                GroupOrderMsgBean.ResultBean.OrderDetailBean.LeaderBean leader = orderDetail.getLeader();
                                if (!EmptyUtil.isEmpty(leader)) {

                                }
                                List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean> teams = orderDetail.getTeams();
                                if (!EmptyUtil.isEmpty(teams)) {
                                    List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean> applys = groupOrderMsgBean.getResult().getOrderDetail().getTeams();
                                    List<GroupMemberBean> groupMemberBeans = new ArrayList<>();
                                    for (int i = 0; i < teams.size(); i++) {
                                        List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> playersBeans = new ArrayList<>();

                                        GroupMemberBean groupMemberBean = new GroupMemberBean();

                                        List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> players = teams.get(i).getPlayers();

                                        List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.IdPhotoBean> idPhotoBeans = teams.get(i).getIdPhotoBeans();

                                        for (int a = 0; a < players.size(); a++) {
                                            GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean playersBean = players.get(0);
                                            if (players.get(a).getAttributeName().equals("attOne")) {
                                                groupMemberBean.setFrontUrl(players.get(a).getVal());

                                            } else if (players.get(a).getAttributeName().equals("attTwo")) {
                                                groupMemberBean.setContractUrl(players.get(a).getVal());
                                            } else {
                                                if (!EmptyUtil.isEmpty(players.get(a).getVal())) {
                                                    playersBeans.add(players.get(a));
                                                }
                                            }

                                        }
                                        Log.e("PlayersBean", playersBeans.toString());
                                        groupMemberBean.setPlayersBeans(playersBeans);
                                        groupMemberBeans.add(groupMemberBean);
                                    }
                                    groupApplyMsgAdapter.loadData(groupMemberBeans);
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public GroupOrderMsgBean parseNetworkResponse(String jsonResult) {
                GroupOrderMsgBean groupOrderMsgBean = JSON.parseObject(jsonResult, GroupOrderMsgBean.class);

                return groupOrderMsgBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)) {
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
     * 去支付
     */
    public void continuePay(){
        Intent intent = new Intent(groupApplyWaitPayActivity, PayMentOrderActivty.class);
        intent.putExtra("orderCode", orderCode);
        groupApplyWaitPayActivity.startActivity(intent);
    }

    /**
     * 取消
     */
    public void cancel() {
        BaseDialog.show(groupApplyWaitPayActivity, "提示", "您确定要取消报名吗?", "确定", "取消", false, false,
                0, new DialogCallBack() {
                    @Override
                    public void submit(CustomDialog.Builder customDialog) {
                        cancelGroupApply();
                    }

                    @Override
                    public void cancel(CustomDialog.Builder customDialog) {
                        customDialog.dismiss();
                    }
                });
    }

    /**
     * 取消订单
     */
    private void cancelGroupApply() {

        HashMap params = new HashMap<>();
        params.put("token", token);//token
        params.put("orderCode", orderCode);//订单ID
        params.put("baseMethod", Method.CANCELORDER);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(groupApplyWaitPayActivity, params, new RequestCallBack<CancelResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<CancelResultBean> response) {
                CancelResultBean cancelResultBean = response.body();
                if (!EmptyUtil.isEmpty(cancelResultBean)) {
                    int resultCode = cancelResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        toastUtil.centerToast("取消报名成功");
                        groupApplyWaitPayActivity.finish();
                    } else {
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
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    /**
     * 订单详情状态
     */
    private void initOrderStatus() {

        switch (orderStatus.get()) {
            case StatusVariable.MATCH_OVER:
                groupwaitpayBinding.llWaitpayNext.setVisibility(View.GONE);
                groupwaitpayBinding.waitpayNextStep.setVisibility(View.GONE);
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setVisibility(View.VISIBLE);
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setText("比赛已结束");
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setBackgroundColor(Color.parseColor("#A0A0A0"));
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setEnabled(false);
                break;
            case StatusVariable.APPLY_ABORT:
                groupwaitpayBinding.llWaitpayNext.setVisibility(View.GONE);
                groupwaitpayBinding.waitpayNextStep.setVisibility(View.GONE);
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setVisibility(View.VISIBLE);
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setText("报名已截止");
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setBackgroundColor(Color.parseColor("#B0B0B0"));
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setEnabled(false);
                break;
            case StatusVariable.PEOPLENUM_FULL:
                groupwaitpayBinding.llWaitpayNext.setVisibility(View.GONE);
                groupwaitpayBinding.waitpayNextStep.setVisibility(View.GONE);
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setVisibility(View.VISIBLE);
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setText("人数已满");
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setBackgroundColor(Color.parseColor("#FFCA00"));
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setEnabled(false);
                break;
            case StatusVariable.PAUSE_APPLY:
                groupwaitpayBinding.llWaitpayNext.setVisibility(View.GONE);
                groupwaitpayBinding.waitpayNextStep.setVisibility(View.GONE);
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setVisibility(View.VISIBLE);
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setText("暂停报名");
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setBackgroundColor(Color.parseColor("#C1C1C1"));
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setEnabled(false);
                break;
            case StatusVariable.APPLY:
                groupwaitpayBinding.llWaitpayNext.setVisibility(View.VISIBLE);
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setVisibility(View.GONE);
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setText("报名");
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setBackgroundColor(Color.parseColor("#FF8F00"));
                groupwaitpayBinding.groupOrderDetailpayTextStatus.setEnabled(false);
                break;
        }


    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd) {
            if (!EmptyUtil.isEmpty(loadingDialog)) {
                loadingDialog.dismiss();
            }
        }
    }
}
