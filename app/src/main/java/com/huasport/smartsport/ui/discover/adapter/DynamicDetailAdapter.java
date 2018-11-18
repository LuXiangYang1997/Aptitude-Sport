package com.huasport.smartsport.ui.discover.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huasport.smartsport.MyApplication;
import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.bean.UserBean;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.ui.discover.bean.CommentFavourBean;
import com.huasport.smartsport.ui.discover.view.DynamicDetailActivity;
import com.huasport.smartsport.ui.discover.view.ReleaseActivity;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicDetailAdapter extends BaseAdapter<CommentFavourBean.ResultBean.DataBean, RecyclerView.ViewHolder> {

    private DynamicDetailActivity dynamicDetailActivity;
    private CommentViewHolder commentViewHolder;
    private FavourViewHolder favourViewHolder;
    private Intent intent;
    private String registerCode;
    public Map mapPos = new HashMap();

    public DynamicDetailAdapter(DynamicDetailActivity dynamicDetailActivity) {
        super(dynamicDetailActivity);
        this.dynamicDetailActivity = dynamicDetailActivity;
        UserBean userBean = MyApplication.getInstance().getUserBean();
        if (!EmptyUtil.isEmpty(userBean)){
            registerCode = userBean.getRegisterCode();
        }
    }


    @Override
    public int getItemViewType(int position) {

        return dynamicDetailActivity.tabPos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View commnetView = LayoutInflater.from(dynamicDetailActivity).inflate(R.layout.comment_itemlayout, parent, false);
            return new CommentViewHolder(commnetView);
        } else {
            View favourView = LayoutInflater.from(dynamicDetailActivity).inflate(R.layout.favour_itemlayout, parent, false);
            return new FavourViewHolder(favourView);
        }
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof CommentViewHolder) {
            commentViewHolder = (CommentViewHolder) viewHolder;
            //时间
            long time = mList.get(position).getCreateTime();
            long fronttimelong = DateUtil.frontDate();//当前时间的前一天时间
            //动态时间
            if (!EmptyUtil.isEmpty(time) && time != 0) {
                String date = DateUtil.DateToStr(time, StatusVariable.HM);
                if (DateUtil.isSameDay(System.currentTimeMillis(), time)) {
                    commentViewHolder.tvTime.setText("今天 " + date);
                } else if (DateUtil.isSameDay(fronttimelong, time)) {
                    commentViewHolder.tvTime.setText("昨天" + date);
                } else {
                    String timeStr = DateUtil.DateToStr(time, StatusVariable.YMD);
                    commentViewHolder.tvTime.setText(timeStr);
                }
            }

            //头像
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterPhoto())) {
                GlideUtil.LoadCircleImage(dynamicDetailActivity, mList.get(position).getRegisterPhoto(), commentViewHolder.imgHeader);
            } else {
                commentViewHolder.imgHeader.setImageResource(R.mipmap.icon_defaultheader_yes);
            }

            //是否是认证 1:是 0：否
            if (!EmptyUtil.isEmpty(mList.get(position).getIsAuth())) {
                if (mList.get(position).getIsAuth().equals(StatusVariable.YESAUTH)) {
                    commentViewHolder.img_headerv.setVisibility(View.VISIBLE);
                } else {
                    commentViewHolder.img_headerv.setVisibility(View.GONE);
                }
            }

            //名字
            String registerNickName = mList.get(position).getRegisterNickName();
            if (!EmptyUtil.isEmpty(registerNickName)) {
                commentViewHolder.tv_name.setText(registerNickName);
            } else {
                commentViewHolder.tv_name.setText("游客");
            }

            //评论详情
            String content = mList.get(position).getContent();
            if (!EmptyUtil.isEmpty(content)) {
                commentViewHolder.tvContent.setText(Util.getHtmlContent(content));
            }

            commentViewHolder.tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dynamicClick.dynamicclick(mList.get(position), position, mList.get(position).getRegisterId());
                }
            });
            //子评论
            final List<CommentFavourBean.ResultBean.DataBean.ReplyInfosBean> replyInfos = mList.get(position).getReplyInfos();

                ommentDataDispose(replyInfos, commentViewHolder, position);

            commentViewHolder.imgHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(dynamicDetailActivity, ReleaseActivity.class);
                    intent.putExtra("registerId", mList.get(position).getRegisterId());
                    dynamicDetailActivity.startActivity(intent);
                }
            });
        } else {
            favourViewHolder = (FavourViewHolder) viewHolder;
            //头像
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterPhoto())) {
                GlideUtil.LoadCircleImage(dynamicDetailActivity, mList.get(position).getRegisterPhoto(), favourViewHolder.imgUserHeader);
            } else {
                favourViewHolder.imgUserHeader.setImageResource(R.mipmap.icon_defaultheader_yes);
            }

            //是否是认证 1:是 0：否
            if (!EmptyUtil.isEmpty(mList.get(position).getIsAuth())) {
                if (mList.get(position).getIsAuth().equals(StatusVariable.YESAUTH)) {
                    favourViewHolder.img_headerv.setVisibility(View.VISIBLE);
                } else {
                    favourViewHolder.img_headerv.setVisibility(View.GONE);
                }
            }

            favourViewHolder.imgUserHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intent = new Intent(dynamicDetailActivity, ReleaseActivity.class);
                    intent.putExtra("registerId", mList.get(position).getRegisterId());
                    dynamicDetailActivity.startActivity(intent);

                }
            });

            //名字
            String registerNickName = mList.get(position).getRegisterNickName();
            if (!EmptyUtil.isEmpty(registerNickName)) {
                favourViewHolder.tv_userName.setText(registerNickName);
            } else {
                favourViewHolder.tv_userName.setText("游客");
            }

        }
    }

    private void ommentDataDispose(final List<CommentFavourBean.ResultBean.DataBean.ReplyInfosBean> replyInfos, final CommentViewHolder commentViewHolder, final int position) {


        if (!EmptyUtil.isEmpty(replyInfos)) {

            if (!mapPos.containsKey(position)) {

                String dyId = dynamicDetailActivity.dynamicRegisterId.get();
                //修改评论数据，本地保存的registerId和评论Id一样的话则是作者回复，否则registerNickName+内容
                for (int i = 0; i < replyInfos.size(); i++) {

                    String registerId = replyInfos.get(i).getRegisterId();
                    if (dyId.equals(registerId)) {

                        replyInfos.get(i).setContent("作者回复：" + Util.getHtmlContent(replyInfos.get(i).getContent()));

                    } else {

                        if (!EmptyUtil.isEmpty(replyInfos.get(i).getRegisterNickName())) {
                            replyInfos.get(i).setContent(replyInfos.get(i).getRegisterNickName() + "回复：" +  Util.getHtmlContent(replyInfos.get(i).getContent()));
                        } else {
                            replyInfos.get(i).setContent("游客回复：" + Util.getHtmlContent(replyInfos.get(i).getContent()));
                        }

                    }
                }
                mapPos.put(position, mList.get(position));
            }
            if (replyInfos.size() > 0) {
                //Linearlayout中如果有view则移除所有
                if (commentViewHolder.ll_tvView.getChildCount() > 0) {
                    commentViewHolder.ll_tvView.removeAllViews();
                }
                //显示评论布局
                commentViewHolder.ll_review.setVisibility(View.VISIBLE);
                //初始化三个TextView
                final TextView text_view_1 = new TextView(dynamicDetailActivity);
                final TextView text_view_2 = new TextView(dynamicDetailActivity);
                final TextView text_view_3 = new TextView(dynamicDetailActivity);
                text_view_1.setId(R.id.text_view_1);
                text_view_1.setMaxLines(3);
                text_view_1.setEllipsize(TextUtils.TruncateAt.END);
                text_view_1.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_333333));
                text_view_1.setTextSize(14);

                text_view_2.setId(R.id.text_view_2);
                text_view_2.setEllipsize(TextUtils.TruncateAt.END);
                text_view_2.setMaxLines(3);
                text_view_2.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_333333));
                text_view_2.setTextSize(14);

                text_view_3.setId(R.id.text_view_3);
                text_view_3.setEllipsize(TextUtils.TruncateAt.END);
                text_view_3.setMaxLines(3);
                text_view_3.setTextColor(dynamicDetailActivity.getResources().getColor(R.color.color_333333));
                text_view_3.setTextSize(14);


                if (replyInfos.size() == 1) {
                    text_view_1.setText(replyInfos.get(0).getContent());
                } else if (replyInfos.size() == 2) {
                    text_view_1.setText(replyInfos.get(0).getContent());
                    text_view_2.setText(replyInfos.get(1).getContent());
                } else if (replyInfos.size() > 2) {
                    text_view_1.setText(replyInfos.get(0).getContent());
                    text_view_2.setText(replyInfos.get(1).getContent());
                    text_view_3.setText(replyInfos.get(2).getContent());
                }

                text_view_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dynamicReplyInfoClick.dynamicReplyInfoClick(replyInfos.get(0), mList.get(position).getId(), mList.get(position).getRegisterId());
                    }
                });
                text_view_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dynamicReplyInfoClick.dynamicReplyInfoClick(replyInfos.get(1), mList.get(position).getId(), mList.get(position).getRegisterId());
                    }
                });

                text_view_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dynamicReplyInfoClick.dynamicReplyInfoClick(replyInfos.get(2), mList.get(position).getId(), mList.get(position).getRegisterId());
                    }
                });

                commentViewHolder.tv_look_childcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Intent intent = new Intent(dynamicDetailActivity, ReplyActivity.class);
//                        mapPos.clear();
//                        intent.putExtra("replyBean", (Serializable) mList.get(position));
//                        intent.putExtra("dynamicId", dynamicDetailActivity.dynamicRegisterId.get());
//                        intent.putExtra("isOneSelf", dynamicDetailActivity.isOneSelf.get());
//                        dynamicDetailActivity.startActivityForResult(intent, 0);
                    }
                });
                //添加第一个TextView
                commentViewHolder.ll_tvView.addView(text_view_1);
                text_view_1.post(new Runnable() {
                    @Override
                    public void run() {
                        //获取第一个Textview的行数
                        int lineCount = text_view_1.getLineCount();
                        //大于等于三行则显示查看全部，并且不添加新View，等于2行则添加第二个View并显示查看全部，等于1行则添加第二个TextView并判断是否大于2行，大于则不添加View，否则添加第三个View
                        if (lineCount >= 3) {
                            text_view_1.setMaxLines(3);
                            commentViewHolder.tv_look_childcomment.setVisibility(View.VISIBLE);
                            commentViewHolder.tv_look_childcomment.setText("查看全部" + mList.get(position).getReplyCount() + "条回复");
                        } else if (lineCount == 2 && !EmptyUtil.isEmpty(text_view_2.getText().toString())) {
                            text_view_2.setMaxLines(1);
                            commentViewHolder.ll_tvView.addView(text_view_2);
                            commentViewHolder.tv_look_childcomment.setVisibility(View.VISIBLE);
                            commentViewHolder.tv_look_childcomment.setText("查看全部" + mList.get(position).getReplyCount() + "条回复");
                        } else if (lineCount == 1 && !EmptyUtil.isEmpty(text_view_2.getText().toString())) {
                            commentViewHolder.ll_tvView.addView(text_view_2);
                            text_view_2.post(new Runnable() {
                                @Override
                                public void run() {
                                    int lineCount1 = text_view_2.getLineCount();
                                    if (lineCount1 >= 2) {
                                        text_view_2.setMaxLines(2);
                                        commentViewHolder.tv_look_childcomment.setVisibility(View.VISIBLE);
                                        commentViewHolder.tv_look_childcomment.setText("查看全部" + mList.get(position).getReplyCount() + "条回复");
                                    } else if (lineCount1 < 2 && !EmptyUtil.isEmpty(text_view_3.getText().toString())) {
                                        text_view_3.setMaxLines(1);
                                        commentViewHolder.ll_tvView.addView(text_view_3);
                                        commentViewHolder.tv_look_childcomment.setVisibility(View.VISIBLE);
                                        commentViewHolder.tv_look_childcomment.setText("查看全部" + mList.get(position).getReplyCount() + "条回复");
                                    } else {
                                        commentViewHolder.tv_look_childcomment.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            commentViewHolder.tv_look_childcomment.setVisibility(View.GONE);
                        }

                    }
                });
            } else {
                commentViewHolder.ll_review.setVisibility(View.GONE);
            }
        } else {
            commentViewHolder.ll_review.setVisibility(View.GONE);
        }

    }


    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgHeader;
        private final TextView tv_name;
        private final TextView tvTime;
        private final TextView tvContent;
        private final ImageView img_headerv;
        private final LinearLayout ll_review;
        private final TextView tv_look_childcomment;
        private final LinearLayout ll_tvView;
        private final View ll_commentlayout;

        public CommentViewHolder(View itemView) {
            super(itemView);
            imgHeader = itemView.findViewById(R.id.img_comment_headerImg);
            tv_name = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvContent = itemView.findViewById(R.id.tv_content);
            img_headerv = itemView.findViewById(R.id.img_headerv);
            ll_review = itemView.findViewById(R.id.ll_review);
            tv_look_childcomment = itemView.findViewById(R.id.tv_look_childcomment);
            ll_tvView = itemView.findViewById(R.id.ll_tvView);
            ll_commentlayout = itemView.findViewById(R.id.ll_commentlayout);
        }
    }

    public class FavourViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgUserHeader;
        private final ImageView img_headerv;
        private final TextView tv_userName;

        public FavourViewHolder(View itemView) {
            super(itemView);
            img_headerv = itemView.findViewById(R.id.img_headerv);
            imgUserHeader = itemView.findViewById(R.id.img_user_headerImg);
            tv_userName = itemView.findViewById(R.id.tv_user_name);


        }
    }


    public interface DynamicClick {

        void dynamicclick(CommentFavourBean.ResultBean.DataBean dataBean, int position, String id);

    }

    DynamicClick dynamicClick;

    public void setDynamicClick(DynamicClick dynamicClick) {
        this.dynamicClick = dynamicClick;
    }

    public interface DynamicReplyInfoClick {

        void dynamicReplyInfoClick(CommentFavourBean.ResultBean.DataBean.ReplyInfosBean dataBean, String s, String id);

    }

    DynamicReplyInfoClick dynamicReplyInfoClick;

    public void setDynamicReplyInfoClick(DynamicReplyInfoClick dynamicReplyInfoClick) {
        this.dynamicReplyInfoClick = dynamicReplyInfoClick;
    }

}
