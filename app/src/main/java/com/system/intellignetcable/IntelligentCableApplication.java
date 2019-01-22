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
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//        SDKInitializer.initialize(this);
//        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
//        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
