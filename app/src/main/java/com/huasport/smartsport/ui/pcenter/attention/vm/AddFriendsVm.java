package com.huasport.smartsport.ui.pcenter.attention.vm;

import android.Manifest;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.AddfriendsLayoutBinding;
import com.huasport.smartsport.rxpermission.RxPermissionUtil;
import com.huasport.smartsport.rxpermission.RxPermissionUtilCallback;
import com.huasport.smartsport.ui.pcenter.attention.adapter.AddressBookFriendsAdapter;
import com.huasport.smartsport.ui.pcenter.attention.adapter.PhoneABookFriendsAdapter;
import com.huasport.smartsport.ui.pcenter.attention.bean.AddressBookBean;
import com.huasport.smartsport.ui.pcenter.attention.bean.PhoneAddressBookBean;
import com.huasport.smartsport.ui.pcenter.attention.bean.PhoneUtil;
import com.huasport.smartsport.ui.pcenter.attention.view.AddFriendsActivity;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ShareUtil;
import com.huasport.smartsport.util.SharedPreferencesUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.httprequest.HttpRequestCallBack;
import com.huasport.smartsport.util.httprequest.HttpRequestUtil;
import com.huasport.smartsport.wxapi.ThirdPart;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AddFriendsVm extends BaseViewModel implements CounterListener {

    private AddFriendsActivity addFriendsActivity;
    private AddfriendsLayoutBinding binding;
    private AddressBookFriendsAdapter addressBookFriendsAdapter;
    private String token;
    private TextView tv_attentionFriend;
    private TextView tv_allattention;
    private RecyclerView addaddressbookfriends_recyclerView;
    private PhoneABookFriendsAdapter phoneABookFriendsAdapter;
    private TextView tv_abookFriendsCount;
    private ThirdPart thirdPart;
    private String shareUrl = "";
    private int followNumber = 0;
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;
    private ShareUtil shareUtil;


    public AddFriendsVm(AddFriendsActivity addFriendsActivity, PhoneABookFriendsAdapter phoneABookFriendsAdapter) {
        this.addFriendsActivity = addFriendsActivity;
        this.phoneABookFriendsAdapter = phoneABookFriendsAdapter;
        binding = addFriendsActivity.getBinding();
        init();
        initView();
        toLeadaddress();
        onClick();
    }
    /**
     * 初始化
     */
    private void init() {
        thirdPart = new ThirdPart(addFriendsActivity);
        //初始化Toast
        toastUtil = new ToastUtil(addFriendsActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(addFriendsActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        //初始化分享
        shareUtil = new ShareUtil(addFriendsActivity);
        //弹出加载框
        loadingDialog.show();
    }
    /**
     * 添加头布局和尾布局
     */
    private void initView() {

        View headerView = LayoutInflater.from(addFriendsActivity).inflate(R.layout.addfriend_headerview, null, false);
        View footerView = LayoutInflater.from(addFriendsActivity).inflate(R.layout.addfriends_footerview, null, false);

        footerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        binding.addfriendsRecyclerView.addHeaderView(headerView);
        binding.addfriendsRecyclerView.addFooterView(footerView);

        tv_attentionFriend = headerView.findViewById(R.id.tv_attentionFriend);

        tv_allattention = headerView.findViewById(R.id.tv_allattention);

        tv_allattention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (followNumber != 0) {
                    onkeyAttention();
                }

            }
        });

        addressBookFriendsAdapter = new AddressBookFriendsAdapter(addFriendsActivity);

        addaddressbookfriends_recyclerView = footerView.findViewById(R.id.addaddressbookfriends_recyclerView);
        tv_abookFriendsCount = footerView.findViewById(R.id.tv_abookFriendsCount);


        addaddressbookfriends_recyclerView.setLayoutManager(new LinearLayoutManager(addFriendsActivity));
        addaddressbookfriends_recyclerView.setAdapter(addressBookFriendsAdapter);


        tv_abookFriendsCount.setText(0 + "位通讯录好友可邀请");

        tv_attentionFriend.setText("一键关注" + 0 + "位通讯录好友");

    }


    private void onClick() {

        addressBookFriendsAdapter.setAttentionClick(new AddressBookFriendsAdapter.AttentionClick() {
            @Override
            public void attentionClick(AddressBookBean.ResultBean.InvitesBean listBean, int position) {
                invites(listBean.getRegisterPhone());
            }
        });


        phoneABookFriendsAdapter.setAttentionClick(new PhoneABookFriendsAdapter.AttentionClick() {
            @Override
            public void attentionClick(AddressBookBean.ResultBean.FriendsBean friendsBean, int position) {
                attention((String) friendsBean.getRegisterId());

            }
        });

    }

    /**
     * 发送短信邀请
     *
     * @param registerPhone
     */
    private void invites(String registerPhone) {

        HashMap params = new HashMap();
        params.put("baseMethod", Method.SENDMSGINVITE);
        params.put("baseUrl", Config.baseUrl2);
        params.put("token", token);
        params.put("phoneNum", registerPhone.replaceAll(" ", ""));

        OkHttpUtil.getRequest(addFriendsActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)) {
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {

                        toastUtil.centerToast("已向您的好友发送短信邀请");

                    } else {

                        toastUtil.centerToast(resultBean.getResultMsg());
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

            }
        });

    }

    /**
     * 导入通讯录
     */
    public void toLeadaddress() {

        //获取通讯录并上传
        RxPermissionUtil.getPermission(addFriendsActivity, Manifest.permission.READ_CONTACTS, new RxPermissionUtilCallback() {
            @Override
            public void grand(boolean grand) {
                if (grand) {
                    binding.rlAllattention.setVisibility(View.GONE);
                    binding.invite.setVisibility(View.GONE);
                    NullStateUtil.setNullState(binding.nulldata, false);
                    PhoneUtil phoneUtil = new PhoneUtil(addFriendsActivity);
                    List<PhoneAddressBookBean> phoneList = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        phoneList = phoneUtil.getPhone();
                    }
                    if(!EmptyUtil.isEmpty(phoneList)){
                        Gson gson = new Gson();
                        String s = gson.toJson(phoneList);
                        Log.e("String", s);
                        File file = WriteConfigJson(s);
                        if (!EmptyUtil.isEmpty(file)) {
                            addressUpload(file.getAbsolutePath());
                        }
                    }
                } else {
                    binding.rlAllattention.setVisibility(View.VISIBLE);
                    binding.invite.setVisibility(View.VISIBLE);
                    NullStateUtil.setNullState(binding.nulldata, true);
                    toastUtil.centerToast("用户拒绝了通讯录权限");
                }
            }
        });
    }

    /**
     * 上传通讯录文件
     *
     * @param path
     */
    public void addressUpload(String path) {

        HashMap params = new HashMap();
        params.put("file", path);
        params.put("baseMethod", Method.ADDRESSUPLOAD);
        params.put("baseUrl", Config.baseUrl2);
        params.put("token", token);

        OkHttpUtil.uploadFile(addFriendsActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {

                        initData();
                        toastUtil.centerToast("导入成功");

            }

            @Override
            public ResultBean parseNetworkResponse(String jsonResult) {
                ResultBean resultBean = JSON.parseObject(jsonResult, ResultBean.class);
                return resultBean;
            }

            @Override
            public void onFailed(int code, String msg) {

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
     */
    private void initData() {

        HashMap friendsparams = new HashMap();
        friendsparams.put("baseMethod", Method.ADDRESSBOOKLIST);
        friendsparams.put("token", token);
        friendsparams.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.getRequest(addFriendsActivity, friendsparams, new RequestCallBack<AddressBookBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<AddressBookBean> response) {
                AddressBookBean addressBookBean = response.body();
                if (!EmptyUtil.isEmpty(addressBookBean)){
                    int resultCode = addressBookBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        AddressBookBean.ResultBean resultBean = addressBookBean.getResult();
                        if(!EmptyUtil.isEmpty(resultBean)){
                            List<AddressBookBean.ResultBean.InvitesBean> list = resultBean.getInvites();
                            List<AddressBookBean.ResultBean.FriendsBean> friends = resultBean.getFriends();

                            if (!EmptyUtil.isEmpty(resultBean.getShareUrl())) {
                                shareUrl = resultBean.getShareUrl();
                            }
                            if (list.size() == 0 && friends.size() == 0) {
                                binding.rlAllattention.setVisibility(View.VISIBLE);
                                binding.invite.setVisibility(View.VISIBLE);
                                NullStateUtil.setNullState(binding.nulldata, true);
                            } else {
                                binding.rlAllattention.setVisibility(View.GONE);
                                binding.invite.setVisibility(View.GONE);
                                NullStateUtil.setNullState(binding.nulldata, false);
                            }
                            if (!EmptyUtil.isEmpty(list)) {
                                tv_abookFriendsCount.setText(resultBean.getInvitesNum() + "位通讯录好友可邀请");
                                addressBookFriendsAdapter.loadData(list);
                            }
                            int unfollowNum = resultBean.getUnfollowNum();
                            followNumber = unfollowNum;
                            if (followNumber > 0) {
                                tv_allattention.setBackground(addFriendsActivity.getResources().getDrawable(R.drawable.addressbookbg));
                            } else {
                                tv_allattention.setBackgroundColor(addFriendsActivity.getResources().getColor(R.color.color_D0D0D0));
                            }
                            if (!EmptyUtil.isEmpty(friends)) {
                                tv_attentionFriend.setText("一键关注" + unfollowNum + "位通讯录好友");
                                phoneABookFriendsAdapter.loadData(friends);
                            }
                        }


                    }


                }
            }

            @Override
            public AddressBookBean parseNetworkResponse(String jsonResult) {
                AddressBookBean addressBookBean = JSON.parseObject(jsonResult, AddressBookBean.class);
                return addressBookBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }


    /**
     * 生成txt文件保存到本地
     *
     * @param args
     * @return
     */
    public static File WriteConfigJson(String args) {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(absolutePath + "/address.txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(args);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 一键关注
     */
    public void onkeyAttention() {

      if (EmptyUtil.isEmpty(token)){
          IntentUtil.startActivity(addFriendsActivity,LoginActivity.class);
          return;
      }

        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.ONEKEYATTENTION);
        params.put("baseUrl", Config.baseUrl2);

        OkHttpUtil.getRequest(addFriendsActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(response.body())) {
                    if (resultBean.getResultCode() == StatusVariable.SUCCESS) {
                        initData();
                        toastUtil.centerToast("一键关注成功");
                    } else {
                        toastUtil.centerToast("一键关注失败");
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

            }
        });

    }

    /**
     * 关注
     */
    public void attention(String id) {

        if (EmptyUtil.isEmpty(token)){
            IntentUtil.startActivity(addFriendsActivity,LoginActivity.class);
            return;
        }
        HttpRequestUtil.attention(addFriendsActivity, token, id, new HttpRequestCallBack() {
            @Override
            public void httpSuccess(ResultBean resultBean) {
                    if (!EmptyUtil.isEmpty(resultBean)){
                        int resultCode = resultBean.getResultCode();
                        if (resultCode == StatusVariable.REQUESTSUCCESS){
                            initData();
                            toastUtil.centerToast(resultBean.getResultMsg());
                            SharedPreferencesUtil.setParam(addFriendsActivity, "ActivityState", true);
                        }else{
                            toastUtil.centerToast(resultBean.getResultMsg());
                        }
                    }
            }

            @Override
            public void httpFailed(int code, String msg) {

            }

            @Override
            public void httpFinish() {

            }
        });

    }

    /**
     * 微信邀请
     */
    public void rlWeChat() {

        sharePop();

    }

    /**
     * 分享
     *
     * @param
     */
    private void sharePop() {

        thirdPart.wxUrlShare(shareUrl, "智体运动邀请函"
                , "我正在参加智能体育大赛，" + "\n" + "快来和我一起解锁智体运动" + "\n" + "的魅力吧！"
                , R.mipmap.icon_share, ThirdPart.WECHAT_FRIEND);

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
