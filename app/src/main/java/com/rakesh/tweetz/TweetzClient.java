package com.rakesh.tweetz;

import android.content.Context;
import android.support.annotation.Nullable;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 *
 * This is the object responsible for communicating with a REST API.
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes:
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 *
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 *
 */
public class TweetzClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "qoCEdcNSZzY4XQy68aOO6usII";       // Change this
    public static final String REST_CONSUMER_SECRET = "Y89jVXBIpcaBBebbbog05foZyr3m29jPYj7iBr1X5H2i1KlwIn"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

    public TweetzClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }

    //HomeTimeLine - Get the timeline
    //GET statuses/home_timeline.json
    //      Count = 25
    //since_id = 1

    public void getHomeTimeline(AsyncHttpResponseHandler handler,long maxId){
        String apiUrl = getApiUrl("statuses/home_timeline.json");

        //Request Params
        RequestParams params = new RequestParams();
        params.put("count",25);
        //params.put("since_id",1);

        if(maxId <= 0){
            params.put("since_id",1);
        }else{
            params.put("max_id",maxId);
        }

        //Execute
        getClient().get(apiUrl,params,handler);
    }

    public void getMentionsTimeline(AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");

        //Request Params
        RequestParams params = new RequestParams();
        params.put("count",25);

        //Execute
        getClient().get(apiUrl,params,handler);
    }

    public void getUserTimeline(AsyncHttpResponseHandler handler,String screenName,long maxId){
        String apiUrl = getApiUrl("statuses/user_timeline.json");

        //Request Params
        RequestParams params = new RequestParams();
        params.put("count",25);
        params.put("screen_name",screenName);

        if(maxId > 0){
            params.put("max_id",maxId);
        }

        //Execute
        getClient().get(apiUrl,params,handler);
    }


    public void composeATweet(String text, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl  = getApiUrl("statuses/update.json");

        //Request Params
        RequestParams params = new RequestParams();
        params.put("status", text);

        //Execute
        getClient().post(apiUrl, params, asyncHttpResponseHandler);
    }

    public void getUser(AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, null, asyncHttpResponseHandler);
    }

    public void getUserInfo(@Nullable String screenName, AsyncHttpResponseHandler asyncHttpResponseHandler){
        String apiUrl = getApiUrl("users/show.json");

        //Request Params
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);

        //Execute
        getClient().get(apiUrl,params,asyncHttpResponseHandler);


    }

}
