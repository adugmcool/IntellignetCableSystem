package com.system.intellignetcable.activity;

import android.app.ProgressDialog;
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
import com.system.intellignetcable.bean.OrderCheckBean;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by adu on 2018/11/29.
 */

public class OrderStatusActivity extends BaseActivity {
    private static final String TAG = "OrderStatusFragment";
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.status_iv)
    ImageView statusIv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    Unbinder unbinder;
    private int type;
    private Gson gson;
    private int workOrderId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_status);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleTv.setText(R.string.worksheet_status);
        gson = new Gson();
        type = getIntent().getIntExtra(ParamUtil.ORDER_STATUS, 0);
        workOrderId = getIntent().getIntExtra(ParamUtil.WORK_ORDER_ID, 0);
        updateUI();
    }

    private void updateUI(){//3表示通过，4表示驳回
        if (type == 3){
            statusIv.setImageResource(R.drawable.status_pass);
            statusTv.setText(R.string.pass);
        }else {
            statusIv.setImageResource(R.drawable.status_fail);
            statusTv.setText(R.string.reject);
        }
    }

    @OnClick(R.id.back_iv)
    public void onBackIvClicked() {
        finish();
    }

    @OnClick(R.id.submit_btn)
    public void onSubmitBtnClicked() {
        String content = contentEt.getText().toString();
        if (content.isEmpty()){
            Toast.makeText(OrderStatusActivity.this, "请输入审核意见！", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog = showProgressDialog(getResources().getString(R.string.submiting));
        orderCheck(content, type, workOrderId);
    }

    private void orderCheck(String reason, int status, int workOrderId){
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_CHECK + "?rejectReason="+reason + "&status=" + status+"&workOrderId="+workOrderId)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        progressDialog.dismiss();
                        OrderCheckBean orderCheckBean = gson.fromJson(response.body(), OrderCheckBean.class);
                        if (orderCheckBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            finish();
                        }else {
                            Toast.makeText(OrderStatusActivity.this, orderCheckBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        progressDialog.dismiss();
                        Toast.makeText(OrderStatusActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误："+ response.code() + "-------" + response.message());
                    }
                });
    }
}
