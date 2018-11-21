package com.huasport.smartsport.ui.pcenter.medal.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.SwipeLayout;
import com.huasport.smartsport.databinding.AddressItemLayoutBinding;
import com.huasport.smartsport.ui.pcenter.medal.bean.AddressBean;
import com.huasport.smartsport.ui.pcenter.medal.view.AddAddressActivity;
import com.huasport.smartsport.ui.pcenter.medal.view.AddNewAddressActivity;
import com.huasport.smartsport.util.EmptyUtil;

import java.io.Serializable;

public class AddressAdapter extends BaseAdapter<AddressBean.ResultBean.ListBean, BaseViewHolder> {

    private AddAddressActivity addAddressActivity;
    public ObservableField<Boolean> item = new ObservableField<>(false);

    public AddressAdapter(AddAddressActivity addAddressActivity) {
        super(addAddressActivity);
        this.addAddressActivity = addAddressActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        AddressItemLayoutBinding addressItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(addAddressActivity), R.layout.address_item_layout, parent, false);
        return new BaseViewHolder(addressItemLayoutBinding);
    }

    @Override
    public void onBindVH(final BaseViewHolder baseViewHolder, final int position) {
        final AddressItemLayoutBinding addressItemLayoutBinding = (AddressItemLayoutBinding) baseViewHolder.getBinding();
        if (!EmptyUtil.isEmpty(mList.get(position))) {
            // if (mList.get(position) != null) {
            if (!EmptyUtil.isEmpty(mList.get(position).getRealname())) {
                // if (mList.get(position).getRealname() != null && !mList.get(position).getRealname().equals("null") && !mList.get(position).getRealname().equals("")) {
                addressItemLayoutBinding.tvRealname.setText(mList.get(position).getRealname());
            }
            if (!EmptyUtil.isEmpty(mList.get(position).getMobile())) {
                //  if (mList.get(position).getMobile() != null && !mList.get(position).getMobile().equals("null") && !mList.get(position).getMobile().equals("")) {
                addressItemLayoutBinding.tvMobilephone.setText(mList.get(position).getMobile());
            }
            if (mList.get(position).isIsdefault()) {
                addressItemLayoutBinding.tvDefault.setVisibility(View.VISIBLE);
                addressItemLayoutBinding.tvDefault.setText("[默认地址]");
            } else {
                addressItemLayoutBinding.tvDefault.setVisibility(View.GONE);
            }

            addressItemLayoutBinding.tvAddress.setText(mList.get(position).getProvince() + "-" + mList.get(position).getCity() + "-" + mList.get(position).getArea() + "-" + mList.get(position).getAddress());

            /**
             * 打开时调用：循环调用onStartOpen,onUpdate,onHandRelease,onUpdate,onOpen,
             * 关闭时调用：onStartClose,onUpdate,onHandRelease,onHandRelease,onUpdate,onClose
             */

            addressItemLayoutBinding.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    baseViewHolder.itemView.setClickable(false);
                }

                @Override
                public void onStartClose(SwipeLayout layout) {
                    baseViewHolder.itemView.setClickable(false);
                }

                @Override
                public void onClose(SwipeLayout layout) {

                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                }
            });
            addressItemLayoutBinding.swipeLayout.setClickToClose(true);

            addressItemLayoutBinding.swipeModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addressItemLayoutBinding.swipeLayout.close();
                    Intent intent = new Intent(addAddressActivity, AddNewAddressActivity.class);
                    intent.putExtra("addressType", "modifyAddress");//修改地址
                    addAddressActivity.addressCode.set(mList.get(position).getAddressCode());
                    intent.putExtra("AddressBean", (Serializable) mList.get(position));
                    addAddressActivity.startActivity(intent);
                }
            });


            addressItemLayoutBinding.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = addAddressActivity.getIntent();
                    intent.putExtra("addressBean", mList.get(position));
                    addAddressActivity.setResult(StatusVariable.ADDRESSCODE, intent);
                    addAddressActivity.finish();

                }
            });

        }

    }


}
