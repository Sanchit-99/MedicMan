package com.example.medicman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()){
                    case R.id.home:
                        i =new Intent(getApplicationContext(),Home.class);
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
                    case R.id.history:
                        i =new Intent(getApplicationContext(),History.class);
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