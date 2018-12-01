package com.system.intellignetcable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.system.intellignetcable.R;
import com.system.intellignetcable.bean.LoginBean;
import com.system.intellignetcable.util.UrlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adu on 2018/11/26.
 */

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.login_name_et)
    EditText loginNameEt;
    @BindView(R.id.login_password_et)
    EditText loginPasswordEt;
    @BindView(R.id.register_btn)
    Button registerBtn;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleTv.setText(R.string.register);
        gson = new Gson();
    }

    @OnClick(R.id.back_iv)
    public void onBackIvClicked() {
        finish();
    }

    @OnClick(R.id.register_btn)
    public void onRegisterBtnClicked() {
        String phone = loginNameEt.getText().toString();
        String password = loginPasswordEt.getText().toString();
        if (phone.isEmpty()) {
            Toast.makeText(this, "请输入手机号！", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
            return;
        }
        register(phone, password);
    }

    private void register(String phone, String password){
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_REGISTER + "?mobile=" + phone + "&password=" + password)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(RegisterActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }
}
