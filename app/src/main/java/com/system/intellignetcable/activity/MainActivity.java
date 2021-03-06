package com.system.intellignetcable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.system.intellignetcable.R;
import com.system.intellignetcable.fragment.AnalyzeFragment;
import com.system.intellignetcable.fragment.LocationFragment;
import com.system.intellignetcable.fragment.MineFragment;
import com.system.intellignetcable.fragment.NewOrderFragment;
import com.system.intellignetcable.fragment.OrderFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.fragment_item)
    FrameLayout fragmentItem;
    @BindView(R.id.order_rb)
    RadioButton orderRb;
    @BindView(R.id.location_rb)
    RadioButton locationRb;
    @BindView(R.id.analyze_rb)
    RadioButton analyzeRb;
    @BindView(R.id.mine_rb)
    RadioButton mineRb;

    private FragmentManager fragmentManager;
//    private OrderFragment orderFragment;
    private NewOrderFragment newOrderFragment;
    private LocationFragment locationFragment;
    private AnalyzeFragment analyzeFragment;
    private MineFragment mineFragment;
    private Fragment currentFragment;
    private Fragment oldFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null){
            oldFragment = getSupportFragmentManager().getFragment(savedInstanceState, "Fragment");
        }else {
            oldFragment = NewOrderFragment.getInstance();
        }
        initData();
//        startActivity(new Intent(this.getApplicationContext(),
//                com.amap.api.maps.offlinemap.OfflineMapActivity.class));
        //构造OfflineMapManager对象
        OfflineMapManager amapManager = new OfflineMapManager(this, new OfflineMapManager.OfflineMapDownloadListener() {
            @Override
            public void onDownload(int i, int i1, String s) {
            }

            @Override
            public void onCheckUpdate(boolean b, String s) {

            }

            @Override
            public void onRemove(boolean b, String s, String s1) {

            }
        });
        try {
            List<OfflineMapCity> cities = amapManager.getDownloadOfflineMapCityList();
            boolean hasBj = false;
            if(cities != null && !cities.isEmpty()){
                for (int i = 0; i < cities.size(); i++) {
                    OfflineMapCity offlineMapCity = cities.get(i);
                    if(offlineMapCity.getCode().equals("010")){
                        hasBj = true;
                        break;
                    }
                }
            }
            if(!hasBj){
                Toast.makeText(MainActivity.this, "开始下载离线地图", Toast.LENGTH_SHORT).show();
                amapManager.downloadByCityCode("010");
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "离线地图下载失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        newOrderFragment = NewOrderFragment.getInstance();
        locationFragment = LocationFragment.getInstance();
        analyzeFragment = AnalyzeFragment.getInstance();
        mineFragment = MineFragment.getInstance();
        switchFragment(oldFragment).commitAllowingStateLoss();
    }

    @OnClick({R.id.order_rb, R.id.location_rb, R.id.analyze_rb, R.id.mine_rb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_rb:
                switchFragment(newOrderFragment).commitAllowingStateLoss();
                break;
            case R.id.location_rb:
                switchFragment(locationFragment).commitAllowingStateLoss();
                break;
            case R.id.analyze_rb:
                switchFragment(analyzeFragment).commitAllowingStateLoss();
                break;
            case R.id.mine_rb:
                switchFragment(mineFragment).commitAllowingStateLoss();
                break;
        }
    }

    public FragmentTransaction switchFragment(Fragment targetFragment) {
        hideSoftInput(fragmentItem);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (currentFragment != targetFragment) {
            if (currentFragment != null) {
                fragmentTransaction.hide(currentFragment);
            }
            if (!targetFragment.isAdded()) {
                fragmentTransaction.add(R.id.fragment_item, targetFragment);
            } else {
                fragmentTransaction.show(targetFragment);
            }
            currentFragment = targetFragment;
        }
        return fragmentTransaction;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
     * 当活动被回收时，存储当前状态
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 存储fragment状态
        if (currentFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "Fragment", currentFragment);
        }
    }
}
