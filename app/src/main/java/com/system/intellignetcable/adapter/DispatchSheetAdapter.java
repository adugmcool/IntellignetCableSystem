package com.system.intellignetcable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.intellignetcable.R;
import com.system.intellignetcable.bean.UsersListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adu on 2018/11/24.
 */

public class DispatchSheetAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<UsersListBean.UsersBean> stringList;
    private int currentSelector = -1;

    public DispatchSheetAdapter(Context context, List<UsersListBean.UsersBean> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.stringList = list;
    }

    public void setCurrentSelector(int currentSelector) {
        this.currentSelector = currentSelector;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return stringList == null ? 0 : stringList.size();
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
            convertView = inflater.inflate(R.layout.adapter_dispatch_sheet_name, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkTv.setText(stringList.get(position).getUserName());
        viewHolder.checkIv.setSelected(currentSelector == position);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.check_iv)
        ImageView checkIv;
        @BindView(R.id.check_tv)
        TextView checkTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
