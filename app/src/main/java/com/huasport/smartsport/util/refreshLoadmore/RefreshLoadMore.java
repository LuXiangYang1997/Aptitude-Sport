package com.huasport.smartsport.util.refreshLoadmore;

import android.support.annotation.NonNull;

import com.huasport.smartsport.constant.StatusVariable;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class RefreshLoadMore {

    private SmartRefreshLayout smartRefreshLayout;
    private RefreshLoadMoreListener refreshLoadMoreListener;


    public RefreshLoadMore(SmartRefreshLayout smartRefreshLayout,RefreshLoadMoreListener refreshLoadMoreListener) {
        this.smartRefreshLayout = smartRefreshLayout;
        this.refreshLoadMoreListener = refreshLoadMoreListener;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //刷新监听
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                    refreshLoadMoreListener.refresh(refreshLayout, StatusVariable.REFRESH);

            }
        });

        //加载监听
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                refreshLoadMoreListener.loadMore(refreshLayout,StatusVariable.LOADMORE);

            }
        });


    }


}
