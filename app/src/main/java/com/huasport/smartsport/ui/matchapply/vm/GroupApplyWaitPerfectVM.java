package com.huasport.smartsport.ui.matchapply.vm;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Color;
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
import com.huasport.smartsport.databinding.ActivityGroupwaitperfectBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplyMsgAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.GroupMemberMsgAdapter;
import com.huasport.smartsport.ui.matchapply.bean.AdditionMemberBean;
import com.huasport.smartsport.ui.matchapply.bean.CancelResultBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupOrderMsgBean;
import com.huasport.smartsport.ui.matchapply.bean.SubmitApplyMessageBean;
import com.huasport.smartsport.ui.matchapply.view.AdditionMemberActivity;
import com.huasport.smartsport.ui.matchapply.view.GroupApplyWaitPerfectActivity;
import com.huasport.smartsport.ui.matchapply.view.PayMentOrderActivty;
import com.huasport.smartsport.ui.pcenter.loginbind.bean.GetVertifyCodeResultBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GroupApplyWaitPerfectVM extends BaseViewModel implements CounterListener {

    private MyApplication application = MyApplication.getInstance();
    private String orderCode;
    private GroupApplyWaitPerfectActivity groupApplyWaitPerfectActivity;
    private GroupApplyMsgAdapter groupApplyMsgAdapter;
    public ObservableField<String> waitPerfectleaderName = new ObservableField<>("");
    public ObservableField<String> waitPerfectgroupName = new ObservableField<>("");
    public ObservableField<String> waitPerfectphoneNum = new ObservableField<>("");
    public ObservableField<String> groupPhoneNum = new ObservableField<>("");//手机号码
    public ObservableField<String> groupVertifyCode = new ObservableField<>("");//验证码
    public ObservableField<String> vertifiyCodeText = new ObservableField<>("获取验证码");
    public ObservableField<Boolean> vertifyCodeTextclickable = new ObservableField<>(true);
    public ObservableField<String> applyStatus = new ObservableField<>("");
    public ObservableField<Integer> limitCount = new ObservableField<>(0);
    private  String token = "";
    private GroupMemberMsgAdapter groupMemberMsgAdapter;//成员信息Adapter
    private ActivityGroupwaitperfectBinding binding;
    private List<AdditionMemberBean.ResultBean.Players> player;
    ObservableField<String> matchCode = new ObservableField<>("");
    ObservableField<Boolean> memberCount = new ObservableField<>(false);
    private List playerBeanList = new ArrayList<>();//添加队员

    private int pos = -1;
    private List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean> teams;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private int number;

    public GroupApplyWaitPerfectVM(GroupApplyWaitPerfectActivity groupApplyWaitPerfectActivity, GroupApplyMsgAdapter groupApplyMsgAdapter, GroupMemberMsgAdapter groupMemberMsgAdapter) {
        this.groupApplyWaitPerfectActivity = groupApplyWaitPerfectActivity;
        this.groupApplyMsgAdapter = groupApplyMsgAdapter;
        this.groupMemberMsgAdapter = groupMemberMsgAdapter;
        init();
        initClick();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {
        orderCode = groupApplyWaitPerfectActivity.getIntent().getStringExtra("orderCode");
        binding = groupApplyWaitPerfectActivity.getBinding();

        //初始化Toast
        toastUtil = new ToastUtil(groupApplyWaitPerfectActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(groupApplyWaitPerfectActivity, R.style.LoadingDialog);
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


        OkHttpUtil.getRequest(groupApplyWaitPerfectActivity, params, new RequestCallBack<GroupOrderMsgBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<GroupOrderMsgBean> response) {
                GroupOrderMsgBean groupOrderMsgBean = response.body();
                if (!EmptyUtil.isEmpty(groupOrderMsgBean)) {
                    int resultCode = groupOrderMsgBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        GroupOrderMsgBean.ResultBean resultBean = groupOrderMsgBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            applyStatus.set(resultBean.getApplyStatus());
                            initOrderStatus();
                            matchCode.set(groupOrderMsgBean.getResult().getMatchCode());

                            GroupOrderMsgBean.ResultBean.OrderDetailBean.LeaderBean leader = resultBean.getOrderDetail().getLeader();

                            if (!EmptyUtil.isEmpty(leader)) {
                                String leaderName = leader.getLeaderName();
                                String leaderPhone = leader.getLeaderPhone();
                                String teamName = leader.getTeamName();


                                if (!EmptyUtil.isEmpty(leaderName)) {
                                    waitPerfectleaderName.set(leaderName);
                                }
                                if (!EmptyUtil.isEmpty(leaderPhone)) {
                                    groupPhoneNum.set(leaderPhone);
                                }
                                if (!EmptyUtil.isEmpty(teamName)) {
                                    waitPerfectgroupName.set(teamName);
                                }

                            }
                            GroupOrderMsgBean.ResultBean.OrderDetailBean orderDetail = resultBean.getOrderDetail();
                            if(!EmptyUtil.isEmpty(orderDetail)){
                                binding.waitprtfectOrderAmount.setText(orderDetail.getOrderAmountStr());
                                binding.waitprtfectOrderCode.setText(orderDetail.getOrderCode());
                                binding.waitprtfectRemark.setText(orderDetail.getRemark());
                            }

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(Long.valueOf(orderDetail.getOrderTime()));
                            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式
                            String date = sf.format(calendar.getTime());
                            binding.waitprtfectOrderTime.setText(date);
                            //申请信息
                            if (orderDetail.getApplys() != null) {
                                List<GroupOrderMsgBean.ResultBean.OrderDetailBean.ApplysBean> applys = orderDetail.getApplys();
                                groupApplyMsgAdapter.loadData(applys);
                            }
                            if (orderDetail.getTeams() != null) {
                                teams = orderDetail.getTeams();
                                binding.member.setText(teams.size() + "");
                                binding.tvLimit.setText(orderDetail.getPersonGroup());
                                groupMemberMsgAdapter.loadData(teams);
                            }
                            int limit = Integer.parseInt(orderDetail.getPersonGroup());
                            limitCount.set(limit);
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
     * 获取验证码
     */
    public void getVertifyCode(){

        number = 60;

        if (EmptyUtil.isEmpty(waitPerfectphoneNum.get())) {
            toastUtil.showTopSnackBar("手机号码不能为空");
            return;
        }
        if (!Util.isPhoneNumber(waitPerfectphoneNum.get())) {
            toastUtil.showTopSnackBar("请输入正确的手机号");
            return;
        }
        //设置获取验证码不可点击
        vertifyCodeTextclickable.set(false);

        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl);
        params.put("baseMethod", Method.GETVERTIFYCODE);
        params.put("phoneNum", waitPerfectphoneNum.get());

        OkHttpUtil.getRequest(groupApplyWaitPerfectActivity, params, new RequestCallBack<GetVertifyCodeResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<GetVertifyCodeResultBean> response) {
                GetVertifyCodeResultBean getVertifyCodeResultBean = response.body();
                if (!EmptyUtil.isEmpty(getVertifyCodeResultBean)) {
                    int resultCode = getVertifyCodeResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {

                        final Timer mTimer = new Timer();

                        TimerTask mTimerTask = new TimerTask() {
                            @Override
                            public void run() {
                                if (number == 0) {
                                    number = 60;
                                    vertifyCodeTextclickable.set(true);
                                    mTimer.cancel();
                                    vertifiyCodeText.set(groupApplyWaitPerfectActivity.getResources().getString(R.string.login_reset_get));
                                } else {
                                    number--;
                                    vertifiyCodeText.set(groupApplyWaitPerfectActivity.getResources().getString(R.string.login_reset_get) + " " + number);
                                }
                            }
                        };
                        mTimer.schedule(mTimerTask, 1000, 1000);

                    } else {
                        toastUtil.centerToast(getVertifyCodeResultBean.getResultMsg());
                        vertifyCodeTextclickable.set(true);
                        vertifiyCodeText.set(groupApplyWaitPerfectActivity.getResources().getString(R.string.login_reset_get));
                    }
                }

            }

            @Override
            public GetVertifyCodeResultBean parseNetworkResponse(String jsonResult) {

                GetVertifyCodeResultBean getVertifyCodeResultBean = JSON.parseObject(jsonResult, GetVertifyCodeResultBean.class);

                return getVertifyCodeResultBean;
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
     * 初始化点击事件
     */
    private void initClick() {

        /**
         * 修改成员信息
         */
        groupMemberMsgAdapter.setItemClick(new GroupMemberMsgAdapter.ItemClick() {
            @Override
            public void itemClick(GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean teamsBean, int position) {
                pos = position;
                Intent intent = new Intent(groupApplyWaitPerfectActivity, AdditionMemberActivity.class);
                intent.putExtra("matchCode", matchCode.get());
                intent.putExtra("orderCode", orderCode);
                intent.putExtra("type", "motify");//修改
                intent.putExtra("playerCode", groupMemberMsgAdapter.mList.get(position).getPlayerCode());
                List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> players = teamsBean.getPlayers();
                intent.putExtra("MotifyBean", (Serializable) players);
                groupApplyWaitPerfectActivity.startActivityForResult(intent, StatusVariable.MODIFYMEMMBER);
            }
        });
    }

    /**
     * 添加成员信息
      */
    public void addMember(){

         if (groupMemberMsgAdapter.mList.size() == limitCount.get()) {
             memberCount.set(true);
         } else {
             memberCount.set(false);
         }
         if (!memberCount.get()) {
             playerBeanList.clear();
             Intent addIntent = new Intent(groupApplyWaitPerfectActivity, AdditionMemberActivity.class);
             addIntent.putExtra("matchCode", matchCode.get());
             addIntent.putExtra("orderCode", orderCode);
             addIntent.putExtra("playerCode", "");
             addIntent.putExtra("type", "add");//添加
             groupApplyWaitPerfectActivity.startActivityForResult(addIntent, StatusVariable.ADDITIONMEMBER);
         } else {
             toastUtil.centerToast("所填加成员已达到最大成员限制");
         }

     }

    /**
     * 取消报名
     */
    public void cancelApply() {


        BaseDialog.show(groupApplyWaitPerfectActivity, "提示", "您确定要取消报名吗？", "确定", "取消", false, false,
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
     * 提交
     */
    public void submitapplyMsg () {


        if (EmptyUtil.isEmpty(waitPerfectgroupName.get())) {
            toastUtil.showTopSnackBar("团队名称不能为空");
            return;
        }

        if (EmptyUtil.isEmpty(waitPerfectleaderName.get())) {
            toastUtil.showTopSnackBar("领队姓名不能为空");
            return;
        }
        if (EmptyUtil.isEmpty(groupPhoneNum.get())) {
            toastUtil.showTopSnackBar("手机号码不能为空");
            return;
        }

        if (!Util.isPhoneNumber(groupPhoneNum.get())) {
            toastUtil.showTopSnackBar("请输入正确的手机号码");
            return;
        }

        if (EmptyUtil.isEmpty(groupVertifyCode.get())) {
            toastUtil.showTopSnackBar("验证码不能为空");
            return;
        }
        HashMap params = new HashMap();
        params.put("baseMethod", Method.PERFEVTMESSAGE);
        params.put("orderCode", orderCode);
        params.put("teamName", waitPerfectgroupName.get());
        params.put("verifyCode", groupVertifyCode.get());
        params.put("leaderName", waitPerfectleaderName.get());
        params.put("leaderPhone", groupPhoneNum.get());
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);


        OkHttpUtil.postRequest(groupApplyWaitPerfectActivity, params, new RequestCallBack<SubmitApplyMessageBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<SubmitApplyMessageBean> response) {
                SubmitApplyMessageBean submitApplyMessageBean = response.body();
                if (!EmptyUtil.isEmpty(submitApplyMessageBean)) {
                    int resultCode = submitApplyMessageBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        SubmitApplyMessageBean.ResultBean resultBean = submitApplyMessageBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            //跳转到成功页面
                            if (submitApplyMessageBean.getResult().getOrderStaus().equals("success")) {
//                                        Intent intent = new Intent(groupApplyWaitPerfectActivity, GroupApplySuccessActivity.class);
//                                        intent.putExtra("orderCode", submitApplyMessageBean.getResult().getOrderCode());
//                                        intent.putExtra("orderType", StatusVariable.SUCCESSAPPLY);
//                                        groupApplyWaitPerfectActivity.startActivity(intent);
                            } else {
                                //跳转到订单支付页面
                                Intent intent = new Intent(groupApplyWaitPerfectActivity, PayMentOrderActivty.class);
                                intent.putExtra("orderCode", submitApplyMessageBean.getResult().getOrderCode());
                                groupApplyWaitPerfectActivity.startActivity(intent);
                            }
                        }
                    } else if (resultCode == StatusVariable.NOLOGIN) {

                        IntentUtil.startActivity(groupApplyWaitPerfectActivity, LoginActivity.class);

                    } else if (resultCode == StatusVariable.NOBIND) {

                        IntentUtil.startActivity(groupApplyWaitPerfectActivity, BindPhoneActivity.class);
                    }
                }
            }

            @Override
            public SubmitApplyMessageBean parseNetworkResponse(String jsonResult) {
                SubmitApplyMessageBean submitApplyMessageBean = JSON.parseObject(jsonResult, SubmitApplyMessageBean.class);
                return submitApplyMessageBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)) {
                    toastUtil.showTopSnackBar(msg);
                }
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

        OkHttpUtil.getRequest(groupApplyWaitPerfectActivity, params, new RequestCallBack<CancelResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<CancelResultBean> response) {
                CancelResultBean cancelResultBean = response.body();
                if (!EmptyUtil.isEmpty(cancelResultBean)){
                    int resultCode = cancelResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        toastUtil.centerToast("取消报名成功");
                        groupApplyWaitPerfectActivity.finish();
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

    /**
     * 订单详情状态
     */
    private void initOrderStatus() {

        switch (applyStatus.get()) {
            case StatusVariable.MATCH_OVER:
                binding.llWaitPerfectNext.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("比赛已结束");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#A0A0A0"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.APPLY_ABORT:
                binding.llWaitPerfectNext.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("报名已截止");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#B0B0B0"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.PEOPLENUM_FULL:
                binding.llWaitPerfectNext.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("人数已满");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#FFCA00"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.PAUSE_APPLY:
                binding.llWaitPerfectNext.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setText("暂停报名");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#C1C1C1"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
            case StatusVariable.APPLY:
                binding.llWaitPerfectNext.setVisibility(View.VISIBLE);
                binding.groupOrderDetailTextStatus.setVisibility(View.GONE);
                binding.groupOrderDetailTextStatus.setText("报名");
                binding.groupOrderDetailTextStatus.setBackgroundColor(Color.parseColor("#FF8F00"));
                binding.groupOrderDetailTextStatus.setEnabled(false);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
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
