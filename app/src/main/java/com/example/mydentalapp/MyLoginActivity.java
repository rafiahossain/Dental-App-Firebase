package com.example.mydentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyLoginActivity extends AppCompatActivity {

    private EditText edittextemail, edittextpassword;
    private Button btnlogin, btnregister, btnforgotpass;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);

        edittextemail = (EditText)findViewById(R.id.email1);
        edittextpassword = (EditText)findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnlogin1);
        btnforgotpass = (Button) findViewById(R.id.forgotpass);
        btnregister = (Button) findViewById(R.id.btnregister1);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edittextemail.getText().toString().trim();
                String password = edittextpassword.getText().toString().trim();

                //check if any field empty
                if(email.equals("")||password.equals("")){
                    Toast.makeText(MyLoginActivity.this, "Please enter values for all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        edittextemail.setError("Please provide valid email address");
                        edittextemail.requestFocus();
                        return;
                    }

                    // Check if password is at least 6 characters long
                    if(password.length() < 6){
                        edittextpassword.setError("Password should be minimum 6 characters");
                        edittextpassword.requestFocus();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //check if user has confirmed email verification
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if(user.isEmailVerified()) {
                                    //redirect to home page/main activity
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(MyLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                }else{
                                    user.sendEmailVerification();
                                    Toast.makeText(MyLoginActivity.this, "Please check email to verify account, check spam folder as well", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    Intent i = new Intent(getApplicationContext(), MyLoginActivity.class);
                                    startActivity(i);
                                }
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MyLoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

        btnforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(i);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyRegisterPage.class);
                startActivity(i);
            }
        });

    }
}