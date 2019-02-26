package com.sdpteam13.barbender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CustomiseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customise);

    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, SelectorActivity.class));
    }

    public void onCheckBoxClick(View view) {
        CheckBox rum = (CheckBox) findViewById(R.id.rum);
        CheckBox gin = (CheckBox) findViewById(R.id.gin);
        CheckBox whisky = (CheckBox) findViewById(R.id.whisky);
        CheckBox vodka = (CheckBox) findViewById(R.id.vodka);
        CheckBox coke = (CheckBox) findViewById(R.id.coke);
        CheckBox lemon = (CheckBox) findViewById(R.id.lemon);
        CheckBox orange = (CheckBox) findViewById(R.id.orange);
        CheckBox tonic = (CheckBox) findViewById(R.id.tonic);

        TextView myDisplay = (TextView) findViewById(R.id.textView8);

        switch(view.getId()) {

            case R.id.rum:

                gin.setChecked(false);
                whisky.setChecked(false);
                vodka.setChecked(false);

                break;

            case R.id.gin:

                whisky.setChecked(false);
                rum.setChecked(false);
                vodka.setChecked(false);

                break;

            case R.id.whisky:

                rum.setChecked(false);
                gin.setChecked(false);
                vodka.setChecked(false);

                break;

            case R.id.vodka:

                rum.setChecked(false);
                gin.setChecked(false);
                whisky.setChecked(false);

                break;

            case R.id.lemon:

                orange.setChecked(false);
                tonic.setChecked(false);
                coke.setChecked(false);

                break;

            case R.id.orange:

                lemon.setChecked(false);
                coke.setChecked(false);
                tonic.setChecked(false);

                break;

            case R.id.coke:

                lemon.setChecked(false);
                orange.setChecked(false);
                tonic.setChecked(false);

                break;

            case R.id.tonic:

                coke.setChecked(false);
                lemon.setChecked(false);
                orange.setChecked(false);

                break;
        }
        }

    }
