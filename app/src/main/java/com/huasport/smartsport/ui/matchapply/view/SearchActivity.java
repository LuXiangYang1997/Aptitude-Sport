package com.huasport.smartsport.ui.matchapply.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ActivitySearchBinding;
import com.huasport.smartsport.ui.matchapply.adapter.SearchResultAdapter;
import com.huasport.smartsport.ui.matchapply.vm.SearchVM;


//搜索Activity
public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchVM> {

    private SearchVM searchVM;
    private SearchResultAdapter searchResultAdapter;

    @Override
    public int initContentView() {
        return R.layout.activity_search;
    }

    @Override
    public int initVariableId() {
        return BR.searchVM;
    }

    @Override
    public SearchVM initViewModel() {

        searchResultAdapter = new SearchResultAdapter(this);
        searchVM = new SearchVM(this,searchResultAdapter , binding.searchHistory);
        return searchVM;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        toolbarBinding.toolbar.setVisibility(View.GONE);
        //搜索结果
        binding.searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.searchResultRecyclerView.setAdapter(searchResultAdapter);

    }

    //解决EditText获取焦点问题
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //当isShouldHideInput(v, ev)为true时，表示的是点击输入框区域，则需要显示键盘，同时显示光标，反之，需要隐藏键盘、光标
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //处理Editext的光标隐藏、显示逻辑
                    binding.searchEdittext.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    //输入法
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
