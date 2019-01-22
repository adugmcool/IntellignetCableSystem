package com.system.intellignetcable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.system.intellignetcable.R;
import com.system.intellignetcable.bean.OrderListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewOrderAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<OrderListBean.PageBean.ListBean> list;

    public NewOrderAdapter(Context context, List<OrderListBean.PageBean.ListBean> list) {
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
            convertView = inflater.inflate(R.layout.item_order_new, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.placeTv.setText(list.get(position).getWorkAddress());
        viewHolder.numberTv.setText(String.valueOf(list.get(position).getSignsNum()));
        viewHolder.timeTv.setText(list.get(position).getCreateDate());
        viewHolder.nameTv.setText(list.get(position).getSendUserName());
        if (list.get(position).getStatus() == 0) {
            viewHolder.statusTv.setText(R.string.unexecuted);
            viewHolder.statusTv.setTextColor(context.getResources().getColor(R.color.color_757575));
        } else if (list.get(position).getStatus() == 1) {
            viewHolder.statusTv.setText(R.string.submission);
            viewHolder.statusTv.setTextColor(context.getResources().getColor(R.color.color_757575));
        } else if (list.get(position).getStatus() == 2) {
            viewHolder.statusTv.setText(R.string.pending_review);
            viewHolder.statusTv.setTextColor(context.getResources().getColor(R.color.color_FF1989FA));
        } else if (list.get(position).getStatus() == 3) {
            viewHolder.statusTv.setText(R.string.finished);
            viewHolder.statusTv.setTextColor(context.getResources().getColor(R.color.color_0DB300));
        } else if (list.get(position).getStatus() == 4) {
            viewHolder.statusTv.setText(R.string.rejected);
            viewHolder.statusTv.setTextColor(context.getResources().getColor(R.color.color_D0021B));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.status_tv)
        TextView statusTv;
        @BindView(R.id.number_tv)
        TextView numberTv;
        @BindView(R.id.time_tv)
        TextView timeTv;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.place_tv)
        TextView placeTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
