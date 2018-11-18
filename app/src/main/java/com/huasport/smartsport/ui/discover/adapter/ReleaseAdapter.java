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
import com.huasport.smartsport.databinding.PcreleaseLayoutBinding;
import com.huasport.smartsport.ui.discover.bean.AttentionSocialInfoBean;
import com.huasport.smartsport.ui.discover.view.ArticleDetailActivity;
import com.huasport.smartsport.ui.discover.view.DynamicDetailActivity;
import com.huasport.smartsport.ui.discover.view.ReleaseActivity;
import com.huasport.smartsport.util.DateUtil;
import com.huasport.smartsport.util.EmptyUtil;
import com.huasport.smartsport.util.GlideUtil;
import com.huasport.smartsport.util.ScreenUtil;
import com.huasport.smartsport.util.Util;

import java.util.List;

public class ReleaseAdapter extends BaseAdapter<AttentionSocialInfoBean.ResultBean.DataBean, RecyclerView.ViewHolder> {

    private ReleaseActivity releaseActivity;
    private PcreleaseLayoutBinding binding;
    private ReleaseDynamicViewHolder dynamicViewHolder;
    private ReleaseArticleViewHolder releaseArticleViewHolder;
    private Intent intent;

    public ReleaseAdapter(ReleaseActivity releaseActivity) {
        super(releaseActivity);
        this.releaseActivity = releaseActivity;
        binding = releaseActivity.getBinding();
    }

    @Override
    public int getItemViewType(int position) {
        if (releaseActivity.tabPosition == 0) {
            return StatusVariable.DYNAMIC;
        } else {
            return StatusVariable.ARTICLE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType) {

        if (viewType == StatusVariable.DYNAMIC) {
            View dynamicView = LayoutInflater.from(releaseActivity).inflate(R.layout.release_itemlayout, parent, false);
            return new ReleaseDynamicViewHolder(dynamicView);
        } else {
            View articleItemView = LayoutInflater.from(releaseActivity).inflate(R.layout.release_article_itemlayout, parent, false);
            return new ReleaseArticleViewHolder(articleItemView);

        }
    }

    @Override
    public void onBindVH(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ReleaseDynamicViewHolder) {
            dynamicViewHolder = (ReleaseDynamicViewHolder) viewHolder;

            if (!EmptyUtil.isEmpty(mList.get(position).getIsLike())) {
                if (mList.get(position).getIsLike().equals("0")) {
                    dynamicViewHolder.img_zan.setImageResource(R.mipmap.icon_grayzan);
                    dynamicViewHolder.tv_fansCount.setTextColor(releaseActivity.getResources().getColor(R.color.color_999999));
                } else {
                    dynamicViewHolder.img_zan.setImageResource(R.mipmap.icon_lightzan);
                    dynamicViewHolder.tv_fansCount.setTextColor(releaseActivity.getResources().getColor(R.color.color_FF8F00));
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
                GlideUtil.LoadCircleImage(releaseActivity, (String) mList.get(position).getRegisterPhoto(), dynamicViewHolder.headerImg);
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
                dynamicViewHolder.tv_detail.setText("");
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
            final List<AttentionSocialInfoBean.ResultBean.DataBean.PicsBean> pics = mList.get(position).getPics();

            dynamicViewHolder.ll_llokbigimg.setVisibility(View.VISIBLE);
            dynamicViewHolder.tv_picView.setVisibility(View.GONE);
            dynamicViewHolder.img_three.setVisibility(View.VISIBLE);
            dynamicViewHolder.img_two.setVisibility(View.VISIBLE);
            dynamicViewHolder.rl_first.setVisibility(View.GONE);
            if (pics.size() > 3) {
                // getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), dynamicViewHolder.img_one, false);
                GlideUtil.LoadImage(releaseActivity, pics.get(0).getUrl(), dynamicViewHolder.img_one);
                GlideUtil.LoadImage(releaseActivity, pics.get(1).getUrl(), dynamicViewHolder.img_two);
                GlideUtil.LoadImage(releaseActivity, pics.get(2).getUrl(), dynamicViewHolder.img_three);
                dynamicViewHolder.tv_picView.setVisibility(View.VISIBLE);
                dynamicViewHolder.tv_picView.setText("共" + pics.size() + "张");
            } else if (pics.size() == 3) {
                // getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), dynamicViewHolder.img_one, false);
                GlideUtil.LoadImage(releaseActivity, pics.get(0).getUrl(), dynamicViewHolder.img_one);
                GlideUtil.LoadImage(releaseActivity, pics.get(1).getUrl(), dynamicViewHolder.img_two);
                GlideUtil.LoadImage(releaseActivity, pics.get(2).getUrl(), dynamicViewHolder.img_three);
            } else if (pics.size() == 2) {
                dynamicViewHolder.img_three.setVisibility(View.GONE);
                // getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), dynamicViewHolder.img_one, false);
                GlideUtil.LoadImage(releaseActivity, pics.get(0).getUrl(), dynamicViewHolder.img_one);
                GlideUtil.LoadImage(releaseActivity, pics.get(1).getUrl(), dynamicViewHolder.img_two);
            } else if (pics.size() == 1) {

                dynamicViewHolder.rl_first.removeAllViews();

                dynamicViewHolder.ll_llokbigimg.setVisibility(View.GONE);
                dynamicViewHolder.rl_first.setVisibility(View.VISIBLE);
                if (!EmptyUtil.isEmpty(pics)) {
                    ImageView imageView = new ImageView(releaseActivity);
                    ImageView image = getImage(pics.get(0).getWidth(), pics.get(0).getHeight(), imageView);
                    GlideUtil.LoadImage(releaseActivity, pics.get(0).getUrl(), image);
                    dynamicViewHolder.rl_first.addView(image);
                }


            } else {
                dynamicViewHolder.ll_llokbigimg.setVisibility(View.GONE);
            }

            dynamicViewHolder.rl_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!EmptyUtil.isEmpty(pics)) {
                        lookImgClick.lookimgClick(pics, 0);

                    }
                }
            });
            dynamicViewHolder.ll_llokbigimg.setOnClickListener(new View.OnClickListener() {
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
                    releaseActivity.refreshStatus.set("refresh");
                    Intent intent = new Intent(releaseActivity, DynamicDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    releaseActivity.startActivityForResult(intent, 0);
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

                    releaseActivity.refreshStatus.set("refresh");
                    Intent intent = new Intent(releaseActivity, DynamicDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    releaseActivity.startActivityForResult(intent, 0);

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
                    releaseClick.releaseclick(StatusVariable.SHARE, mList.get(position), position);

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
                    releaseClick.releaseclick(StatusVariable.FAVOUR, mList.get(position), position);

                }
            });
            dynamicViewHolder.rl_dynamicDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releaseActivity.refreshStatus.set("refresh");
                    intent = new Intent(releaseActivity, DynamicDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    releaseActivity.startActivityForResult(intent, 0);


                }
            });

        } else if (viewHolder instanceof ReleaseArticleViewHolder) {
            releaseArticleViewHolder = (ReleaseArticleViewHolder) viewHolder;
            //是否是精品 0：不是 1：是
            if (!EmptyUtil.isEmpty(mList.get(position).getSplendidStatic())) {
                if (mList.get(position).getSplendidStatic().equals(StatusVariable.SPLENDIDSTATIC)) {
                    releaseArticleViewHolder.sift.setVisibility(View.VISIBLE);
                } else {
                    releaseArticleViewHolder.sift.setVisibility(View.GONE);
                }
            }
            //文章部分内容
            if (!EmptyUtil.isEmpty(mList.get(position).getTitle())) {

                    releaseArticleViewHolder.tv_article_content.setText(Util.getHtmlContent((String) mList.get(position).getTitle()));

            }
            //文章发表人昵称
            if (!EmptyUtil.isEmpty(mList.get(position).getRegisterNickName())) {
                releaseArticleViewHolder.tv_articlename.setText(mList.get(position).getRegisterNickName());
            } else {
                releaseArticleViewHolder.tv_articlename.setText("游客");
            }
            long time = mList.get(position).getCreateTime();
            long fronttimelong = DateUtil.frontDate();//当前时间的前一天时间
            //文章时间
            if (!EmptyUtil.isEmpty(time)) {
                String date = DateUtil.DateToStr(time, StatusVariable.HM);
                if (DateUtil.isSameDay(System.currentTimeMillis(), time)) {
                    releaseArticleViewHolder.tv_articletime.setText("今天 " + date);
                } else if (DateUtil.isSameDay(fronttimelong, time)) {
                    releaseArticleViewHolder.tv_articletime.setText("昨天" + date);
                } else {
                    String timeStr = DateUtil.DateToStr(time, StatusVariable.YMD);
                    releaseArticleViewHolder.tv_articletime.setText(timeStr);
                }
            }

            releaseArticleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releaseActivity.refreshStatus.set("refresh");
                    Intent intent = new Intent(releaseActivity, ArticleDetailActivity.class);
                    intent.putExtra("dynamicId", mList.get(position).getId());
                    releaseActivity.startActivityForResult(intent, 0);
                }
            });

        }

    }


    public class ReleaseDynamicViewHolder extends RecyclerView.ViewHolder {

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

        public ReleaseDynamicViewHolder(View itemView) {
            super(itemView);

            sift = itemView.findViewById(R.id.releasesift);
            headerImg = itemView.findViewById(R.id.releaseheaderImg);
            img_v = itemView.findViewById(R.id.img_releasev);
            name = itemView.findViewById(R.id.releasename);
            tv_sifttime = itemView.findViewById(R.id.tv_releasesifttime);
            tv_detail = itemView.findViewById(R.id.tv_releasedetail);
            img_one = itemView.findViewById(R.id.img_releaseone);
            img_two = itemView.findViewById(R.id.img_releasetwo);
            img_three = itemView.findViewById(R.id.img_releasethree);
            ll_share = itemView.findViewById(R.id.ll_releaseshare);
            ll_comment = itemView.findViewById(R.id.ll_releasecomment);
            ll_favour = itemView.findViewById(R.id.ll_releasefavour);
            tv_shareCount = itemView.findViewById(R.id.tv_releaseshareCount);
            tvCommentCount = itemView.findViewById(R.id.tv_releaseCount);
            tv_fansCount = itemView.findViewById(R.id.tv_releasezanCount);
            tv_produce = itemView.findViewById(R.id.tv_releaseproduce);
            img_zan = itemView.findViewById(R.id.img_releasezan);
            rl_dynamicDetail = itemView.findViewById(R.id.rl_dynamicDetail);
            tv_picView = itemView.findViewById(R.id.tv_picView);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            ll_llokbigimg = itemView.findViewById(R.id.ll_lookbigimg);
            rl_first = itemView.findViewById(R.id.rl_first);

        }
    }

    public class ReleaseArticleViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_article_content;
        private final TextView tv_articlename;
        private final TextView tv_articletime;
        private final ImageView sift;


        public ReleaseArticleViewHolder(View itemView) {
            super(itemView);

            tv_article_content = itemView.findViewById(R.id.tv_releasearticle_content);
            tv_articlename = itemView.findViewById(R.id.tv_releasearticlename);
            tv_articletime = itemView.findViewById(R.id.tv_releasearticletime);
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
            layoutParams.width = ScreenUtil.getScreenWidth(releaseActivity) / 2;
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


    public interface ReleaseClick {

        void releaseclick(int type, AttentionSocialInfoBean.ResultBean.DataBean dataBean, int position);

    }

    ReleaseClick releaseClick;

    public void setReleaseClick(ReleaseClick releaseClick) {
        this.releaseClick = releaseClick;
    }


    //查看大图
    public interface LookReleaseImgClick {

        void lookimgClick(List<AttentionSocialInfoBean.ResultBean.DataBean.PicsBean> picsBeansList, int position);

    }

    LookReleaseImgClick lookImgClick;

    public void setLookImgClick(LookReleaseImgClick lookImgClick) {
        this.lookImgClick = lookImgClick;
    }

}
