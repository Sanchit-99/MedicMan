package com.example.medicman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


    }

    public void backfromprofile(View view) {
        Intent i = new Intent(getApplicationContext(),Home.class);
        startActivity(i);
        finish();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getApplicationContext(),Login.class);
        startActivity(i);
        finish();

    }
}