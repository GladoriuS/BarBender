package com.sdpteam13.barbender;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> order = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        findViewById(R.id.martini).setOnClickListener(this);
        findViewById(R.id.gintonic).setOnClickListener(this);
        findViewById(R.id.textView3).setOnClickListener(this);
        order.add("0");
    }

    @Override
    public void onClick(View v) {
        // clickable areas of UI layout
        TextView textView = (TextView) findViewById(R.id.textView4);
        switch (v.getId()){
            case R.id.martini:
                order.add("Martini");
                int sum = 0;
                sum = Integer.parseInt(order.get(0))+5;
                order.set(0,String.valueOf(sum));
                textView.setText("£"+order.get(0));
                Toast.makeText(getApplicationContext(), "Drink added!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.gintonic:
                order.add("Gin&Tonic");
                int sum2 = 0;
                sum2 = Integer.parseInt(order.get(0))+5;
                order.set(0,String.valueOf(sum2));
                textView.setText("£"+order.get(0));
                Toast.makeText(getApplicationContext(), "Drink added!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.textView3:
                System.out.println(order);
                Intent intent = new Intent(this, OrderActivity.class);
                intent.putStringArrayListExtra("order", order);
                startActivity(intent);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, SelectorActivity.class);
        startActivity(intent);
    }
}
