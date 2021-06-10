package com.example.snapy.RecyclerViewReciever;

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

public class RecieverAdapter extends RecyclerView.Adapter<RecieverViewHolder> {
    private List<RecieverObject> usersList;
    private Context context;

    public RecieverAdapter(List<RecieverObject> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
}

    @NonNull
    @Override
    public RecieverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_reciever_item, null);
        RecieverViewHolder rcv = new RecieverViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecieverViewHolder rcViewHolders, int i) {
        rcViewHolders.username.setText(usersList.get(i).getUsername());

        String imageUrl = usersList.get(i).getProfileImageUrl();

        if (imageUrl.equals("default")) {
            rcViewHolders.profileImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.profile));
        } else {
            Glide.with(context).load(imageUrl).into(rcViewHolders.profileImage);
        }

        rcViewHolders.receiveCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean receiveState = !usersList.get(rcViewHolders.getLayoutPosition()).getReceive();
                usersList.get(rcViewHolders.getLayoutPosition()).setReceive(receiveState);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.usersList.size();
    }
}
