package com.sdpteam13.barbender;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    EditText email, password;
    ProgressBar progressBar;


    //Add salt
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }


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
        byte[] salt;
        String encryptedPassword = "";
        String state = "";
        try {
            salt = BackEndNStuff.getSalt("http://192.168.105.142/APP/",userEmail).getBytes();
            Log.d(TAG,"The salt is: " + salt);

            encryptedPassword = getSHA(userPassword,salt);
            state = BackEndNStuff.logIn("http://192.168.105.142/APP/", encryptedPassword);

            if(state.equals("Success"))
            {
                startActivity(new Intent(this, BarlistActivity.class));
            }
            else
            {
                Toast.makeText(this,"Log-in failed",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }

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
