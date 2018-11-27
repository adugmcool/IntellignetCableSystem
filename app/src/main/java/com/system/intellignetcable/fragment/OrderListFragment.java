package com.system.intellignetcable.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.adapter.OrderListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by adu on 2018/11/25.
 */

public class OrderListFragment extends Fragment implements AdapterView.OnItemClickListener{
    @BindView(R.id.order_list)
    ListView orderList;
    Unbinder unbinder;
    @BindView(R.id.dispatch_sheet_rl)
    RelativeLayout dispatchSheetRl;
    private OrderListAdapter orderListAdapter;
    private List<String> list;
    public static final String ARGS_PAGE = "args_page";
    private int mPage;
    private MainActivity mainActivity;

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
    }

    private void initData() {
        list = new ArrayList<>();
        list.add("东三环中路9号");
        list.add("管庄东路");
        list.add("常营民族东路");
        list.add("东三环中路9号");
        list.add("管庄东路");
        list.add("常营民族东路");
        list.add("东三环中路9号");
        list.add("管庄东路");
        list.add("常营民族东路");
        orderListAdapter = new OrderListAdapter(getActivity(), list);
        orderList.setAdapter(orderListAdapter);
        if (mPage == 1){
            dispatchSheetRl.setVisibility(View.VISIBLE);
        }else {
            dispatchSheetRl.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.dispatch_sheet_rl)
    public void onViewClicked() {
        mainActivity.switchFragment(DispatchSheetFragment.getInstance()).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mPage == 0){
            mainActivity.switchFragment(OrderInfoDetailFragment.newInstance()).addToBackStack(null).commitAllowingStateLoss();
        }else {
            mainActivity.switchFragment(OrderManagementDetailFragment.newInstance()).addToBackStack(null).commitAllowingStateLoss();
        }
    }
}
