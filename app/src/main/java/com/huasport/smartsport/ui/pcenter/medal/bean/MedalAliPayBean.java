package com.huasport.smartsport.ui.pcenter.medal.bean;

public class MedalAliPayBean {


    /**
     * result : {"sign":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018062660422282&biz_content=%7B%22body%22%3A%222018%E5%B9%B4%E5%85%A8%E5%9B%BD%E6%99%BA%E8%83%BD%E4%BD%93%E8%82%B2%E5%A4%A7%E8%B5%9B%E5%8F%82%E8%B5%9B%E5%A5%96%E7%89%8C%22%2C%22out_trade_no%22%3A%2220180816202159yj5a16%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%222018%E5%B9%B4%E5%85%A8%E5%9B%BD%E6%99%BA%E8%83%BD%E4%BD%93%E8%82%B2%E5%A4%A7%E8%B5%9B%E5%8F%82%E8%B5%9B%E5%A5%96%E7%89%8C%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.02%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fzntyapi.efida.com.cn%2Forder%2Falipay%2Fpay%2Fcallback&sign=n%2Bi%2BXOUJERYCRgqTnRrlIE1SI%2BMsAcz5VxinuJ%2Ba7jDbLkHx7MRlgLs%2BZZKyGU05PT1HRGCHIUqDy7YkSH4vY%2BDjg%2FiV2TKhHWsPdav3n0ge%2BJzS%2BrC3ghUPVvv4%2Fl7cDybHDy148VdsNeZGYDH3iW2MG30s4mi2F%2FtQqAvZ%2BDyJW1doBnzshrjDu%2BoEtnva%2FYN3rm0x4jpondhv33F7X4KnXdOZeadn3Ik1Ps9uxulDtmTL7CkB%2FIkSGfvnl7DRZDxJOuUJIxiAeuarlvTb5yF017IvDAUAtxiYyRuTOhysodDRpobecNTUA4KP%2BVo68I2r8Dj7Mp5rCKZ20SOp%2FQ%3D%3D&sign_type=RSA2&timestamp=2018-08-16+20%3A25%3A14&version=1.0"}
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
         * sign : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018062660422282&biz_content=%7B%22body%22%3A%222018%E5%B9%B4%E5%85%A8%E5%9B%BD%E6%99%BA%E8%83%BD%E4%BD%93%E8%82%B2%E5%A4%A7%E8%B5%9B%E5%8F%82%E8%B5%9B%E5%A5%96%E7%89%8C%22%2C%22out_trade_no%22%3A%2220180816202159yj5a16%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%222018%E5%B9%B4%E5%85%A8%E5%9B%BD%E6%99%BA%E8%83%BD%E4%BD%93%E8%82%B2%E5%A4%A7%E8%B5%9B%E5%8F%82%E8%B5%9B%E5%A5%96%E7%89%8C%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.02%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fzntyapi.efida.com.cn%2Forder%2Falipay%2Fpay%2Fcallback&sign=n%2Bi%2BXOUJERYCRgqTnRrlIE1SI%2BMsAcz5VxinuJ%2Ba7jDbLkHx7MRlgLs%2BZZKyGU05PT1HRGCHIUqDy7YkSH4vY%2BDjg%2FiV2TKhHWsPdav3n0ge%2BJzS%2BrC3ghUPVvv4%2Fl7cDybHDy148VdsNeZGYDH3iW2MG30s4mi2F%2FtQqAvZ%2BDyJW1doBnzshrjDu%2BoEtnva%2FYN3rm0x4jpondhv33F7X4KnXdOZeadn3Ik1Ps9uxulDtmTL7CkB%2FIkSGfvnl7DRZDxJOuUJIxiAeuarlvTb5yF017IvDAUAtxiYyRuTOhysodDRpobecNTUA4KP%2BVo68I2r8Dj7Mp5rCKZ20SOp%2FQ%3D%3D&sign_type=RSA2&timestamp=2018-08-16+20%3A25%3A14&version=1.0
         */

        private String sign;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
