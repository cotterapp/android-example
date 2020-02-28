package com.example.cotterexample;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.cotter.app.IdentityManager;
import com.cotter.app.TrustedDeviceHelper;
import com.cotter.app.TrustedDeviceResponse;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String resp = IdentityManager.handleResponse(getIntent());
        if (resp != null) {
            Log.i("Login Response: ", resp);
        }

        TrustedDeviceResponse trustDevResp = TrustedDeviceHelper.handleResponse(getIntent());
        if (trustDevResp != null) {
            Log.i("TrustedDeviceResponse: ", Integer.toString(trustDevResp.event.ID));
            Log.i("TrustedDevice String: ", trustDevResp.response);
        }

    }
}
