package com.example.snapy.RecyclerViewFollow;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.snapy.R;
import com.example.snapy.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowViewHolders> {
    private List<FollowObject> usersList;
    private Context context;

    public FollowAdapter(List<FollowObject> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public FollowViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_followers_item, null);
        FollowViewHolders rcv = new FollowViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowViewHolders rcViewHolders, int i) {
        rcViewHolders.email.setText(usersList.get(i).getUsername());

        String imageUrl = usersList.get(i).getProfileImageUrl();

        if (imageUrl.equals("default")) {
            rcViewHolders.profileImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.profile));
        } else {
            Glide.with(context).load(imageUrl).into(rcViewHolders.profileImage);
        }

        if (UserInformation.listFollowing.contains(usersList.get(rcViewHolders.getLayoutPosition()).getUid())) {
            rcViewHolders.follow.setText("Following");
            rcViewHolders.follow.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_round));
            rcViewHolders.follow.setTextColor(Color.parseColor("#ffffff"));
        } else {
            rcViewHolders.follow.setText("Follow");
            rcViewHolders.follow.setBackground(ContextCompat.getDrawable(context, R.drawable.button_follow));
            rcViewHolders.follow.setTextColor(ContextCompat.getColor(context, R.color.blueLight));
        }

        rcViewHolders.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (!UserInformation.listFollowing.contains(usersList.get(rcViewHolders.getLayoutPosition()).getUid())) {
                    rcViewHolders.follow.setText("Following");
                    rcViewHolders.follow.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_round));
                    rcViewHolders.follow.setTextColor(Color.parseColor("#ffffff"));
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("following")
                            .child(usersList.get(rcViewHolders.getLayoutPosition()).getUid()).setValue(true);
                } else {
                    rcViewHolders.follow.setText("Follow");
                    rcViewHolders.follow.setBackground(ContextCompat.getDrawable(context, R.drawable.button_follow));
                    rcViewHolders.follow.setTextColor(ContextCompat.getColor(context, R.color.blueLight));
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("following")
                            .child(usersList.get(rcViewHolders.getLayoutPosition()).getUid()).removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.usersList.size();
    }
}
