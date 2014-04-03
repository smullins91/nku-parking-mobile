package com.capstoneproject.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MapFragment extends SupportMapFragment
{
    private GoogleMap map;
    static final LatLng CENTER_OF_MAP = new LatLng(39.031136, -84.464629);
    private ArrayList<PolygonOptions> polys = new ArrayList<PolygonOptions>();

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
            updateLotInfo();
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

    public GoogleMap getMyMap()
    {
        GoogleMap myMap = this.map;
        myMap = getMap();
        myMap.setMyLocationEnabled(true);
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTER_OF_MAP, 16));

        return myMap;
    }

    private void updateLotInfo() {

        NetworkHelper.getLots(getActivity(), new HttpResponse(getActivity()) {

            @Override
            public void onSuccess(JSONArray response) {

                //JSONArray array = new JSONArray();

                try {
                   // array = response.getJSONArray("lots");

                    for(int i = 0; i < response.length(); i++) {

                        JSONObject lot = (JSONObject) response.get(i);
                        JSONArray coordinates = lot.getJSONArray("points");
                        PolygonOptions poly = new PolygonOptions();

                        LatLngBounds.Builder bounds = new LatLngBounds.Builder();

                        for(int j = 0; j < coordinates.length(); j++) {
                            JSONObject obj = (JSONObject)coordinates.get(j);
                            Log.d("Lat", Double.toString(obj.getDouble("lat")));
                            Log.d("Lng", Double.toString(obj.getDouble("lng")));
                            LatLng point = new LatLng(obj.getDouble("lat"), obj.getDouble("lng"));
                            poly = poly.add(point);
                            bounds.include(point);
                        }

                        poly = poly.strokeColor(Color.BLACK).fillColor(0x7Fffa500);
                        LatLng center = bounds.build().getCenter();

                        map.addPolygon(poly);
                        map.addMarker(new MarkerOptions()
                                .position(center)
                                .title("Parking Lot " + lot.getString("lotNumber"))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });

    }

}