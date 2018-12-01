package com.system.intellignetcable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.system.intellignetcable.adapter.AnalyzeAdapter;
import com.system.intellignetcable.bean.OrderListBean;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by adu on 2018/11/30.
 */

public class OrderSearchResultActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, AdapterView.OnItemClickListener {
    private static final String TAG = "OrderSearchFragment";
    private String searchString;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_result_lv)
    ListView searchResultLv;
    Unbinder unbinder;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private AnalyzeAdapter analyzeAdapter;
    private List<String> list;
    private List<Integer> idList;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int userId;
    private int type;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_search_result);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(false));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        gson = new Gson();
        searchString = getIntent().getStringExtra(ParamUtil.SEARCH_CONTENT);
        type = (int) SharedPreferencesUtil.get(this, ParamUtil.TYPE, 2);
        userId = (int) SharedPreferencesUtil.get(this, ParamUtil.USER_ID, 0);
        list = new ArrayList<>();
        idList = new ArrayList<>();
        analyzeAdapter = new AnalyzeAdapter(this, list);
        searchResultLv.setAdapter(analyzeAdapter);
        getOrderList(userId, type, searchString, String.valueOf(pageIndex), String.valueOf(pageSize));
        searchResultLv.setOnItemClickListener(this);
    }

    @OnClick(R.id.back_iv)
    public void onBackIvClicked() {
        finish();
    }

    @OnClick(R.id.search_iv)
    public void onSearchIvClicked() {
        hideSoftInput(searchIv);
        if (!searchEt.getText().toString().isEmpty()){
            pageIndex = 1;
            list.clear();
            idList.clear();
            searchString = searchEt.getText().toString();
            getOrderList(userId, type, searchString, String.valueOf(pageIndex), String.valueOf(pageSize));
        }
    }

    private void getOrderList(int userId, int type, String workAddress, String pageNo, String pageSize) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_LIST + "?userId=" + userId + "&type=" + type + "&workAddress=" + workAddress + "&pageNo=" + pageNo + "&pageSize=" + pageSize)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        OrderListBean orderListBean = gson.fromJson(response.body(), OrderListBean.class);
                        if (orderListBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (orderListBean.getPage().getList().size() > 0) {
                                for (int i = 0; i < orderListBean.getPage().getList().size(); i++) {
                                    list.add(orderListBean.getPage().getList().get(i).getWorkAddress());
                                    idList.add(orderListBean.getPage().getList().get(i).getWorkOrderId());
                                }
                            }
                            analyzeAdapter.notifyDataSetChanged();
                            if (orderListBean.getPage().getList() == null || orderListBean.getPage().getList().size() < 10){
                                refreshLayout.setEnableLoadMore(false);
                            }else {
                                refreshLayout.setEnableLoadMore(true);
                            }
                        } else {
                            Toast.makeText(OrderSearchResultActivity.this, orderListBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        Toast.makeText(OrderSearchResultActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageIndex++;
        getOrderList(userId, type, searchString, String.valueOf(pageIndex), String.valueOf(pageSize));
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        list.clear();
        idList.clear();
        pageIndex = 1;
        getOrderList(userId, type, searchString, String.valueOf(pageIndex), String.valueOf(pageSize));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        hideSoftInput(view);
        Intent intent = new Intent(OrderSearchResultActivity.this, OrderInfoDetailActivity.class);
        intent.putExtra(ParamUtil.WORK_ORDER_ID, idList.get(position));
        startActivity(intent);
    }
}
