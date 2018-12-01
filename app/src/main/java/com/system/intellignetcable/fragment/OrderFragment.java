package com.system.intellignetcable.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.activity.OrderSearchResultActivity;
import com.system.intellignetcable.activity.ScanActivity;
import com.system.intellignetcable.adapter.OrderPageAdapter;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zydu on 2018/11/21.
 */

public class OrderFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private static OrderFragment orderFragment;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.scan_rl)
    RelativeLayout scanRl;
    private List<String> strings;
    private List<Fragment> fragments;
    private OrderPageAdapter orderPageAdapter;
    private MainActivity mainActivity;
    private int type;

    public static OrderFragment getInstance() {
        if (orderFragment == null) {
            orderFragment = new OrderFragment();
        }
        return orderFragment;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        this.mainActivity = (MainActivity) context;
//    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        strings = new ArrayList<>();
        fragments = new ArrayList<>();
        type = (int) SharedPreferencesUtil.get(mainActivity, ParamUtil.TYPE, 2);
        if (type == 1){ // 1为管理员，2为普通用户
            fragments.add(new Fragment());
            fragments.add(OrderListFragment.newInstance(1));
        }else {
            fragments.add(OrderListFragment.newInstance(0));
            fragments.add(new Fragment());
        }
        strings.add(getResources().getString(R.string.worksheet_info));
        strings.add(getResources().getString(R.string.worksheet_management));
        for (String tab : strings) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        tabLayout.setOnTabSelectedListener(this);
        orderPageAdapter = new OrderPageAdapter(getChildFragmentManager(), strings, fragments);
        viewPager.setAdapter(orderPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if (type == 1) {
            viewPager.setCurrentItem(1);
        }else {
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.search_iv)
    public void onViewClicked() {
        if (!searchEt.getText().toString().isEmpty()) {
            Intent intent = new Intent(mainActivity, OrderSearchResultActivity.class);
            intent.putExtra(ParamUtil.SEARCH_CONTENT, searchEt.getText().toString());
            startActivity(intent);
            searchEt.setText("");
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @OnClick(R.id.scan_rl)
    public void onScanRlClicked() {
        startActivity(new Intent(mainActivity, ScanActivity.class));
    }
}
