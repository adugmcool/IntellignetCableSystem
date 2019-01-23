package com.system.intellignetcable.bean;

import java.io.Serializable;
import java.util.List;

public class LocationSearchBean implements Serializable {

    /**
     * msg : success
     * code : 0
     * list : ["详情地址"]
     */

    private String msg;
    private int code;
    private List<String> list;

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

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
