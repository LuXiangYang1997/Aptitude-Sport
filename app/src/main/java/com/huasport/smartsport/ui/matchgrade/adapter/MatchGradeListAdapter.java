package com.huasport.smartsport.ui.matchgrade.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.MatchgradelistItemlayoutBinding;
import com.huasport.smartsport.ui.matchgrade.bean.MatchEventListBean;
import com.huasport.smartsport.ui.matchgrade.bean.MatchGradeListBean;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchGradeListAdapter extends BaseAdapter<MatchGradeListBean.ResultBean.MatchsBean, BaseViewHolder> {
    private int tag = -1;
    private FragmentActivity activity;
    private final ToastUtil toastUtil;
    private List<MatchEventListBean.ResultBean.ResultListBean> list;
    private MatchEventAdapter matchEventAdapter;
    private List<MatchEventListBean.ResultBean.ResultListBean> listBeans = new ArrayList<>();
    private final LoadingDialog loadingDialog;


    public MatchGradeListAdapter(FragmentActivity activity) {
        super(activity);
        this.activity = activity;
        toastUtil = new ToastUtil(activity);
        loadingDialog = new LoadingDialog(activity, R.style.LoadingDialog);
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        MatchgradelistItemlayoutBinding matchgradelistItemlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.matchgradelist_itemlayout, parent, false);

        return new BaseViewHolder(matchgradelistItemlayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        final MatchgradelistItemlayoutBinding binding = (MatchgradelistItemlayoutBinding) baseViewHolder.getBinding();

        //判断选中状态
        boolean select = mList.get(position).isSelect();
        if(!EmptyUtil.isEmpty(select)){

            if (select) {
                binding.llList.setVisibility(View.VISIBLE);
                binding.imgArrow.setImageResource(R.mipmap.icon_arrowup);
            } else {
                binding.llList.setVisibility(View.GONE);
                binding.imgArrow.setImageResource(R.mipmap.icon_arrowdown);
            }

        }
        //比赛icon
        String matchImg = mList.get(position).getMatchImg();
        if (!EmptyUtil.isEmpty(matchImg)) {
            GlideUtil.LoadImage(activity, mList.get(position).getMatchImg(), binding.imgMatch);
        }
        //名字
        String matchName = mList.get(position).getMatchName();
        if (!EmptyUtil.isEmpty(matchName)) {
            binding.tvMatchtitle.setText(matchName);
        }
        //时间
        long startTime = mList.get(position).getStartTime();
        long endTime = mList.get(position).getEndTime();
        if (!EmptyUtil.isEmpty(startTime) && !EmptyUtil.isEmpty(endTime)) {
            String sTime = DateUtil.timeToDate(startTime);
            String eTime = DateUtil.timeToDate(endTime);
            binding.tvTime.setText(sTime + "至" + eTime);
        } else if (!EmptyUtil.isEmpty(startTime) && EmptyUtil.isEmpty(endTime)) {
            String sTime = DateUtil.timeToDate(startTime);
            binding.tvTime.setText(sTime);
        } else if (EmptyUtil.isEmpty(startTime) && EmptyUtil.isEmpty(endTime)) {
            String eTime = DateUtil.timeToDate(endTime);
            binding.tvTime.setText(eTime);
        }
        //修改箭头状态
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mList.get(position).isSelect()) {
                    mList.get(position).setSelect(true);
                    tag = position;
                    binding.imgArrow.setImageResource(R.mipmap.icon_arrowup);
                    binding.llList.setVisibility(View.VISIBLE);
                    loadingDialog.show();//弹出加载框
                    initData(mList.get(position).getMatchCode(), mList.get(position).getMatchName());
                } else {
                    mList.get(position).setSelect(false);
                    tag = -1;
                    binding.llList.setVisibility(View.GONE);
                    binding.imgArrow.setImageResource(R.mipmap.icon_arrowdown);
                }
                notifyDataSetChanged();
            }
        });
        if (tag == position) {
            matchEventAdapter = new MatchEventAdapter(activity);
            binding.recyclerViewEvent.setLayoutManager(new LinearLayoutManager(activity));
            binding.recyclerViewEvent.setAdapter(matchEventAdapter);
            matchEventAdapter.loadData(listBeans);
        }
    }

    /**
     * 加载Event数据
     * @param code
     * @param matchName
     */
    private void initData(final String code, final String matchName) {
        listBeans.clear();
        HashMap params = new HashMap();
        params.put("baseMethod", Method.HOMEGRADESMALLLIST);
        params.put("matchCode", code);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(activity, params, new RequestCallBack<MatchEventListBean>() {
            @Override
            public void onSuccess(Response<MatchEventListBean> response) {
                MatchEventListBean matchEventListBean = response.body();
                if (!EmptyUtil.isEmpty(matchEventListBean)){
                    int resultCode = matchEventListBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        MatchEventListBean.ResultBean resultBean = matchEventListBean.getResult();
                        if (!EmptyUtil.isEmpty(resultBean)){

                            List<MatchEventListBean.ResultBean.ResultListBean> resultList = resultBean.getResultList();

                            for (int i = 0; i < resultList.size(); i++) {
                                MatchEventListBean.ResultBean.ResultListBean resultListBean = new MatchEventListBean.ResultBean.ResultListBean();
                                resultListBean.setArea(resultList.get(i).getArea());
                                resultListBean.setCompetitionCode(resultList.get(i).getCompetitionCode());
                                resultListBean.setEvent(resultList.get(i).getEvent());
                                resultListBean.setGroup(resultList.get(i).getGroup());
                                resultListBean.setMatchCode(code);
                                resultListBean.setMatchName(matchName);
                                listBeans.add(resultListBean);

                            }
                            notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public MatchEventListBean parseNetworkResponse(String jsonResult) {

                MatchEventListBean matchEventListBean = JSON.parseObject(jsonResult, MatchEventListBean.class);

                return matchEventListBean;
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
                if (!EmptyUtil.isEmpty(loadingDialog)){
                    loadingDialog.dismiss();
                }
            }
        });
    }

}
