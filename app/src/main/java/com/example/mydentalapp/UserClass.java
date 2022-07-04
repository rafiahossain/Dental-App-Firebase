package com.example.mydentalapp;

import androidx.appcompat.app.AppCompatActivity;

public class UserClass {

    //This class is to send user data to firebase
    //Create string and integer variables

    public String emailadd, username;
    public int startweek;

    public UserClass(){
        //Empty public constructor, Does not return anything
        //To create an empty object of this class, To access the variables
    }

    public UserClass(String emailadd, String username, int startweek){
        this.emailadd = emailadd;
        this.username = username;
        this.startweek = startweek;

    }

//    public String getUserEmail(){
//        return emailadd;
//    }
//
//    public String getUserName(){
//        return username;
//    }
//
//    public int getStartWeek(){
//        return startweek;
//    }



}
