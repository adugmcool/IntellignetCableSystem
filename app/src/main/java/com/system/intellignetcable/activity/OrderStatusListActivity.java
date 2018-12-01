package com.system.intellignetcable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.system.intellignetcable.R;
import com.system.intellignetcable.adapter.OrderListAdapter;
import com.system.intellignetcable.bean.OrderListBean;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adu on 2018/11/30.
 */

public class OrderStatusListActivity extends BaseActivity  implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {
    private static final String TAG = "OrderStatusListFragment";
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.order_list)
    ListView orderList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private static int status;
    private int pageIndex = 1;
    private int pageSize = 10;
    private Gson gson;
    private OrderListAdapter orderListAdapter;
    private List<OrderListBean.PageBean.ListBean> listBeans;
    private int userId;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_status_list);
        ButterKnife.bind(this);
        gson = new Gson();
        setListener();
        initData();
    }

    private void initData() {
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(false));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        listBeans = new ArrayList<>();
        status = getIntent().getIntExtra(ParamUtil.ORDER_STATUS, 0);
        updateData();
    }

    private void updateData(){
        if (status == 2){
            titleTv.setText(R.string.pending_review);
        }else if (status == 3){
            titleTv.setText(R.string.reviewed);
        }else {
            titleTv.setText(R.string.rejected);
        }
        userId = (int) SharedPreferencesUtil.get(this, ParamUtil.USER_ID, 0);
        type = (int) SharedPreferencesUtil.get(this, ParamUtil.TYPE, 2); // 1为管理员，2为普通用户
        getList(userId, type, String.valueOf(pageIndex), String.valueOf(pageSize), status);
        orderListAdapter = new OrderListAdapter(this, listBeans);
        orderList.setAdapter(orderListAdapter);
    }

    private void setListener() {
        orderList.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(OrderStatusListActivity.this, OrderInfoDetailActivity.class);
        intent.putExtra(ParamUtil.WORK_ORDER_ID, listBeans.get(position).getWorkOrderId());
        startActivity(intent);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageIndex++;
        getList(userId, type, String.valueOf(pageIndex), String.valueOf(pageSize), status);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        listBeans.clear();
        pageIndex = 1;
        getList(userId, type, String.valueOf(pageIndex), String.valueOf(pageSize), status);
    }

    private void getList(long userId, int type, String pageNo, String pageSize, int status){
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_LIST + "?userId="+userId + "&type=" + type + "&pageNo=" + pageNo + "&pageSize=" + pageSize + "&status=" + status)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        OrderListBean orderListBean = gson.fromJson(response.body(), OrderListBean.class);
                        if (orderListBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (orderListBean.getPage().getList() != null && orderListBean.getPage().getList().size()>0){
                                    listBeans.addAll(orderListBean.getPage().getList());
                            }
                            orderListAdapter.notifyDataSetChanged();
                            if (orderListBean.getPage().getList() == null || orderListBean.getPage().getList().size() < 10) {
                                refreshLayout.setEnableLoadMore(false);
                            } else {
                                refreshLayout.setEnableLoadMore(true);
                            }
                        }else {
                            Toast.makeText(OrderStatusListActivity.this, orderListBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        Toast.makeText(OrderStatusListActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误："+ response.code() + "-------" + response.message());
                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            if (status == 2){ //待审核
                listBeans.clear();
                pageIndex = 1;
                getList(userId, type, String.valueOf(pageIndex), String.valueOf(pageSize), status);
            }
        }
    }
}
