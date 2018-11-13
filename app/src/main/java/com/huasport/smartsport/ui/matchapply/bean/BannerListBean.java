package com.huasport.smartsport.ui.matchapply.bean;

import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/6.
 */

public class BannerListBean {


    /**
     * result : {"list":["http://16635297.s21i.faiusr.com/2/ABUIABACGAAggpXE1wUohu-RxQEw8gc41AM.jpg","http://16635297.s21i.faiusr.com/2/ABUIABACGAAgtZXE1wUotpClNzDyBzjUAw.jpg","http://16635297.s21i.faiusr.com/2/ABUIABACGAAggZXE1wUo_YPi0gIw8gc41AM.jpg"]}
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
        private List<String> list;

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
