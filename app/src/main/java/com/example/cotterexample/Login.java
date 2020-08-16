package com.example.cotterexample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cotter.app.Cotter;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Cotter.MAIN_SERVER_URL = "http://10.0.2.2:1234/api/v0";
//        Cotter.init(this, getString(R.string.api_key_id_test));
        Cotter.init(this, getString(R.string.api_key_id));
    }

    public void loginEmail(View view) {
        Cotter.newIdentity(this, "com.example.cotterexample://auth_callback").login("EMAIL", this, Dashboard.class);
    }
    public void loginEmailWithInput(View view) {
        EditText input = findViewById(R.id.input);
        Cotter.newIdentity(this, "com.example.cotterexample://auth_callback").loginWithInput("EMAIL", input.getText().toString(), this, Dashboard.class);
    }
    public void loginPhone(View view) {
        Cotter.newIdentity(this, "com.example.cotterexample://auth_callback").login("PHONE", this, Dashboard.class);
    }
    public void loginPhoneWithInput(View view) {
        EditText input = findViewById(R.id.inputPhone);
        Cotter.newIdentity(this, "com.example.cotterexample://auth_callback").loginWithInput("PHONE", input.getText().toString(), this, Dashboard.class);
    }
}
