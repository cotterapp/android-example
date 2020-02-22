package com.example.cotterexample;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.cotter.app.IdentityManager;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String resp = IdentityManager.handleResponse(getIntent());
        Log.i("Login Response: ", resp);
    }
}
