package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MatchOtherLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.MatchOtherBean;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.URLImageGetter;


/**
 * Created by 陆向阳 on 2018/6/19.
 */

public class MatchOtherAdapter extends BaseAdapter<MatchOtherBean, BaseViewHolder> {

    private MatchIntroduceActivity matchIntroduceActivity;

    public MatchOtherAdapter(MatchIntroduceActivity matchIntroduceActivity) {
        super(matchIntroduceActivity);
        this.matchIntroduceActivity = matchIntroduceActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        MatchOtherLayoutBinding matchOtherLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(matchIntroduceActivity), R.layout.match_other_layout, parent, false);

        return new BaseViewHolder(matchOtherLayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        MatchOtherLayoutBinding matchOtherLayoutBinding = (MatchOtherLayoutBinding) baseViewHolder.getBinding();

        LogUtil.e(mList.get(position).getContent().toString());

        if(!EmptyUtil.isEmpty(mList.get(position).getName())){
            matchOtherLayoutBinding.tvMatchName.setText(mList.get(position).getName());
        }

        if (!EmptyUtil.isEmpty(mList.get(position).getContent())) {
            if (!EmptyUtil.isEmpty(mList.get(position).getContent().toString())) {
                matchOtherLayoutBinding.htmlLayout.setVisibility(View.VISIBLE);//显示标题
                matchOtherLayoutBinding.matchEquipwebview.setMovementMethod(LinkMovementMethod.getInstance());
                URLImageGetter ReviewImgGetter = new URLImageGetter(matchIntroduceActivity, matchOtherLayoutBinding.matchEquipwebview);//实例化URLImageGetter类
                matchOtherLayoutBinding.matchEquipwebview.setText(Html.fromHtml(mList.get(position).getContent().toString(), ReviewImgGetter, null));//加载Html

            } else {
                matchOtherLayoutBinding.htmlLayout.setVisibility(View.GONE);
            }
        }
    }

}

