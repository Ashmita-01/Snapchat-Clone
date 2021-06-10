package com.example.snapy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snapy.LoginSignUp.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private CircleImageView profileImageView;
    private DatabaseReference reference;

    private String userID;
    private Button logout;
    FirebaseAuth mAuth;

    ImageView imageView;
    TextView editProfilebtn,myStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("Users");



        myStory = findViewById(R.id.myStorybtn);

        profileImageView = findViewById(R.id.profile_icon);
        editProfilebtn = findViewById(R.id.editProfile);
        logout = findViewById(R.id.btnLogout);
        imageView=findViewById(R.id.arrowback);

        //profileName=findViewById(R.id.profileName);
        //profileEmail=findViewById(R.id.profileDob);
        //profiledob=findViewById(R.id.profileDob);


        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });*/

        editProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,EditProfile.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

       // final TextView greetingTextView = findViewById(R.id.greeting);
        final TextView fullNameTextView = findViewById(R.id.profileName);
        final TextView emailTextView = findViewById(R.id.profileEmail);
        final TextView dobTextView = findViewById(R.id.profileDob);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String dob = userProfile.dob;

                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    dobTextView.setText(dob);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"Something wrong happened!",Toast.LENGTH_LONG).show();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });



        getUserInfo();

    }

    public void mystory(View view) {
        Intent intent=new Intent(getApplicationContext(), ShowStory.class);
        startActivity(intent);


    }

    private void getUserInfo() {
        reference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){

                    //String name = dataSnapshot.child("name").getValue().toString();


                    if(dataSnapshot.hasChild("image") ){
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}