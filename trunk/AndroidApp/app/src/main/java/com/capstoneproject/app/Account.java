package com.capstoneproject.app;
import android.app.Activity;
import android.os.Bundle;

public class Account extends Activity
{
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
    }


}
