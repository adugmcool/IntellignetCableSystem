package com.system.intellignetcable.activity;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.system.intellignetcable.R;
import com.system.intellignetcable.adapter.ImageAdapter;
import com.system.intellignetcable.adapter.SignageOrderInfoAdapter;
import com.system.intellignetcable.bean.SignageManagementBean;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;
import com.system.intellignetcable.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zydu on 2018/11/30.
 */

public class SignageManagementActivity extends BaseActivity {
    private static final String TAG = "SignageManagement";
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.gridview)
    MyGridView gridview;
    @BindView(R.id.detail_ll)
    LinearLayout detailLl;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.right_iv)
    TextView rightIv;
    private Gson gson;
    private List<SignageManagementBean.SignBoardBean.TemplateValuesBean> templateValuesBeanList;
    private String pathString;
    private List<String> imageList;
    private ImageAdapter imageAdapter;
    private SignageOrderInfoAdapter signageOrderInfoAdapter;
    private SignageManagementBean signageManagementBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_mangement_detail);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleTv.setText(R.string.signage_management);
        rightIv.setVisibility(View.VISIBLE);
        rightIv.setText(R.string.submit);
        gson = new Gson();
        templateValuesBeanList = new ArrayList<>();
        imageList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        signageOrderInfoAdapter = new SignageOrderInfoAdapter(this, templateValuesBeanList);
        //设置Adapter
        recyclerView.setAdapter(signageOrderInfoAdapter);
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, OrientationHelper.HORIZONTAL));
        imageAdapter = new ImageAdapter(this, imageList);
        gridview.setAdapter(imageAdapter);
        if (((int) SharedPreferencesUtil.get(SignageManagementActivity.this, ParamUtil.TYPE, 2)) == 1){
            signageManagementDetail("A11801001001180800000A01");
        }else {
            signageManagementScan("A11801001001180800000A01");
        }
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }

    private void signageManagementScan(String epc) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_SIGN_BOARD_SCAN)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .params(ParamUtil.USER_ID, (int) SharedPreferencesUtil.get(this, ParamUtil.USER_ID, 0))
                .params("epc", epc)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        signageManagementBean = gson.fromJson(response.body(), SignageManagementBean.class);
                        if (signageManagementBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (signageManagementBean.getSignBoard().getTemplateValues() != null && signageManagementBean.getSignBoard().getTemplateValues().size() > 0) {
                                templateValuesBeanList.addAll(signageManagementBean.getSignBoard().getTemplateValues());
                                signageOrderInfoAdapter.notifyDataSetChanged();
                            }
                            pathString = signageManagementBean.getSignBoard().getImagesUrls();
                            if (!pathString.isEmpty()) {
                                String[] strings = pathString.split(",");
                                for (String s : strings) {
                                    imageList.add(s);
                                }
                                imageAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(SignageManagementActivity.this, signageManagementBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(SignageManagementActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    private void signageManagementDetail(String epc) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_DETAIL)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .params("epc", epc)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        signageManagementBean = gson.fromJson(response.body(), SignageManagementBean.class);
                        if (signageManagementBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (signageManagementBean.getSignBoard().getTemplateValues() != null && signageManagementBean.getSignBoard().getTemplateValues().size() > 0) {
                                templateValuesBeanList.addAll(signageManagementBean.getSignBoard().getTemplateValues());
                                signageOrderInfoAdapter.notifyDataSetChanged();
                            }
                            pathString = signageManagementBean.getSignBoard().getImagesUrls();
                            if (!pathString.isEmpty()) {
                                String[] strings = pathString.split(",");
                                for (String s : strings) {
                                    imageList.add(s);
                                }
                                imageAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(SignageManagementActivity.this, signageManagementBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(SignageManagementActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    // 将所有参数拼接
    private void getParamsString(){
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < signageManagementBean.getSignBoard().getTemplateValues().size(); i++){
            sb.append(signageManagementBean.getSignBoard().getTemplateValues().get(i).getFieldName() + "=" + signageManagementBean.getSignBoard().getTemplateValues().get(i).getFieldValue() + "&");
        }
        Log.i(TAG, "urlString----" + sb.substring(0, sb.length() - 1));
        saveOrUpdate(signageManagementBean.getSignBoard().getEpc(), signageManagementBean.getSignBoard().getUserId(),
                signageManagementBean.getSignBoard().getWorkOrderId(), signageManagementBean.getSignBoard().getImagesUrls(),
                signageManagementBean.getSignBoard().getLongitude(), signageManagementBean.getSignBoard().getLatitude(),
                signageManagementBean.getSignBoard().getDetailAddress(), sb.substring(0, sb.length() - 1));
    }

    //保存或修改epc
    private void saveOrUpdate(String epc, int userId, int workOrderId, String imagesUrls, Object longitude, Object latitude, Object detailAddress, String ss){
        Log .i(TAG, "urlString------" + UrlUtils.TEST_URL + UrlUtils.METHOD_POST_SIGN_BOARD_SAVE_UPDATE + "?" + "epc=" + epc + "&" + "userId=" + userId + "&" + "workOrderId=" + workOrderId + "&"
                + "imagesUrls=" + imagesUrls + "&" + "longitude=" + longitude + "&" + "latitude=" + latitude + "&" + "detailAddress=" + detailAddress + "&"+ ss);
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_SIGN_BOARD_SAVE_UPDATE + "?" + "epc=" + epc + "&" + "userId=" + userId + "&" + "workOrderId=" + workOrderId + "&"
                + "imagesUrls=" + imagesUrls + "&" + "longitude=" + longitude + "&" + "latitude=" + latitude + "&" + "detailAddress=" + detailAddress + "&"+ ss)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        SignageManagementBean managementBean = gson.fromJson(response.body(), SignageManagementBean.class);
                        if (managementBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            Toast.makeText(SignageManagementActivity.this, "提交成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignageManagementActivity.this, managementBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(SignageManagementActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    @OnClick(R.id.right_iv)
    public void onRightClicked() {
        getParamsString();
    }
}
