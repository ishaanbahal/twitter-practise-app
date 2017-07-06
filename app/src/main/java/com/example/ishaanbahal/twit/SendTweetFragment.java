package com.example.ishaanbahal.twit;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.ishaanbahal.twit.user.SendTweet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendTweetFragment extends DialogFragment {

    @BindView(R.id.tweet)
    EditText tweet;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @OnClick(R.id.btn_send)
    public void sendTeweet(View v){
        SendTweet sendTweet = new SendTweet(this,this.token, this.tokenSecret);
        progressBar.setVisibility(View.VISIBLE);
        sendTweet.execute(tweet.getText().toString());
    }

    private String token, tokenSecret;
    public SendTweetFragment() {
    }

    public void setToken(String token, String tokenSecret){
        this.tokenSecret=tokenSecret;
        this.token=token;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Send a tweet");

        View view =  inflater.inflate(R.layout.fragment_send_tweet, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
