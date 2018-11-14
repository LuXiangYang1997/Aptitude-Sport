package com.huasport.smartsport.ui.matchgrade.view;

import android.support.v7.widget.LinearLayoutManager;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.BR;
import com.huasport.smartsport.base.BaseFragment;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.databinding.MatchGradeLayoutBinding;
import com.huasport.smartsport.ui.matchgrade.adapter.MatchGradeListAdapter;
import com.huasport.smartsport.ui.matchgrade.adapter.MatchGradeTabAdapter;
import com.huasport.smartsport.ui.matchgrade.vm.MatchGradeVm;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;

/**
 * 比赛成绩
 */
public class MatchGradeFragment extends BaseFragment<MatchGradeLayoutBinding, MatchGradeVm> {


    private MyApplication myApplication = MyApplication.getInstance();
    private MatchGradeVm matchGradeVm;
    private MatchGradeTabAdapter matchGradeTabAdapter;
    private MatchGradeListAdapter matchGradeListAdapter;

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

        matchGradeTabAdapter = new MatchGradeTabAdapter(this.getActivity());

        matchGradeListAdapter = new MatchGradeListAdapter(this.getActivity());

        matchGradeVm = new MatchGradeVm(this,binding,matchGradeTabAdapter,matchGradeListAdapter);

        return matchGradeVm;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        myApplication = MyApplication.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局

        binding.recyclviewTab.setLayoutManager(linearLayoutManager);
        binding.recyclviewTab.setAdapter(matchGradeTabAdapter);

        binding.recyclerViewMatclist.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        binding.recyclerViewMatclist.setAdapter(matchGradeListAdapter);

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