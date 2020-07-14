package com.example.medicman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    private ImageView ivSplash;
    private TextView tvSplash;
    private Animation ivAnim;
    private Animation tvAnim;

    private final int TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivSplash = findViewById(R.id.iv_splash);
        tvSplash = findViewById(R.id.tv_splash);

        ivAnim = AnimationUtils.loadAnimation(this, R.anim.splash_logo_anim);
        tvAnim = AnimationUtils.loadAnimation(this, R.anim.splash_text_anim);

        ivSplash.setAnimation(ivAnim);
        tvSplash.setAnimation(tvAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), Home.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(i);
            }
        }, TIME_OUT);
    }
}