package com.capstoneproject.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpResponse extends JsonHttpResponseHandler {

    Context mContext;

    public HttpResponse(Context context) {
        mContext = context;
    }

    @Override
    public void onFailure(Throwable e, JSONObject errorResponse) {


        try {

            if(!errorResponse.isNull("error")) {

                String error = errorResponse.getString("error");

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Error").setMessage(error);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void onSuccess(JSONObject response) {


    }


}
