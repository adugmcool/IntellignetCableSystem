package com.system.intellignetcable.bean;

import java.io.Serializable;

/**
 * Created by zydu on 2018/11/27.
 */

public class OrderCheckBean implements Serializable{
    private String msg;
    private int code;

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
}
