package com.rakesh.tweetz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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

/**
 * Created by rparuthi on 3/30/2017.
 */

public class MentionsTimelineFragment extends TweetsListFragment {
    private TweetzClient tweetzClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetzClient = TweetzApplication.getRestClient();
        populateTimeLine(0);
    }

    public static MentionsTimelineFragment newInstance(int pos, String tag){
        return new MentionsTimelineFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstaneState) {
        super.onViewCreated(view, savedInstaneState);
    }

    public void populateTimeLine(int offSet) {
        final int mPage = offset;

        tweetzClient.getMentionsTimeline(new JsonHttpResponseHandler() {

            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());
                //Create models
                ArrayList<Tweet> newTweets = Tweet.fromJsonArray(response);

                if (newTweets.size() > 0) {
                    addAll(newTweets);
                }
            }
            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
                Toast.makeText(getActivity(),"API limit reached", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
