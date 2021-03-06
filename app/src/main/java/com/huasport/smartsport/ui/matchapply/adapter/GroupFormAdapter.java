package com.huasport.smartsport.ui.matchapply.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.ui.matchapply.bean.AddMemberInitializeBean;
import com.huasport.smartsport.ui.matchapply.view.AdditionMemberActivity;
import com.huasport.smartsport.util.EmptyUtil;


import java.util.HashMap;

/**
 * Created by 陆向阳 on 2018/7/26.
 */

public class GroupFormAdapter extends BaseAdapter<AddMemberInitializeBean.ResultBean.PropertiesBean, RecyclerView.ViewHolder> {

    private HashMap<String, String> mParams;
    private String phoneNum;
    private String playerName;
    private AdditionMemberActivity additionMemberActivity;

    public GroupFormAdapter(AdditionMemberActivity additionMemberActivity) {
        super(additionMemberActivity);
        this.additionMemberActivity = additionMemberActivity;
        mParams = new HashMap<>();
    }


    public HashMap<String, String> getParams() {
        return mParams;
    }


    public String getPhoneNum() {
        return phoneNum;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public int getItemViewType(int position) {

        if (mList.get(position).getCnname().equals("性别")) {
            return StatusVariable.MSG_TYPE_SEX;
        } else if (mList.get(position).getCnname().equals("证件类型") || mList.get(position).getCnname().equals("生日")) {
            return StatusVariable.MSG_TYPE_BTN;
        } else {
            return StatusVariable.MSG_TYPE_NORMAL;
        }
    }

    //绑定视图
    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        if (viewType == StatusVariable.MSG_TYPE_SEX) {
            View sexView = LayoutInflater.from(additionMemberActivity).inflate(R.layout.sex_layout, parent, false);
            return new SexViewHolder(sexView);
        } else if (viewType == StatusVariable.MSG_TYPE_BTN) {
            View btnView = LayoutInflater.from(additionMemberActivity).inflate(R.layout.btn_layout, parent, false);
            return new TextViewHolder(btnView);
        } else {
            View edittextView = LayoutInflater.from(additionMemberActivity).inflate(R.layout.edittext_layout, parent, false);
            return new EditViewHolder(edittextView);
        }
    }

    //绑定数据
    @Override
    public void onBindVH(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof SexViewHolder) {
            //性别Holder
            SexViewHolder sexViewHolder = (SexViewHolder) viewHolder;
            //判断是否是性别，是则判断是否是必填，必填显示星号，反之则不显示

            if (!EmptyUtil.isEmpty(mList.get(position).getCnname())) {
                // if (mList.get(position).getCnname() != null && !mList.get(position).getCnname().equals("") && !mList.get(position).getCnname().equals("null")) {

                if (mList.get(position).getCnname().equals("性别")) {
                    sexViewHolder.tv_sex.setText(mList.get(position).getCnname());

                    //判断是否显示
                    if (mList.get(position).isIsRequired()) {
                        sexViewHolder.sex_requird.setVisibility(View.VISIBLE);
                    } else {
                        sexViewHolder.sex_requird.setVisibility(View.GONE);
                    }

                    if (!EmptyUtil.isEmpty(mList.get(position).getVal())) {

                        //  if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("") && !mList.get(position).getVal().equals("null")) {

                        if (mList.get(position).getVal().equals("m")) {
                            sexViewHolder.boy.setChecked(true);
                        } else if (mList.get(position).getVal().equals("f")) {
                            sexViewHolder.girl.setChecked(true);
                        }
                        mParams.put(mList.get(position).getAttributeName(), mList.get(position).getVal());
                    } else {
                        mParams.put(mList.get(position).getAttributeName(), "");//空字符串
                    }
                    //如果性别状态改变了则重新存储参数
                    sexViewHolder.sex_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            if (i == R.id.boy) {
                                mList.get(position).setVal("m");
                            } else {
                                mList.get(position).setVal("f");
                            }
                            mParams.put((String) mList.get(position).getAttributeName(), mList.get(position).getVal());
                        }
                    });

                }
            }

        } else if (viewHolder instanceof TextViewHolder) {

            final TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
            if (!EmptyUtil.isEmpty(mList.get(position).getCnname())) {
                // if (mList.get(position).getCnname() != null && !mList.get(position).getCnname().equals("") && !mList.get(position).getCnname().equals("null")) {

                if (mList.get(position).getCnname().equals("证件类型") || mList.get(position).getCnname().equals("生日")) {
                    textViewHolder.btntv.setText(mList.get(position).getCnname());

                    if (mList.get(position).isIsRequired() == true) {
                        textViewHolder.text_requird.setVisibility(View.VISIBLE);
                    } else {
                        textViewHolder.text_requird.setVisibility(View.GONE);
                    }

                    if (mList.get(position).getCnname().equals("证件类型")) {
                        if (!EmptyUtil.isEmpty(mList.get(position).getVal())) {
                            // if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("") && !mList.get(position).getVal().equals("null")) {
                            textViewHolder.selectName.setText(mList.get(position).getVal());
                            if (mList.get(position).getVal().equals("身份证")) {
                                mParams.put((String) mList.get(position).getAttributeName(), StatusVariable.IDCARD + "");
                            } else if (mList.get(position).getVal().equals("护照")) {
                                mParams.put((String) mList.get(position).getAttributeName(), StatusVariable.PASSCARD + "");
                            } else if (mList.get(position).getVal().equals("军官证")) {
                                mParams.put((String) mList.get(position).getAttributeName(), StatusVariable.CERTIFICATECARD + "");
                            }
                        } else {
                            textViewHolder.selectName.setText("请选择" + mList.get(position).getCnname());
                            mParams.put((String) mList.get(position).getAttributeName(), "");
                        }
                    }

                    if (mList.get(position).getCnname().equals("生日")) {
                        if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("") && !mList.get(position).getVal().equals("null")) {
                            textViewHolder.selectName.setText(mList.get(position).getVal());
                            mParams.put((String) mList.get(position).getAttributeName(), mList.get(position).getVal());
                        } else {
                            textViewHolder.selectName.setText("请选择" + mList.get(position).getCnname());
                            mParams.put((String) mList.get(position).getAttributeName(), "");
                        }
                    }

                } else {
                    textViewHolder.selectName.setText("请选择" + mList.get(position).getCnname());
                }

                textViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mList.get(position).getCnname().equals("生日")) {
                            click.birdthClick(textViewHolder, position, StatusVariable.BIRDTHCLICK);
                        } else if (mList.get(position).getCnname().equals("证件类型")) {
                            click.certificateClick(textViewHolder, position, StatusVariable.CERTIFICATE);
                        }
                    }
                });


            }


        } else {
            Log.e("lwd", "输入类型:" + mList.get(position).getCnname());
            final EditViewHolder editViewHolder = (EditViewHolder) viewHolder;

            if (!EmptyUtil.isEmpty(mList.get(position).getCnname())) {

                //if (mList.get(position).getCnname() != null && !mList.get(position).getCnname().equals("") && !mList.get(position).getCnname().equals("null")) {
                if (mList.get(position).getCnname().equals("身份证正面") || mList.get(position).getCnname().equals("身份证反面") || mList.get(position).getCnname().equals("军官证") || mList.get(position).getCnname().equals("护照")) {
                    editViewHolder.ll_edittext.setVisibility(View.GONE);
                }
                editViewHolder.textView_name.setText(mList.get(position).getCnname());

                if (mList.get(position).isIsRequired() == true) {
                    editViewHolder.edittext_requird.setVisibility(View.VISIBLE);
                } else {
                    editViewHolder.edittext_requird.setVisibility(View.GONE);
                }
                if (!EmptyUtil.isEmpty(mList.get(position).getVal())) {
                    // if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("") && !mList.get(position).getVal().equals("null")) {
                    editViewHolder.form_edittext.setText(mList.get(position).getVal());
                    mParams.put(mList.get(position).getAttributeName(), mList.get(position).getVal());
                } else {
                    editViewHolder.form_edittext.setHint("请输入" + mList.get(position).getCnname());
                    mParams.put(mList.get(position).getAttributeName(), "");
                }

            }

            final TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable editabledata) {
                    Log.e("textWatcher", editViewHolder.getAdapterPosition() + "");
                    if (editViewHolder.form_edittext.hasFocus()) {//判断当前EditText是否有焦点在
                        //通过接口回调将数据传递到Activity中
                        mList.get(position).setVal(editabledata.toString());
                        mParams.put((String) mList.get(position).getAttributeName(), editabledata.toString());
                    }
                }
            };

            //设置EditText的焦点监听器判断焦点变化，当有焦点时addTextChangedListener，失去焦点时removeTextChangedListener
            editViewHolder.form_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        editViewHolder.form_edittext.addTextChangedListener(textWatcher);
                    } else {
                        editViewHolder.form_edittext.removeTextChangedListener(textWatcher);
                    }
                }
            });

        }


    }

    //性别
    public class SexViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_sex;
        private final TextView sex_requird;
        private final RadioGroup sex_radiogroup;
        private final RadioButton boy;
        private final RadioButton girl;

        public SexViewHolder(View itemView) {
            super(itemView);
            tv_sex = itemView.findViewById(R.id.tv_sex);
            sex_requird = itemView.findViewById(R.id.sex_requird);
            sex_radiogroup = itemView.findViewById(R.id.sex_radiogroup);
            boy = itemView.findViewById(R.id.boy);
            girl = itemView.findViewById(R.id.girl);
        }
    }

    //输入
    public class EditViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_name;
        private final EditText form_edittext;
        private final TextView edittext_requird;
        private final LinearLayout ll_edittext;

        public EditViewHolder(View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_name);
            form_edittext = itemView.findViewById(R.id.form_edittext);
            edittext_requird = itemView.findViewById(R.id.edittext_requird);
            ll_edittext = itemView.findViewById(R.id.ll_edittext);
        }
    }

    //文本或选择
    public class TextViewHolder extends RecyclerView.ViewHolder {

        private final TextView btntv;
        private final TextView selectName;
        private final TextView text_requird;


        public TextViewHolder(View itemView) {
            super(itemView);
            btntv = itemView.findViewById(R.id.tv_datebirth);
            selectName = itemView.findViewById(R.id.select_name);
            text_requird = itemView.findViewById(R.id.text_requird);
        }
    }

    //手机号
    public class PhoneNumberViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvPhoneNum;
        private final EditText etphoneNum;
        private final TextView phoneNumberRequird;
        private final TextView send_authcode;
        private final EditText form_verificationCode;
        private final TextView code_requird;

        public PhoneNumberViewHolder(View itemView) {
            super(itemView);
            tvPhoneNum = itemView.findViewById(R.id.tv_phoneNum);//名字
            etphoneNum = itemView.findViewById(R.id.form_userPhoneNum);//输入框
            phoneNumberRequird = itemView.findViewById(R.id.phoneNum_requird);//是否是必填
            send_authcode = itemView.findViewById(R.id.send_Authcode);//发送验证码
            form_verificationCode = itemView.findViewById(R.id.form_VerificationCode);//验证码输入框
            code_requird = itemView.findViewById(R.id.code_requird);//是否必填
        }
    }

    public interface GroupFormClick {
        void birdthClick(RecyclerView.ViewHolder viewHolder, int position, int type);//生日点击

        void certificateClick(RecyclerView.ViewHolder viewHolder, int position, int type);//证件类型

        void codeGet(RecyclerView.ViewHolder viewHolder, int position, int type);//获取验证码
    }

    GroupFormClick click;

    public void setClick(GroupFormClick click) {
        this.click = click;
    }

}
