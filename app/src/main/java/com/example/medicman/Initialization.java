package com.example.medicman;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Initialization extends Application {

    public static final String CHANNEL_1_ID = "channel1";
//    static UserInfo userInfoFromFirebase;
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null){

//            Toast.makeText(this, "loggedin", Toast.LENGTH_SHORT).show();
//            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference ref = database.getReference("User/" + user.getUid() + "/UserInfo");
//
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                        userInfoFromFirebase=dataSnapshot.getValue(UserInfo.class);
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    System.out.println("The read failed: " + databaseError.getCode());
//                }
//            });
        }else {
            Toast.makeText(this, "not", Toast.LENGTH_SHORT).show();
        }

        createNotificationChannels();

    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.setDescription("This is Channel 1");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
