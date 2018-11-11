package com.huasport.smartsport.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/6.
 */

public abstract class BasePageAdapter<T> extends PagerAdapter {

    public List<T> mData;//数据源
    private SparseArray<View> mViews;
    private Context context;


    public BasePageAdapter(Context context) {
        this.context = context;
        this.mData = new ArrayList<>();
        mViews = new SparseArray<View>(mData.size());
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//设置MAX_VALUE实现循环播放
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = newView(container, position);
            mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }

    public abstract View newView(ViewGroup parent, int position);

    //Item销毁后移除这个View
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    //加载数据
    public void loadData(List data) {
        Log.e("DATA",data.size()+"");
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

}

