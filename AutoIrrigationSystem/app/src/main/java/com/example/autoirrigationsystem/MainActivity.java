package com.example.autoirrigationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=3000;
    //After completion of 2000 ms, the next activity will get started.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //this will bind your MainActivity.class file with activity_main.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent objIntent=new Intent(MainActivity.this,
                        LoginAct.class);
                //Intent is used to switch from one activity to another.

                startActivity(objIntent);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }
}
