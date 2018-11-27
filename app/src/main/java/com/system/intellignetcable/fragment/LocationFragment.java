package com.system.intellignetcable.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.intellignetcable.R;

/**
 * Created by zydu on 2018/11/21.
 */

public class LocationFragment extends Fragment {
    private static LocationFragment locationFragment;

    public static LocationFragment getInstance(){
        if(locationFragment == null){
            locationFragment = new LocationFragment();
        }
        return locationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container,false);
        return view;
    }
}
