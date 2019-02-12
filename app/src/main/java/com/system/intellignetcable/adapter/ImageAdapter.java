package com.system.intellignetcable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.system.intellignetcable.R;
import com.system.intellignetcable.bean.PicStringBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zydu on 2018/11/30.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<PicStringBean> list;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, List<PicStringBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 1 : list.size() + 1;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
//        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_imageview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
        if (list == null){
            viewHolder.image.setImageResource(R.drawable.add_photo);
            viewHolder.deleteIv.setVisibility(View.GONE);
            viewHolder.image.setEnabled(true);
            viewHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddOrDeleteImageClickListener.addImageClick();
                }
            });
        }else {
            if (position != list.size()){
                Glide.with(context).load(list.get(position).getPath()).into(viewHolder.image);
                viewHolder.image.setEnabled(false);
                viewHolder.deleteIv.setVisibility(View.VISIBLE);
                viewHolder.deleteIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAddOrDeleteImageClickListener.deleteImageClick(position);
                    }
                });
            }else {
                viewHolder.image.setBackgroundResource(R.drawable.add_photo);
                viewHolder.deleteIv.setVisibility(View.GONE);
                viewHolder.image.setEnabled(true);
                viewHolder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAddOrDeleteImageClickListener.addImageClick();
                    }
                });
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.delete_iv)
        ImageView deleteIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private OnAddOrDeleteImageClickListener onAddOrDeleteImageClickListener;

    public void setOnDeleteImageClickListener(OnAddOrDeleteImageClickListener onAddOrDeleteImageClickListener){
        this.onAddOrDeleteImageClickListener = onAddOrDeleteImageClickListener;
    }

    public interface OnAddOrDeleteImageClickListener{
        void deleteImageClick(int pos);
        void addImageClick();
    }
}
