package com.umow.android;

import android.os.Bundle;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by xuejianyu on 11/25/14.
 */
public class ActivityLandscaper extends Activity_Base {
    @Override
    protected int getContentViewResourceId() {
        return R.layout.activity_landscaper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> users, ParseException e) {

                if (e == null) {
                    // The query was successful.

                    {
                        String[] values = new String[users.size()];

                        int i=0;
                        for (ParseUser user : users) {
                            values[i] = user.getUsername();
                            i++;
                        }

                        {
                            ParseUser user = users.get(0);

                            String displayText = "Username: ";
                            displayText += user.getUsername() + "\n";

                            displayText += "Email: ";
                            displayText += user.getEmail() + "\n";

                            TextView tvLog = (TextView) findViewById(R.id.activity_landscaper_display);
                            tvLog.setText(displayText);
                        }
                    }


                } else {
                    // Something went wrong.
                }
            }
        });
    }
}