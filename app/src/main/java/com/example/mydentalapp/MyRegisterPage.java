package com.example.mydentalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class MyRegisterPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText username, password, confirmpassword, email, startDate;
    private ProgressBar progressBar;

//    int code;//FOR THE EMAIL VERIFICATION PART

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_register_page);

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        startDate = findViewById(R.id.startDate);
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }

            private void updateCalendar() {
                String Format = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);
                startDate.setText(sdf.format(calendar.getTime()));
            }
        };

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MyRegisterPage.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

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
    }

    public void register(View view) {
        String emx = email.getText().toString().trim();
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confpass = confirmpassword.getText().toString().trim();
        String stDate = startDate.getText().toString().trim();

        //check if user exists or not
        if (emx.equals("") || user.equals("") || pass.equals("") || confpass.equals("") || stDate.equals(""))
            Toast.makeText(MyRegisterPage.this, "Please enter values for all field", Toast.LENGTH_SHORT).show();
        else {
            // Check if user entered a real valid email address
            if (!Patterns.EMAIL_ADDRESS.matcher(emx).matches()) {
                email.setError("Please provide valid email address");
                email.requestFocus();
                return;
            }

            // Check if password is at least 6 characters long
            if (pass.length() < 6) {
                password.setError("Password should be minimum 6 characters");
                password.requestFocus();
                return;
            }

            //check if user exists or not
            if (pass.equals(confpass)) {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(emx, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    UserClass u = new UserClass(emx, user, stDate);
                                    FirebaseDatabase.getInstance("https://dentalhealthapp-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(MyRegisterPage.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(getApplicationContext(), MyLoginActivity.class);
                                                startActivity(i);
                                            } else {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(MyRegisterPage.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(MyRegisterPage.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(MyRegisterPage.this, "Password does not match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void tologin(View view) {
        Intent i = new Intent(getApplicationContext(), MyLoginActivity.class);
        startActivity(i);
    }

}