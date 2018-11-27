package com.system.intellignetcable.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.adapter.AnalyzeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;

/**
 * Created by zydu on 2018/11/21.
 */

public class AnalyzeFragment extends Fragment implements AdapterView.OnItemClickListener{
    private static AnalyzeFragment analyzeFragment;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_rl)
    RelativeLayout searchRl;
    @BindView(R.id.analyze_lv)
    ListView analyzeLv;
    @BindView(R.id.analyze_btn)
    Button analyzeBtn;
    Unbinder unbinder;
//    @BindView(R.id.refresh_layout)
//    SmartRefreshLayout refreshLayout;
    private MainActivity mainActivity;
    private AnalyzeAdapter analyzeAdapter;
    private List<String> list;

    public static AnalyzeFragment getInstance() {
        if (analyzeFragment == null) {
            analyzeFragment = new AnalyzeFragment();
        }
        return analyzeFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        this.mainActivity = (MainActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyze, container, false);
        unbinder = ButterKnife.bind(this, view);
        setListener();
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
        analyzeLv.setAdapter(analyzeAdapter);
    }

    private void setListener() {
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//
//            }
//        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//
//            }
//        });
        analyzeLv.setOnItemClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.search_iv)
    public void onSearchIvClicked() {
    }

    @OnClick(R.id.analyze_btn)
    public void onAnalyzeBtnClicked() {
        mainActivity.switchFragment(IntelligentAnalysisFragment.getInstance()).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mainActivity.switchFragment(IntelligentAnalysisFragment.getInstance()).addToBackStack(null).commitAllowingStateLoss();
    }
}
