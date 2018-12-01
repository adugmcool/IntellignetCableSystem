package com.system.intellignetcable.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zydu on 2018/11/28.
 */

public class TemplateListBean implements Serializable {

    /**
     * msg : success
     * code : 0
     * templates : [{"userTeamplateName":"光华路001","userTeamplateId":2},{"userTeamplateName":"建国路","userTeamplateId":3},{"userTeamplateName":"测试0001","userTeamplateId":4}]
     */

    private String msg;
    private int code;
    private List<TemplatesBean> templates;

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

    public List<TemplatesBean> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplatesBean> templates) {
        this.templates = templates;
    }

    public static class TemplatesBean {
        /**
         * userTeamplateName : 光华路001
         * userTeamplateId : 2
         */

        private String userTeamplateName;
        private int userTeamplateId;

        public String getUserTeamplateName() {
            return userTeamplateName;
        }

        public void setUserTeamplateName(String userTeamplateName) {
            this.userTeamplateName = userTeamplateName;
        }

        public int getUserTeamplateId() {
            return userTeamplateId;
        }

        public void setUserTeamplateId(int userTeamplateId) {
            this.userTeamplateId = userTeamplateId;
        }
    }
}
