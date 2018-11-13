package com.huasport.smartsport.ui.pcenter.bean;

import java.io.Serializable;

public class UserCertStatusBean implements Serializable {


    /**
     * result : {"auth":{"registerCode":"2817131058513920","authCode":"2892383950899200","idCard":null,"certType":"enterprise","certTypeDesc":"企业认证","certAtt1":null,"certAtt2":null,"certAtt3":null,"certAtt4":null,"workUnitName":null,"positionName":null,"realName":null,"enterpriseName":"牛","enterprisePerson":"陆向阳","authTag":"v1","authStatus":"wait_audit","authStatusDesc":"申请认证","rejectReason":null,"authTime":1538290848000,"authTimeStr":"2018-09-30 15:00:48","auditTime":null,"auditTimeStr":""},"authStatus":"wait_audit","authStatusDesc":"申请认证"}
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
         * auth : {"registerCode":"2817131058513920","authCode":"2892383950899200","idCard":null,"certType":"enterprise","certTypeDesc":"企业认证","certAtt1":null,"certAtt2":null,"certAtt3":null,"certAtt4":null,"workUnitName":null,"positionName":null,"realName":null,"enterpriseName":"牛","enterprisePerson":"陆向阳","authTag":"v1","authStatus":"wait_audit","authStatusDesc":"申请认证","rejectReason":null,"authTime":1538290848000,"authTimeStr":"2018-09-30 15:00:48","auditTime":null,"auditTimeStr":""}
         * authStatus : wait_audit
         * authStatusDesc : 申请认证
         */

        private AuthBean auth;
        private String authStatus;
        private String authStatusDesc;

        public AuthBean getAuth() {
            return auth;
        }

        public void setAuth(AuthBean auth) {
            this.auth = auth;
        }

        public String getAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(String authStatus) {
            this.authStatus = authStatus;
        }

        public String getAuthStatusDesc() {
            return authStatusDesc;
        }

        public void setAuthStatusDesc(String authStatusDesc) {
            this.authStatusDesc = authStatusDesc;
        }

        public static class AuthBean implements Serializable {
            /**
             * registerCode : 2817131058513920
             * authCode : 2892383950899200
             * idCard : null
             * certType : enterprise
             * certTypeDesc : 企业认证
             * certAtt1 : null
             * certAtt2 : null
             * certAtt3 : null
             * certAtt4 : null
             * workUnitName : null
             * positionName : null
             * realName : null
             * enterpriseName : 牛
             * enterprisePerson : 陆向阳
             * authTag : v1
             * authStatus : wait_audit
             * authStatusDesc : 申请认证
             * rejectReason : null
             * authTime : 1538290848000
             * authTimeStr : 2018-09-30 15:00:48
             * auditTime : null
             * auditTimeStr :
             */

            private String registerCode;
            private String authCode;
            private Object idCard;
            private String certType;
            private String certTypeDesc;
            private Object certAtt1;
            private Object certAtt2;
            private Object certAtt3;
            private Object certAtt4;
            private Object workUnitName;
            private Object positionName;
            private Object realName;
            private String enterpriseName;
            private String enterprisePerson;
            private String authTag;
            private String authStatus;
            private String authStatusDesc;
            private Object rejectReason;
            private long authTime;
            private String authTimeStr;
            private Object auditTime;
            private String auditTimeStr;

            public String getRegisterCode() {
                return registerCode;
            }

            public void setRegisterCode(String registerCode) {
                this.registerCode = registerCode;
            }

            public String getAuthCode() {
                return authCode;
            }

            public void setAuthCode(String authCode) {
                this.authCode = authCode;
            }

            public Object getIdCard() {
                return idCard;
            }

            public void setIdCard(Object idCard) {
                this.idCard = idCard;
            }

            public String getCertType() {
                return certType;
            }

            public void setCertType(String certType) {
                this.certType = certType;
            }

            public String getCertTypeDesc() {
                return certTypeDesc;
            }

            public void setCertTypeDesc(String certTypeDesc) {
                this.certTypeDesc = certTypeDesc;
            }

            public Object getCertAtt1() {
                return certAtt1;
            }

            public void setCertAtt1(Object certAtt1) {
                this.certAtt1 = certAtt1;
            }

            public Object getCertAtt2() {
                return certAtt2;
            }

            public void setCertAtt2(Object certAtt2) {
                this.certAtt2 = certAtt2;
            }

            public Object getCertAtt3() {
                return certAtt3;
            }

            public void setCertAtt3(Object certAtt3) {
                this.certAtt3 = certAtt3;
            }

            public Object getCertAtt4() {
                return certAtt4;
            }

            public void setCertAtt4(Object certAtt4) {
                this.certAtt4 = certAtt4;
            }

            public Object getWorkUnitName() {
                return workUnitName;
            }

            public void setWorkUnitName(Object workUnitName) {
                this.workUnitName = workUnitName;
            }

            public Object getPositionName() {
                return positionName;
            }

            public void setPositionName(Object positionName) {
                this.positionName = positionName;
            }

            public Object getRealName() {
                return realName;
            }

            public void setRealName(Object realName) {
                this.realName = realName;
            }

            public String getEnterpriseName() {
                return enterpriseName;
            }

            public void setEnterpriseName(String enterpriseName) {
                this.enterpriseName = enterpriseName;
            }

            public String getEnterprisePerson() {
                return enterprisePerson;
            }

            public void setEnterprisePerson(String enterprisePerson) {
                this.enterprisePerson = enterprisePerson;
            }

            public String getAuthTag() {
                return authTag;
            }

            public void setAuthTag(String authTag) {
                this.authTag = authTag;
            }

            public String getAuthStatus() {
                return authStatus;
            }

            public void setAuthStatus(String authStatus) {
                this.authStatus = authStatus;
            }

            public String getAuthStatusDesc() {
                return authStatusDesc;
            }

            public void setAuthStatusDesc(String authStatusDesc) {
                this.authStatusDesc = authStatusDesc;
            }

            public Object getRejectReason() {
                return rejectReason;
            }

            public void setRejectReason(Object rejectReason) {
                this.rejectReason = rejectReason;
            }

            public long getAuthTime() {
                return authTime;
            }

            public void setAuthTime(long authTime) {
                this.authTime = authTime;
            }

            public String getAuthTimeStr() {
                return authTimeStr;
            }

            public void setAuthTimeStr(String authTimeStr) {
                this.authTimeStr = authTimeStr;
            }

            public Object getAuditTime() {
                return auditTime;
            }

            public void setAuditTime(Object auditTime) {
                this.auditTime = auditTime;
            }

            public String getAuditTimeStr() {
                return auditTimeStr;
            }

            public void setAuditTimeStr(String auditTimeStr) {
                this.auditTimeStr = auditTimeStr;
            }
        }
    }
}
