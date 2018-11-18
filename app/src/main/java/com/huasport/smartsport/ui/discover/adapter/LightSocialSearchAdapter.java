package com.huasport.smartsport.ui.discover.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huasport.smartsport.R;
import com.huasport.smartsport.base.BaseAdapter;
import com.huasport.smartsport.constant.StatusVariable;
import com.huasport.smartsport.ui.discover.bean.LightSocialSearchBean;
import com.huasport.smartsport.ui.discover.view.ArticleDetailActivity;
import com.huasport.smartsport.ui.discover.view.DynamicDetailActivity;
import com.huasport.smartsport.ui.discover.view.LightSocialSearchActivity;
import com.huasport.smartsport.ui.discover.view.ReleaseActivity;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.ScreenUtil;
import com.huasport.smartsport.util.Util;

import java.util.List;


public class LightSocialSearchAdapter extends BaseAdapter<LightSocialSearchBean.ResultBean.ListBean, RecyclerView.ViewHolder> {

    private LightSocialSearchActivity lightSocialSearchActivity;
    private LightSocialDynamicViewHolder dynamicViewHolder;
    private LightSocialArticleViewHolder articleViewHolder;
    private LightSocialUserViewHolder userViewHolder;
    private Intent intent;

    public LightSocialSearchAdapter(LightSocialSearchActivity lightSocialSearchActivity) {
        super(lightSocialSearchActivity);
        this.lightSocialSearchActivity = lightSocialSearchActivity;
    }

    @Override
    public int getItemViewType(int position) {

        if (mList.get(position).getType().equals(StatusVariable.DYNAMICSTR)) {
            return StatusVariable.DYNAMIC;
        } else if (mList.get(position).getType().equals(StatusVariable.ARTICLESTR)) {
            return StatusVariable.ARTICLE;
        } else {
            return StatusVariable.USER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        if (viewType == StatusVariable.DYNAMIC) {
            View dynamicView = LayoutInflater.from(lightSocialSearchActivity).inflate(R.layout.lightsocial_dynamiclayout, parent, false);
            return new LightSocialDynamicViewHolder(dynamicView);
        } else if (viewType == StatusVariable.ARTICLE) {
            View articleView = LayoutInflater.from(lightSocialSearchActivity).inflate(R.layout.lightsocial_articlelayout, parent, false);
            return new LightSocialArticleViewHolder(articleView);
        } else {
            View userView = LayoutInflater.from(lightSocialSearchActivity).inflate(R.layout.lightsocial_userlayout, parent, false);
            return new LightSocialUserViewHolder(userView);
        }
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof LightSocialDynamicViewHolder) {
            dynamicViewHolder = (LightSocialDynamicViewHolder) viewHolder;

            if (!EmptyUtil.isEmpty(mList.get(position).getIsLike())) {
                if (mList.get(position).getIsLike().equals("0")) {
                    dynamicViewHolder.img_zan.setImageResource(R.mipmap.icon_grayzan);
                    dynamicViewHolder.tv_fansCount.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_999999));
                } else {
                    dynamicViewHolder.img_zan.setImageResource(R.mipmap.icon_lightzan);
                    dynamicViewHolder.tv_fansCount.setTextColor(lightSocialSearchActivity.getResources().getColor(R.color.color_FF8F00));
                }
            }

            //名字
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterNickName())) {
                dynamicViewHolder.name.setText(mList.get(position).getRegisterNickName());
            } else {
                dynamicViewHolder.name.setText("游客");
            }
            //头像
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterPhoto())) {
                GlideUtil.LoadCircleImage(lightSocialSearchActivity, (String) mList.get(position).getRegisterPhoto(), dynamicViewHolder.headerImg);
            } else {
                dynamicViewHolder.headerImg.setImageResource(R.mipmap.icon_defaultheader_yes);

            }

            //是否是认证 1:是 0：否
            if (!EmptyUtil.isEmpty(mList.get(position).getIsAuth())) {
                if (mList.get(position).getIsAuth().equals(StatusVariable.YESAUTH)) {
                    dynamicViewHolder.img_v.setVisibility(View.VISIBLE);
                } else {
                    dynamicViewHolder.img_v.setVisibility(View.GONE);
                }
            }
            //是否是精品 0：不是 1：是
            if (!EmptyUtil.isEmpty(mList.get(position).getSplendidStatic())) {
                if (mList.get(position).getSplendidStatic().equals(StatusVariable.SPLENDIDSTATIC)) {
                    dynamicViewHolder.sift.setVisibility(View.VISIBLE);
                } else {
                    dynamicViewHolder.sift.setVisibility(View.GONE);
                }
            }
            String produce = mList.get(position).getContent();
            long time = mList.get(position).getCreateTime();
            long fronttimelong = DateUtil.frontDate();//当前时间的前一天时间
            String posStr = (String) mList.get(position).getPosition();

            //动态时间
            if (!EmptyUtil.isEmpty(time)) {
                String date = DateUtil.DateToStr(time, StatusVariable.HM);
                if (DateUtil.isSameDay(System.currentTimeMillis(), time)) {
                    if (!EmptyUtil.isEmpty(posStr)) {
                        dynamicViewHolder.tv_sifttime.setText("今天 " + date + " " + posStr);
                    } else {
                        dynamicViewHolder.tv_sifttime.setText("今天 " + date);
                    }
                } else if (DateUtil.isSameDay(fronttimelong, time)) {
                    if (!EmptyUtil.isEmpty(posStr)) {
                        dynamicViewHolder.tv_sifttime.setText("昨天" + date + " " + posStr);
                    } else {
                        dynamicViewHolder.tv_sifttime.setText("昨天" + date);
                    }

                } else {
                    String timeStr = DateUtil.DateToStr(time, StatusVariable.YMD);
                    if (!EmptyUtil.isEmpty(timeStr)) {
                        if (!EmptyUtil.isEmpty(posStr)) {
                            dynamicViewHolder.tv_sifttime.setText(timeStr + " " + posStr);
                        } else {
                            dynamicViewHolder.tv_sifttime.setText(timeStr);
                        }
                    } else {
                        dynamicViewHolder.tv_sifttime.setText(posStr);
                    }


                }
            } else {
                if (!EmptyUtil.isEmpty(posStr)) {
                    dynamicViewHolder.tv_sifttime.setText(posStr);
                }
            }
            //介绍
            if (!EmptyUtil.isEmpty(produce)) {
                dynamicViewHolder.tv_detail.setVisibility(View.VISIBLE);
                String str = Util.reMoveLast(produce);
                if (!EmptyUtil.isEmpty(str)) {
                    dynamicViewHolder.tv_detail.setText(str);
                }
            } else {
                dynamicViewHolder.tv_detail.setVisibility(View.GONE);
            }
            //分享次数
            if (!EmptyUtil.isEmpty(mList.get(position).getShareCount())) {
                dynamicViewHolder.tv_shareCount.setText(mList.get(position).getShareCount() + "");
            } else {
                dynamicViewHolder.tv_shareCount.setText("0");
            }
            //赞次数
            if (!EmptyUtil.isEmpty(mList.get(position).getLikesCount())) {
                dynamicViewHolder.tv_fansCount.setText(mList.get(position).getLikesCount() + "");
            } else {
                dynamicViewHolder.tv_fansCount.setText("0");
            }
            //评论次数
            if (!EmptyUtil.isEmpty(mList.get(position).getCommentsCount())) {
                dynamicViewHolder.tvCommentCount.setText(mList.get(position).getCommentsCount() + "");
            } else {
                dynamicViewHolder.tvCommentCount.setText("0");
            }
            //图片
            final List<LightSocialSearchBean.ResultBean.ListBean.PicsBean> pics = mList.get(position).getPics();
            dynamicViewHolder.ll_llokbigimg.setVisibility(View.VISIBLE);
            dynamicViewHolder.tv_picView.setVisibility(View.GONE);
            dynamicViewHolder.img_three.setVisibility(View.VISIBLE);
            dynamicViewHolder.img_two.setVisibility(View.VISIBLE);
            dynamicViewHolder.rl_first.setVisibility(View.GONE);
            if (pics.size() > 3) {
                // getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), dynamicViewHolder.img_one, false);
                GlideUtil.LoadImage(lightSocialSearchActivity, pics.get(0).getUrl(), dynamicViewHolder.img_one);
                GlideUtil.LoadImage(lightSocialSearchActivity, pics.get(1).getUrl(), dynamicViewHolder.img_two);
                GlideUtil.LoadImage(lightSocialSearchActivity, pics.get(2).getUrl(), dynamicViewHolder.img_three);
                dynamicViewHolder.tv_picView.setVisibility(View.VISIBLE);
                dynamicViewHolder.tv_picView.setText("共" + pics.size() + "张");
            } else if (pics.size() == 3) {
                // getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), dynamicViewHolder.img_one, false);
                GlideUtil.LoadImage(lightSocialSearchActivity, pics.get(0).getUrl(), dynamicViewHolder.img_one);
                GlideUtil.LoadImage(lightSocialSearchActivity, pics.get(1).getUrl(), dynamicViewHolder.img_two);
                GlideUtil.LoadImage(lightSocialSearchActivity, pics.get(2).getUrl(), dynamicViewHolder.img_three);
            } else if (pics.size() == 2) {
                dynamicViewHolder.img_three.setVisibility(View.GONE);
                // getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), dynamicViewHolder.img_one, false);
                GlideUtil.LoadImage(lightSocialSearchActivity, pics.get(0).getUrl(), dynamicViewHolder.img_one);
                GlideUtil.LoadImage(lightSocialSearchActivity, pics.get(1).getUrl(), dynamicViewHolder.img_two);
            } else if (pics.size() == 1) {

                dynamicViewHolder.rl_first.removeAllViews();

                dynamicViewHolder.ll_llokbigimg.setVisibility(View.GONE);
                dynamicViewHolder.rl_first.setVisibility(View.VISIBLE);

                if (!EmptyUtil.isEmpty(pics)) {
                    ImageView imageView = new ImageView(lightSocialSearchActivity);
                    ImageView image = getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), imageView);
                    dynamicViewHolder.rl_first.addView(image);
                    GlideUtil.LoadImage(lightSocialSearchActivity, pics.get(0).getUrl(), image);
                }
            } else {
                dynamicViewHolder.ll_llokbigimg.setVisibility(View.GONE);
            }

            dynamicViewHolder.ll_llokbigimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lookImgClick.lookimgClick(pics, 0);

                }
            });
            dynamicViewHolder.rl_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!EmptyUtil.isEmpty(pics)) {
                        lookImgClick.lookimgClick(pics, 0);
                    }

                }
            });
            dynamicViewHolder.img_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!EmptyUtil.isEmpty(pics)) {
                        lookImgClick.lookimgClick(pics, 0);
                    }
                }
            });
            dynamicViewHolder.img_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!EmptyUtil.isEmpty(pics)) {
                        lookImgClick.lookimgClick(pics, 1);
                    }
                }
            });
            dynamicViewHolder.img_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!EmptyUtil.isEmpty(pics)) {
                        lookImgClick.lookimgClick(pics, 2);
                    }
                }
            });
            dynamicViewHolder.rl_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lightSocialSearchActivity.refreshStatus.set("refresh");
                    Intent intent = new Intent(lightSocialSearchActivity, DynamicDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    lightSocialSearchActivity.startActivityForResult(intent, 0);
                }
            });
            /**
             * 评论
             * @param COMMENT 评论标识
             *
             */
            dynamicViewHolder.ll_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lightSocialSearchActivity.refreshStatus.set("refresh");
                    Intent intent = new Intent(lightSocialSearchActivity, DynamicDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    lightSocialSearchActivity.startActivityForResult(intent, 0);
//                    commentClick.commentclick(StatusVariable.COMMENT, mList.get(position), position);

                }
            });

            /**
             * 分享
             * @param SHARE 分享标识
             *
             */
            dynamicViewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentClick.commentclick(StatusVariable.SHARE, mList.get(position), position);

                }
            });
            /**
             * 赞
             * @param FAVOUR 赞标识
             *
             */
            dynamicViewHolder.ll_favour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentClick.commentclick(StatusVariable.FAVOUR, mList.get(position), position);

                }
            });

            dynamicViewHolder.rl_dynamicDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lightSocialSearchActivity.refreshStatus.set("refresh");
                    Intent intent = new Intent(lightSocialSearchActivity, DynamicDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    lightSocialSearchActivity.startActivityForResult(intent, 0);

                }
            });

            dynamicViewHolder.headerImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lightSocialSearchActivity.refreshStatus.set("refresh");
                    intent = new Intent(lightSocialSearchActivity, ReleaseActivity.class);
                    intent.putExtra("registerId", mList.get(position).getRegisterID());
                    lightSocialSearchActivity.startActivityForResult(intent, 0);

                }
            });


        } else if (viewHolder instanceof LightSocialArticleViewHolder) {
            articleViewHolder = (LightSocialArticleViewHolder) viewHolder;

            //是否是精品 0：不是 1：是
            if (!EmptyUtil.isEmpty(mList.get(position).getSplendidStatic())) {
                if (mList.get(position).getSplendidStatic().equals(StatusVariable.SPLENDIDSTATIC)) {
                    articleViewHolder.sift.setVisibility(View.VISIBLE);
                } else {
                    articleViewHolder.sift.setVisibility(View.GONE);
                }
            }
            //文章部分内容
            if (!EmptyUtil.isEmpty(mList.get(position).getTitle())) {

                    articleViewHolder.tv_article_content.setText(Util.getHtmlContent((String) mList.get(position).getTitle()));

            }
            //文章发表人昵称
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterNickName())) {
                articleViewHolder.tv_articlename.setText(mList.get(position).getRegisterNickName());
            } else {
                articleViewHolder.tv_articlename.setText("游客");
            }
            long time = mList.get(position).getCreateTime();
            long fronttimelong = DateUtil.frontDate();//当前时间的前一天时间
            //文章时间
            if (!EmptyUtil.isEmpty(time)) {
                String date = DateUtil.DateToStr(time, StatusVariable.HM);
                if (DateUtil.isSameDay(System.currentTimeMillis(), time)) {
                    articleViewHolder.tv_articletime.setText("今天 " + date);
                } else if (DateUtil.isSameDay(fronttimelong, time)) {
                    articleViewHolder.tv_articletime.setText("昨天" + date);
                } else {
                    String timeStr = DateUtil.DateToStr(time, StatusVariable.YMD);
                    articleViewHolder.tv_articletime.setText(timeStr);
                }
            }

            articleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lightSocialSearchActivity.refreshStatus.set("refresh");
                    Intent intent = new Intent(lightSocialSearchActivity, ArticleDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    lightSocialSearchActivity.startActivityForResult(intent, 0);
                }
            });

        } else {
            userViewHolder = (LightSocialUserViewHolder) viewHolder;

            //名字
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterNickName())) {
                userViewHolder.user_name.setText(mList.get(position).getRegisterNickName());
            } else {
                userViewHolder.user_name.setText("游客");
            }
            //头像
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterPhoto())) {
                GlideUtil.LoadCircleImage(lightSocialSearchActivity, (String) mList.get(position).getRegisterPhoto(), userViewHolder.imguserheader);
            } else {
                userViewHolder.imguserheader.setImageResource(R.mipmap.icon_defaultheader_yes);
            }

            //是否是认证 1:是 0：否
            if (!EmptyUtil.isEmpty(mList.get(position).getIsAuth())) {
                if (mList.get(position).getIsAuth().equals(StatusVariable.YESAUTH)) {
                    userViewHolder.img_user.setVisibility(View.VISIBLE);
                } else {
                    userViewHolder.img_user.setVisibility(View.GONE);
                }
            }
            String produce = mList.get(position).getContent();
            //介绍
            if (!EmptyUtil.isEmpty(produce)) {
                userViewHolder.tv_uer_produce.setText(produce);
            }

            userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lightSocialSearchActivity.refreshStatus.set("refresh");
                    intent = new Intent(lightSocialSearchActivity, ReleaseActivity.class);
                    intent.putExtra("registerId", mList.get(position).getRegisterID());
                    lightSocialSearchActivity.startActivityForResult(intent, 0);


                }
            });

        }
    }

    public class LightSocialDynamicViewHolder extends RecyclerView.ViewHolder {

        private final ImageView sift;
        private final ImageView headerImg;
        private final ImageView img_v;
        private final TextView name;
        private final TextView tv_sifttime;
        private final TextView tv_detail;
        private final ImageView img_one;
        private final ImageView img_two;
        private final ImageView img_three;
        private final LinearLayout ll_share;
        private final LinearLayout ll_comment;
        private final LinearLayout ll_favour;
        private final TextView tv_shareCount;
        private final TextView tvCommentCount;
        private final TextView tv_fansCount;
        private final TextView tv_produce;
        private final ImageView img_zan;
        private final RelativeLayout rl_dynamicDetail;
        private final TextView tv_picView;
        private final RelativeLayout rl_bg;
        private final LinearLayout ll_llokbigimg;
        private final RelativeLayout rl_first;


        public LightSocialDynamicViewHolder(View itemView) {
            super(itemView);
            sift = itemView.findViewById(R.id.sift);
            headerImg = itemView.findViewById(R.id.headerImg);
            img_v = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            tv_sifttime = itemView.findViewById(R.id.tv_sifttime);
            tv_detail = itemView.findViewById(R.id.tv_detail);
            img_one = itemView.findViewById(R.id.img_one);
            img_two = itemView.findViewById(R.id.img_two);
            img_three = itemView.findViewById(R.id.img_three);
            ll_share = itemView.findViewById(R.id.ll_share);
            ll_comment = itemView.findViewById(R.id.ll_comment);
            ll_favour = itemView.findViewById(R.id.ll_favour);
            tv_shareCount = itemView.findViewById(R.id.tv_shareCount);
            tvCommentCount = itemView.findViewById(R.id.tv_commentCount);
            tv_fansCount = itemView.findViewById(R.id.tv_zanCount);
            tv_produce = itemView.findViewById(R.id.tv_produce);
            img_zan = itemView.findViewById(R.id.img_zan);
            rl_dynamicDetail = itemView.findViewById(R.id.rl_dynamicDetail);
            tv_picView = itemView.findViewById(R.id.tv_picView);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            ll_llokbigimg = itemView.findViewById(R.id.ll_lookbigimg);
            rl_first = itemView.findViewById(R.id.rl_first);
        }
    }

    public class LightSocialArticleViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_article_content;
        private final TextView tv_articlename;
        private final TextView tv_articletime;
        private final ImageView sift;

        public LightSocialArticleViewHolder(View itemView) {
            super(itemView);
            tv_article_content = itemView.findViewById(R.id.tv_article_content);
            tv_articlename = itemView.findViewById(R.id.tv_articlename);
            tv_articletime = itemView.findViewById(R.id.tv_articletime);
            sift = itemView.findViewById(R.id.sift);
        }
    }

    public class LightSocialUserViewHolder extends RecyclerView.ViewHolder {


        private final ImageView imguserheader;
        private final TextView user_name;
        private final ImageView img_user;
        private final TextView tv_uer_produce;

        public LightSocialUserViewHolder(View itemView) {
            super(itemView);

            imguserheader = itemView.findViewById(R.id.img_userheader);
            user_name = itemView.findViewById(R.id.user_name);
            img_user = itemView.findViewById(R.id.img_userv);
            tv_uer_produce = itemView.findViewById(R.id.tv_user_produce);


        }
    }

    public ImageView getImage(int width, int height, ImageView imageView) {

        if (width > height) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            imageView.setLayoutParams(layoutParams);
        } else if (height > width) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.width = ScreenUtil.getScreenWidth(lightSocialSearchActivity) / 2;
            layoutParams.height = 800;
            imageView.setLayoutParams(layoutParams);
        } else if (width == height) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.height = height;
            layoutParams.width=width;
            imageView.setLayoutParams(layoutParams);
        }
        return imageView;

    }


    public interface CommentClick {

        void commentclick(int type, LightSocialSearchBean.ResultBean.ListBean dataBean, int position);

    }

    CommentClick commentClick;

    public void setCommentClick(CommentClick commentClick) {
        this.commentClick = commentClick;
    }


    //查看大图
    public interface LookSearchImgClick {

        void lookimgClick(List<LightSocialSearchBean.ResultBean.ListBean.PicsBean> picsBeansList, int position);

    }

    LookSearchImgClick lookImgClick;

    public void setLookImgClick(LookSearchImgClick lookImgClick) {
        this.lookImgClick = lookImgClick;
    }

}
