package com.example.medicman;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.medicman.Initialization.CHANNEL_1_ID;

public class AlertReceiver extends BroadcastReceiver {
    private NotificationManagerCompat notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {

        String medicine_name = intent.getStringExtra("medicine_name");
        String username = intent.getStringExtra("username");
        float medicine_dosage = intent.getFloatExtra("medicine_dosage",0);
        int id=intent.getIntExtra("id",0);
       Uri image_uri = Uri.parse(intent.getStringExtra("image_uri"));
        Bitmap b=null;
        try {
           b = MediaStore.Images.Media.getBitmap(context.getContentResolver(), image_uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title="Hey, "+username+" It's Medicine Time";
        String message="Please Take "+medicine_dosage+" pills of "+medicine_name;


        notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(b)
                .setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(b)
                .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(id, notification);

    }
}
