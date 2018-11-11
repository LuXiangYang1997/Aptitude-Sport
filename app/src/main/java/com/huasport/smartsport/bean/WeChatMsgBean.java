package com.huasport.smartsport.bean;

public class WeChatMsgBean {


    /**
     * access_token : 15_YQGYPnj_8fckbVbGB6V6sPPDoVbd-GZV7_dPX_2MTq_Sj9Am-Mi41nQ34DJx-mS57Fz1PP5lS3-KpegfSNzIFZpbcRwkX5ciQXH7qrjMqe0
     * expires_in : 7200
     * refresh_token : 15_3csJWSvQSgSj73olzFyVjcCae53S0cJ7FxfwB-bSRVRW_7HjNFGL5LwliOxdbYX0RJUtghwHf-XxaVlGe98X31-hKdvQnc51scz9ni2CM7A
     * openid : ow7EN00TWnyKTDCQauE0qWKeqpoY
     * scope : snsapi_userinfo
     * unionid : on_CA0UZ1gpridjs08lzkOAsR1Vk
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
