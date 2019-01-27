package com.system.intellignetcable.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhotoFragment;
import com.system.intellignetcable.R;

/**
 * Created by adu on 2018/12/2.
 */

public class BaseFragment extends TakePhotoFragment {
    protected TextView baseHintTv;
    protected View baseContentRl;

    protected void setLoadingView(TextView baseHintTv, View baseContentRl){
        this.baseContentRl = baseContentRl;
        this.baseHintTv = baseHintTv;
    }

    protected void showLoading(){
        baseHintTv.setVisibility(View.VISIBLE);
        baseHintTv.setText(R.string.data_loading);
        baseContentRl.setVisibility(View.GONE);
    }

    protected void showDataSuc(){
        baseHintTv.setVisibility(View.GONE);
        baseContentRl.setVisibility(View.VISIBLE);
    }

    protected void showFail(){
        baseHintTv.setVisibility(View.VISIBLE);
        baseHintTv.setText(R.string.data_load_fail);
        baseContentRl.setVisibility(View.GONE);
    }

    protected void showNoData(){
        baseHintTv.setVisibility(View.VISIBLE);
        baseHintTv.setText(R.string.not_data);
        baseContentRl.setVisibility(View.GONE);
    }

}
