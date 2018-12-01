package com.system.intellignetcable.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import butterknife.Unbinder;

/**
 * Created by adu on 2018/11/25.
 */

public class OrderListFragment extends Fragment implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {
    private static final String TAG = "OrderListFragment";
    @BindView(R.id.order_list)
    ListView orderList;
    Unbinder unbinder;
    @BindView(R.id.dispatch_sheet_rl)
    RelativeLayout dispatchSheetRl;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private OrderListAdapter orderListAdapter;
    private List<OrderListBean.PageBean.ListBean> list;
    public static final String ARGS_PAGE = "args_page";
    private int mPage;
    private MainActivity mainActivity;
    private Gson gson;
    private int userId;
    private int type;
    private int pageIndex = 1;
    private int pageSize = 10;

    public static OrderListFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        OrderListFragment orderListFragment = new OrderListFragment();
        orderListFragment.setArguments(args);
        return orderListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        orderList.setOnItemClickListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initData() {
        refreshLayout.setRefreshHeader(new MaterialHeader(mainActivity).setShowBezierWave(false));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mainActivity));
        gson = new Gson();
        type = (int) SharedPreferencesUtil.get(mainActivity, ParamUtil.TYPE, 2);
        userId = (int) SharedPreferencesUtil.get(mainActivity, ParamUtil.USER_ID, 0);
        list = new ArrayList<>();
        if (mPage == 1 && type == 1) {
            dispatchSheetRl.setVisibility(View.VISIBLE);
        } else {
            dispatchSheetRl.setVisibility(View.GONE);
        }
        getOrderList(userId, type, String.valueOf(pageIndex), String.valueOf(pageSize));
        orderListAdapter = new OrderListAdapter(getActivity(), list);
        orderList.setAdapter(orderListAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pageIndex = 1;
        unbinder.unbind();
    }

    @OnClick(R.id.dispatch_sheet_rl)
    public void onViewClicked() {
        startActivity(new Intent(mainActivity, DispatchSheetActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mainActivity.hideSoftInput(view);
        Intent intent = new Intent(mainActivity, OrderInfoDetailActivity.class);
        intent.putExtra(ParamUtil.WORK_ORDER_ID, list.get(position).getWorkOrderId());
        startActivity(intent);
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
                            if (orderListBean.getPage().getList() != null && orderListBean.getPage().getList().size() > 0) {
                                list.addAll(orderListBean.getPage().getList());
                            }
                            orderListAdapter.notifyDataSetChanged();
                            if (orderListBean.getPage().getList() == null || orderListBean.getPage().getList().size() < 10) {
                                refreshLayout.setEnableLoadMore(false);
                            } else {
                                refreshLayout.setEnableLoadMore(true);
                            }
                        } else {
                            Toast.makeText(getActivity(), orderListBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
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
}
