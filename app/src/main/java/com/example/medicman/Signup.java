package com.example.medicman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etPass1;
    private EditText etPass2;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.et_email_sigup);
        etPass1 = findViewById(R.id.et_pass_sigup);
        etPass2 = findViewById(R.id.et_passConf_signup);
        lottieAnimationView=findViewById(R.id.loading_anim);
    }

    public void registerDetails(View view) {
        String email = etEmail.getText().toString().trim();
        String p1 = etPass1.getText().toString();
        String p2 = etPass2.getText().toString();
        String regX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$";
        //Pattern.compile(regX).matcher(email).matches()
        if(!email.equals("")){
            if(p1.equals(p2)){
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                mAuth.createUserWithEmailAndPassword(email, p1)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    lottieAnimationView.cancelAnimation();
                                    Intent i = new Intent(getApplicationContext(), Setup.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    finish();
                                    startActivity(i);
                                    Toast.makeText(Signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                }else{
                                    lottieAnimationView.cancelAnimation();
                                    lottieAnimationView.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Signup.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoLogin(View view) {
        Intent i = new Intent(getApplicationContext(),Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);
    }
}