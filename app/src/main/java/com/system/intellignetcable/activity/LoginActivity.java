package com.system.intellignetcable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adu on 2018/11/24.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.login_name_et)
    EditText loginNameEt;
    @BindView(R.id.login_password_et)
    EditText loginPasswordEt;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.register_tv)
    TextView registerTv;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        titleTv.setText(R.string.login);
        gson = new Gson();
    }

    private void initView() {
        backIv.setVisibility(View.GONE);
    }

    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        String phone = loginNameEt.getText().toString();
        String password = loginPasswordEt.getText().toString();
        if (phone.isEmpty()) {
            Toast.makeText(this, "请输入手机号！", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
            return;
        }
        loginPost(phone, password);
    }

    private void loginPost(String phone, String password) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_LOGIN + "?mobile=" + phone + "&password=" + password)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            SharedPreferencesUtil.put(LoginActivity.this, ParamUtil.TOKEN, loginBean.getToken());
                            SharedPreferencesUtil.put(LoginActivity.this, ParamUtil.USER_ID, loginBean.getUser().getUserId());
                        } else {
                            Toast.makeText(LoginActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(LoginActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    @OnClick(R.id.register_tv)
    public void onRegisterClicked() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
