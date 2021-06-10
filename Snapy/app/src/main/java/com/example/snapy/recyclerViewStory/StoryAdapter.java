package com.example.snapy.recyclerViewStory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.snapy.R;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryViewHolder>{
    private List<StoryObject> usersList;
    private Context context;

    public StoryAdapter(List<StoryObject> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_story_item, null);
        StoryViewHolder rcv = new StoryViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final StoryViewHolder rcViewHolders, int i) {
        rcViewHolders.email.setText(usersList.get(i).getUsername());
        rcViewHolders.email.setTag(usersList.get(i).getUid());

        rcViewHolders.layout.setTag(usersList.get(i).getChatOrStory());

        String imageUrl = usersList.get(i).getProfileImageUrl();

        if (imageUrl.equals("default")) {
            rcViewHolders.profileImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.profile));
        } else {
            rcViewHolders.cardView.setTag(imageUrl);
            Glide.with(context).load(imageUrl).into(rcViewHolders.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return this.usersList.size();
    }
}
