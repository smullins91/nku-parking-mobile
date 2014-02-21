package com.capstoneproject.app;

import android.os.Bundle;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapFragment extends SupportMapFragment
{
    private GoogleMap map;
    static final LatLng CENTER_OF_MAP = new LatLng(39.031136, -84.464629);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        try
        {
            map = getMap();
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTER_OF_MAP, 16));

            loadBuildingImages();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadBuildingImages()
    {
        Coordinates coordinates = new Coordinates();
        coordinates.loadCoordinates(map);
        coordinates.insertMarkers(map);
    }
}