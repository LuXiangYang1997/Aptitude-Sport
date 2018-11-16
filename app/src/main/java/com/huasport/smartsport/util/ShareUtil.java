package com.huasport.smartsport.util;

import android.content.Context;

import com.huasport.smartsport.R;
import com.huasport.smartsport.wxapi.ThirdPart;

public class ShareUtil {

    private Context context;
    private ThirdPart thirdPart;

    public ShareUtil(Context context) {
        this.context = context;
         thirdPart = new ThirdPart(context);
    }

    public void shareFriends(String url,String content,int iconId,String userNick){

        thirdPart.wxUrlShare(url +
                        "&uSource=share", "来自于" + userNick + "的动态分享"
                , content
                , iconId, ThirdPart.WECHAT_FRIEND);
    }

    public void shareFriendsQuan(String url,String content,int iconId,String userNick){

        thirdPart.wxUrlShare(url +
                        "&uSource=share", "来自于" + userNick + "的动态分享"
                , ""
                ,iconId, ThirdPart.WECHAT_MOMENT);

    }





}
