package com.rakesh.tweetz.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rakesh.tweetz.fragments.UserTimelineFragment;

/**
 * Created by rparuthi on 4/1/2017.
 */

public class ProfilePagerAdapter extends FragmentPagerAdapter {


    private String tabTiles[] = {"Tweets"};

    public ProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return tabTiles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTiles[position];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        switch (position){
            case 0:
                fragment = new UserTimelineFragment();
                break;
            default:
                fragment = new UserTimelineFragment();
                break;
        }
        return fragment;
    }
}
