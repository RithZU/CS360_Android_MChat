package com.example.sk_rith.m_chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailView, passwordView;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailView = (EditText) findViewById(R.id.login_email);
        passwordView = (EditText) findViewById(R.id.login_password);
        firebaseAuth = FirebaseAuth.getInstance();
        //passwordView.setOnEditorActionListener();
    }
    public void signInExistingUser(View view){
        attemptToLogin();
    }
    public void registerNewUser(View view){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    private void attemptToLogin(){
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if(email.equals("") || password.equals("")) return;
        Toast.makeText(this,"Attempting to login",Toast.LENGTH_SHORT).show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Login","Signing in " + task.isSuccessful());
                if(task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                    finish();
                    startActivity(intent);
                }
                else {
                    Log.d("Login", "Failed to login" + task.getException());
                    showErrorDialog("Please enter a correct email or password");
                }

            }
        });
    }
    private void showErrorDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("Invalid account")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
