package com.huasport.smartsport.ui.pcenter.attention.view;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseActivity;
import com.huasport.smartsport.databinding.AddfriendsLayoutBinding;
import com.huasport.smartsport.ui.pcenter.attention.adapter.AddressBookFriendsAdapter;
import com.huasport.smartsport.ui.pcenter.attention.adapter.PhoneABookFriendsAdapter;
import com.huasport.smartsport.ui.pcenter.attention.vm.AddFriendsVm;


public class AddFriendsActivity extends BaseActivity<AddfriendsLayoutBinding, AddFriendsVm> implements View.OnClickListener {

    private AddFriendsVm addFriendsVm;
    private TextView aBookName;
    private TextView wechatName;
    private PhoneABookFriendsAdapter phoneABookFriendsAdapter;
    private AddressBookFriendsAdapter addressBookFriendsAdapter;

    @Override
    public int initContentView() {
        return R.layout.addfriends_layout;
    }

    @Override
    public int initVariableId() {
        return BR.addfriendsVm;
    }

    @Override
    public AddFriendsVm initViewModel() {


        phoneABookFriendsAdapter = new PhoneABookFriendsAdapter(this);

        addFriendsVm = new AddFriendsVm(this, phoneABookFriendsAdapter);

        return addFriendsVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTitle("添加好友");
        toolbarBinding.llLeft.setOnClickListener(this);
        binding.addfriendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.addfriendsRecyclerView.setAdapter(phoneABookFriendsAdapter);
        binding.addfriendsRecyclerView.setPullRefreshEnabled(false);
        binding.addfriendsRecyclerView.setLoadingMoreEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(0);
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                setResult(0);
                finish();
                break;
        }
    }
}
