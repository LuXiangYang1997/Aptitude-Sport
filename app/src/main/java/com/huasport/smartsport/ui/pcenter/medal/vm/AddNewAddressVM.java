package com.huasport.smartsport.ui.pcenter.medal.vm;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.api.Method;
import com.huasport.smartsport.api.OkHttpUtil;
import com.huasport.smartsport.api.RequestCallBack;
import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.LoadingDialog;
import com.huasport.smartsport.databinding.AddnewaddressLayoutBinding;
import com.huasport.smartsport.ui.pcenter.attention.bean.AddressBean;
import com.huasport.smartsport.ui.pcenter.medal.bean.AddAddressResultBean;
import com.huasport.smartsport.ui.pcenter.medal.bean.RegionBean;
import com.huasport.smartsport.ui.pcenter.medal.view.AddNewAddressActivity;
import com.huasport.smartsport.util.Config;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.huasport.smartsport.util.Util;
import com.huasport.smartsport.util.counter.Counter;
import com.huasport.smartsport.util.counter.CounterListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;


public class AddNewAddressVM extends BaseViewModel implements CounterListener {

    private AddNewAddressActivity addNewAddressActivity;
    public ObservableField<String> consignee = new ObservableField<>("");//收货人
    public ObservableField<String> phoneNumber = new ObservableField<>("");//联系电话
    public ObservableField<String> province = new ObservableField<>("");//省
    public ObservableField<String> city = new ObservableField<>("");//市
    public ObservableField<String> area = new ObservableField<>("");//区
    public ObservableField<Integer> default_address = new ObservableField<>(0);//是否是默认地址
    public ObservableField<String> detail_address = new ObservableField<>("");//详细地址
    public ObservableField<String> addressCode = new ObservableField<>("");//code
    private AddressBean.ResultBean.ListBean addressBean;
    private AddnewaddressLayoutBinding addnewaddressLayoutBinding;
    private String token;
    private String addressType;
    private ArrayList<RegionBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ToastUtil toastUtil;
    private LoadingDialog loadingDialog;
    private Counter counter;

    public AddNewAddressVM(AddNewAddressActivity addNewAddressActivity) throws JSONException {
        this.addNewAddressActivity = addNewAddressActivity;
        addnewaddressLayoutBinding = addNewAddressActivity.getBinding();
        init();
        initData();
        initJsonData();
    }

    private void init() {
        //初始化Toast
        toastUtil = new ToastUtil(addNewAddressActivity);
        //初始化加载框
        loadingDialog = new LoadingDialog(addNewAddressActivity, R.style.LoadingDialog);
        //初始化Counter
        counter = new Counter(this, 1);
        //获取token
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)) {
            token = userBean.getToken();
        }
        //弹出加载框
        loadingDialog.show();
    }

    private void initData() {

        Intent intent = addNewAddressActivity.getIntent();
        addressType = intent.getStringExtra("addressType");
        if (addressType.equals("modifyAddress")) {
            addressBean = (AddressBean.ResultBean.ListBean) intent.getSerializableExtra("AddressBean");
            addressCode.set(addressBean.getAddressCode());
            consignee.set(addressBean.getRealname());
            phoneNumber.set(addressBean.getMobile());
            province.set(addressBean.getProvince());
            city.set(addressBean.getCity());
            area.set(addressBean.getArea());
            detail_address.set(addressBean.getAddress());
            addnewaddressLayoutBinding.tvAddressCity.setText(province.get() + "-" + city.get() + "-" + area.get());
            if (addressBean.isIsdefault()) {
                addnewaddressLayoutBinding.cbDefault.setChecked(true);
                default_address.set(1);
            } else {
                addnewaddressLayoutBinding.cbDefault.setChecked(false);
                default_address.set(0);
            }

        }
        addnewaddressLayoutBinding.cbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addnewaddressLayoutBinding.cbDefault.setChecked(true);
                    default_address.set(1);
                } else {
                    addnewaddressLayoutBinding.cbDefault.setChecked(false);
                    default_address.set(0);
                }
            }
        });
    }

    //添加或修改地址
    public void addAddress() {

        if (addressType.equals("modifyAddress")) {

            if (EmptyUtil.isEmpty(addressCode.get())) {
                toastUtil.centerToast("地址编码不能为空");
                return;
            }
        }
        if (EmptyUtil.isEmpty(consignee.get())) {
            toastUtil.centerToast( "请填写收货人姓名");
            return;
        }
        if (EmptyUtil.isEmpty(phoneNumber.get())) {
            toastUtil.centerToast( "请填写收货人联系方式");
            return;
        }

        if (!EmptyUtil.isEmpty(province.get()) || !EmptyUtil.isEmpty(city.get()) || EmptyUtil.isEmpty(area.get())) {

        } else {
            toastUtil.centerToast( "请选择收货地址");
        }
        if (EmptyUtil.isEmpty(detail_address.get())) {
            toastUtil.centerToast("请填写详细地址");
            return;
        }
        if (!Util.isPhoneNumber(phoneNumber.get())) {
            toastUtil.centerToast(" 请填写正确的收货人联系方式");
            return;

        }
        HashMap params = new HashMap();
        params.put("addressCode", addressCode.get());
        params.put("token", token);
        params.put("realname", consignee.get());
        params.put("mobile", phoneNumber.get());
        params.put("province", province.get());
        params.put("city", city.get());
        params.put("area", area.get());
        params.put("address", detail_address.get());
        params.put("isdefault", default_address.get() + "");
        params.put("baseMethod", Method.MODIFYADDRESS);
        params.put("baseUrl", Config.baseUrl);


        OkHttpUtil.postRequest(addNewAddressActivity, params, new RequestCallBack<AddAddressResultBean>() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<AddAddressResultBean> response) {
                AddAddressResultBean addAddressResultBean = response.body();
                if (!EmptyUtil.isEmpty(addAddressResultBean)){
                    int resultCode = addAddressResultBean.getResultCode();
                    if (resultCode == StatusVariable.REQUESTSUCCESS){
                        addNewAddressActivity.finish();
                    }
                }
            }

            @Override
            public AddAddressResultBean parseNetworkResponse(String jsonResult) {
                AddAddressResultBean addAddressResultBean = JSON.parseObject(jsonResult, AddAddressResultBean.class);
                return addAddressResultBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    public void selectCisty() {

        OptionsPickerView pickerView = new OptionsPickerView.Builder(addNewAddressActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() + "-" +
                        options2Items.get(options1).get(options2) + "-" +
                        options3Items.get(options1).get(options2).get(options3);
                if (!TextUtils.isEmpty(tx)) {
                    String spStr1[] = tx.split("-");
                    province.set(spStr1[0]);
                    city.set(spStr1[1]);
                    area.set(spStr1[2]);
                    addnewaddressLayoutBinding.tvAddressCity.setText(tx);

                } else {
                    province.set("");
                    city.set("");
                    area.set("");
                }
            }
        }).setTitleText("")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setContentTextSize(16)
                .build();

        pickerView.setPicker(options1Items, options2Items, options3Items);
        pickerView.show();

    }


    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String jsonData = Util.getJson("china_city_data.json", addNewAddressActivity);
        ArrayList<RegionBean> jsonBean = parseData(jsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<RegionBean> parseData(String result) {//Gson 解析
        ArrayList<RegionBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                RegionBean entity = gson.fromJson(data.optJSONObject(i).toString(), RegionBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    public void countEnd(boolean isEnd) {
        if (isEnd){
            if (!EmptyUtil.isEmpty(loadingDialog)){
                loadingDialog.dismiss();
            }
        }
    }
}
