package com.huasport.smartsport.ui.discover.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.RecommendItemlayoutBinding;
import com.huasport.smartsport.ui.discover.bean.RecommandBean;
import com.huasport.smartsport.ui.discover.view.ArticleDetailActivity;
import com.huasport.smartsport.ui.discover.view.DynamicDetailActivity;
import com.huasport.smartsport.util.EmptyUtil;


public class RecommandAdapter extends BaseAdapter<RecommandBean.ResultBean.DataBean, BaseViewHolder> {

    private FragmentActivity activity;
    private Intent intent;


    public RecommandAdapter(FragmentActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        RecommendItemlayoutBinding recommendItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.recommend_itemlayout, parent, false);
        return new BaseViewHolder(recommendItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {

        RecommendItemlayoutBinding binding = (RecommendItemlayoutBinding) baseViewHolder.getBinding();
        if (mList.get(position).getType().equals("article")) {
            String title = mList.get(position).getTitle();

            binding.imgRecommand.setImageResource(R.mipmap.icon_articlerecommand);
            if (!EmptyUtil.isEmpty(mList.get(position).getTitle())) {
                if (title.contains("\n")) {
                    String substring = title.substring(0, title.indexOf("\n"));
                    binding.tvRecommandName.setText(substring);
                } else {
                    binding.tvRecommandName.setText(title);
                }
            }
        } else if (mList.get(position).getType().equals("dynamic")) {
            binding.imgRecommand.setImageResource(R.mipmap.icon_dynamicrecommand);
            String content = mList.get(position).getContent();
            if (!EmptyUtil.isEmpty(content)) {
                if (content.contains("\n")) {
                    String substring = content.substring(0, content.indexOf("\n"));
                    binding.tvRecommandName.setText(substring);
                } else {
                    binding.tvRecommandName.setText(content);
                }
            } else {
                binding.tvRecommandName.setText("查看图片");
            }

        }

        binding.tvRecommandName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EmptyUtil.isEmpty(mList.get(position).getType())) {
                    if (mList.get(position).getType().equals("article")) {
                        intent = new Intent(activity, ArticleDetailActivity.class);
                        intent.putExtra("dynamicId", mList.get(position).getId());
                    } else {
                        intent = new Intent(activity, DynamicDetailActivity.class);
                        intent.putExtra("dynamicId", mList.get(position).getId());
                    }
                    activity.startActivity(intent);
                }
            }
        });
    }
}
