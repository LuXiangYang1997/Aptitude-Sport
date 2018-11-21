package com.huasport.smartsport.ui.matchapply.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.SearchresultItemBinding;
import com.huasport.smartsport.ui.matchapply.bean.SearchResultTestBean;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.ui.matchapply.view.SearchActivity;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;


/**
 * Created by 陆向阳 on 2018/6/12.
 */

public class SearchResultAdapter extends BaseAdapter<SearchResultTestBean.ResultBean.ListBean, BaseViewHolder> {

    private SearchActivity searchActivity;
    private static String format = "yyyy年MM月dd日";
    public SearchResultAdapter(SearchActivity searchActivity) {
        super(searchActivity);
        this.searchActivity = searchActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        SearchresultItemBinding searchresultItemBinding = DataBindingUtil.inflate(LayoutInflater.from(searchActivity), R.layout.searchresult_item, parent, false);

        return new BaseViewHolder(searchresultItemBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        SearchresultItemBinding searchresultItemBinding = (SearchresultItemBinding) baseViewHolder.getBinding();
        String poster = mList.get(position).getPoster();
        if (!EmptyUtil.isEmpty(poster)){
            GlideUtil.LoadImage(searchActivity,mList.get(position).getPoster(),  searchresultItemBinding.imgMatch);
        }
        String matchName = mList.get(position).getMatchName();
        if (!EmptyUtil.isEmpty(matchName)){
            searchresultItemBinding.tvMatchName.setText(matchName);
        }
        String endTime = mList.get(position).getEndTime();
        String startTime = mList.get(position).getStartTime();
        String startTimeStr = DateUtil.StrToDate(startTime, format);
        String endTimStr = DateUtil.StrToDate(endTime, format);
        String strDate = startTimeStr + "-" + endTimStr;
        searchresultItemBinding.tvTime.setText(strDate);

       searchresultItemBinding.llMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(searchActivity, MatchIntroduceActivity.class);
                intent.putExtra("matchCode", mList.get(position).getMatchCode());//传递matchcode给下一个界面
                intent.putExtra("imgUrl", mList.get(position).getPoster());
                intent.putExtra("type", "normal");
                searchActivity.startActivity(intent);
            }
        });
    }
}
