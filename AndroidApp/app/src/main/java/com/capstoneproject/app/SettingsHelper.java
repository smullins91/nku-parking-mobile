package com.capstoneproject.app;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsHelper {

    public static final String SETTINGS_NAME = "parking-config";
    public static final String SETTINGS_SESSION = "session";
    public static final String SETTINGS_TYPE = "type";
    public static final String SETTINGS_RESERVATION_LOT = "reservation_lot";
    public static final String SETTINGS_RESERVATION_SPACE = "reservation_space";
    public static final String SETTINGS_RESERVATION_TIME = "reservation_time";
    private SharedPreferences mPrefs;

    public SettingsHelper(Context context) {
        mPrefs = context.getSharedPreferences(SETTINGS_NAME, context.MODE_PRIVATE);
    }

    public String getSessionKey() {
        return mPrefs.getString(SETTINGS_SESSION, null);
    }

    public String getReservationLot() {
        return mPrefs.getString(SETTINGS_RESERVATION_LOT, null);
    }

    public int getReservationSpace() {
        return mPrefs.getInt(SETTINGS_RESERVATION_SPACE, -1);
    }


    public long getReservationTime() {
        return mPrefs.getLong(SETTINGS_RESERVATION_TIME, 0);
    }

    public int getType() {
        return mPrefs.getInt(SETTINGS_TYPE, 3);
    }

    public void setSessionKey(String key) {

        SharedPreferences.Editor editor = mPrefs.edit();

        editor.putString(SETTINGS_SESSION, key);
        editor.commit();
    }

    public void setReservationLot(String lot) {

        SharedPreferences.Editor editor = mPrefs.edit();

        editor.putString(SETTINGS_RESERVATION_LOT, lot);
        editor.commit();
    }

    public void setReservationSpace(int space) {

        SharedPreferences.Editor editor = mPrefs.edit();

        editor.putInt(SETTINGS_RESERVATION_SPACE, space);
        editor.commit();
    }

    public void setReservationTime(long time) {

        SharedPreferences.Editor editor = mPrefs.edit();

        editor.putLong(SETTINGS_RESERVATION_TIME, time);
        editor.commit();
    }

    public void setType(int type) {

        SharedPreferences.Editor editor = mPrefs.edit();

        editor.putInt(SETTINGS_TYPE, type);
        editor.commit();
    }

}
