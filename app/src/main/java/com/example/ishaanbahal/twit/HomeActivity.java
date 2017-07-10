package com.example.ishaanbahal.twit;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ishaanbahal.twit.databinding.ActivityHomeBinding;
import com.example.ishaanbahal.twit.user.DaggerTimelineAdapterComponent;
import com.example.ishaanbahal.twit.user.DaggerTimelineComponent;
import com.example.ishaanbahal.twit.user.Status;
import com.example.ishaanbahal.twit.user.Timeline;
import com.example.ishaanbahal.twit.user.TimelineAdapter;
import com.example.ishaanbahal.twit.user.TimelineAdapterModule;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import twitter4j.User;

public class HomeActivity extends AppCompatActivity {
    private String token, tokenSecret;
    private Long userId;
    private ActivityHomeBinding _activityHomeBinding;
    @Inject
    TimelineAdapter _timelineAdapter;
    @Inject
    Timeline timeline;
    @BindView(R.id.listview)
    ListView timelineListView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.floatingActionButton)
    public void sendNewTweet(View v) {
        FragmentManager fm = getSupportFragmentManager();
        SendTweetFragment dialogFragment = new SendTweetFragment();
        dialogFragment.setToken(this.token, this.tokenSecret);
        dialogFragment.show(fm, "create_tweet");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        _timelineAdapter = DaggerTimelineAdapterComponent
                .builder()
                .timelineAdapterModule(new TimelineAdapterModule(this))
                .build()
                .timelineAdapter();
        timelineListView.setAdapter(_timelineAdapter);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        token = intent.getStringExtra(LoginActivity.TOKEN);
        tokenSecret = intent.getStringExtra(LoginActivity.TOKEN_SECRET);
        userId = intent.getLongExtra(LoginActivity.USER_ID, 0L);
        timeline = DaggerTimelineComponent.builder()
                .token(token)
                .tokenSecret(tokenSecret)
                .userId(userId)
                .activity(this)
                .build()
                .timeline();
        timeline.execute();
    }

    public void setData(User user, ArrayList<Status> _statuses) {
        _activityHomeBinding.setUser(user);
        _timelineAdapter.addAll(_statuses);
        _timelineAdapter.notifyDataSetChanged();
        View view = getLayoutInflater().inflate(R.layout.show_more_footer, null, false);
        timelineListView.addFooterView(view);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                SharedPreferences pref = getSharedPreferences("social_pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                Toast.makeText(this, "User logged out successfully!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
