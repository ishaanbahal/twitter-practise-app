package com.example.ishaanbahal.twit.user;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ishaanbahal.twit.HomeActivity;
import com.example.ishaanbahal.twit.constants.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import twitter4j.MediaEntity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by IshaanBahal on 28/06/17.
 */

public class Timeline extends AsyncTask<String, Void, User> {
    HomeActivity activity;
    private String token;
    private String tokenSecret;
    private Long userId;
    private ArrayList<com.example.ishaanbahal.twit.user.Status> _statuses;

    @Inject
    public Timeline(@Named("activity") HomeActivity activity, @Named("token") String token,@Named("tokenSecret") String tokenSecret,@Named("userId") Long userId){
        this.token = token;
        this.tokenSecret = tokenSecret;
        this.userId = userId;
        this.activity=activity;
    }

    @Override
    protected User doInBackground(String... strings) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder
                .setOAuthConsumerKey(Constants.APP_CONSUMER_KEY)
                .setOAuthConsumerSecret(Constants.APP_CONSUMER_SECRET)
                .setOAuthAccessToken(this.token)
                .setOAuthAccessTokenSecret(this.tokenSecret);
        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = twitterFactory.getInstance();
        try{
            User user = twitter.showUser(this.userId);
            List<twitter4j.Status> statuses =twitter.getHomeTimeline();
            _statuses = new ArrayList<>();
            for (twitter4j.Status status : statuses){
                com.example.ishaanbahal.twit.user.Status _status = new com.example.ishaanbahal.twit.user.Status();
                _status.setName(status.getUser().getName());
                _status.setUsername("@"+status.getUser().getScreenName());
                _status.setCreatedAt(status.getCreatedAt());
                _status.setImageUrl(status.getUser().getProfileImageURL());
                _status.setTweet(status.getText());
                MediaEntity []mediaEntities = status.getMediaEntities();
                for (MediaEntity mediaEntity: mediaEntities){
                    _status.setMediaUrl(mediaEntity.getMediaURLHttps());
                    break;
                }
                _statuses.add(_status);
            }
            return user;
        }catch(TwitterException e){
            Log.e("ERROR", "doInBackground: ",e );
        }
        return null;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        this.activity.setData(user, _statuses);
    }
}
