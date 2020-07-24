package com.example.medicman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

    Toolbar tb;
    TextView about_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        about_txt=findViewById(R.id.about_txt);
        tb=findViewById(R.id.tlbr);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("About");
        tb.setTitleTextColor(Color.WHITE);
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.faq:
                        startActivity(new Intent(getApplicationContext(),FAQ.class));
                        break;
                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(),Contact.class));
                        break;
                }
                return false;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            about_txt.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void yash_clicked(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/yashtank311/"));
        startActivity(browserIntent);
    }

    public void sanchit_clicked(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/sanchit187/"));
        startActivity(browserIntent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}