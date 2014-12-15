package com.umow.android;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuejianyu on 11/17/14.
 */
public class ActivitySandbox extends Activity_Base {

    List<String> drawer_items;
    DrawerLayout drawerLayout;
    ListView lvNavigationDrawer;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawer_items = new ArrayList<String>();
        drawer_items.add("Search Activity");
        drawer_items.add("Landscaper Activity");
        drawer_items.add("Payment Activity");
        drawer_items.add("Admin Activity");

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_sandbox_drawerLayout);
        lvNavigationDrawer = (ListView) findViewById(R.id.activity_sandbox_navigationDrawer);

        lvNavigationDrawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawer_items));
        lvNavigationDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // Search Activity
                if(position == 0) {
                    Intent intent = new Intent(ActivitySandbox.this, ActivitySearch.class);
                    startActivity(intent);
                }
                // Landscaper
                else if(position == 1) {
                    Intent intent = new Intent(ActivitySandbox.this, ActivityLandscaper.class);
                    startActivity(intent);
                }
                // Payment
                else if(position == 2) {
                    Intent intent = new Intent(ActivitySandbox.this, ActivityPayment.class);
                    startActivity(intent);
                }
                // Admin
                else if(position == 3) {
                    Intent intent = new Intent(ActivitySandbox.this, ActivityAdmin.class);
                    startActivity(intent);
                }

                drawerLayout.closeDrawer(lvNavigationDrawer);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.actionbar_hamburger,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
            }
        };
        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected int getContentViewResourceId() {
        return R.layout.activity_sandbox;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

}