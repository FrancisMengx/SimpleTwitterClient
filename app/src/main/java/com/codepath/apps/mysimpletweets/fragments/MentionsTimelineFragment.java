package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.PostTweetFragment;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by xmeng on 11/15/15.
 */
public class MentionsTimelineFragment extends TweetsListFragment {

    TwitterClient client;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        oldestTweetId = 0;
        populateTimeline();
    }

    // Send api request to get the timeline JSON
    //
    public void populateTimeline() {
        client.getMentionsTimeline(oldestTweetId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                addAll(Tweet.fromJSONArray(json));
                oldestTweetId += tweets.get(tweets.size()-1).getId();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResp) {
                Log.d("DEBUG", errorResp.toString());
                Toast.makeText(getActivity(), "Request tweets failed", Toast.LENGTH_LONG).show();
            }
        });
    }


}
