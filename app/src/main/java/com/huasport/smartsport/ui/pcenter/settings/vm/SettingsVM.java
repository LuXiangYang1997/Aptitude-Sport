package com.huasport.smartsport.ui.pcenter.settings.vm;

import android.content.Intent;

import com.huasport.smartsport.base.BaseViewModel;
import com.huasport.smartsport.custom.CustomDialog;
import com.huasport.smartsport.dialog.BaseDialog;
import com.huasport.smartsport.dialog.DialogCallBack;
import com.huasport.smartsport.ui.pcenter.loginbind.view.LoginActivity;
import com.huasport.smartsport.ui.pcenter.settings.view.SettingsActivity;
import com.huasport.smartsport.util.SharedPreferencesUtil;

public class SettingsVM extends BaseViewModel {

    private SettingsActivity settingsActivity;
    private Intent intent;

    public SettingsVM(SettingsActivity settingsActivity) {
        this.settingsActivity = settingsActivity;
    }

    /**
     * 退出登录
     */
    public void outLogin() {

        BaseDialog.show(settingsActivity, "退出提示", "您确定退出当前账号吗", "确定", "取消", false, false,
                0, new DialogCallBack() {
                    @Override
                    public void submit(CustomDialog.Builder customDialog) {
                        customDialog.dismiss();//退出登录
                        SharedPreferencesUtil.remove(settingsActivity, "UserBean"
                        );
                        Intent intent = new Intent(settingsActivity, LoginActivity.class);
                        settingsActivity.startActivity(intent);
                        settingsActivity.finish();
                    }

                    @Override
                    public void cancel(CustomDialog.Builder customDialog) {
                        customDialog.dismiss();
                    }
                });
    }

    /**
     * 编辑资料
     */
    public void editUserInfo() {

//        intent = new Intent(settingsActivity, PersonalMesssageActivity.class);
//        settingsActivity.startActivity(intent);

    }


}
