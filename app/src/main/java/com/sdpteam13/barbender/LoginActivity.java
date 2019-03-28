package com.sdpteam13.barbender;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = LoginActivity.class.getSimpleName();
    private final String preferenceFile = "MyPrefsFile"; // for storing preferences
    EditText email, password;
    ProgressBar progressBar;


    public String getSHA(String password,byte[] salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

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

        try {
            //admin bypass for testing
            if (userEmail.equals("admin@a.c")) {
                startActivity(new Intent(this, BarlistActivity.class));
                return;
            }

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
            if (userPassword.length() < 10) {
                password.setError("Minimum length of password is 10 characters");
                password.requestFocus();
                return;
            }

            //progress bar starts
            progressBar.setVisibility(View.VISIBLE);

            //DANI - sign user in here if credentials work and send them to BarlistActivity
            byte[] salt;
            String encryptedPassword = "";
            String state = "";

            try {
                salt = Base64.decode(BackEndNStuff.getSalt("http://192.168.105.142/APP/LOGIN/", userEmail), 0);
                Log.d(TAG, "The salt is: " + salt);

                encryptedPassword = getSHA(userPassword, salt);
                Log.d(TAG, "the hashed password is:" + encryptedPassword);

                state = BackEndNStuff.logIn("http://192.168.105.142/APP/LOGIN/", userEmail, encryptedPassword);
                Log.d(TAG, "the state is:" + state);

                //progress bar ends after successful sign in
                progressBar.setVisibility(View.GONE);

                if (state.equals("invalid credentials") || state.equals("None")|| state.contains("500 Internal Server Error")) {
                    Toast.makeText(this, "Log-in failed", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.d(TAG, "successful login");
                    //saving current token in shared preferences
                    SharedPreferences settings = getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("token", state);
                    editor.apply();

                    Log.d(TAG,"the token is: "+ settings.getString("token","Nothing"));

                    Intent intent = new Intent(this, BarlistActivity.class);
                    startActivity(intent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e)
        {
            Toast.makeText(this,"Loggin failed!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        // clickable areas of UI layout
        switch (v.getId()){
            case R.id.login:
                signInUser();
                break;

            case R.id.register:
                //startActivity(new Intent(this, RegisterActivity.class));
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
