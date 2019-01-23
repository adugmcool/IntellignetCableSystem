package com.system.intellignetcable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.system.intellignetcable.R;
import com.system.intellignetcable.bean.MapDataDetailBean;
import com.system.intellignetcable.bean.OrderInfoDetailBean;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adu on 2018/11/29.
 */

public class OrderInfoDetailActivity extends BaseActivity {
    private static final String TAG = "OrderInfoDetailActivity";
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.hint_tv)
    TextView hintTv;
    @BindView(R.id.epcs_llayout)
    LinearLayout epcsLlayout;
    @BindView(R.id.line_view)
    View lineView;

    private int workOrderId;
    @BindView(R.id.detail_ll)
    LinearLayout detailLl;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.signs_number_tv)
    TextView signsNumberTv;
    @BindView(R.id.dispatch_time_tv)
    TextView dispatchTimeTv;
    @BindView(R.id.dispatch_name_tv)
    TextView dispatchNameTv;
    @BindView(R.id.place_tv)
    TextView placeTv;
    @BindView(R.id.pass_btn)
    Button passBtn;
    @BindView(R.id.reject_btn)
    Button rejectBtn;
    @BindView(R.id.status_ll)
    LinearLayout statusLl;
    private Gson gson;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_info_detail);
        ButterKnife.bind(this);
        setLoadingView(hintTv, contentLl);
        initData();
    }

    private void initData() {
        titleTv.setText(R.string.worksheet_detail);
        workOrderId = getIntent().getIntExtra(ParamUtil.WORK_ORDER_ID, 0);
        type = (int) SharedPreferencesUtil.get(this, ParamUtil.TYPE, 2);
        gson = new Gson();
        showLoading();
        orderDetail(workOrderId);
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }

    private void orderDetail(int orderId) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_DETAIL)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .params("workOrderId", orderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        OrderInfoDetailBean orderInfoDetailBean = gson.fromJson(response.body(), OrderInfoDetailBean.class);
                        if (orderInfoDetailBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (orderInfoDetailBean.getWorkOrder() == null) {
                                showNoData();
                            } else {
                                showDataSuc();
                                updateData(orderInfoDetailBean.getWorkOrder());

                                List<String> epcs = orderInfoDetailBean.getWorkOrder().getList();
                                if (epcs != null && !epcs.isEmpty()) {
                                    lineView.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < epcs.size(); i++) {
                                        final String epcId = epcs.get(i);
                                        View view = LayoutInflater.from(OrderInfoDetailActivity.this).inflate(R.layout.item_epc, null);
                                        TextView indexTv = view.findViewById(R.id.index_tv);
                                        TextView epcTv = view.findViewById(R.id.epc_tv);
                                        TextView addressTv = view.findViewById(R.id.address_tv);
                                        indexTv.setText((i + 1) + "");
                                        epcTv.setText(epcId);
                                        addressTv.setText("地址: " + orderInfoDetailBean.getWorkOrder().getWorkAddress());
                                        view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(OrderInfoDetailActivity.this, SignageManagementActivity.class);
                                                intent.putExtra("epcId", epcId);
                                                startActivity(intent);
                                            }
                                        });
                                        epcsLlayout.addView(view);
                                    }

                                } else {
                                    lineView.setVisibility(View.GONE);
                                }

                            }
                        } else {
                            showFail();
                            Toast.makeText(OrderInfoDetailActivity.this, orderInfoDetailBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        showFail();
                        Toast.makeText(OrderInfoDetailActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    private void updateData(OrderInfoDetailBean.WorkOrderBean workOrderBean) {
        if (workOrderBean.getStatus() == 0) {
            statusTv.setText(R.string.unexecuted);
            statusTv.setTextColor(getResources().getColor(R.color.color_757575));
            statusLl.setVisibility(View.GONE);
        } else if (workOrderBean.getStatus() == 1) {
            statusTv.setText(R.string.submission);
            statusTv.setTextColor(getResources().getColor(R.color.color_757575));
            statusLl.setVisibility(View.GONE);
        } else if (workOrderBean.getStatus() == 2) {
            statusTv.setText(R.string.pending_review);
            statusTv.setTextColor(getResources().getColor(R.color.color_FF1989FA));
            if (type == 1) {
                statusLl.setVisibility(View.VISIBLE);
            } else {
                statusLl.setVisibility(View.GONE);
            }
        } else if (workOrderBean.getStatus() == 3) {
            statusTv.setText(R.string.finished);
            statusTv.setTextColor(getResources().getColor(R.color.color_0DB300));
            statusLl.setVisibility(View.GONE);
        } else if (workOrderBean.getStatus() == 4) {
            statusTv.setText(R.string.rejected);
            statusTv.setTextColor(getResources().getColor(R.color.color_D0021B));
            statusLl.setVisibility(View.GONE);
        }
        signsNumberTv.setText(String.valueOf(workOrderBean.getSignsNum()));
        dispatchTimeTv.setText(workOrderBean.getCreateDate());
        dispatchNameTv.setText(workOrderBean.getSendUserName());
        placeTv.setText(workOrderBean.getUserTeamplateName());
    }

    @OnClick(R.id.pass_btn)
    public void onPassBtnClicked() {
        Intent intent = new Intent(this, OrderStatusActivity.class);
        intent.putExtra(ParamUtil.ORDER_STATUS, 3);
        intent.putExtra(ParamUtil.WORK_ORDER_ID, workOrderId);
        startActivity(intent);
    }

    @OnClick(R.id.reject_btn)
    public void onRejectBtnClicked() {
        Intent intent = new Intent(this, OrderStatusActivity.class);
        intent.putExtra(ParamUtil.ORDER_STATUS, 4);
        intent.putExtra(ParamUtil.WORK_ORDER_ID, workOrderId);
        startActivity(intent);
    }
}
