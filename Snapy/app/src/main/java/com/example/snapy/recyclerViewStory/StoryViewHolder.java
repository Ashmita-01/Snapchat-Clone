package com.example.snapy.recyclerViewStory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snapy.DisplayImageActivity;
import com.example.snapy.R;

public class StoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView email;
    public LinearLayout layout;
    public ImageView profileImage;
    public CardView cardView;

    public StoryViewHolder (View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        email = itemView.findViewById(R.id.textViewUsernameStory);
        layout = itemView.findViewById(R.id.layoutStory);
        profileImage = itemView.findViewById(R.id.imageViewStoryProfile);
        cardView = itemView.findViewById(R.id.cardViewStory);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), DisplayImageActivity.class);
        Bundle b = new Bundle();
        b.putString("uid", email.getTag().toString());
        b.putString("profileImageUrl", cardView.getTag().toString());
        b.putString("email", email.getText().toString());
        b.putString("chatOrStory", layout.getTag().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}
