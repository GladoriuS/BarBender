package com.sdpteam13.barbender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = RegisterActivity.class.getSimpleName();
    EditText email, password, first, last;
    ProgressBar progressBar2;

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
        if (userpassword.length() < 10) {
            password.setError("Minimum length of new password is 10 characters");
            password.requestFocus();
            return;
        }
        //progress bar starts
        progressBar2.setVisibility(View.VISIBLE);

        //DANI - sign user up here and then send them to BarlistActivity
        byte[] salt;
        String encryptedPassword = "";
        String state = "";

        try {
            salt = getSalt();
            encryptedPassword = getSHA(userpassword,salt);
            Log.d(TAG,"The reg enrypted password is: " + encryptedPassword);
            Log.d(TAG,"The reg salt is: " + Base64.encodeToString(salt,0));
            state = BackEndNStuff.register("http://192.168.105.142/APP/REGISTER/",username,encryptedPassword,Base64.encodeToString(salt,0));
            Log.d(TAG,"The current state is: " + state);

            startActivity(new Intent(this,LoginActivity.class));


        }catch (NoSuchAlgorithmException e)
        {
            Log.e(TAG,"Generating the salt failed");
        }catch (IOException e)
        {
            Log.e(TAG,"Registering failed");
        }




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