package com.capstoneproject.app;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener
{
    public static MainActivity mThis; //Needed to access the activity in a broadcast receiver
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabNames = {"Parking Lots","Map"};
    private MenuItem mSearchItem;
    private ParkingNavigationFragment mLotFragment;
    private MapFragment mMapFragment;
    private PendingIntent mAlarmIntent;
    private boolean mNotificationVisible;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SettingsHelper settings = new SettingsHelper(this);
        String key = settings.getSessionKey();

        if(key == null || key.length() == 0) {
            displayLogin();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadActionBarTabs();

       // SpaceNotification notification = new SpaceNotification(this, "X", System.currentTimeMillis() + 5000);
        //notification.show();
    }

    private void loadActionBarTabs()
    {
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name : tabNames)
        {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        /*on swiping the viewpager make respective tab selected */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                actionBar.setSelectedNavigationItem(position); // on changing the page make respected tab selected
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });
    }

    @Override
    public void onResume() {
        mThis = this;
        Intent intent = new Intent(this, StatusReceiver.class);
        mAlarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager)(getSystemService(Context.ALARM_SERVICE));
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, mAlarmIntent);

        super.onResume();
    }

    @Override
    public void onDestroy() {
        if(mProgress != null && mProgress.isShowing()) {
            mProgress.cancel();
            mProgress = null;
        }

        super.onDestroy();
    }

    @Override
    public void onPause() {

        mThis = null;

        if(mAlarmIntent != null){
            AlarmManager alarm = (AlarmManager)(getSystemService(Context.ALARM_SERVICE));
            alarm.cancel(mAlarmIntent);
            mAlarmIntent = null;
        }

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        mSearchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) mSearchItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(mLotFragment);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if(!queryTextFocused) {
                    mSearchItem.collapseActionView();
                   // searchView.setQuery("", false);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.menu_refresh: refresh(); break;
            case R.id.menu_logout: logout(); break;
        }


        return true;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft)
    {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
    {

        int pos = tab.getPosition();

        if(mSearchItem != null) {

            mSearchItem.collapseActionView(); //Close the search box

            if (pos == 0)
                mSearchItem.setVisible(true); //Only show icon on the first tab
            else
                mSearchItem.setVisible(false);
        }

        viewPager.setCurrentItem(pos);  // on tab selected show respected fragment view
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft)
    {

    }

    public void setLotFragment(ParkingNavigationFragment fragment) {
        mLotFragment = fragment;
    }

    public void setMapFragment(MapFragment fragment) {
        mMapFragment = fragment;
    }

    /** A placeholder fragment containing a simple view.*/
    public static class PlaceholderFragment
    {

    }

    public void getUserStatus() {

        NetworkHelper.getStatus(this, new HttpResponse(this) {

            @Override
            public void onFailure(Throwable e, JSONObject result) {
                super.onFailure(e, result);

                if(!result.isNull("error"))
                    logout();
            }

            @Override
            public void onSuccess(JSONObject result) {

                try {

                    SettingsHelper settings = new SettingsHelper(getApplicationContext());
                    int type = result.getInt("RoleId");

                    switch (type) {
                        case 2:
                        case 3:
                            type = 1;
                            break; //Faculty/Staff
                        case 4:
                            type = 2;
                            break; //Student
                        case 5:
                            type = 3;
                            break; //Visitor
                        default:
                            type = 3;
                    }

                    settings.setType(type);

                    if(!result.isNull("TimeOut") && !result.isNull("SpaceId") && !result.isNull("LotNumber")) {

                        long time = result.getLong("TimeOut") * 1000;
                        int space = result.getInt("SpaceId");
                        String lot = "Parking Lot " + result.getString("LotNumber");


                        long previousTime = settings.getReservationTime();
                        int previousSpace = settings.getReservationSpace();
                        String previousLot = settings.getReservationLot();

                        settings.setReservationLot(lot);
                        settings.setReservationSpace(space);
                        settings.setReservationTime(time);


                        if(!mNotificationVisible || time != previousTime || space != previousSpace || !lot.equals(previousLot)) {
                            SpaceNotification notification = new SpaceNotification(getApplicationContext(), lot, time);
                            notification.show();
                            mNotificationVisible = true;
                        }


                    } else {
                        settings.setReservationLot(null);
                        settings.setReservationSpace(-1);
                        settings.setReservationTime(0);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

    }

    public void displayLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    public void showMap(String lot) {

        viewPager.setCurrentItem(1);
        ArrayList<Marker> markers = mMapFragment.getMarkers();

        for(int i = 0; i < markers.size(); i++) {

            Marker marker = markers.get(i);

            if(marker.getTitle().toLowerCase().contains(lot.toLowerCase())) {
                CameraUpdate cam = CameraUpdateFactory.newLatLng(marker.getPosition());
                mMapFragment.getMyMap().animateCamera(cam);
                marker.showInfoWindow();
                break;
            }

        }

    }

    public void refresh() {

        Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
        mLotFragment.updateLotInfo();
        mMapFragment.updateLotInfo();

    }


    public void logout() {

        mProgress = ProgressDialog.show(this, "",
                "Signing out...", true);

        NetworkHelper.logout(this, new HttpResponse(getApplicationContext()) {

            @Override
            public void onFailure(Throwable e, JSONObject result) {
                if(mProgress != null && mProgress.isShowing()) {
                    mProgress.dismiss();
                }
//                super.onFailure(e, result);
            }

            @Override
            public void onSuccess(JSONObject result) {

                if(mProgress != null && mProgress.isShowing()) {
                    mProgress.dismiss();
                }

                SettingsHelper settings = new SettingsHelper(MainActivity.this);
                settings.setSessionKey("");
                settings.setReservationLot(null);
                settings.setReservationSpace(-1);
                settings.setReservationTime(0);

                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(0);

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

            }

        });
    }
}