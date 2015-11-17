package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xmeng on 11/8/15.
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagLine;
    private int followers;
    private int followings;
    private int tweetsCount;

    public String getTagLine() {
        return tagLine;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowings() {
        return followings;
    }

    public int getTweetsCount() {
        return tweetsCount;
    }

    public static User fromJson(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.tagLine = json.getString("description");
            u.followers = json.getInt("followers_count");
            u.followings = json.getInt("friends_count");
            u.tweetsCount = json.getInt("statuses_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;

    }

}
