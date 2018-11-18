package com.huasport.smartsport.ui.discover.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.DynamicimgItemlayotBinding;
import com.huasport.smartsport.ui.discover.bean.DynamicDetailBean;
import com.huasport.smartsport.ui.discover.view.DynamicDetailActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.ScreenUtil;

public class DynamicImageAdapter extends BaseAdapter<DynamicDetailBean.ResultBean.DataBean.PicsBean, BaseViewHolder> {

    private DynamicDetailActivity dynamicDetailActivity;
    private DynamicimgItemlayotBinding binding;

    public DynamicImageAdapter(DynamicDetailActivity dynamicDetailActivity) {
        super(dynamicDetailActivity);
        this.dynamicDetailActivity = dynamicDetailActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        DynamicimgItemlayotBinding dynamicimgItemlayotBinding = DataBindingUtil.inflate(LayoutInflater.from(dynamicDetailActivity), R.layout.dynamicimg_itemlayot, parent, false);


        return new BaseViewHolder(dynamicimgItemlayotBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {

        binding = (DynamicimgItemlayotBinding) baseViewHolder.getBinding();
        if (mList.size() == 1) {
            ImageView image = getImage(mList.get(position).getWidth(), mList.get(position).getHeight(), binding.dynamicimg, true);
            GlideUtil.LoadImage(dynamicDetailActivity, mList.get(position).getUrl(), image);
        }
        if (!EmptyUtil.isEmpty(mList.get(position).getUrl())) {
            GlideUtil.LoadImage(dynamicDetailActivity, mList.get(position).getUrl(), binding.dynamicimg);
        }

        binding.dynamicimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lookImgClick.lookimgclick(mList.get(position), position);

            }
        });


    }

    //对Imageview进行处理
    public ImageView getImage(int width, int height, ImageView imageView, boolean dispose) {

        if (dispose) {
            if (width > height) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 1;
                layoutParams.width = ScreenUtil.getScreenWidth(dynamicDetailActivity);
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                imageView.setLayoutParams(layoutParams);
            } else if (height > width) {
                if (width > ScreenUtil.getScreenWidth(dynamicDetailActivity)) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.weight = 1;
                    layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    imageView.setLayoutParams(layoutParams);
                } else {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.width = ScreenUtil.getScreenWidth(dynamicDetailActivity) / 2;
                    layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    imageView.setLayoutParams(layoutParams);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.width = ScreenUtil.getScreenWidth(dynamicDetailActivity) / 2;
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                imageView.setLayoutParams(layoutParams);
            } else if (width == height) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 1;
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                imageView.setLayoutParams(layoutParams);
            }
            return imageView;
        } else {

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            layoutParams.height = binding.dynamicimg.getHeight();
            imageView.setLayoutParams(layoutParams);
        }
        return imageView;
    }


    public interface LookImgClick {

        void lookimgclick(DynamicDetailBean.ResultBean.DataBean.PicsBean picsBean, int pos);

    }

    LookImgClick lookImgClick;

    public void setLookImgClick(LookImgClick lookImgClick) {
        this.lookImgClick = lookImgClick;
    }
}
