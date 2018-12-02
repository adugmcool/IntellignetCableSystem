package com.system.intellignetcable.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.system.intellignetcable.R;

/**
 * Created by adu on 2018/6/24.
 */

public class StringListPopupWindow extends PopupWindow implements View.OnClickListener {
    private TextView localTv;
    private TextView photographTv;
    private TextView cancelTv;
    private Activity activity;
    private final View view;

    public StringListPopupWindow(Activity activity) {
        this.activity = activity;
        view = LayoutInflater.from(activity).inflate(R.layout.popupwindow_text, null);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setContentView(view);
        setBackgroundAlpha(activity, 0.5f);
        initView();
    }

    private void initView() {
        localTv = view.findViewById(R.id.local_tv);
        photographTv = view.findViewById(R.id.photograph_tv);
        cancelTv = view.findViewById(R.id.cancel_tv);
        localTv.setOnClickListener(this);
        photographTv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (activity != null)
                    setBackgroundAlpha(activity, 1f);
            }
        });
    }

    /**
     * 设置页面的透明度
     *
     * @param bgAlpha 1表示不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.local_tv:
                if (onLocalClickLister != null)
                    onLocalClickLister.onLocalClick();
                break;
            case R.id.photograph_tv:
                if (onPhotographClickLister != null)
                    onPhotographClickLister.onPhotographClick();
                break;
            case R.id.cancel_tv:
                break;
        }
        this.dismiss();
    }

    private OnLocalClickLister onLocalClickLister;

    private OnPhotographClickLister onPhotographClickLister;

    public void setOnLocalClickLister(OnLocalClickLister onLocalClickLister) {
        this.onLocalClickLister = onLocalClickLister;
    }

    public void setOnPhotographClickLister(OnPhotographClickLister onPhotographClickLister) {
        this.onPhotographClickLister = onPhotographClickLister;
    }

    public interface OnLocalClickLister {
        void onLocalClick();
    }

    public interface OnPhotographClickLister {
        void onPhotographClick();
    }
}
