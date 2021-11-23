package com.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
    private Button educate_button;
    EditText h;
    EditText w;
    int height;
    int weight;
    EditText labelMsg;
    EditText riskMsg;
    double bmi_output;
    String risk;

    public String URL;
    public static  final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        h = findViewById(R.id.height);
        w = findViewById(R.id.weight);
        labelMsg = findViewById(R.id.label_bmi_text);
        riskMsg = findViewById(R.id.message_risk_text);

        // Assign button IDs to objects
        bmi_button = findViewById(R.id.button_bmi);
        educate_button = findViewById(R.id.button_educate);

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
                }

                try {
                    weight = Integer.parseInt(w.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplication().getBaseContext(), "Error: Input weight", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
                }

                URL =  "http://webstrar99.fulton.asu.edu/page3/Service1.svc/calculateBMI?height=" + height + "&weight=" + weight;

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;

                        try {
                            BMI = new bmi(jsonObject);
                            bmi_output = BMI.getBMI();
                            risk = BMI.getRisk();
                            int color =  Color.parseColor(getColor(bmi_output));

                            Log.i(TAG, "bmi: " + bmi_output);
                            labelMsg.setText(String.valueOf(bmi_output));

                            Log.i(TAG, "bmi: " + risk);
                            riskMsg.setText(risk);

                            Log.i(TAG, "risk color: " + getColor(bmi_output));
                            labelMsg.setTextColor(color);
                            riskMsg.setTextColor(color);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        educate_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = BMI.getMore();
                                Log.i(TAG, "educate link: " + url);
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(browserIntent);
                            }
                        });
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

    public String getColor(double yourBMI) {
        if(yourBMI < 18)
            return "#FF0D26C8";
        else if(yourBMI <25)
            return "#FF1C8C08";
        else if(yourBMI < 30)
            return "#FF7010C5";
        else
            return "#FFEF0A24";
    }
}