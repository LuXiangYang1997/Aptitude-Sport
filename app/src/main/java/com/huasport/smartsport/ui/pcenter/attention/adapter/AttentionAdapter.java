package com.huasport.smartsport.ui.pcenter.attention.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.AttentionItemlayoutBinding;
import com.huasport.smartsport.ui.discover.view.ReleaseActivity;
import com.huasport.smartsport.ui.pcenter.attention.bean.AttentionBean;
import com.huasport.smartsport.ui.pcenter.attention.view.AttentionActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;


public class AttentionAdapter extends BaseAdapter<AttentionBean.ResultBean.ListBean, BaseViewHolder> {

    private AttentionActivity attentionActivity;
    private AttentionItemlayoutBinding binding;
    private Intent intent;

    public AttentionAdapter(AttentionActivity attentionActivity) {
        super(attentionActivity);
        this.attentionActivity = attentionActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        AttentionItemlayoutBinding attentionItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(attentionActivity), R.layout.attention_itemlayout, parent, false);

        return new BaseViewHolder(attentionItemlayoutBinding);
    }

    @Override
    public void onBindVH(final BaseViewHolder baseViewHolder, final int position) {

        binding = (AttentionItemlayoutBinding) baseViewHolder.getBinding();
        //认证状态
        if (mList.get(position).getIsAuth().equals(StatusVariable.YESAUTH)) {
            binding.imgAttentionV.setVisibility(View.VISIBLE);
        } else if (mList.get(position).getIsAuth().equals(StatusVariable.NOAUTH)) {
            binding.imgAttentionV.setVisibility(View.GONE);
        }
        //名字

        if (!EmptyUtil.isEmpty(mList.get(position).getRegisterNickName())) {
            String registerNickName = (String) mList.get(position).getRegisterNickName();
            if (registerNickName.length() > 20) {
                String substring = registerNickName.substring(0, 20);
                binding.tvAttentionname.setText(substring + "...");
            } else {
                binding.tvAttentionname.setText(registerNickName);
            }
        } else {
            binding.tvAttentionname.setText("游客");
        }
        //描述
        if (!EmptyUtil.isEmpty(mList.get(position).getPosition())) {
            binding.tvAttentionsift.setText((String) mList.get(position).getPosition());
        } else {
            binding.tvAttentionsift.setText("");
        }
        final String status = mList.get(position).getStatus();

        // 关注状态  邀请(invite) 未关注(unfollow) 已关注(follow) 互相关注(mutualfollow)
        if (!EmptyUtil.isEmpty(status)) {
            if (status.equals(StatusVariable.FOLLOW)) {
                binding.tvAttention.setEnabled(true);
                binding.tvAttention.setText("已关注");
                binding.tvAttention.setTextColor(attentionActivity.getResources().getColor(R.color.color_999999));
                binding.tvAttention.setBackground(attentionActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
            } else if (status.equals(StatusVariable.UNFOLLOW)) {
                binding.tvAttention.setEnabled(true);
                binding.tvAttention.setText("关注");
                binding.tvAttention.setTextColor(attentionActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tvAttention.setBackground(attentionActivity.getResources().getDrawable(R.drawable.attentionbg_no));
            } else if (status.equals(StatusVariable.INVITE)) {
                binding.tvAttention.setEnabled(true);
                binding.tvAttention.setText("邀请");
                binding.tvAttention.setTextColor(attentionActivity.getResources().getColor(R.color.color_FF8F00));
                binding.tvAttention.setBackground(attentionActivity.getResources().getDrawable(R.drawable.attentionbg_no));
            } else if (status.equals(StatusVariable.MUTUALFOLLOW)) {
                binding.tvAttention.setEnabled(true);
                binding.tvAttention.setText("互相关注");
                binding.tvAttention.setTextColor(attentionActivity.getResources().getColor(R.color.color_999999));
                binding.tvAttention.setBackground(attentionActivity.getResources().getDrawable(R.drawable.attentionbg_yes));
            }

        }

        String isAddressList = mList.get(position).getIsAddressList();
        if (!EmptyUtil.isEmpty(isAddressList)) {
            if (isAddressList.equals(StatusVariable.NOADDRESSBOOK)) {
                binding.tvAddressbook.setVisibility(View.GONE);
            } else if (isAddressList.equals(StatusVariable.YESADDRESSBOOK)) {
                binding.tvAddressbook.setVisibility(View.VISIBLE);
            }
        }
        //头像
        if (!EmptyUtil.isEmpty(mList.get(position).getRegisterPhoto())) {
            GlideUtil.LoadCircleImage(attentionActivity, (String) mList.get(position).getRegisterPhoto(), binding.headerImg);
        } else {
           binding.headerImg.setImageResource(R.mipmap.icon_defaultheader_yes);
        }

        binding.tvAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals(StatusVariable.FOLLOW) || status.equals(StatusVariable.UNFOLLOW) || status.equals(StatusVariable.MUTUALFOLLOW)) {
                    attentionClick.attentionClick(baseViewHolder, position);
                }
            }
        });


        binding.headerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(attentionActivity, ReleaseActivity.class);
                intent.putExtra("registerId", mList.get(position).getRegisterId());
                attentionActivity.startActivityForResult(intent, 0);
            }
        });


    }

    public interface AttentionClick {

        void attentionClick(BaseViewHolder baseViewHolder, int position);

    }

    AttentionClick attentionClick;

    public void setAttentionClick(AttentionClick attentionClick) {
        this.attentionClick = attentionClick;
    }


}
