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

import com.system.intellignetcable.R;
import com.system.intellignetcable.bean.SignageManagementBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zydu on 2018/11/30.
 */

public class SignageOrderInfoAdapter extends RecyclerView.Adapter {
    private static final String TAG = "SignageOrderInfoAdapter";
    private static final int TEXT_TYPE = 0;
    private static final int DATE_TYPE = 1;
    private static final int LIST_TYPE = 2;
    private static final int NAME_TYPE = 3;
    private static final int IAMGE_TYPE = 4;
    private static final int MAP_TYPE = 5;
    private Context context;
    private List<SignageManagementBean.SignBoardBean.TemplateValuesBean> list;
    private SignageManagementBean.SignBoardBean signBoardBean;
    private List<String> imageList = new ArrayList<>();
    private LayoutInflater inflater;
    private SimpleDateFormat simpleDateFormat;
    private int mYear;
    private int mMonth;
    private int mDay;

    public SignageOrderInfoAdapter(Context context, List<SignageManagementBean.SignBoardBean.TemplateValuesBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        this.mYear = ca.get(Calendar.YEAR);
        this.mMonth = ca.get(Calendar.MONTH);
        this.mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    // 将时间戳转换成日期格式
    private String toDate(String s){
        Date date = new Date(Long.parseLong(s));
        return simpleDateFormat.format(date);
    }

    //日期格式转成时间戳
    public long getStringToDate(String dateString) {
        Date date = new Date();
        try{
            date = simpleDateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    // 图片String转list集合
    private void imageStringToList(){
        if (signBoardBean.getImagesUrls() != null && signBoardBean.getImagesUrls().length() > 0){
            String[] images = signBoardBean.getImagesUrls().split(",");
            for (String s : images){

            }
        }
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
        } else {
            view = inflater.inflate(R.layout.item_info_list, parent, false);
            return (new ListHolder(view));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TextHolder){
            ((TextHolder) holder).textNameTv.setText(list.get(position).getFieldName() + ":");
            ((TextHolder) holder).textEt.setText(list.get(position).getFieldValue());
            ((TextHolder) holder).textEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    list.get(position).setFieldValue(s.toString());
                    Log.i(TAG, list.get(position).getFieldName() + "-----" + list.get(position).getFieldValue());
                }
            });
        }else if (holder instanceof DateHolder){
            ((DateHolder) holder).dateNameTv.setText(list.get(position).getFieldName() + ":");
            if (list.get(position).getFieldValue() != null) {
                ((DateHolder) holder).dateTv.setText(toDate(list.get(position).getFieldValue()));
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
                            list.get(position).setFieldValue(String.valueOf(getStringToDate(days)));
                            Log.i(TAG, list.get(position).getFieldName() + "-----" + toDate(list.get(position).getFieldValue()));
                        }
                    }, mYear, mMonth, mDay).show();
                }
            });
        }else {
            ((ListHolder) holder).listNameTv.setText(list.get(position).getFieldName() + ":");
            if (list.get(position).getDicts() != null && list.get(position).getDicts().size() > 0){
                List<String> values = new ArrayList<>();
                int selectItem = -1;
                for (int i = 0; i < list.get(position).getDicts().size(); i++){
                    values.add(list.get(position).getDicts().get(i).getDictText());
                    if (list.get(position).getFieldValue() != null && list.get(position).getFieldValue().equals(list.get(position).getDicts().get(i).getDictCode())){
                        selectItem = i;
                    }
                }
                // 声明一个ArrayAdapter用于存放简单数据
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,values);
                // 把定义好的Adapter设定到spinner中
                ((ListHolder) holder).listSpinner.setAdapter(adapter);
                if (selectItem != -1) {
                    ((ListHolder) holder).listSpinner.setSelection(selectItem, true);
                }else {

                }
                ((ListHolder) holder).listSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        list.get(position).setFieldValue(list.get(position).getDicts().get(i).getDictCode());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0 || position == list.size() + 1 || position == list.size() + 1){
//            return NAME_TYPE;
//        }
        if (list.get(position).getShowType().equals("date")) {
            return DATE_TYPE;
        } else if (list.get(position).getShowType().equals("list")) {
            return LIST_TYPE;
        } else {
            return TEXT_TYPE;
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

    class ListHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.list_name_tv)
        TextView listNameTv;
        @BindView(R.id.list_spinner)
        Spinner listSpinner;

        ListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
