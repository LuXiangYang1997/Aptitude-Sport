package com.huasport.smartsport.ui.matchgrade.view;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.SelectMatchprogramLayoutBinding;
import com.huasport.smartsport.ui.matchgrade.adapter.SelectMatchProgramAdapter;
import com.huasport.smartsport.ui.matchgrade.bean.MatchGradeTabBean;

import java.io.Serializable;
import java.util.List;

/**
 * 选择项目
 */
public class SelectMatchProgramActivity extends BaseActivity<SelectMatchprogramLayoutBinding,BaseViewModel>{

    @Override
    public int initContentView() {
        return R.layout.select_matchprogram_layout;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public BaseViewModel initViewModel() {

        return new BaseViewModel();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        List<MatchGradeTabBean.ResultBean.TypesBean>  matchList = (List<MatchGradeTabBean.ResultBean.TypesBean>) getIntent().getSerializableExtra("typesList");

        setTitle(getResources().getString(R.string.select_program));

        //返回事件
        toolbarBinding.llLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(StatusVariable.MATCHGRADECODE);
                finish();
            }
        });

        final SelectMatchProgramAdapter adapter = new SelectMatchProgramAdapter(this);
        binding.recyclerViewProgram.setLayoutManager(new GridLayoutManager(this,3));
        binding.recyclerViewProgram.setAdapter(adapter);
        adapter.loadData(matchList);

        //将点击的条目的状态改为选中状态并传回上一个界面
        adapter.setItemClick(new SelectMatchProgramAdapter.ItemClick() {
            @Override
            public void itemClick(int position) {

                for (int i = 0; i < adapter.mList.size(); i++) {
                    adapter.mList.get(i).setCheck(false);
                }
                adapter.mList.get(position).setCheck(true);
                Intent intent = getIntent();
                intent.putExtra("typesList", (Serializable) adapter.mList);
                setResult(StatusVariable.MATCHGRADECODE, intent);
                finish();
            }
        });

    }
}
