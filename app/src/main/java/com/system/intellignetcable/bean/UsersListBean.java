package com.system.intellignetcable.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zydu on 2018/11/28.
 */

public class UsersListBean implements Serializable {

    /**
     * msg : success
     * code : 0
     * users : [{"userId":2,"userName":"t0001","mobile":"13520181115","headImages":"http://47.99.148.150/cableimages/1.jpg"},{"userId":4,"userName":"测试普通用户","mobile":"18512341234","headImages":"http://47.99.148.150/cableimages/1.jpg"}]
     */

    private String msg;
    private int code;
    private List<UsersBean> users;

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

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        /**
         * userId : 2
         * userName : t0001
         * mobile : 13520181115
         * headImages : http://47.99.148.150/cableimages/1.jpg
         */

        private int userId;
        private String userName;
        private String mobile;
        private String headImages;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHeadImages() {
            return headImages;
        }

        public void setHeadImages(String headImages) {
            this.headImages = headImages;
        }
    }
}
