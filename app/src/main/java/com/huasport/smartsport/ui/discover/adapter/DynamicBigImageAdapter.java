package com.huasport.smartsport.ui.discover.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BasePageAdapter;
import com.huasport.smartsport.databinding.BigimgItemlayoutBinding;
import com.huasport.smartsport.ui.discover.bean.PicBean;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.ScreenUtil;

public class DynamicBigImageAdapter extends BasePageAdapter<PicBean> {

    private Context context;

    public DynamicBigImageAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View newView(ViewGroup parent, int position) {

        BigimgItemlayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bigimg_itemlayout, parent, false);
        int mPos = position % mData.size();
        ImageView image = getImage(mData.get(mPos).getWidth(), mData.get(position).getHeight(), binding.imgBig);
        //这设置图片为背景，处理白边
        GlideUtil.LoadImage(context, mData.get(mPos).getUrl(), image);
        return binding.getRoot();
    }

    public ImageView getImage(int width, int height, ImageView imageView) {

        if (width < ScreenUtil.getScreenWidth(context) && height < ScreenUtil.getScreenHeight(context)) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.height = height * 2;
            layoutParams.width = width * 2;
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            imageView.setLayoutParams(layoutParams);
        }
        return imageView;

    }

}
