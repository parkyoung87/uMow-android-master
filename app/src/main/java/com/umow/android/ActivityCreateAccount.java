package com.umow.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.umow.android.util.UtilToast;

/**
 * Created by xuejianyu on 11/17/14.
 */
public class ActivityCreateAccount extends Activity_Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final EditText etEmail = (EditText) findViewById(R.id.activty_createaccount_et_email);

        final EditText etPassword = (EditText) findViewById(R.id.activty_createaccount_et_password);
        final EditText etPasswordConfirm = (EditText) findViewById(R.id.activty_createaccount_et_passwordconfirm);
        Button buttonCreateAccount = (Button) findViewById(R.id.activty_createaccount_button_createaccount);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String passwordConfirm = etPasswordConfirm.getText().toString();

                if(password.length() <= 5) {
                    UtilToast.showToast(ActivityCreateAccount.this, "Password must be more than 6 characters");
                    return;
                }

                if(!password.equals(passwordConfirm)) {
                    UtilToast.showToast(ActivityCreateAccount.this, "Passwords do not match");
                    return;
                }

                ParseUser user = new ParseUser();
                user.setUsername(email);
                user.setPassword(password);
                user.setEmail(email);

                final ProgressDialog progressDialog = new ProgressDialog(ActivityCreateAccount.this);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        progressDialog.dismiss();

                        if (e == null) {
                            // Hooray! Let them use the app now.
                            UtilToast.showToast(ActivityCreateAccount.this, "User created successfully");
                            finish();
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            UtilToast.showToast(ActivityCreateAccount.this, e.getMessage());
                        }
                    }
                });

            }
        });






    }

    @Override
    protected int getContentViewResourceId() {
        return R.layout.activity_createaccount;
    }

}
