package com.system.intellignetcable.bean;

import java.io.Serializable;

/**
 * Created by adu on 2018/12/2.
 */

public class PicStringBean implements Serializable {
    private String path;
    private boolean fromServe;

    public PicStringBean(String path) {
        this.path = path;
    }

    public PicStringBean(String path, boolean fromServe) {
        this.path = path;
        this.fromServe = fromServe;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFromServe() {
        return fromServe;
    }

    public void setFromServe(boolean fromServe) {
        this.fromServe = fromServe;
    }
}
