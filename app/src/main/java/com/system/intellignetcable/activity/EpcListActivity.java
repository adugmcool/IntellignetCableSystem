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
import com.system.intellignetcable.adapter.EpcListAdapter;
import com.system.intellignetcable.bean.MapDataDetailBean;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adu on 2018/11/24.
 */

public class EpcListActivity extends BaseActivity {
    private static final String TAG = "EpcListActivity";
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.right_iv)
    TextView rightIv;
    @BindView(R.id.order_list)
    ListView orderList;
    private ArrayList<MapDataDetailBean.ListBean> listBeans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBeans = (ArrayList<MapDataDetailBean.ListBean>) getIntent().getSerializableExtra("listBeans");

        setContentView(R.layout.activity_epc_list);
        ButterKnife.bind(this);
        titleTv.setText("EPC列表");
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EpcListAdapter epcListAdapter = new EpcListAdapter(this, listBeans);
        orderList.setAdapter(epcListAdapter);
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EpcListActivity.this, SignageManagementActivity.class);
                intent.putExtra("epcId", listBeans.get(position).getEpc());
                startActivity(intent);
            }
        });
    }

}
