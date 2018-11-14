package com.huasport.smartsport.util.refreshLoadmore;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * 刷新加载监听
 */
public interface RefreshLoadMoreListener {
    /**
     * 刷新
     * @param refreshLayout
     */
    void refresh(RefreshLayout refreshLayout,int type);

    /**
     * 加载
     * @param refreshLayout
     */
    void loadMore(RefreshLayout refreshLayout,int type);

}
