package com.system.intellignetcable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.system.intellignetcable.R;
import com.system.intellignetcable.adapter.ScanAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adu on 2018/11/30.
 */

public class ScanActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.scan_lv)
    ListView scanLv;
    private ScanAdapter scanAdapter;
    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_scan);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleTv.setText(R.string.scan);
        list = new ArrayList<>();
        for (int i = 0; i<20; i++){
            list.add("设备" + i);
        }
        scanAdapter = new ScanAdapter(this, list);
        scanLv.setAdapter(scanAdapter);
        scanLv.setOnItemClickListener(this);
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(ScanActivity.this, SignageManagementActivity.class));
    }
}
