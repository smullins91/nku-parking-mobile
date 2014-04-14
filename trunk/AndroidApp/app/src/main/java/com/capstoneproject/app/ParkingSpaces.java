package com.capstoneproject.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class ParkingSpaces extends Activity implements View.OnClickListener
{
    private static final int NUMBER_OF_ROWS = 11;
    private static final int NUMBER_OF_COLUMNS = 5;
    private static final int INVALID_POINTER_ID = -1;
    private int mId = 0;
    private int mRows = 0;
    private int mColumns = 0;
    private float mLastTouchX, mPosX;
    private float mLastTouchY, mPosY;
    private ScaleGestureDetector mScaleDetector;
    private int mActivePointerId = INVALID_POINTER_ID;
    private GridLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_spaces_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //hide the keyboard

        Bundle extras = getIntent().getExtras();

        if(extras != null) {

            mRows = extras.getInt("rows");
            mColumns = extras.getInt("columns");
            mId = extras.getInt("id");
            setTitle(extras.getString("title"));

        }

        mLayout = (GridLayout) findViewById(R.id.parking_spaces);
        mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return ParkingSpaces.this.onTouchEvent(motionEvent);
            }
        });

        initializeParkingSpaces();
    }

    private void initializeParkingSpaces()
    {
        GridLayout buttonContainer = (GridLayout) findViewById(R.id.parking_spaces);
        buttonContainer.setColumnCount(mColumns);
        buttonContainer.setRowCount(mRows);

        for (int row = 0; row < mRows; row++)
        {
            for (int column = 0; column < mColumns; column++)
            {
                ImageButton button = new ImageButton(getBaseContext());


                //int key = (Integer.parseInt("" + rows + columns));
                int key = mColumns * row + column;
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

                if ((column != 0) && (column % 2 != 0))
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
        final ImageButton button = (ImageButton) view;
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
                    .setMessage("Are you sure that you want to reserve spot " + buttonText + "?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            NetworkHelper.reserveSpace(ParkingSpaces.this, mId, button.getId(), new HttpResponse(ParkingSpaces.this) {

                                @Override
                                public void onFailure(Throwable e, JSONObject result) {

                                }

                                @Override
                                public void onSuccess(JSONObject result) {


                                }
                            });
                            Toast.makeText(getBaseContext(), "You have reserved a spot. Your reservation is valid for 1 hour. Thank you!", Toast.LENGTH_LONG).show();
                        }

            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //No selected. So, do nothing, go back to the activity
                }

            }).setIcon(R.drawable.ic_launcher).show();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);

        final int action = MotionEventCompat.getActionMasked(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);

                // Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                // Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                // Find the index of the active pointer and fetch its position
                final int pointerIndex =
                        MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);

                // Calculate the distance moved
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                mPosX += dx;
                mPosY += dy;

                mLayout.setX(mPosX);
                mLayout.setY(mPosY);
                mLayout.invalidate();

                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex);
                    mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }
                break;
            }
        }

        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        private float mScaleFactor = 1.f;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            mLayout.invalidate();

            return true;
        }
    }

}