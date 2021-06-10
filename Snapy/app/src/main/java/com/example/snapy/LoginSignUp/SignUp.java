package com.example.snapy.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.snapy.R;
import com.example.snapy.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    TextView tvLogin;
    EditText etnameReg, etemailReg, etDobReg, etPasswrdReg;
    Button btnSignup;
    RadioButton rbmaleReg, rbfemaleReg, rbotherReg;
    ProgressBar progressBarReg;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        tvLogin = findViewById(R.id.login_transaction);
        tvLogin.setOnClickListener(this);
        etnameReg = findViewById(R.id.etnameregister);
        etemailReg = findViewById(R.id.etEmailregister);
        etDobReg = findViewById(R.id.etDobregister);
        etPasswrdReg = findViewById(R.id.etpasswrdregister);
        rbmaleReg = findViewById(R.id.rbmaleregister);
        rbfemaleReg = findViewById(R.id.rbfemaleregister);
        rbotherReg = findViewById(R.id.rbOtherregister);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(this);
        progressBarReg = findViewById(R.id.progressBar);


        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignup:
                registerUser();
                break;
            case R.id.login_transaction:
                startActivity(new Intent(SignUp.this,MainActivity.class));
                break;
        }
    }

    private void registerUser() {
        final String fullName = etnameReg.getText().toString().trim();
        String password = etPasswrdReg.getText().toString().trim();
        final String email = etemailReg.getText().toString().trim();
        final String dob = etDobReg.getText().toString().trim();


        if (fullName.isEmpty()) {
            etnameReg.setError("Full Name is required");
            etnameReg.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            etemailReg.setError("Email is required");
            etemailReg.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemailReg.setError("Please provide valid email");
            etemailReg.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etPasswrdReg.setError("Password is required");
            etPasswrdReg.requestFocus();
            return;
        }
        if (password.length() < 6) {
            etPasswrdReg.setError("Min password length must be 6 characters");
            etPasswrdReg.requestFocus();
            return;
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()){
            etPasswrdReg.setError("Password too weak");
            etPasswrdReg.requestFocus();
            return;
        }
        if(dob.isEmpty()){
            etDobReg.setError("Date of Birth is required");
            etDobReg.requestFocus();
            return;
        }

        //signUpFunction();
        progressBarReg.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(fullName,dob,email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                            setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();
                                progressBarReg.setVisibility(View.GONE);
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(SignUp.this,"Failed to Register! Try Again ",Toast.LENGTH_LONG).show();
                                progressBarReg.setVisibility(View.GONE);
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(SignUp.this,"Failed to Register",Toast.LENGTH_LONG).show();
                    progressBarReg.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        etnameReg.setText("");
        etemailReg.setText("");
        etDobReg.setText("");
        etPasswrdReg.setText("");
    }

    /* private boolean validateEmail() {
        String emailInput = etemailReg.getText().toString().trim();
        if (emailInput.isEmpty()) {
            etemailReg.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etemailReg.setError("Please enter a valid email address");
            return false;
        } else {
            etemailReg.setError(null);
            return true;
        }
    }*/
/*
    public void LoginActivity() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    public void sendVerificationEmail() {
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Register Successfully,please verify your email !!", Toast.LENGTH_SHORT).show();
                    etemailReg.setText("");
                    etPasswrdReg.setText("");
                    LoginActivity();
                }

            }
        });
    }

    public void signUpFunction() {
        mAuth.createUserWithEmailAndPassword(etemailReg.getText().toString(), etPasswrdReg.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendVerificationEmail();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {

                            Log.e("Authentication",task.getException().toString());
                            Toast.makeText(getApplicationContext(), "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
*/

}