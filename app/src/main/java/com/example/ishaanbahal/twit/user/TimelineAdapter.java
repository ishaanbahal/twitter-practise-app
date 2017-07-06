package com.example.ishaanbahal.twit.user;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

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
        Status status = getItem(position);
        ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_timeline, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(status.getName());
        viewHolder.username.setText(status.getUsername());
        viewHolder.status.setText(status.getTweet());

        Picasso.with(getContext())
                .load(status.getImageUrl()).into(viewHolder.profilePic);

        if(status.getMediaUrl()!=null){
            Picasso.with(getContext()).load(status.getMediaUrl()).into(viewHolder.mediaPic);
            viewHolder.mediaPic.setVisibility(View.VISIBLE);
        }else{
            viewHolder.mediaPic.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(this);
        return convertView;
    }
}
