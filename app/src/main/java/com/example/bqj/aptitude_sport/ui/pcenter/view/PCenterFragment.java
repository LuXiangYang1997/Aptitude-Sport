package com.example.bqj.aptitude_sport.ui.pcenter.view;


import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.bqj.aptitude_sport.BR;
import com.example.bqj.aptitude_sport.MyApplication;
import com.example.bqj.aptitude_sport.R;
import com.example.bqj.aptitude_sport.base.BaseFragment;
import com.example.bqj.aptitude_sport.bean.UserBean;
import com.example.bqj.aptitude_sport.databinding.PcenterLayoutBinding;
import com.example.bqj.aptitude_sport.ui.pcenter.vm.PcenterVm;
import com.example.bqj.aptitude_sport.util.EmptyUtil;
import com.example.bqj.aptitude_sport.util.GlideUtil;

/**
 * 个人中心
 */
public class PCenterFragment extends BaseFragment<PcenterLayoutBinding, PcenterVm> {


    private MyApplication myApplication = MyApplication.getInstance();

    @Override
    public int initContentView() {
        return R.layout.pcenter_layout;
    }

    @Override
    public int initVariableId() {
        return BR.pcenterVm;
    }

    @Override
    public PcenterVm initViewModel() {

        PcenterVm pcenterVm = new PcenterVm(this);

        return pcenterVm;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

    }

    /**
     * 更新用户信息
     */
    public void initUserInfo() {

        UserBean userBean = myApplication.getUserBean();

        if (!EmptyUtil.isEmpty(userBean)) {
            String headimgUrl = userBean.getHeadimgUrl();
            String nickName = userBean.getNickName();
            String account = userBean.getAccount();
            //用户头像
            if (!EmptyUtil.isEmpty(headimgUrl)) {
                GlideUtil.LoadCircleImage(this.getActivity(), headimgUrl, binding.imgHeader);
            } else {
                binding.imgHeader.setImageResource(R.mipmap.icon_defaultheader_yes);
            }
            //用户nickName
            if (!EmptyUtil.isEmpty(nickName)) {
                binding.tvUserName.setText(nickName);
            } else {
                if (!EmptyUtil.isEmpty(account)) {
                    binding.tvUserName.setText(account);
                } else {
                    binding.tvUserName.setText("");
                }
            }

        }
    }

}