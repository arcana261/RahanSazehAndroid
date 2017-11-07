package com.example.arcana.rahansazeh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forceRTLIfSupported();
        setActionBarIcon(R.drawable.rahan_sazeh_logo);
    }

    public void onLoginClicked(View view) {
        //Toast.makeText(this, "hi!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ProjectSelectionActivity.class);
        startActivity(intent);
    }
}
