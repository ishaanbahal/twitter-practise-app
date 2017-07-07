package com.example.ishaanbahal.twit.user;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ishaanbahal.twit.R;
import com.example.ishaanbahal.twit.databinding.ItemTimelineBinding;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IshaanBahal on 28/06/17.
 */

public class TimelineAdapter extends ArrayAdapter<Status> implements View.OnClickListener{

    public TimelineAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @Override
    public void onClick(View view) {
        ViewHolder vh = (ViewHolder) view.getTag();
        Log.d("TESTING", "onClick: "+vh.name.getText().toString());
    }

    static class ViewHolder{

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.username)
        TextView username;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.profile_pic)
        ImageView profilePic;

        @BindView(R.id.media_pic)
        ImageView mediaPic;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemTimelineBinding itemTimelineBinding;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        itemTimelineBinding = DataBindingUtil.getBinding(convertView);
        if (itemTimelineBinding==null){
            itemTimelineBinding = ItemTimelineBinding.inflate(inflater, parent, false);
        }
        itemTimelineBinding.setStatus(getItem(position));
        return itemTimelineBinding.getRoot();
    }
}
