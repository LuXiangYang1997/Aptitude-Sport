package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MatchTitleLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.MatchsBean;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;

import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/19.
 */

public class MatchAdapter extends BaseAdapter<MatchsBean.ResultBean.MatchBean, BaseViewHolder> {

    private MatchIntroduceActivity matchIntroduceActivity;
    private MatchAwardsAdapter matchAwardsAdapter;
    private int lineCounts;
    private int foldLines = 3; //大于3行的时候折叠
    private boolean isOpen = false;//是否展开
    private static String format = "yyyy年MM月dd日";
    private String sTime;
    private String eTime;

    public MatchAdapter(MatchIntroduceActivity matchIntroduceActivity) {
        super(matchIntroduceActivity);
        this.matchIntroduceActivity = matchIntroduceActivity;
    }


    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        MatchTitleLayoutBinding matchTitleLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(matchIntroduceActivity), R.layout.match_title_layout, parent, false);
        return new BaseViewHolder(matchTitleLayoutBinding);


    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {

        final MatchTitleLayoutBinding matchTitleLayoutBinding = (MatchTitleLayoutBinding) baseViewHolder.getBinding();
        //matchImg
        if(!EmptyUtil.isEmpty(mList.get(position).getPoster())){
            GlideUtil.LoadImage(matchIntroduceActivity,mList.get(position).getPoster(),matchTitleLayoutBinding.imgMatch);
        }
        //matchName
        if(!EmptyUtil.isEmpty(mList.get(position).getMatchName())){
            matchTitleLayoutBinding.tvMatchName.setText(mList.get(position).getMatchName());
        }

        //时间
        String startTime = mList.get(position).getStartTime();
        String endTime = mList.get(position).getEndTime();

        if(!EmptyUtil.isEmpty(startTime)){
            sTime = DateUtil.StrToDate(startTime, format);
        }
        if (!EmptyUtil.isEmpty(endTime)){
            eTime = DateUtil.StrToDate(endTime, format);
        }
        if(!EmptyUtil.isEmpty(sTime)&&!EmptyUtil.isEmpty(eTime)){
            matchTitleLayoutBinding.tvMatchTime.setText(sTime+"-"+eTime);
        }else if (!EmptyUtil.isEmpty(sTime)&&EmptyUtil.isEmpty(eTime)){
            matchTitleLayoutBinding.tvMatchTime.setText(sTime);
        }else if (EmptyUtil.isEmpty(sTime)&&!EmptyUtil.isEmpty(eTime)){
            matchTitleLayoutBinding.tvMatchTime.setText(eTime);
        }

        //对Indroduce进行判空操作
        MatchsBean.ResultBean.MatchBean matchBean = mList.get(position);
        if (!EmptyUtil.isEmpty(matchBean.getIntroduce()) || !EmptyUtil.isEmpty(matchBean.getIntroduce())) {
            matchTitleLayoutBinding.matchIntroduce.setVisibility(View.VISIBLE);//显示标题
            matchTitleLayoutBinding.tvMatchappro.setMovementMethod(LinkMovementMethod.getInstance());
            matchTitleLayoutBinding.tvMatchappro.setText(Html.fromHtml(mList.get(position).getIntroduce()));//加载Html
        } else {
            matchTitleLayoutBinding.matchIntroduce.setVisibility(View.GONE);
        }
        //初始化，后续还会优化
        textView(matchTitleLayoutBinding);

        List<MatchsBean.ResultBean.MatchBean.AwardsBean> awards = mList.get(position).getAwards();//获取比赛奖项
        //比赛奖项
        if (awards.size() > 0) {//判断是否大于0
            matchAwardsAdapter = new MatchAwardsAdapter(matchIntroduceActivity);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(matchIntroduceActivity);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置横向滑动
            matchTitleLayoutBinding.matchAwardsRecyclerView.setLayoutManager(linearLayoutManager);//设置Manager
            matchTitleLayoutBinding.matchAwardsRecyclerView.setAdapter(matchAwardsAdapter);//设置适配器
            matchAwardsAdapter.loadData(awards);
        } else {
            matchTitleLayoutBinding.matchAwardLayout.setVisibility(View.GONE);
        }

        // 比赛装备
        List<MatchsBean.ResultBean.MatchBean.ChaptersBean> chapters = mList.get(position).getChapters();

        if (chapters.size() > 0) {
            MatchEquipAdapter matchEquipAdapter = new MatchEquipAdapter(matchIntroduceActivity);
            LinearLayoutManager equipLayoutManager = new LinearLayoutManager(matchIntroduceActivity);
            matchTitleLayoutBinding.matchEquipsRecyclerView.setLayoutManager(equipLayoutManager);//设置Manager
            matchTitleLayoutBinding.matchEquipsRecyclerView.setAdapter(matchEquipAdapter);//设置适配器
            matchTitleLayoutBinding.matchEquipsRecyclerView.setNestedScrollingEnabled(false);//禁止RecyclerView滑动
            matchEquipAdapter.loadData(chapters);
        } else {
            matchTitleLayoutBinding.matchEquipsRecyclerView.setVisibility(View.GONE);
        }

        matchTitleLayoutBinding.llUnfold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    //展开
                    matchTitleLayoutBinding.tvMatchappro.setHeight(matchTitleLayoutBinding.tvMatchappro.getLineHeight() * foldLines);
                    matchTitleLayoutBinding.arrows.setImageResource(R.mipmap.icon_arrow_zhankai);
                    matchTitleLayoutBinding.expandTextview.setText("展开");
                    isOpen = false;
                } else {
                    //收起
                    matchTitleLayoutBinding.tvMatchappro.setHeight(matchTitleLayoutBinding.tvMatchappro.getLineHeight() * matchTitleLayoutBinding.tvMatchappro.getLineCount());
                    matchTitleLayoutBinding.arrows.setImageResource(R.mipmap.icon_arrow_shouqi);
                    matchTitleLayoutBinding.expandTextview.setText("收起");
                    isOpen = true;
                }
            }
        });


    }

    //初始化textView高度
    public void textView(MatchTitleLayoutBinding matchTitleLayoutBinding) {

        lineCounts = matchTitleLayoutBinding.tvMatchappro.getLineCount();//获取TextView的行数
        //判断是否大于所设置的行数，大于则是展开，否则是收起，初始化展开TextView的文本
        Log.e("Height", matchTitleLayoutBinding.tvMatchappro.getHeight() + "");
        if (isOpen && matchTitleLayoutBinding.tvMatchappro.getHeight() != lineCounts * matchTitleLayoutBinding.tvMatchappro.getLineHeight()) {
            matchTitleLayoutBinding.tvMatchappro.setHeight(matchTitleLayoutBinding.tvMatchappro.getLineHeight() * matchTitleLayoutBinding.tvMatchappro.getLineCount());
        } else if (!isOpen && matchTitleLayoutBinding.tvMatchappro.getHeight() != foldLines * matchTitleLayoutBinding.tvMatchappro.getLineHeight()) {
            matchTitleLayoutBinding.tvMatchappro.setHeight(matchTitleLayoutBinding.tvMatchappro.getLineHeight() * foldLines);
        }
    }

}
