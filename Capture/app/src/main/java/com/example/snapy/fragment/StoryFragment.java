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
import com.example.snapy.UserInformation;
import com.example.snapy.recyclerViewStory.StoryAdapter;
import com.example.snapy.recyclerViewStory.StoryObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<StoryObject> results = new ArrayList<>();


    public static StoryFragment newInstance() {
        StoryFragment fragment = new StoryFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_story, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerViewStory);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new StoryAdapter(getDataset(), getContext());

        mRecyclerView.setAdapter(adapter);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeStory);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                listenForData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        clear();
        listenForData();

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
        for (int i = 0; i < UserInformation.listFollowing.size(); i++) {
            DatabaseReference followingStoryDB = FirebaseDatabase.getInstance().getReference().child("Users").child(UserInformation.listFollowing.get(i));
            followingStoryDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.child("email").getValue().toString();
                    String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                    String uid = dataSnapshot.getRef().getKey();

                    long timestampBeg = 0, timestampEnd = 0;

                    for (DataSnapshot storySnapshot : dataSnapshot.child("story").getChildren()) {
                        if (storySnapshot.child("timestampBeg").getValue() != null) {
                            timestampBeg = Long.parseLong(storySnapshot.child("timestampBeg").getValue().toString());
                        }
                        if (storySnapshot.child("timestampEnd").getValue() != null) {
                            timestampEnd = Long.parseLong(storySnapshot.child("timestampEnd").getValue().toString());
                        }

                        long currentTime = System.currentTimeMillis();

                        if (currentTime >= timestampBeg && currentTime <= timestampEnd) {
                            StoryObject object = new StoryObject(username, uid, profileImageUrl,"story");
                            if (!results.contains(object)) {
                                results.add(object);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}