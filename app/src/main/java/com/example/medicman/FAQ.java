package com.example.medicman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class FAQ extends AppCompatActivity {

    Toolbar tb;
    TextView ans_1,ans_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);

        ans_1=findViewById(R.id.txt_1);
        ans_2=findViewById(R.id.txt_2);

        tb=findViewById(R.id.tlbr);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Frequently Asked Questions");
        tb.setTitleTextColor(Color.WHITE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ans_1.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            ans_2.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}