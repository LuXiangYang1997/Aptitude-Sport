package com.huasport.smartsport.ui.pcenter.attention.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;

import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.AddressbookfriendItemlayoutBinding;
import com.huasport.smartsport.ui.pcenter.attention.bean.AddressBookBean;
import com.huasport.smartsport.ui.pcenter.attention.view.AddFriendsActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;

public class AddressBookFriendsAdapter extends BaseAdapter<AddressBookBean.ResultBean.InvitesBean, BaseViewHolder> {

    private AddFriendsActivity addFriendsActivity;
    private AddressbookfriendItemlayoutBinding binding;

    public AddressBookFriendsAdapter(AddFriendsActivity addFriendsActivity) {
        super(addFriendsActivity);
        this.addFriendsActivity = addFriendsActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        AddressbookfriendItemlayoutBinding addressbookfriendItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(addFriendsActivity), R.layout.addressbookfriend_itemlayout, parent, false);
        return new BaseViewHolder(addressbookfriendItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        binding = (AddressbookfriendItemlayoutBinding) baseViewHolder.getBinding();
        //认证状态
//        if (mList.get(position).getIsAuth().equals(StatusVariable.YESAUTH)) {
//            binding.imgAbookV.setVisibility(View.VISIBLE);
//        } else if (mList.get(position).getIsAuth().equals(StatusVariable.NOAUTH)){
//            binding.imgAbookV.setVisibility(View.GONE);
//        }
        //名字
        if (!EmptyUtil.isEmpty(mList.get(position).getRegisterNickName())) {
            binding.tvAbookName.setText((String) mList.get(position).getRegisterNickName());
        } else {
            binding.tvAbookName.setText("");
        }
        //描述
        if (!EmptyUtil.isEmpty(mList.get(position).getPosition())) {
            binding.tvAbookDescribe.setText((String) mList.get(position).getPosition());
        } else {
            binding.tvAbookDescribe.setText("");
        }
        final String status = mList.get(position).getStatus();

        // 关注状态  邀请(invite) 未关注(unfollow) 已关注(follow) 互相关注(mutualfollow)
        if (!EmptyUtil.isEmpty(status)) {
            if (status.equals(StatusVariable.FOLLOW)) {
                binding.tvAbookAttention.setEnabled(true);
                binding.tvAbookAttention.setText("已关注");
                binding.tvAbookAttention.setTextColor(addFriendsActivity.getResources().getColor(R.color.color_999999));
                binding.tvAbookAttention.setBackground(addFriendsActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
            } else if (status.equals(StatusVariable.UNFOLLOW)) {
                binding.tvAbookAttention.setEnabled(true);
                binding.tvAbookAttention.setText("关注");
                binding.tvAbookAttention.setTextColor(addFriendsActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tvAbookAttention.setBackground(addFriendsActivity.getResources().getDrawable(R.drawable.attentionbg_no));
            } else if (status.equals(StatusVariable.INVITE)) {
                binding.tvAbookAttention.setEnabled(true);
                binding.tvAbookAttention.setText("邀请");
                binding.tvAbookAttention.setTextColor(addFriendsActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tvAbookAttention.setBackground(addFriendsActivity.getResources().getDrawable(R.drawable.attentionbg_no));
            } else if (status.equals(StatusVariable.MUTUALFOLLOW)) {
                binding.tvAbookAttention.setEnabled(true);
                binding.tvAbookAttention.setText("互相关注");
                binding.tvAbookAttention.setTextColor(addFriendsActivity.getResources().getColor(R.color.color_999999));
                binding.tvAbookAttention.setBackground(addFriendsActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
            }

        }
        //头像
        if (!EmptyUtil.isEmpty(mList.get(position).getRegisterPhoto())) {
            GlideUtil.LoadCircleImage(addFriendsActivity, (String) mList.get(position).getRegisterPhoto(), binding.imgAbookHeader);
        } else {
            binding.imgAbookHeader.setImageResource(R.mipmap.icon_defaultheader_yes);
        }

        binding.tvAbookAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals(StatusVariable.INVITE)) {
                    attentionClick.attentionClick(mList.get(position), position);
                }
            }
        });
    }

    public interface AttentionClick {

        void attentionClick(AddressBookBean.ResultBean.InvitesBean listBean, int position);

    }

    AttentionClick attentionClick;

    public void setAttentionClick(AttentionClick attentionClick) {
        this.attentionClick = attentionClick;
    }
}
