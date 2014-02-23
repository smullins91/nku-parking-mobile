package com.capstoneproject.app;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class NetworkHelper {

    public static final String API_URL = "http://ec2-54-200-98-161.us-west-2.compute.amazonaws.com:8080/";
    public static final String API_LOGIN = "login";
    public static final String API_REGISTER = "register";
    public static final String API_VERIFY = "verify";
    public static final String HEADER_AUTH = "Authentication";
    public static final String TYPE_JSON = "application/json";
    public static final String USER_AGENT = "ParkingApp v1.0";

    public static void login(Context context, String username, String password, AsyncHttpResponseHandler callback) {

        JSONObject object = new JSONObject();

        try {
            object.put("username", username);
            object.put("password", password);
            post(API_LOGIN, context, object, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static void get(String api, Context context, RequestParams params, AsyncHttpResponseHandler callback) {

        AsyncHttpClient client = createClient(context);
        client.get(API_URL + api, params, callback);
    }

    private static void post(String api, Context context, JSONObject json, AsyncHttpResponseHandler callback) {

        AsyncHttpClient client = createClient(context);

        try {
            StringEntity entity = new StringEntity(json.toString());
            client.post(context, API_URL + api, entity, TYPE_JSON, callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static AsyncHttpClient createClient(Context context) {

        SettingsHelper settings = new SettingsHelper(context);
        String key = settings.getSessionKey();

        AsyncHttpClient client = new AsyncHttpClient();
        client.setUserAgent(USER_AGENT);
        client.addHeader(HEADER_AUTH, key);
        client.setTimeout(10 * 1000);

        return client;
    }

}
