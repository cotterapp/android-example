package com.example.cotterexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cotter.app.Cotter;
import com.cotter.app.CotterBiometricCallback;
import com.cotter.app.CotterMethodChecker;
import com.cotter.app.ScreenNames;
import com.cotter.app.Strings;

public class MainActivity extends AppCompatActivity {

    private TextView res;
    private TextView bioDefault;
    private TextView bioAvailable;
    private TextView bioEnrolled;
    private TextView bioEnrolledAny;
    private TextView pinDefault;
    private TextView pinEnrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If you want to use api keys in a file:
        // Add an apiKeys.xml inside your /res directory
        // and add the following:
        //
        // <?xml version="1.0" encoding="utf-8"?>
        // <resources>
        // <string name="user_id">User ID</string>
        // <string name="api_key_id">Api Key ID</string>
        // <string name="api_secret_key">Api Secret Key</string>
        // </resources>
        //
        // Then, uncomment the line below
        //
         Cotter.init(this.getApplicationContext(), "https://www.cotter.app/api/v0",
                 getString(R.string.user_id),
                 getString(R.string.api_key_id),
                 getString(R.string.api_secret_key));

        // For local development
//         Cotter.init(this.getApplicationContext(), "http://10.0.2.2:1234/api/v0",
//         getString(R.string.user_id), getString(R.string.api_key_id_test),
//         getString(R.string.api_secret_key_test));

        // Setting strings for Headers
        Cotter.strings.setHeaders(ScreenNames.PinEnrollmentEnterPin, "Aktivasi PIN");
        Cotter.strings.setHeaders(ScreenNames.PinEnrollmentReEnterPin, "Konfirmasi PIN");
        Cotter.strings.setHeaders(ScreenNames.PinVerification, "Verifikasi");

        // SETTING STRINGS FOR PIN ENROLLMENT
        // Entering Pin for the first time
        Cotter.strings.setPinEnrollmentEnterPinStrings(Strings.Title, "Buat PIN untuk keamanan akunmu");
        Cotter.strings.setPinEnrollmentEnterPinStrings(Strings.ShowPin, "Lihat PIN");
        Cotter.strings.setPinEnrollmentEnterPinStrings(Strings.HidePin, "Sembunyikan");
        Cotter.strings.setPinEnrollmentEnterPinStrings(Strings.ErrorCombination,
                "PIN terlalu mudah. Yuk buat PIN baru dengan kombinasi yang lebih sulit.");
        // Alert dialog when pressed "X"
        Cotter.strings.setPinEnrollmentEnterPinStrings(Strings.DialogTitle, "Yakin Tidak Mau Buat PIN Sekarang?");
        Cotter.strings.setPinEnrollmentEnterPinStrings(Strings.DialogSubtitle,
                "PIN ini diperlukan untuk keamanan akunmu, lho.");
        Cotter.strings.setPinEnrollmentEnterPinStrings(Strings.DialogPositiveButton, "Input PIN");
        Cotter.strings.setPinEnrollmentEnterPinStrings(Strings.DialogNegativeButton, "Lain Kali");
        // Re-entering PIN
        Cotter.strings.setPinEnrollmentReEnterPinStrings(Strings.Title, "Masukkan PIN sekali lagi untuk konfirmasi");
        Cotter.strings.setPinEnrollmentReEnterPinStrings(Strings.ShowPin, "Lihat PIN");
        Cotter.strings.setPinEnrollmentReEnterPinStrings(Strings.HidePin, "Sembunyikan");
        Cotter.strings.setPinEnrollmentReEnterPinStrings(Strings.ErrorNoMatch,
                "Kamu perlu memasukkan PIN yang sama seperti sebelumnya.");
        // Success Entering PIN
        Cotter.strings.setPinEnrollmentSuccessStrings(Strings.Title, "PIN Sukses Didaftarkan!");
        Cotter.strings.setPinEnrollmentSuccessStrings(Strings.Subtitle,
                "Mulai sekarang kamu bisa login dan konfirmasi transaksi meggunakan PIN");
        Cotter.strings.setPinEnrollmentSuccessStrings(Strings.ButtonText, "Selesai");
        Cotter.colors.setSuccessImage(R.drawable.check_green);
        // Biometric prompt
        Cotter.strings.setPinEnrollmentSuccessStrings(Strings.BiometricTitle, "Verifikasi");
        Cotter.strings.setPinEnrollmentSuccessStrings(Strings.BiometricSubtitle,
                "Sentuh sensor sidik jari untuk melanjutkan");
        Cotter.strings.setPinEnrollmentSuccessStrings(Strings.BiometricNegativeButton, "Batalkan");

        // SETTING STRINGS FOR PIN VERIFICATION
        // Pin Verification page
        Cotter.strings.setPinVerificationStrings(Strings.Title, "Masukkan PIN");
        Cotter.strings.setPinVerificationStrings(Strings.ShowPin, "Lihat PIN");
        Cotter.strings.setPinVerificationStrings(Strings.HidePin, "Sembunyikan");
        Cotter.strings.setPinVerificationStrings(Strings.ErrorInvalid, "PIN tidak sesuai.");
        // Biometric prompt
        Cotter.strings.setPinVerificationStrings(Strings.BiometricTitle, "Verifikasi");
        Cotter.strings.setPinVerificationStrings(Strings.BiometricSubtitle,
                "Sentuh sensor sidik jari untuk melanjutkan");
        Cotter.strings.setPinVerificationStrings(Strings.BiometricNegativeButton, "Input PIN");
        // Alert dialog when biometric signature is not valid
        Cotter.strings.setPinVerificationStrings(Strings.DialogTitle, "Biometric kamu tidak bisa diverifikasi");
        Cotter.strings.setPinVerificationStrings(Strings.DialogSubtitle, "Kamu boleh masukkan PIN atau coba lagi.");
        Cotter.strings.setPinVerificationStrings(Strings.DialogPositiveButton, "Input PIN");
        Cotter.strings.setPinVerificationStrings(Strings.DialogNegativeButton, "Coba lagi");

        // Setting colors
        Cotter.colors.setColorPrimary("#5E9051");
        Cotter.colors.setColorAccent("#53228B");
        Cotter.colors.setColorDanger("#B00020");

        // setting text views
        res = findViewById(R.id.result);
        bioDefault = findViewById(R.id.bio_default);
        bioAvailable = findViewById(R.id.bio_available);
        bioEnrolled = findViewById(R.id.bio_enrolled);
        bioEnrolledAny = findViewById(R.id.bio_enrolled_any);
        pinDefault = findViewById(R.id.pin_default);
        pinEnrolled = findViewById(R.id.pin_enrolled);

        updateMethods();

        CotterBiometricCallback cbc = new CotterBiometricCallback() {
            @Override
            public void onSuccess(boolean enrolled) {
                res.setText("Success" + enrolled);
                updateMethods();
            }

            @Override
            public void onCanceled() {
                res.setText("Canceled");
                updateMethods();
            }

            @Override
            public void onError(String s) {
                res.setText("Error" + s);
                updateMethods();
            }
        };
        Cotter.initBiometricSwitch(this, this, this, cbc);

        Cotter.methods.biometricAvailable(new CotterMethodChecker() {
            @Override
            public void onCheck(boolean b) {
                // Check if biometric available and enabled
                bioAvailable.setText("Biometric available: " + b);
            }
        });
    }

    public void openLogin(View view) {
        Intent in = new Intent(this, Login.class);
        startActivity(in);
    }

    public void openPinEnrollment(View view) {
        Cotter.PinEnrollment.startFlow(view, Dashboard.class, "PIN_ENROLLMENT");
    }

    public void openPinVerification(View view) {
        Cotter.PinVerification.startFlow(view, Dashboard.class, "LOGIN");
    }

    public void openPinChange(View view) {
        Cotter.PinChange.startFlow(view, Dashboard.class, "PIN_CHANGE");
    }

    public void disableBiometric(View view) {
        Cotter.biometricPrompt.disableBiometric();
    }

    public void enableBiometric(View view) {
        Cotter.biometricPrompt.enableBiometric();
    }

    public void updateMethods() {
        Cotter.methods.biometricEnrolled(new CotterMethodChecker() {
            @Override
            public void onCheck(boolean b) {
                // Check if biometric available and enabled
                bioEnrolled.setText("Biometric enrolled this device: " + b);
            }
        });
        Cotter.methods.biometricEnrolledAny(new CotterMethodChecker() {
            @Override
            public void onCheck(boolean b) {
                // Check if biometric available and enabled
                bioEnrolledAny.setText("Biometric enrolled any device: " + b);
            }
        });
        Cotter.methods.pinEnrolled(new CotterMethodChecker() {
            @Override
            public void onCheck(boolean b) {
                // Check if biometric available and enabled
                pinEnrolled.setText("Pin Enrolled: " + b);
            }
        });
        Cotter.methods.biometricDefault(new CotterMethodChecker() {
            @Override
            public void onCheck(boolean b) {
                // Check if biometric available and enabled
                bioDefault.setText("Biometric default: " + b);
            }
        });
        Cotter.methods.pinDefault(new CotterMethodChecker() {
            @Override
            public void onCheck(boolean b) {
                // Check if biometric available and enabled
                pinDefault.setText("Pin default: " + b);
            }
        });
    }

    public void goToTrustedDevice(View view) {
        Intent in = new Intent(this, TrustedDevice.class);
        startActivity(in);
    }
}
