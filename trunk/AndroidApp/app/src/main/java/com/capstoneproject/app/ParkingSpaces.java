package com.capstoneproject.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ParkingSpaces extends Activity implements View.OnClickListener
{
    private static final int NUMBER_OF_ROWS = 11;
    private static final int NUMBER_OF_COLUMNS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_spaces_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //hide the keyboard

        initializeParkingSpaces();
    }

    private void initializeParkingSpaces()
    {
        GridLayout buttonContainer = (GridLayout) findViewById(R.id.parking_spaces);
        buttonContainer.setColumnCount(NUMBER_OF_COLUMNS);

        for (int rows = 0; rows < NUMBER_OF_ROWS; rows++)
        {
            for (int columns = 0; columns < NUMBER_OF_COLUMNS; columns++)
            {
                ImageButton button = new ImageButton(getBaseContext());


                int key = (Integer.parseInt("" + rows + columns));
                button.setId(key);
                //button.setText(rows + "," + columns);
                button.setOnClickListener(this); //calls the onclick method of the interface
               // button.setTextColor(Color.WHITE);
                int random = (int) (Math.random() * 4);
                if (random == 0)
                {
                    button.setImageResource(R.drawable.available);
                }
                else if (random == 1)
                {
                    button.setImageResource(R.drawable.closed);
                }
                else if (random == 2)
                {
                    button.setImageResource(R.drawable.reserved);
                }
                else
                {
                    button.setImageResource(R.drawable.unavailable);
                }

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();

                if ((columns != 0) && (columns % 2 != 0))
                {
                    params.rightMargin = 80;
                }

                buttonContainer.addView(button, params);

            }
        }
    }

    @Override
    public void onClick(View view)
    {
        ImageButton button = (ImageButton) view;
        String buttonText = "" + button.getId();

        if (buttonText.equals("0,0") || buttonText.equals("1,1") || buttonText.equals("2,2") || buttonText.equals("3,3") || buttonText.equals("4,4"))
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
                            Toast.makeText(getBaseContext(), "You have reserved a spot. Your reservation is valid for 1 hour. Thank you!", Toast.LENGTH_LONG).show();
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