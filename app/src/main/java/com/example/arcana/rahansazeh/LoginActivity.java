package com.example.arcana.rahansazeh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.arcana.rahansazeh.model.DaoSession;
import com.example.arcana.rahansazeh.model.User;
import com.example.arcana.rahansazeh.model.UserDao;
import com.example.arcana.rahansazeh.service.LoginService;
import com.example.arcana.rahansazeh.utils.AsyncTaskResult;
import com.example.arcana.rahansazeh.validation.NationalCodeValidator;
import com.example.arcana.rahansazeh.validation.TextValidator;

import java.util.List;

public class LoginActivity extends BaseActivity {
    private EditText etUserName;
    private TextValidator userNameValidator;
    private UserDao userDao;

    private class LoginAsyncTask extends AsyncTask<String, Void, AsyncTaskResult<Boolean>> {
        private ProgressDialog progressDialog;
        private String userName;
        private boolean preLoggedIn;

        public LoginAsyncTask(String userName) {
            this.userName = userName;
        }

        @Override
        protected AsyncTaskResult<Boolean> doInBackground(String... args) {
            try {
                if (preLoggedIn) {
                    return new AsyncTaskResult<Boolean>(true);
                }
                else {
                    LoginService service = services().createLogin();
                    return new AsyncTaskResult<Boolean>(service.Login(userName));
                }
            }
            catch (Exception err) {
                return new AsyncTaskResult<Boolean>(err);
            }
        }

        @Override
        protected void onPreExecute() {
            List<User> result = userDao.queryBuilder()
                    .where(UserDao.Properties.NationalCode.eq(userName))
                    .limit(1)
                    .offset(0)
                    .list();

            preLoggedIn = result.size() > 0;

            if (!preLoggedIn) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("ورود به نرم‌افزار");
                progressDialog.setMessage(LoginActivity.this.getResources().getString(R.string.please_wait));
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
            }
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<Boolean> result) {
            if (!preLoggedIn) {
                progressDialog.cancel();
            }

            if (result.getError() != null) {
                result.getError().printStackTrace();
                showErrorDialog(getStringResource(R.string.connection_error));
            }
            else if (!result.getResult()) {
                showErrorDialog("نام کاربری اشتباه است");
            }
            else {
                if (!preLoggedIn) {
                    User newUser = new User(null, userName);
                    userDao.insert(newUser);
                }

                Intent intent = new Intent(LoginActivity.this,
                        ProjectSelectionActivity.class);

                intent.putExtra("userName", etUserName.getText().toString());

                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forceRTLIfSupported();
        setActionBarIcon(R.drawable.rahan_sazeh_logo);

        etUserName = findViewById(R.id.userName);
        userNameValidator = new NationalCodeValidator(etUserName, false);
        TextValidator.applyValidation(etUserName, userNameValidator);

        userDao = getDaoSession().getUserDao();
    }

    public void onLoginClicked(View view) {
        if (userNameValidator.isValid()) {
            String userName = etUserName.getText().toString();
            new LoginAsyncTask(userName).execute();
        }
    }
}
