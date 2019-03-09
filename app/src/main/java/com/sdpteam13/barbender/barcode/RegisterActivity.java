package com.sdpteam13.barbender.barcode;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sdpteam13.barbender.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, password, first, last;
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        first = (EditText) findViewById(R.id.first);
        last = (EditText) findViewById(R.id.last);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        findViewById(R.id.register).setOnClickListener(this);
    }


    private void registerUser(){
        String username = email.getText().toString().trim();
        String userpassword = password.getText().toString().trim();
        String firstname = first.getText().toString().trim();
        String lastname = last.getText().toString().trim();

        // Cases of incorrect input and helpful messages
        if (firstname.isEmpty()) {
            first.setError("Please enter your first name");
            first.requestFocus();
            return;
        }

        if (lastname.isEmpty()) {
            last.setError("Please enter your surname");
            last.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }

        if (userpassword.isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
            return;
        }
        //password restrictions for a minimumly secure password
        if (userpassword.length() < 6) {
            password.setError("Minimum length of new password is 6 characters");
            password.requestFocus();
            return;
        }
        //progress bar starts
        progressBar2.setVisibility(View.VISIBLE);

        //DANI - sign user up here and then send them to BarlistActivity

        //progress bar ends after successful sign up
        progressBar2.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        // clickable areas of UI layout
        switch (v.getId()){
            case R.id.register:
                registerUser();
                break;
        }
    }
}