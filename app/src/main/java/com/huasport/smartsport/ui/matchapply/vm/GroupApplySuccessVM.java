package com.huasport.smartsport.ui.matchapply.vm;

import android.databinding.ObservableField;
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
import com.huasport.smartsport.databinding.ActivityGroupapplysuccessBinding;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplyCardMsgAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplyMsgAdapter;
import com.huasport.smartsport.ui.matchapply.adapter.GroupApplySuccessAdapter;
import com.huasport.smartsport.ui.matchapply.bean.GroupMemberBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupOrderMsgBean;
import com.huasport.smartsport.ui.matchapply.view.GroupApplySuccessActivity;
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


/**
 * Created by 陆向阳 on 2018/7/26.
 */

public class GroupApplySuccessVM extends BaseViewModel implements CounterListener {

    private GroupApplySuccessActivity groupApplySuccessActivity;
    private String token;
    private GroupApplySuccessAdapter groupApplySuccessAdapter;
    private GroupApplyMsgAdapter groupApplyMsgAdapter;
    private GroupApplyCardMsgAdapter groupApplyCardMsgAdapter;
    private List<GroupOrderMsgBean.ResultBean.OrderDetailBean.LeaderBean> leaderBeanList = new ArrayList<>();
    private ActivityGroupapplysuccessBinding binding;
    public ObservableField<String> leaderName = new ObservableField<>("");
    public ObservableField<String> groupName = new ObservableField<>("");
    public ObservableField<String> phoneNum = new ObservableField<>("");
    private String orderCode;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private MyApplication application = MyApplication.getInstance();

    public GroupApplySuccessVM(GroupApplySuccessActivity groupApplySuccessActivity, GroupApplySuccessAdapter groupApplySuccessAdapter, GroupApplyMsgAdapter groupApplyMsgAdapter, GroupApplyCardMsgAdapter groupApplyCardMsgAdapter) {
        this.groupApplySuccessActivity = groupApplySuccessActivity;
        this.groupApplySuccessAdapter = groupApplySuccessAdapter;
        this.groupApplyCardMsgAdapter = groupApplyCardMsgAdapter;
        this.groupApplyMsgAdapter = groupApplyMsgAdapter;
        binding = groupApplySuccessActivity.getBinding();
        init();
        initOrderData();
    }

    /**
     * 初始化
     */
    private void init() {
        orderCode = groupApplySuccessActivity.getIntent().getStringExtra("orderCode");
        //初始化Toast
        toastUtil = new ToastUtil(groupApplySuccessActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(groupApplySuccessActivity, R.style.LoadingDialog);
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
     * 获取订单详细信息
     * */
    private void initOrderData() {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.SIGININFO);
        params.put("token", token);
        params.put("orderCode", orderCode);
        params.put("baseUrl", Config.baseUrl);


        OkHttpUtil.getRequest(groupApplySuccessActivity, params, new RequestCallBack<GroupOrderMsgBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<GroupOrderMsgBean> response) {
                GroupOrderMsgBean groupOrderMsgBean = response.body();
                if (!EmptyUtil.isEmpty(groupOrderMsgBean)) {
                    int resultCode = groupOrderMsgBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        GroupOrderMsgBean.ResultBean resultBean = groupOrderMsgBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {

                            GroupOrderMsgBean.ResultBean.OrderDetailBean orderDetail = resultBean.getOrderDetail();
                            if (!EmptyUtil.isEmpty(orderDetail)){
                                //订单信息
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.valueOf(orderDetail.getOrderTime()));
                                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式
                                String datestr = sf.format(calendar.getTime());


                                binding.groupOrderTime.setText(datestr);
                                binding.groupOrderamount.setText(orderDetail.getOrderAmountStr());//订单总额
                                binding.groupRemark.setText(orderDetail.getRemark());//备注
                                binding.orderCode.setText(orderDetail.getOrderCode());//订单编号

                                if (orderDetail.getApplys() != null) {
                                    //申请信息
                                    List<GroupOrderMsgBean.ResultBean.OrderDetailBean.ApplysBean> applys = orderDetail.getApplys();
                                    for (int i = 0; i < applys.size(); i++) {
                                        applys.get(i).setTitle(groupOrderMsgBean.getResult().getTitle());
                                    }
                                    groupApplyCardMsgAdapter.loadData(applys);
                                    groupApplyMsgAdapter.loadData(applys);
                                }

                                if (orderDetail.getLeader() != null) {
                                    //团队信息
                                    GroupOrderMsgBean.ResultBean.OrderDetailBean.LeaderBean leader = orderDetail.getLeader();
                                    groupName.set(leader.getTeamName());
                                    leaderName.set(leader.getLeaderName());
                                    phoneNum.set(leader.getLeaderPhone());
                                }

                            }
                            List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean> teams = orderDetail.getTeams();
                            if (!EmptyUtil.isEmpty(teams)){
                                List<GroupMemberBean> groupMemberBeans = new ArrayList<>();

                                for (int i = 0; i < teams.size(); i++) {
                                    List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> playersBeans = new ArrayList<>();
                                    GroupMemberBean groupMemberBean = new GroupMemberBean();

                                    List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> players = teams.get(i).getPlayers();

                                    List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.IdPhotoBean> idPhotoBeans = teams.get(i).getIdPhotoBeans();

                                    String playerName = teams.get(i).getPlayerName();

                                    for (int a = 0; a < players.size(); a++) {
                                        GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean playersBean = players.get(0);


                                        if (players.get(a).getAttributeName().equals("attOne")) {
                                            groupMemberBean.setFrontUrl(players.get(a).getVal());

                                        } else if (players.get(a).getAttributeName().equals("attTwo")) {
                                            groupMemberBean.setContractUrl(players.get(a).getVal());
                                        } else {
                                            if (!EmptyUtil.isEmpty(players.get(a).getVal())){
                                                playersBeans.add(players.get(a));
                                            }
                                        }

                                    }
                                    Log.e("PlayersBean", playersBeans.toString());
                                    groupMemberBean.setPlayerName(playerName);
                                    groupMemberBean.setPlayersBeans(playersBeans);
                                    groupMemberBeans.add(groupMemberBean);

                                }
                                groupApplySuccessAdapter.loadData(groupMemberBeans);
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


    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if(!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }
}
