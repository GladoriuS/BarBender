package com.sdpteam13.barbender;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectorActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // clickable areas of UI layout
        Intent intent;
        switch (v.getId()){
            case R.id.button8:
                intent = new Intent(this, BrowseActivity.class);
                startActivity(intent);
                break;

            case R.id.button7:
                intent = new Intent(this, CustomiseActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, BarlistActivity.class);
        startActivity(intent);
    }
}
