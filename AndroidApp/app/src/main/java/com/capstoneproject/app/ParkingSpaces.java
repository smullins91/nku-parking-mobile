package com.capstoneproject.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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
    private boolean[] mSpaces;

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

            mSpaces = new boolean[mRows * mColumns];
        }

        mLayout = (GridLayout) findViewById(R.id.parking_spaces);
        mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return ParkingSpaces.this.onTouchEvent(motionEvent);
            }
        });

        getSpaces();

    }

    private void getSpaces() {

        NetworkHelper.getSpaces(this, mId, new HttpResponse(ParkingSpaces.this) {
            @Override
            public void onFailure(Throwable e, JSONObject result) {

            }

            @Override
            public void onSuccess(JSONArray result) {

                for(int i = 0; i < mSpaces.length; i++)
                    mSpaces[i] = false;

                try {
                    for (int i = 0; i < result.length(); i++)
                        mSpaces[result.getJSONObject(i).getInt("SpaceId")] = true;

                    initializeParkingSpaces();
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeParkingSpaces()
    {
        GridLayout buttonContainer = (GridLayout) findViewById(R.id.parking_spaces);
        buttonContainer.setColumnCount(mColumns);
        buttonContainer.setRowCount(mRows);
        buttonContainer.removeAllViews();

        for (int row = 0; row < mRows; row++)
        {
            for (int column = 0; column < mColumns; column++)
            {
                ImageButton button = new ImageButton(getBaseContext());

                int key = mColumns * row + column;
                button.setId(key);
                button.setOnClickListener(this); //calls the onclick method of the interface
               // button.setTextColor(Color.WHITE);
                /*int random = (int) (Math.random() * 4);
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
                }*/

                if(mSpaces[key]) {
                    button.setImageResource(R.drawable.reserved);
                } else
                    button.setImageResource(R.drawable.available);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
               // params.topMargin = 10;

                if ((column != 0) && (column % 2 != 0))
                {
                    button.setBackground(getResources().getDrawable(R.drawable.space_button_left));
                    params.rightMargin = 80;
                } else
                    button.setBackground(getResources().getDrawable(R.drawable.space_button_right));

                button.setPadding(10,10,10,10);

                buttonContainer.addView(button, params);

            }
        }
    }

    @Override
    public void onClick(View view)
    {
        final ImageButton button = (ImageButton) view;

        if (mSpaces[button.getId()]) {
           showReservedMessage();
        } else {
            new AlertDialog.Builder(this).setTitle("Reserve Space")
                    .setMessage("Are you sure that you want to reserve space " + button.getId() + "?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            NetworkHelper.reserveSpace(ParkingSpaces.this, mId, button.getId(), 0, new HttpResponse(ParkingSpaces.this) {

                                @Override
                                public void onFailure(Throwable e, JSONObject result) {
                                    getSpaces();
                                    showReservedMessage();
                                }

                                @Override
                                public void onSuccess(JSONObject result) {
                                    Toast.makeText(getBaseContext(), "You have reserved a spot. Your reservation is valid for 1 hour. Thank you!", Toast.LENGTH_LONG).show();
                                    getSpaces();
                                }
                            });
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

    private void showReservedMessage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reserved").setMessage("Sorry, this space is reserved. Please choose another. Thank you!");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setIcon(R.drawable.ic_launcher).show();

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