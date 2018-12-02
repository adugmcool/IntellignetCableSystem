package com.system.intellignetcable.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.system.intellignetcable.R;
import com.system.intellignetcable.util.StatusBarColorUtils;

/**
 * Created by adu on 2018/11/24.
 */

public class BaseActivity extends AppCompatActivity {
    protected TextView baseHintTv;
    protected View baseContentRl;

    protected void setLoadingView(TextView baseHintTv, View baseContentRl){
        this.baseContentRl = baseContentRl;
        this.baseHintTv = baseHintTv;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarColorUtils.setStatusBarColor(this, R.color.color_FF1989FA);
    }

    // 隐藏软键盘
    public void hideSoftInput(View view){
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected ProgressDialog showProgressDialog(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        return progressDialog;
    }

    protected ProgressDialog showProgressDialog(String hint){
        ProgressDialog progressDialog = ProgressDialog.show(this,null, hint);
        return progressDialog;
    }

    protected ProgressDialog showProgressDialog(String title, String hint){
        ProgressDialog progressDialog = ProgressDialog.show(this,title, hint);
        return progressDialog;
    }


    protected void showLoading(){
        baseHintTv.setVisibility(View.VISIBLE);
        baseHintTv.setText(R.string.data_loading);
        baseContentRl.setVisibility(View.GONE);
    }

    protected void showDataSuc(){
        baseHintTv.setVisibility(View.GONE);
        baseContentRl.setVisibility(View.VISIBLE);
    }

    protected void showFail(){
        baseHintTv.setVisibility(View.VISIBLE);
        baseHintTv.setText(R.string.data_load_fail);
        baseContentRl.setVisibility(View.GONE);
    }

    protected void showNoData(){
        baseHintTv.setVisibility(View.VISIBLE);
        baseHintTv.setText(R.string.not_data);
        baseContentRl.setVisibility(View.GONE);
    }
}
