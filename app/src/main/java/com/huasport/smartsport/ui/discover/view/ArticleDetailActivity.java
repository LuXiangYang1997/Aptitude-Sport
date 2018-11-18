package com.huasport.smartsport.ui.discover.view;

import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ArticledetailLayoutBinding;
import com.huasport.smartsport.ui.discover.adapter.ArticleDetailAdapter;
import com.huasport.smartsport.ui.discover.vm.ArticleDetailVm;

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

    }

    /**
     * 设置删除文章按钮
     */
    public void setRightText() {

       setRightText(getResources().getString(R.string.deleate));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_right:
                articleDetailVm.deleteArticle();
                break;
        }
    }
}
