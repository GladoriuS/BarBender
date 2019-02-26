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


    //ArrayList<Order> orders = new ArrayList<>();
    //Order order = new Order();

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
        startActivity(new Intent(this, SelectorActivity.class));
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
                //order.setSpirit("rum");
                //order.addComponent("rum");

                gin.setChecked(false);
                whisky.setChecked(false);
                vodka.setChecked(false);

                break;

            case R.id.gin:

                order.set(1,"Gin");
                //order.setSpirit("gin");
                //order.addComponent("gin");

                whisky.setChecked(false);
                rum.setChecked(false);
                vodka.setChecked(false);

                break;

            case R.id.whisky:

                order.set(1,"Whisky");
                //order.setSpirit("whisky");
                //order.addComponent("whisky");

                rum.setChecked(false);
                gin.setChecked(false);
                vodka.setChecked(false);

                break;

            case R.id.vodka:

                order.set(1,"Vodka");
                //order.setSpirit("vodka");
                //order.addComponent("rum");

                rum.setChecked(false);
                gin.setChecked(false);
                whisky.setChecked(false);

                break;

            case R.id.lemon:

                order.set(2,"Lemon");
                //order.setMixer("lemon");

                orange.setChecked(false);
                tonic.setChecked(false);
                coke.setChecked(false);

                break;

            case R.id.orange:

                order.set(2,"Orange");
                //order.setMixer("orange");

                lemon.setChecked(false);
                coke.setChecked(false);
                tonic.setChecked(false);

                break;

            case R.id.coke:

                order.set(2,"Coke");
                //order.setMixer("coke");

                lemon.setChecked(false);
                orange.setChecked(false);
                tonic.setChecked(false);

                break;

            case R.id.tonic:

                order.set(2,"Tonic");
                //order.setMixer("tonic");

                coke.setChecked(false);
                lemon.setChecked(false);
                orange.setChecked(false);

                break;

        }
    }

    public void onClick(View v) {
        if (!order.get(1).isEmpty() && !order.get(2).isEmpty() && !drinkName.getText().toString().isEmpty()) {
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
            /*Log.d(TAG, "Intent should start");
            Log.d(TAG, "Sie of list: "+ order.size());
            System.out.println(order);
            if(order.get(1).isEmpty() && order.get(2).isEmpty())
            {
                Toast.makeText(this,"You did not choose anything",Toast.LENGTH_SHORT);
            }
            else
            {
                if(order.get(2).isEmpty())
                    order.remove(2);
                if(order.get(1).isEmpty())
                    order.remove(1);
                Intent intent = new Intent(this, OrderActivity.class);
                intent.putStringArrayListExtra("order", order);
                intent.putExtra("activity","customize");
                startActivity(intent);

            }*/
            Toast.makeText(this,"You should complete your drink!",Toast.LENGTH_SHORT).show();
        }
    }


}
