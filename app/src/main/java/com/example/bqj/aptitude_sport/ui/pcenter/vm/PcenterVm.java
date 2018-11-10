package com.example.bqj.aptitude_sport.ui.pcenter.vm;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.bqj.aptitude_sport.base.BaseViewModel;
import com.example.bqj.aptitude_sport.ui.pcenter.view.PCenterFragment;

public class PcenterVm extends BaseViewModel{

    private PCenterFragment fragment;

    public PcenterVm(PCenterFragment fragment) {
        this.fragment=fragment;
    }


    /**
     * 设置
     */
    public void setting(){



    }

    /**
     * 修改个人信息
     */
    public void personalMessage(){



    }

    /**
     * 认证
     */
    public void approve(){



    }

    /**
     * 比赛列表
     * @param type 0:待支付 1:待完善 2:已成功 3:全部比赛
     */
    public void matchList(int type){


    }

    /**
     * 发布
     */
    public void release(){



    }

    /**
     * 关注
     */
    public void follow(){


    }

    /**
     * 粉丝
     */
    public void fans(){



    }

    /**
     *我的报名卡
     */
    public void applycard(){



    }

    /**
     * 我的成绩
     */
    public void grade(){



    }

    /**
     * 我的奖章
     */
    public void medal(){



    }

    /**
     * 我的订单
     */
    public void order(){



    }

    /**
     * 帮助与反馈
     */
    public void help(){



    }

    /**
     * 关于我们
     */
    public void about(){



    }

    /**
     * 隐私政策
     */
    public void privacy(){


    }

    @Override
    public void onResume() {
        super.onResume();
        fragment.initUserInfo();

    }
}