package com.sdpteam13.barbender;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sdpteam13.barbender.barcode.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText email, password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
    }

    private void signInUser(){
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        // Cases of incorrect input and helpful messages
        if (userEmail.isEmpty()) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
            return;
        }
        //password restrictions for a minimumly secure password
        if (userPassword.length() < 6) {
            password.setError("Minimum length of password is 6 characters");
            password.requestFocus();
            return;
        }
        //progress bar starts
        progressBar.setVisibility(View.VISIBLE);

        //DANI - sign user in here if credentials work and send them to BarlistActivity

        //progress bar ends after successful sign in
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        // clickable areas of UI layout
        switch (v.getId()){
            case R.id.login:
                signInUser();
                break;

            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
