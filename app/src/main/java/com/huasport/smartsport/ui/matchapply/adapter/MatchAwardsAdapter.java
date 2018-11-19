package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MatchawardsItemBinding;
import com.huasport.smartsport.ui.matchapply.bean.MatchsBean;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;


/**
 * Created by 陆向阳 on 2018/6/7.
 */

public class MatchAwardsAdapter extends BaseAdapter<MatchsBean.ResultBean.MatchBean.AwardsBean, BaseViewHolder> {

    private MatchIntroduceActivity matchIntroduceActivity;
    private MatchawardsItemBinding matchawardsItemBinding;

    public MatchAwardsAdapter(MatchIntroduceActivity matchIntroduceActivity) {
        super(matchIntroduceActivity);
        this.matchIntroduceActivity = matchIntroduceActivity;

    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        matchawardsItemBinding = DataBindingUtil.inflate(LayoutInflater.from(matchIntroduceActivity), R.layout.matchawards_item, parent, false);

        return new BaseViewHolder(matchawardsItemBinding);

    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {

        MatchawardsItemBinding binding = (MatchawardsItemBinding) baseViewHolder.getBinding();
        //等级
        String awardType = mList.get(position).getAwardType();
        if (!EmptyUtil.isEmpty(awardType)){
            if (awardType.equals("first")) {
                binding.imgLevel.setImageResource(R.mipmap.icon_no_one);
            } else if (awardType.equals("second")) {
                binding.imgLevel.setImageResource(R.mipmap.icon_no_two);
            } else if (awardType.equals("third")) {
                binding.imgLevel.setImageResource(R.mipmap.icon_no_three);
            }
        }
        //图片
        String prizeImg = mList.get(position).getPrizeImg();

        if(!EmptyUtil.isEmpty(prizeImg)){

            GlideUtil.LoadCircleImage(matchIntroduceActivity,prizeImg,binding.imgPrizeImg);

        }
        //name
        String awardName = mList.get(position).getAwardName();
        if(!EmptyUtil.isEmpty(awardName)){

            binding.tvAwardName.setText(awardName);
        }
        String prizeName = mList.get(position).getPrizeName();
        if(!EmptyUtil.isEmpty(prizeName)){
            binding.tvPrizeName.setText(prizeName);
        }

    }

}
