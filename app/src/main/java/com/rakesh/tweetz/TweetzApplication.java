package com.rakesh.tweetz;

import android.app.Application;
import android.content.Context;

/**
 * Created by rparuthi on 3/29/2017.
 */

public class TweetzApplication  extends Application {

    private static Context context;


    public void onCreate(){
        super.onCreate();
    }

    public static TweetzClient getRestClient() {
        return (TweetzClient) TweetzClient.getInstance(TweetzClient.class, TweetzApplication.context);
    }


}
