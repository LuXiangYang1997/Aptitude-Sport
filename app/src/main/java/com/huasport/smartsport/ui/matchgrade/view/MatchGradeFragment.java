package com.huasport.smartsport.ui.matchgrade.view;

import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.BR;
import com.huasport.smartsport.base.BaseFragment;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.databinding.MatchGradeLayoutBinding;
import com.huasport.smartsport.ui.matchgrade.vm.MatchGradeVm;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;


public class MatchGradeFragment extends BaseFragment<MatchGradeLayoutBinding, MatchGradeVm> {


    private MyApplication myApplication = MyApplication.getInstance();
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