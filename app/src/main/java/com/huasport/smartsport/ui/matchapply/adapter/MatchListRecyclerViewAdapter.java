package com.huasport.smartsport.ui.matchapply.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MatchlistrecyclerItemBinding;
import com.huasport.smartsport.ui.matchapply.bean.MatchListBeans;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.ui.matchapply.view.MatchListActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;


/**
 * Created by 陆向阳 on 2018/6/7.
 */

public class MatchListRecyclerViewAdapter extends BaseAdapter<MatchListBeans.ResultBean.ListBean, BaseViewHolder> {

    private MatchListActivity matchListActivity;
    private MatchlistrecyclerItemBinding matchlistrecyclerItemBinding;

    public MatchListRecyclerViewAdapter(MatchListActivity matchListActivity) {
        super(matchListActivity);
        this.matchListActivity = matchListActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        matchlistrecyclerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(matchListActivity), R.layout.matchlistrecycler_item, parent, false);

        return new BaseViewHolder(matchlistrecyclerItemBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        //绑定数据
        MatchlistrecyclerItemBinding binding = (MatchlistrecyclerItemBinding) baseViewHolder.getBinding();
        //赛事图片
        if(!EmptyUtil.isEmpty(mList.get(position).getPoster())){
            GlideUtil.LoadImage(matchListActivity,mList.get(position).getPoster(),binding.imgMatch);
        }
        //赛事name
        if(!EmptyUtil.isEmpty(mList.get(position).getMatchName())){
            binding.tvName.setText(mList.get(position).getMatchName());
        }
        //报名截止时间
        if(!EmptyUtil.isEmpty(mList.get(position).getRegTime())){
            binding.tvTime.setText("报名截止:"+mList.get(position).getRegTime());
        }

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(matchListActivity, MatchIntroduceActivity.class);
        intent.putExtra("matchCode", mList.get(position).getMatchCode());//传递matchcode给下一个界面
        intent.putExtra("imgUrl", mList.get(position).getPoster());
        intent.putExtra("type","normal");

        matchListActivity.startActivity(intent);
            }
        });

    }







}
