package com.nom.android;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText name,pwd, cpassword,emailx;
    Button btn_register, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        databaseHelper = new DatabaseHelper(this);
        name = (EditText)findViewById(R.id.name);
        pwd = (EditText)findViewById(R.id.password);
        emailx = (EditText)findViewById(R.id.email);
        cpassword = (EditText)findViewById(R.id.cpassword);
        btn_register = (Button)findViewById(R.id.btn_register);
        cancel = findViewById(R.id.cancel_button);
        btn_register.setEnabled(false);
        // bouton désactiver
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                btn_register.setEnabled(true);
                btn_register.setEnabled(!s.toString().isEmpty());

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), MainActivity.class);
                otherActivity.putExtra("name",""+name);
                startActivity(otherActivity);
                finish();
            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String password = pwd.getText().toString();
                String email = emailx.getText().toString();
                String confirm_password = cpassword.getText().toString();

                if(email.equals("") || username.equals("") || password.equals("") || confirm_password.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields Required", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.equals(confirm_password)){
                        Boolean checkEmail= databaseHelper.checkEmail(email);
                        if(checkEmail == true){
                            Boolean insert = databaseHelper.Insert(username, password,email);
                            if(insert == true){
                                Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                                name.setText("");
                                pwd.setText("");
                                cpassword.setText("");
                                emailx.setText("");
                                Intent otherActivity = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(otherActivity);
                                finish();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
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
