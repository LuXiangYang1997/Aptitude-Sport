package com.huasport.smartsport.ui.discover.view;

import android.view.View;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.ArticleLayoutBinding;
import com.huasport.smartsport.ui.discover.vm.ArticleVm;

public class ArticleActivity extends BaseActivity<ArticleLayoutBinding, ArticleVm> implements View.OnClickListener {


    private ArticleVm articleVm;

    @Override
    public int initContentView() {
        return R.layout.article_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public ArticleVm initViewModel() {

        articleVm = new ArticleVm(this);

        return articleVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        toolbarBinding.tvRight.setText(getResources().getString(R.string.release));
        toolbarBinding.tvLeft.setText(getResources().getString(R.string.cancel));
        toolbarBinding.tvLeft.setCompoundDrawables(null, null, null, null);
        toolbarBinding.llLeft.setOnClickListener(this);
        toolbarBinding.tvRight.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        articleVm.back();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                articleVm.back();
                break;
            case R.id.tv_right:
                articleVm.releaseArticle("release");
                break;
        }
    }

}
