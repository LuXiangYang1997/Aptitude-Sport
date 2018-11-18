package com.huasport.smartsport.util.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huasport.smartsport.R;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.custom.ChildViewPager;
import com.huasport.smartsport.ui.discover.adapter.DynamicBigImageAdapter;
import com.huasport.smartsport.ui.discover.bean.PicBean;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.SoftKeyBoardListener;
import com.huasport.smartsport.util.Util;

import java.util.List;

public class PopWindowUtil {

    private static String nickName = "";
    private static View view;
    private static String edittext = "";
    private static int mWidth;
    private static int mHeight;

    /**
     * 选择图片弹出框
     * @param context
     * @param selectPicCallBack
     */
    public static void selectPicture(final Context context, final SelectPicCallBack selectPicCallBack) {

        View popView = LayoutInflater.from(context).inflate(R.layout.select_picture_layout, null);

        final PopupWindow popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Util.backgroundAlpha(context, 0.5f);

        popupWindow.setOutsideTouchable(true);//点击外部消失
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);

        TextView tv_camera = popView.findViewById(R.id.tv_camera);//拍照
        TextView tv_photo = popView.findViewById(R.id.tv_photo);//相册
        TextView tv_cancel = popView.findViewById(R.id.tv_cancel);//取消

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPicCallBack.camera(popupWindow, StatusVariable.CAMERACODE);

            }
        });
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPicCallBack.photo(popupWindow, StatusVariable.PHOTOCODE);

            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPicCallBack.cancel(popupWindow);

            }
        });

        //popwindow消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                Util.backgroundAlpha(context, 1f);

            }
        });
    }

    /**
     * 发现-发布点击弹出框
     * @param context
     * @param textView
     * @param releaseCallBack
     */
    public static void releaseClick(final Context context, TextView textView, final ReleaseCallBack releaseCallBack) {

        View releaseView = LayoutInflater.from(context).inflate(R.layout.release_layout, null);

        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setBackgroundDrawable(new ColorDrawable());

        popupWindow.setFocusable(true);

        popupWindow.setTouchable(true);

        popupWindow.setOutsideTouchable(true);

        popupWindow.setContentView(releaseView);

        popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int width = popupWindow.getContentView().getMeasuredWidth();

        popupWindow.showAsDropDown(textView, textView.getWidth() - width, 0);

        Util.backgroundAlpha(context, 0.5f);

        //动态
        releaseView.findViewById(R.id.tv_dynamic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseCallBack.dynamic(popupWindow);
            }
        });
        //文章
        releaseView.findViewById(R.id.tv_article).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseCallBack.article(popupWindow);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(context, 1.0f);
            }
        });

    }

    /**
     * 分享
     * @param context
     * @param shareCallBack
     */
    public static void share(final Context context, final ShareCallBack shareCallBack) {

        View shareView = LayoutInflater.from(context).inflate(R.layout.discover_share_layout, null, false);
        final PopupWindow sharePop = new PopupWindow(shareView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sharePop.setContentView(shareView);
        sharePop.showAtLocation(shareView, Gravity.BOTTOM, 0, 0);
        sharePop.setOutsideTouchable(false);
        Util.backgroundAlpha(context, 0.5f);

        //微信好友分享
        shareView.findViewById(R.id.ll_social_shareWechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareCallBack.weChatFriendsShare(sharePop);

            }
        });


        //微信朋友圈分享
        shareView.findViewById(R.id.ll_social_sharefriend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareCallBack.wechatQuanShare(sharePop);

            }
        });


        shareView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePop.dismiss();
            }
        });

        sharePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(context, 1f);
            }
        });

    }

    /**
     * 发布评论弹出框
     * @param context
     */
    public static void commandPop(final Context context,final CommandCallback commandCallback) {

        view = LayoutInflater.from(context).inflate(R.layout.comment_edittext_layout, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final EditText commentEdittext = view.findViewById(R.id.edit_command);
        if (!EmptyUtil.isEmpty(edittext)) {
            commentEdittext.setText(edittext);
        } else {
            commentEdittext.setText("");
        }
        TextView comment_send = view.findViewById(R.id.tv_command_send);

        comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commandCallback.releaseCommand(popupWindow,commentEdittext.getText().toString(),commentEdittext);
            }
        });

        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setBackgroundDrawable(new ColorDrawable());

        popupWindow.setFocusable(true);

        popupWindow.setTouchable(true);

        popupWindow.setOutsideTouchable(false);

//        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);//防止遮挡edittext

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setContentView(view);

        commentEdittext.requestFocus();

        commentEdittext.setHint("评论");

        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);


        if (commentEdittext.hasFocus()) {
            Util.softBoard(context);
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()

        {
            @Override
            public void onDismiss() {

                edittext = commentEdittext.getText().toString();

            }
        });

        //初始化监听软键盘显示隐藏状态
        Activity activity = (Activity) context;
        SoftKeyBoardListener.setListener(activity, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });


    }

    /**
     * 查看大图
     * @param context
     * @param picsBeansList
     * @param pos
     */
    public static void lookBigImg(Context context, List<PicBean> picsBeansList, int pos){

    View imgView = LayoutInflater.from(context).inflate(R.layout.bigimg_layout, null, false);

       final PopupWindow popupWindow = new PopupWindow(imgView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                true);
        final TextView tv_position = imgView.findViewById(R.id.tv_position);
        TextView tv_size = imgView.findViewById(R.id.tv_size);
        //初始化数显
        tv_size.setText(picsBeansList.size() + "");
        tv_position.setText((pos + 1) + "");
        ChildViewPager img_viewpager = imgView.findViewById(R.id.img_viewpager);
        //设置adapter
        DynamicBigImageAdapter bigImageAdapter = new DynamicBigImageAdapter(context);
        bigImageAdapter.loadData(picsBeansList);
        popupWindow.setTouchable(true);
        img_viewpager.setAdapter(bigImageAdapter);

        img_viewpager.setCurrentItem(pos);

        //viewpager切换监听
        img_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_position.setText((position + 1) + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        img_viewpager.setOnSingleTouchListener(new ChildViewPager.OnSingleTouchListener() {
            @Override
            public void onSingleTouch() {
                popupWindow.dismiss();
            }
        });

        popupWindow.setClippingEnabled(false);
        popupWindow.showAtLocation(imgView, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);

        mWidth = imgView.getWidth();
        mHeight = imgView.getHeight();

        //在Android 6.0以上 ，只能通过拦截事件来解决返回键关闭popWindown
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int x = (int) event.getX();
                final int y = (int) event.getY();

                if ((event.getAction() == MotionEvent.ACTION_DOWN)
                        && ((x < 0) || (x >= mWidth) || (y < 0) || (y >= mHeight))) {
                    // donothing
                    // 消费事件
                    return false;
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    return true;
                }
                return false;
            }

        });

    }

}
