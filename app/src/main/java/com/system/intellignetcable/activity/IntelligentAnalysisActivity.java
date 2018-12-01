package com.system.intellignetcable.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.intellignetcable.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adu on 2018/11/30.
 */

public class IntelligentAnalysisActivity extends BaseActivity {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_analyze_result);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleTv.setText(getString(R.string.intelligent_analyze));
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
