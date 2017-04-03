package com.rakesh.tweetz.models;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rparuthi on 3/23/2017.
 */

public class User implements Serializable{

    private String name;
    private long uid;
    private String tagLine;

    public int getFollowersCount() {
        return followersCount;
    }

    public String getTagLine() {
        return tagLine;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    private int followersCount;
    private int followingCount;

    public String getScreenName() {
        return screenName;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    private String screenName;
    private String profileImageUrl;


    //Create user object from json
    public static User fromJson(JSONObject jsonObject){
        User user = new User();

        try{
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
            user.tagLine =jsonObject.getString("description");
            user.followersCount =jsonObject.getInt("followers_count");
            user.followingCount =jsonObject.getInt("friends_count");

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  user;
    }


}
