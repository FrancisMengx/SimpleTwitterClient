package com.codepath.apps.mysimpletweets;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Logger;

public class TimelineActivity extends AppCompatActivity implements PostTweetFragment.PostTweetListener{

    TwitterClient client;
    TweetsArrayAdapter tweetsAdapter;
    ArrayList<Tweet> tweets;
    ListView lvTweets;
    int sinceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        sinceId = 1;
        client = TwitterApplication.getRestClient();
        tweets = new ArrayList<Tweet>();
        tweetsAdapter = new TweetsArrayAdapter(this, tweets);
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        lvTweets.setAdapter(tweetsAdapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline();
                sinceId += 25;
                return true;
            }
        });
        populateTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        MenuItem item = menu.findItem(R.id.action_compose);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fm = getFragmentManager();
                DialogFragment postTweet = new PostTweetFragment();
                postTweet.show(fm, "postTweets");
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_compose) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Send api request to get the timeline JSON
    //
    private void populateTimeline() {
        client.getHomeTimeline(sinceId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                if(sinceId == 1) {
                    tweets.clear();
                }
                tweets.addAll(Tweet.fromJSONArray(json));
                tweetsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResp) {
                Log.d("DEBUG", errorResp.toString());
                Toast.makeText(getApplicationContext(), "Request tweets failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onFinishPost() {
        sinceId = 1;
        populateTimeline();
    }
}
