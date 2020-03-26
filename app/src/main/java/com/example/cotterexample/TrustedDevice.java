package com.example.cotterexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cotter.app.Callback;
import com.cotter.app.Cotter;
import com.cotter.app.CotterMethodChecker;
import com.cotter.app.Strings;
import com.cotter.app.TrustedDeviceHelper;

import org.json.JSONObject;

public class TrustedDevice extends AppCompatActivity {

    private TextView thisDeviceEnrolled;
    private TextView anyDeviceEnrolled;
    private TextView trustedDeviceDefault;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_device);

        EditText thisDev = findViewById(R.id.this_device);
        thisDev.setText(TrustedDeviceHelper.getPublicKey(this) + ":" + TrustedDeviceHelper.getAlgorithm(this));

        thisDeviceEnrolled = findViewById(R.id.this_device_enrolled);
        anyDeviceEnrolled = findViewById(R.id.any_device_enrolled);
        trustedDeviceDefault = findViewById(R.id.trusted_device_default);


        // Customization
        Cotter.strings.setApproveRequestStrings(Strings.Title, "ARE you trying to sign in?");
        Cotter.strings.setApproveRequestStrings(Strings.Subtitle, "SOMEONE is trying to sign in to your account from another device.");
        Cotter.strings.setApproveRequestStrings(Strings.ButtonYes, "YES");
        Cotter.strings.setApproveRequestStrings(Strings.ButtonNo, "NO");
        Cotter.colors.setLogo(R.drawable.logo);

        // RequestAuth
        Cotter.strings.setRequestAuthStrings(Strings.DialogTitle, "APPROVE this login from your phone");
        Cotter.strings.setRequestAuthStrings(Strings.DialogSubtitle, "A NOTIFICATION is sent to your trusted device to confirm it's you.");
        Cotter.strings.setRequestAuthErrorStrings(Strings.DialogTitle, "SOMETHING went wrong");
        Cotter.strings.setRequestAuthErrorStrings(Strings.DialogSubtitle, "WE'RE UNABLE to confirm that it's you. Please try again.");
//
//        // Success Sheet
        Cotter.strings.setSuccessSheetStrings(Strings.DialogTitle, "SUCCESS Registering New Device");
        Cotter.strings.setSuccessSheetStrings(Strings.DialogSubtitle, "YOU CAN now use your new device to access your account without approval.");
        Cotter.strings.setSuccessSheetErrorStrings(Strings.DialogTitle, "UNABLE to Register New Device");
        Cotter.strings.setSuccessSheetErrorStrings(Strings.DialogSubtitle, "PLEASE try again.");
//
//        // QR Code Show
        Cotter.strings.setQRCodeShowStrings(Strings.Title, "REGISTER this Device");
        Cotter.strings.setQRCodeShowStrings(Strings.Subtitle, "PLEASE scan this QR Code from a Trusted Device.");
        Cotter.strings.setQRCodeShowErrorStrings(Strings.Title, "SOMETHING went wrong");
        Cotter.strings.setQRCodeShowErrorStrings(Strings.Subtitle, "THE REQUEST timed out. Please try again.");

        updateMethods();
    }

    public void enrollDevice(View v) {
        TrustedDeviceHelper.enrollDevice(this, new Callback() {
            @Override
            public void onSuccess(JSONObject result) {
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Trusted enrollDevice", result.toString() );
                updateMethods();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                updateMethods();
            }
        });
    }
    public void requestAuth(View v) {
        TrustedDeviceHelper.requestAuth(this, "LOGIN", this, Dashboard.class, new Callback() {
            @Override
            public void onSuccess(JSONObject result) {
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Trusted requestAuth", result.toString() );
                updateMethods();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                updateMethods();
            }
        });
    }
    public void checkNewRequest(View v) {
        TrustedDeviceHelper.getNewEvent(this, this);
        updateMethods();
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

    public void openBarcodeScanner(View view) {
        TrustedDeviceHelper.startEnrollOtherDeviceAsTrusted(this, this);
    }
    public void openBarcodeShower(View view) {
        TrustedDeviceHelper.startEnrollThisDeviceAsTrusted(this, this);
    }
}
