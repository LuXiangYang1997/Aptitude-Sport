package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.RegistrationsuccessItemBinding;
import com.huasport.smartsport.ui.matchapply.bean.RegistrationInfoBean;
import com.huasport.smartsport.ui.matchapply.view.SuccessPaymentInfoActivity;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.Util;

/**
 * Created by 陆向阳 on 2018/6/11.
 */

public class RegitrationSuccessAdapter extends BaseAdapter<RegistrationInfoBean.ResultBean.OrderDetailBean.ApplysBean, BaseViewHolder> {

    private SuccessPaymentInfoActivity successPaymentInfoActivity;


    public RegitrationSuccessAdapter(SuccessPaymentInfoActivity successPaymentInfoActivity) {
        super(successPaymentInfoActivity);
        this.successPaymentInfoActivity = successPaymentInfoActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        RegistrationsuccessItemBinding registrationsuccessItemBinding = DataBindingUtil.inflate(LayoutInflater.from(successPaymentInfoActivity), R.layout.registrationsuccess_item, parent, false);

        return new BaseViewHolder(registrationsuccessItemBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        final RegistrationsuccessItemBinding registrationsuccessItemBinding = (RegistrationsuccessItemBinding) baseViewHolder.getBinding();
        registrationsuccessItemBinding.setVariable(BR.position, position);

        String title = mList.get(position).getTitle();
        if(!EmptyUtil.isEmpty(title)){
            registrationsuccessItemBinding.tvTitle.setText(title);
        }

        String address = mList.get(position).getAddress();
        if(!EmptyUtil.isEmpty(address)){
            registrationsuccessItemBinding.tvAddress.setText(address);
        }
        String siteName = mList.get(position).getSiteName();
        if(!EmptyUtil.isEmpty(siteName)){
            registrationsuccessItemBinding.siteName.setText(siteName);
        }

        String matchName = mList.get(position).getMatchName();
        if(!EmptyUtil.isEmpty(siteName)){
            registrationsuccessItemBinding.tvMatchName.setText(matchName);
        }

        String eventStartTime = mList.get(position).getEventStartTime();
        if (!EmptyUtil.isEmpty(eventStartTime)){
            registrationsuccessItemBinding.tvEventStartTime.setText(eventStartTime);
        }

        if (!EmptyUtil.isEmpty(mList.get(position).getMatchGroupName())) {
            if (mList.get(position).getEventName() != null) {
                registrationsuccessItemBinding.matchGroupName.setText("个人赛" + mList.get(position).getMatchGroupName() + "-" + mList.get(position).getEventName());
            } else {
                registrationsuccessItemBinding.matchGroupName.setText("个人赛" + mList.get(position).getMatchGroupName());
            }

        } else {
            if (!EmptyUtil.isEmpty(mList.get(position).getEventName())) {
                //  if (mList.get(position).getEventName() != null) {
                registrationsuccessItemBinding.matchGroupName.setText("个人赛" + mList.get(position).getEventName());
            } else {
                registrationsuccessItemBinding.matchGroupName.setText("个人赛");
            }
        }
        SpannableString spannableString = Util.matcherSearchText(successPaymentInfoActivity.getResources().getColor(R.color.color_FF8F00), registrationsuccessItemBinding.matchGroupName.getText().toString(), "个人赛");
        registrationsuccessItemBinding.matchGroupName.setText(spannableString);

        int number = position % 3;
        if (number == 1) {
            registrationsuccessItemBinding.cardApply.setImageResource(R.mipmap.img_kpb);
        } else if (number == 2) {
            registrationsuccessItemBinding.cardApply.setImageResource(R.mipmap.img_kpc);
        } else if (number == 0) {
            registrationsuccessItemBinding.cardApply.setImageResource(R.mipmap.img_kpa);
        }

        //长按事件

        ImageView rlCard = baseViewHolder.itemView.findViewById(R.id.card_apply);

        rlCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ImageView imageView = registrationsuccessItemBinding.cardApply;//获取imageView
                try {
                    registrationsuccessItemBinding.rlCard.setDrawingCacheEnabled(true);
                    registrationsuccessItemBinding.rlCard.buildDrawingCache();  //启用DrawingCache并创建位图
                    Bitmap bitmap = Bitmap.createBitmap(registrationsuccessItemBinding.rlCard.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
                    registrationsuccessItemBinding.rlCard.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
                    Util.saveImageToGallery(successPaymentInfoActivity, bitmap);
////                    //保存截图到手机
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

    }

//    //条目长按
//    public interface ItemLongClick {
//        void itemlongclick(View view);
//    }
//
//    ItemLongClick itemLongClick;
//
//    public void setItemLongClick(ItemLongClick itemLongClick) {
//        this.itemLongClick = itemLongClick;
//    }

}
