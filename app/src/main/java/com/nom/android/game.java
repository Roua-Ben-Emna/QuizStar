package com.nom.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class game extends AppCompatActivity {

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Button easyb = (Button) findViewById(R.id.btn1);
        Button normal = (Button) findViewById(R.id.btn2);
        Button difficult = (Button) findViewById(R.id.btn3);
        Button cancel =(Button) findViewById(R.id.cancel_button);
        sp = getSharedPreferences("login",MODE_PRIVATE);


        easyb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), easy.class);

                startActivity(otherActivity);
                finish();
            }
        });
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), normal.class);

                startActivity(otherActivity);
                finish();
            }
        });

        difficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), difficult.class);

                startActivity(otherActivity);
                finish();
            }
        });
        // cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), MainActivity2.class);

                startActivity(otherActivity);
                finish();
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "Welcome Back", Toast.LENGTH_LONG).show();
        // ...
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResumed called", Toast.LENGTH_LONG).show();
        // ...
    }
}