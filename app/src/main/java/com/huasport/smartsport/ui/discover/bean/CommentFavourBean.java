package com.huasport.smartsport.ui.discover.bean;

import java.io.Serializable;
import java.util.List;

public class CommentFavourBean implements Serializable {


    /**
     * result : {"allRow":1,"data":[{"id":"2690d84f404649ec9d89a808bb77466d","socialInfoId":"6691543070f44eebb8478919d759fa4a","registerId":"2791618185512960","registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","content":"呵呵","replyInfos":[{"registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","commentId":"2690d84f404649ec9d89a808bb77466d","registerId":"2791618185512960","content":"坎坎坷坷扩","createTime":1539849010566},{"registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","commentId":"2690d84f404649ec9d89a808bb77466d","registerId":"2791618185512960","content":"见到你反倒是","createTime":1539849716570},{"registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","commentId":"2690d84f404649ec9d89a808bb77466d","registerId":"2791618185512960","content":"是的范德萨","createTime":1539849730187}],"replyCount":5,"createTime":1539835189445}],"totalPage":1,"pageSize":10,"currentPage":1}
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

    public static class ResultBean implements Serializable {
        /**
         * allRow : 1
         * data : [{"id":"2690d84f404649ec9d89a808bb77466d","socialInfoId":"6691543070f44eebb8478919d759fa4a","registerId":"2791618185512960","registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","content":"呵呵","replyInfos":[{"registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","commentId":"2690d84f404649ec9d89a808bb77466d","registerId":"2791618185512960","content":"坎坎坷坷扩","createTime":1539849010566},{"registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","commentId":"2690d84f404649ec9d89a808bb77466d","registerId":"2791618185512960","content":"见到你反倒是","createTime":1539849716570},{"registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","commentId":"2690d84f404649ec9d89a808bb77466d","registerId":"2791618185512960","content":"是的范德萨","createTime":1539849730187}],"replyCount":5,"createTime":1539835189445}]
         * totalPage : 1
         * pageSize : 10
         * currentPage : 1
         */

        private int allRow;
        private int totalPage;
        private int pageSize;
        private int currentPage;
        private String timestamp;
        private List<DataBean> data;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public int getAllRow() {
            return allRow;
        }

        public void setAllRow(int allRow) {
            this.allRow = allRow;
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

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            /**
             * id : 2690d84f404649ec9d89a808bb77466d
             * socialInfoId : 6691543070f44eebb8478919d759fa4a
             * registerId : 2791618185512960
             * registerNickName : 陌雨纷飞2
             * registerPhoto : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132
             * isAuth : 1
             * position : java工程师 | java工程师
             * content : 呵呵
             * replyInfos : [{"registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","commentId":"2690d84f404649ec9d89a808bb77466d","registerId":"2791618185512960","content":"坎坎坷坷扩","createTime":1539849010566},{"registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","commentId":"2690d84f404649ec9d89a808bb77466d","registerId":"2791618185512960","content":"见到你反倒是","createTime":1539849716570},{"registerNickName":"陌雨纷飞2","registerPhoto":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132","isAuth":"1","position":"java工程师 | java工程师","commentId":"2690d84f404649ec9d89a808bb77466d","registerId":"2791618185512960","content":"是的范德萨","createTime":1539849730187}]
             * replyCount : 5
             * createTime : 1539835189445
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
            private String createDate;

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

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

            public static class ReplyInfosBean implements Serializable {
                /**
                 * registerNickName : 陌雨纷飞2
                 * registerPhoto : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkGlltgEHFVXe40ibxbaLLJ8B3icvnvsE77WabqvRHcfyCf6p92k6KTyjK1oYSwvb5mdgGSMdYk5NA/132
                 * isAuth : 1
                 * position : java工程师 | java工程师
                 * commentId : 2690d84f404649ec9d89a808bb77466d
                 * registerId : 2791618185512960
                 * content : 坎坎坷坷扩
                 * createTime : 1539849010566
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
