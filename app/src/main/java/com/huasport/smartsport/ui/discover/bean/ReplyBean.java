package com.huasport.smartsport.ui.discover.bean;

import java.util.List;

public class ReplyBean {


    /**
     * result : {"allRow":4,"data":{"id":"d1d2707e31de44b48ee566ad2e711f28","socialInfoId":"a53bef483aa9449fa90457e8757cf2af","registerId":"2859846872909824","registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","content":"能把赏赐都烧光","replyInfos":[{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"高尚","createTime":1539920528099},{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"我多向往，有个美丽的地方","createTime":1539921423413},{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"危险人物","createTime":1539921446480},{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"高尚","createTime":1539921644513}],"replyCount":4,"createTime":1539920275946},"totalPage":1,"pageSize":10,"currentPage":1}
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
         * allRow : 4
         * data : {"id":"d1d2707e31de44b48ee566ad2e711f28","socialInfoId":"a53bef483aa9449fa90457e8757cf2af","registerId":"2859846872909824","registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","content":"能把赏赐都烧光","replyInfos":[{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"高尚","createTime":1539920528099},{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"我多向往，有个美丽的地方","createTime":1539921423413},{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"危险人物","createTime":1539921446480},{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"高尚","createTime":1539921644513}],"replyCount":4,"createTime":1539920275946}
         * totalPage : 1
         * pageSize : 10
         * currentPage : 1
         */

        private int allRow;
        private DataBean data;
        private int totalPage;
        private int pageSize;
        private int currentPage;

        public int getAllRow() {
            return allRow;
        }

        public void setAllRow(int allRow) {
            this.allRow = allRow;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public static class DataBean {
            /**
             * id : d1d2707e31de44b48ee566ad2e711f28
             * socialInfoId : a53bef483aa9449fa90457e8757cf2af
             * registerId : 2859846872909824
             * registerNickName : 师师师
             * registerPhoto : http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132
             * isAuth : 1
             * position : 测试企业名称
             * content : 能把赏赐都烧光
             * replyInfos : [{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"高尚","createTime":1539920528099},{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"我多向往，有个美丽的地方","createTime":1539921423413},{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"危险人物","createTime":1539921446480},{"registerNickName":"师师师","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132","isAuth":"1","position":"测试企业名称","commentId":"d1d2707e31de44b48ee566ad2e711f28","registerId":"2859846872909824","content":"高尚","createTime":1539921644513}]
             * replyCount : 4
             * createTime : 1539920275946
             */

            private String id;
            private String socialInfoId;
            private String registerId;
            private String registerNickName;
            private String registerPhoto;
            private String isAuth;
            private String position;
            private String content;
            private int replyCount;
            private long createTime;
            private List<ReplyInfosBean> replyInfos;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSocialInfoId() {
                return socialInfoId;
            }

            public void setSocialInfoId(String socialInfoId) {
                this.socialInfoId = socialInfoId;
            }

            public String getRegisterId() {
                return registerId;
            }

            public void setRegisterId(String registerId) {
                this.registerId = registerId;
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

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getReplyCount() {
                return replyCount;
            }

            public void setReplyCount(int replyCount) {
                this.replyCount = replyCount;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public List<ReplyInfosBean> getReplyInfos() {
                return replyInfos;
            }

            public void setReplyInfos(List<ReplyInfosBean> replyInfos) {
                this.replyInfos = replyInfos;
            }

            public static class ReplyInfosBean {
                /**
                 * registerNickName : 师师师
                 * registerPhoto : http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKvoWEbqXyvFTkHIJmtOBZWdacvh0mibiaib02kpycrfibaz3vbGKRYnjic4iaORjfdEg0ibXFUBjYKcYLYg/132
                 * isAuth : 1
                 * position : 测试企业名称
                 * commentId : d1d2707e31de44b48ee566ad2e711f28
                 * registerId : 2859846872909824
                 * content : 高尚
                 * createTime : 1539920528099
                 */

                private String registerNickName;
                private String registerPhoto;
                private String isAuth;
                private String position;
                private String commentId;
                private String registerId;
                private String content;
                private long createTime;

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

                public String getPosition() {
                    return position;
                }

                public void setPosition(String position) {
                    this.position = position;
                }

                public String getCommentId() {
                    return commentId;
                }

                public void setCommentId(String commentId) {
                    this.commentId = commentId;
                }

                public String getRegisterId() {
                    return registerId;
                }

                public void setRegisterId(String registerId) {
                    this.registerId = registerId;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }
            }
        }
    }
}
