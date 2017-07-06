package com.example.ishaanbahal.twit.auth;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by IshaanBahal on 30/06/17.
 */


@Module
public class AuthModule {
    String authToken;
    String authSecret;

    public AuthModule(String authToken, String authSecret){
        this.authToken = authToken;
        this.authSecret = authSecret;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    String providesAuthToken(){
        return authToken;
    }

    @Provides
    String providesAuthSecret(){
        return authSecret;
    }
}
