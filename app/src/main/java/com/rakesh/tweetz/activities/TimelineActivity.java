package com.rakesh.tweetz.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.rakesh.tweetz.R;
import com.rakesh.tweetz.adapters.TweetsPagerAdapter;

public class TimelineActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Find viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.homeViewPager);

        //Set adapter on viewpager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        //find Sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.homeTabs);

        //attach viewpager to tabstrips
        tabStrip.setViewPager(vpPager);

    }

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

                Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_SHORT).show();
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
