package com.example.snapy.RecyclerViewReciever;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.snapy.R;

public class RecieverViewHolder extends RecyclerView.ViewHolder {
    public TextView username;
    public CheckBox receiveCheck;
    public ImageView profileImage;

    public RecieverViewHolder(View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.textViewUsernameFollowItem);
        receiveCheck = itemView.findViewById(R.id.recieverId);
        profileImage = itemView.findViewById(R.id.imageViewProfileFollowItem);
    }
}
