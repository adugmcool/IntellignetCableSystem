package com.system.intellignetcable.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.system.intellignetcable.R;
import com.system.intellignetcable.adapter.DispatchSheetAdapter;
import com.system.intellignetcable.bean.OrderCheckBean;
import com.system.intellignetcable.bean.TemplateListBean;
import com.system.intellignetcable.bean.UsersListBean;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adu on 2018/11/29.
 */

public class DispatchSheetActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "DispatchSheetActivity";
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
    @BindView(R.id.right_iv)
    TextView rightIv;
    private Gson gson;
    private DispatchSheetAdapter dispatchSheetAdapter;
    private List<UsersListBean.UsersBean> list;
    private List<TemplateListBean.TemplatesBean> templatesBeanList;
    private List<String> templateList;
    private int currentPos = -1;
    private int currentTemolatPos = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dispatch_sheet);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleTv.setText(R.string.dispatch_sheet);
        rightIv.setVisibility(View.VISIBLE);
        rightIv.setText(R.string.query_template);
        gson = new Gson();
        list = new ArrayList<>();
        templatesBeanList = new ArrayList<>();
        templateList = new ArrayList<>();
        dispatchList.setOnItemClickListener(this);
        usersList();
        queryTemplate();
    }

    @OnClick(R.id.back_iv)
    public void onBackIvClicked() {
        finish();
    }

    @OnClick(R.id.dispatch_btn)
    public void onDispatchBtnClicked() {
        String num = signsNumberEt.getText().toString();
        String address = placeEt.getText().toString();
        int userId = (int) SharedPreferencesUtil.get(this, ParamUtil.USER_ID, 0);
        if (num.isEmpty()) {
            Toast.makeText(DispatchSheetActivity.this, "请输入标识牌数量！", Toast.LENGTH_SHORT).show();
            return;
        } else if (address.isEmpty()) {
            Toast.makeText(DispatchSheetActivity.this, "请输入位置！", Toast.LENGTH_SHORT).show();
            return;
        }else if (currentPos == -1){
            Toast.makeText(DispatchSheetActivity.this, "请选择指派用户！", Toast.LENGTH_SHORT).show();
            return;
        }else if (currentTemolatPos == -1){
            Toast.makeText(DispatchSheetActivity.this, "请选择模板！", Toast.LENGTH_SHORT).show();
            return;
        }
        createOrder(Integer.parseInt(num), address, list.get(currentPos).getUserId(), userId, templatesBeanList.get(currentTemolatPos).getUserTeamplateId());
    }

    // 展示模板列表
    private void showTemplateDialog(List<String> list){
        final String[] items = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            items[i] = list.get(i);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("请选择:");
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentTemolatPos = which;
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void createOrder(int signsNum, String workAddress, int workUserId, long userId, long userTemplateId) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_CREATE + "?signsNum=" + signsNum + "&workAddress=" + workAddress + "&workUserId=" + workUserId + "&userId=" + userId + "&userTemplateId=" + userTemplateId)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        OrderCheckBean orderCheckBean = gson.fromJson(response.body(), OrderCheckBean.class);
                        if (orderCheckBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            finish();
                        } else {
                            Toast.makeText(DispatchSheetActivity.this, orderCheckBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(DispatchSheetActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    private void usersList(){
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_USERS)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .params("type", 2) //默认查询普通用户列表
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UsersListBean usersListBean = gson.fromJson(response.body(), UsersListBean.class);
                        if (usersListBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            list = usersListBean.getUsers();
                            dispatchSheetAdapter = new DispatchSheetAdapter(DispatchSheetActivity.this, list);
                            dispatchList.setAdapter(dispatchSheetAdapter);
                        } else {
                            Toast.makeText(DispatchSheetActivity.this, usersListBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(DispatchSheetActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    private void queryTemplate(){
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_WORK_ORDER_TEMPLATES)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .params("delFlag", 0) //默认为0
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        TemplateListBean templateListBean = gson.fromJson(response.body(), TemplateListBean.class);
                        if (templateListBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            templatesBeanList = templateListBean.getTemplates();
                            for (int i = 0; i < templatesBeanList.size(); i++){
                                templateList.add(templatesBeanList.get(i).getUserTeamplateName());
                            }
                        } else {
                            Toast.makeText(DispatchSheetActivity.this, templateListBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(DispatchSheetActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dispatchSheetAdapter.setCurrentSelector(position);
        currentPos = position;
    }

    @OnClick(R.id.right_iv)
    public void onViewClicked() {
        if (templateList.size() > 0){
            showTemplateDialog(templateList);
        }
    }
}
