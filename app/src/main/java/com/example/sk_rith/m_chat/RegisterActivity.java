package com.example.sk_rith.m_chat;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerPassword, registerConfirmPassword;
    private EditText registerEmail;
    private Button btn_signup;

    //Firebase
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerEmail = (EditText)findViewById(R.id.register_email);
        registerPassword = (EditText) findViewById(R.id.register_password);
        registerConfirmPassword = (EditText) findViewById(R.id.register_confirmPassword);
        btn_signup = (Button) findViewById(R.id.register_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("signup ","clicked");
                if(isEmailValid(registerEmail.getText().toString()) && isPasswordValid(registerPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_SHORT);
                    createFirebaseUser();

                }


                if(!isEmailValid(registerEmail.getText().toString()))
                {
//                    new AlertDialog.Builder(getApplicationContext())
//                            .setTitle("Registration Error")
//                            .setMessage("Invalid Email")
//                            .setPositiveButton(android.R.string.ok,null)
//                            .setIcon(android.R.drawable.ic_dialog_alert).show();
                }
                if(!isPasswordValid(registerPassword.getText().toString())){

                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }
    private boolean isEmailValid(String email){
        return email.contains("@");
    }
    private boolean isPasswordValid(String password){
        String confirmPassword = registerConfirmPassword.getText().toString();
        return confirmPassword.equals(password) && password.length()>4;
    }
    private void createFirebaseUser(){
        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("Status", ""+task.isSuccessful());
                if(!task.isSuccessful()){
                    Log.d("Status",""+"failed to create user");
                }
            }
        });
    }

}
