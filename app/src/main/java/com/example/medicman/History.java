package com.example.medicman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class History extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()){
                    case R.id.info:
                        i =new Intent(getApplicationContext(),Medicine_info.class);
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
                    case R.id.home:
                        i =new Intent(getApplicationContext(),Home.class);
                         startActivity(i);
                        finish();
                         overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }
}