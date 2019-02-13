package com.system.intellignetcable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
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
import com.system.intellignetcable.bean.MapDataDetailBean;
import com.system.intellignetcable.bean.OrderInfoDetailBean;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adu on 2018/11/29.
 */

public class OrderInfoDetailActivity extends BaseActivity {
    private static final String TAG = "OrderInfoDetailActivity";
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.hint_tv)
    TextView hintTv;
    @BindView(R.id.epcs_llayout)
    LinearLayout epcsLlayout;
    @BindView(R.id.line_view)
    View lineView;

    private int workOrderId;
    @BindView(R.id.detail_ll)
    LinearLayout detailLl;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.signs_number_tv)
    TextView signsNumberTv;
    @BindView(R.id.dispatch_time_tv)
    TextView dispatchTimeTv;
    @BindView(R.id.dispatch_name_tv)
    TextView dispatchNameTv;
    @BindView(R.id.place_tv)
    TextView placeTv;
    @BindView(R.id.pass_btn)
    Button passBtn;
    @BindView(R.id.reject_btn)
    Button rejectBtn;
    @BindView(R.id.status_ll)
    LinearLayout statusLl;
    private Gson gson;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_info_detail);
        ButterKnife.bind(this);
        setLoadingView(hintTv, contentLl);
        initData();
        initMap();
    }

    private void initData() {
        titleTv.setText(R.string.worksheet_detail);
        workOrderId = getIntent().getIntExtra(ParamUtil.WORK_ORDER_ID, 0);
        type = (int) SharedPreferencesUtil.get(this, ParamUtil.TYPE, 2);
        gson = new Gson();
        showLoading();
        orderDetail(workOrderId);
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }

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


    private void orderDetail(int orderId) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_DETAIL)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .params("workOrderId", orderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        OrderInfoDetailBean orderInfoDetailBean = gson.fromJson(response.body(), OrderInfoDetailBean.class);
                        if (orderInfoDetailBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (orderInfoDetailBean.getWorkOrder() == null) {
                                showNoData();
                            } else {
                                showDataSuc();
                                updateData(orderInfoDetailBean.getWorkOrder());

                                List<OrderInfoDetailBean.WorkOrderBean.ListBean> epcs = orderInfoDetailBean.getWorkOrder().getList();
                                if (epcs != null && !epcs.isEmpty()) {
                                    lineView.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < epcs.size(); i++) {
                                        final String epcId = epcs.get(i).getEpc();
                                        final OrderInfoDetailBean.WorkOrderBean.ListBean listBean = epcs.get(i);
                                        View view = LayoutInflater.from(OrderInfoDetailActivity.this).inflate(R.layout.item_epc, null);
                                        TextView naviBtn = view.findViewById(R.id.navi_btn);
                                        TextView indexTv = view.findViewById(R.id.index_tv);
                                        TextView epcTv = view.findViewById(R.id.epc_tv);
                                        TextView addressTv = view.findViewById(R.id.address_tv);
                                        indexTv.setText((i + 1) + "");
                                        epcTv.setText(epcId);
                                        addressTv.setText("地址: " + epcs.get(i).getDetailAddress());
                                        view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(OrderInfoDetailActivity.this, SignageManagementActivity.class);
                                                intent.putExtra("epcId", epcId);
                                                startActivity(intent);
                                            }
                                        });
                                        naviBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(latLng == null){
                                                    Toast.makeText(OrderInfoDetailActivity.this, "正在获取当前位置", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                Poi start = new Poi("", latLng, "");
                                                Poi end = new Poi(listBean.getDetailAddress(), new LatLng(Double.parseDouble(listBean.getLatitude()), Double.parseDouble(listBean.getLongitude())), "B000A83M61");
                                                AmapNaviPage.getInstance().showRouteActivity(OrderInfoDetailActivity.this, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), new INaviInfoCallback() {
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
                                        });
                                        epcsLlayout.addView(view);
                                    }

                                } else {
                                    lineView.setVisibility(View.GONE);
                                }

                            }
                        } else {
                            showFail();
                            Toast.makeText(OrderInfoDetailActivity.this, orderInfoDetailBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        showFail();
                        Toast.makeText(OrderInfoDetailActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    private void updateData(OrderInfoDetailBean.WorkOrderBean workOrderBean) {
        if (workOrderBean.getStatus() == 0) {
            statusTv.setText(R.string.unexecuted);
            statusTv.setTextColor(getResources().getColor(R.color.color_757575));
            statusLl.setVisibility(View.GONE);
        } else if (workOrderBean.getStatus() == 1) {
            statusTv.setText(R.string.submission);
            statusTv.setTextColor(getResources().getColor(R.color.color_757575));
            statusLl.setVisibility(View.GONE);
        } else if (workOrderBean.getStatus() == 2) {
            statusTv.setText(R.string.pending_review);
            statusTv.setTextColor(getResources().getColor(R.color.color_FF1989FA));
            if (type == 1) {
                statusLl.setVisibility(View.VISIBLE);
            } else {
                statusLl.setVisibility(View.GONE);
            }
        } else if (workOrderBean.getStatus() == 3) {
            statusTv.setText(R.string.finished);
            statusTv.setTextColor(getResources().getColor(R.color.color_0DB300));
            statusLl.setVisibility(View.GONE);
        } else if (workOrderBean.getStatus() == 4) {
            statusTv.setText(R.string.rejected);
            statusTv.setTextColor(getResources().getColor(R.color.color_D0021B));
            statusLl.setVisibility(View.GONE);
        }
        signsNumberTv.setText(String.valueOf(workOrderBean.getSignsNum()));
        dispatchTimeTv.setText(workOrderBean.getCreateDate());
        dispatchNameTv.setText(workOrderBean.getSendUserName());
        placeTv.setText(workOrderBean.getUserTeamplateName());
    }

    @OnClick(R.id.pass_btn)
    public void onPassBtnClicked() {
        Intent intent = new Intent(this, OrderStatusActivity.class);
        intent.putExtra(ParamUtil.ORDER_STATUS, 3);
        intent.putExtra(ParamUtil.WORK_ORDER_ID, workOrderId);
        startActivity(intent);
    }

    @OnClick(R.id.reject_btn)
    public void onRejectBtnClicked() {
        Intent intent = new Intent(this, OrderStatusActivity.class);
        intent.putExtra(ParamUtil.ORDER_STATUS, 4);
        intent.putExtra(ParamUtil.WORK_ORDER_ID, workOrderId);
        startActivity(intent);
    }
}
