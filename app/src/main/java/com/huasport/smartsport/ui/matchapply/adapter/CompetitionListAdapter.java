package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.huasport.smartsport.databinding.CompetitionlistHeaderitemBinding;
import com.huasport.smartsport.ui.matchapply.bean.CompetitionListBean;
import com.huasport.smartsport.ui.matchapply.bean.EventsBean;
import com.huasport.smartsport.ui.matchapply.bean.GroupEventsBean;
import com.huasport.smartsport.ui.matchapply.view.CompetitionListActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * Created by 陆向阳 on 2018/6/8.
 */
public class CompetitionListAdapter extends BaseAdapter<CompetitionListBean.ResultBean.SitesBean, BaseViewHolder> {

    private CompetitionListActivity competitionListActivity;
    private int expandPosition = -1;//记录点击的position
    private List<GroupEventsBean.ResultBean.GroupsBean.EventsBean> events = new ArrayList<>();
    private List<String> itemCodelist = new ArrayList();//存储ItemCode
    private List<String> itemTypeList = new ArrayList<>();//存储ItemType
    private List<String> siteCode = new ArrayList<>();
    private GroupItemRecyclerViewAdapter groupedRecyclerViewAdapternew;
    public ObservableField<Boolean> select = new ObservableField<>(false);
    public ObservableField<String> limit = new ObservableField<>("");//记录人数限制
    public ObservableField<Boolean> selectStatus = new ObservableField<>(false);//记录选中状态
    private List<EventsBean> eventsBeanList = new ArrayList<>();
    private final ToastUtil toastUtil;
    private String sTime = "";
    private String eTime = "";
    private static String format = "yyyy年MM月dd日";
    private static  String changelineformat="yyyy-MM-dd";

    public CompetitionListAdapter(CompetitionListActivity competitionListActivity) {
        super(competitionListActivity);
        this.competitionListActivity = competitionListActivity;
        toastUtil = new ToastUtil(competitionListActivity);
    }

    public List<String> getList() {
        return itemCodelist;
    }

    //根据viewType来确定显示的布局
    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        CompetitionlistHeaderitemBinding competitionlistHeaderitemBinding = DataBindingUtil.inflate(LayoutInflater.from(competitionListActivity), R.layout.competitionlist_headeritem, parent, false);

        return new BaseViewHolder(competitionlistHeaderitemBinding);
    }

    @Override
    public void onBindVH(final BaseViewHolder baseViewHolder, final int position) {

        final CompetitionlistHeaderitemBinding headeritemBinding = (CompetitionlistHeaderitemBinding) baseViewHolder.getBinding();
        //name
        String fieldName = mList.get(position).getFieldName();
        if (!EmptyUtil.isEmpty(fieldName)){
            headeritemBinding.tvFieldName.setText(fieldName);
        }
        //时间
        String startTime = mList.get(position).getStartTime();
        String endTime = mList.get(position).getEndTime();

        if(!EmptyUtil.isEmpty(startTime)){
            sTime = DateUtil.StrToDate(startTime, changelineformat);
        }
        if (!EmptyUtil.isEmpty(endTime)){
            eTime = DateUtil.StrToDate(endTime, changelineformat);
        }
        if(!EmptyUtil.isEmpty(sTime)&&!EmptyUtil.isEmpty(eTime)){
            headeritemBinding.tvTime.setText(sTime +"\n"+ eTime);
        }else if (!EmptyUtil.isEmpty(sTime)&&EmptyUtil.isEmpty(eTime)){
            headeritemBinding.tvTime.setText(sTime);
        }else if (EmptyUtil.isEmpty(sTime)&&!EmptyUtil.isEmpty(eTime)){
            headeritemBinding.tvTime.setText(eTime);
        }
        //address
        String fieldAddress = mList.get(position).getFieldAddress();
        if(!EmptyUtil.isEmpty(fieldAddress)){
            headeritemBinding.tvFiledAddress.setText(fieldAddress);
        }


        // 判断显示逻辑
        if (position == expandPosition) {

            EventsAdapter eventsAdapter = new EventsAdapter(competitionListActivity, this);
            headeritemBinding.groupItemRecyclerView.setLayoutManager(new LinearLayoutManager(competitionListActivity));
            headeritemBinding.groupItemRecyclerView.setAdapter(eventsAdapter);
            eventsAdapter.loadData(mList.get(position).getEventsBeanArrayList());

            //清除选中集合
            siteCode.clear();

            mList.get(position).setCheck(true);//设置为选中状态
            headeritemBinding.programLayout.setVisibility(View.VISIBLE);
            siteCode.add(mList.get(position).getSiteCode());
            setSiteCode(siteCode);

        } else {
            //清除选中集合
            headeritemBinding.programLayout.setVisibility(View.GONE);
            mList.get(position).setCheck(false);
            itemCodelist.clear();

        }
        if (mList.get(position).getDistance() != null && !mList.get(position).getDistance().equals("null")) {
            headeritemBinding.distance.setText("<" + mList.get(position).getDistance() + "km");
        }

        headeritemBinding.headerCheckbox.setChecked(mList.get(position).isCheck());
        //点击事件,点击一个隐藏其他显示出来的
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsBeanList.clear();
                if (expandPosition != position) {
                    expandPosition = position;
                    selectStatus.set(true);
                    matchGroupinitData(headeritemBinding, mList.get(position).getSiteCode(), position);
                } else {
                    expandPosition = -1;
                    notifyDataSetChanged();
                    itemCodelist.clear();
                    itemTypeList.clear();
                    itemCodelist.clear();
                    selectStatus.set(false);
                }
            }
        });

    }

    /**
     * 选择分组列表数据请求
     *
     * @param headeritemBinding
     * @param siteCode
     * @param position
     */
    private void matchGroupinitData(final CompetitionlistHeaderitemBinding headeritemBinding, String siteCode, final int position) {


        HashMap params = new HashMap();
        params.put("baseMethod", Method.MATCHEVENTS);
        params.put("siteCode", siteCode);
        params.put("baseUrl", Config.baseUrl);

        OkHttpUtil.getRequest(competitionListActivity, params, new RequestCallBack<GroupEventsBean>() {
            @Override
            public void onSuccess(Response<GroupEventsBean> response) {
                GroupEventsBean groupEventsBean = response.body();
                if(!EmptyUtil.isEmpty(groupEventsBean)){
                    int resultCode = groupEventsBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        GroupEventsBean.ResultBean resultBean = groupEventsBean.getResult();
                        if(!EmptyUtil.isEmpty(resultBean)){
                            List<GroupEventsBean.ResultBean.GroupsBean> groups = resultBean.getGroups();

                            for (int i = 0; i < groups.size(); i++) {

                                List<GroupEventsBean.ResultBean.GroupsBean.EventsBean> events = groups.get(i).getEvents();

                                for (int f = 0; f < events.size(); f++) {
                                    //将数据存到新得对象中
                                    EventsBean eventsBean = new EventsBean();
                                    String groupName = groups.get(i).getGroupName();
                                    eventsBean.setGroupName(groupName);
                                    eventsBean.setCanApply(events.get(f).isCanApply());
                                    eventsBean.setEndTime(events.get(f).getEndTime());
                                    eventsBean.setEntryFee(events.get(f).getEntryFee());
                                    eventsBean.setGroupLimit(events.get(f).getGroupLimit());
                                    eventsBean.setPersonLimit(events.get(f).getPersonLimit());
                                    eventsBean.setItemCode(events.get(f).getItemCode());
                                    eventsBean.setItemType(events.get(f).getItemType());
                                    eventsBean.setValid(events.get(f).isIsValid());
                                    eventsBean.setStartTime(events.get(f).getStartTime());
                                    eventsBean.setSurplusQuota(events.get(f).getSurplusQuota());
                                    eventsBean.setItemName(events.get(f).getItemName());
                                    eventsBean.setEntryFeeStr(events.get(f).getEntryFeeStr());
                                    eventsBeanList.add(eventsBean);
                                }

                            }
                            mList.get(position).setEventsBeanArrayList(eventsBeanList);

                            notifyDataSetChanged();
                        } else {
                            toastUtil.showTopSnackBar("暂无分组");
                        }
                    }

                }
            }

            @Override
            public GroupEventsBean parseNetworkResponse(String jsonResult) {
                GroupEventsBean groupEventsBean = JSON.parseObject(jsonResult, GroupEventsBean.class);

                return groupEventsBean;
            }

            @Override
            public void onFailed(int code, String msg) {
                if(!EmptyUtil.isEmpty(msg)){
                    toastUtil.centerToast(msg);
                }
            }
        });
    }

    //添加数据
    public void addEventBean(EventsBean eventsBean) {
        if (eventsBean.getItemType().equals("personal")) {

            //添加Type
            itemTypeList.add(eventsBean.getItemType());
            //添加ItemCode
            itemCodelist.add(eventsBean.getItemCode());

        } else if (eventsBean.getItemType().equals("group")) {
            itemTypeList.clear();
            itemCodelist.clear();
            //添加Type
            itemTypeList.add(eventsBean.getItemType());
            //添加ItemCode
            itemCodelist.add(eventsBean.getItemCode());

        }

        Log.e("ListSizeSize", itemCodelist.size() + "");
        setItemCodelist(itemCodelist);
        setItemTypeList(itemTypeList);
        //报名限制
        limit.set(eventsBean.getGroupLimit() + "");
    }

    //删除数据
    public void deleteEventBean(EventsBean eventsBean) {

        //删除ItemCode
        itemCodelist.remove(eventsBean.getItemCode());
        Log.e("ListSizeSize", itemCodelist.size() + "");
        //删除ItemType
        itemTypeList.remove(eventsBean.getItemType());
        setItemCodelist(itemCodelist);
        setItemTypeList(itemTypeList);
    }

    /*
     * 获取比赛类型
     * */
    public List<String> getItemTypeList() {
        return itemTypeList;
    }

    public void setItemTypeList(List<String> itemTypeList) {
        this.itemTypeList = itemTypeList;
    }

    /*
     * 获取ItemCode
     * */
    public List<String> getItemCodelist() {
        return itemCodelist;
    }

    public void setItemCodelist(List<String> itemCodelist) {
        this.itemCodelist = itemCodelist;
    }

    //RecyclerView条目的点击事件
    public interface clickItem {
        void onclickItem(BaseViewHolder baseViewHolder, int position);
    }

    clickItem clickItem;

    public void setClickItem(CompetitionListAdapter.clickItem clickItem) {
        this.clickItem = clickItem;
    }

    public List<String> getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(List<String> groupSelectList) {
        this.siteCode = groupSelectList;
    }
}
