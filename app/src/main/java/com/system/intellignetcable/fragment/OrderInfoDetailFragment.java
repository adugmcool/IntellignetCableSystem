package com.system.intellignetcable.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by adu on 2018/11/25.
 */

public class OrderInfoDetailFragment extends Fragment {
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
    Unbinder unbinder;

    public static OrderInfoDetailFragment newInstance() {
        OrderInfoDetailFragment orderInfoDetailFragment = new OrderInfoDetailFragment();
        return orderInfoDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_info_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        titleTv.setText(R.string.worksheet_detail);
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
