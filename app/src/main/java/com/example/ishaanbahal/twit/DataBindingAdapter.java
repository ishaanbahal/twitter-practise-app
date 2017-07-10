package com.example.ishaanbahal.twit;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by IshaanBahal on 07/07/17.
 */

public class DataBindingAdapter {

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
                .load(url).into(imageView);
    }
}
