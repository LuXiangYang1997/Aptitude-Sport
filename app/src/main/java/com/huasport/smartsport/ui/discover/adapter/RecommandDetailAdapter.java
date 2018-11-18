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
import com.huasport.smartsport.ui.discover.bean.RecommandBean;
import com.huasport.smartsport.ui.discover.view.ArticleDetailActivity;
import com.huasport.smartsport.ui.discover.view.DynamicDetailActivity;
import com.huasport.smartsport.ui.discover.view.RecommandActvity;
import com.huasport.smartsport.ui.discover.view.ReleaseActivity;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.ScreenUtil;
import com.huasport.smartsport.util.Util;

import java.util.List;

public class RecommandDetailAdapter extends BaseAdapter<RecommandBean.ResultBean.DataBean, RecyclerView.ViewHolder> {

    private RecommandActvity recommandActvity;
    private RecommandDynamicViewHolder recommandrecommanddynamicViewHolder;
    private RecommandArticleViewHolder recommandarticleViewHolder;
    private Intent intent;

    public RecommandDetailAdapter(RecommandActvity recommandActvity) {
        super(recommandActvity);
        this.recommandActvity = recommandActvity;
    }

    @Override
    public int getItemViewType(int position) {

        if (mList.get(position).getType().equals("dynamic")) {
            return StatusVariable.DYNAMIC;
        } else {
            return StatusVariable.ARTICLE;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {
        if (viewType == StatusVariable.DYNAMIC) {
            View dynamicView = LayoutInflater.from(recommandActvity).inflate(R.layout.recommand_dynamicitem_layout, null);
            return new RecommandDynamicViewHolder(dynamicView);
        } else {
            View articleItemView = LayoutInflater.from(recommandActvity).inflate(R.layout.recommand_articleitem_layout, null);
            return new RecommandArticleViewHolder(articleItemView);

        }

    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof RecommandDynamicViewHolder) {

            recommandrecommanddynamicViewHolder = (RecommandDynamicViewHolder) viewHolder;

            if (!EmptyUtil.isEmpty(mList.get(position).getIsLike())) {
                if (mList.get(position).getIsLike().equals("0")) {
                    recommandrecommanddynamicViewHolder.img_zan.setImageResource(R.mipmap.icon_grayzan);
                    recommandrecommanddynamicViewHolder.tv_fansCount.setTextColor(recommandActvity.getResources().getColor(R.color.color_999999));
                } else {
                    recommandrecommanddynamicViewHolder.img_zan.setImageResource(R.mipmap.icon_lightzan);
                    recommandrecommanddynamicViewHolder.tv_fansCount.setTextColor(recommandActvity.getResources().getColor(R.color.color_FF8F00));
                }
            }

            //名字
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterNickName())) {
                recommandrecommanddynamicViewHolder.name.setText(mList.get(position).getRegisterNickName());
            } else {
                recommandrecommanddynamicViewHolder.name.setText("游客");

            }
            //头像
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterPhoto())) {
                GlideUtil.LoadCircleImage(recommandActvity, (String) mList.get(position).getRegisterPhoto(), recommandrecommanddynamicViewHolder.headerImg);
            } else {
                recommandrecommanddynamicViewHolder.headerImg.setImageResource(R.mipmap.icon_defaultheader_yes);

            }

            //是否是认证 1:是 0：否
            if (!EmptyUtil.isEmpty(mList.get(position).getIsAuth())) {
                if (mList.get(position).getIsAuth().equals(StatusVariable.YESAUTH)) {
                    recommandrecommanddynamicViewHolder.img_v.setVisibility(View.VISIBLE);
                } else {
                    recommandrecommanddynamicViewHolder.img_v.setVisibility(View.GONE);
                }
            }
            //是否是精品 0：不是 1：是
            if (!EmptyUtil.isEmpty(mList.get(position).getSplendidStatic())) {
                if (mList.get(position).getSplendidStatic().equals(StatusVariable.SPLENDIDSTATIC)) {
                    recommandrecommanddynamicViewHolder.sift.setVisibility(View.VISIBLE);
                } else {
                    recommandrecommanddynamicViewHolder.sift.setVisibility(View.GONE);
                }
            }
            String produce = mList.get(position).getContent();
            long time = mList.get(position).getCreateTime();
            long fronttimelong = DateUtil.frontDate();//当前时间的前一天时间
            String posStr = mList.get(position).getPosition();
            //动态时间
            if (!EmptyUtil.isEmpty(time)) {
                String date = DateUtil.DateToStr(time, StatusVariable.HM);
                if (DateUtil.isSameDay(System.currentTimeMillis(), time)) {
                    if (!EmptyUtil.isEmpty(posStr)) {
                        recommandrecommanddynamicViewHolder.tv_sifttime.setText("今天 " + date + " " + posStr);
                    } else {
                        recommandrecommanddynamicViewHolder.tv_sifttime.setText("今天 " + date);
                    }
                } else if (DateUtil.isSameDay(fronttimelong, time)) {
                    if (!EmptyUtil.isEmpty(posStr)) {
                        recommandrecommanddynamicViewHolder.tv_sifttime.setText("昨天" + date + " " + posStr);
                    } else {
                        recommandrecommanddynamicViewHolder.tv_sifttime.setText("昨天" + date);
                    }

                } else {
                    String timeStr = DateUtil.DateToStr(time, StatusVariable.YMD);
                    if (!EmptyUtil.isEmpty(timeStr)) {
                        if (!EmptyUtil.isEmpty(posStr)) {
                            recommandrecommanddynamicViewHolder.tv_sifttime.setText(timeStr + " " + posStr);
                        } else {
                            recommandrecommanddynamicViewHolder.tv_sifttime.setText(timeStr);
                        }
                    } else {
                        recommandrecommanddynamicViewHolder.tv_sifttime.setText(posStr);
                    }


                }
            } else {
                if (!EmptyUtil.isEmpty(posStr)) {
                    recommandrecommanddynamicViewHolder.tv_sifttime.setText(posStr);
                }
            }
            //介绍
            if (!EmptyUtil.isEmpty(produce)) {
                recommandrecommanddynamicViewHolder.tv_detail.setVisibility(View.VISIBLE);
                String str = Util.reMoveLast(produce);
                if (!EmptyUtil.isEmpty(str)) {
                    recommandrecommanddynamicViewHolder.tv_detail.setText(str);
                }
            } else {
                recommandrecommanddynamicViewHolder.tv_detail.setVisibility(View.GONE);
                recommandrecommanddynamicViewHolder.tv_detail.setText("");
            }
            //分享次数
            if (!EmptyUtil.isEmpty(mList.get(position).getShareCount())) {
                recommandrecommanddynamicViewHolder.tv_shareCount.setText(mList.get(position).getShareCount() + "");
            } else {
                recommandrecommanddynamicViewHolder.tv_shareCount.setText("0");
            }
            //赞次数
            if (!EmptyUtil.isEmpty(mList.get(position).getLikesCount())) {
                recommandrecommanddynamicViewHolder.tv_fansCount.setText(mList.get(position).getLikesCount() + "");
            } else {
                recommandrecommanddynamicViewHolder.tv_fansCount.setText("0");

            }
            //评论次数
            if (!EmptyUtil.isEmpty(mList.get(position).getCommentsCount())) {
                recommandrecommanddynamicViewHolder.tvCommentCount.setText(mList.get(position).getCommentsCount() + "");
            } else {
                recommandrecommanddynamicViewHolder.tvCommentCount.setText("0");

            }
            //图片
            final List<RecommandBean.ResultBean.DataBean.PicsBean> pics = mList.get(position).getPics();

            recommandrecommanddynamicViewHolder.ll_llokbigimg.setVisibility(View.VISIBLE);
            recommandrecommanddynamicViewHolder.tv_picView.setVisibility(View.GONE);
            recommandrecommanddynamicViewHolder.img_three.setVisibility(View.VISIBLE);
            recommandrecommanddynamicViewHolder.img_two.setVisibility(View.VISIBLE);
            recommandrecommanddynamicViewHolder.rl_first.setVisibility(View.GONE);
            if (pics.size() > 3) {
                // getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), dynamicViewHolder.img_one, false);
                GlideUtil.LoadImage(recommandActvity, pics.get(0).getUrl(), recommandrecommanddynamicViewHolder.img_one);
                GlideUtil.LoadImage(recommandActvity, pics.get(1).getUrl(), recommandrecommanddynamicViewHolder.img_two);
                GlideUtil.LoadImage(recommandActvity, pics.get(2).getUrl(), recommandrecommanddynamicViewHolder.img_three);
                recommandrecommanddynamicViewHolder.tv_picView.setVisibility(View.VISIBLE);
                recommandrecommanddynamicViewHolder.tv_picView.setText("共" + pics.size() + "张");
            } else if (pics.size() == 3) {
                // getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), dynamicViewHolder.img_one, false);
                GlideUtil.LoadImage(recommandActvity, pics.get(0).getUrl(), recommandrecommanddynamicViewHolder.img_one);
                GlideUtil.LoadImage(recommandActvity, pics.get(1).getUrl(), recommandrecommanddynamicViewHolder.img_two);
                GlideUtil.LoadImage(recommandActvity, pics.get(2).getUrl(), recommandrecommanddynamicViewHolder.img_three);
            } else if (pics.size() == 2) {
                recommandrecommanddynamicViewHolder.img_three.setVisibility(View.GONE);
                // getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), dynamicViewHolder.img_one, false);
                GlideUtil.LoadImage(recommandActvity, pics.get(0).getUrl(), recommandrecommanddynamicViewHolder.img_one);
                GlideUtil.LoadImage(recommandActvity, pics.get(1).getUrl(), recommandrecommanddynamicViewHolder.img_two);
            } else if (pics.size() == 1) {

                recommandrecommanddynamicViewHolder.rl_first.removeAllViews();

                recommandrecommanddynamicViewHolder.ll_llokbigimg.setVisibility(View.GONE);
                recommandrecommanddynamicViewHolder.rl_first.setVisibility(View.VISIBLE);
                if (!EmptyUtil.isEmpty(pics)) {
                    ImageView imageView = new ImageView(recommandActvity);

                    ImageView image = getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), imageView);

                    GlideUtil.LoadImage(recommandActvity, pics.get(0).getUrl(), image);
                    recommandrecommanddynamicViewHolder.rl_first.addView(image);
                }
            } else {
                recommandrecommanddynamicViewHolder.ll_llokbigimg.setVisibility(View.GONE);
            }

            recommandrecommanddynamicViewHolder.ll_llokbigimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lookImgClick.lookimgClick(pics, 0);

                }
            });
            recommandrecommanddynamicViewHolder.rl_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!EmptyUtil.isEmpty(pics)) {
                        lookImgClick.lookimgClick(pics, 0);
                    }
                }
            });
            recommandrecommanddynamicViewHolder.img_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!EmptyUtil.isEmpty(pics)) {
                        lookImgClick.lookimgClick(pics, 0);
                    }
                }
            });
            recommandrecommanddynamicViewHolder.img_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!EmptyUtil.isEmpty(pics)) {
                        lookImgClick.lookimgClick(pics, 1);
                    }
                }
            });
            recommandrecommanddynamicViewHolder.img_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!EmptyUtil.isEmpty(pics)) {
                        lookImgClick.lookimgClick(pics, 2);
                    }
                }
            });
            recommandrecommanddynamicViewHolder.rl_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recommandActvity.refreshStatus.set("refresh");
                    Intent intent = new Intent(recommandActvity, DynamicDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    recommandActvity.startActivityForResult(intent, 0);
                }
            });
            /**
             * 评论
             * @param COMMENT 评论标识
             *
             */
            recommandrecommanddynamicViewHolder.ll_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recommandActvity.refreshStatus.set("refresh");
                    Intent intent = new Intent(recommandActvity, DynamicDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    recommandActvity.startActivityForResult(intent, 0);

                }
            });

            /**
             * 分享
             * @param SHARE 分享标识
             *
             */
            recommandrecommanddynamicViewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
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
            recommandrecommanddynamicViewHolder.ll_favour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentClick.commentclick(StatusVariable.FAVOUR, mList.get(position), position);

                }
            });

            recommandrecommanddynamicViewHolder.rl_dynamicDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recommandActvity.refreshStatus.set("refresh");
                    intent = new Intent(recommandActvity, DynamicDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    recommandActvity.startActivityForResult(intent, 0);

                }
            });

            recommandrecommanddynamicViewHolder.headerImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recommandActvity.refreshStatus.set("refresh");
                    intent = new Intent(recommandActvity, ReleaseActivity.class);
                    intent.putExtra("registerId", mList.get(position).getRegisterID());
                    recommandActvity.startActivityForResult(intent, 0);


                }
            });


        } else {
            recommandarticleViewHolder = (RecommandArticleViewHolder) viewHolder;

            //是否是精品 0：不是 1：是
            if (!EmptyUtil.isEmpty(mList.get(position).getSplendidStatic())) {
                if (mList.get(position).getSplendidStatic().equals(StatusVariable.SPLENDIDSTATIC)) {
                    recommandarticleViewHolder.sift.setVisibility(View.VISIBLE);
                } else {
                    recommandarticleViewHolder.sift.setVisibility(View.GONE);
                }
            }

            //文章部分内容
            if (!EmptyUtil.isEmpty(mList.get(position).getTitle())) {

                    recommandarticleViewHolder.tv_article_content.setText(Util.getHtmlContent((String) mList.get(position).getTitle()));


            }
            //文章发表人昵称
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterNickName())) {
                recommandarticleViewHolder.tv_articlename.setText(mList.get(position).getRegisterNickName());
            } else {
                recommandarticleViewHolder.tv_articlename.setText("游客");
            }
            long time = mList.get(position).getCreateTime();
            long fronttimelong = DateUtil.frontDate();//当前时间的前一天时间
            //文章时间
            if (!EmptyUtil.isEmpty(time)) {
                String date = DateUtil.DateToStr(time, StatusVariable.HM);
                if (DateUtil.isSameDay(System.currentTimeMillis(), time)) {
                    recommandarticleViewHolder.tv_articletime.setText("今天 " + date);
                } else if (DateUtil.isSameDay(fronttimelong, time)) {
                    recommandarticleViewHolder.tv_articletime.setText("昨天" + date);
                } else {
                    String timeStr = DateUtil.DateToStr(time, StatusVariable.YMD);
                    recommandarticleViewHolder.tv_articletime.setText(timeStr);
                }
            }

            recommandarticleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(recommandActvity, ArticleDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    recommandActvity.startActivityForResult(intent, 0);
                }
            });

        }

    }

    public interface CommentClick {

        void commentclick(int type, RecommandBean.ResultBean.DataBean dataBean, int position);

    }

    CommentClick commentClick;

    public void setCommentClick(CommentClick commentClick) {
        this.commentClick = commentClick;
    }

    public class RecommandDynamicViewHolder extends RecyclerView.ViewHolder {

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

        public RecommandDynamicViewHolder(View itemView) {
            super(itemView);
            sift = itemView.findViewById(R.id.recommand_sift);
            headerImg = itemView.findViewById(R.id.recommandheaderImg);
            img_v = itemView.findViewById(R.id.recommandimg_v);
            name = itemView.findViewById(R.id.recommand_name);
            tv_sifttime = itemView.findViewById(R.id.tv_recommand_sifttime);
            tv_detail = itemView.findViewById(R.id.tv_recommand_detail);
            img_one = itemView.findViewById(R.id.img_recommand_one);
            img_two = itemView.findViewById(R.id.img_recommand_two);
            img_three = itemView.findViewById(R.id.img_recommand_three);
            ll_share = itemView.findViewById(R.id.ll_recommand_share);
            ll_comment = itemView.findViewById(R.id.ll_recommand_comment);
            ll_favour = itemView.findViewById(R.id.ll_recommand_favour);
            tv_shareCount = itemView.findViewById(R.id.tv_recommand_shareCount);
            tvCommentCount = itemView.findViewById(R.id.tv_recommand_commentCount);
            tv_fansCount = itemView.findViewById(R.id.tv_recommand_zanCount);
            tv_produce = itemView.findViewById(R.id.tv_recommand_produce);
            img_zan = itemView.findViewById(R.id.img_recommand_zan);
            rl_dynamicDetail = itemView.findViewById(R.id.rl_dynamicDetail);
            tv_picView = itemView.findViewById(R.id.tv_picView);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            ll_llokbigimg = itemView.findViewById(R.id.ll_lookbigimg);
            rl_first = itemView.findViewById(R.id.rl_first);
        }

    }

    public class RecommandArticleViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_article_content;
        private final TextView tv_articlename;
        private final TextView tv_articletime;
        private final ImageView sift;

        public RecommandArticleViewHolder(View itemView) {
            super(itemView);
            tv_article_content = itemView.findViewById(R.id.tv_article_content);
            tv_articlename = itemView.findViewById(R.id.tv_articlename);
            tv_articletime = itemView.findViewById(R.id.tv_articletime);
            sift = itemView.findViewById(R.id.sift);
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
            layoutParams.width = ScreenUtil.getScreenWidth(recommandActvity) / 2;
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


    //查看大图
    public interface LookRecommandImgClick {

        void lookimgClick(List<RecommandBean.ResultBean.DataBean.PicsBean> picsBeansList, int position);

    }

    LookRecommandImgClick lookImgClick;

    public void setLookImgClick(LookRecommandImgClick lookImgClick) {
        this.lookImgClick = lookImgClick;
    }

}
