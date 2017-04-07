package com.sodino.titlebar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.sodino.change.R;
import com.sodino.titlebar.activity.BaseActivity;

/**
 * Created by Administrator on 2017/4/6.
 */

public class MineFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
    private CheckBox checkBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_mine, container, false);
        checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        boolean isNight = BaseActivity.isNight();
        checkBox.setChecked(isNight);
        checkBox.setOnCheckedChangeListener(this);
        return v;
    }

    @Override
    protected boolean isFixTransparentStatusBar() {
        return true;
    }

    @Override
    protected void fixTransparentStatusBar(View view) {
        BaseActivity.addTransparentStatusBarBgView(mActivity, view);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            mActivity.changeTheme(true);
        } else {
            mActivity.changeTheme(false);
        }
    }
}
