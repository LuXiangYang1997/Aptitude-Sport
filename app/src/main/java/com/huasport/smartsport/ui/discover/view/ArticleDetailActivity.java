package com.huasport.smartsport.ui.discover.view;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ArticledetailLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.ArticleDetailAdapter;
import com.huasport.smartsport.ui.discover.vm.ArticleDetailVm;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.impl.ScrollBoundaryDeciderAdapter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

public class ArticleDetailActivity extends BaseActivity<ArticledetailLayoutBinding, ArticleDetailVm> implements View.OnClickListener {

    private ArticleDetailVm articleDetailVm;
    private ArticleDetailAdapter articleDetailAdapter;
    public int tabPos = 0;
    public String dynamicId = "";
    private boolean activityState;
    public ObservableField<String> isOneSelf=new ObservableField<>("");
    public ObservableField<String> dyId=new ObservableField<>("");
    @Override
    public int initContentView() {
        return R.layout.articledetail_layout;
    }

    @Override
    public int initVariableId() {
        return BR.articleDetailVm;
    }

    @Override
    public ArticleDetailVm initViewModel() {

        dynamicId = getIntent().getStringExtra("dynamicId");

        articleDetailAdapter = new ArticleDetailAdapter(this);

        articleDetailVm = new ArticleDetailVm(this, articleDetailAdapter);

        return articleDetailVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();


        toolbarBinding.llLeft.setOnClickListener(this);
        toolbarBinding.tvRight.setOnClickListener(this);
        binding.dynamicDteailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.dynamicDteailRecyclerView.setAdapter(articleDetailAdapter);

        binding.smartRefreshlayout.setEnableAutoLoadMore(true);
        binding.smartRefreshlayout.setScrollBoundaryDecider(new ScrollBoundaryDeciderAdapter());//自定义滚动边界
        binding.smartRefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                articleDetailVm.loadMore();
                binding.smartRefreshlayout.finishLoadMore(2000);
            }
        });

    }

    public void setRightText() {

       setRightText(getResources().getString(R.string.deleate));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
//                activityState = (boolean) SharedPreferencesUtils.getParam(this, "ActivityState", false);
//                if (activityState) {
//                    setResult(1000);
//                    this.finish2();
//                } else {
//                    this.setResult(-1);
//                    this.finish2();
//                }
                break;
            case R.id.tv_right:
                articleDetailVm.deleteArticle();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        activityState = (boolean) SharedPreferencesUtils.getParam(this, "ActivityState", false);
//
//        if (activityState) {
//            setResult(1000);
//            this.finish2();
//        } else {
//            this.setResult(-1);
//        }
//        this.finish2();
    }
}
