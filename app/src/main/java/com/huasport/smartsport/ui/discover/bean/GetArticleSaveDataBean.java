package com.huasport.smartsport.ui.discover.bean;

public class GetArticleSaveDataBean {


    /**
     * result : {"socialInfo":{"id":null,"registerID":null,"registerNickName":null,"registerPhoto":null,"isAuth":null,"position":null,"content":null,"title":null,"pics":null,"commentsCount":0,"likesCount":0,"shareCount":0,"createTime":null,"splendidStatic":null,"type":null,"isLike":null,"isFollow":null,"platform":null}}
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
         * socialInfo : {"id":null,"registerID":null,"registerNickName":null,"registerPhoto":null,"isAuth":null,"position":null,"content":null,"title":null,"pics":null,"commentsCount":0,"likesCount":0,"shareCount":0,"createTime":null,"splendidStatic":null,"type":null,"isLike":null,"isFollow":null,"platform":null}
         */

        private SocialInfoBean socialInfo;

        public SocialInfoBean getSocialInfo() {
            return socialInfo;
        }

        public void setSocialInfo(SocialInfoBean socialInfo) {
            this.socialInfo = socialInfo;
        }

        public static class SocialInfoBean {
            /**
             * id : null
             * registerID : null
             * registerNickName : null
             * registerPhoto : null
             * isAuth : null
             * position : null
             * content : null
             * title : null
             * pics : null
             * commentsCount : 0
             * likesCount : 0
             * shareCount : 0
             * createTime : null
             * splendidStatic : null
             * type : null
             * isLike : null
             * isFollow : null
             * platform : null
             */

            private Object id;
            private Object registerID;
            private Object registerNickName;
            private Object registerPhoto;
            private Object isAuth;
            private Object position;
            private Object content;
            private Object title;
            private Object pics;
            private int commentsCount;
            private int likesCount;
            private int shareCount;
            private Object createTime;
            private Object splendidStatic;
            private Object type;
            private Object isLike;
            private Object isFollow;
            private Object platform;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getRegisterID() {
                return registerID;
            }

            public void setRegisterID(Object registerID) {
                this.registerID = registerID;
            }

            public Object getRegisterNickName() {
                return registerNickName;
            }

            public void setRegisterNickName(Object registerNickName) {
                this.registerNickName = registerNickName;
            }

            public Object getRegisterPhoto() {
                return registerPhoto;
            }

            public void setRegisterPhoto(Object registerPhoto) {
                this.registerPhoto = registerPhoto;
            }

            public Object getIsAuth() {
                return isAuth;
            }

            public void setIsAuth(Object isAuth) {
                this.isAuth = isAuth;
            }

            public Object getPosition() {
                return position;
            }

            public void setPosition(Object position) {
                this.position = position;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public Object getPics() {
                return pics;
            }

            public void setPics(Object pics) {
                this.pics = pics;
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

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getSplendidStatic() {
                return splendidStatic;
            }

            public void setSplendidStatic(Object splendidStatic) {
                this.splendidStatic = splendidStatic;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public Object getIsLike() {
                return isLike;
            }

            public void setIsLike(Object isLike) {
                this.isLike = isLike;
            }

            public Object getIsFollow() {
                return isFollow;
            }

            public void setIsFollow(Object isFollow) {
                this.isFollow = isFollow;
            }

            public Object getPlatform() {
                return platform;
            }

            public void setPlatform(Object platform) {
                this.platform = platform;
            }
        }
    }
}
