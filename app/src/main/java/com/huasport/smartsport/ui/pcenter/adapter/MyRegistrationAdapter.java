package com.huasport.smartsport.ui.pcenter.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MyregistrationItemBinding;
import com.huasport.smartsport.ui.matchapply.view.GroupApplySuccessActivity;
import com.huasport.smartsport.ui.matchapply.view.GroupApplyWaitPerfectActivity;
import com.huasport.smartsport.ui.matchapply.view.SuccessPaymentInfoActivity;
import com.huasport.smartsport.ui.pcenter.bean.MyRegistrationBean;
import com.huasport.smartsport.ui.pcenter.view.MatchStatusListActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;


/**
 * Created by 陆向阳 on 2018/6/13.
 */

public class MyRegistrationAdapter extends BaseAdapter<MyRegistrationBean.ResultBean.ListBean, BaseViewHolder> {

    private MatchStatusListActivity matchStatusListActivity;
    private String status;
    private Intent intent;
    private Intent intent2;
    private Intent intent3;

    public MyRegistrationAdapter(MatchStatusListActivity matchStatusListActivity) {
        super(matchStatusListActivity);
        this.matchStatusListActivity = matchStatusListActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        MyregistrationItemBinding myregistrationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(matchStatusListActivity), R.layout.myregistration_item, parent, false);
        return new BaseViewHolder(myregistrationItemBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        MyregistrationItemBinding myregistrationItemBinding = (MyregistrationItemBinding) baseViewHolder.getBinding();
        myregistrationItemBinding.setVariable(BR.position, position);
        myregistrationItemBinding.setVariable(BR.myRegistrationAdapter, this);
        myregistrationItemBinding.setVariable(BR.myRegistrationBean, mList.get(position));
        String matchImg = mList.get(position).getMatchImg();
        if (!EmptyUtil.isEmpty(matchImg)) {
            GlideUtil.LoadImage(matchStatusListActivity,matchImg,myregistrationItemBinding.itemIvImage);
        }
        //状态
        status = mList.get(position).getOrderStatusDesc();
        if ("待支付".equals(status)) {
            myregistrationItemBinding.itemTvOrderStatus.setBackgroundResource(R.drawable.young_side_line);
            myregistrationItemBinding.itemTvOrderStatus.setTextColor(ContextCompat.getColor(matchStatusListActivity, R.color.color_00CED1));
        } else if ("待完善".equals(status)) {
            myregistrationItemBinding.itemTvOrderStatus.setBackgroundResource(R.drawable.yellow_side_line);
            myregistrationItemBinding.itemTvOrderStatus.setTextColor(ContextCompat.getColor(matchStatusListActivity, R.color.color_FF8F00));
        } else if ("成功".equals(status)) {
            myregistrationItemBinding.itemTvOrderStatus.setBackgroundResource(R.drawable.red_side_line);
            myregistrationItemBinding.itemTvOrderStatus.setTextColor(ContextCompat.getColor(matchStatusListActivity, R.color.color_E50113));
        } else if ("取消".equals(status)) {
            myregistrationItemBinding.itemNext.setVisibility(View.GONE);
            myregistrationItemBinding.itemTvOrderStatus.setBackgroundResource(R.drawable.gray_side_line);
            myregistrationItemBinding.itemTvOrderStatus.setTextColor(ContextCompat.getColor(matchStatusListActivity, R.color.color_999999));

        }
    }

    public void onClickItem(MyRegistrationBean.ResultBean.ListBean bean, int position) {
        if ("待完善".equals(bean.getOrderStatusDesc())) {
            //待完善
            if (bean.getEventType().equals("group")) {
                intent = new Intent(matchStatusListActivity, GroupApplyWaitPerfectActivity.class);
                intent.putExtra("orderCode", bean.getOrderCode());
                intent.putExtra("orderStatus", bean.getOrderStatus());

            } else {
//                intent = new Intent(matchStatusListActivity, RegistrationInformationActivity.class);
//                intent.putExtra("orderCode", bean.getOrderCode());
//                intent.putExtra("orderStatus", bean.getOrderStatus());
            }
            matchStatusListActivity.startActivity(intent);
        } else if ("待支付".equals(bean.getOrderStatusDesc())) {
            //待支付、已支付
            if (bean.getEventType().equals("group")) {
//                intent2 = new Intent(matchStatusListActivity, GroupApplyWaitPayActivity.class);
//                intent2.putExtra("orderCode", bean.getOrderCode());
//                intent2.putExtra("orderStatus", bean.getOrderStatus());
//                intent2.putExtra("orderType", "wait_pay");
            } else {
                intent2 = new Intent(matchStatusListActivity, SuccessPaymentInfoActivity.class);
                intent2.putExtra("orderCode", bean.getOrderCode());
                intent2.putExtra("orderStatus", bean.getOrderStatus());
                intent2.putExtra("orderType", "waitpay");
            }
            matchStatusListActivity.startActivity(intent2);

        } else if ("成功".equals(bean.getOrderStatusDesc())) {
            if (bean.getEventType().equals("group")) {
                intent3 = new Intent(matchStatusListActivity, GroupApplySuccessActivity.class);
                intent3.putExtra("orderCode", bean.getOrderCode());
                intent3.putExtra("orderStatus", bean.getOrderStatus());
                intent3.putExtra("orderType", "personal_success");
            } else if (bean.getEventType().equals("personal")) {
                intent3 = new Intent(matchStatusListActivity, SuccessPaymentInfoActivity.class);
                intent3.putExtra("orderCode", bean.getOrderCode());
                intent3.putExtra("orderStatus", bean.getOrderStatus());
                intent3.putExtra("orderType", "mySuccess");
            }
            matchStatusListActivity.startActivity(intent3);
        }

    }
}
