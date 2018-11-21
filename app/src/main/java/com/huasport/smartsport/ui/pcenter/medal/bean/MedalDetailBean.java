package com.huasport.smartsport.ui.pcenter.medal.bean;

import java.io.Serializable;

public class MedalDetailBean implements Serializable{


    /**
     * result : {"goods":{"goodsCode":"G20180806000001","title":"首届全国智能体育大赛参赛奖牌","goodsPic":"http://devwx.zntyydh.com/img/award_3.jpg","thumb":null,"description":"<p>\n    <img src=\"http://devwx.zntyydh.com/img/imga_1.jpg\"/> <img src=\"http://devwx.zntyydh.com/img/imga_2.jpg\"/><img src=\"http://devwx.zntyydh.com/img/imga_3.jpg\"/><img src=\"http://devwx.zntyydh.com/img/imga_4.jpg\"/><img src=\"http://devwx.zntyydh.com/img/img_5b.jpg\"/> \n<\/p>","productPrice":1,"extraMoney":1}}
     * resultCode : 200
     * resultMsg : 请求操作成功
     */

    private ResultBean result;
    private int resultCode;
    private String resultMsg;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public static class ResultBean implements Serializable{
        /**
         * goods : {"goodsCode":"G20180806000001","title":"首届全国智能体育大赛参赛奖牌","goodsPic":"http://devwx.zntyydh.com/img/award_3.jpg","thumb":null,"description":"<p>\n    <img src=\"http://devwx.zntyydh.com/img/imga_1.jpg\"/> <img src=\"http://devwx.zntyydh.com/img/imga_2.jpg\"/><img src=\"http://devwx.zntyydh.com/img/imga_3.jpg\"/><img src=\"http://devwx.zntyydh.com/img/imga_4.jpg\"/><img src=\"http://devwx.zntyydh.com/img/img_5b.jpg\"/> \n<\/p>","productPrice":1,"extraMoney":1}
         */

        private GoodsBean goods;

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public static class GoodsBean implements Serializable{
            /**
             * goodsCode : G20180806000001
             * title : 首届全国智能体育大赛参赛奖牌
             * goodsPic : http://devwx.zntyydh.com/img/award_3.jpg
             * thumb : null
             * description : <p>
             <img src="http://devwx.zntyydh.com/img/imga_1.jpg"/> <img src="http://devwx.zntyydh.com/img/imga_2.jpg"/><img src="http://devwx.zntyydh.com/img/imga_3.jpg"/><img src="http://devwx.zntyydh.com/img/imga_4.jpg"/><img src="http://devwx.zntyydh.com/img/img_5b.jpg"/>
             </p>
             * productPrice : 1
             * extraMoney : 1
             */

            private String goodsCode;
            private String title;
            private String goodsPic;
            private Object thumb;
            private String description;
            private int productPrice;
            private int extraMoney;

            public String getGoodsCode() {
                return goodsCode;
            }

            public void setGoodsCode(String goodsCode) {
                this.goodsCode = goodsCode;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getGoodsPic() {
                return goodsPic;
            }

            public void setGoodsPic(String goodsPic) {
                this.goodsPic = goodsPic;
            }

            public Object getThumb() {
                return thumb;
            }

            public void setThumb(Object thumb) {
                this.thumb = thumb;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(int productPrice) {
                this.productPrice = productPrice;
            }

            public int getExtraMoney() {
                return extraMoney;
            }

            public void setExtraMoney(int extraMoney) {
                this.extraMoney = extraMoney;
            }
        }
    }
}