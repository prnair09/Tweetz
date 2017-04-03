package com.rakesh.tweetz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.rakesh.tweetz.R;
import com.rakesh.tweetz.adapters.TweetsPagerAdapter;
import com.rakesh.tweetz.models.Tweet;

/**
 * Created by rparuthi on 3/30/2017.
 */

public class HomeFragment extends Fragment {

    private ViewPager vpPager;

    TweetsPagerAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //Find viewpager
        vpPager = (ViewPager) v.findViewById(R.id.homeViewPager);

        pagerAdapter = new TweetsPagerAdapter(getActivity().getSupportFragmentManager());

        //Set adapter on viewpager
        vpPager.setAdapter(pagerAdapter);

        //find Sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) v.findViewById(R.id.homeTabs);

        //attach viewpager to tabstrips
        tabStrip.setViewPager(vpPager);


        return v;
    }

    public void addTweet(Tweet tweet){
        HomeTimeLineFragment fragment = (HomeTimeLineFragment) pagerAdapter.getRegisteredFragment(0);
        fragment.addNewTweet(tweet);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.

        inflater.inflate(R.menu.menu_items, menu);

        //Get searchmenu item
        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //set listener for SearchView
        setListeners(searchView);

    }

    public void setListeners(SearchView searchView){

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Toast.makeText(getActivity(),newText,Toast.LENGTH_SHORT).show();
                /*
                //mQueryString = newText;
                if(newText.length() == 0 ){
                    //articlesAdapter.clearArticles();
                    //articlesAdapter.notifyDataSetChanged();
                    scrollListener.resetState();
                    return true;
                }
                //mHandler.removeCallbacksAndMessages(null);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mQueryString.length() > 1) {
                            //GetArticles
                            getArticles(mQueryString,0);
                        }
                    }
                }, 300);*/

                return true;
            }

        });
    }
}
