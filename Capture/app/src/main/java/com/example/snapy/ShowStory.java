package com.example.snapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ShowStory extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_story);


    }
    public void endstory(View view) {

        Intent intent=new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }
}