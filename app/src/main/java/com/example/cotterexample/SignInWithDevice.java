package com.example.cotterexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cotter.app.Callback;
import com.cotter.app.Cotter;

import org.json.JSONObject;

public class SignInWithDevice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_with_device);

//        Cotter.MAIN_SERVER_URL = "http://10.0.2.2:1234/api/v0";
//        Cotter.init(this, getString(R.string.api_key_id_test));
        Cotter.init(this, getString(R.string.api_key_id));
    }


    public void signUpWithDevice(View v) {
        EditText input = findViewById(R.id.input);
        Cotter.signUpWithDevice(this, input.getText().toString(), new Callback() {
            @Override
            public void onSuccess(JSONObject result) {
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Trusted enrollDevice", result.toString() );
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signInWithDevice(View v) {
        EditText input = findViewById(R.id.input);
        Cotter.signInWithDevice(this, input.getText().toString(), this, Dashboard.class, new Callback() {
            @Override
            public void onSuccess(JSONObject result) {
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Trusted enrollDevice", result.toString() );
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
