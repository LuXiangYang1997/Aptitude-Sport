package com.huasport.smartsport.ui.pcenter.view;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseFragment;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.databinding.PcenterLayoutBinding;
import com.huasport.smartsport.ui.pcenter.vm.PcenterVm;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;

/**
 * 个人中心
 */
public class PCenterFragment extends BaseFragment<PcenterLayoutBinding, PcenterVm> {


    private MyApplication myApplication = MyApplication.getInstance();
    private PcenterVm pcenterVm;

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

        pcenterVm = new PcenterVm(this,binding);

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