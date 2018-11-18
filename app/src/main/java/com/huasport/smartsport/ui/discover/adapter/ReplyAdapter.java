package com.huasport.smartsport.ui.discover.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.databinding.ReplyItemlayoutBinding;
import com.huasport.smartsport.ui.discover.bean.ReplyBean;
import com.huasport.smartsport.ui.discover.view.ReplyActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.Util;

public class ReplyAdapter extends BaseAdapter<ReplyBean.ResultBean.DataBean.ReplyInfosBean, BaseViewHolder> {

    private final String dynamicId;
    private ReplyActivity replyActivity;
    private String registerCode = "";

    public ReplyAdapter(ReplyActivity replyActivity) {
        super(replyActivity);
        this.replyActivity = replyActivity;
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)){
            registerCode = userBean.getRegisterCode();
        }
        dynamicId = replyActivity.getIntent().getStringExtra("dynamicId");
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        ReplyItemlayoutBinding replyItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(replyActivity), R.layout.reply_itemlayout, parent, false);

        return new BaseViewHolder(replyItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {

        ReplyItemlayoutBinding binding = (ReplyItemlayoutBinding) baseViewHolder.getBinding();
        String registerId = mList.get(position).getRegisterId();

        if (registerId.equals(dynamicId)) {

            binding.replyName.setText(replyActivity.getString(R.string.author_reply));
            binding.tvReplycomment.setText(Util.getHtmlContent(mList.get(position).getContent()));
        } else {

            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterNickName())) {
                binding.replyName.setText(mList.get(position).getRegisterNickName() + "回复：");
            } else {
                binding.replyName.setText(replyActivity.getString(R.string.tourist_reply));
            }

            binding.tvReplycomment.setText(  Util.getHtmlContent(mList.get(position).getContent()));

        }

        if (!EmptyUtil.isEmpty(mList.get(position).getContent())) {
            binding.tvReplycomment.setText(  Util.getHtmlContent(mList.get(position).getContent()));
        }

    }
}
