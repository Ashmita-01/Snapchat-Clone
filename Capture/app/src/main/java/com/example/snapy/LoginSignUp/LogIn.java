package com.example.snapy.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snapy.R;
import com.example.snapy.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    EditText email,password;
    Button login;
    TextView textView;
    private FirebaseAuth mAuth;
    public void alreadyRegister(View view){
        loginInFunction();
    }
    public void loginInFunction(){
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if(mAuth.getCurrentUser().isEmailVerified()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                welcomeActivity();
                            }else{
                                Toast.makeText(LogIn.this, "Please verify email address to login", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        login=findViewById(R.id.Button);
        email=findViewById(R.id.editTextTextEmailAddress2);
        password=findViewById(R.id.editTextPassword2);
        mAuth = FirebaseAuth.getInstance();
        textView=findViewById(R.id.forgetTextView);
    }
    public void ResetPAsswordLink(View view){
        final EditText editText=new EditText(view.getContext());
        AlertDialog.Builder resetPasswordDialog=new AlertDialog.Builder(view.getContext());
        resetPasswordDialog.setTitle("Reset Password ?");
        resetPasswordDialog.setMessage("Enter Your Email id");
        resetPasswordDialog.setView(editText);
        resetPasswordDialog.setPositiveButton("Send Link", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=editText.getText().toString();
                mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(LogIn.this,"Password Resend Link Successfully Send",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LogIn.this, "Error ! Password Resend link not send"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        resetPasswordDialog.create().show();
    }
    public void welcomeActivity(){
        Intent intent=new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}