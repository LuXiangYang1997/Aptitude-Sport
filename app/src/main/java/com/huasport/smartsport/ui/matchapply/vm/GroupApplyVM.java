package com.huasport.smartsport.ui.matchapply.vm;

import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.CompoundButton;
import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.BR;
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
import com.huasport.smartsport.databinding.ActivityGroupapplyBinding;
import com.huasport.smartsport.ui.matchapply.adapter.AccretionMemberAdapter;
import com.huasport.smartsport.ui.matchapply.bean.GroupApplyActivity;
import com.huasport.smartsport.ui.matchapply.bean.GroupOrderMsgBean;
import com.huasport.smartsport.ui.matchapply.bean.SubmitApplyMessageBean;
import com.huasport.smartsport.ui.matchapply.view.AdditionMemberActivity;
import com.huasport.smartsport.ui.matchapply.view.GroupApplySuccessActivity;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.ui.matchapply.view.PayMentOrderActivty;
import com.huasport.smartsport.ui.matchapply.view.ProtocolActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.bean.GetVertifyCodeResultBean;
import com.huasport.smartsport.ui.pcenter.loginbind.view.BindPhoneActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.settings.bean.UserInfoBean;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 陆向阳 on 2018/7/26.
 */

public class GroupApplyVM extends BaseViewModel implements CounterListener {

    private MyApplication application = MyApplication.getInstance();
    private GroupApplyActivity groupApplyActivity;
    public ObservableField<String> groupName = new ObservableField<>("");//团队名称
    public ObservableField<String> leaderName = new ObservableField<>("");//领队姓名
    public ObservableField<String> groupPhoneNum = new ObservableField<>("");//手机号码
    public ObservableField<String> groupVertifyCode = new ObservableField<>("");//验证码
    public ObservableField<String> vertifiyCodeText = new ObservableField<>("获取验证码");
    public ObservableField<Boolean> vertifyCodeTextclickable = new ObservableField<>(true);
    public ObservableField<Boolean> saveStatus = new ObservableField<>(false);//是否保存为报名卡
    public ObservableField<Boolean> statement = new ObservableField<>(false);//免责声明
    public ObservableField<String> matchNameStr = new ObservableField<>("");//赛事名称
    public ObservableField<String> matchTime = new ObservableField<>("");//赛事时间
    public ObservableField<String> member = new ObservableField<>("0");
    public ObservableField<String> limit = new ObservableField<>("0");
    private final ActivityGroupapplyBinding binding;
    private String matchCode;
    private String orderCode;
    private int number;
    private String token;
    private int height;
    private List playerBeanList = new ArrayList<>();//添加队员
    private AccretionMemberAdapter accretionMemberAdapter;
    private int pos = -1;
    public ObservableField<Boolean> memberStatus = new ObservableField<>(false);
    private String personalLimit;
    private UserInfoBean.ResultBean.RegisterBean register;
    private String type;
    public ObservableField<UserInfoBean.ResultBean.RegisterBean> registerBeanObservableField = new ObservableField<>();
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;


    public GroupApplyVM(GroupApplyActivity groupApplyActivity, AccretionMemberAdapter accretionMemberAdapter) {
        this.groupApplyActivity = groupApplyActivity;
        binding = groupApplyActivity.getBinding();
        binding.setVariable(BR.view, new View(groupApplyActivity));
        this.accretionMemberAdapter = accretionMemberAdapter;
        init();
        initClick();
        getUserInfo();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {


        Intent intent = groupApplyActivity.getIntent();
        String matchName = intent.getStringExtra("matchName");
        String matchStartTime = intent.getStringExtra("matchStartTime");
        String matchEndTime = intent.getStringExtra("matchEndTime");
        matchCode = intent.getStringExtra("matchCode");
        orderCode = intent.getStringExtra("orderCode");
        type = intent.getStringExtra("type");

        personalLimit = groupApplyActivity.getIntent().getStringExtra("groupLimit");
        limit.set(personalLimit);
        binding.tvGrouplimit.setText(personalLimit);
        binding.groupMember.setText(playerBeanList.size() + "");

        //格式化日期
        if (!EmptyUtil.isEmpty(matchStartTime) && !EmptyUtil.isEmpty(matchEndTime)) {
            String time = DateUtil.dateConvert(matchStartTime, matchEndTime);
            matchTime.set(time);
        }
        if (!EmptyUtil.isEmpty(matchName)) {
            matchNameStr.set(matchName + "," + "查看详情");
        }
        if (!EmptyUtil.isEmpty(type)) {
            if (type.equals("waitpay")) {
                binding.groupformcbCheckbox.setChecked(false);
                noselectCb();
                binding.groupformCard.setVisibility(View.GONE);
            } else {
                binding.groupformcbCheckbox.setChecked(true);
                selectCb();
            }
        }

        //初始化Toast
        toastUtil = new ToastUtil(groupApplyActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(groupApplyActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 2);
        //获取token
        UserBean userBean = application.getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {

            token = userBean.getToken();

        }
        //弹出加载框
        loadingDialog.show();
    }

    /**
     * 获取用户报名卡数据
     */
    private void getUserInfo() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.GETUSERINFO);
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl3);


        OkHttpUtil.getRequest(groupApplyActivity, params, new RequestCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<UserInfoBean> response) {
                UserInfoBean userInfoBean = response.body();
                if (!EmptyUtil.isEmpty(userInfoBean)) {
                    int resultCode = userInfoBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        UserInfoBean.ResultBean resultBean = userInfoBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {
                            UserInfoBean.ResultBean.RegisterBean register = resultBean.getRegister();
                            registerBeanObservableField.set(register);
                            if (!EmptyUtil.isEmpty(register.getRealName())) {
                                binding.formuserName.setText(register.getRealName());
                            }
                            if (!EmptyUtil.isEmpty(register.getPhone())) {
                                binding.formuserPhone.setText(register.getPhone());
                            }
                            binding.formuserPhone.setText(register.getPhone());
                            if (register.isIsCard()) {
                                binding.groupformcbCheckbox.setChecked(true);
                                selectCb();
                                binding.groupformCard.setVisibility(View.VISIBLE);
                                binding.llGroupApplymsg.setVisibility(View.GONE);
                                binding.groupcbSave.setChecked(false);
                                saveStatus.set(false);
                                if (!EmptyUtil.isEmpty(type)) {
                                    if (type.equals("waitpay")) {
                                        binding.groupformcbCheckbox.setChecked(false);
                                        noselectCb();
                                        binding.groupformCard.setVisibility(View.GONE);
                                    } else {
                                        binding.groupformcbCheckbox.setChecked(true);
                                        selectCb();
                                    }
                                }

                            } else {
                                binding.groupformcbCheckbox.setChecked(false);
                                noselectCb();
//                            binding.groupcbSave.setChecked(true);
//                            saveStatus.set(true);
                                binding.groupformCard.setVisibility(View.GONE);
//                            binding.\applycard.setVisibility(View.VISIBLE);
                                binding.llGroupApplymsg.setVisibility(View.VISIBLE);
//                            binding.groupformcbCheckbox.setChecked(false);
//                            noselectCb();
//                            binding.groupformCard.setVisibility(View.GONE);

                            }

                        }
                    } else if (resultCode == StatusVariable.NOBIND) {
                        IntentUtil.startActivity(groupApplyActivity, BindPhoneActivity.class);
                    } else if (resultCode == StatusVariable.NOLOGIN) {
                        IntentUtil.startActivity(groupApplyActivity, LoginActivity.class);
                    }
                }

            }


            @Override
            public UserInfoBean parseNetworkResponse(String jsonResult) {
                UserInfoBean userInfoBean = JSON.parseObject(jsonResult, UserInfoBean.class);
                return userInfoBean;
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
     * 初始化数据
     */
    private void initData() {
        Intent intent = groupApplyActivity.getIntent();
        String orderCode = intent.getStringExtra("orderCode");
        if (EmptyUtil.isEmpty(orderCode)) {
            return;
        } else {

            HashMap params = new HashMap();
            params.put("baseMethod", Method.SIGININFO);
            params.put("token", token);
            params.put("orderCode", orderCode);
            params.put("baseUrl", Config.baseUrl);


            OkHttpUtil.getRequest(groupApplyActivity, params, new RequestCallBack<GroupOrderMsgBean>() {
                @Override
                public void onSuccess(com.lzy.okgo.model.Response<GroupOrderMsgBean> response) {
                    GroupOrderMsgBean groupOrderMsgBean = response.body();
                    if (!EmptyUtil.isEmpty(groupOrderMsgBean)) {
                        int resultCode = groupOrderMsgBean.getResultCode();
                        if (resultCode == StatusVariable.REQUESTSUCCESS) {
                            GroupOrderMsgBean.ResultBean resultBean = groupOrderMsgBean.getResult();
                            if (!EmptyUtil.isEmpty(resultBean)) {
                                GroupOrderMsgBean.ResultBean.OrderDetailBean orderDetail = resultBean.getOrderDetail();
                                if (!EmptyUtil.isEmpty(orderDetail)) {
                                    GroupOrderMsgBean.ResultBean.OrderDetailBean.LeaderBean leader = orderDetail.getLeader();
                                    if (!EmptyUtil.isEmpty(leader)) {
                                        leaderName.set(leader.getLeaderName());
                                        groupName.set(leader.getTeamName());
                                        groupPhoneNum.set(leader.getLeaderPhone());
                                    }
                                    List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean> teams = orderDetail.getTeams();
                                    if (!EmptyUtil.isEmpty(teams)) {
                                        member.set(teams.size() + "");
                                        limit.set(orderDetail.getPersonGroup());
                                        accretionMemberAdapter.loadData(teams);
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
    }

    /**
     * 初始化点击事件和监听
     */
    private void initClick() {

        binding.groupCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    statement.set(true);
                } else {
                    statement.set(false);
                }
            }
        });

        //修改成员
        accretionMemberAdapter.setItemClick(new AccretionMemberAdapter.ItemClick() {
            @Override
            public void click(BaseViewHolder baseViewHolder, int position) {
                pos = position;
                playerBeanList.clear();
                Intent intent = new Intent(groupApplyActivity, AdditionMemberActivity.class);
                intent.putExtra("matchCode", matchCode);
                intent.putExtra("playerCode", accretionMemberAdapter.mList.get(position).getPlayerCode());
                intent.putExtra("orderCode", orderCode);
                intent.putExtra("type", "motify");//修改
                List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> players = accretionMemberAdapter.mList.get(position).getPlayers();
                intent.putExtra("MotifyBean", (Serializable) players);
                groupApplyActivity.startActivityForResult(intent, StatusVariable.MODIFYMEMMBER);

            }
        });

        binding.groupformcbCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    selectCb();
                } else {
                    noselectCb();
                }
            }
        });
        //保存报名卡
        binding.groupcbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveStatus.set(true);
                } else {
                    saveStatus.set(false);
                }
            }
        });


    }

    /**
     * 获取验证码
     */
    public void getVertifyCode(){

        //获取Toolbar高度
        height = groupApplyActivity.toolbarBinding.toolbar.getHeight();
        number = 60;

        if (EmptyUtil.isEmpty(groupPhoneNum.get())) {
            toastUtil.showTopSnackBar("手机号码不能为空");
            return;
        }
        if (!Util.isPhoneNumber(groupPhoneNum.get())) {
            toastUtil.showTopSnackBar("请输入正确的手机号");
            return;
        }
        //设置获取验证码不可点击
        vertifyCodeTextclickable.set(false);

        HashMap params = new HashMap();
        params.put("baseUrl", Config.baseUrl);
        params.put("baseMethod", Method.GETVERTIFYCODE);
        params.put("phoneNum", groupPhoneNum.get());

        OkHttpUtil.getRequest(groupApplyActivity, params, new RequestCallBack<GetVertifyCodeResultBean>() {
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
                                    vertifiyCodeText.set(groupApplyActivity.getResources().getString(R.string.login_reset_get));
                                } else {
                                    number--;
                                    vertifiyCodeText.set(groupApplyActivity.getResources().getString(R.string.login_reset_get) + " " + number);
                                }
                            }
                        };
                        mTimer.schedule(mTimerTask, 1000, 1000);

                    } else {
                        toastUtil.centerToast(getVertifyCodeResultBean.getResultMsg());
                        vertifyCodeTextclickable.set(true);
                        vertifiyCodeText.set(groupApplyActivity.getResources().getString(R.string.login_reset_get));
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
     * 提交报名信息
     */
    public void applyMessage(){

        if (EmptyUtil.isEmpty(groupName.get())) {
            toastUtil.showTopSnackBar("团队名称不能为空");
            return;
        }

        if (EmptyUtil.isEmpty(leaderName.get())) {
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

        if (!statement.get()) {
            toastUtil.showTopSnackBar("请确认免责声明");
            return;
        }
        HashMap params = new HashMap();
        params.put("baseMethod", Method.PERFEVTMESSAGE);
        params.put("orderCode", orderCode);
        params.put("teamName", groupName.get());
        params.put("verifyCode", groupVertifyCode.get());
        params.put("leaderName", leaderName.get());
        params.put("leaderPhone", groupPhoneNum.get());
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);
        if (!binding.groupformcbCheckbox.isChecked()) {
            params.put("isCard", "0");
        } else {
            params.put("isCard", "1");
        }

        OkHttpUtil.postRequest(groupApplyActivity, params, new RequestCallBack<SubmitApplyMessageBean>() {
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
                                        Intent intent = new Intent(groupApplyActivity, GroupApplySuccessActivity.class);
                                        intent.putExtra("orderCode", submitApplyMessageBean.getResult().getOrderCode());
                                        intent.putExtra("orderType", StatusVariable.SUCCESSAPPLY);
                                        groupApplyActivity.startActivity(intent);
                            } else {
                                //跳转到订单支付页面
                                Intent intent = new Intent(groupApplyActivity, PayMentOrderActivty.class);
                                intent.putExtra("orderCode", submitApplyMessageBean.getResult().getOrderCode());
                                groupApplyActivity.startActivity(intent);
                            }
                        }
                    } else if (resultCode == StatusVariable.NOLOGIN) {

                        IntentUtil.startActivity(groupApplyActivity, LoginActivity.class);

                    } else if (resultCode == StatusVariable.NOBIND) {

                        IntentUtil.startActivity(groupApplyActivity, BindPhoneActivity.class);
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
     * 赛事介绍
     */
    public void matchIntroduceDetail() {

        Intent detailIntent = new Intent(groupApplyActivity, MatchIntroduceActivity.class);
        detailIntent.putExtra("matchCode", matchCode);
        detailIntent.putExtra("type", "other");
        groupApplyActivity.startActivity(detailIntent);

    }

    /**
     * 免责声明
     */
    public void stateMent() {

        IntentUtil.startActivity(groupApplyActivity, ProtocolActivity.class);

    }

    /**
     * 添加团队成员
     */
    public void addMember() {
        int member = Integer.parseInt(limit.get());
        if (accretionMemberAdapter.mList.size() == member) {
            memberStatus.set(true);
        }
        if (!memberStatus.get()) {
            playerBeanList.clear();
            Intent addIntent = new Intent(groupApplyActivity, AdditionMemberActivity.class);
            addIntent.putExtra("matchCode", matchCode);
            addIntent.putExtra("orderCode", orderCode);
            addIntent.putExtra("playerCode", "");
            addIntent.putExtra("type", "add");//添加
            groupApplyActivity.startActivityForResult(addIntent, StatusVariable.ADDITIONMEMBER);
        } else {
            toastUtil.showTopSnackBar("添加成员已满" + limit.get() + "人");
        }

    }


    /**
     * 保存团队信息
     */
    public void SaveMsg() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.SAVELEADERMSG);
        params.put("orderCode", orderCode);
        params.put("teamName", groupName.get());
        params.put("verifyCode", groupVertifyCode.get());
        params.put("leaderName", leaderName.get());
        params.put("leaderPhone", groupPhoneNum.get());
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);
        if (!binding.groupformcbCheckbox.isChecked()) {
            params.put("isCard", "0");
        } else {
            params.put("isCard", "1");
        }

        OkHttpUtil.postRequest(groupApplyActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        toastUtil.showTopSnackBar("保存成功");
                    } else {
                        toastUtil.showTopSnackBar("保存失败");
                    }
                }
            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {

                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);

                return resultBean;
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
     * 选中时的操作
     */
    public void selectCb() {
        binding.llGroupApplymsg.setVisibility(View.GONE);
        binding.groupformapplycardBg.setImageResource(R.mipmap.icon_mycard_bg);
        groupVertifyCode.set("0");
        if (EmptyUtil.isNotEmpty(register)) {

            if (!EmptyUtil.isEmpty(registerBeanObservableField.get().getRealName())) {
                binding.formuserName.setText(registerBeanObservableField.get().getRealName());
                leaderName.set(registerBeanObservableField.get().getRealName());
            }
            if (!EmptyUtil.isEmpty(registerBeanObservableField.get().getPhone())) {
                binding.formuserPhone.setText(registerBeanObservableField.get().getPhone());
                groupPhoneNum.set(registerBeanObservableField.get().getPhone());
            }
        }

    }

    /**
     * 未选中时操作
     */
    public void noselectCb() {
        binding.llGroupApplymsg.setVisibility(View.VISIBLE);
        binding.groupformapplycardBg.setImageResource(R.mipmap.icon_cardlight);
        groupVertifyCode.set("");
        leaderName.set("");
        groupPhoneNum.set("");
    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd) {
            if (!EmptyUtil.isEmpty(loadingDialog)) {
                loadingDialog.dismiss();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        binding.groupMember.setText(accretionMemberAdapter.mList.size() + "");

    }

}