package com.sdpteam13.barbender

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.*
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode
import com.sdpteam13.barbender.barcode.BarcodeCaptureActivity

class SeatActivity : AppCompatActivity() {

    private lateinit var mResultTextView: TextView
    private lateinit var order : ArrayList<String>
    private lateinit var token : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)

        mResultTextView = findViewById(R.id.result_textview)

        token = intent.getStringExtra("token")

        //get the order array from previous activity
        order = intent.getStringArrayListExtra("order")

        findViewById<ImageView>(R.id.scan_barcode_button).setOnClickListener {
            val intent = Intent(applicationContext, BarcodeCaptureActivity::class.java)
            startActivityForResult(intent, BARCODE_READER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    val barcode = data.getParcelableExtra<Barcode>(BarcodeCaptureActivity.BarcodeObject)
                    val p = barcode.cornerPoints
                    if (barcode.displayValue.equals("1") or barcode.displayValue.equals("2") or barcode.displayValue.equals("3")) {
                        for(i in order.indices)
                        {
                            when
                            {
                                order[i].equals("Martini") -> BackEndNStuff.post("http://192.168.105.142/APP/", barcode.displayValue, order[i], "None",token)
                                order[i].contains("&") -> BackEndNStuff.post("http://192.168.105.142/APP/", barcode.displayValue, order[i].split("&")[0], order[i].split("&")[1],token)
                                order[i].contains("*") -> BackEndNStuff.post("http://192.168.105.142/APP/", barcode.displayValue, order[i], "None",token)
                                order[i].contains(".") -> BackEndNStuff.post("http://192.168.105.142/APP/", barcode.displayValue, "None", order[i],token)


                            }
                        }
                        Toast.makeText(this, "Seat Confirmed!", Toast.LENGTH_SHORT).show()
                        mResultTextView.text = "Seat number: " + barcode.displayValue + "\n\n I won't be long now...please feel free to order again at any time!"
                        /** display string of the result: mResultTextView.text = barcode.displayValue */

                        /*The drinks to be ordered are stored in the array "order"
                        Each drink is stored in an index, eg order[0] is the first drink
                        order[1] is the second drink to be ordered etc.
                         */


                        //val intent2 = Intent(applicationContext, BackEndNStuff::class.java)
                        //intent2.putExtra("Seat", barcode.displayValue)
                        //startActivity(intent2)
                    }else
                        mResultTextView.setText("Not a valid QR code, please scan the code on your seat")

                } else
                    mResultTextView.setText("No barcode captured")
            } else
                Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                        CommonStatusCodes.getStatusCodeString(resultCode)))
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private val LOG_TAG = BackEndNStuff::class.java.simpleName
        private val BARCODE_READER_REQUEST_CODE = 1
    }
}