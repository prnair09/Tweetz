package com.rakesh.tweetz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.rakesh.tweetz.TweetzApplication;
import com.rakesh.tweetz.TweetzClient;
import com.rakesh.tweetz.models.Tweet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.R.attr.offset;



public class UserTimelineFragment extends TweetsListFragment {


    private long max_id = 0;
    private TweetzClient tweetzClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetzClient = TweetzApplication.getRestClient();
        populateTimeLine(0);
    }

    public static UserTimelineFragment newInstance(String screenName){
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("ScreenName",screenName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }


    @Override
    public void populateTimeLine(int offSet) {
        String screenName = getArguments().getString("ScreenName");
            tweetzClient.getUserTimeline(new JsonHttpResponseHandler() {
                //Success
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    Log.d("DEBUG", response.toString());

                    ArrayList<Tweet> newTweets = Tweet.fromJsonArray(response);

                    if (newTweets.size() > 0) {
                        max_id = (long) newTweets.get(newTweets.size() - 1).getUid() - 1;
                        addAll(newTweets);
                    }
                }
                //Failure
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG", errorResponse.toString());
                    Toast.makeText(getActivity(), "API limit reached", Toast.LENGTH_SHORT).show();
                }
            },screenName, max_id);

    }
}
