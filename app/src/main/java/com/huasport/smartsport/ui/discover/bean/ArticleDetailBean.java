package com.huasport.smartsport.ui.discover.bean;


import java.util.List;

public class ArticleDetailBean {


    /**
     * result : {"data":{"id":"f4162b8402d54602b536e26dfba2dd08","registerID":"2806024440023040","registerNickName":"沉默","registerPhoto":"http://devfdfs.zntyydh.com/group1/M00/00/05/CkAillvHBfmAfwVtAC7jjgzM3zg691.jpg","isAuth":"0","position":null,"content":"","title":null,"pics":[{"id":"9ea4a7bb209649c2a15b97880860cad8","url":"http://devfdfs.zntyydh.com/group1/M00/00/05/CkAillvHDrGAdLrXAPEXttwFgak284.jpg","width":3024,"height":4032,"sort":0},{"id":"6f63003d92dc4843bc17c05463608df8","url":"http://devfdfs.zntyydh.com/group1/M00/00/05/CkAillvHDrmAT-bcAPR71M-tiSs824.jpg","width":4032,"height":3024,"sort":0}],"commentsCount":0,"likesCount":0,"shareCount":0,"createTime":1539772091116,"splendidStatic":"0","type":"dynamic","isLike":"0","isFollow":"0","platform":"app"},"isOneself":"0"}
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

    public static class ResultBean {
        /**
         * data : {"id":"f4162b8402d54602b536e26dfba2dd08","registerID":"2806024440023040","registerNickName":"沉默","registerPhoto":"http://devfdfs.zntyydh.com/group1/M00/00/05/CkAillvHBfmAfwVtAC7jjgzM3zg691.jpg","isAuth":"0","position":null,"content":"","title":null,"pics":[{"id":"9ea4a7bb209649c2a15b97880860cad8","url":"http://devfdfs.zntyydh.com/group1/M00/00/05/CkAillvHDrGAdLrXAPEXttwFgak284.jpg","width":3024,"height":4032,"sort":0},{"id":"6f63003d92dc4843bc17c05463608df8","url":"http://devfdfs.zntyydh.com/group1/M00/00/05/CkAillvHDrmAT-bcAPR71M-tiSs824.jpg","width":4032,"height":3024,"sort":0}],"commentsCount":0,"likesCount":0,"shareCount":0,"createTime":1539772091116,"splendidStatic":"0","type":"dynamic","isLike":"0","isFollow":"0","platform":"app"}
         * isOneself : 0
         */

        private ResultBean.DataBean data;
        private String isOneself;

        public ResultBean.DataBean getData() {
            return data;
        }

        public void setData(ResultBean.DataBean data) {
            this.data = data;
        }

        public String getIsOneself() {
            return isOneself;
        }

        public void setIsOneself(String isOneself) {
            this.isOneself = isOneself;
        }

        public static class DataBean {
            /**
             * id : f4162b8402d54602b536e26dfba2dd08
             * registerID : 2806024440023040
             * registerNickName : 沉默
             * registerPhoto : http://devfdfs.zntyydh.com/group1/M00/00/05/CkAillvHBfmAfwVtAC7jjgzM3zg691.jpg
             * isAuth : 0
             * position : null
             * content :
             * title : null
             * pics : [{"id":"9ea4a7bb209649c2a15b97880860cad8","url":"http://devfdfs.zntyydh.com/group1/M00/00/05/CkAillvHDrGAdLrXAPEXttwFgak284.jpg","width":3024,"height":4032,"sort":0},{"id":"6f63003d92dc4843bc17c05463608df8","url":"http://devfdfs.zntyydh.com/group1/M00/00/05/CkAillvHDrmAT-bcAPR71M-tiSs824.jpg","width":4032,"height":3024,"sort":0}]
             * commentsCount : 0
             * likesCount : 0
             * shareCount : 0
             * createTime : 1539772091116
             * splendidStatic : 0
             * type : dynamic
             * isLike : 0
             * isFollow : 0
             * platform : app
             */

            private String id;
            private String registerID;
            private String registerNickName;
            private String registerPhoto;
            private String isAuth;
            private Object position;
            private String content;
            private Object title;
            private int commentsCount;
            private int likesCount;
            private int shareCount;
            private long createTime;
            private String splendidStatic;
            private String type;
            private String isLike;
            private String isFollow;
            private String platform;
            private String shareURl;
            private List<DynamicDetailBean.ResultBean.DataBean.PicsBean> pics;

            public String getShareURl() {
                return shareURl;
            }

            public void setShareURl(String shareURl) {
                this.shareURl = shareURl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRegisterID() {
                return registerID;
            }

            public void setRegisterID(String registerID) {
                this.registerID = registerID;
            }

            public String getRegisterNickName() {
                return registerNickName;
            }

            public void setRegisterNickName(String registerNickName) {
                this.registerNickName = registerNickName;
            }

            public String getRegisterPhoto() {
                return registerPhoto;
            }

            public void setRegisterPhoto(String registerPhoto) {
                this.registerPhoto = registerPhoto;
            }

            public String getIsAuth() {
                return isAuth;
            }

            public void setIsAuth(String isAuth) {
                this.isAuth = isAuth;
            }

            public Object getPosition() {
                return position;
            }

            public void setPosition(Object position) {
                this.position = position;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public int getCommentsCount() {
                return commentsCount;
            }

            public void setCommentsCount(int commentsCount) {
                this.commentsCount = commentsCount;
            }

            public int getLikesCount() {
                return likesCount;
            }

            public void setLikesCount(int likesCount) {
                this.likesCount = likesCount;
            }

            public int getShareCount() {
                return shareCount;
            }

            public void setShareCount(int shareCount) {
                this.shareCount = shareCount;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getSplendidStatic() {
                return splendidStatic;
            }

            public void setSplendidStatic(String splendidStatic) {
                this.splendidStatic = splendidStatic;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getIsLike() {
                return isLike;
            }

            public void setIsLike(String isLike) {
                this.isLike = isLike;
            }

            public String getIsFollow() {
                return isFollow;
            }

            public void setIsFollow(String isFollow) {
                this.isFollow = isFollow;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public List<DynamicDetailBean.ResultBean.DataBean.PicsBean> getPics() {
                return pics;
            }

            public void setPics(List<DynamicDetailBean.ResultBean.DataBean.PicsBean> pics) {
                this.pics = pics;
            }

            public static class PicsBean {
                /**
                 * id : 9ea4a7bb209649c2a15b97880860cad8
                 * url : http://devfdfs.zntyydh.com/group1/M00/00/05/CkAillvHDrGAdLrXAPEXttwFgak284.jpg
                 * width : 3024
                 * height : 4032
                 * sort : 0
                 */

                private String id;
                private String url;
                private int width;
                private int height;
                private int sort;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }
            }
        }
    }
    
    
    
    
}
