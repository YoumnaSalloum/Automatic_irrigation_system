package com.example.autoirrigationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginAct extends AppCompatActivity {
    Button buttonLogin;
    EditText Editpass;
    EditText Edituser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        buttonLogin = (Button) findViewById(R.id.button_login);
        Editpass = (EditText) findViewById(R.id.edittext_password);
        Edituser = (EditText) findViewById(R.id.edittext_username);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        String ValuePassword =  Editpass.getText().toString();
        String ValueUserName = Edituser.getText().toString();

        Log.i("pass",ValuePassword);
        Log.i("pass",ValueUserName);
        if (ValuePassword.equals("12345") && ValueUserName.equals("aabbc")) {
            Toast.makeText(this, "username and password matched..", Toast.LENGTH_SHORT).show();
            Intent HomePage = new Intent(LoginAct.this,HomePage.class);
            startActivity(HomePage);

        } else
            Toast.makeText(this, "username and password do not matched.. !!!", Toast.LENGTH_SHORT).show();


    }
}
