package com.huasport.smartsport.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.huasport.smartsport.R;
import com.huasport.smartsport.custom.CustomDialog;


/**
 * 统一风格Dialog
 */
public class BaseDialog {
    /**
     * @param context
     * @param title           标题
     * @param msg             提示信息
     * @param submitText      确定文本
     * @param cancelText      取消文本
     * @param isCancel        点击外部是否可以取消
     * @param isForcedUpgrade 是否是强制，true：无取消按钮 ，false：有取消按钮
     * @param dialogCallBack  按钮回调
     */
    public static void show(Context context, String title, String msg, String submitText, String cancelText, boolean isCancel, boolean isForcedUpgrade, int cancelColor, final DialogCallBack dialogCallBack) {
        //弹出Dialog
        final CustomDialog.Builder customdialog = new CustomDialog.Builder(context);
        if (isForcedUpgrade) {

            customdialog.setCancelable(isCancel);
            customdialog.setGravity(Gravity.CENTER).
                    setContentView(R.layout.force_updata_layout).
                    setText(R.id.tv_content, title).
                    setText(R.id.tv_detailMsg, msg).
                    setOnClickListener(R.id.tv_submit, submitText, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (customdialog != null) {
                                dialogCallBack.submit(customdialog);
                            }
                        }
                    });

        } else {
            customdialog.setCancelable(isCancel);
            customdialog.setGravity(Gravity.CENTER).
                    setContentView(R.layout.dialog_layout).
                    setText(R.id.tv_content, title).
                    setText(R.id.tv_detailMsg, msg).
                    setOnClickListener(R.id.tv_submit, submitText, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (customdialog != null) {
                                dialogCallBack.submit(customdialog);
                            }
                        }
                    }).
                    setOnClickListener(R.id.tv_cancel, cancelText, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (customdialog != null) {
                                dialogCallBack.cancel(customdialog);
                            }
                        }
                    });
        }
        TextView cancelTv = customdialog.getView().findViewById(R.id.tv_cancel);
        if (cancelColor != 0) {
            cancelTv.setTextColor(cancelColor);
        }
        customdialog.setWidth(0.8f).show();

    }


}
