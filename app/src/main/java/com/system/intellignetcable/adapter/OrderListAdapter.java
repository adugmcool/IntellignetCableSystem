package com.system.intellignetcable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.intellignetcable.R;
import com.system.intellignetcable.bean.OrderListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adu on 2018/11/25.
 */

public class OrderListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<OrderListBean.PageBean.ListBean> list;

    public OrderListAdapter(Context context, List<OrderListBean.PageBean.ListBean> list) {
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
            convertView = inflater.inflate(R.layout.adapter_order_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.orderNameTv.setText(list.get(position).getWorkAddress());
        if (list.get(position).getStatus() == 0){
            viewHolder.statusTv.setText(R.string.unexecuted);
            viewHolder.statusTv.setTextColor(context.getResources().getColor(R.color.color_757575));
        }else if (list.get(position).getStatus() == 1){
            viewHolder.statusTv.setText(R.string.submission);
            viewHolder.statusTv.setTextColor(context.getResources().getColor(R.color.color_757575));
        }else if (list.get(position).getStatus() == 2){
            viewHolder.statusTv.setText(R.string.pending_review);
            viewHolder.statusTv.setTextColor(context.getResources().getColor(R.color.color_FF1989FA));
        }else if (list.get(position).getStatus() == 3){
            viewHolder.statusTv.setText(R.string.finished);
            viewHolder.statusTv.setTextColor(context.getResources().getColor(R.color.color_0DB300));
        }else if (list.get(position).getStatus() == 4){
            viewHolder.statusTv.setText(R.string.rejected);
            viewHolder.statusTv.setTextColor(context.getResources().getColor(R.color.color_D0021B));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.order_name_tv)
        TextView orderNameTv;
        @BindView(R.id.next_iv)
        ImageView nextIv;
        @BindView(R.id.status_tv)
        TextView statusTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
