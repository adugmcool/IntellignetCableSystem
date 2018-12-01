package com.system.intellignetcable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.system.intellignetcable.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zydu on 2018/11/30.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, List<String> list) {
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
            convertView = inflater.inflate(R.layout.item_imageview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(list.get(position)).into(viewHolder.image);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
