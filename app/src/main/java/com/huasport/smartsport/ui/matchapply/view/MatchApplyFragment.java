package com.huasport.smartsport.ui.matchapply.view;


import android.widget.ImageView;

import com.huasport.smartsport.BR;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseFragment;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.databinding.MatchApplyLayoutBinding;
import com.huasport.smartsport.ui.matchapply.vm.MatchApplyVm;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;

public class MatchApplyFragment extends BaseFragment<MatchApplyLayoutBinding, MatchApplyVm> {


    private MyApplication myApplication = MyApplication.getInstance();
    private MatchApplyVm matchApplyVm;

    @Override
    public int initContentView() {
        return R.layout.match_apply_layout;
    }

    @Override
    public int initVariableId() {
        return BR.matchApplyVm;
    }

    @Override
    public MatchApplyVm initViewModel() {

        matchApplyVm = new MatchApplyVm(this,binding);

        return matchApplyVm;
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

        boolean loginState = MyApplication.getInstance().getLoginState();
        if (loginState){

            UserBean userBean = myApplication.getUserBean();
            ImageView imgMatchApplyHeader = binding.imgMatchApplyHeader;

            if (!EmptyUtil.isEmpty(userBean)) {
                String headimgUrl = userBean.getHeadimgUrl();
                //用户头像
                if (!EmptyUtil.isEmpty(imgMatchApplyHeader)) {
                    if (!EmptyUtil.isEmpty(headimgUrl)) {
                        GlideUtil.LoadCircleImage(this.getActivity(), headimgUrl, binding.imgMatchApplyHeader);
                    } else {
                        binding.imgMatchApplyHeader.setImageResource(R.mipmap.icon_defaultheader_yes);
                    }
                }
            }

        }else{
            binding.imgMatchApplyHeader.setImageResource(R.mipmap.icon_defaultheader_no);
        }

    }


}