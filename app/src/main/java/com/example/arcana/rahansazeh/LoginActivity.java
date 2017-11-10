package com.example.arcana.rahansazeh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.arcana.rahansazeh.validation.NationalCodeValidator;
import com.example.arcana.rahansazeh.validation.TextValidator;

public class LoginActivity extends BaseActivity {
    private EditText userName;
    private TextValidator userNameValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forceRTLIfSupported();
        setActionBarIcon(R.drawable.rahan_sazeh_logo);

        userName = findViewById(R.id.userName);
        userNameValidator = new NationalCodeValidator(userName, false);
        TextValidator.applyValidation(userName, userNameValidator);
    }

    public void onLoginClicked(View view) {
        if (userNameValidator.isValid()) {
            Intent intent = new Intent(this, ProjectSelectionActivity.class);

            intent.putExtra("userName", userName.getText().toString());

            startActivity(intent);
        }
    }
}
