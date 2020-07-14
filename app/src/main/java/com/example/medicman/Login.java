package com.example.medicman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicman.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etPass;

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.et_email_login);
        etPass = findViewById(R.id.et_pass_login);
        btnLogin = findViewById(R.id.btn_login_login);
    }

    public void login(final View view) {
        String memail,mpass;

        memail=etEmail.getText().toString();
        mpass=etPass.getText().toString();

        if (!memail.equals("") && !mpass.equals("")){

            btnLogin.setVisibility(view.INVISIBLE);

            mAuth.signInWithEmailAndPassword(memail,mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent i = new Intent(getApplicationContext(),Home.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(i);
                    }else {
                        String error=task.getException().getMessage();
                        Toast.makeText(Login.this, "Error:" + error, Toast.LENGTH_SHORT).show();
                    }
                    btnLogin.setVisibility(view.VISIBLE);
                }
            });
        }else {
            Toast.makeText(this, "Please fill the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void forgotPass(View view) {
        String email;

        email=etEmail.getText().toString();
        if (!email.equals("")){
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Password Reset Link is Sent to Registered Email Address", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this, "Please Provide your registered Email Address", Toast.LENGTH_SHORT).show();
        }

    }

    public void startSignup(View view) {
        startActivity(new Intent(getApplicationContext(), Signup.class));
    }
}