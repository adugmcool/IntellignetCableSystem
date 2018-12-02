package com.system.intellignetcable.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.LoginActivity;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.bean.LoginBean;
import com.system.intellignetcable.bean.MapDataBean;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zydu on 2018/11/21.
 */

public class LocationFragment extends BaseFragment {
    private static LocationFragment locationFragment;
    @BindView(R.id.location_mv)
    MapView locationMv;
    Unbinder unbinder;
    //定位相关
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private BaiduMap mBaiduMap;

    public static LocationFragment getInstance(){
        if(locationFragment == null){
            locationFragment = new LocationFragment();
        }
        return locationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        unbinder = ButterKnife.bind(this, view);
        initMap();
        findMapDataStat();
        return view;
    }

    private void initMap() {
        mBaiduMap = locationMv.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    private void chooseMyLocation(double la,double lo) {
        // 构造定位数据
        MyLocationData locationData = new MyLocationData.Builder()
                .latitude(la)
                .longitude(lo)
                .build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locationData);
        // 设置定位图层的配置，设置图标跟随状态（图标一直在地图中心）
//        MyLocationConfiguration config = new MyLocationConfiguration(
//                MyLocationConfiguration.LocationMode.FOLLOWING, true, marker);
//        mBaiduMap.setMyLocationConfigeration(config);
        // 当不需要定位时，关闭定位图层
        // baiduMap.setMyLocationEnabled(false);

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || locationMv == null) {
                return;
            }
            double mCurrentLat = location.getLatitude();
            double mCurrentLon = location.getLongitude();
            double mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void findMapDataStat(){
            OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_SYS_MAP_DATA)
                    .headers("token", (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Gson gson = new Gson();
                            MapDataBean mapDataBean = gson.fromJson(response.body(), MapDataBean.class);
                            if (mapDataBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                                List<MapDataBean.ListBean> listBeans = mapDataBean.getList();
                                if(listBeans != null && !listBeans.isEmpty()){
                                    for (int i = 0; i < listBeans.size(); i++) {
                                        MapDataBean.ListBean listBean = listBeans.get(i);
                                        LatLng latLng = new LatLng(Double.parseDouble(listBean.getLatitude()), Double.parseDouble(listBean.getLongitude()));
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("ListBean", listBean);

                                        if (i == 0) {
                                            chooseMyLocation(Double.parseDouble(listBean.getLatitude()), Double.parseDouble(listBean.getLongitude()));
                                        }
                                        View view = View.inflate(getActivity().getApplicationContext(), R.layout.item_location_pop, null);
                                        TextView nameTv = view.findViewById(R.id.name_tv);
                                        TextView numTv = view.findViewById(R.id.num_tv);
                                        nameTv.setText(listBean.getAreaName());
                                        numTv.setText(listBean.getNum() + "个");
                                        //将View转化为Bitmap
                                        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromView(view);
                                        OverlayOptions options = new MarkerOptions().position(latLng).icon(descriptor).extraInfo(bundle).zIndex(9).draggable(true);
                                        mBaiduMap.addOverlay(options);
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), mapDataBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    @Override
    public void onResume() {
        locationMv.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        locationMv.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        locationMv.onDestroy();
        super.onDestroy();
    }
}
