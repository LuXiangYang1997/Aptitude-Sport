package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.LogoLayoutBinding;
import com.huasport.smartsport.ui.matchapply.bean.MatchApplyListBean;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.IntentUtil;

public class LogoAdapter extends BaseAdapter<MatchApplyListBean.ResultBean.LogosBean,BaseViewHolder> {

    private FragmentActivity activity;

    public LogoAdapter(FragmentActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

       LogoLayoutBinding logoLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.logo_layout,parent,false);

       return new BaseViewHolder(logoLayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        LogoLayoutBinding binding = (LogoLayoutBinding) baseViewHolder.getBinding();
        String logo = mList.get(position).getLogo();
        if (!EmptyUtil.isEmpty(logo)){
            GlideUtil.LoadImage(activity,logo,binding.imgLogo);
        }
        //跳转浏览器
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startBrowser(activity,mList.get(position).getUrl());
            }
        });



    }
}
