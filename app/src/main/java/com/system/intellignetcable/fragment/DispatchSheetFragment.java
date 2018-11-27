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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.adapter.DispatchSheetAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by adu on 2018/11/24.
 */

public class DispatchSheetFragment extends Fragment {
    private static DispatchSheetFragment dispatchSheetFragment;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.signs_number_et)
    EditText signsNumberEt;
    @BindView(R.id.place_et)
    EditText placeEt;
    @BindView(R.id.dispatch_list)
    ListView dispatchList;
    @BindView(R.id.dispatch_btn)
    Button dispatchBtn;
    Unbinder unbinder;
    private DispatchSheetAdapter dispatchSheetAdapter;
    private List<String> list;

    public static DispatchSheetFragment getInstance() {
        if (dispatchSheetFragment == null) {
            dispatchSheetFragment = new DispatchSheetFragment();
        }
        return dispatchSheetFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dispatch_sheet, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        titleTv.setText(R.string.dispatch_sheet);
        list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("张三");
        list.add("李四");
        dispatchSheetAdapter = new DispatchSheetAdapter(getActivity(), list);
        dispatchList.setAdapter(dispatchSheetAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.back_iv)
    public void onBackIvClicked() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        MainActivity.setCurrentFragment(null);
    }

    @OnClick(R.id.dispatch_btn)
    public void onDispatchBtnClicked() {
    }
}
