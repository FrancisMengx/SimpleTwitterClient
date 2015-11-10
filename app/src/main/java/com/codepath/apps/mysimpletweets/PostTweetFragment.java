package com.codepath.apps.mysimpletweets;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class PostTweetFragment extends DialogFragment {
    TwitterClient client;
    View view;
    public interface PostTweetListener {
        public void onFinishPost();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_tweet, container, false);
        client = TwitterApplication.getRestClient();
        Button btnPost = (Button)view.findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitPost();
            }
        });
        return view;
    }

    public void onSubmitPost() {
        TextView tvNewTweet = (TextView)view.findViewById(R.id.etNewPost);
        String postText = tvNewTweet.getText().toString();
        client.postStatus(postText, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                PostTweetListener activity = (PostTweetListener) getActivity();
                activity.onFinishPost();
                getDialog().dismiss();
            }
        });
    }
}
