package com.system.intellignetcable.bean;

import java.io.Serializable;

/**
 * Created by adu on 2018/11/26.
 */

public class LoginBean implements Serializable{

    /**
     * msg : success
     * code : 0
     * expire : 43199995
     * user : {"userId":3,"userName":"测试管理员","mobile":"18512345678","password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92","createTime":"2018-11-24 03:33:53","type":1,"headImages":"http://47.99.148.150/cableimages/1.jpg"}
     * token : d5bb15899a4e49879788f5989ff853d9
     */

    private String msg;
    private int code;
    private int expire;
    private UserBean user;
    private String token;

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

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean {
        /**
         * userId : 3
         * userName : 测试管理员
         * mobile : 18512345678
         * password : 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92
         * createTime : 2018-11-24 03:33:53
         * type : 1
         * headImages : http://47.99.148.150/cableimages/1.jpg
         */

        private int userId;
        private String userName;
        private String mobile;
        private String password;
        private String createTime;
        private int type;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getHeadImages() {
            return headImages;
        }

        public void setHeadImages(String headImages) {
            this.headImages = headImages;
        }
    }
}
