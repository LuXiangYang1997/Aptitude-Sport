package com.huasport.smartsport.ui.matchgrade.vm;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.MatchgraderankingsLayoutBinding;
import com.huasport.smartsport.ui.matchgrade.adapter.MatchGradeRankingsListAdapter;
import com.huasport.smartsport.ui.matchgrade.bean.MatchGradeScoreHeaderBean;
import com.huasport.smartsport.ui.matchgrade.bean.MatchScoreListBean;
import com.huasport.smartsport.ui.matchgrade.bean.UserRankBean;
import com.huasport.smartsport.ui.matchgrade.view.MatchGradeRankingsActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.NullStateUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMore;
import com.huasport.smartsport.util.refreshLoadmore.RefreshLoadMoreListener;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.HashMap;
import java.util.List;

public class MatchGradeRankingVM extends BaseViewModel implements RefreshLoadMoreListener, CounterListener {

    private MatchGradeRankingsActivity matchGradeRankingsActivity;
    private final MatchgraderankingsLayoutBinding binding;
    private MatchGradeRankingsListAdapter matchGradeRankingsListAdapter;
    private String comptitionCodeStr = "";
    private String comptitionDateStr = "";
    private int page = 1;
    public int position = 0;
    private int totalPage = 0;
    private String token;
    private View gradelayout;
    private ToastUtil toastUtil;
    private List<MatchGradeScoreHeaderBean.ResultBean.ListBean> list;
    private Counter counter;
    private LoadingDialog loadingDialog;


    public MatchGradeRankingVM(MatchGradeRankingsActivity matchGradeRankingsActivity, MatchGradeRankingsListAdapter matchGradeRankingsListAdapter) {
        this.matchGradeRankingsActivity = matchGradeRankingsActivity;
        this.matchGradeRankingsListAdapter = matchGradeRankingsListAdapter;
        binding = matchGradeRankingsActivity.getBinding();
        init();
        initView();
        initDataList();
        initData(StatusVariable.REFRESH);
    }

    private void init() {
        //初始化刷新
        new RefreshLoadMore(binding.smartRefreshlayout,this);
        //获取从上个界面传过来的值
        Intent intent = matchGradeRankingsActivity.getIntent();
        final String comptitionCode = intent.getStringExtra("comptitionCode");
        String comptitionDate = intent.getStringExtra("comptitionDate");
        String matchName = intent.getStringExtra("matchName");

        binding.tvMatchtitle.setText(matchName);

        comptitionCodeStr = comptitionCode;
        comptitionDateStr = comptitionDate;

        //初始化Toast
        toastUtil = new ToastUtil(matchGradeRankingsActivity);
        //初始化头View
        gradelayout = LayoutInflater.from(matchGradeRankingsActivity).inflate(R.layout.graderankings_headlayout, null);
        //初始化计数器
        counter = new Counter(this,3);

        loadingDialog = new LoadingDialog(matchGradeRankingsActivity,R.style.LoadingDialog);

        //token
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)){
            token = userBean.getToken();
            //token不为空的时候才去请求用户score信息
            if (!EmptyUtil.isEmpty(token)){
                initUserInfo();
            }else{
                counter.countDown();
            }
        }else{
            counter.countDown();
        }

        loadingDialog.show();

    }

    /**
     * 初始化用户数据
     */
    private void initUserInfo() {
        HashMap params = new HashMap();
        params.put("token", token);
        params.put("baseMethod", Method.MATCHUSERSCORE);
        params.put("competitionCode", comptitionCodeStr);
        params.put("competitionDate", comptitionDateStr);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(matchGradeRankingsActivity, params, new RequestCallBack<UserRankBean>() {
            @Override
            public void onSuccess(Response<UserRankBean> response) {
                UserRankBean userRankBean = response.body();
                if (!EmptyUtil.isEmpty(userRankBean)){
                    int resultCode = userRankBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        UserRankBean.ResultBean resultBean = userRankBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){
                            UserRankBean.ResultBean.RankBean rankBean = resultBean.getRank();
                            if (!EmptyUtil.isEmpty(rankBean)){
                                ImageView header = gradelayout.findViewById(R.id.img_myheader);
                                TextView userSocre = gradelayout.findViewById(R.id.tv_user_score);
                                TextView userRanking = gradelayout.findViewById(R.id.tv_user_rank);

                                //加载圆形头像，没有则显示本地默认头像
                                if (!EmptyUtil.isEmpty(rankBean.getPlayerHeaderImg())) {
                                    GlideUtil.LoadCircleImage(matchGradeRankingsActivity, rankBean.getPlayerHeaderImg(), header);
                                } else {
                                    header.setImageResource(R.mipmap.icon_defaultheader_yes);
                                }
                                //成绩
                                if (!EmptyUtil.isEmpty(rankBean.getScore())) {
                                    userSocre.setText(rankBean.getScore());
                                }
                                //排名
                                if (!EmptyUtil.isEmpty(rankBean.getRank())) {
                                    userRanking.setText("第" + String.valueOf(rankBean.getRank()) + "名");
                                    SpannableStringBuilder spannableStringBuilder = Util.setSpan(userRanking.getText().toString(), Color.parseColor("#FF8F00"), 1, userRanking.getText().length() - 1);
                                    userRanking.setText(spannableStringBuilder);
                                }
                                binding.recyclerViewRank.addHeaderView(gradelayout);
                            } else {
                                binding.recyclerViewRank.removeView(gradelayout);
                            }
                        }else{
                            LogUtil.e("rankBean是空的");
                        }
                    }else {
                        LogUtil.e("resultBean是空的");
                    }
                }
            }

            @Override
            public UserRankBean parseNetworkResponse(String jsonResult) {

                UserRankBean userRankBean = JSON.parseObject(jsonResult, UserRankBean.class);

                return userRankBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
            }
        });

    }

    /**
     * 初始化
     */
    private void initDataList() {

        String matchCode = matchGradeRankingsActivity.getIntent().getStringExtra("matchCode");
        HashMap param = new HashMap();
        param.put("baseMethod", Method.MATCHSCOREHEADER);
        param.put("matchCode", matchCode);
        param.put("baseUrl",Config.baseUrl);

        OkHttpUtil.getRequest(matchGradeRankingsActivity, param, new RequestCallBack<MatchGradeScoreHeaderBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<MatchGradeScoreHeaderBean> response) {
                MatchGradeScoreHeaderBean matchGradeScoreHeaderBean = response.body();
                if (!EmptyUtil.isEmpty(matchGradeScoreHeaderBean)){
                    int resultCode = matchGradeScoreHeaderBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MatchGradeScoreHeaderBean.ResultBean resultBean = matchGradeScoreHeaderBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){

                                list = matchGradeScoreHeaderBean.getResult().getList();
                                //数据做比对
                                dataCompare(list, comptitionCodeStr);
                                 //前一场，后一场处理
                                dataSite(list);

                        }
                    }
                }
            }

            @Override
            public MatchGradeScoreHeaderBean parseNetworkResponse(String jsonResult) {

                MatchGradeScoreHeaderBean matchGradeScoreHeaderBean = JSON.parseObject(jsonResult, MatchGradeScoreHeaderBean.class);

                return matchGradeScoreHeaderBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!EmptyUtil.isEmpty(msg)){

                    toastUtil.centerToast(msg);

                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
            }
        });

    }

    /**
     * 初始化列表数据
     * @param loadType
     */
    private void initData(final int loadType) {
        HashMap params = new HashMap();
        params.put("baseMethod", Method.MATCHSCORELIST);
        params.put("competitionCode", comptitionCodeStr);
        params.put("competitionDate", comptitionCodeStr);
        params.put("currentPage", page + "");
        params.put("pageSize", "10");
        params.put("baseUrl",Config.baseUrl);

        OkHttpUtil.getRequest(matchGradeRankingsActivity, params, new RequestCallBack<MatchScoreListBean>() {
            @Override
            public void onSuccess(Response<MatchScoreListBean> response) {
                MatchScoreListBean matchScoreListBean = response.body();
                if (!EmptyUtil.isEmpty(matchScoreListBean)) {
                    int resultCode = matchScoreListBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS) {
                        MatchScoreListBean.ResultBean resultBean = matchScoreListBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)) {

                            totalPage = resultBean.getTotalPage();
                            //空数据判断
                            if (totalPage == 0){
                                NullStateUtil.setNullState(binding.nulldata,true);
                            }else{
                                NullStateUtil.setNullState(binding.nulldata,false);
                            }

                            List<MatchScoreListBean.ResultBean.ListBean> list = resultBean.getList();
                            if (!EmptyUtil.isEmpty(list)) {

                                if (loadType == StatusVariable.REFRESH){
                                    matchGradeRankingsListAdapter.loadData(list);
                                    binding.smartRefreshlayout.finishRefresh();
                                }else{
                                    matchGradeRankingsListAdapter.loadMoreData(list);
                                    binding.smartRefreshlayout.finishLoadMore();
                                }

                            }else{
                                LogUtil.e("list是空的");
                            }
                        }else{
                            LogUtil.e("resultBean是空的");
                        }
                        page++;
                    }
                }

            }

            @Override
            public MatchScoreListBean parseNetworkResponse(String jsonResult) {

                MatchScoreListBean matchScoreListBean = JSON.parseObject(jsonResult, MatchScoreListBean.class);

                return matchScoreListBean;
            }

            @Override
            public void onFailed(int code, String msg) {

                if (!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                counter.countDown();
            }
        });

    }

    /**
     * 对前一场，后一场做处理
     *
     * @param list
     */
    private void dataSite(List<MatchGradeScoreHeaderBean.ResultBean.ListBean> list) {
        if (EmptyUtil.isEmpty(list)) {

            binding.tvFront.setAlpha(0.6f);
            binding.tvBehind.setAlpha(0.6f);
            binding.tvFront.setClickable(false);
            binding.tvBehind.setClickable(false);

            return;
        }
        if (list.size() == 1) {

            binding.tvFront.setAlpha(0.6f);
            binding.tvBehind.setAlpha(0.6f);
            binding.tvFront.setClickable(false);
            binding.tvBehind.setClickable(false);

        } else if (list.size() > 1) {

            if (position == 0) {

                binding.tvFront.setClickable(false);
                binding.tvFront.setAlpha(0.6f);
                binding.tvBehind.setAlpha(1.0f);
                binding.tvBehind.setClickable(true);

            } else if (position > 0 && position < list.size() - 1) {

                binding.tvBehind.setClickable(true);
                binding.tvBehind.setAlpha(1.0f);
                binding.tvFront.setAlpha(1.0f);
                binding.tvFront.setClickable(true);

            } else if (position == list.size() - 1) {

                binding.tvBehind.setClickable(false);
                binding.tvBehind.setAlpha(0.6f);
                binding.tvFront.setAlpha(1.0f);
                binding.tvFront.setClickable(true);

            }
        }
    }

    /**
     * 对小标题进行code判断，相等
     *
     * @param list
     * @param comptitionCode
     */
    private void dataCompare(List<MatchGradeScoreHeaderBean.ResultBean.ListBean> list, String comptitionCode) {
        for (int i = 0; i < list.size(); i++) {

            MatchGradeScoreHeaderBean.ResultBean.ListBean listBean = list.get(i);
            if (listBean.getCompetitionCode().equals(comptitionCode)) {

                position = i;
                binding.tvEventTitle.setText(listBean.getCompetitionName());

            }

        }
    }

    private void initView() {
        //前一场
        binding.tvFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = (position - 1);//下标-1
                String competitionCode = list.get(position).getCompetitionCode();
                String competitionDate = (String) list.get(position).getCompetitionDate();
                comptitionCodeStr = competitionCode;
                comptitionDateStr = competitionDate;
                initUserInfo();
                page = 1;
                initData(StatusVariable.REFRESH);
                dataCompare(list, competitionCode);
                dataSite(list);

            }
        });
        //后一场
        binding.tvBehind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = (position + 1);
                String competitionCode = list.get(position).getCompetitionCode();
                String competitionDate = (String) list.get(position).getCompetitionDate();
                comptitionCodeStr = competitionCode;
                comptitionDateStr = competitionDate;
                initUserInfo();
                page = 1;
                initData(StatusVariable.REFRESH);
                dataCompare(list, competitionCode);
                dataSite(list);

            }
        });


    }

    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            loadingDialog.dismiss();
        }
    }

    //刷新数据
    @Override
    public void refresh(RefreshLayout refreshLayout, int type) {
        page = 1;
        binding.smartRefreshlayout.setNoMoreData(false);
        initData(type);
    }
    //加载数据
    @Override
    public void loadMore(RefreshLayout refreshLayout, int type) {
        if (page <= totalPage) {
            initData(type);
            binding.smartRefreshlayout.setNoMoreData(false);
        } else {
            binding.smartRefreshlayout.finishLoadMoreWithNoMoreData();
            binding.smartRefreshlayout.setNoMoreData(true);
        }
    }


}