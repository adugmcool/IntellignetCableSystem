package com.system.intellignetcable.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.system.intellignetcable.R;
import com.system.intellignetcable.bean.SignageManagementBean;
import com.system.intellignetcable.fragment.LocationFragment;
import com.system.intellignetcable.view.MyGridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zydu on 2018/11/30.
 */

public class SignageOrderInfoAdapter extends RecyclerView.Adapter{
    private static final String TAG = "SignageOrderInfoAdapter";
    private static final int TEXT_TYPE = 0;
    private static final int DATE_TYPE = 1;
    private static final int LIST_TYPE = 2;
    private static final int NAME_TYPE = 3;
    private static final int IMAGE_TYPE = 4;
    private static final int MAP_TYPE = 5;
    private Context context;
    private List<SignageManagementBean.SignBoardBean.TemplateValuesBean> list;
    private LayoutInflater inflater;
    private SimpleDateFormat simpleDateFormat;
    private int mYear;
    private int mMonth;
    private int mDay;
    private ImageAdapter imageAdapter;
    //定位相关
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private BaiduMap mBaiduMap;
    private MapView mapView;
    private double mCurrentLat;
    private double mCurrentLon;
    private String mCurrentAdress;

    public double getmCurrentLat() {
        return mCurrentLat;
    }

    public double getmCurrentLon() {
        return mCurrentLon;
    }

    public String getmCurrentAdress() {
        return mCurrentAdress;
    }

    public SignageOrderInfoAdapter(Context context, List<SignageManagementBean.SignBoardBean.TemplateValuesBean> list, ImageAdapter imageAdapter) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        this.mYear = ca.get(Calendar.YEAR);
        this.mMonth = ca.get(Calendar.MONTH);
        this.mDay = ca.get(Calendar.DAY_OF_MONTH);
        this.imageAdapter = imageAdapter;
    }

    private void initMap() {
        mBaiduMap = mapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(context);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            double mCurrentAccracy = location.getRadius();
            mCurrentAdress = location.getAddress().address;
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    // 将时间戳转换成日期格式
    private String toDate(String s) {
        if (s.contains("-")){
            return s;
        }else {
            Date date = new Date(Long.parseLong(s));
            return simpleDateFormat.format(date);
        }

    }

    //日期格式转成时间戳
    public long getStringToDate(String dateString) {
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TEXT_TYPE) {
            view = inflater.inflate(R.layout.item_info_text, parent, false);
            return (new TextHolder(view));
        } else if (viewType == DATE_TYPE) {
            view = inflater.inflate(R.layout.item_info_date, parent, false);
            return (new DateHolder(view));
        } else if (viewType == LIST_TYPE) {
            view = inflater.inflate(R.layout.item_info_list, parent, false);
            return (new ListHolder(view));
        } else if (viewType == NAME_TYPE) {
            view = inflater.inflate(R.layout.item_signage_text, parent, false);
            return (new TypeNameHolder(view));
        } else if (viewType == IMAGE_TYPE) {
            view = inflater.inflate(R.layout.item_gridview, parent, false);
            return (new GridViewHolder(view));
        } else {
            view = inflater.inflate(R.layout.item_map, parent, false);
            return (new LocationHolder(view));
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TextHolder) {
            if(list.get(position -1).getFieldName().equals("cableNumber")){
                Log.i("onBindViewHolder", "position : " + position + " --- " + (new Gson().toJson(list)));
            }
            final String fieldName = list.get(position - 1).getFieldDesc();
            final String fieldValue = list.get(position -1).getFieldValue();
            ((TextHolder) holder).textEt.setTag(R.id.cable_id, position);
            ((TextHolder) holder).textNameTv.setText(fieldName + ":");
            ((TextHolder) holder).textEt.setText(fieldValue);
            if (((TextHolder) holder).textEt.getTag() instanceof TextWatcher) {
                ((TextHolder) holder).textEt.removeTextChangedListener((TextWatcher)((TextHolder) holder).textEt.getTag() );
            }
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(position == (int)((TextHolder) holder).textEt.getTag(R.id.cable_id)){
                        for (int i = 0; i < list.size(); i++) {
                            String name = list.get(i).getFieldDesc();
                            if(name.equals(fieldName)){
                                list.get(i).setFieldValue(s.toString());
                                break;
                            }
                        }
                    }
//                    list.get(position -1).setFieldValue(s.toString());
//                    Log.i(TAG, list.get(position -1).getFieldName() + "-----" + list.get(position -1).getFieldValue());
                }
            };
            ((TextHolder) holder).textEt.addTextChangedListener(textWatcher);
            ((TextHolder) holder).textEt.setTag(textWatcher);
        } else if (holder instanceof DateHolder) {
            ((DateHolder) holder).dateNameTv.setText(list.get(position -1).getFieldDesc() + ":");
            if (list.get(position -1).getFieldValue() != null) {
                ((DateHolder) holder).dateTv.setText(toDate(list.get(position -1).getFieldValue()));
                list.get(position -1).setFieldValue(toDate(list.get(position -1).getFieldValue()));
            }
            ((DateHolder) holder).dateTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 调用时间选择器
                    new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            String days;
                            if (mMonth + 1 < 10) {
                                if (mDay < 10) {
                                    days = new StringBuffer().append(mYear).append("-").append("0").
                                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                                } else {
                                    days = new StringBuffer().append(mYear).append("-").append("0").
                                            append(mMonth + 1).append("-").append(mDay).toString();
                                }

                            } else {
                                if (mDay < 10) {
                                    days = new StringBuffer().append(mYear).append("-").
                                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                                } else {
                                    days = new StringBuffer().append(mYear).append("-").
                                            append(mMonth + 1).append("-").append(mDay).toString();
                                }

                            }
                            ((DateHolder) holder).dateTv.setText(days);
//                            list.get(position -1).setFieldValue(String.valueOf(getStringToDate(days)));
                            list.get(position -1).setFieldValue(days);
//                            Log.i(TAG, list.get(position -1).getFieldName() + "-----" + toDate(list.get(position -1).getFieldValue()));
                        }
                    }, mYear, mMonth, mDay).show();
                }
            });
        } else if (holder instanceof ListHolder){
            ((ListHolder) holder).listNameTv.setText(list.get(position -1).getFieldDesc() + ":");
            if (list.get(position -1).getDicts() != null && list.get(position -1).getDicts().size() > 0) {
                List<String> values = new ArrayList<>();
                int selectItem = -1;
                for (int i = 0; i < list.get(position -1).getDicts().size(); i++) {
                    values.add(list.get(position -1).getDicts().get(i).getDictText());
                    if (list.get(position -1).getFieldValue() != null && list.get(position -1).getFieldValue().equals(list.get(position -1).getDicts().get(i).getDictCode())) {
                        selectItem = i;
                    }
                }
                // 声明一个ArrayAdapter用于存放简单数据
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, values);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // 把定义好的Adapter设定到spinner中
                ((ListHolder) holder).listSpinner.setAdapter(adapter);
                if (selectItem != -1) {
                    ((ListHolder) holder).listSpinner.setSelection(selectItem, true);
                } else {

                }
                ((ListHolder) holder).listSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        list.get(position -1).setFieldValue(list.get(position -1).getDicts().get(i).getDictCode());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }else if (holder instanceof TypeNameHolder){
            if (position == 0) {
                ((TypeNameHolder) holder).typeTv.setText(R.string.worksheet_info);
            }else if (position == list.size() + 1){
                ((TypeNameHolder) holder).typeTv.setText(R.string.picture_info);
            }else {
                ((TypeNameHolder) holder).typeTv.setText(R.string.location);
            }
        }else if (holder instanceof GridViewHolder){
            ((GridViewHolder) holder).gridview.setAdapter(imageAdapter);
        }else if (holder instanceof LocationHolder){
            mapView = ((LocationHolder) holder).locationMv;
            initMap();
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 5 : list.size() + 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == list.size() + 1 || position == list.size() + 3) {
            return NAME_TYPE;
        }else if (position > 0 && position <= list.size()){
            if (list.get(position - 1).getShowType().equals("date")) {
                return DATE_TYPE;
            } else if (list.get(position - 1).getShowType().equals("list")) {
                return LIST_TYPE;
            } else {
                return TEXT_TYPE;
            }
        }else if (position == list.size() + 2) {
            return IMAGE_TYPE;
        } else if(position == list.size() + 4){
            return MAP_TYPE;
        }else {
            return -1;
        }
    }

    class TextHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_name_tv)
        TextView textNameTv;
        @BindView(R.id.text_et)
        EditText textEt;

        TextHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class DateHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date_name_tv)
        TextView dateNameTv;
        @BindView(R.id.date_tv)
        TextView dateTv;

        DateHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_name_tv)
        TextView listNameTv;
        @BindView(R.id.list_spinner)
        Spinner listSpinner;

        ListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class TypeNameHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type_tv)
        TextView typeTv;

        TypeNameHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gridview)
        MyGridView gridview;

        GridViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class LocationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.location_mv)
        MapView locationMv;

        LocationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
