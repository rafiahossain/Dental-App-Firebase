package com.example.mydentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    //ghh
    EditText message;
    ImageButton send;
    ListView chatListView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> messageall = new ArrayList<>();
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        message = (EditText)findViewById(R.id.message);
        send = (ImageButton)findViewById(R.id.send);
        chatListView = (ListView)findViewById(R.id.chatListView);

        String email = mAuth.getCurrentUser().getEmail();

        String dentistemail = "@qstodentist@gmail.com";
        setTitle("Chat with Dentist");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getText().toString().isEmpty()){
                    Toast.makeText(ChatActivity.this, "Please enter message", Toast.LENGTH_LONG).show();
                } else {
                    Map<String, Object> messageData = new HashMap<>();
                    messageData.put("sender", email);
                    messageData.put("receiver", dentistemail);
                }

            }
        });

    }

    //jhjhj

}