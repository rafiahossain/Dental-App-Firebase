package com.example.mydentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private ImageButton logout, chat;
    private Button emDentist;
    //private ProgressBar progressBar;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    //private TextView usernametextview, emailtextview, stweektextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        logout = (ImageButton)findViewById(R.id.btnlogout);
        chat = (ImageButton)findViewById(R.id.btnchat);
        emDentist = (Button)findViewById(R.id.btnEmailDentist);
        //progressBar = (ProgressBar)findViewById(R.id.progressBar);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), MyLoginActivity.class);
                startActivity(i);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        emDentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyEmailActivity.class);
                startActivity(i);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        //.child(userID);

        //final type because we will access them in inner classes
        final TextView usernametextview = (TextView)findViewById(R.id.usernametv);
        final TextView emailtextview = (TextView)findViewById(R.id.emailtv);
        final TextView stweektextview = (TextView)findViewById(R.id.st_weektv);

        //Works here:
        //usernametextview.setText("My username");
        //So why doesnt it read from database

//        emailtextview.setText("My email");
//        usernametextview.setText("my username");
//        stweektextview.setText("my start");

        //get data from real time database

        //instead of reference use:
        //FirebaseDatabase.getInstance("https://dentalhealthapp-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        //                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
        //but it crashes at login why?
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserClass userProfile = snapshot.getValue(UserClass.class);

                if(userProfile != null){
                    emailtextview.setText(userProfile.emailadd);
                    usernametextview.setText(userProfile.username);
                    stweektextview.setText(userProfile.startweek);

//                    emailtextview.setText(userProfile.getUserEmail());
//                    usernametextview.setText(userProfile.getUserName());
//                    stweektextview.setText(userProfile.getStartWeek());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

        //From firebase documentation:

//        ValueEventListener userprofilelistener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                UserClass userProfile = snapshot.getValue(UserClass.class);
//
//                if(userProfile != null){
//                    String emailadd = userProfile.emailadd;
//                    String username = userProfile.username;
//                    int startweek = userProfile.startweek;
//
//                    emailtextview.setText(emailadd);
//                    usernametextview.setText(username);
//                    stweektextview.setText(startweek);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
//            }
//        };
//        reference.child(userID).addValueEventListener(userprofilelistener);

    }



}