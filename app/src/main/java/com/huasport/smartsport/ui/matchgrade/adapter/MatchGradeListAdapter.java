package com.huasport.smartsport.ui.matchgrade.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MatchgradelistItemlayoutBinding;
import com.huasport.smartsport.ui.matchgrade.bean.MatchGradeListBean;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;

public class MatchGradeListAdapter extends BaseAdapter<MatchGradeListBean.ResultBean.MatchsBean, BaseViewHolder> {
    private int tag = -1;
    private FragmentActivity activity;
//    private List<MatchEventListBean.ResultBean.ResultListBean> list;
//    private MatchEventAdapter matchEventAdapter;
//    private List<MatchEventListBean.ResultBean.ResultListBean> listBeans = new ArrayList<>();


    public MatchGradeListAdapter(FragmentActivity activity) {
        super(activity);
        this.activity = activity;
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
//            matchEventAdapter = new MatchEventAdapter(activity);
//            binding.eventMatchRecyclerview.setLayoutManager(new LinearLayoutManager(activity));
//            binding.eventMatchRecyclerview.setAdapter(matchEventAdapter);
//            matchEventAdapter.loadData(listBeans);
        }
    }

//    private void initData(final String code, final String matchName) {
//        listBeans.clear();
//        HashMap params = new HashMap();
//        params.put("method", Method.HOMEGRADESMALLLIST);
//        params.put("matchCode", code);
//        params.put("baseUrl", Config.baseUrl);
//        OkhttpUtils.getRequest(activity, params, new BaseHttpCallBack<MatchEventListBean>(activity, true) {
//            @Override
//            public MatchEventListBean parseNetworkResponse(String jsonResult) throws Exception {
//
//                MatchEventListBean matchEventListBean = JSON.parseObject(jsonResult, MatchEventListBean.class);
//
//                return matchEventListBean;
//            }
//
//            @Override
//            public void onSuccess(MatchEventListBean matchEventListBean, Call call, Response response) {
//                if (EmptyUtils.isNotEmpty(matchEventListBean)) {
//                    if (matchEventListBean.getResultCode() == StatusVariable.SUCCESS) {
//
//                        list = matchEventListBean.getResult().getResultList();
//                        for (int i = 0; i < list.size(); i++) {
//                            MatchEventListBean.ResultBean.ResultListBean resultListBean = new MatchEventListBean.ResultBean.ResultListBean();
//                            resultListBean.setArea(list.get(i).getArea());
//                            resultListBean.setCompetitionCode(list.get(i).getCompetitionCode());
//                            resultListBean.setEvent(list.get(i).getEvent());
//                            resultListBean.setGroup(list.get(i).getGroup());
//                            resultListBean.setMatchCode(code);
//                            resultListBean.setMatchName(matchName);
//                            listBeans.add(resultListBean);
//
//                        }
//                        notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String code, String msg) {
//                ToastUtils.toast(activity, msg);
//            }
//        });
//
//
//    }

}
