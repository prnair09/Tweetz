package com.rakesh.tweetz.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rakesh.tweetz.R;
import com.rakesh.tweetz.fragments.ComposeTweetFragment;
import com.rakesh.tweetz.fragments.HomeFragment;
import com.rakesh.tweetz.models.Tweet;

public class MainActivity extends BaseActivity
        implements ComposeTweetFragment.ComposeTweetListener {


    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    private Fragment homeFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle = setupDrawerToggle();

        mDrawer.addDrawerListener(mDrawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        if(savedInstanceState == null){
            try {
                homeFragment = HomeFragment.class.newInstance();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        //displayTimelineActivity();

        displayHomeFragment();

        /*
        if (this instanceof MainActivity.TweetSuccessListener) {
            onTweetSuccessListener = (MainActivity.TweetSuccessListener) this;
        } else {
            throw new ClassCastException(this
                    + " must implement MainActivity.TweetSuccessListener");
        }*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        setTitle("Home");
    }

    private void displayTimelineActivity(){
        Intent intent = new Intent(this,TimelineActivity.class);
        startActivity(intent);
    }


    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void selectDrawerItemOld(MenuItem menuItem){
        Fragment fragment = null;
        Class fragmentClass = HomeFragment.class;

        switch (menuItem.getItemId()) {

            case R.id.profile_fragment:
                //fragmentClass = ProfileFragment.class;
                break;
        }

        try{
            fragment = (Fragment) fragmentClass.newInstance();

        }catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.flContent,fragment).commit();

        menuItem.setChecked(true);

        setTitle(menuItem.getTitle());

        mDrawer.closeDrawers();

    }

    //Display selected item
    private void selectDrawerItem(MenuItem menuItem){

        switch (menuItem.getItemId()) {

            case R.id.profile_fragment:
                //displayProfileFragment();
                Intent intent = new Intent(this,ProfileActivity.class);
                intent.putExtra("ScreenName","");
                startActivity(intent);
                break;
            case R.id.home_fragment:
                displayHomeFragment();
                break;
        }

        menuItem.setChecked(true);

        setTitle(menuItem.getTitle());

        mDrawer.closeDrawers();

    }

    //Displays HomeFragment with out replacing other fragments
    private void displayHomeFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(homeFragment.isAdded()){
            ft.show(homeFragment);
        }else{
            ft.add(R.id.flContent,homeFragment,"Home Fragment");
        }
        ft.commit();

        setTitle("Home");

    }

    /*
    //Displays profile fragment with out replacing other fragments
    private void displayProfileFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(profileFragment.isAdded()){
            ft.show(profileFragment);
        }else{
            ft.add(R.id.flContent,profileFragment,"Profile Fragment");
        }

        if(homeFragment.isAdded()){ft.hide(homeFragment);}
       // if(userTimelineFragment.isAdded()){ft.hide(userTimelineFragment);}

        ft.commit();

        setTitle("Profile");

    }

    //Displays profile fragment with out replacing other fragments
    private void displayUserTimelineFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(userTimelineFragment.isAdded()){
            ft.show(userTimelineFragment);
        }else{
            ft.add(R.id.flContent,userTimelineFragment,"User Timeline Fragment");
        }

        if(homeFragment.isAdded()){ft.hide(homeFragment);}
        if(profileFragment.isAdded()){ft.hide(profileFragment);}

        ft.commit();

        setTitle("Profile");

    }*/

    @Override
    protected void onPostCreate( Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.drawer_open,R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onTweetSuccess(Tweet tweet) {
       // tweets.add(0, tweet);
       // TweetsListFragment.addNewTweet(tweet);
        //aTweets.notifyDataSetChanged();
        //lvTweets.smoothScrollToPosition(0);
        //lvTweets.setSelection(0);

        //FragmentManager fm = getSupportFragmentManager();
        //adapterViewPager.getRegisteredFragment(vpPager.getCurrentItem());
        //fm.findFragmentById(R.id.lvTweets);

        //Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();

        HomeFragment fragment = (HomeFragment)
                                    getSupportFragmentManager().findFragmentByTag("Home Fragment");

        if(fragment != null){
            fragment.addTweet(tweet);
        }

    }

}
