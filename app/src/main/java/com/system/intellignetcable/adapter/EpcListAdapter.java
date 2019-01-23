package com.system.intellignetcable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.intellignetcable.R;
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
        MapDataDetailBean.ListBean listBean = list.get(position);
        viewHolder.indexTv.setText((position + 1) + "");
        viewHolder.epcTv.setText(listBean.getEpc());
        viewHolder.addressTv.setText("地址: " + listBean.getDetailAddress());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.index_tv)
        TextView indexTv;
        @BindView(R.id.epc_tv)
        TextView epcTv;
        @BindView(R.id.address_tv)
        TextView addressTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
