package com.huasport.smartsport.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.util.LogUtil;
import com.huasport.smartsport.util.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付回调页面
 * Created by lwd on 2018/6/22.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private ToastUtil toastUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e( "WXPayEntryActivity onCreate");
        api = WXAPIFactory.createWXAPI(this, ThirdPart.WX_APPID);
        api.handleIntent(getIntent(), this);
        toastUtil = new ToastUtil(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.e("WXPayEntryActivity baseResp.errCode:" + baseResp.errCode);
        String tip = "";
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case 0://成功
                    tip = "支付成功";
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent();
                    intent.setAction("WXPaySuccess");
                    intent.putExtra(StatusVariable.PAYSUCCESS, "WXPaySuccess");
                    finish();
                    this.sendBroadcast(intent);
//                    ToastUtils.toast(this, baseResp.errCode + "");
//                    PayStatusBean payStatusBean = new PayStatusBean();
//                    payStatusBean.setPayStatus(StatusVariable.PAYSUCCESS);
//                    RxBus.getDefault().post(StatusVariable.PAYSUCCESS);
//                    RxBus.getDefault().post(new RxBusMessage(RxBusMessage.RxBusFlag.RXBUS_WX_PAYSUCCESS));
                    break;
                case -1://错误
                    tip = "支付错误";
                    break;
                case -2://用户取消
                    tip = "取消支付";
                    break;
                default:
                    tip = "支付失败";
                    break;
            }
        }
        this.finish();
        toastUtil.centerToast(tip);
        overridePendingTransition(0, 0);

    }
}
