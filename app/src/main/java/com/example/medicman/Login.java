package com.example.medicman;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etPass;
    LottieAnimationView lottieAnimationView;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.et_email_login);
        etPass = findViewById(R.id.et_pass_login);
        btnLogin = findViewById(R.id.btn_login_login);
        lottieAnimationView=findViewById(R.id.loading_anim);
    }

    public void login(final View view) {

        String memail = etEmail.getText().toString().trim();
        String mpass = etPass.getText().toString();

        if (!memail.equals("") && !mpass.equals("")) {
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
            mAuth.signInWithEmailAndPassword(memail, mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        lottieAnimationView.cancelAnimation();
                        Intent i = new Intent(getApplicationContext(), Home.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(i);
                    } else {
                        lottieAnimationView.cancelAnimation();
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                        String error = task.getException().getMessage();
                        Toast.makeText(Login.this, "Error: User not found" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Please fill the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void forgotPass(View view) {
        String email;

        email = etEmail.getText().toString();
        if (!email.equals("")) {
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
        } else {
            Toast.makeText(this, "Please Provide your registered Email Address", Toast.LENGTH_SHORT).show();
        }

    }

    public void startSignup(View view) {
        startActivity(new Intent(getApplicationContext(), Signup.class));
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}