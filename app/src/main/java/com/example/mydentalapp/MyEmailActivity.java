package com.example.mydentalapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MyEmailActivity extends AppCompatActivity {
    //Initialise Variables
    EditText etTo, etSubject, etMessage;
    Button btnSendMessage;
    ImageButton closeMail;
    String sEmail, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        //Assign variables
        etTo = findViewById(R.id.et_to);
        etSubject = findViewById(R.id.et_subject);
        etMessage = findViewById(R.id.et_message);
        btnSendMessage = findViewById(R.id.btnSend);
        closeMail = findViewById(R.id.closeMail);

        //Sender Credentials
        sEmail = "QsToDentist@gmail.com";
        sPassword = "dentist190422!";

        closeMail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(i);
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Initialise properties
                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                //Initialise Session
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sEmail, sPassword);
                    }
                });


                try {
                    //Initialise email content
                    Message message = new MimeMessage(session);
                    //Sender email
                    message.setFrom(new InternetAddress(sEmail));
                    //Recipient email
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(etTo.getText().toString().trim()));
                    //Email Subject
                    message.setSubject(etSubject.getText().toString().trim());
                    //Email Message
                    message.setText(etMessage.getText().toString().trim());

                    //Send Email
                    new SendMail().execute(message);

                } catch (MessagingException e){
                    e.printStackTrace();
                }

            }
        });


    }


    private class SendMail extends AsyncTask<Message,String,String> {
        //Initialise Progress Dialog
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //create and show progress dialog
            progressDialog = ProgressDialog.show(MyEmailActivity.this,
                    "Please Wait...", "Sending Mail..", true, false);
        }

        @Override
        protected String doInBackground(Message... messages) {
            try {
                //When success
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                //When error
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Dismiss Progress Dialog
            progressDialog.dismiss();
            if (s.equals("Success")){
                //When Success

                //Initialise Alert Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MyEmailActivity.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color = '#509324'>Success</font>"));
                builder.setMessage("Main sent successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //Clear all edittext
                        etTo.setText("");
                        etSubject.setText("");
                        etMessage.setText("");
                    }
                });
                //Show alert dialog
                builder.show();
            }else{
                //When error
                Toast.makeText(getApplicationContext(),"Something went wrong ?", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
