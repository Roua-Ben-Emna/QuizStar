package com.nom.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.View;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    public ImageView start ;
    public TextView maxScore,welcome;
    public static  final String emailRef="emailRef";
    public static  final String nameRef="nameRef";
    public static  final String scoreRef="scoreRef";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        String name = sp.getString(nameRef,"");
        String maxSc = sp.getString(scoreRef,"");
        setContentView(R.layout.activity_main2);
        welcome = findViewById(R.id.welcome);
        maxScore = findViewById(R.id.maxScore);
        welcome.setText("Welcome \n" + name);
        maxScore.setText("Your Max Score  \n" + maxSc);
        Button logoutBtn = findViewById(R.id.idBtnLogout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences =getSharedPreferences("login",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        start = findViewById(R.id.abutton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), game.class);
                startActivity(otherActivity);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
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