package com.example.snapy.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snapy.DashboardActivity;
import com.example.snapy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvRegister,tvForgetPass,tvLogin,tvRegisterPage;
    EditText etEmailLogin,etPassLogin;
    Button btnLogin;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

   /* public void signInClicked(View view){
        Intent signIn=new Intent(this,SignUp.class);
        startActivity(signIn);
    }
    public void logInClicked(View view){
        Intent lognIn=new Intent(this,LogIn.class);
        startActivity(lognIn);
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLogin = findViewById(R.id.login_transaction);
        tvRegisterPage = findViewById(R.id.register_transaction);
        tvRegisterPage.setOnClickListener(this);
        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(this);
        tvForgetPass = findViewById(R.id.tvForgetPass);
        tvForgetPass.setOnClickListener(this);
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPassLogin = findViewById(R.id.etPassLogin);
        btnLogin = findViewById(R.id.btnlogIn);
        btnLogin.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser!=null){
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvRegister:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.tvForgetPass:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.btnlogIn:
                userLogin();
                break;
            case R.id.register_transaction:
                startActivity(new Intent(this, SignUp.class));
        }
    }

    private void userLogin() {
        String email = etEmailLogin.getText().toString().trim();
        String password = etPassLogin.getText().toString().trim();

        if (email.isEmpty()) {
            etEmailLogin.setError("Email is required");
            etEmailLogin.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailLogin.setError("Please provide valid email");
            etEmailLogin.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassLogin.setError("Password is required");
            etPassLogin.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassLogin.setError("Min password length must be 6 characters");
            etPassLogin.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        //Redirecting to Home Screen
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to Login! Incorrect Credentials", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        etEmailLogin.setText("");
        etPassLogin.setText("");
    }
}
