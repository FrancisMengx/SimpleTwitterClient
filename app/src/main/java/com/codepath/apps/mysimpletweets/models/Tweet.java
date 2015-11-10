package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xmeng on 11/8/15.
 */

// Parse JSON + store data
public class Tweet {
    private String body;
    private long id;
    private String createAt;
    private User user;

    public String getCreateAt() {
        return createAt;
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }


    public User getUser() {
        return user;
    }

    public static Tweet fromJson (JSONObject json) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = json.getString("text");
            tweet.id = json.getLong("id");
            tweet.createAt = json.getString("created_at");
            tweet.user = User.fromJson(json.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray json) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for(int i = 0; i < json.length(); i++){
            try {
                Tweet tweet = Tweet.fromJson(json.getJSONObject(i));
                if(tweet!=null) {
                    tweets.add(tweet);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }
}
