package com.system.intellignetcable;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * Created by zydu on 2018/11/26.
 */

public class IntelligentCableApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
    }
}
