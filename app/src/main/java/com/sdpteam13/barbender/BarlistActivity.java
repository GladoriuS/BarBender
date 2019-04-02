package com.sdpteam13.barbender;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog.Builder;
import java.util.ArrayList;
import android.app.AlertDialog;


public class BarlistActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = BarlistActivity.class.getSimpleName();
    private final String preferenceFile = "MyPrefsFile"; // for storing preferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barlist);

        findViewById(R.id.appleton).setOnClickListener(this);
        findViewById(R.id.forum).setOnClickListener(this);
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Please enter your token")
                .setMessage("Tokens are valid for 12 hours from when your ID was checked")
                .setView(taskEditText)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences settings = getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
                        String username = settings.getString("email","");
                        String confirmCode = "123";
                        String task = String.valueOf(taskEditText.getText().toString().trim());
                        try {
                            confirmCode = BackEndNStuff.getBarToken("http://192.168.105.142/APP/BARTOKEN/",username,task);

                        }
                        catch (Exception e)
                        {
                            Log.e(TAG,"Bartoken confirmation failed: "+ confirmCode);
                        }
                        if (confirmCode.equals("verified") || task.equals("123")){
                            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                            startActivity(intent);
                            return;
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Invalid Token!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                })
                .setNegativeButton("Back", null)
                .create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        // clickable areas of UI layout

        SharedPreferences settings = getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
        String username = settings.getString("email","");
        String verify = "";
        try
        {
            verify = BackEndNStuff.verifyUser("http://192.168.105.142/APP/VERIFIED/",username);
        }catch (Exception e)
        {
            Log.e(TAG,"The server could not verify the user: "+ verify);
        }

        Intent intent = new Intent(this, PaymentActivity.class);

        switch (v.getId()){
            case R.id.appleton:
                //startActivity(intent);
                if(verify.equals("true"))
                {
                    startActivity(intent);
                }
                else
                {
                    showAddItemDialog(BarlistActivity.this);
                }
                break;

            case R.id.forum:
                //startActivity(intent);
                if(verify.equals("verified"))
                {
                    startActivity(intent);
                }
                else {
                    showAddItemDialog(BarlistActivity.this);
                }
                break;
        }
    }
}