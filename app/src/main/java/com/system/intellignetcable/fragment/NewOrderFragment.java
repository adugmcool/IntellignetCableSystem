package com.system.intellignetcable.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.system.intellignetcable.activity.DispatchSheetActivity;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.activity.OrderInfoDetailActivity;
import com.system.intellignetcable.activity.OrderSearchResultActivity;
import com.system.intellignetcable.activity.ScanActivity;
import com.system.intellignetcable.adapter.NewOrderAdapter;
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

public class NewOrderFragment extends BaseFragment implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {
    private static final String TAG = "NewOrderFragment";
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_rl)
    RelativeLayout searchRl;
    @BindView(R.id.order_list)
    ListView orderList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.dispatch_sheet_rl)
    RelativeLayout dispatchSheetRl;
    @BindView(R.id.hint_tv)
    TextView hintTv;
    Unbinder unbinder;
    private static NewOrderFragment newOrderFragment;
    @BindView(R.id.fab)
    Button fab;
    private MainActivity mainActivity;
    private int type;
    private int pageIndex = 1;
    private int pageSize = 10;
    private Gson gson;
    private List<OrderListBean.PageBean.ListBean> list;
    private int userId;
    private NewOrderAdapter newOrderAdapter;

    public static NewOrderFragment getInstance() {
        if (newOrderFragment == null) {
            newOrderFragment = new NewOrderFragment();
        }
        return newOrderFragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_new, container, false);
        unbinder = ButterKnife.bind(this, view);
        setLoadingView(hintTv, refreshLayout);
        initData();
        setListener();
        return view;
    }

    private void initData() {
        showLoading();
        refreshLayout.setRefreshHeader(new MaterialHeader(mainActivity).setShowBezierWave(false));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mainActivity));
        gson = new Gson();
        type = (int) SharedPreferencesUtil.get(mainActivity, ParamUtil.TYPE, 2);
        userId = (int) SharedPreferencesUtil.get(mainActivity, ParamUtil.USER_ID, 0);
        list = new ArrayList<>();
        if (type == 1) {
            dispatchSheetRl.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        } else {
            dispatchSheetRl.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }
        getOrderList(userId, type, String.valueOf(pageIndex), String.valueOf(pageSize));
        newOrderAdapter = new NewOrderAdapter(getActivity(), list);
        orderList.setAdapter(newOrderAdapter);
    }

    private void setListener() {
        orderList.setOnItemClickListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.search_iv)
    public void onSearchIvClicked() {
        if (!searchEt.getText().toString().isEmpty()) {
            Intent intent = new Intent(mainActivity, OrderSearchResultActivity.class);
            intent.putExtra(ParamUtil.SEARCH_CONTENT, searchEt.getText().toString());
            startActivity(intent);
            searchEt.setText("");
        }
    }

    @OnClick(R.id.dispatch_sheet_rl)
    public void onDispatchSheetRlClicked() {
        startActivity(new Intent(mainActivity, DispatchSheetActivity.class));
    }

    private void getOrderList(int userId, int type, String pageNo, String pageSize) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_LIST + "?userId=" + userId + "&type=" + type + "&pageNo=" + pageNo + "&pageSize=" + pageSize)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        OrderListBean orderListBean = gson.fromJson(response.body(), OrderListBean.class);
                        if (orderListBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (orderListBean.getPage().getList() == null || orderListBean.getPage().getList().size() == 0) {
                                showNoData();
                            } else if (orderListBean.getPage().getList() != null && orderListBean.getPage().getList().size() > 0) {
                                showDataSuc();
                                list.addAll(orderListBean.getPage().getList());
                                newOrderAdapter.notifyDataSetChanged();
                            }
                            if (orderListBean.getPage().getList() == null || orderListBean.getPage().getList().size() < 10) {
                                refreshLayout.setEnableLoadMore(false);
                            } else {
                                refreshLayout.setEnableLoadMore(true);
                            }
                        } else {
                            showFail();
                            Toast.makeText(getActivity(), orderListBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        showFail();
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageIndex++;
        getOrderList(userId, type, String.valueOf(pageIndex), String.valueOf(pageSize));
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        list.clear();
        pageIndex = 1;
        getOrderList(userId, type, String.valueOf(pageIndex), String.valueOf(pageSize));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mainActivity.hideSoftInput(view);
        Intent intent = new Intent(mainActivity, OrderInfoDetailActivity.class);
        intent.putExtra(ParamUtil.WORK_ORDER_ID, list.get(position).getWorkOrderId());
        startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(mainActivity, ScanActivity.class));
    }
}
