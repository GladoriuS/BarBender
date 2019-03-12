package com.sdpteam13.barbender;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private String token ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        token = getIntent().getStringExtra("token");
        TextView textView = (TextView) findViewById(R.id.textView6);
        TextView textView2 = (TextView) findViewById(R.id.textView7);

        ArrayList<String> order = getIntent().getStringArrayListExtra("order");
        int orderSize = order.size()-1;

        textView2.setText("Total: £"+order.get(0)+" for "+String.valueOf(orderSize)+" drinks");

        order.remove(0);
        StringBuilder builder = new StringBuilder();
        for (String details : order) {
            builder.append(details + "\n");
        }
        textView.setText(builder.toString());

        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // clickable areas of UI layout
        TextView textView2 = (TextView) findViewById(R.id.textView7);
        ArrayList<String> order = getIntent().getStringArrayListExtra("order");
        Intent intent;
        switch (v.getId()){
            case R.id.cancel:
                intent = new Intent(this, SelectorActivity.class);
                intent.putExtra("token",token);
                startActivity(intent);
                break;

            case R.id.confirm:
                System.out.println(order.size());
                if (order.size() == 0){
                    textView2.setText("You have not ordered any drinks, please go back and select a drink first.");
                    break;
                }
                else {
                    intent = new Intent(this, SeatActivity.class);
                    intent.putStringArrayListExtra("order", order);
                    intent.putExtra("token",token);
                    startActivity(intent);
                    break;
                }
        }
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, SelectorActivity.class));
    }
}
