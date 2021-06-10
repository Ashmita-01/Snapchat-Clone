package com.example.snapy.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snapy.R;
import com.example.snapy.recyclerViewStory.StoryAdapter;
import com.example.snapy.recyclerViewStory.StoryObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<StoryObject> results = new ArrayList<>();

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            clear();
            listenForData();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerViewStory);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new StoryAdapter(getDataset(), getContext());

        mRecyclerView.setAdapter(adapter);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeChat);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                listenForData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void clear() {
        int size = this.results.size();
        this.results.clear();
        adapter.notifyItemRangeChanged(0, size);
    }

    private ArrayList<StoryObject> getDataset() {
        listenForData();
        return results;
    }

    private void listenForData() {
        DatabaseReference receiveDB = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid()).child("received");

        receiveDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        getUserInfo(snap.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserInfo(String key) {
        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference().child("users").child(key);
        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String username = dataSnapshot.child("username").getValue().toString();
                    String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                    String uid = dataSnapshot.getRef().getKey();

                    StoryObject obj = new StoryObject(username, uid, profileImageUrl,"chat");
                    if (!results.contains(obj)) {
                        results.add(obj);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}