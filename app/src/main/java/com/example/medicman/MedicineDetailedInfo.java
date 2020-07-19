package com.example.medicman;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MedicineDetailedInfo extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_detailed_info);
        TextView tvMedTitle = findViewById(R.id.tv_med_title);
        TextView tvSideEffects = findViewById(R.id.tv_med_sideEffects);
        TextView tvUsage = findViewById(R.id.tv_med_usage);

        toolbar = findViewById(R.id.tlbr_medinfo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Medicine Information");
        toolbar.setTitleTextColor(Color.WHITE);


        if (getIntent().hasExtra("MedInfo")) {
            MedicineUserGuideInfo med = getIntent().getParcelableExtra("MedInfo");
            tvMedTitle.setText(med.getMedName());
            tvSideEffects.setText(med.getMedSideEffects());
            tvUsage.setText(med.getMedSideEffects());
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}