package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.GroupsuccesscardLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.GroupOrderMsgBean;
import com.huasport.smartsport.ui.matchapply.view.GroupApplySuccessActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.Util;

public class GroupApplyCardMsgAdapter extends BaseAdapter<GroupOrderMsgBean.ResultBean.OrderDetailBean.ApplysBean, BaseViewHolder> {

    private GroupApplySuccessActivity groupApplySuccessActivity;


    public GroupApplyCardMsgAdapter(GroupApplySuccessActivity groupApplySuccessActivity) {
        super(groupApplySuccessActivity);
        this.groupApplySuccessActivity = groupApplySuccessActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        GroupsuccesscardLayoutBinding groupsuccesscardLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(groupApplySuccessActivity), R.layout.groupsuccesscard_layout, parent, false);
        return new BaseViewHolder(groupsuccesscardLayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        GroupsuccesscardLayoutBinding groupsuccesscardLayoutBinding = (GroupsuccesscardLayoutBinding) baseViewHolder.getBinding();
        groupsuccesscardLayoutBinding.setVariable(BR.position, position);
        groupsuccesscardLayoutBinding.setVariable(BR.cardBean, mList.get(position));

//        if (mList.get(position).getMatchGroupName() == null || mList.get(position).getMatchGroupName().equals("")) {
//            groupsuccesscardLayoutBinding.verticalLine.setVisibility(View.GONE);
//        }
//        if (mList.get(position).getEventName() == null || mList.get(position).getEventName().equals("")) {
//            groupsuccesscardLayoutBinding.verticalLine.setVisibility(View.GONE);
//        }
        if (!EmptyUtil.isEmpty(mList.get(position).getMatchGroupName())) {
            if (!EmptyUtil.isEmpty(mList.get(position).getEventName())) {
                groupsuccesscardLayoutBinding.matchGroupName.setText("团队赛" + mList.get(position).getMatchGroupName() + "-" + mList.get(position).getEventName());
            } else {
                groupsuccesscardLayoutBinding.matchGroupName.setText("团队赛" + (String) mList.get(position).getMatchGroupName());
            }

        } else {
            if (!EmptyUtil.isEmpty(mList.get(position).getEventName())) {
                groupsuccesscardLayoutBinding.matchGroupName.setText("团队赛" + mList.get(position).getEventName());
            } else {
                groupsuccesscardLayoutBinding.matchGroupName.setText("团队赛");
            }

        }
        SpannableString spannableString = Util.matcherSearchText(groupApplySuccessActivity.getResources().getColor(R.color.color_FF8F00), groupsuccesscardLayoutBinding.matchGroupName.getText().toString(), "团队赛");
        groupsuccesscardLayoutBinding.matchGroupName.setText(spannableString);
        //报名卡长按保存事件
        groupsuccesscardLayoutBinding.cardApply.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                try {
                    //长按截取屏幕
                    Bitmap bitmap = Util.getBitmap(v, groupApplySuccessActivity);
                    //保存截图到手机
                    Util.saveImageToGallery(groupApplySuccessActivity, bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }
}
