package com.example.ishaanbahal.twit.user;

import com.example.ishaanbahal.twit.HomeActivity;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by IshaanBahal on 10/07/17.
 */

@Component
public interface TimelineComponent {

    Timeline timeline();

    @Component.Builder
    interface Builder{
        @BindsInstance Builder token(@Named("token") String token);
        @BindsInstance Builder tokenSecret(@Named("tokenSecret") String tokenSecret);
        @BindsInstance Builder userId(@Named("userId") Long userId);
        @BindsInstance Builder activity(@Named("activity")HomeActivity activity);
        TimelineComponent build();
    }

}
