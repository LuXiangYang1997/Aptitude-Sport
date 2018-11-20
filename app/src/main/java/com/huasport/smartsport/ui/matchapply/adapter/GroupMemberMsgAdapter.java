package com.huasport.smartsport.ui.matchapply.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.bean.ResultBean;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.databinding.GroupWperfectmemberLayoutBinding;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.ui.matchapply.bean.GroupOrderMsgBean;
import com.huasport.smartsport.ui.matchapply.view.GroupApplyWaitPerfectActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import java.util.HashMap;


public class GroupMemberMsgAdapter extends BaseAdapter<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean, BaseViewHolder> {

    private MyApplication application = MyApplication.getInstance();
    private GroupApplyWaitPerfectActivity groupApplyWaitPerfectActivity;
    private String token = "";

    public GroupMemberMsgAdapter(GroupApplyWaitPerfectActivity groupApplyWaitPerfectActivity) {
        super(groupApplyWaitPerfectActivity);
        this.groupApplyWaitPerfectActivity = groupApplyWaitPerfectActivity;

    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        GroupWperfectmemberLayoutBinding groupWperfectmemberLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(groupApplyWaitPerfectActivity), R.layout.group_wperfectmember_layout, parent, false);

        return new BaseViewHolder(groupWperfectmemberLayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {

        GroupWperfectmemberLayoutBinding binding = (GroupWperfectmemberLayoutBinding) baseViewHolder.getBinding();
        binding.waitperfectMemberName.setText(mList.get(position).getPlayerName());
        binding.waitperfectMemberphonenum.setText(mList.get(position).getPlayerPhone());

        BaseDialog.show(groupApplyWaitPerfectActivity, "删除提示", "确定删除此成员信息吗？", "确定", "取消", false, false,
                0, new DialogCallBack() {
                    @Override
                    public void submit(CustomDialog.Builder customDialog) {
                        deleteUser(position);
                    }

                    @Override
                    public void cancel(CustomDialog.Builder customDialog) {
                        customDialog.dismiss();
                    }
                });

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.itemClick(mList.get(position), position);
            }
        });
    }

    //删除成员
    private void deleteUser(final int position) {

        UserBean userBean = application.getUserBean();
        if (!EmptyUtil.isEmpty(userBean)){
            token = userBean.getToken();
        }

        HashMap params = new HashMap();
        params.put("baseMethod", Method.DELUSER);
        params.put("playerCode", mList.get(position).getPlayerCode());
        params.put("token", token);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.postRequest(groupApplyWaitPerfectActivity, params, new RequestCallBack<ResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResultBean> response) {
                ResultBean resultBean = response.body();
                if (!EmptyUtil.isEmpty(resultBean)){
                    int resultCode = resultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        mList.remove(mList.get(position));
                        groupApplyWaitPerfectActivity.update();
                        notifyDataSetChanged();
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

    public interface ItemClick {

        void itemClick(GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean teamsBean, int position);

    }

    ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }
}
