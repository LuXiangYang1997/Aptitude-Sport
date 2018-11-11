package com.huasport.smartsport.dialog;

import com.huasport.smartsport.custom.CustomDialog;

public interface DialogCallBack {
    /**
     * 确定按钮的回调
     * @param customDialog
     */
    void submit(CustomDialog.Builder customDialog);

    /**
     * 取消按钮的回调
     * @param customDialog
     */
    void cancel(CustomDialog.Builder customDialog);

}
