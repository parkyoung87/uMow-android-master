package com.umow.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.umow.android.util.UtilToast;

import java.util.List;

/**
 * Created by xuejianyu on 11/25/14.
 */
public class ActivityAdmin extends Activity_Base {
    @Override
    protected int getContentViewResourceId() {
        return R.layout.activity_admin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button buttonGetUsers = (Button) findViewById(R.id.activity_admin_button_getusers);
        buttonGetUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final ProgressDialog progressDialog = new ProgressDialog(ActivityAdmin.this);
                progressDialog.setMessage("Retrieving Users...");
                progressDialog.show();

                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> users, ParseException e) {
                        progressDialog.dismiss();

                        if (e == null) {
                            // The query was successful.
                            {
                                String log = "";
                                for (int i = 0; i < users.size(); i++) {
                                    ParseUser user = users.get(i);
                                    log += user.getUsername() + "\n";
                                }

                                TextView tvLog = (TextView) findViewById(R.id.activity_admin_textview_log);
                                tvLog.setText(log);
                            }

                            {
                                final ListView listView = (ListView) findViewById(R.id.activity_admin_listview_users);

                                String[] values = new String[users.size()];

                                int i=0;
                                for(ParseUser user : users) {
                                    values[i] = user.getUsername();
                                    i++;
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityAdmin.this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
                                listView.setAdapter(adapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // ListView Clicked item index
                                        int itemPosition     = position;

                                        // ListView Clicked item value
                                        String  itemValue    = (String) listView.getItemAtPosition(position);

                                        // Show Alert
                                        UtilToast.showToast(ActivityAdmin.this, "Position :" + itemPosition + "  ListItem : " + itemValue);
                                    }

                                });
                            }


                        } else {
                            // Something went wrong.
                        }
                    }
                });

            }
        });
    }
}
