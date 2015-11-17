package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Date;

/**
 * Created by xmeng on 11/8/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{
    public Tweet tweet;
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tweet = (Tweet) getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfile);
        final TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvName.setText(tweet.getUser().getName());
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        tvTime.setText(getReletiveTime(tweet.getCreateAt()));

        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                Log.i("debug", tvUserName.getText().toString());
                i.putExtra("screenName", tvUserName.getText().toString());
                getContext().startActivity(i);
            }
        });

        return convertView;
    }

    public String getReletiveTime(String timeStamp) {
        Date date = new Date(timeStamp);
        long now = System.currentTimeMillis();
        return DateUtils.getRelativeTimeSpanString(date.getTime(), now, DateUtils.FORMAT_ABBREV_TIME).toString();
    }
}
