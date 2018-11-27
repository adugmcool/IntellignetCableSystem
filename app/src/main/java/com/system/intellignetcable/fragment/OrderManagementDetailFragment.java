package com.system.intellignetcable.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.view.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by adu on 2018/11/26.
 */

public class OrderManagementDetailFragment extends Fragment {
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.status_spinner)
    Spinner statusSpinner;
    @BindView(R.id.order_info_tv)
    TextView orderInfoTv;
    @BindView(R.id.gridview)
    MyGridView gridview;
    Unbinder unbinder;
    private MainActivity mainActivity;

    public static OrderManagementDetailFragment newInstance() {
        OrderManagementDetailFragment orderManagementDetailFragment = new OrderManagementDetailFragment();
        return orderManagementDetailFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_mangement_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        titleTv.setText(R.string.worksheet_detail);
        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
//        statusSpinner.setAdapter(adapter);
        statusSpinner.setDropDownVerticalOffset(76);
        statusSpinner.setDropDownHorizontalOffset(408);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                mainActivity.switchFragment(OrderStatusFragment.newInstance()).addToBackStack(null).commitAllowingStateLoss();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        MainActivity.setCurrentFragment(OrderFragment.getInstance());
    }
}
