package com.codepath.apps.mysimpletweets;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;

public class TimelineActivity extends AppCompatActivity implements PostTweetFragment.PostTweetListener{

    TweetsListFragment fragmentTweetsList;
    ViewPager vPager;
    FragmentPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        vPager = (ViewPager)findViewById(R.id.viewpager);
        adapter = new TweetsPagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(adapter);
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        MenuItem item = menu.findItem(R.id.action_compose);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                android.app.FragmentManager fm = getFragmentManager();
                DialogFragment postTweet = new PostTweetFragment();
                postTweet.show(fm, "postTweets");
                return false;
            }
        });

        MenuItem profileItem = menu.findItem(R.id.action_profile);
        profileItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
                return true;
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
    @Override
    public void onFinishPost() {
        fragmentTweetsList = (TweetsListFragment)adapter.getItem(vPager.getCurrentItem());
        fragmentTweetsList.populateTimeline();
    }

    public void onProfileView() {
        //launch profile view

    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 2;
        String[] titles;
        TweetsListFragment[] tweetFragments;


        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
            tweetFragments = new TweetsListFragment[2];
            tweetFragments[0] = new HomeTimelineFragment();
            tweetFragments[1] = new MentionsTimelineFragment();
            titles = new String[2];
            titles[0] = "Home";
            titles[1] = "Mentions";
        }

        @Override
        public Fragment getItem(int position) {
                return tweetFragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }
}
