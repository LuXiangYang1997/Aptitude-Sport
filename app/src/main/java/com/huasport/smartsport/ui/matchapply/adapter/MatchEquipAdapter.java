package com.huasport.smartsport.ui.matchapply.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.MatchequipItemBinding;
import com.huasport.smartsport.ui.matchapply.bean.MatchOtherBean;
import com.huasport.smartsport.ui.matchapply.bean.MatchsBean;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.util.EmptyUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/7.
 */

public class MatchEquipAdapter extends BaseAdapter<MatchsBean.ResultBean.MatchBean.ChaptersBean, BaseViewHolder> {

    private MatchIntroduceActivity matchIntroduceActivity;
    private MatchequipItemBinding matchequipItemBinding;
    private List<MatchOtherBean> matchOtherBeanList;


    public MatchEquipAdapter(MatchIntroduceActivity matchIntroduceActivity) {
        super(matchIntroduceActivity);
        this.matchIntroduceActivity = matchIntroduceActivity;
        initData();
    }

    private void initData() {

    }


    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {

        matchequipItemBinding = DataBindingUtil.inflate(LayoutInflater.from(matchIntroduceActivity), R.layout.matchequip_item, parent, false);

        return new BaseViewHolder(matchequipItemBinding);

    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        MatchequipItemBinding binding = (MatchequipItemBinding) baseViewHolder.getBinding();

        String type = mList.get(position).getType();
        String name = mList.get(position).getName();
        List<MatchsBean.ResultBean.MatchBean.ChaptersBean.CompositeVosBean> compositeVos = mList.get(position).getCompositeVos();
        if (!EmptyUtil.isEmpty(compositeVos)) {
            for (int i = 0; i <= compositeVos.size(); i++) {
                if (compositeVos.size() != 0) {
                    compositeVos.get(i).setType(type);
                    compositeVos.get(i).setTitle(name);
                }
                break;
            }

        }
        List<MatchsBean.ResultBean.MatchBean.ChaptersBean.CompositeVosBean> equipmentsVos = mList.get(position).getCompositeVos();
        //循环拿出实体
        if (!EmptyUtil.isEmpty(equipmentsVos)) {

            MatchEquipDataAdapter matchEquipDataAdapter = new MatchEquipDataAdapter(matchIntroduceActivity);
            binding.matchequipRecyclerview.setLayoutManager(new LinearLayoutManager(matchIntroduceActivity));
            binding.matchequipRecyclerview.setAdapter(matchEquipDataAdapter);
            matchEquipDataAdapter.loadData(equipmentsVos);

        }
        matchOtherBeanList = new ArrayList<>();

        if (mList.get(position).getContent() != null) {
            if (!EmptyUtil.isEmpty(mList.get(position).getContent().toString())) {
                MatchOtherBean matchOtherBean = new MatchOtherBean();
                matchOtherBean.setName(mList.get(position).getName());
                matchOtherBean.setContent(mList.get(position).getContent().toString());
                matchOtherBean.setType(mList.get(position).getType());
                matchOtherBeanList.add(matchOtherBean);
            }
        }

        if (matchOtherBeanList.size() > 0) {
            //适配器
            MatchOtherAdapter matchOtherAdapter = new MatchOtherAdapter(matchIntroduceActivity);
            binding.matchOtherRecyclerView.setLayoutManager(new LinearLayoutManager(matchIntroduceActivity));//layoutManager
            binding.matchOtherRecyclerView.setAdapter(matchOtherAdapter);//设置适配器
            matchOtherAdapter.loadData(matchOtherBeanList);//加载数据
        }

    }

}
