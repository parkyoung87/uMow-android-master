package com.umow.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.umow.android.util.UtilToast;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuejianyu on 11/25/14.
 */
public class ActivitySearch extends Activity_Base {

    /** Local variables **/
    GoogleMap googleMap;
    List<String> drawer_items;
    DrawerLayout drawerLayout;
    ListView lvNavigationDrawer;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        // drawer
        drawer_items = new ArrayList<String>();
        drawer_items.add("Search Activity");
        drawer_items.add("Landscaper Activity");
        drawer_items.add("Payment Activity");
        drawer_items.add("Admin Activity");

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_search_drawerLayout);
        lvNavigationDrawer = (ListView) findViewById(R.id.activity_search_navigationDrawer);

        lvNavigationDrawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawer_items));
        lvNavigationDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // Search Activity
                if (position == 0) {
                    Intent intent = new Intent(ActivitySearch.this, ActivitySearch.class);
                    startActivity(intent);
                }
                // Landscaper
                else if (position == 1) {
                    Intent intent = new Intent(ActivitySearch.this, ActivityLandscaper.class);
                    startActivity(intent);
                }
                // Payment
                else if (position == 2) {
                    Intent intent = new Intent(ActivitySearch.this, ActivityPayment.class);
                    startActivity(intent);
                }
                // Admin
                else if (position == 3) {
                    Intent intent = new Intent(ActivitySearch.this, ActivityAdmin.class);
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

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
            }
        };
        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);







        createMapView();
        addMarker();

        SearchView searchView = (SearchView) findViewById(R.id.activity_search_searchview);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {

                // checks if zip is valid
                if(queryText.length() < 5 || queryText.length() > 5) {
                    UtilToast.showToast(ActivitySearch.this, "Please enter a valid 5-digit zipcode");
                    return false;
                }

                // test toast display
                // UtilToast.showToast(ActivitySearch.this, queryText);

                final ProgressDialog progressDialog = new ProgressDialog(ActivitySearch.this);
                progressDialog.setMessage("Retrieving Users...");
                progressDialog.show();

                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> users, ParseException e) {
                        progressDialog.dismiss();

                        if (e == null) {
                            // The query was successful.

                            final ListView listView = (ListView) findViewById(R.id.activity_search_listview_landscapers);

                            String[] values = new String[users.size()];

                            int i=0;
                            for(ParseUser user : users) {
                                values[i] = user.getUsername();
                                i++;
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivitySearch.this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // ListView Clicked item index
                                    int itemPosition     = position;

                                    // ListView Clicked item value
                                    String  itemValue    = (String) listView.getItemAtPosition(position);

                                    // Show Alert
                                    UtilToast.showToast(ActivitySearch.this, "Position :" + itemPosition + "  ListItem : " + itemValue);
                                }
                            });

                        } else {
                            // Something went wrong.
                        }
                    }
                });


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }





    @Override
    protected int getContentViewResourceId() {
        return R.layout.activity_search;
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






    /**
     * Initialises the mapview
     */
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    /**
     * Adds a marker to the map
     */
    private void addMarker(){

        /** Make sure that the map has been initialised **/
        if(null != googleMap){
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(0, 0))
                            .title("Marker")
                            .draggable(true)
            );
        }
    }




}
