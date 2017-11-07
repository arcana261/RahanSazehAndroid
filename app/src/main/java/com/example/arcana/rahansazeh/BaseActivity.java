package com.example.arcana.rahansazeh;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by arcana on 11/4/17.
 */

public class BaseActivity extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    protected void setActionBarIcon(int res) {
        android.support.v7.app.ActionBar bar = getSupportActionBar();

        if (bar != null) {
            bar.setDisplayUseLogoEnabled(true);
            bar.setLogo(res);
        }
    }
}
