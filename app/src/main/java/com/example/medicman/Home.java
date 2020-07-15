package com.example.medicman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        floatingActionButton=findViewById(R.id.add_medicine);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Add_medication.class);
                startActivity(i);
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()){
                    case R.id.history:
                        i =new Intent(getApplicationContext(),History.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.mprofile:
                        i =new Intent(getApplicationContext(),Profile.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.info:
                        i =new Intent(getApplicationContext(),Medicine_info.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        }
    }
}