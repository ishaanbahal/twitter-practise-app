package com.example.ishaanbahal.twit.user;

import android.content.Context;

import com.example.ishaanbahal.twit.R;

import dagger.Module;
import dagger.Provides;

/**
 * Created by IshaanBahal on 10/07/17.
 */

@Module
public class TimelineAdapterModule {
    private final Context context;

    @Provides
    TimelineAdapter provideTimelineAdapter(Context context){
        return new TimelineAdapter(context, R.layout.item_timeline);
    }

    public TimelineAdapterModule(Context context){
        this.context = context;
    }

    @Provides
    Context context(){
        return context;
    }
}
