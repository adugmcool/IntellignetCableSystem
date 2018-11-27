package com.system.intellignetcable.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
}
