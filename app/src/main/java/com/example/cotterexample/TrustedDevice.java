package com.example.cotterexample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cotter.app.Cotter;
import com.cotter.app.CotterMethodChecker;
import com.cotter.app.TrustedDeviceHelper;

public class TrustedDevice extends AppCompatActivity {

    private TextView thisDeviceEnrolled;
    private TextView anyDeviceEnrolled;
    private TextView trustedDeviceDefault;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_device);

        EditText thisDev = findViewById(R.id.this_device);
        thisDev.setText(TrustedDeviceHelper.getPublicKey() + ":" + TrustedDeviceHelper.getAlgorithm());

        thisDeviceEnrolled = findViewById(R.id.this_device_enrolled);
        anyDeviceEnrolled = findViewById(R.id.any_device_enrolled);
        trustedDeviceDefault = findViewById(R.id.trusted_device_default);

        updateMethods();
    }

    public void enrollDevice(View v) {
        TrustedDeviceHelper.enrollDevice(this);
        updateMethods();
    }
    public void authorizeDevice(View v) {
        TrustedDeviceHelper.authorizeDevice(this, "LOGIN");
        updateMethods();
    }
    public void requestAuth(View v) {
        TrustedDeviceHelper.requestAuthFromNonTrusted(this, "LOGIN");
        updateMethods();
    }
    public void checkNewRequest(View v) {
        TrustedDeviceHelper.getNewEvent(this, this);
        updateMethods();
    }
    public void removeDevice(View v) {
        TrustedDeviceHelper.removeDevice(this);
        updateMethods();
    }
    public void enrollOtherDevice(View v) {
        EditText otherDev = findViewById(R.id.other_device);
        TrustedDeviceHelper.enrollOtherDevice(this, otherDev.getText().toString());
        updateMethods();
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
