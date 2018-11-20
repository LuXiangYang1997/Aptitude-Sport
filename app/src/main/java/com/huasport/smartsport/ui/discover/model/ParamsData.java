package com.huasport.smartsport.ui.discover.model;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.huasport.smartsport.ui.matchapply.bean.AddMemberInitializeBean;
import com.huasport.smartsport.ui.matchapply.bean.AthletesMessageBean;
import com.huasport.smartsport.ui.matchapply.bean.PersonalInfoBean;
import com.huasport.smartsport.ui.matchapply.view.FillRegistrationFormActivity;
import com.huasport.smartsport.ui.matchapply.vm.AdditionMemberVM;
import com.huasport.smartsport.ui.matchapply.vm.FillRegistrationFormVM;
import com.huasport.smartsport.ui.matchapply.vm.RegistrationInformationVM;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.ToastUtil;

import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/25.
 */
//判断必填选项是否为空
public class ParamsData {

    private Context context;
    private final ToastUtil toastUtil;

    public ParamsData(Context context) {
        this.context = context;
        toastUtil = new ToastUtil(context);
    }

    public  boolean getParams(FillRegistrationFormActivity fillRegistrationFormActivity, List<AthletesMessageBean.ResultBean.PropertiesBean> propertiesBeans, View view, FillRegistrationFormVM fillRegistrationFormVM) {
        boolean isNull = false;
        for (AthletesMessageBean.ResultBean.PropertiesBean propertiesBean : propertiesBeans) {
            if (propertiesBean.isIsRequired() && EmptyUtil.isEmpty(propertiesBean.getVal())) {

                toastUtil.showTopSnackBar(propertiesBean.getCnname() + "不能为空");
                isNull = false;
                break;
            } else {
                if (propertiesBean.getCnname().equals("证件类型")) {
                    if (propertiesBean.getVal() != null) {
                        if (propertiesBean.getVal().equals("身份证")) {
                            Log.e("FrontImage", fillRegistrationFormVM.frontImgstr.get());
                            if (EmptyUtil.isEmpty(fillRegistrationFormVM.frontImgstr.get())) {
                                toastUtil.showTopSnackBar("请上传身份证正面");
                                isNull = false;
                                break;
                            } else if (EmptyUtil.isEmpty(fillRegistrationFormVM.contraryImgstr.get())) {
                                toastUtil.showTopSnackBar("请上传身份证反面");
                                isNull = false;
                                break;
                            }
                        } else if (propertiesBean.getVal().equals("护照")) {

                            if (EmptyUtil.isEmpty(fillRegistrationFormVM.frontImgstr.get())) {
                                toastUtil.showTopSnackBar("请上传护照");
                                isNull = false;
                                break;
                            }


                        } else if (propertiesBean.getVal().equals("军官证")) {
                            if (EmptyUtil.isEmpty(fillRegistrationFormVM.frontImgstr.get())) {
                                toastUtil.showTopSnackBar("请上传军官证");
                                isNull = false;
                                break;
                            }
                        }

                    }

                } else {
                    isNull = true;
                }
            }

        }
        return isNull;
    }

    public  boolean getParam(List<PersonalInfoBean> beans, RegistrationInformationVM registrationInformationVM) {
        boolean isNull = false;
        for (PersonalInfoBean bean : beans) {

            if (bean.isRequired() && EmptyUtil.isEmpty(bean.getVal())) {
                toastUtil.showTopSnackBar(bean.getCnname() + "不能为空");
                isNull = false;
                break;

            } else {
                if (bean.getCnname().equals("证件类型")) {
                    if (bean.getVal() != null) {
                        if (bean.getVal().equals("身份证")) {
                            Log.e("FrontImage", registrationInformationVM.frontImgstr.get());
                            if (EmptyUtil.isEmpty(registrationInformationVM.frontImgstr.get())) {
                                toastUtil.showTopSnackBar("请上传身份证正面");
                                isNull = false;
                                break;
                            } else if (EmptyUtil.isEmpty(registrationInformationVM.contraryImgstr.get())) {
                                toastUtil.showTopSnackBar("请上传身份证反面");
                                isNull = false;
                                break;
                            }
                        } else if (bean.getVal().equals("护照")) {

                            if (EmptyUtil.isEmpty(registrationInformationVM.frontImgstr.get())) {
                                toastUtil.showTopSnackBar("请上传护照");
                                isNull = false;
                                break;
                            }


                        } else if (bean.getVal().equals("军官证")) {
                            if (EmptyUtil.isEmpty(registrationInformationVM.frontImgstr.get())) {
                                toastUtil.showTopSnackBar("请上传军官证");
                                isNull = false;
                                break;
                            }
                        }

                    }

                } else {
                    isNull = true;
                }

            }
        }
        return isNull;
    }

    public  boolean groupParams(List<AddMemberInitializeBean.ResultBean.PropertiesBean> propertiesBeans, AdditionMemberVM additionMemberVM) {
        boolean isNull = false;
        for (AddMemberInitializeBean.ResultBean.PropertiesBean propertiesBean : propertiesBeans) {
            if (propertiesBean.isIsRequired() && EmptyUtil.isEmpty(propertiesBean.getVal())) {
                toastUtil.showTopSnackBar(propertiesBean.getCnname() + "不能为空");
                isNull = false;
                break;
            } else {
                if (propertiesBean.getCnname().equals("证件类型")) {
                    if (propertiesBean.getVal() != null) {
                        if (propertiesBean.getVal().equals("身份证")) {
                            Log.e("FrontImage", additionMemberVM.imgOne.get());
                            if (EmptyUtil.isEmpty(additionMemberVM.imgOne.get())) {
                                toastUtil.showTopSnackBar("请上传身份证正面");
                                isNull = false;
                                break;
                            } else if (EmptyUtil.isEmpty(additionMemberVM.imgTwo.get())) {
                                toastUtil.showTopSnackBar("请上传身份证反面");
                                isNull = false;
                                break;
                            }
                        } else if (propertiesBean.getVal().equals("护照")) {

                            if (EmptyUtil.isEmpty(additionMemberVM.imgOne.get())) {
                                toastUtil.showTopSnackBar("请上传护照");
                                isNull = false;
                                break;
                            }


                        } else if (propertiesBean.getVal().equals("军官证")) {
                            if (EmptyUtil.isEmpty(additionMemberVM.imgOne.get())) {
                                toastUtil.showTopSnackBar("请上传军官证");
                                isNull = false;
                                break;
                            }
                        }

                    }

                } else {
                    isNull = true;
                }
            }

        }
        return isNull;
    }


}
