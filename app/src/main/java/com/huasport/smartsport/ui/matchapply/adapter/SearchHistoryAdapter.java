package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.SearchHistoryItemBinding;
import com.huasport.smartsport.ui.matchapply.bean.SearchHistoryTestBean;
import com.huasport.smartsport.ui.matchapply.view.SearchActivity;

/**
 * Created by 陆向阳 on 2018/6/12.
 */
public class SearchHistoryAdapter extends BaseAdapter<SearchHistoryTestBean, BaseViewHolder> {

    private SearchActivity searchActivity;
    private SearchHistoryItemBinding searchHistoryItemBinding;

    public SearchHistoryAdapter(SearchActivity searchActivity) {
        super(searchActivity);
        this.searchActivity = searchActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        searchHistoryItemBinding = DataBindingUtil.inflate(LayoutInflater.from(searchActivity), R.layout.search_history_item, parent, false);

        return new BaseViewHolder(searchHistoryItemBinding);
    }

    @Override
    public void onBindVH(final BaseViewHolder baseViewHolder, int position) {

        SearchHistoryItemBinding searchHistoryItemBinding = (SearchHistoryItemBinding) baseViewHolder.getBinding();
        searchHistoryItemBinding.setVariable(BR.position, position);
        searchHistoryItemBinding.setVariable(BR.searchTestBean, mList.get(position));
        searchHistoryItemBinding.setVariable(BR.searchHistoryAdapter, this);




    }
}
