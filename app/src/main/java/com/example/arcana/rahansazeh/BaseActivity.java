package com.example.arcana.rahansazeh;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.arcana.rahansazeh.model.DaoSession;
import com.example.arcana.rahansazeh.service.ServiceFactory;
import com.example.arcana.rahansazeh.service.api.RahanSazehServiceFactory;
import com.example.arcana.rahansazeh.service.mock.MockServiceFactory;

/**
 * Created by arcana on 11/4/17.
 */

public class BaseActivity extends AppCompatActivity {
    private boolean backButtonEnabled;
    private ServiceFactory serviceFactory;

    public BaseActivity() {
        this.backButtonEnabled = false;
        this.serviceFactory = getServiceFactory();
    }

    public static ServiceFactory getServiceFactory() {
        return new RahanSazehServiceFactory();
    }

    protected ServiceFactory services() {
        return serviceFactory;
    }

    protected String getStringResource(int resId) {
        return getResources().getString(resId);
    }

    protected DaoSession getDaoSession() {
        return ((RahanSazehApp)getApplication()).getDaoSession();
    }

    protected void showErrorDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "تأیید",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    protected void showErrorDialog(String message) {
        showErrorDialog(getResources().getString(R.string.error), message);
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

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

    @Override
    public boolean onSupportNavigateUp(){
        if (backButtonEnabled) {
            finish();
            return true;
        }
        else {
            return false;
        }
    }

    protected void enableBackButton() {
        android.support.v7.app.ActionBar bar = getSupportActionBar();

        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            backButtonEnabled = true;
        }
    }
}
