package com.capstoneproject.app;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;


public class NetworkHelper {

    public static final String API_URL = "https://ec2-54-200-98-161.us-west-2.compute.amazonaws.com/";
    public static final String API_LOGIN = "login";
    public static final String API_REGISTER = "register";
    public static final String API_VERIFY = "verify";
    public static final String API_LOTS = "lots";
    public static final String HEADER_AUTH = "Authorization";
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


    public static void getLots(Context context, AsyncHttpResponseHandler callback) {
        get(API_LOTS, context, null, callback);
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

        SettingsHelper settings = new SettingsHelper(context.getApplicationContext());
        String key = settings.getSessionKey();

        AsyncHttpClient client = new AsyncHttpClient();
        client.setUserAgent(USER_AGENT);
        if(key != null)
            client.addHeader(HEADER_AUTH, key);
        client.setTimeout(10 * 1000);

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            CustomSSL sf = new CustomSSL(trustStore);
            sf.setHostnameVerifier(CustomSSL.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sf);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return client;
    }


    private static class CustomSSL extends SSLSocketFactory {

        SSLContext sslContext = SSLContext.getInstance("TLS");

        public CustomSSL(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {

            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

}
