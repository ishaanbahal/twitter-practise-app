package com.example.ishaanbahal.twit.user;

import android.content.Context;

import dagger.Component;

/**
 * Created by IshaanBahal on 10/07/17.
 */

@Component(modules = TimelineAdapterModule.class)
public interface TimelineAdapterComponent {
    TimelineAdapter timelineAdapter();

    Context inject(Context context);
}
