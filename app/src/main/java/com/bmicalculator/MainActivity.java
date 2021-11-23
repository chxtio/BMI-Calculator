package com.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bmicalculator.Models.bmi;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    private bmi BMI;
    private Button bmi_button;
    private Button label_button;
    private Button message_button;
    EditText h;
    EditText w;
    int height;
    int weight;

//    public static final String URL = "http://webstrar99.fulton.asu.edu/page3/Service1.svc/calculateBMI?height=60&weight=156";
    public String URL;
    public static  final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        h = findViewById(R.id.height);
        w = findViewById(R.id.weight);

        // Assign button IDs to objects
        bmi_button = findViewById(R.id.button_bmi);
        label_button = findViewById(R.id.button_label);
        message_button = findViewById(R.id.button_message);

        bmi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    height = Integer.parseInt(h.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplication().getBaseContext(), "Error: Input height", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
//                    height = 0; //default fallback value
                }

                try {
                    weight = Integer.parseInt(w.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplication().getBaseContext(), "Error: Input weight", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
//                    weight = 0; //default fallback value
                }

                URL =  "http://webstrar99.fulton.asu.edu/page3/Service1.svc/calculateBMI?height=" + height + "&weight=" + weight;

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;
//                        Log.i(TAG, "jsonObject: " + jsonObject);
                        try {
                            BMI = new bmi(jsonObject);
                            Log.i(TAG, "bmi: " + BMI.getBMI());
                            Log.i(TAG, "bmi: " + BMI.getRisk());
                            Log.i(TAG, "bmi: " + BMI.getMore());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                        Log.d(TAG, "onFailure");
                        Log.d(TAG, "URL: " + URL);
                    }
                });
            }
        });

    }
}