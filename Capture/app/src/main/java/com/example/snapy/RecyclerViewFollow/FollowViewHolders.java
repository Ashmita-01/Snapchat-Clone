package com.example.snapy.RecyclerViewFollow;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.snapy.R;

public class FollowViewHolders extends RecyclerView.ViewHolder {

    public TextView email;
    public Button follow;
    public ImageView profileImage;

    public FollowViewHolders(View itemView) {
        super(itemView);

        email = itemView.findViewById(R.id.textViewUsernameFollowItem);
        follow = itemView.findViewById(R.id.buttonFollowItem);
        profileImage = itemView.findViewById(R.id.imageViewFollower);
    }
}
