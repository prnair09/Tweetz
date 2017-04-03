package com.rakesh.tweetz.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.rakesh.tweetz.R;
import com.rakesh.tweetz.TweetzApplication;
import com.rakesh.tweetz.TweetzClient;
import com.rakesh.tweetz.adapters.TweetsArrayAdapter;
import com.rakesh.tweetz.listeners.EndlessScrollListener;
import com.rakesh.tweetz.models.Tweet;
import com.rakesh.tweetz.models.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rparuthi on 3/29/2017.
 */


public abstract class TweetsListFragment extends DialogFragment {


    Toolbar toolbar;

    private TweetzClient tweeterClient;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private RecyclerView rvTweets;
    private FloatingActionButton fabCompose;

    public ListView getLvTweets() {
        return lvTweets;
    }

    public TweetzClient getTweeterClient() {
        return tweeterClient;
    }

    private SharedPreferences mSettings;

    User currentUser;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);

        tweeterClient = TweetzApplication.getRestClient();

        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        fabCompose = (FloatingActionButton) v.findViewById(R.id.fabComposeTweet);

        fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCompose();
            }
        });
        setLoggedInUser();

        return v;
    }

    public void showCompose(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(currentUser);
        composeTweetFragment.show(fragmentManager, "Compose_Tweet");

    }

    //Creation LifeCycle Event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(View view, Bundle savedInstaneState) {

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeLine(page);
                return true;
            }
        });

        lvTweets.setAdapter(aTweets);

    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
        aTweets.notifyDataSetChanged();
    }

    public  void addNewTweet(Tweet tweet){
        tweets.add(0, tweet);
        aTweets.notifyDataSetChanged();
        lvTweets.setSelection(0);
    }


    private void setLoggedInUser() {

        if(!mSettings.getString("ScreenName","").isEmpty()) {
            tweeterClient.getUser(new TextHttpResponseHandler() {

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("DEBUG", responseString);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    if (responseString != null) {
                        try {
                            JSONObject objJson = new JSONObject(responseString);

                            currentUser = User.fromJson(objJson);


                            SharedPreferences.Editor editor = mSettings.edit();
                            editor.putString("ScreenName", currentUser.getScreenName());
                            editor.apply();


                            Log.d("DEBUG", currentUser.getName());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }
    }



    public abstract void populateTimeLine(int offset);
}
