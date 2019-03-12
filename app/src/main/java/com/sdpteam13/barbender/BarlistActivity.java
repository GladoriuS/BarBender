package com.sdpteam13.barbender;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class BarlistActivity extends AppCompatActivity implements View.OnClickListener{

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barlist);

        token = getIntent().getStringExtra("token");
        findViewById(R.id.appleton).setOnClickListener(this);
        findViewById(R.id.forum).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // clickable areas of UI layout
        Intent intent = new Intent(this, SelectorActivity.class);
        intent.putExtra("token",token);
        switch (v.getId()){
            case R.id.appleton:
                startActivity(intent);
                break;

            case R.id.forum:
                startActivity(intent);
                break;
        }
    }
}