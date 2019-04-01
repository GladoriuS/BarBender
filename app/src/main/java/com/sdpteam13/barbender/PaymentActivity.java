package com.sdpteam13.barbender;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        findViewById(R.id.newCard).setOnClickListener(this);
        findViewById(R.id.savedCard).setOnClickListener(this);
        findViewById(R.id.cash).setOnClickListener(this);
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Please enter your cards CVV for verification")
                .setMessage("Please enter the 3 digit CVV number found on the back of your card for verification")
                .setView(taskEditText)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText().toString().trim());
                        String confirmCode = "123";
                        if (task.equals(confirmCode)){
                            Intent intent = new Intent(getApplicationContext(), SelectorActivity.class);
                            startActivity(intent);
                            return;
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Incorrect CVV!", Toast.LENGTH_SHORT).show();
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
            case R.id.newCard:
                //startActivity(intent);
                Toast.makeText(getApplicationContext(), "Payment Getaway Not Yet Implemented", Toast.LENGTH_SHORT).show();;
                break;

            case R.id.savedCard:
                //startActivity(intent);
                showAddItemDialog(PaymentActivity.this);
                break;

            case R.id.cash:
                //startActivity(intent);
                startActivity(new Intent(getApplicationContext(), SelectorActivity.class));
                break;
        }
    }

}
