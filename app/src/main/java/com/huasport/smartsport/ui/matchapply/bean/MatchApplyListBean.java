package com.huasport.smartsport.ui.matchapply.bean;

import java.util.List;

/**
 * Created by 陆向阳 on 2018/6/13.
 */

public class MatchApplyListBean {


    /**
     * result : {"types":[{"gameCode":"project201807201526477978","gameName":"智能运动","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNUkCAdadWAACIuOF2YtE78.jpeg"},{"gameCode":"project201807181408328238","gameName":"智能健身","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tO2WCAAQe9AAWSsbVkhck299.jpg"},{"gameCode":"project201807181031111085","gameName":"智能台球","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tOpm6AKugpAAFXU2-kWuk869.jpg"},{"gameCode":"project201807170958331228","gameName":"智能高尔夫","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNTUmAF3mKAAD3sjcPNhs039.jpg"},{"gameCode":"project201807161547419735","gameName":"测试篮球项目","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/02/rBADF1tMTZyAF2KyAAOnQby_Qe8200.jpg"}],"logos":[{"name":"国家体育总局","logo":"http://www.sport.gov.cn","url":"http://static.wx.zntyydh.com/logo/logo_gjtyj@2x.png"},{"name":"浙江省政府","logo":"http://www.zj.gov.cn","url":"http://static.wx.zntyydh.com/logo/logo_zjrm@2x.png"},{"name":"浙江省体育局","logo":"http://www.zjsports.gov.cn","url":"http://static.wx.zntyydh.com/logo/logo_zjstyj@2x.png"},{"name":"杭州余杭区政府","logo":"http://www.yuhang.gov.cn","url":"http://static.wx.zntyydh.com/logo/logo_hzyh@2x.png"},{"name":"中华全国体育总会","logo":"http://www.sport.org.cn","url":"http://static.wx.zntyydh.com/logo/logo_zhqg@2x.png"},{"name":"华奥星空","logo":"http://huasports.com","url":"http://static.wx.zntyydh.com/logo/logo_haxk@2x.png"},{"name":"浙数文化","logo":"http://www.600633.cn/zbcm/index.shtml","url":"http://static.wx.zntyydh.com/logo/logo_zswh@2x.png"},{"name":"黑岩文化","logo":"http://www.sh-blackrock.com","url":"http://static.wx.zntyydh.com/logo/logo_yd@2x.png"},{"name":"天翼云","logo":"http://m.ctyun.cn","url":"http://static.wx.zntyydh.com/logo/logo_tyyun@2x.png"},{"name":"安妮股份","logo":"http://www.anne.com.cn","url":"http://static.wx.zntyydh.com/logo/logo_angf@2x.png"}]}
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
        private List<LogosBean> logos;

        public List<TypesBean> getTypes() {
            return types;
        }

        public void setTypes(List<TypesBean> types) {
            this.types = types;
        }

        public List<LogosBean> getLogos() {
            return logos;
        }

        public void setLogos(List<LogosBean> logos) {
            this.logos = logos;
        }

        public static class TypesBean {
            /**
             * gameCode : project201807201526477978
             * gameName : 智能运动
             * gameImg : http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNUkCAdadWAACIuOF2YtE78.jpeg
             */

            private String gameCode;
            private String gameName;
            private String gameImg;

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

        public static class LogosBean {
            /**
             * name : 国家体育总局
             * logo : http://www.sport.gov.cn
             * url : http://static.wx.zntyydh.com/logo/logo_gjtyj@2x.png
             */

            private String name;
            private String logo;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
