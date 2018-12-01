package com.system.intellignetcable.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.system.intellignetcable.R;
import com.system.intellignetcable.util.StatusBarColorUtils;

/**
 * Created by adu on 2018/11/24.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarColorUtils.setStatusBarColor(this, R.color.color_FF1989FA);
    }

    // 隐藏软键盘
    public void hideSoftInput(View view){
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
