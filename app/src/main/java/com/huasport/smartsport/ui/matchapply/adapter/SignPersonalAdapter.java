package com.huasport.smartsport.ui.matchapply.adapter;

import android.content.Context;
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
import com.huasport.smartsport.ui.matchapply.bean.PersonalInfoBean;
import com.huasport.smartsport.util.EmptyUtil;

import java.util.HashMap;

/**
 * Created by 陆向阳 on 2018/6/29.
 */

public class SignPersonalAdapter extends BaseAdapter<PersonalInfoBean, RecyclerView.ViewHolder> {

    private Context mContext;
    private HashMap<String, String> mParams = new HashMap<>();

    public SignPersonalAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    public HashMap<String, String> getParam() {
        return mParams;
    }

    public interface onClick {
        void birthdayOnClick(RecyclerView.ViewHolder viewHolder, int position, int type);//生日点击

        void certificateClick(RecyclerView.ViewHolder viewHolder, int position, int type);//证件类型

        void codeGet(RecyclerView.ViewHolder viewHolder, int position, int type);//获取验证码
    }

    onClick click;

    public void setOnClickListener(onClick click) {
        this.click = click;
    }

    @Override
    public int getItemViewType(int position) {
        String cnName = mList.get(position).getCnname();
        if (cnName.equals("性别")) {
            return StatusVariable.MSG_TYPE_SEX;
        } else if (mList.get(position).getCnname().equals("手机号码")) {
            return StatusVariable.MSG_TYPE_PHONENUM;
        } else if (cnName.equals("证件类型") || cnName.equals("生日")) {
            return StatusVariable.MSG_TYPE_BTN;
        } else if (mList.get(position).getCnname().equals("头像")) {
            return StatusVariable.MSG_TYPE_HEAD;
        } else {
            return StatusVariable.MSG_TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        if (viewType == StatusVariable.MSG_TYPE_SEX) {
            //性别
            View sexView = LayoutInflater.from(mContext).inflate(R.layout.item_sex_layout, parent, false);
            return new SexViewHolder(sexView);
        } else if (viewType == StatusVariable.MSG_TYPE_BTN) {
            //选择布局
            View btnView = LayoutInflater.from(mContext).inflate(R.layout.item_btn_layout, parent, false);
            return new TextViewHolder(btnView);
        } else if (viewType == StatusVariable.MSG_TYPE_PHONENUM) {
            //手机号码
            View phoneNumber = LayoutInflater.from(mContext).inflate(R.layout.phonenum_layout, parent, false);
            return new PhoneNumberViewHolder(phoneNumber);
        } else if (viewType == StatusVariable.MSG_TYPE_HEAD) {
            //头像
            View head = LayoutInflater.from(mContext).inflate(R.layout.item_head_layout, parent, false);
            return new HeadViewHolder(head);
        } else {
            //输入布局
            View editTextView = LayoutInflater.from(mContext).inflate(R.layout.item_edittext_layout, parent, false);
            return new EditViewHolder(editTextView);
        }
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof SexViewHolder) {
            /** 性别 ViewHolder */
            final SexViewHolder sexViewHolder = (SexViewHolder) viewHolder;
            if (!EmptyUtil.isEmpty(mList.get(position).getCnname())) {
                //  if (mList.get(position).getCnname() != null && !mList.get(position).getCnname().equals("") && !mList.get(position).getCnname().equals("null")) {

                if (mList.get(position).getCnname().equals("性别")) {
                    sexViewHolder.tv_sex.setText(mList.get(position).getCnname());

                    //判断是否显示
                    if (mList.get(position).isRequired()) {
                        sexViewHolder.sex_requird.setVisibility(View.VISIBLE);
                    } else {
                        sexViewHolder.sex_requird.setVisibility(View.GONE);
                    }
                    if (!EmptyUtil.isEmpty(mList.get(position).getVal())) {
                        // if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("null") && !mList.get(position).getVal().equals("")) {

                        if (mList.get(position).getVal().equals("m")) {
                            sexViewHolder.radioButtonboy.setChecked(true);
                        } else if (mList.get(position).getVal().equals("f")) {
                            sexViewHolder.radioButtongirl.setChecked(true);
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
            /** 选择布局 ViewHolder*/
            final TextViewHolder textViewHolder = (TextViewHolder) viewHolder;

            //判断类型来区分点击事件
            if (!EmptyUtil.isEmpty(mList.get(position).getCnname())) {
                // if (mList.get(position).getCnname() != null && !mList.get(position).getCnname().equals("null") && !mList.get(position).getCnname().equals("")) {

                if (mList.get(position).getCnname().equals("证件类型") || mList.get(position).getCnname().equals("生日")) {
                    textViewHolder.btntv.setText(mList.get(position).getCnname());

                    if (mList.get(position).isRequired() == true) {
                        textViewHolder.text_requird.setVisibility(View.VISIBLE);
                    } else {
                        textViewHolder.text_requird.setVisibility(View.GONE);
                    }

                    if (mList.get(position).getCnname().equals("证件类型")) {
                        if (!EmptyUtil.isEmpty(mList.get(position).getVal())) {
                            // if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("null") && !mList.get(position).getVal().equals("")) {
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
                        if (!EmptyUtil.isEmpty(mList.get(position).getVal())) {
                            // if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("null") && !mList.get(position).getVal().equals("")) {
                            textViewHolder.selectName.setText(mList.get(position).getVal());
                            mParams.put((String) mList.get(position).getAttributeName(), mList.get(position).getVal());
                        } else {
                            textViewHolder.selectName.setText("请选择" + mList.get(position).getCnname());
                            mParams.put((String) mList.get(position).getAttributeName(), "");
                        }
                    }

                }

            } else {
                textViewHolder.selectName.setText("请选择" + mList.get(position).getCnname());
            }


            textViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mList.get(position).getCnname().equals("生日")) {
                        click.birthdayOnClick(textViewHolder, position, StatusVariable.BIRDTHCLICK);
                    } else if (mList.get(position).getCnname().equals("证件类型")) {
                        click.certificateClick(textViewHolder, position, StatusVariable.CERTIFICATE);

                    }
                }
            });

        } else if (viewHolder instanceof PhoneNumberViewHolder) {
            /** 手机号码 ViewHolder*/
            final PhoneNumberViewHolder phoneNumberViewHolder = (PhoneNumberViewHolder) viewHolder;
            if (!EmptyUtil.isEmpty(mList.get(position).getCnname())) {
                // if (mList.get(position).getCnname() != null && !mList.get(position).getCnname().equals("null") && !mList.get(position).getCnname().equals("")) {

                if (mList.get(position).getCnname().equals("手机号码")) {
                    phoneNumberViewHolder.tvPhoneNum.setText(mList.get(position).getCnname());

                    if (mList.get(position).isRequired() == true) {
                        phoneNumberViewHolder.phoneNumberRequird.setVisibility(View.VISIBLE);
                    } else {
                        phoneNumberViewHolder.phoneNumberRequird.setVisibility(View.GONE);
                    }
                    if (!EmptyUtil.isEmpty(mList.get(position).getVal())) {
                        //  if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("null") && !mList.get(position).getVal().equals("")) {
                        phoneNumberViewHolder.etphoneNum.setText(mList.get(position).getVal());
                        phoneNum = mList.get(position).getVal();
                        mParams.put((String) mList.get(position).getAttributeName(), mList.get(position).getVal());

                    } else {
                        phoneNum = "";
                        phoneNumberViewHolder.etphoneNum.setText("");
                        phoneNumberViewHolder.etphoneNum.setHint("请输入手机号");
                        mParams.put((String) mList.get(position).getAttributeName(), "");
                    }


                }

            }

            if (code != null && !code.equals("null") && !code.equals("")) {
                phoneNumberViewHolder.form_verificationCode.setText(code);
            }
            phoneNumberViewHolder.etphoneNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mList.get(position).setVal(editable.toString());
                    phoneNum = editable.toString();
                    mParams.put((String) mList.get(position).getAttributeName(), mList.get(position).getVal());
                }
            });

            phoneNumberViewHolder.form_verificationCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    code = editable.toString();
                }
            });

            //发送验证码
            phoneNumberViewHolder.send_authcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.codeGet(phoneNumberViewHolder, position, StatusVariable.CODEGET);
                }
            });

        } else if (viewHolder instanceof HeadViewHolder) {
            /**头像*/
            final HeadViewHolder editViewHolder = (HeadViewHolder) viewHolder;

        } else {
            /** 输入 ViewHolder*/
            final EditViewHolder editViewHolder = (EditViewHolder) viewHolder;
            if (!EmptyUtil.isEmpty(mList.get(position).getCnname())) {
                // if (mList.get(position).getCnname() != null && !mList.get(position).getCnname().equals("null") && !mList.get(position).getCnname().equals("")) {

                editViewHolder.textView_name.setText(mList.get(position).getCnname());

                if (mList.get(position).isRequired() == true) {
                    editViewHolder.edittext_requird.setVisibility(View.VISIBLE);
                } else {
                    editViewHolder.edittext_requird.setVisibility(View.GONE);
                }
                if (!EmptyUtil.isEmpty(mList.get(position).getVal())) {
                    // if (mList.get(position).getVal() != null && !mList.get(position).getVal().equals("null") && !mList.get(position).getVal().equals("")) {
                    editViewHolder.form_edittext.setText(mList.get(position).getVal());
                    mParams.put(mList.get(position).getAttributeName(), mList.get(position).getVal().trim());
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
                        mParams.put((String) mList.get(position).getAttributeName(), editabledata.toString().trim());
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
        private final RadioButton radioButtonboy;
        private final RadioButton radioButtongirl;
        private LinearLayout ll_sexlayout;

        public SexViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.ll_sexlayout);
            tv_sex = itemView.findViewById(R.id.tv_sex);
            sex_requird = itemView.findViewById(R.id.sex_requird);
            sex_radiogroup = itemView.findViewById(R.id.sex_radiogroup);
            radioButtonboy = itemView.findViewById(R.id.boy);
            radioButtongirl = itemView.findViewById(R.id.girl);
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
        private final LinearLayout ll_phonenumber;

        public PhoneNumberViewHolder(View itemView) {
            super(itemView);
            ll_phonenumber = itemView.findViewById(R.id.ll_phonenumber);
            tvPhoneNum = itemView.findViewById(R.id.tv_phoneNum);//名字
            etphoneNum = itemView.findViewById(R.id.form_userPhoneNum);//输入框
            phoneNumberRequird = itemView.findViewById(R.id.phoneNum_requird);//是否是必填
            send_authcode = itemView.findViewById(R.id.send_Authcode);//发送验证码
            form_verificationCode = itemView.findViewById(R.id.form_VerificationCode);//验证码输入框
            code_requird = itemView.findViewById(R.id.code_requird);//是否必填
        }
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {
        private final TextView item_iv_head;

        public HeadViewHolder(View itemView) {
            super(itemView);
            item_iv_head = itemView.findViewById(R.id.item_iv_head);//头像
        }
    }

    //文本或选择
    public class TextViewHolder extends RecyclerView.ViewHolder {

        private final TextView btntv;
        private final TextView selectName;
        private final TextView text_requird;
        private final LinearLayout ll_datebirth;

        public TextViewHolder(View itemView) {
            super(itemView);
            ll_datebirth = itemView.findViewById(R.id.ll_datebirth);
            btntv = itemView.findViewById(R.id.tv_datebirth);
            selectName = itemView.findViewById(R.id.select_name);
            text_requird = itemView.findViewById(R.id.text_requird);
        }
    }

    //输入
    public class EditViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_name;
        private final EditText form_edittext;
        private final TextView edittext_requird;
        private LinearLayout ll_edittext;

        public EditViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.ll_edittext);
            textView_name = itemView.findViewById(R.id.textView_name);
            form_edittext = itemView.findViewById(R.id.form_edittext);
            edittext_requird = itemView.findViewById(R.id.edittext_requird);
        }
    }

    private String code;
    private String phoneNum;

    public String getCode() {
        return code;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

}
