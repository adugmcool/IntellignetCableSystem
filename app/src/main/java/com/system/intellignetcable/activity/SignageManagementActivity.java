package com.system.intellignetcable.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.system.intellignetcable.R;
import com.system.intellignetcable.adapter.ImageAdapter;
import com.system.intellignetcable.adapter.SignageOrderInfoAdapter;
import com.system.intellignetcable.bean.PicStringBean;
import com.system.intellignetcable.bean.SignageManagementBean;
import com.system.intellignetcable.util.ParamUtil;
import com.system.intellignetcable.util.SharedPreferencesUtil;
import com.system.intellignetcable.util.UrlUtils;
import com.system.intellignetcable.view.StringListPopupWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zydu on 2018/11/30.
 */

public class SignageManagementActivity extends TakePhotoActivity implements ImageAdapter.OnAddOrDeleteImageClickListener, StringListPopupWindow.OnLocalClickLister, StringListPopupWindow.OnPhotographClickLister {
    private static final String TAG = "SignageManagement";
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.right_iv)
    TextView rightIv;
    @BindView(R.id.hint_tv)
    TextView hintTv;
    private Gson gson;
    private List<SignageManagementBean.SignBoardBean.TemplateValuesBean> templateValuesBeanList;
    private SignageOrderInfoAdapter signageOrderInfoAdapter;
    private SignageManagementBean signageManagementBean;
    private List<PicStringBean> imageList;
    private List<String> imageFromServeList;
    private ImageAdapter imageAdapter;
    private StringListPopupWindow popupWindow;
    private SimpleDateFormat simpleDateFormat;
    private TakePhoto takePhoto;
    private List<File> picFiles;
    private String epcId;
    private Bundle savedInstanceState;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        epcId = getIntent().getStringExtra("epcId");
////        if(TextUtils.isEmpty(epcId)){
//            epcId = "E20068080000000000000002";
////        }
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.fragment_order_mangement_detail);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleTv.setText(R.string.signage_management);
        rightIv.setVisibility(View.VISIBLE);
        rightIv.setText(R.string.submit);
        hintTv.setVisibility(View.VISIBLE);
        hintTv.setText(R.string.data_loading);
        recyclerView.setVisibility(View.GONE);
        gson = new Gson();
        templateValuesBeanList = new ArrayList<>();
        imageList = new ArrayList<>();
        picFiles = new ArrayList<>();
        imageFromServeList = new ArrayList<>();
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        imageAdapter = new ImageAdapter(this, imageList);
        imageAdapter.setOnDeleteImageClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        signageOrderInfoAdapter = new SignageOrderInfoAdapter(this, templateValuesBeanList, imageAdapter, savedInstanceState);
        //设置Adapter
        recyclerView.setAdapter(signageOrderInfoAdapter);
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, OrientationHelper.HORIZONTAL));

        if (((int) SharedPreferencesUtil.get(SignageManagementActivity.this, ParamUtil.TYPE, 2)) == 1) {
            signageManagementDetail(epcId);
        } else {
            signageManagementScan(epcId);
        }

        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(200 * 1024).setMaxPixel(800).create();
        takePhoto = getTakePhoto();
        takePhoto.onEnableCompress(compressConfig, true);
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
                        hintTv.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        signageManagementBean = gson.fromJson(response.body(), SignageManagementBean.class);
                        if (signageManagementBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (signageManagementBean.getSignBoard().getTemplateValues() != null && signageManagementBean.getSignBoard().getTemplateValues().size() > 0) {
                                templateValuesBeanList.addAll(signageManagementBean.getSignBoard().getTemplateValues());
                            }
                            String pathString = signageManagementBean.getSignBoard().getImagesUrls();
                            if (!TextUtils.isEmpty(pathString)) {
                                String[] strings = pathString.split(",");
                                for (String s : strings) {
                                    imageFromServeList.add(s);
                                    imageList.add(new PicStringBean(s, true));
                                }
                            }
                            signageOrderInfoAdapter.notifyDataSetChanged();
                            imageAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(SignageManagementActivity.this, signageManagementBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        hintTv.setVisibility(View.VISIBLE);
                        hintTv.setText(R.string.data_load_fail);
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(SignageManagementActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    private void signageManagementDetail(String epc) {
        OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.SIGNBOARDDETAIL)
                .tag(this)
                .headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .params("epc", epc)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        hintTv.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        signageManagementBean = gson.fromJson(response.body(), SignageManagementBean.class);
                        if (signageManagementBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            if (signageManagementBean.getSignBoard().getTemplateValues() != null && signageManagementBean.getSignBoard().getTemplateValues().size() > 0) {
                                templateValuesBeanList.addAll(signageManagementBean.getSignBoard().getTemplateValues());
                            }
                            String pathString = signageManagementBean.getSignBoard().getImagesUrls();
                            if (!TextUtils.isEmpty(pathString)) {
                                String[] strings = pathString.split(",");
                                for (String s : strings) {
                                    imageFromServeList.add(s);
                                    imageList.add(new PicStringBean(s, true));
                                }
                            }
                            signageOrderInfoAdapter.notifyDataSetChanged();
                            imageAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(SignageManagementActivity.this, signageManagementBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        hintTv.setVisibility(View.VISIBLE);
                        hintTv.setText(R.string.data_load_fail);
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(SignageManagementActivity.this, "请求错误！", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "请求错误：" + response.code() + "-------" + response.message());
                    }
                });
    }

    public String getImageUrls() {
        String imageUrls;
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < imageFromServeList.size(); i++) {
            stringBuilder.append(imageFromServeList.get(i) + ",");
        }
        if(stringBuilder.length() == 0){
            return "";
        }
        imageUrls = stringBuilder.substring(0, stringBuilder.length() - 1);
        return imageUrls;
    }

    // 将所有参数拼接
    private void getParamsString() {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < signageManagementBean.getSignBoard().getTemplateValues().size(); i++) {
            sb.append(signageManagementBean.getSignBoard().getTemplateValues().get(i).getFieldName() + "=" + signageManagementBean.getSignBoard().getTemplateValues().get(i).getFieldValue() + "&");
        }
        Log.i(TAG, "urlString----" + sb.substring(0, sb.length() - 1));
        saveOrUpdate(signageManagementBean.getSignBoard().getEpc(), signageManagementBean.getSignBoard().getUserId(),
                signageManagementBean.getSignBoard().getWorkOrderId(), getImageUrls(),
                String.valueOf(signageOrderInfoAdapter.getmCurrentLon()), String.valueOf(signageOrderInfoAdapter.getmCurrentLat()),
                signageOrderInfoAdapter.getmCurrentAdress(), sb.substring(0, sb.length() - 1), picFiles);
    }

    //保存或修改epc
    private void saveOrUpdate(String epc, int userId, int workOrderId, String imagesUrls, String longitude, String latitude, String detailAddress, String ss, List<File> files) {
        Log.i(TAG, "urlString------" + UrlUtils.TEST_URL + UrlUtils.METHOD_POST_SIGN_BOARD_SAVE_UPDATE + "?" + "epc=" + epc + "&" + "userId=" + userId + "&" + "workOrderId=" + workOrderId + "&"
                + "imagesUrls=" + imagesUrls + "&" + "longitude=" + longitude + "&" + "latitude=" + latitude + "&" + "detailAddress=" + detailAddress + "&" + ss);
        PostRequest request = OkGo.<String>post(UrlUtils.TEST_URL + UrlUtils.METHOD_POST_SIGN_BOARD_SAVE_UPDATE + "?" + "epc=" + epc + "&" + "userId=" + userId + "&" + "workOrderId=" + workOrderId + "&"
                + "imagesUrls=" + imagesUrls + "&" + "longitude=" + longitude + "&" + "latitude=" + latitude + "&" + "detailAddress=" + detailAddress + "&" + ss)
                .tag(this)
                .isMultipart(true);

        if (!files.isEmpty()) {
            request.addFileParams("picfiles", files);
        } else {
            request.params("avatar\"; filename=\"" + "1.png", drawableToFile(SignageManagementActivity.this, R.drawable.login_head, "1.png"));
        }
        request.headers("token", (String) SharedPreferencesUtil.get(this, ParamUtil.TOKEN, ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        SignageManagementBean managementBean = gson.fromJson(response.body(), SignageManagementBean.class);
                        if (managementBean.getMsg().equals(UrlUtils.METHOD_POST_SUCCESS)) {
                            finish();
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


    /**
     * drawable转为file
     *
     * @param mContext
     * @param drawableId drawable的ID
     * @param fileName   转换后的文件名
     * @return
     */
    public File drawableToFile(Context mContext, int drawableId, String fileName) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), drawableId);
        String defaultPath = mContext.getFilesDir()
                .getAbsolutePath() + "/defaultGoodInfo";
        File file = new File(defaultPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String defaultImgPath = defaultPath + "/" + fileName;
        file = new File(defaultImgPath);
        if(file.exists()){
            return file;
        }
        try {
            file.createNewFile();

            FileOutputStream fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 20, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @OnClick(R.id.right_iv)
    public void onRightClicked() {
        getParamsString();
    }

    @Override
    public void takeSuccess(TResult result) {
        if (result != null) {
            ArrayList<TImage> tImageArrayList = result.getImages();
            if (tImageArrayList != null) {
                for (int i = 0; i < tImageArrayList.size(); i++) {
                    imageList.add(new PicStringBean(tImageArrayList.get(i).getOriginalPath(), false));
                    picFiles.add(new File(tImageArrayList.get(i).getOriginalPath()));
                    updataAlbum(new File(tImageArrayList.get(i).getOriginalPath()));
                }
            }
        }
        imageAdapter.notifyDataSetChanged();
    }


    //更新相册
    private void updataAlbum(File file) {
        //最后通知图库更新
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);//扫描单个文件
        intent.setData(Uri.fromFile(file));//给图片的绝对路径
        sendBroadcast(intent);
    }

    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }

    @Override
    public void deleteImageClick(int pos) {
        if (imageList.get(pos).isFromServe()) {
            imageFromServeList.remove(pos);
        } else {
            picFiles.remove(pos - (imageFromServeList.size() - 1));
        }
        imageList.remove(pos);
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void addImageClick() {
        showPopup();
    }

    //弹出popupwindow
    public void showPopup() {
        if (popupWindow == null) {
            popupWindow = new StringListPopupWindow(this);
        }
        popupWindow.showAtLocation(recyclerView, Gravity.BOTTOM, 0, 0);
        popupWindow.setBackgroundAlpha(this, 0.5f);
        popupWindow.setOnLocalClickLister(this);
        popupWindow.setOnPhotographClickLister(this);
    }

    @Override
    public void onLocalClick() {
        takePhoto.onPickMultiple(100);
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
    protected void onDestroy() {
        imageList.clear();
        super.onDestroy();
    }
}
