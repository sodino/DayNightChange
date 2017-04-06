package com.sodino.titlebar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sodino.titlebar.R;
import com.sodino.titlebar.activity.BaseActivity;

/**
 * Created by Administrator on 2017/4/6.
 */

public class HomeFragment extends com.sodino.titlebar.fragment.BaseFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_home, container, false);
        return v;
    }
    @Override
    protected boolean isFixTransparentStatusBar(){
        return true;
    }
    @Override
    protected void fixTransparentStatusBar(View view) {
        BaseActivity.addTransparentStatusBarBgView(mActivity, view);
    }
}
