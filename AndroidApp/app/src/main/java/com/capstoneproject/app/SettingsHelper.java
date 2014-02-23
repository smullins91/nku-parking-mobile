package com.capstoneproject.app;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsHelper {

    public static final String SETTINGS_NAME = "parking-config";
    public static final String SETTINGS_SESSION = "session";
    private SharedPreferences mPrefs;

    public SettingsHelper(Context context) {
        mPrefs = context.getSharedPreferences(SETTINGS_NAME, context.MODE_PRIVATE);
    }

    public String getSessionKey() {
        return mPrefs.getString(SETTINGS_SESSION, null);
    }

    public void setSessionKey(String key) {

        SharedPreferences.Editor editor = mPrefs.edit();

        editor.putString(SETTINGS_SESSION, key);
        editor.commit();
    }

}
