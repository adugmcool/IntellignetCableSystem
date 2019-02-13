package com.system.intellignetcable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.OrderInfoDetailActivity;
import com.system.intellignetcable.bean.MapDataDetailBean;
import com.system.intellignetcable.bean.OrderListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adu on 2018/11/25.
 */

public class EpcListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<MapDataDetailBean.ListBean> list;
    private LatLng latLng;

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public EpcListAdapter(Context context, List<MapDataDetailBean.ListBean > list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_epc, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final MapDataDetailBean.ListBean listBean = list.get(position);
        viewHolder.indexTv.setText((position + 1) + "");
        viewHolder.epcTv.setText(listBean.getEpc());
        viewHolder.addressTv.setText("地址: " + listBean.getDetailAddress());
        viewHolder.naviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latLng == null){
                    Toast.makeText(context, "正在获取当前位置", Toast.LENGTH_SHORT).show();
                    return;
                }
                Poi start = new Poi("", latLng, "");
                Poi end = new Poi(listBean.getDetailAddress(), new LatLng(Double.parseDouble(listBean.getLatitude()), Double.parseDouble(listBean.getLongitude())), "B000A83M61");
                AmapNaviPage.getInstance().showRouteActivity(context, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), new INaviInfoCallback() {
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
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.index_tv)
        TextView indexTv;
        @BindView(R.id.epc_tv)
        TextView epcTv;
        @BindView(R.id.address_tv)
        TextView addressTv;
        @BindView(R.id.navi_btn)
        TextView naviBtn;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
