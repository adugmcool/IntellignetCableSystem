package com.system.intellignetcable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.system.intellignetcable.R;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;

/**
 * Created by adu on 2018/11/27.
 */

public class NavigationActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initData();
    }

    private void initData() {
        String token = (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, "");
        if (token.equals("")){
            startActivity(new Intent(this, LoginActivity.class));
        }else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}
