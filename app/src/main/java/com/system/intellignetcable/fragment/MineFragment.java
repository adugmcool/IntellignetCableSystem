package com.system.intellignetcable.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.LoginActivity;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.bean.LoginBean;
import com.system.intellignetcable.util.AppUtils;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zydu on 2018/11/21.
 */

public class MineFragment extends Fragment {
    private static final String TAG = "MineFragment";
    private static MineFragment mineFragment;
    @BindView(R.id.head_photo)
    CircleImageView headPhoto;
    @BindView(R.id.nickname_tv)
    TextView nicknameTv;
    @BindView(R.id.version_tv)
    TextView versionTv;
    @BindView(R.id.updates_rl)
    RelativeLayout updatesRl;
    @BindView(R.id.dispatch_list_tv)
    TextView dispatchListTv;
    @BindView(R.id.quit_btn)
    Button quitBtn;
    Unbinder unbinder;
    private MainActivity mainActivity;
    private Gson gson;

    public static MineFragment getInstance() {
        if (mineFragment == null) {
            mineFragment = new MineFragment();
        }
        return mineFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        gson = new Gson();
        mineInfo();
        versionTv.setText("v" + AppUtils.getVersionName(getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.head_photo)
    public void onHeadPhotoClicked() {
    }

    @OnClick(R.id.updates_rl)
    public void onUpdatesRlClicked() {
    }

    @OnClick(R.id.dispatch_list_tv)
    public void onDispatchListTvClicked() {
        mainActivity.switchFragment(DispatchSheetFragment.getInstance()).addToBackStack(null).commitAllowingStateLoss();
    }

    @OnClick(R.id.quit_btn)
    public void onQuitBtnClicked() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    private void mineInfo(){
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_GET_USER)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                .params(ParamUtil.TOKEN, (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                .params(ParamUtil.USER_ID, (int) SharedPreferencesUtil.get(getActivity(), ParamUtil.USER_ID, 0))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            Glide.with(mainActivity).load(loginBean.getUser().getHeadImages()).into(headPhoto);
                            nicknameTv.setText(loginBean.getUser().getUserName());
                        }else {
                            Toast.makeText(getActivity(), loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误："+ response.code() + "-------" + response.message());
                    }
                });
    }

    private void logout(){
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_LOGOUT)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                .params(ParamUtil.TOKEN, (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                .params(ParamUtil.USER_ID, (int) SharedPreferencesUtil.get(getActivity(), ParamUtil.USER_ID, 0))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            SharedPreferencesUtil.put(getActivity(), ParamUtil.TOKEN, "");
                        }else {
                            Toast.makeText(getActivity(), loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误："+ response.code() + "-------" + response.message());
                    }
                });
    }
}
