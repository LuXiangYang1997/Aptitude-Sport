package com.example.bqj.aptitude_sport.ui.matchgrade.view;


import android.util.Log;

import com.example.bqj.aptitude_sport.BR;
import com.example.bqj.aptitude_sport.MyApplication;
import com.example.bqj.aptitude_sport.R;

import com.example.bqj.aptitude_sport.base.BaseFragment;
import com.example.bqj.aptitude_sport.bean.UserBean;
import com.example.bqj.aptitude_sport.databinding.MatchGradeLayoutBinding;
import com.example.bqj.aptitude_sport.ui.matchgrade.vm.MatchGradeVm;
import com.example.bqj.aptitude_sport.util.EmptyUtil;
import com.example.bqj.aptitude_sport.util.GlideUtil;

public class MatchGradeFragment extends BaseFragment<MatchGradeLayoutBinding, MatchGradeVm> {


    private MyApplication  myApplication = MyApplication.getInstance();
    private MatchGradeVm matchGradeVm;

    @Override
    public int initContentView() {
        return R.layout.match_grade_layout;
    }

    @Override
    public int initVariableId() {
        return BR.matchGradeVm;
    }

    @Override
    public MatchGradeVm initViewModel() {

        matchGradeVm = new MatchGradeVm(this);

        return matchGradeVm;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        myApplication = MyApplication.getInstance();

    }
    /**
     * 更新用户信息
     */
    public void initUserInfo(){
        boolean loginState = MyApplication.getInstance().getLoginState();
        if (loginState){
            UserBean userBean = myApplication.getUserBean();
            if (!EmptyUtil.isEmpty(userBean)){
                String headimgUrl = userBean.getHeadimgUrl();
                //用户头像
                if (!EmptyUtil.isEmpty(headimgUrl)){
                    GlideUtil.LoadCircleImage(this.getActivity(),headimgUrl,binding.imgMatchGradeHeader);
                }else{
                    binding.imgMatchGradeHeader.setImageResource(R.mipmap.icon_defaultheader_yes);
                }
            }
        }else{
            binding.imgMatchGradeHeader.setImageResource(R.mipmap.icon_defaultheader_no);
        }

    }

}