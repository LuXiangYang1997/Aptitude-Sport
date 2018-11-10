package com.example.bqj.aptitude_sport;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.bqj.aptitude_sport.constant.StatusVariable;
import com.example.bqj.aptitude_sport.databinding.ActivityMainBinding;
import com.example.bqj.aptitude_sport.ui.discover.view.DiscoverFragment;
import com.example.bqj.aptitude_sport.ui.matchapply.view.MatchApplyFragment;
import com.example.bqj.aptitude_sport.ui.matchgrade.view.MatchGradeFragment;
import com.example.bqj.aptitude_sport.ui.pcenter.view.PCenterFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding homeBinding;
    private int type = 0;
    private ViewPagerAdapter viewPagerAdapter;
    private long exitTime = 0;

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

//        HashMap params=new HashMap();
//        params.put("baseMethod", Method.HOMEPAGELIST);
//        params.put("baseUrl", Config.baseUrl);
//
//        OkHttpUtil.getRequest(this, params, new RequestCallBack<MatchListBean>() {
//
//            @Override
//            public MatchListBean parseNetworkResponse(String jsonResult) {
//
//                MatchListBean matchListBean = JSON.parseObject(jsonResult, MatchListBean.class);
//
//                return matchListBean;
//            }
//
//            @Override
//            public void onSuccess(Response<MatchListBean> response) {
//
//                MatchListBean matchListBean = response.body();
//                ToastUtil.centerToast(MainActivity.this,matchListBean.getResult().getLogos().get(0).getName());
//
//            }
//
//            @Override
//            public void onFailed(int code, String msg) {
//
//            }
//        });


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
    private void tabState(int type) {

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
    protected void onResume() {
        super.onResume();
    }


}



