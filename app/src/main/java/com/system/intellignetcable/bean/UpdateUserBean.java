package com.system.intellignetcable.bean;

import java.io.Serializable;

/**
 * Created by adu on 2018/11/30.
 */

public class UpdateUserBean implements Serializable {


    /**
     * msg : success
     * code : 0
     * obj : {"appVersion":"V1.0.0","appUrl":"http://baidu.com","remarks":"最新版本","isUse":"1"}
     */

    private String msg;
    private int code;
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
