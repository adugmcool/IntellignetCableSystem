package com.system.intellignetcable.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;

import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Poi;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.EpcListActivity;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.activity.OrderInfoDetailActivity;
import com.system.intellignetcable.activity.SignageManagementActivity;
import com.system.intellignetcable.adapter.StringListAdapter;
import com.system.intellignetcable.bean.LocationSearchBean;
import com.system.intellignetcable.bean.MapDataBean;
import com.system.intellignetcable.bean.MapDataDetailBean;
import com.system.intellignetcable.bean.OrderInfoDetailBean;
import com.system.intellignetcable.bean.OrderListBean;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;

import java.io.Serializable;
import java.util.ArrayList;
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
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_lv)
    ListView searchLv;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.navi_btn)
    Button naviBtn;


    private int userId;
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
    private LocationSource.OnLocationChangedListener mListener = null;
    private MainActivity mainActivity;
    private Polyline polyline;

    public static LocationFragment getInstance() {
//        if (locationFragment == null) {
            locationFragment = new LocationFragment();
//        }
        return locationFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivity = (MainActivity) activity;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            findMapDataStat();
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        unbinder = ButterKnife.bind(this, view);
        userId = (int) SharedPreferencesUtil.get(mainActivity, ParamUtil.USER_ID, 0);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        locationMv.onCreate(savedInstanceState);
        initMap();

        findMapDataStat();

        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    findMapDataStat();
                    searchLv.setVisibility(View.GONE);
                } else {
//                    findByDetailAddress(s.toString());
                    int type = (int) SharedPreferencesUtil.get(mainActivity, ParamUtil.TYPE, 2);
                    int userId = (int) SharedPreferencesUtil.get(mainActivity, ParamUtil.USER_ID, 0);
                    searchList(userId, type, s.toString(), "1", "10");
                }
            }
        });
//        mHandler.sendEmptyMessageDelayed(1, 5000);
        naviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(naviMarker != null && naviBean != null){
                    Poi start = new Poi("", latLng, "");
                    Poi end = new Poi(naviBean.getDetailAddress(), new LatLng(Double.parseDouble(naviBean.getLatitude()), Double.parseDouble(naviBean.getLongitude())), "B000A83M61");
                    AmapNaviPage.getInstance().showRouteActivity(getActivity(), new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), new INaviInfoCallback() {
                        @Override
                        public void onInitNaviFailure() {

                        }

                        @Override
                        public void onGetNavigationText(String s) {

                        }

                        @Override
                        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

                        }

                        @Override
                        public void onArriveDestination(boolean b) {

                        }

                        @Override
                        public void onStartNavi(int i) {

                        }

                        @Override
                        public void onCalculateRouteSuccess(int[] ints) {

                        }

                        @Override
                        public void onCalculateRouteFailure(int i) {

                        }

                        @Override
                        public void onStopSpeaking() {

                        }

                        @Override
                        public void onReCalculateRoute(int i) {

                        }

                        @Override
                        public void onExitPage(int i) {

                        }

                        @Override
                        public void onStrategyChanged(int i) {

                        }

                        @Override
                        public View getCustomNaviBottomView() {
                            return null;
                        }

                        @Override
                        public View getCustomNaviView() {
                            return null;
                        }

                        @Override
                        public void onArrivedWayPoint(int i) {

                        }
                    });
                }
            }
        });
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
                    isFirstLoc = false;
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mCurrentLat, mCurrentLon)));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
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
                            aMap.clear();
                            List<MapDataBean.ListBean> listBeans = mapDataBean.getList();

                            if (listBeans != null && !listBeans.isEmpty()) {
                                for (int i = 0; i < listBeans.size(); i++) {
                                    final MapDataBean.ListBean listBean = listBeans.get(i);
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
                                    Marker marker = aMap.addMarker(options);
                                    marker.setObject(listBean);
                                    aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {
                                            MapDataBean.ListBean listBean = (MapDataBean.ListBean) marker.getObject();
                                            findMapEPCDataStat(listBean.getAreaId() + "", listBean.getAreaName());
                                            return false;
                                        }
                                    });
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
            aMap = null;
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//        locationMv.onSaveInstanceState(outState);
    }


    private void findMapEPCDataStat(String areaId, final String name) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.FINDMAPEPCDATASTAT + "?areaId=" + areaId)
                .headers("token", (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        final MapDataDetailBean mapDataDetailBean = gson.fromJson(response.body(), MapDataDetailBean.class);
                        if (mapDataDetailBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            List<MapDataDetailBean.ListBean> listBeanList = mapDataDetailBean.getList();
                            for (int i = 0; i < listBeanList.size(); i++) {
                                listBeanList.get(i).setDetailAddress(name);
                            }
                            Intent intent = new Intent(getActivity(), EpcListActivity.class);
                            intent.putExtra("listBeans", (Serializable) mapDataDetailBean.getList());
                            startActivity(intent);
                        } else {
                            showFail();
                            Toast.makeText(getActivity(), mapDataDetailBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        showFail();
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void findByDetailAddress(String detailAddress) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.FINDBYDETAILADDRESS)
                .headers("token", (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                .params("detailAddress", detailAddress)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LocationSearchBean locationSearchBean = gson.fromJson(response.body(), LocationSearchBean.class);
                        if (locationSearchBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (locationSearchBean.getList() == null || locationSearchBean.getList().size() == 0) {
                                searchLv.setVisibility(View.GONE);
                            } else if (locationSearchBean.getList().size() > 0) {
                                final List<String> strings = new ArrayList<>();
                                searchLv.setVisibility(View.VISIBLE);
                                for (int i = 0; i < locationSearchBean.getList().size(); i++) {
                                    strings.add(locationSearchBean.getList().get(i));
                                }
                                StringListAdapter adapter = new StringListAdapter(getActivity(), strings);
                                searchLv.setAdapter(adapter);
                                searchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        mainActivity.hideSoftInput(view);
                                        searchLv.setVisibility(View.GONE);
                                        searchList(strings.get(position));
                                    }
                                });
                            }
                        } else {
//                            showFail();
                            Toast.makeText(getActivity(), locationSearchBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        showFail();
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //
//
    private void searchList(final String detailAddress) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_SIGN_BOARD_LIST + "?detailAddress=" + detailAddress)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        final MapDataDetailBean mapDataDetailBean = gson.fromJson(response.body(), MapDataDetailBean.class);
                        if (mapDataDetailBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (mapDataDetailBean.getList() == null || mapDataDetailBean.getList().size() == 0) {
                                searchLv.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "没有搜到相关数据！", Toast.LENGTH_SHORT).show();
                            } else if (mapDataDetailBean.getList().size() > 0) {
                                aMap.clear();
                                List<LatLng> latLngs = new ArrayList<>();
                                for (int i = 0; i < mapDataDetailBean.getList().size(); i++) {
                                    LatLng latLng = new LatLng(Double.parseDouble(mapDataDetailBean.getList().get(i).getLatitude()), Double.parseDouble(mapDataDetailBean.getList().get(i).getLongitude()));
                                    latLngs.add(latLng);
                                    MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                            .position(latLng)
                                            .draggable(true);
                                    aMap.addMarker(markerOption);
                                }
                                polyline =aMap.addPolyline(new PolylineOptions().
                                        addAll(latLngs).width(10).color(Color.BLUE));
                                aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        return false;
                                    }
                                });

                            }
                        } else {
//                            showFail();
                            Toast.makeText(getActivity(), mapDataDetailBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        showFail();
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Marker naviMarker;
    private OrderInfoDetailBean.WorkOrderBean.ListBean naviBean;

    private void orderDetail(int orderId) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_DETAIL)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                .params("workOrderId", orderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        OrderInfoDetailBean orderInfoDetailBean = gson.fromJson(response.body(), OrderInfoDetailBean.class);
                        if (orderInfoDetailBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (orderInfoDetailBean.getWorkOrder().getList() == null || orderInfoDetailBean.getWorkOrder().getList().size() == 0) {
                                searchLv.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "没有搜到相关数据！", Toast.LENGTH_SHORT).show();
                            } else if (orderInfoDetailBean.getWorkOrder().getList().size() > 0) {
                                aMap.clear();
                                List<LatLng> latLngs = new ArrayList<>();

                                for (int i = 0; i < orderInfoDetailBean.getWorkOrder().getList().size(); i++) {
                                    if (i == 0) {
                                        chooseMyLocation(Double.parseDouble(orderInfoDetailBean.getWorkOrder().getList().get(i).getLatitude()), Double.parseDouble(orderInfoDetailBean.getWorkOrder().getList().get(i).getLongitude()));
                                    }
                                    LatLng latLng = new LatLng(Double.parseDouble(orderInfoDetailBean.getWorkOrder().getList().get(i).getLatitude()), Double.parseDouble(orderInfoDetailBean.getWorkOrder().getList().get(i).getLongitude()));
                                    latLngs.add(latLng);
                                    MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                            .position(latLng)
                                            .draggable(true);
                                    Marker marker = aMap.addMarker(markerOption);
                                    marker.setObject(orderInfoDetailBean.getWorkOrder().getList().get(i));
                                }
                                polyline =aMap.addPolyline(new PolylineOptions().
                                        addAll(latLngs).width(10).color(Color.BLUE));
                                aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        if(naviMarker != null && naviMarker.getObject() == naviBean){
                                            naviMarker = null;
                                            naviBean = null;
                                            naviBtn.setVisibility(View.GONE);
                                            return false;
                                        }
                                        naviBean = (OrderInfoDetailBean.WorkOrderBean.ListBean) marker.getObject();
                                        naviMarker = marker;
                                        naviBtn.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                });

                            }
                        } else {
                            showFail();
                            Toast.makeText(getActivity(), orderInfoDetailBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void searchList(int userId, int type, String workAddress, String pageNo, String pageSize) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_LIST + "?userId=" + userId + "&type=" + type + "&workAddress=" + workAddress + "&pageNo=" + pageNo + "&pageSize=" + pageSize)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        final OrderListBean orderListBean = gson.fromJson(response.body(), OrderListBean.class);
                        if (orderListBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (orderListBean.getPage().getList() == null || orderListBean.getPage().getList().size() == 0) {
                                searchLv.setVisibility(View.GONE);
//                                Toast.makeText(getActivity(), "没有搜到相关数据！", Toast.LENGTH_SHORT).show();
                            } else if (orderListBean.getPage().getList().size() > 0) {
//                                List<String> strings = new ArrayList<>();
//                                searchLv.setVisibility(View.VISIBLE);
//                                for (int i = 0; i < orderListBean.getPage().getList().size(); i++) {
//                                    strings.add(orderListBean.getPage().getList().get(i).getWorkAddress());
//                                }
//                                adapter = new StringListAdapter(getActivity(), strings);
//                                searchLv.setAdapter(adapter);
//                                searchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                        mainActivity.hideSoftInput(view);
//                                        Intent intent = new Intent(mainActivity, OrderInfoDetailActivity.class);
//                                        intent.putExtra(ParamUtil.WORK_ORDER_ID, orderListBean.getPage().getList().get(position).getWorkOrderId());
//                                        startActivity(intent);
//                                    }
//                                });

                                final List<String> strings = new ArrayList<>();
                                searchLv.setVisibility(View.VISIBLE);
                                for (int i = 0; i < orderListBean.getPage().getList().size(); i++) {
                                    strings.add(orderListBean.getPage().getList().get(i).getWorkAddress());
                                }
                                StringListAdapter adapter = new StringListAdapter(getActivity(), strings);
                                searchLv.setAdapter(adapter);
                                searchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        mainActivity.hideSoftInput(view);
                                        searchLv.setVisibility(View.GONE);
                                        orderDetail(orderListBean.getPage().getList().get(position).getWorkOrderId());
                                    }
                                });
                            }
                        } else {
                            showFail();
                            Toast.makeText(getActivity(), orderListBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        showFail();
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
