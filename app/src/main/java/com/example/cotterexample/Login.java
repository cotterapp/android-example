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
    }

    public void login(View view) {
        Cotter.newIdentity(this).login("EMAIL", this, Dashboard.class);
    }
    public void loginWithInput(View view) {
        EditText input = findViewById(R.id.input);
        Cotter.newIdentity(this).loginWithInput("EMAIL", input.getText().toString(), this, Dashboard.class);
    }
}
