package com.nom.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class congratulations extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    public static  final String emailRef="emailRef";
    public static  final String nameRef="nameRef";
    public static  final String scoreRef="scoreRef";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congrat);
        databaseHelper = new DatabaseHelper(this);
        TextView wrong = findViewById(R.id.wrong);
        TextView correct = findViewById(R.id.correct);
        TextView score = findViewById(R.id.score);
        TextView titre = findViewById(R.id.titre);
        ImageView img = findViewById(R.id.imageView2);
        Button btnRestart = findViewById(R.id.btnRestart);
        String wrongAnswer = getIntent().getExtras().getString("wrongAnswer");
        String correctAnswer = getIntent().getExtras().getString("correctAnswer");
        sp = getSharedPreferences("login",MODE_PRIVATE);
        String name = sp.getString(nameRef,"");
        String maxSc = sp.getString(scoreRef,"");
        String email = sp.getString(emailRef,"");
        wrong.setText(wrongAnswer);
        correct.setText(correctAnswer);
        score.setText(correctAnswer + "/10");
        int max=Integer.parseInt(maxSc);
        int sc=Integer.parseInt(correctAnswer);
        if(max<sc){
             databaseHelper.updateScore(email,correctAnswer);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(scoreRef,maxSc);
            editor.commit();
        }
        int n = Integer.parseInt(correctAnswer);
        if (n < 6) {
            titre.setText("Ooops " + name + " !!");
            img.setImageResource(R.drawable.oups);
       } else {
            titre.setText("Great Job " + name + " !!");
            img.setImageResource(R.drawable.bravo);
        }

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), game.class);
                otherActivity.putExtra("name", "" + name);
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
