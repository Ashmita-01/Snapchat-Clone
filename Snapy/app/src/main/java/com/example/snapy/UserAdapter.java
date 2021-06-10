package com.example.snapy;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<NewUserInfo> mUsers;

    public UserAdapter (Context mContext,List<NewUserInfo> mUsers){
        this.mUsers= mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewUserInfo user = mUsers.get(position);
        holder.fullName.setText(user.getFullName());
        holder.profile_image.setImageResource(R.drawable.profile_icon);

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView fullName;
        public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);

            fullName = itemView.findViewById(R.id.username);
            profile_image=itemView.findViewById(R.id.profilePhoto);
        }
    }
}
