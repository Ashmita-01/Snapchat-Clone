package com.example.snapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class ShowCaptureActivity extends AppCompatActivity {

    String Uid;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_capture);
        final byte[] img = CameraFragment.imageByte;

        try {
            bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

        ImageView imgView = findViewById(R.id.imageViewCapturedImageShowCapture);
        imgView.setImageBitmap(bitmap);

        Uid = FirebaseAuth.getInstance().getUid();

        FloatingActionButton mSend = findViewById(R.id.buttonStorySend);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChooseRecieversActivity.class);
                startActivity(intent);
            }
        });
    }

}
