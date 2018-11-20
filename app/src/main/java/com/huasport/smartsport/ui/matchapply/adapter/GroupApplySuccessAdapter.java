package com.huasport.smartsport.ui.matchapply.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.GroupapplysuccessOneLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.GroupMemberBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupOrderMsgBean;
import com.huasport.smartsport.util.EmptyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupApplySuccessAdapter extends BaseAdapter<GroupMemberBean, BaseViewHolder> {

    private Context mContext;
    private List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> playersBeanList = new ArrayList<>();
    private HashMap<String, String> urlMap = new HashMap<>();//存储证件照的url
    private String playerName;

    public GroupApplySuccessAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        GroupapplysuccessOneLayoutBinding groupApplySuccessOneLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.groupapplysuccess_one_layout, parent, false);
        return new BaseViewHolder(groupApplySuccessOneLayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {

        GroupapplysuccessOneLayoutBinding groupapplysuccessOneLayoutBinding = (GroupapplysuccessOneLayoutBinding) baseViewHolder.getBinding();
        //过滤空数据
        List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> players = mList.get(position).getPlayersBeans();

        if (!EmptyUtil.isEmpty(mList.get(position).getFrontUrl())) {
            groupapplysuccessOneLayoutBinding.imgGroupsuccessImgOne.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(mList.get(position).getFrontUrl()).into(groupapplysuccessOneLayoutBinding.imgGroupsuccessImgOne);
        }
        if (!EmptyUtil.isEmpty(mList.get(position).getContractUrl())) {
            groupapplysuccessOneLayoutBinding.imgGroupsuccessImgTwo.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(mList.get(position).getContractUrl()).into(groupapplysuccessOneLayoutBinding.imgGroupsuccessImgTwo);
        }

        for (int i = 0; i < players.size(); i++) {
            if (!EmptyUtil.isEmpty(players.get(i).getCnname())) {
                if (!EmptyUtil.isEmpty(players.get(i).getVal())) {
                    // if (players.get(i).getVal() != null && !players.get(i).getVal().equals("null") && !players.get(i).getCnname().equals("")) {
                    if (players.get(i).getCnname().equals("证件类型")) {
                        if (players.get(i).getVal().equals("2")) {
                            players.get(i).setVal("护照");
                        } else if (players.get(i).getVal().equals("1")) {
                            players.get(i).setVal("身份证");
                        } else if (players.get(i).getVal().equals("4")) {
                            players.get(i).setVal("军官证");
                        }
                    }
                    if (players.get(i).getCnname().equals("性别")) {
                        if (players.get(i).getVal().equals("m")) {
                            players.get(i).setVal("男");
                        } else {
                            players.get(i).setVal("女");
                        }
                    }
                }

                playersBeanList.add(players.get(i));

            }
        }
        String playerName = mList.get(position).getPlayerName();
        Log.e("playerBeanList", mList.size() + "----------lxy");
        GroupPersonalMsgAdapter groupPersonalMsgAdapter = new GroupPersonalMsgAdapter(mContext);
        groupapplysuccessOneLayoutBinding.groupTeamMessageRecylerView.setLayoutManager(new LinearLayoutManager(mContext));
        groupapplysuccessOneLayoutBinding.groupTeamMessageRecylerView.setAdapter(groupPersonalMsgAdapter);
        groupPersonalMsgAdapter.loadData(mList.get(position).getPlayersBeans());

    }
}
