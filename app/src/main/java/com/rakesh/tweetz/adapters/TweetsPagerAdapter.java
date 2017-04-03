package com.rakesh.tweetz.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.rakesh.tweetz.fragments.HomeTimeLineFragment;
import com.rakesh.tweetz.fragments.MentionsTimelineFragment;
import com.rakesh.tweetz.helper.SmartFragmentStatePagerAdapter;

/**
 * Created by rparuthi on 3/30/2017.
 */

//Return the order of fragments in view pager
public class TweetsPagerAdapter  extends SmartFragmentStatePagerAdapter{
    private String tabTitle[] = {"Home", "Mentions"};

    public TweetsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    //Get the fragment based on the position
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                //fragment = new HomeTimeLineFragment();
                fragment = HomeTimeLineFragment.newInstance(0, "TimeLine");
                break;
            case 1:
                fragment = MentionsTimelineFragment.newInstance(1,"Mentions");
                break;
            default:
                fragment = HomeTimeLineFragment.newInstance(0, "TimeLine");
                break;
        }
        return fragment;

    }

    //Gets title
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }

    //How many tabs to swipe
    @Override
    public int getCount() {
        return tabTitle.length;
    }
}
