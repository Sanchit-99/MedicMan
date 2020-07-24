package com.example.medicman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.IOException;

public class MedicineImageViewer extends AppCompatActivity {

    Toolbar tb;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_image_viewer);


        Intent i = getIntent();
        Uri uri = Uri.parse(i.getStringExtra("image_uri"));
        String name = i.getStringExtra("med_name");
        tb=findViewById(R.id.tlbr);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(name);
        tb.setTitleTextColor(Color.WHITE);

        imageView=findViewById(R.id.medicine_image);

        try {
            Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            imageView.setBackground(new BitmapDrawable(getResources(), b));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}