package com.huasport.smartsport.ui.discover.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.DynamicImglayoutBinding;
import com.huasport.smartsport.ui.discover.bean.ReleaseBean;
import com.huasport.smartsport.ui.discover.view.DynamicActivity;
import com.huasport.smartsport.util.EmptyUtil;
import java.util.Collections;


public class ReleaseMsgAdapter extends BaseAdapter<ReleaseBean, BaseViewHolder> implements com.huasport.smartsport.ui.discover.adapter.ItemTouchHelper {

    private DynamicActivity dynamicActivity;
    private boolean dragstate = false;

    public ReleaseMsgAdapter(DynamicActivity dynamicActivity) {
        super(dynamicActivity);
        this.dynamicActivity = dynamicActivity;

    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        DynamicImglayoutBinding dynamicImglayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(dynamicActivity), R.layout.dynamic_imglayout, parent, false);

        return new BaseViewHolder(dynamicImglayoutBinding);
    }

    @Override
    public void onBindVH(final BaseViewHolder baseViewHolder, final int position) {
        final DynamicImglayoutBinding binding = (DynamicImglayoutBinding) baseViewHolder.getBinding();


        binding.imgDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgClick.imgClick(StatusVariable.NORMAL, mList.get(position), position);
            }
        });
        //删除图片
        binding.ivDelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgClick.imgClick(StatusVariable.DELIMG, mList.get(position), position);
            }
        });
        if (EmptyUtil.isEmpty(mList.get(position).getTypes())) {
            binding.ivDelImg.setVisibility(View.VISIBLE);
        } else {
            binding.ivDelImg.setVisibility(View.GONE);
        }

        if (EmptyUtil.isEmpty(mList.get(position).getTypes())) {

            Glide.with(dynamicActivity).load(mList.get(position).getPath()).into(binding.imgDynamic);
        } else {

            binding.imgDynamic.setImageResource(R.mipmap.icon_dynamicimgadd);
        }
        binding.imgDynamic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (EmptyUtil.isEmpty(mList.get(position).getTypes())) {
                    dragstate = true;
                } else {
                    dragstate = false;
                }
                return false;
            }
        });
    }

    //移动
    @Override
    public void onItemMove(int fromPos, int toPos) {
        //排序
        if (fromPos < toPos) {
            for (int i = fromPos; i < toPos; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else {
            for (int i = fromPos; i > toPos; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        notifyItemMoved(fromPos, toPos);
    }

    public interface ImgClick {

        void imgClick(int types, ReleaseBean releaseBean, int position);

    }

    ImgClick imgClick;

    public void setImgClick(ImgClick imgClick) {
        this.imgClick = imgClick;
    }

    public interface ItemLongClick {

        void longClick(ReleaseBean releaseBean, int position);

    }

    ItemLongClick itemLongClick;

    public void setItemLongClick(ItemLongClick itemLongClick) {
        this.itemLongClick = itemLongClick;
    }

    public boolean dragState() {
        return dragstate;
    }

}
