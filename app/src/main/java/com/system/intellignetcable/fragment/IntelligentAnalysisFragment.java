package com.system.intellignetcable.fragment;

import android.app.Activity;
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
 * Created by adu on 2018/11/22.
 */

public class IntelligentAnalysisFragment extends Fragment {
    private static IntelligentAnalysisFragment intelligentAnalysisFragment;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.fault_tv)
    TextView faultTv;
    @BindView(R.id.reason_tv)
    TextView reasonTv;
    @BindView(R.id.result_tv)
    TextView resultTv;
    @BindView(R.id.place_tv)
    TextView placeTv;
    Unbinder unbinder;

    public static IntelligentAnalysisFragment getInstance() {
        if (intelligentAnalysisFragment == null) {
            intelligentAnalysisFragment = new IntelligentAnalysisFragment();
        }
        return intelligentAnalysisFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyze_result, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        titleTv.setText(getString(R.string.intelligent_analyze));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
//        mainActivity.switchFragment(AnalyzeFragment.getInstance()).commitAllowingStateLoss();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        MainActivity.setCurrentFragment(AnalyzeFragment.getInstance());
    }
}
