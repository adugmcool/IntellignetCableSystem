package com.system.intellignetcable.fragment;

import android.content.Context;
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
import com.system.intellignetcable.adapter.OrderPageAdapter;

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

    public static OrderFragment getInstance() {
        if (orderFragment == null) {
            orderFragment = new OrderFragment();
        }
        return orderFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
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
        strings.add(getResources().getString(R.string.worksheet_info));
        strings.add(getResources().getString(R.string.worksheet_management));
        for (String tab : strings) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        for (int i = 0; i < strings.size(); i++) {
            fragments.add(OrderListFragment.newInstance(i));
        }
        tabLayout.setOnTabSelectedListener(this);
        orderPageAdapter = new OrderPageAdapter(getChildFragmentManager(), strings, fragments);
        viewPager.setAdapter(orderPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.search_iv)
    public void onViewClicked() {
        mainActivity.switchFragment(OrderSearchResultFragment.getIntance("")).addToBackStack(null).commitAllowingStateLoss();
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
        mainActivity.switchFragment(ScanFragment.newInstance()).addToBackStack(null).commitAllowingStateLoss();
    }
}
