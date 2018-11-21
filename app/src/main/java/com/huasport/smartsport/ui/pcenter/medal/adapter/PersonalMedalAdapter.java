package com.huasport.smartsport.ui.pcenter.medal.adapter;


import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.huasport.smartsport.BR;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.base.BaseViewHolder;
import com.huasport.smartsport.databinding.PersonalMymedalLayoutBinding;
import com.huasport.smartsport.ui.pcenter.bean.PersonalMedalBean;
import com.huasport.smartsport.ui.pcenter.medal.view.PersonalMyMedalActivity;
import com.huasport.smartsport.util.EmptyUtil;


public class PersonalMedalAdapter extends BaseAdapter<PersonalMedalBean.ResultBean.ScoreCertBean, BaseViewHolder> {

    private PersonalMyMedalActivity personalMyMedalActivity;
    private PopupWindow popupWindow;

    public PersonalMedalAdapter(PersonalMyMedalActivity personalMyMedalActivity) {
        super(personalMyMedalActivity);
        this.personalMyMedalActivity = personalMyMedalActivity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        PersonalMymedalLayoutBinding personalMymedalLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(personalMyMedalActivity), R.layout.personal_mymedal_layout, parent, false);
        return new BaseViewHolder(personalMymedalLayoutBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, final int position) {
        PersonalMymedalLayoutBinding binding = (PersonalMymedalLayoutBinding) baseViewHolder.getBinding();
        binding.setVariable(BR.medalBean, mList.get(position));
        binding.setVariable(BR.medalAdapter, this);

        if (!EmptyUtil.isEmpty(mList.get(position).getMatchName())) {
            binding.medalMatchName.setText(mList.get(position).getMatchName());
        }
        if (!EmptyUtil.isEmpty(mList.get(position).getComtitionName())) {
            binding.medalCompetitionName.setText((String) mList.get(position).getComtitionName());
        } else {
            binding.medalCompetitionName.setText(mList.get(position).getEventName());
        }
        if (!EmptyUtil.isEmpty(mList.get(position).getPartTimeStr())) {
            binding.partTimeStr.setText(mList.get(position).getPartTimeStr());
        }


        binding.lookupCerficate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                successType
//                SharedPreferencesUtil.setParam(personalMyMedalActivity, "successType", "mymedal");
//                Intent intent = new Intent(personalMyMedalActivity, LookUpCertificateActivity.class);
//                intent.putExtra("cerficateUrl", mList.get(position).getCertPicUrl());
//                intent.putExtra("certCode", mList.get(position).getScoreCertCode());
//                personalMyMedalActivity.startActivity(intent);


            }
        });


    }

    public void lookMedal(PersonalMedalBean.ResultBean.ScoreCertBean scoreCertBean) {

        shareClick.shareclick(scoreCertBean);

    }

    public interface ShareClick {

        void shareclick(PersonalMedalBean.ResultBean.ScoreCertBean scoreCertBean);

    }

    ShareClick shareClick;

    public void setShareClick(ShareClick shareClick) {
        this.shareClick = shareClick;
    }
}
