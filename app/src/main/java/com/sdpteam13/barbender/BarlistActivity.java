package com.sdpteam13.barbender;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog.Builder;
import java.util.ArrayList;
import android.app.AlertDialog;


public class BarlistActivity extends AppCompatActivity implements View.OnClickListener{


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
                        String task = String.valueOf(taskEditText.getText().toString().trim());
                        String confirmCode = "130319";
                        if (task.equals(confirmCode)){
                            Intent intent = new Intent(getApplicationContext(), SelectorActivity.class);
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
        Intent intent = new Intent(this, SelectorActivity.class);
        switch (v.getId()){
            case R.id.appleton:
                //startActivity(intent);
                showAddItemDialog(BarlistActivity.this);
                break;

            case R.id.forum:
                //startActivity(intent);
                showAddItemDialog(BarlistActivity.this);
                break;
        }
    }
}