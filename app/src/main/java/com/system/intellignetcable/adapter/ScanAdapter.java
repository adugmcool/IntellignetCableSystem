package com.system.intellignetcable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.system.intellignetcable.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adu on 2018/11/25.
 */

public class ScanAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> list;

    public ScanAdapter(Context context, List<String> list) {
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
            convertView = inflater.inflate(R.layout.adapter_scan_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTv.setText(list.get(position));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.confirm_tv)
        TextView confirmTv;
        @BindView(R.id.status_tv)
        TextView statusTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
