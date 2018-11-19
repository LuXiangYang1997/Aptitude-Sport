package com.huasport.smartsport.ui.matchapply.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.ui.matchapply.bean.MatchsBean;
import com.huasport.smartsport.ui.matchapply.view.MatchIntroduceActivity;
import com.huasport.smartsport.util.GlideUtil;


/**
 * Created by 陆向阳 on 2018/6/20.
 */

public class MatchEquipDataAdapter extends BaseAdapter<MatchsBean.ResultBean.MatchBean.ChaptersBean.CompositeVosBean, RecyclerView.ViewHolder> {
    private MatchIntroduceActivity matchIntroduceActivity;
    private final int TYPEONE = 1;
    private final int TYPETWO = 2;

    public MatchEquipDataAdapter(MatchIntroduceActivity matchIntroduceActivity) {
        super(matchIntroduceActivity);
        this.matchIntroduceActivity = matchIntroduceActivity;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getType() != null && !mList.get(position).getType().equals("null")) {
            if (mList.get(position).getType().equals("pic_explain")) {
                return TYPEONE;
            } else {
                return TYPETWO;
            }
        } else {
            return TYPETWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        if (viewType == TYPEONE) {
            View inflate = LayoutInflater.from(matchIntroduceActivity).inflate(R.layout.matchequip_item2, parent, false);
            return new zuzhiViewHolder(inflate);
        } else {
            View equip = LayoutInflater.from(matchIntroduceActivity).inflate(R.layout.match_equip_itemlayout, parent, false);
            return new equipViewHolder(equip);
        }
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder baseViewHolder, int position) {

        if (baseViewHolder instanceof equipViewHolder) {
            equipViewHolder equip = (equipViewHolder) baseViewHolder;

            if (mList.get(position).getType().equals("pic_desc")) {
                GlideUtil.LoadImage(matchIntroduceActivity, mList.get(position).getAttaUrl(), equip.equip_img);
                equip.equip_name.setText(mList.get(position).getName());
                equip.equip_title_tv.setText(mList.get(position).getTitle());
                if (position == 0) {
                    equip.equip_title_tv.setVisibility(View.VISIBLE);
                } else {
                    equip.equip_title_tv.setVisibility(View.GONE);
                }
            }
        } else {
            zuzhiViewHolder orientation = (zuzhiViewHolder) baseViewHolder;
            orientation.content.setText(mList.get(position).getName());
            GlideUtil.LoadImage(matchIntroduceActivity, mList.get(position).getAttaUrl(), orientation.img);
            orientation.title_tv.setText(mList.get(position).getTitle());
        }

    }

    public class equipViewHolder extends RecyclerView.ViewHolder {

        private final ImageView equip_img;
        private final TextView equip_name;
        private final TextView equip_title_tv;

        public equipViewHolder(View itemView) {
            super(itemView);
            equip_img = itemView.findViewById(R.id.equip_img);
            equip_name = itemView.findViewById(R.id.equip_name);
            equip_title_tv = itemView.findViewById(R.id.eequip_title_tv);
        }
    }

    public class zuzhiViewHolder extends RecyclerView.ViewHolder {

        private final TextView content;
        private final ImageView img;
        private final TextView title_tv;

        public zuzhiViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            img = itemView.findViewById(R.id.img);
            title_tv = itemView.findViewById(R.id.title_tv);
        }
    }

}
