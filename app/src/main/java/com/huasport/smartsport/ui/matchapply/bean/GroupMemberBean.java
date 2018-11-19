package com.huasport.smartsport.ui.matchapply.bean;

import java.util.List;

public class GroupMemberBean {

    private List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> playersBeans;
//    private List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.IdPhotoBean> idPhotoBeans;
    private String frontUrl;
    private String contractUrl;
    private GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean playersBean;
    private String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean getPlayersBean() {
        return playersBean;
    }

    public void setPlayersBean(GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean playersBean) {
        this.playersBean = playersBean;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> getPlayersBeans() {
        return playersBeans;
    }

    public void setPlayersBeans(List<GroupOrderMsgBean.ResultBean.OrderDetailBean.TeamsBean.PlayersBean> playersBeans) {
        this.playersBeans = playersBeans;
    }

}
