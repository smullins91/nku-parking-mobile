package com.capstoneproject.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter
{
    public TabsPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int index)
    {
        switch (index)
        {
            case 0:
                return new ParkingNavigationFragment(); // Fragment activity for the map
            case 1:

                return new MapFragment(); // Fragment activity for the navigation
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return 2; // get item count - equal to number of tabs
    }
}