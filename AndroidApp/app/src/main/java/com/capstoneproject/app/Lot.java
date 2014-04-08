package com.capstoneproject.app;

import org.json.JSONException;
import org.json.JSONObject;

public class Lot {

    private int mId;
    private String mNumber;
    private boolean mActive;
    private int mType;
    private int mRows;
    private int mColumns;
    private int mAvailable;

    public Lot(JSONObject data) throws JSONException {

        mId = data.getInt("id");
        mNumber = data.getString("lotNumber");
        mActive = data.getInt("active") == 1;
        mType = data.getInt("type");
        mRows = data.getInt("rows");
        mColumns = data.getInt("columns");
        mAvailable = data.getInt("available");

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
}
