package com.example.medicman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

import static com.example.medicman.Initialization.userInfoFromFirebase;

public class Profile extends AppCompatActivity {


    TextView email,dob;
    EditText username,number;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    CircleImageView profile_pic;
    String imageurl;
    Uri new_image_uri;
    Uri new_download_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        init();
        setdata();
    }

    private void setdata() {
        email.setText(userInfoFromFirebase.email);
        dob.setText(userInfoFromFirebase.dob);
        username.setText(userInfoFromFirebase.userName);
        number.setText(userInfoFromFirebase.providerPhNo);
        imageurl=userInfoFromFirebase.getProfileUrl();
        Glide.with(this).load(imageurl).into(profile_pic);

    }

    private void init() {
        new_download_uri=null;
        new_image_uri=null;
         firebaseAuth = FirebaseAuth.getInstance();
         user = firebaseAuth.getCurrentUser();
        email=findViewById(R.id.user_email_firebase);
        dob=findViewById(R.id.user_dob_firebase);
        username=findViewById(R.id.username_firebase);
        number=findViewById(R.id.medicine_provider_firebase);
        profile_pic=findViewById(R.id.profile_pic);
    }

    public void backfromprofile(View view) {
        Intent i = new Intent(getApplicationContext(),Home.class);
        startActivity(i);
        finish();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getApplicationContext(),Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();

    }

    public void update(View view) {
        String new_username=username.getText().toString();
        String new_number=number.getText().toString();

        if(new_number.equals("") || new_username.equals("")){
            Toast.makeText(this, "Fields Can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(new_image_uri!=null){
            //user changed image, upload to db

            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://medicman-8ca63.appspot.com");
            final StorageReference storageReference = storageRef.child("Images/" + user.getUid() + "/profile.jpg");
            UploadTask uploadTask = storageReference.putFile(new_image_uri);

            Task<Uri> urlTask;
            urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        new_download_uri = task.getResult();
                        userInfoFromFirebase.setProfileUrl(new_download_uri.toString());
                        Toast.makeText(Profile.this, "Picture Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Profile.this, "ERROR:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        userInfoFromFirebase.setUserName(new_username);
        userInfoFromFirebase.setProviderPhNo(new_number);
        FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("UserInfo").setValue(userInfoFromFirebase);
    }

    public void changePhoto(View view) {
        ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            new_image_uri = data.getData();
            Bitmap b= null;
            try {
                b = MediaStore.Images.Media.getBitmap(this.getContentResolver(),new_image_uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            profile_pic.setImageDrawable(new BitmapDrawable(getResources(),b));
        }
    }
}