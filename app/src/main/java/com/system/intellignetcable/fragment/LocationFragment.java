package com.system.intellignetcable.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.system.intellignetcable.R;
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

public class LocationFragment extends BaseFragment implements LocationSource, AMapLocationListener {
    private static LocationFragment locationFragment;
    @BindView(R.id.location_mv)
    MapView locationMv;
    Unbinder unbinder;
    //初始化地图控制器对象
    private AMap aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private LatLng latLng;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;


    public static LocationFragment getInstance() {
        if (locationFragment == null) {
            locationFragment = new LocationFragment();
        }
        return locationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        unbinder = ButterKnife.bind(this, view);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        locationMv.onCreate(savedInstanceState);
        initMap();
        findMapDataStat();
        return view;
    }

    private void initMap() {
        if (aMap == null) {
            aMap = locationMv.getMap();
        }
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(true);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    private void chooseMyLocation(double la, double lo) {
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        //将地图移动到定位点
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(la, lo)));
        // 设置定位图层的配置，设置图标跟随状态（图标一直在地图中心）
//        MyLocationConfiguration config = new MyLocationConfiguration(
//                MyLocationConfiguration.LocationMode.FOLLOWING, true, marker);
//        mBaiduMap.setMyLocationConfigeration(config);
        // 当不需要定位时，关闭定位图层
        // baiduMap.setMyLocationEnabled(false);

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                double mCurrentLat = aMapLocation.getLatitude();
                double mCurrentLon = aMapLocation.getLongitude();
                latLng = new LatLng(mCurrentLat, mCurrentLon);//构造一个位置
                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mCurrentLat, mCurrentLon)));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);

                    isFirstLoc = false;
                }

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void findMapDataStat() {
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
                            if (listBeans != null && !listBeans.isEmpty()) {
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
                                    MarkerOptions options = new MarkerOptions().position(latLng).icon(descriptor).zIndex(9).draggable(false);
                                    aMap.addMarker(options);
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
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (locationMv != null) {
            locationMv.onDestroy();
        }
        if (mLocationClient != null) {
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        locationMv.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        locationMv.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        locationMv.onSaveInstanceState(outState);
    }
}
