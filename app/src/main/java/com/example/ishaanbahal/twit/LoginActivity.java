package com.example.ishaanbahal.twit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.ishaanbahal.twit.auth.FetchRequestTokenAsync;

import butterknife.ButterKnife;
import butterknife.OnClick;
import twitter4j.User;
import twitter4j.auth.AccessToken;

public class LoginActivity extends AppCompatActivity {

    public static String TOKEN = "token";
    public static String TOKEN_SECRET = "token_secret";
    public static String USER_ID = "user_id";

    @OnClick(R.id.button)
    public void initLogin(View v) {
        new FetchRequestTokenAsync(this).execute();
    }

    private void navigateToHome(String token, String tokenSecret, Long userId){
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.putExtra(TOKEN,token);
        homeIntent.putExtra(TOKEN_SECRET,tokenSecret);
        homeIntent.putExtra(USER_ID,userId);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SharedPreferences pref = getSharedPreferences("social_pref",Context.MODE_PRIVATE);
        if(tokenExists(pref)){
            navigateToHome(
                    pref.getString(TOKEN,""),
                    pref.getString(TOKEN_SECRET,""),
                    pref.getLong(USER_ID,0L));
        }
    }

    public boolean tokenExists(SharedPreferences pref){
        return pref.getString(TOKEN,null)!=null && pref.getString(TOKEN_SECRET,null)!=null;
    }


    public void callBackDataFromAsyncTask(AccessToken accessToken, User user){
        Toast.makeText(this, "Logged in as"+user.getName(), Toast.LENGTH_SHORT).show();
        SharedPreferences pref= getSharedPreferences("social_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN,accessToken.getToken());
        editor.putString(TOKEN_SECRET,accessToken.getTokenSecret());
        editor.putLong(USER_ID,user.getId());
        editor.apply();
        navigateToHome(accessToken.getToken(),accessToken.getTokenSecret(), user.getId());
    }

}
