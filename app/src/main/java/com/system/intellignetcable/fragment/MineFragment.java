package com.system.intellignetcable.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.system.intellignetcable.R;
import com.system.intellignetcable.activity.DispatchSheetActivity;
import com.system.intellignetcable.activity.LoginActivity;
import com.system.intellignetcable.activity.MainActivity;
import com.system.intellignetcable.activity.OrderStatusListActivity;
import com.system.intellignetcable.activity.SignageManagementActivity;
import com.system.intellignetcable.bean.AppVersionBean;
import com.system.intellignetcable.bean.LoginBean;
import com.system.intellignetcable.bean.PicStringBean;
import com.system.intellignetcable.bean.SignageManagementBean;
import com.system.intellignetcable.bean.UpdateUserBean;
import com.system.intellignetcable.util.AppUtils;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;
import com.system.intellignetcable.view.StringListPopupWindow;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zydu on 2018/11/21.
 */

public class MineFragment extends BaseFragment implements  StringListPopupWindow.OnLocalClickLister, StringListPopupWindow.OnPhotographClickLister  {
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
    @BindView(R.id.pending_review_tv)
    TextView pendingReviewTv;
    @BindView(R.id.reviewed_tv)
    TextView reviewedTv;
    @BindView(R.id.rejected_tv)
    TextView rejectedTv;
    @BindView(R.id.dispatch_line_view)
    View dispatchLineView;
    private View layoutView;
    private MainActivity mainActivity;
    private Gson gson;
    private TakePhoto takePhoto;
    private StringListPopupWindow popupWindow;
    private SimpleDateFormat simpleDateFormat;
    private String userId;


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
        layoutView = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, layoutView);
        initData();
        return layoutView;
    }

    private void initData() {
        gson = new Gson();
        mineInfo();
        versionTv.setText("V" + AppUtils.getVersionName(getActivity()));
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(200 * 1024).setMaxPixel(800).create();
        takePhoto = getTakePhoto();
        takePhoto.onEnableCompress(compressConfig, true);
        headPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });
        userId = (int) SharedPreferencesUtil.get(getActivity(), ParamUtil.USER_ID, 0) + "";
    }


    @Override
    public void takeSuccess(TResult result) {
        if (result != null) {
            ArrayList<TImage> tImageArrayList = result.getImages();
            if (tImageArrayList != null) {
                for (int i = 0; i < tImageArrayList.size(); i++) {
                    File file = new File(tImageArrayList.get(i).getOriginalPath());
                    updataAlbum(file);
                    List<File> files = new ArrayList<>();
                    files.add(file);
                    updateUser(userId, files);
                }
            }
        }

    }


    //更新相册
    private void updataAlbum(File file) {
        //最后通知图库更新
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);//扫描单个文件
        intent.setData(Uri.fromFile(file));//给图片的绝对路径
    }

    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }

    //弹出popupwindow
    public void showPopup() {
        if (popupWindow == null) {
            popupWindow = new StringListPopupWindow(getActivity());
        }
        popupWindow.showAtLocation(layoutView, Gravity.BOTTOM, 0, 0);
        popupWindow.setBackgroundAlpha(getActivity(), 0.5f);
        popupWindow.setOnLocalClickLister(this);
        popupWindow.setOnPhotographClickLister(this);
    }

    @Override
    public void onLocalClick() {
        takePhoto.onPickMultiple(1);
    }

    @Override
    public void onPhotographClick() {
        File file = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera/" + simpleDateFormat.format(System.currentTimeMillis()) + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        //相机获取不剪裁
        takePhoto.onPickFromCapture(imageUri);
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
        getAppVersion();
    }

    @OnClick(R.id.dispatch_list_tv)
    public void onDispatchListTvClicked() {
        startActivity(new Intent(mainActivity, DispatchSheetActivity.class));
    }

    @OnClick(R.id.quit_btn)
    public void onQuitBtnClicked() {
        logout();
    }

    private void mineInfo() {
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
                            if (!mainActivity.isDestroyed()) {
                                Glide.with(mainActivity).load(loginBean.getUser().getHeadImages()).into(headPhoto);
                            }
                            nicknameTv.setText(loginBean.getUser().getUserName());
                            if(loginBean.getUser().getType() == 1){
                                dispatchListTv.setVisibility(View.VISIBLE);
                                dispatchLineView.setVisibility(View.VISIBLE);
                            }else{
                                dispatchListTv.setVisibility(View.GONE);
                                dispatchLineView.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getActivity(), loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    private void logout() {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_LOGOUT)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                .params(ParamUtil.USER_ID, (int) SharedPreferencesUtil.get(getActivity(), ParamUtil.USER_ID, 0))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            SharedPreferencesUtil.remove(getActivity(), ParamUtil.TOKEN);
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            mineFragment = null;
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    private void updateUser(String userId, final List<File> files){
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.UPDATEUSER + "?" + "userId=" + userId)
                .tag(this)
                .isMultipart(true)
                .addFileParams("file", files)
                .headers("token", (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UpdateUserBean updateUserBean = gson.fromJson(response.body(), UpdateUserBean.class);
                        if(updateUserBean.getResult().equals("true")){
                            if (!mainActivity.isDestroyed() && !files.isEmpty() && files.get(0).exists()) {
                                Glide.with(mainActivity).load(files.get(0)).into(headPhoto);
                            }
                        }
                        Toast.makeText(getActivity(), updateUserBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    //查询APP最新版本
    private void getAppVersion() {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_SYS_APP_VERSION)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(getActivity(), ParamUtil.TOKEN, ""))
                .params(ParamUtil.TYPE, 1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        AppVersionBean appVersionBean = gson.fromJson(response.body(), AppVersionBean.class);
                        if (appVersionBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (appVersionBean.getObj().getAppVersion() != null && !appVersionBean.getObj().getAppVersion().equals("V" + AppUtils.getVersionName(getActivity()))) {
                                showUpdateDialog(appVersionBean.getObj().getAppUrl());
                            }
                        } else {
                            Toast.makeText(getActivity(), appVersionBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getActivity(), "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    @OnClick(R.id.pending_review_tv)
    public void onPendingReviewTvClicked() {
        startStatusListActivity(2);
    }

    @OnClick(R.id.reviewed_tv)
    public void onReviewedTvClicked() {
        startStatusListActivity(3);
    }

    @OnClick(R.id.rejected_tv)
    public void onRejectedTvClicked() {
        startStatusListActivity(4);
    }

    //进入工单状态列表界面
    private void startStatusListActivity(int type) {
        Intent intent = new Intent(mainActivity, OrderStatusListActivity.class);
        intent.putExtra(ParamUtil.ORDER_STATUS, type);
        startActivity(intent);
    }

    //弹出是否升级提示框
    private void showUpdateDialog(final String appUrl) {
        //创建AlertDialog的构造器的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        //构造器内容,为对话框设置文本项(之后还有列表项的例子)
        builder.setMessage("是否升级？");
        //为构造器设置确定按钮,第一个参数为按钮显示的文本信息，第二个参数为点击后的监听事件，用匿名内部类实现
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(appUrl);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
}
