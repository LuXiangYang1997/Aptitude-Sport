package com.huasport.smartsport.ui.matchgrade.bean;

import java.io.Serializable;
import java.util.List;

public class MatchGradeTabBean implements Serializable{


    /**
     * result : {"types":[{"gameCode":"project201808212055143612","gameName":"测试05","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/0B/rBADF1t8C7GAFMq9AAB7imHs7d4879.jpg"},{"gameCode":"project201808212055040954","gameName":"测试04","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/0B/rBADF1t8C6eASwNJAADPvTVy6ik921.jpg"},{"gameCode":"project201808212054330491","gameName":"测试03","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/0B/rBADF1t8C5eANcV4AACBRLPii_o473.jpg"},{"gameCode":"project201808212054188188","gameName":"测试02","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/0B/rBADF1t8C3aASjePAADTjyBEORA342.jpg"},{"gameCode":"project201808212054023109","gameName":"测试01","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/0B/rBADF1t8C2mAAGUiAACS8JA7Lds138.jpg"},{"gameCode":"project201808212053282506","gameName":"测试00","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/0B/rBADF1t8C0eAYJGvAAB9PDLNfHw088.jpg"},{"gameCode":"project201808211917450165","gameName":"测试项目821","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/0B/rBADF1t79NiAD83VAACkWDFwrvM922.jpg"},{"gameCode":"project201807201526477978","gameName":"智能运动","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNUkCAdadWAACIuOF2YtE78.jpeg"},{"gameCode":"project201807181408328238","gameName":"智能健身","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tO2WCAAQe9AAWSsbVkhck299.jpg"},{"gameCode":"project201807181031111085","gameName":"智能台球","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tOpm6AKugpAAFXU2-kWuk869.jpg"},{"gameCode":"project201807170958331228","gameName":"智能高尔夫","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNTUmAF3mKAAD3sjcPNhs039.jpg"},{"gameCode":"project201807161547419735","gameName":"测试篮球项目","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/02/rBADF1tMTZyAF2KyAAOnQby_Qe8200.jpg"}]}
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
        private List<TypesBean> types;

        public List<TypesBean> getTypes() {
            return types;
        }

        public void setTypes(List<TypesBean> types) {
            this.types = types;
        }

        public static class TypesBean implements Serializable{
            /**
             * gameCode : project201808212055143612
             * gameName : 测试05
             * gameImg : http://zntyfdfs.efida.com.cn/group1/M00/00/0B/rBADF1t8C7GAFMq9AAB7imHs7d4879.jpg
             */

            private String gameCode;
            private String gameName;
            private String gameImg;
            private boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getGameCode() {
                return gameCode;
            }

            public void setGameCode(String gameCode) {
                this.gameCode = gameCode;
            }

            public String getGameName() {
                return gameName;
            }

            public void setGameName(String gameName) {
                this.gameName = gameName;
            }

            public String getGameImg() {
                return gameImg;
            }

            public void setGameImg(String gameImg) {
                this.gameImg = gameImg;
            }
        }
    }
}
