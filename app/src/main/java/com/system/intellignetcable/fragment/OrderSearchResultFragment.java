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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.adapter.AnalyzeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by adu on 2018/11/25.
 */

public class OrderSearchResultFragment extends Fragment {
    private static OrderSearchResultFragment orderSearchResultFragment;
    private static String searchString;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_result_lv)
    ListView searchResultLv;
    Unbinder unbinder;
    private AnalyzeAdapter analyzeAdapter;
    private List<String> list;

    public static OrderSearchResultFragment getIntance(String search) {
        searchString = search;
        if (orderSearchResultFragment == null) {
            orderSearchResultFragment = new OrderSearchResultFragment();
        }
        return orderSearchResultFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_search_result, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        list= new ArrayList<>();
        list.add("东三环中路9号");
        list.add("朝阳北路");
        list.add("东三环中路9号");
        list.add("朝阳北路");
        analyzeAdapter = new AnalyzeAdapter(getActivity(), list);
        searchResultLv.setAdapter(analyzeAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {//不在最前端显示

        } else {//在最前端显示

        }
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
        MainActivity.setCurrentFragment(OrderFragment.getInstance());
    }

    @OnClick(R.id.search_iv)
    public void onSearchIvClicked() {
    }
}

