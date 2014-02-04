package com.capstoneproject.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.*;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends ActionBarActivity
{
    private GoogleMap map;
    static final LatLng GRIFFIN = new LatLng(39.030985, -84.466555);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mapFrag.getMap();

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(GRIFFIN, 16));

        Marker griffinMarker = map.addMarker(new MarkerOptions().position(GRIFFIN).title("Griffin Hall").snippet("Building code GH").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        //loadBuildingImages();

        //Drawable marker=getResources().getDrawable(R.drawable.marker);
       // marker.setBounds(0,0, marker.getIntrinsicWidth(),marker.getIntrinsicHeight());


        /*if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }*/
    }

    private void loadBuildingImages()
    {
        //Marker griffinHall = map.addMarker(new MarkerOptions().position(GRIFFIN).title("Griffin Hall").snippet("Building Code GH"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


     /** A placeholder fragment containing a simple view.*/
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
