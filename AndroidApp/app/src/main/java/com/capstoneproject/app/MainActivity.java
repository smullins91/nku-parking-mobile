package com.capstoneproject.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener
{
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabNames = {"Parking Lots","Map"};
    private MenuItem mSearchItem;
    private ParkingNavigationFragment mLotFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SettingsHelper settings = new SettingsHelper(this);
        String key = settings.getSessionKey();

        if(key == null || key.length() == 0)
        {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadActionBarTabs();
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
            case R.id.menu_settings: break;
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

    /** A placeholder fragment containing a simple view.*/
    public static class PlaceholderFragment
    {

    }


    public void logout() {

        final ProgressDialog progress = ProgressDialog.show(this, "",
                "Signing out...", true);
        progress.show();

        NetworkHelper.logout(this, new HttpResponse(this) {

            @Override
            public void onFailure(Throwable e, JSONObject result) {
                progress.dismiss();
                super.onFailure(e, result);
            }

            @Override
            public void onSuccess(JSONObject result) {

                progress.dismiss();

                SettingsHelper settings = new SettingsHelper(MainActivity.this);
                settings.setSessionKey("");

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

            }

        });
    }
}