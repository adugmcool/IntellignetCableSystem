package com.system.intellignetcable.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.system.intellignetcable.R;
import com.system.intellignetcable.adapter.PopupStatusAdapter;

import java.util.List;

/**
 * Created by zydu on 2018/11/27.
 */

public class OrderStatusPopup extends PopupWindow implements AdapterView.OnItemClickListener{
    private Context context;
    private List<String> stringList;
    private PopupStatusAdapter popupStatusAdapter;

    public OrderStatusPopup(Context context, List<String> stringList){
        this.context = context;
        this.stringList = stringList;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_list, null);
        setContentView(view);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setWidth(256);
        this.setHeight(240);
        this.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
        initData(view);
    }

    private void initData(View view) {
        ListView listView = view.findViewById(R.id.popup_lv);
        popupStatusAdapter = new PopupStatusAdapter(context, stringList);
        listView.setAdapter(popupStatusAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.dismiss();
        onListItemClickListener.listItemClick(position);
    }

    private OnListItemClickListener onListItemClickListener;

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener){
        this.onListItemClickListener = onListItemClickListener;
    }

    public interface OnListItemClickListener{
        void listItemClick(int position);
    }
}
