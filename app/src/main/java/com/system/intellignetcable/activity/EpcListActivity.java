package com.system.intellignetcable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.LatLng;
import com.system.intellignetcable.R;
import com.system.intellignetcable.adapter.EpcListAdapter;
import com.system.intellignetcable.bean.MapDataDetailBean;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adu on 2018/11/24.
 */

public class EpcListActivity extends BaseActivity {
    private static final String TAG = "EpcListActivity";
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.right_iv)
    TextView rightIv;
    @BindView(R.id.order_list)
    ListView orderList;
    private ArrayList<MapDataDetailBean.ListBean> listBeans;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private LatLng latLng;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    //声明mListener对象，定位监听器
    private LocationSource.OnLocationChangedListener mListener = null;

    private void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        double mCurrentLat = aMapLocation.getLatitude();
                        double mCurrentLon = aMapLocation.getLongitude();
                        latLng = new LatLng(mCurrentLat, mCurrentLon);//构造一个位置
                        epcListAdapter.setLatLng(latLng);
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
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

    private  EpcListAdapter epcListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBeans = (ArrayList<MapDataDetailBean.ListBean>) getIntent().getSerializableExtra("listBeans");

        setContentView(R.layout.activity_epc_list);
        ButterKnife.bind(this);
        titleTv.setText("EPC列表");
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        epcListAdapter = new EpcListAdapter(this, listBeans);
        orderList.setAdapter(epcListAdapter);
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EpcListActivity.this, SignageManagementActivity.class);
                intent.putExtra("epcId", listBeans.get(position).getEpc());
                startActivity(intent);
            }
        });
        initMap();
    }

}
