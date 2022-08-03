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
    TextInputLayout fullname, email, startDate, password;
    TextView fullnameLabel, usernameLabel;

    //Global variables for hooks
    String _EMAIL, _USERNAME, _NAME, _STARTDATE, PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Hooking variables to xml file
        fullname = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        startDate = findViewById(R.id.start_date_profile);
        password = findViewById(R.id.password_profile);
        fullnameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);

        //Method to show all data
        showAllUserData();
    }

    private void showAllUserData() {

//        FirebaseDatabase.getInstance("https://dentalhealthapp-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserClass userProfile = snapshot.getValue(UserClass.class);
//
//                if(userProfile != null){
//                    email.getEditText().setText(userProfile.emailadd);
//                    usernameLabel.setText(userProfile.username);
//                    startDate.getEditText().setText(userProfile.startweek);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
//            }
//        });

    }

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