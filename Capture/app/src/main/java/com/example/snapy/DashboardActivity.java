package com.example.snapy;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.snapy.fragment.ChatFragment;
import com.example.snapy.fragment.StoryFragment;

public class DashboardActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    ImageButton fpaCamera, fpaChat, fpaPeople;
    ViewPager viewPager;
    public static ProgressBar bar;
    //Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        UserInformation userInformationListener = new UserInformation();
        userInformationListener.startFetching();

        fpaCamera = findViewById(R.id.imageButtonCamera);
        fpaChat = findViewById(R.id.imageButtonChat);
        fpaPeople = findViewById(R.id.imageButtonPeople);
        viewPager = findViewById(R.id.viewPager);


        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        bar = findViewById(R.id.loadingMain);

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);

        fpaCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() == 1) {
                    CameraFragment page = (CameraFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
                    if (page != null) {
                        page.captureImage();
                    }
                } else {
                    viewPager.setCurrentItem(1);
                }
            }
        });

        fpaChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        fpaPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        viewPager.setCurrentItem(1);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ChatFragment.newInstance();
                case 1:
                    return CameraFragment.newInstance();
                case 2:
                    return StoryFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
