package com.system.intellignetcable.bean;

import java.io.Serializable;

/**
 * Created by adu on 2018/11/30.
 */

public class AppVersionBean implements Serializable {


    /**
     * msg : success
     * code : 0
     * obj : {"appVersion":"V1.0.0","appUrl":"http://baidu.com","remarks":"最新版本","isUse":"1"}
     */

    private String msg;
    private int code;
    private ObjBean obj;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * appVersion : V1.0.0
         * appUrl : http://baidu.com
         * remarks : 最新版本
         * isUse : 1
         */

        private String appVersion;
        private String appUrl;
        private String remarks;
        private String isUse;

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getAppUrl() {
            return appUrl;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getIsUse() {
            return isUse;
        }

        public void setIsUse(String isUse) {
            this.isUse = isUse;
        }
    }
}
