package com.nom.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;



public class MainActivity extends AppCompatActivity   {
    DatabaseHelper databaseHelper;
    private EditText email,pwd;

    public static  final String emailRef="emailRef";
    public static  final String nameRef="nameRef";
    public static  final String scoreRef="scoreRef";

    SharedPreferences sp;




    @Override
    protected void onCreate(Bundle savedInstanceState)

    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        email  = findViewById(R.id.email);
        pwd  = findViewById(R.id.password);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);
        sp = getSharedPreferences("login",MODE_PRIVATE);

         if(sp.contains(emailRef)){
             Intent otherActivity = new Intent(getApplicationContext(), MainActivity2.class);
             startActivity(otherActivity);
         }
        btn_login.setEnabled(false);
        // bouton désactiver
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                btn_login.setEnabled(true);
                btn_login.setEnabled(!s.toString().isEmpty());


            }
        });
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                btn_login.setEnabled(true);
                btn_login.setEnabled(!s.toString().isEmpty());


            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLogin = email.getText().toString();
                String password = pwd.getText().toString();

                Boolean checklogin = databaseHelper.CheckLogin(emailLogin, password);
                if(checklogin == true){
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    String name= databaseHelper.recoverName(emailLogin);
                    String maxSc= databaseHelper.recoverScore(emailLogin);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(emailRef,emailLogin);
                    editor.putString(nameRef,name);
                    editor.putString(scoreRef,maxSc);
                    editor.commit();
                    Intent otherActivity = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(otherActivity);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), Register.class);
                startActivity(otherActivity);
                finish();
            }
        });

    }
    }
            /*  Les Intents sont des messages asynchrones qui
            permettent aux composants d'une application de
            demander des fonctionnalités à d'autres
            composants Android Les Intents vous permettent
            d'interagir avec vos propres composants ou des
            composants d'autres applications ..(une activité
            peut démarrer une autre activité
            Les
            Intents sont des objets de type
            android content Intent
            le
            code peut les envoyer au système Android pour
            définir les composants que vous ciblez
            la
            méthode startActivity de la classe Activity
            d'Android permet de démarrer une autre activité  */