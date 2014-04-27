package com.capstoneproject.app;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lot {

    private int mId;
    private String mNumber;
    private boolean mActive;
    private int mType;
    private int mRows;
    private int mColumns;
    private int mAvailable;
    private ArrayList<LatLng> mPoints = new ArrayList<LatLng>();

    public Lot(JSONObject data) throws JSONException {

        mId = data.getInt("id");
        mNumber = data.getString("lotNumber");
        mActive = data.getInt("active") == 1;
        mType = data.getInt("type");
        mRows = data.getInt("rows");
        mColumns = data.getInt("columns");
        mAvailable = data.getInt("available");

        JSONArray coordinates = data.getJSONArray("points");

        for(int j = 0; j < coordinates.length(); j++) {
            JSONObject obj = (JSONObject)coordinates.get(j);
            LatLng point = new LatLng(obj.getDouble("lat"), obj.getDouble("lng"));
            mPoints.add(point);
        }

    }


    public int getId() {
        return mId;
    }

    public String getNumber() {
        return mNumber;
    }

    public boolean isActive() {
        return mActive;
    }

    public int getType() {
        return mType;
    }

    public int getRows() {
        return mRows;
    }

    public int getColumns() {
        return mColumns;
    }

    public int getAvailable() {
        return mAvailable;
    }

    public ArrayList<LatLng> getPoints() {
        return mPoints;
    }

}
