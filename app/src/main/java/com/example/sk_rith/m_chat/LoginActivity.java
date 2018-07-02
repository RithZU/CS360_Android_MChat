package com.example.sk_rith.m_chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText emailView, passwordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailView = (EditText) findViewById(R.id.login_email);
        passwordView = (EditText) findViewById(R.id.login_password);

        //passwordView.setOnEditorActionListener();
    }
    public void signInExistingUser(View view){

    }
    public void registerNewUser(View view){
        Intent intent  = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
