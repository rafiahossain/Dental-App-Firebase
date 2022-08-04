package com.example.mydentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    //Variables
    TextInputLayout fullname, startDate, password;
    TextView fullnameLabel, emailLabel, usernameLabel;

    //Global variables for getting string values through intent
    String _NAME, _EMAIL, _USERNAME, _STARTDATE, _PASSWORD;

    FirebaseAuth mAuth;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Hooking variables to xml file

        //Textview
        fullnameLabel = findViewById(R.id.fullname_field);
        emailLabel = findViewById(R.id.email_field);
        usernameLabel = findViewById(R.id.username_field);

        //TextInputLayout
        fullname = findViewById(R.id.full_name_profile);
        startDate = findViewById(R.id.start_date_profile);
        password = findViewById(R.id.password_profile);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        //.child(userID);

        //get data from real time database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userProfile = snapshot.getValue(UserClass.class);

                if (userProfile != null) {
//                    fullnameLabel.setText(userProfile.name);
//                    emailLabel.setText(userProfile.emailadd);
//                    usernameLabel.setText(userProfile.username);

                    fullname.getEditText().setText(userProfile.name);
                    startDate.getEditText().setText(userProfile.startDate);
                    password.getEditText().setText(userProfile.password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

        //Method to show all data
        //showAllUserData();
    }

//    private void showAllUserData() {
//        //Get values from intent
////        Intent i = getIntent();
////        _NAME = i.getStringExtra("name");
////        _EMAIL = i.getStringExtra("emailadd");
////        _USERNAME = i.getStringExtra("username");
////        _STARTDATE = i.getStringExtra("startDate");
////        _PASSWORD = i.getStringExtra("password");
////
////        //SetText for all values
////        fullnameLabel.setText(_NAME);
////        emailLabel.setText(_EMAIL);
////        fullname.getEditText().setText(_NAME);
////        username.getEditText().setText(_USERNAME);
////        startDate.getEditText().setText(_STARTDATE);
////        password.getEditText().setText(_PASSWORD);
//
//
//
////        FirebaseDatabase.getInstance("https://dentalhealthapp-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
////                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                UserClass userProfile = snapshot.getValue(UserClass.class);
////
////                if(userProfile != null){
////                    email.getEditText().setText(userProfile.emailadd);
////                    usernameLabel.setText(userProfile.username);
////                    startDate.getEditText().setText(userProfile.startweek);
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
////            }
////        });
//
//    }

    public void logout(View view) {
        Intent i = new Intent(getApplicationContext(), MyLoginActivity.class);
        startActivity(i);
    }

    public void chat(View view) {
        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(i);
    }

    public void tips(View view) {

    }

    public void update(View view) {

    }


}