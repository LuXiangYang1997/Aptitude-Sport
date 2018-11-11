package com.huasport.smartsport.bean;

import java.util.List;

public class MatchListBean {


    /**
     * result : {"types":[{"gameCode":"project201808212055143612","gameName":"SS1超级轨道赛车","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/0B/rBADF1t8C7GAFMq9AAB7imHs7d4879.jpg"},{"gameCode":"project201807201526477978","gameName":"智能运动","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNUkCAdadWAACIuOF2YtE78.jpeg"},{"gameCode":"project201807181408328238","gameName":"智能健身","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tO2WCAAQe9AAWSsbVkhck299.jpg"},{"gameCode":"project201807181031111085","gameName":"智能台球","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tOpm6AKugpAAFXU2-kWuk869.jpg"},{"gameCode":"project201807170958331228","gameName":"智能高尔夫","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/03/rBADF1tNTUmAF3mKAAD3sjcPNhs039.jpg"},{"gameCode":"project201807161547419735","gameName":"测试篮球项目","gameImg":"http://zntyfdfs.efida.com.cn/group1/M00/00/02/rBADF1tMTZyAF2KyAAOnQby_Qe8200.jpg"}],"logos":[{"name":"国家体育总局","logo":"http://static.wx.zntyydh.com/logo/logo_gjtyj@2x.png","url":"http://www.sport.gov.cn"},{"name":"浙江省政府","logo":"http://static.wx.zntyydh.com/logo/logo_zjrm@2x.png","url":"http://www.zj.gov.cn"},{"name":"浙江省体育局","logo":"http://static.wx.zntyydh.com/logo/logo_zjstyj@2x.png","url":"http://www.zjsports.gov.cn"},{"name":"杭州余杭区政府","logo":"http://static.wx.zntyydh.com/logo/logo_hzyh@2x.png","url":"http://www.yuhang.gov.cn"},{"name":"中华全国体育总会","logo":"http://static.wx.zntyydh.com/logo/logo_zhqg@2x.png","url":"http://www.sport.org.cn"},{"name":"华奥星空","logo":"http://static.wx.zntyydh.com/logo/logo_haxk@2x.png","url":"http://3g.sports.cn/index.php"},{"name":"华运智体","logo":"http://static.wx.zntyydh.com/logo/logo_hyzt@2x.png","url":"http://huasports.com"},{"name":"浙数文化","logo":"http://static.wx.zntyydh.com/logo/logo_zswh@2x.png","url":"http://www.600633.cn/zbcm/index.shtml"},{"name":"黑岩文化","logo":"http://static.wx.zntyydh.com/logo/logo_yd@2x.png","url":"http://www.sh-blackrock.com"},{"name":"天翼云","logo":"http://static.wx.zntyydh.com/logo/logo_tyyun@2x.png","url":"http://m.ctyun.cn"},{"name":"安妮股份","logo":"http://static.wx.zntyydh.com/logo/logo_angf@2x.png","url":"http://www.anne.com.cn"}]}
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
             * gameCode : project201808212055143612
             * gameName : SS1超级轨道赛车
             * gameImg : http://zntyfdfs.efida.com.cn/group1/M00/00/0B/rBADF1t8C7GAFMq9AAB7imHs7d4879.jpg
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
             * logo : http://static.wx.zntyydh.com/logo/logo_gjtyj@2x.png
             * url : http://www.sport.gov.cn
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
