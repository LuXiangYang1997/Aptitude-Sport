package com.huasport.smartsport;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.databinding.ActivityMainBinding;
import com.huasport.smartsport.rxbus.RxBus;
import com.huasport.smartsport.ui.discover.view.DiscoverFragment;
import com.huasport.smartsport.ui.matchapply.view.MatchApplyFragment;
import com.huasport.smartsport.ui.matchgrade.view.MatchGradeFragment;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.view.PCenterFragment;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding homeBinding;
    private int type = 0;
    private ViewPagerAdapter viewPagerAdapter;
    private long exitTime = 0;
    private boolean loginState;
    private Subscription subscribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
        initData();
    }


    /**
     * 初始化
     */
    private void init() {

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new MatchApplyFragment());
        fragmentList.add(new MatchGradeFragment());
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new PCenterFragment());

        viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager(), fragmentList);

        homeBinding.vpHome.setAdapter(viewPagerAdapter);

    }

    /**
     * 初始化数据
     */
    private void initData() {


    }

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_match_apply:
                type = StatusVariable.MATCHAPPLY;
                break;
            case R.id.ll_match_score:
                type = StatusVariable.MATCHSCORE;
                viewPagerAdapter.getItem(1).onResume();
                break;
            case R.id.ll_discover:
                type = StatusVariable.DISCOVER;
                break;
            case R.id.ll_personalcenter:
                //判断是否登录，未登录则到登录界面，否则到个人中心
                if (!loginState){
                    MyApplication.getInstance().setClickState(true);
                    IntentUtil.startActivityForResult(this,LoginActivity.class);
                    return;
                }
                type = StatusVariable.PERSONALCENTE;
                break;

        }
        tabState(type);
    }

    /**
     * 底部tab状态
     *
     * @param type
     */
    public void tabState(int type) {

        homeBinding.imgMatchApply.setImageResource(R.mipmap.icon_match_apply_unselect);
        homeBinding.tvMatchApply.setTextColor(getResources().getColor(R.color.color_333333));
        homeBinding.imgMatchGrade.setImageResource(R.mipmap.icon_match_grade_unselect);
        homeBinding.tvMatchGrade.setTextColor(getResources().getColor(R.color.color_333333));
        homeBinding.imgDiscover.setImageResource(R.mipmap.icon_discover_unselect);
        homeBinding.tvDiscover.setTextColor(getResources().getColor(R.color.color_333333));
        homeBinding.imgPcenter.setImageResource(R.mipmap.icon_pcenter_unselect);
        homeBinding.tvPcenter.setTextColor(getResources().getColor(R.color.color_333333));

        switch (type) {
            case StatusVariable.MATCHAPPLY:
                homeBinding.imgMatchApply.setImageResource(R.mipmap.icon_match_apply_select);
                homeBinding.tvMatchApply.setTextColor(getResources().getColor(R.color.color_FF8F00));
                break;
            case StatusVariable.MATCHSCORE:
                homeBinding.imgMatchGrade.setImageResource(R.mipmap.icon_match_grade_select);
                homeBinding.tvMatchGrade.setTextColor(getResources().getColor(R.color.color_FF8F00));
                break;
            case StatusVariable.DISCOVER:
                homeBinding.imgDiscover.setImageResource(R.mipmap.icon_discover_select);
                homeBinding.tvDiscover.setTextColor(getResources().getColor(R.color.color_FF8F00));
                break;
            case StatusVariable.PERSONALCENTE:
                homeBinding.imgPcenter.setImageResource(R.mipmap.icon_pcenter_select);
                homeBinding.tvPcenter.setTextColor(getResources().getColor(R.color.color_FF8F00));
                break;
        }
        homeBinding.vpHome.setCurrentItem(type);
    }

    /**
     * 点击两次退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //根据返回的Code来切换tab
        if (!EmptyUtil.isEmpty(resultCode)){
            if (resultCode==StatusVariable.MATCHAPPLYCODE){
                tabState(StatusVariable.MATCHAPPLY);
            }else if (resultCode==StatusVariable.MATCHAPPLYCODE){
                tabState(StatusVariable.MATCHAPPLY);
            }else if (resultCode==StatusVariable.MATCHGRADECODE){
                tabState(StatusVariable.MATCHSCORE);
            }else if (resultCode==StatusVariable.DISCOVERCODE){
                tabState(StatusVariable.DISCOVER);
            }else if (resultCode==StatusVariable.PCENTERCODE){
                tabState(StatusVariable.PERSONALCENTE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginState = MyApplication.getInstance().getLoginState();


    }


}



