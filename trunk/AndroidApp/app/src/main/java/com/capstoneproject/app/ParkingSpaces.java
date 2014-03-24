package com.capstoneproject.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class ParkingSpaces extends Activity implements View.OnClickListener
{
    private static final int NUMBER_OF_ROWS = 5;
    private static final int NUMBER_OF_COLUMNS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_spaces_main);

        InputMethodManager im = (InputMethodManager) this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        initializeParkingSpaces();
    }

    private void initializeParkingSpaces()
    {
        GridLayout buttonContainer = (GridLayout) findViewById(R.id.parking_spaces);
        buttonContainer.setRowCount(5);

        for (int rows = 0; rows < NUMBER_OF_ROWS; rows++)
        {
            for (int columns = 0; columns < NUMBER_OF_COLUMNS; columns++)
            {
                Button button = new Button(getBaseContext());
                int key = (Integer.parseInt("" + rows + columns));
                button.setId(key);
                button.setText("" + rows + "," + columns);
                button.setOnClickListener(this); //calls the onclick method of the interface
                buttonContainer.addView(button);
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        Button button = (Button) view;
        String buttonText = button.getText().toString();



        if (buttonText.equals("1,0") || buttonText.equals("1,1") || buttonText.equals("1,2") || buttonText.equals("1,3") || buttonText.equals("1,4"))
        {
            String getValueFromDatabase = "Reserved";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("RESERVED SPOT").setMessage("Sorry, this spot is : " + getValueFromDatabase + ". Please consider using another spot. Thank you!");
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setIcon(R.drawable.ic_launcher).show();
        }
        else
        {
            new AlertDialog.Builder(this).setTitle("Reserve a spot")
                    .setMessage("Are you sure that you want to reserve spot " + buttonText + " ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(getBaseContext(), "You have reserved a spot. Your reservation is valid for 1 hour. Thank you!", Toast.LENGTH_SHORT).show();
                        }

                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            //No selected. So, do nothing, go back to the activity
                        }

            }).setIcon(R.drawable.ic_launcher).show();
        }
    }
}