package com.huasport.smartsport.ui.pcenter.attention.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.PhoneabookfriendsItemlayoutBinding;
import com.huasport.smartsport.ui.pcenter.attention.bean.AddressBookBean;
import com.huasport.smartsport.ui.pcenter.attention.view.AddFriendsActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;


public class PhoneABookFriendsAdapter extends BaseAdapter<AddressBookBean.ResultBean.FriendsBean, BaseViewHolder> {

    private AddFriendsActivity addFriendsActivity;

    public PhoneABookFriendsAdapter(AddFriendsActivity addFriendsActivity) {
        super(addFriendsActivity);
        this.addFriendsActivity = addFriendsActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        PhoneabookfriendsItemlayoutBinding phoneabookfriendsItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(addFriendsActivity), R.layout.phoneabookfriends_itemlayout, parent, false);

        return new BaseViewHolder(phoneabookfriendsItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        PhoneabookfriendsItemlayoutBinding binding = (PhoneabookfriendsItemlayoutBinding) baseViewHolder.getBinding();
        if (!EmptyUtil.isEmpty(mList.get(position).getRegisterNickName())) {
            binding.tvAbookfriendName.setText(mList.get(position).getRegisterNickName());
        }
        //描述
        if (!EmptyUtil.isEmpty(mList.get(position).getPosition())) {
            binding.tvAbookDescribe.setText((String) mList.get(position).getPosition());
        } else {
            binding.tvAbookDescribe.setText("");
        }

        if (mList.get(position).getIsAuth().equals(StatusVariable.YESAUTH)) {
            binding.imgAbookV.setVisibility(View.VISIBLE);
        } else if (mList.get(position).getIsAuth().equals(StatusVariable.NOAUTH)) {
            binding.imgAbookV.setVisibility(View.GONE);
        }
        //头像
        if (!EmptyUtil.isEmpty(mList.get(position).getRegisterPhoto())) {
            GlideUtil.LoadCircleImage(addFriendsActivity, (String) mList.get(position).getRegisterPhoto(), binding.imgAbookfriendHeader);
        } else {
            binding.imgAbookfriendHeader.setImageResource(R.mipmap.icon_defaultheader_yes);
        }

        String status = mList.get(position).getStatus();

        if (!EmptyUtil.isEmpty(status)) {
            if (status.equals(StatusVariable.FOLLOW)) {
                binding.tvAbookfriendAttention.setEnabled(true);
                binding.tvAbookfriendAttention.setText("已关注");
                binding.tvAbookfriendAttention.setTextColor(addFriendsActivity.getResources().getColor(R.color.color_999999));
                binding.tvAbookfriendAttention.setBackground(addFriendsActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
            } else if (status.equals(StatusVariable.UNFOLLOW)) {
                binding.tvAbookfriendAttention.setEnabled(true);
                binding.tvAbookfriendAttention.setText("关注");
                binding.tvAbookfriendAttention.setTextColor(addFriendsActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tvAbookfriendAttention.setBackground(addFriendsActivity.getResources().getDrawable(R.drawable.attentionbg_no));
            } else if (status.equals(StatusVariable.INVITE)) {
                binding.tvAbookfriendAttention.setEnabled(true);
                binding.tvAbookfriendAttention.setText("邀请");
                binding.tvAbookfriendAttention.setTextColor(addFriendsActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tvAbookfriendAttention.setBackground(addFriendsActivity.getResources().getDrawable(R.drawable.attentionbg_no));
            } else if (status.equals(StatusVariable.MUTUALFOLLOW)) {
                binding.tvAbookfriendAttention.setEnabled(true);
                binding.tvAbookfriendAttention.setText("互相关注");
                binding.tvAbookfriendAttention.setTextColor(addFriendsActivity.getResources().getColor(R.color.color_999999));
                binding.tvAbookfriendAttention.setBackground(addFriendsActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
            }

        }

        binding.tvAbookfriendAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attentionClick.attentionClick(mList.get(position), position);
            }
        });


    }


    public interface AttentionClick {

        void attentionClick(AddressBookBean.ResultBean.FriendsBean friendsBean, int position);

    }

    AttentionClick attentionClick;

    public void setAttentionClick(AttentionClick attentionClick) {
        this.attentionClick = attentionClick;
    }
}
