package com.sodino.titlebar.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.sodino.titlebar.DeviceUtil;
import com.sodino.titlebar.R;
import com.sodino.titlebar.StatusbarColorUtils;

/**
 * Created by Administrator on 2017/1/13.
 */

public class BaseActivity extends FragmentActivity {
    protected static int themeID = R.style.Day;
    protected View rootView;
    protected View viewStatusbarBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeID);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View v = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(v);
    }

    @Override
    public void setContentView(View view) {
        rootView = view;
        super.setContentView(view);

        if (isFixTransparentStatusBar()) {
            Window window = getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int visibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 亮色模式,避免系统状态栏的图标不可见
                    visibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                window.getDecorView().setSystemUiVisibility(visibility);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);

                fixTransparentStatusBar(view);
                // 最后fix一下状态栏背景白色与系统的文字图标白色的问题
                fixTransparentStatusBarWhiteTextColor(view, viewStatusbarBackground);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                WindowManager.LayoutParams localLayoutParams = window.getAttributes();
//                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                fixTransparentStatusBar(view);
                // 最后fix一下状态栏背景白色与系统的文字图标白色的问题
                fixTransparentStatusBarWhiteTextColor(view, viewStatusbarBackground);
            } else {
                setStatusbarBackgroundGone();
            }
        } else {
            setStatusbarBackgroundGone();
        }
    }


    protected void setStatusbarBackgroundGone() {
        if (viewStatusbarBackground != null && viewStatusbarBackground.getVisibility() != View.GONE) {
            viewStatusbarBackground.setVisibility(View.GONE);
        }
        View v = findViewById(R.id.status_bar_background);
        // setContentViewNoTitlebar()的话，viewStatusbarBackground为null
        if (v != null && v.getVisibility() != View.GONE) {
            v.setVisibility(View.GONE);
        }
    }

    protected boolean isFixTransparentStatusBar() {
        return true;
    }

    protected void fixTransparentStatusBar(View view) {
        // 当出现自定义TransparentStatusbarView时，重载处理
    }

    public boolean fixTransparentStatusBarWhiteTextColor(View rootView, View viewTSBarBg) {
        // 是否需要更新状态栏背景，避免看不见状态栏白色的文字
        boolean dark = themeID == R.style.Day;
        if (DeviceUtil.isMeizu()) {
            StatusbarColorUtils.fix(DeviceUtil.MEIZU, this ,dark);
            return true;
        }else if (DeviceUtil.isMIUI()) {
            StatusbarColorUtils.fix(DeviceUtil.XIAOMI, this ,dark);
            return true;
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // M及以上会使用亮色模式,不需要修改
            return false;
        } else if (viewTSBarBg != null && viewTSBarBg.getVisibility() == View.VISIBLE) {
            // 其它的机子在android 4.4到5.2之间的，都没办法改状态栏图标及文字的颜色，所以要改背景
            viewTSBarBg.setBackgroundResource(R.drawable.status_bar_background);
            return true;
        } else {
            return false;
        }
    }

    public View getTransparentStatusBarView() {
        View v = LayoutInflater.from(this).inflate(R.layout.transparent_status_bar_bg_view, null, false);
        return v;
    }
    /**
     * 目前只处理了LinearLayout,后续根据实际情况请自行扩展．
     * */
    public static View addTransparentStatusBarBgView(BaseActivity baseActivity, View view) {
        if (baseActivity == null) {
            return null;
        }
        View tsbar = null;
        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;
            if (linearLayout.getOrientation() == LinearLayout.VERTICAL) {
                tsbar = baseActivity.getTransparentStatusBarView();
                LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) baseActivity.getResources().getDimension(R.dimen.status_bar_height));
                linearLayout.addView(tsbar, 0, layParams);
            }
        }

        baseActivity.fixTransparentStatusBarWhiteTextColor(view, tsbar);

        return tsbar;
    }

    public static int getThemeID(){
        return themeID;
    }

    public void changeTheme(int themeID) {
        BaseActivity.themeID = themeID;
        View decorView = getWindow().getDecorView();
        Log.d("Test", "decorView : " + decorView.toString());

        this.finish();
        // 定义两个动画效果，确保Activity切换效果不明显即可
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Intent intent = new Intent(this, this.getClass());
        intent.putExtra("changeTheme", true);
        startActivity(intent);
    }
}
