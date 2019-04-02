package com.sdpteam13.barbender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomiseActivity extends AppCompatActivity {
    public static final String TAG = CustomiseActivity.class.getSimpleName();

    ArrayList<String> order = new ArrayList<>();
    EditText drinkName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customise);
        drinkName =  findViewById(R.id.editText);
        order.add("0");
        order.add("");
        order.add("");


    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, SelectorActivity.class);
        startActivity(intent);
    }

    public void onCheckBoxClick(View view)
    {
        CheckBox rum = (CheckBox) findViewById(R.id.rum);
        CheckBox gin = (CheckBox) findViewById(R.id.gin);
        CheckBox whisky = (CheckBox) findViewById(R.id.whisky);
        CheckBox vodka = (CheckBox) findViewById(R.id.vodka);
        CheckBox coke = (CheckBox) findViewById(R.id.coke);
        CheckBox lemon = (CheckBox) findViewById(R.id.lemon);
        CheckBox orange = (CheckBox) findViewById(R.id.orange);
        CheckBox tonic = (CheckBox) findViewById(R.id.tonic);

        switch(view.getId())
        {

            case R.id.rum:

                order.set(1,"Rum");

                gin.setChecked(false);
                whisky.setChecked(false);
                vodka.setChecked(false);

                break;

            case R.id.gin:

                order.set(1,"Gin");

                whisky.setChecked(false);
                rum.setChecked(false);
                vodka.setChecked(false);

                break;

            case R.id.whisky:

                order.set(1,"Whisky");

                rum.setChecked(false);
                gin.setChecked(false);
                vodka.setChecked(false);

                break;

            case R.id.vodka:

                order.set(1,"Vodka");

                rum.setChecked(false);
                gin.setChecked(false);
                whisky.setChecked(false);

                break;

            case R.id.lemon:

                order.set(2,"Lemon");

                orange.setChecked(false);
                tonic.setChecked(false);
                coke.setChecked(false);

                break;

            case R.id.orange:

                order.set(2,"Orange");

                lemon.setChecked(false);
                coke.setChecked(false);
                tonic.setChecked(false);

                break;

            case R.id.coke:

                order.set(2,"Coke");

                lemon.setChecked(false);
                orange.setChecked(false);
                tonic.setChecked(false);

                break;

            case R.id.tonic:

                order.set(2,"Tonic");

                coke.setChecked(false);
                lemon.setChecked(false);
                orange.setChecked(false);

                break;

        }
    }

    public void onClick(View v) {
        if (!order.get(1).isEmpty() && !order.get(2).isEmpty() ) {
            Log.d(TAG, "Intent should start");
            System.out.println(order);
            order.set(1, drinkName.getText().toString() +" (" + (order.get(1) + "&" + order.get(2)) + ")");
            order.remove(2);
            order.set(0,"5");
            Intent intent = new Intent(this, OrderActivity.class);
            intent.putStringArrayListExtra("order", order);
            startActivity(intent);
        }
        else {
            Log.d(TAG, "Intent should start");
            Log.d(TAG, "Sie of list: "+ order.size());
            System.out.println(order);
            if(order.get(1).isEmpty() && order.get(2).isEmpty())
            {
                Toast.makeText(this,"You did not choose anything",Toast.LENGTH_SHORT);
            }
            else
            {
                if(order.get(2).isEmpty())
                {
                    order.remove(2);
                    order.set(1,order.get(1) + "*");
                    Log.d(TAG, "Only spirit is:" + order.get(1));
                    order.set(0,"5");
                }
                if(order.get(1).isEmpty())
                {
                    order.remove(1);
                    order.set(1,order.get(1) + ".");
                    Log.d(TAG, "Only mixer is:" + order.get(1));
                    order.set(0,"5");
                }
                order.set(1, drinkName.getText().toString() +" (" + order.get(1)+ ")");
                Intent intent = new Intent(this, OrderActivity.class);
                intent.putStringArrayListExtra("order", order);
                startActivity(intent);

            }
        }
    }


}
