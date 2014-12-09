package com.umow.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by xuejianyu on 11/16/14.
 */

public class ActivityFirstPage extends Activity_Base {

    private EditText UsernameView;
    private EditText PasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        {
            Button buttonJohn = (Button) findViewById(R.id.activity_login_button_john);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityFirstPage.this, ActivityJohn.class);
                    startActivity(intent);
                }
            };
            buttonJohn.setOnClickListener(onClickListener);
        }



        UsernameView = (EditText) findViewById(R.id.Activity_firstpage_username);
        PasswordView = (EditText) findViewById(R.id.activity_firstpage_password);

        findViewById(R.id.activity_login_button_login).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Validate the log in data
                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder("Please");
                if (isEmpty(UsernameView)) {
                    validationError = true;
                    validationErrorMessage.append("Enter your username");
                }
                if (isEmpty(PasswordView)) {
                    if (validationError) {
                        validationErrorMessage.append(",and");
                    }
                    validationError = true;
                    validationErrorMessage.append("enter your password");
                }
                validationErrorMessage.append(".");

                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(ActivityFirstPage.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(ActivityFirstPage.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Logging in.  Please wait.");
                dlg.show();
                // Call the Parse login method
                ParseUser.logInInBackground(UsernameView.getText().toString(), PasswordView.getText()
                        .toString(), new LogInCallback() {

                    @Override
                    public void done(ParseUser user, ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(ActivityFirstPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Start an intent for the dispatch activity
                            Intent intent = new Intent(ActivityFirstPage.this, ActivitySandbox.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }
        });




        {
            ((Button) findViewById(R.id.activity_login_button_createaccount)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ActivityFirstPage.this, ActivityCreateAccount.class);
                    startActivity(intent);
                }
            });
        }

    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
    @Override protected int getContentViewResourceId() {
        return R.layout.activity_firstpage;
    }
}
