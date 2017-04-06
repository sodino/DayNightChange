package com.sodino.titlebar.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;

import com.sodino.titlebar.R;
import com.sodino.titlebar.fragment.BaseFragment;
import com.sodino.titlebar.fragment.CartFragment;
import com.sodino.titlebar.fragment.ContactFragment;
import com.sodino.titlebar.fragment.HomeFragment;
import com.sodino.titlebar.fragment.MineFragment;

public class MainActivity extends TitlebarActivity implements View.OnClickListener {
    private FragmentManager fgManager;
    private BaseFragment currentFragment;//当前的fragment
    private BaseFragment fragHome, fragContact, fragCart, fragMine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewNoTitlebar(R.layout.activity_main);

        fgManager = getSupportFragmentManager();


        LinearLayout toolbar = ((LinearLayout)findViewById(R.id.toolbar));
        int count = toolbar.getChildCount();
        for (int i = 0;i < count;i ++) {
            View v = toolbar.getChildAt(i);
            v.setOnClickListener(this);
        }

        boolean changeTheme = getIntent().getBooleanExtra("changeTheme", false);
        if (changeTheme){
            onClick(findViewById(R.id.mine));
        }else {
            onClick(findViewById(R.id.home));
        }
    }

    @Override
    public void onClick(View v) {
        int vID = v.getId();

        switch(vID) {
            case R.id.home:{
                if (fragHome == null) {
                    fragHome = new HomeFragment();
                }

                switchFragment(fragHome, vID);
            }break;
            case R.id.contact:{
                if (fragContact == null) {
                    fragContact = new ContactFragment();
                }

                switchFragment(fragContact, vID);
            }break;
            case R.id.cart:{
                if (fragCart == null) {
                    fragCart = new CartFragment();
                }

                switchFragment(fragCart, vID);
            }break;
            case R.id.mine:{
                if (fragMine == null) {
                    fragMine = new MineFragment();
                }

                switchFragment(fragMine, vID);
            }break;
        }
    }

    private void switchFragment(BaseFragment to, int vID) {
        if (currentFragment != to) {
            FragmentTransaction ft = fgManager.beginTransaction();
            if (to.isAdded()) {
                ft.hide(currentFragment).show(to);
            } else {
                if(currentFragment != null) {
                    ft.hide(currentFragment);
                }
                ft.add(R.id.layoutContent, to, String.valueOf(vID));
            }
            ft.commit();

            currentFragment = to;
        }
    }
}
