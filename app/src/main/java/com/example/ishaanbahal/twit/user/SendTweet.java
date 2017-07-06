package com.example.ishaanbahal.twit.user;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.ishaanbahal.twit.SendTweetFragment;
import com.example.ishaanbahal.twit.constants.Constants;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by IshaanBahal on 29/06/17.
 */

public class SendTweet extends AsyncTask<String, Void, Void> {
    private SendTweetFragment fragment;
    private String token, tokenSecret;

    public SendTweet(SendTweetFragment fragment, String token, String tokenSecret){
        this.token=token;
        this.tokenSecret = tokenSecret;
        this.fragment = fragment;
    }

    @Override
    protected Void doInBackground(String... strings) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder
                .setOAuthConsumerKey(Constants.APP_CONSUMER_KEY)
                .setOAuthConsumerSecret(Constants.APP_CONSUMER_SECRET)
                .setOAuthAccessToken(this.token)
                .setOAuthAccessTokenSecret(this.tokenSecret);
        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = twitterFactory.getInstance();
        try{
            twitter4j.Status status = twitter.updateStatus(strings[0]);
            return null;
        }catch (TwitterException e){
            Log.e("TESTING_TAG", "doInBackground: ", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(this.fragment.getContext(), "Successfully posted a new tweet!", Toast.LENGTH_SHORT).show();
        this.fragment.dismiss();
    }
}
