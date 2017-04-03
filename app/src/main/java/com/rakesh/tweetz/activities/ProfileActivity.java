package com.rakesh.tweetz.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.rakesh.tweetz.R;
import com.rakesh.tweetz.TweetzApplication;
import com.rakesh.tweetz.TweetzClient;
import com.rakesh.tweetz.fragments.UserTimelineFragment;
import com.rakesh.tweetz.models.User;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private ImageView ivProfileImage;
    private int mMaxScrollSize;

    private Fragment userTimelineFragment;

    private TweetzClient client;

    private TextView tvScreenName;
    private TextView tvUserName;

    private TextView tvFollowers;
    private TextView tvFollowing;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);

        tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        String screenName = !getIntent().getStringExtra("ScreenName").isEmpty() ?
                            getIntent().getStringExtra("ScreenName"):getLoggedInUserScreenName();

        getUserInfo(screenName);

        if(savedInstanceState == null) {

            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.flProfile, fragmentUserTimeline);
            ft.commit();
        }
    }

    private String getLoggedInUserScreenName(){
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        return mSettings.getString("ScreenName","rakeshnairp");
    }

    private void getUserInfo(String screenName) {
        client = TweetzApplication.getRestClient();

        client.getUserInfo(screenName, new JsonHttpResponseHandler() {

            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJson(response);

                populateProfileHeader();
            }

            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });

    }

    private void populateProfileHeader() {

        tvScreenName.setText(user.getScreenName());
        tvUserName.setText(user.getName());

        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFollowingCount() + " Following");


        Picasso.with(this).
                load(user.getProfileImageUrl())
                .transform(new RoundedCornersTransformation(4, 4))
                .into(ivProfileImage);
    }

    private void displayUserTimelineFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        String screenName = getIntent().getStringExtra("ScreenName");
        Fragment fragment = UserTimelineFragment.newInstance(screenName);
        ft.replace(R.id.profile_fragment, fragment, "UserTimeline Fragment");
        ft.commit();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;

            ivProfileImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            ivProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }
}
