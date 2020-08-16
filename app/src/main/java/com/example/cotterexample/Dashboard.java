package com.example.cotterexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cotter.app.Callback;
import com.cotter.app.Cotter;
import com.cotter.app.CotterMethodChecker;
import com.cotter.app.IdentityManager;
import com.cotter.app.TrustedDeviceHelper;
import com.cotter.app.TrustedDeviceResponse;

import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {

    private TextView thisDeviceEnrolled;
    private TextView anyDeviceEnrolled;
    private TextView trustedDeviceDefault;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        thisDeviceEnrolled = findViewById(R.id.this_device_enrolled);
        anyDeviceEnrolled = findViewById(R.id.any_device_enrolled);
        trustedDeviceDefault = findViewById(R.id.trusted_device_default);


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

    public void checkNewRequest(View v) {
        TrustedDeviceHelper.getNewEvent(this, this);
    }
    public void removeDevice(View v) {
        TrustedDeviceHelper.removeDevice(this, new Callback() {
            @Override
            public void onSuccess(JSONObject result) {
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Trusted removeDevice", result.toString() );
                updateMethods();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                updateMethods();
            }
        });
    }

    public void openBarcodeScanner(View view) {
        TrustedDeviceHelper.startEnrollOtherDeviceAsTrusted(this, this);
    }
    public void openBarcodeShower(View view) {
        TrustedDeviceHelper.startEnrollThisDeviceAsTrusted(this, this);
    }

    public void updateMethods() {
        Cotter.methods.trustedDeviceEnrolled(new CotterMethodChecker() {
            @Override
            public void onCheck(boolean b) {
                // Check if TrustedDevice available and enabled
                thisDeviceEnrolled.setText("TrustedDevice enrolled this device: " + b);
            }
        });
        Cotter.methods.trustedDeviceEnrolledAny(new CotterMethodChecker() {
            @Override
            public void onCheck(boolean b) {
                // Check if TrustedDevice available and enabled
                anyDeviceEnrolled.setText("TrustedDevice enrolled any device: " + b);
            }
        });
        Cotter.methods.trustedDeviceDefault(new CotterMethodChecker() {
            @Override
            public void onCheck(boolean b) {
                // Check if TrustedDevice available and enabled
                trustedDeviceDefault.setText("TrustedDevice default: " + b);
            }
        });
    }

}
