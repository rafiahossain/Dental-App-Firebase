package com.example.mydentalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.mydentalapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class MyRegisterPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView banner;
    private EditText username, password, confirmpassword, emailx, START_WEEK;
    private Button register, login;
    private ProgressBar progressBar;

    int code;//FOR THE EMAIL VERIFICATION PART

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_register_page);

        banner = (TextView)findViewById(R.id.banner);
        emailx = (EditText)findViewById(R.id.email);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        confirmpassword = (EditText)findViewById(R.id.confirmpassword);
        START_WEEK = (EditText)findViewById(R.id.st_week);
        register = (Button)findViewById(R.id.btnregister);
        login = (Button)findViewById(R.id.btnlogin);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();


        //
        //
        //
//        Random random = new Random();
//        code = random.nextInt(8999)+1000;
//        EditText emailtxt = findViewById(R.id.email);
//        //find a host like 000webhost to place sendEmail.php in
//        String url = "";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new Stringrequest(Request.Method.POST, url, new Response.Listener<String>(){
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(MyRegisterPage.this, response, Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error){
//                Toast.makeText(MyRegisterPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("email", emailtxt.getText().toString());
//                params.put("code", String.valueOf(code));
//                return params;
//            }
//
//        };
//
//        requestQueue.add(stringRequest);

        //stopped at 9.34
        //
        //

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emx = emailx.getText().toString().trim();
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String confpass = confirmpassword.getText().toString().trim();
                String tempStr = START_WEEK.getText().toString().trim();
                int START_WEEK_PREGNANCY = Integer.parseInt(tempStr);

                //check if user exists or not
                if(emx.equals("")||user.equals("")||pass.equals("")||confpass.equals("")||tempStr.equals(""))
                    Toast.makeText(MyRegisterPage.this, "Please enter values for all field", Toast.LENGTH_SHORT).show();
                else{
                    // Check if user entered a real valid email address
                    if(!Patterns.EMAIL_ADDRESS.matcher(emx).matches()){
                        emailx.setError("Please provide valid email address");
                        emailx.requestFocus();
                        return;
                    }

                    // Check if password is at least 6 characters long
                    if(pass.length() < 6){
                        password.setError("Password should be minimum 6 characters");
                        password.requestFocus();
                        return;
                    }

                    //check if user exists or not
                    if(pass.equals(confpass)){
                        progressBar.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(emx, pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            UserClass u = new UserClass(emx, user, START_WEEK_PREGNANCY);
                                            FirebaseDatabase.getInstance("https://dentalhealthapp-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(MyRegisterPage.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(getApplicationContext(), MyLoginActivity.class);
                                                        startActivity(i);
                                                    }else{
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(MyRegisterPage.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }else{
                                            Toast.makeText(MyRegisterPage.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else{
                        Toast.makeText(MyRegisterPage.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyLoginActivity.class);
                startActivity(i);
            }
        });

    }

}