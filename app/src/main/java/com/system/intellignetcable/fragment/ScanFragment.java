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
import android.widget.ListView;
import android.widget.TextView;

import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.adapter.ScanAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by adu on 2018/11/25.
 */

public class ScanFragment extends Fragment {
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.scan_lv)
    ListView scanLv;
    Unbinder unbinder;
    private ScanAdapter scanAdapter;
    private List<String> list;

    public static ScanFragment newInstance() {
        ScanFragment scanFragment = new ScanFragment();
        return scanFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        titleTv.setText(R.string.scan);
        list = new ArrayList<>();
        for (int i = 0; i<20; i++){
            list.add("设备" + i);
        }
        scanAdapter = new ScanAdapter(getActivity(), list);
        scanLv.setAdapter(scanAdapter);
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
