package com.huasport.smartsport.ui.matchgrade.adapter;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MatcheventItemlayoutBinding;
import com.huasport.smartsport.ui.matchgrade.bean.MatchEventListBean;
import com.huasport.smartsport.ui.matchgrade.view.MatchGradeRankingsActivity;
import com.huasport.smartsport.util.EmptyUtil;

class MatchEventAdapter extends BaseAdapter<MatchEventListBean.ResultBean.ResultListBean, BaseViewHolder> {

    private FragmentActivity activity;
    private MatcheventItemlayoutBinding binding;

    public MatchEventAdapter(FragmentActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        MatcheventItemlayoutBinding matcheventItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.matchevent_itemlayout, parent, false);

        return new BaseViewHolder(matcheventItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {

        binding = (MatcheventItemlayoutBinding) baseViewHolder.getBinding();

        String area = mList.get(position).getArea();
        String group = mList.get(position).getGroup();
        String event = mList.get(position).getEvent();
        //最后一条隐藏掉线
        if (position == mList.size() - 1) {
            binding.lineView.setVisibility(View.GONE);
        }
        //title
        if (!EmptyUtil.isEmpty(area) && !EmptyUtil.isEmpty(group) && !EmptyUtil.isEmpty(event)) {
            binding.Eventname.setText(area + group + event);
        } else if (EmptyUtil.isEmpty(area) && !EmptyUtil.isEmpty(group) && !EmptyUtil.isEmpty(event)) {
            binding.Eventname.setText(group + event);
        } else if (!EmptyUtil.isEmpty(area) && EmptyUtil.isEmpty(group) && !EmptyUtil.isEmpty(event)) {
            binding.Eventname.setText(area + event);
        } else if (!EmptyUtil.isEmpty(area) && EmptyUtil.isEmpty(group) && EmptyUtil.isEmpty(event)) {
            binding.Eventname.setText(area + group);
        }
        //跳转到成绩页
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, MatchGradeRankingsActivity.class);
                intent.putExtra("comptitionCode", mList.get(position).getCompetitionCode());
                intent.putExtra("comptitionDate", "");
                intent.putExtra("matchName", mList.get(position).getMatchName());
                intent.putExtra("matchCode", mList.get(position).getMatchCode());
                activity.startActivity(intent);


            }
        });
    }


}
