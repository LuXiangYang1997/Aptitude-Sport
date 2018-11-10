package com.example.bqj.aptitude_sport.ui.matchapply.view;


import android.util.Log;
import android.widget.ImageView;

import com.example.bqj.aptitude_sport.BR;
import com.example.bqj.aptitude_sport.MyApplication;
import com.example.bqj.aptitude_sport.R;
import com.example.bqj.aptitude_sport.base.BaseFragment;
import com.example.bqj.aptitude_sport.bean.UserBean;
import com.example.bqj.aptitude_sport.databinding.MatchApplyLayoutBinding;
import com.example.bqj.aptitude_sport.ui.matchapply.vm.MatchApplyVm;
import com.example.bqj.aptitude_sport.util.EmptyUtil;
import com.example.bqj.aptitude_sport.util.GlideUtil;

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

        matchApplyVm = new MatchApplyVm(this);

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