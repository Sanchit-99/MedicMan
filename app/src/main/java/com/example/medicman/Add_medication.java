package com.example.medicman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

//import static com.example.medicman.Home.MedicinArray;
import static com.example.medicman.Home.unique_id;
import static com.example.medicman.Home.userInfoFromFirebase;

public class Add_medication extends AppCompatActivity {

    Calendar selected_time;
    StorageReference storageRef;
    FirebaseStorage storage;
    TextView dosage_txt,time_txt;
    ImageView imageView;
    Toolbar tb;
    EditText medicineName;
    float dosage;
    int  mHour, mMinute,msec;
    String time;
    Uri uri, downloadUri;
    Bitmap b;
    DatabaseReference ref;
    String key;
    LottieAnimationView loading_anim,done_anim;
    private static final int RESULT_LOAD_IMAGE_CAMERA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medication);
        loading_anim=findViewById(R.id.loading_anim);
        done_anim=findViewById(R.id.done_anim);
        medicineName=findViewById(R.id.med_name);
        uri=null;
        downloadUri =null;
        ref=FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MedicineInfo");
        dosage=(float)0;
        dosage_txt=findViewById(R.id.dosage_txt);
        time_txt=findViewById(R.id.alarm_time_txt);
        imageView=findViewById(R.id.img_view);
        tb=findViewById(R.id.tlbr);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Medication");
        tb.setTitleTextColor(Color.WHITE);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void addEntry(View view) {
         key =ref.push().getKey();
        if(time==null || dosage==0.0 || medicineName.getText().toString().equals("") ){
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        uploadImageToFirebase();


    }

    private void uploadDataToFirebase() {
        MedicineInfo info = new MedicineInfo();
        info.setName(medicineName.getText().toString());
        info.setTime("Time : "+time);
        info.setDosage("Dosage : "+dosage+" Pills");
        info.setImage_url(""+ downloadUri);
        info.setId(unique_id);
       // MedicinArray.add(info);

        info.setKey(key);
        ref.child(key).setValue(info);
    }

    private void uploadImageToFirebase() {

        if (uri!=null) {

            loading_anim.setVisibility(View.VISIBLE);
            loading_anim.playAnimation();
            storageRef = FirebaseStorage.getInstance().getReference();
            final StorageReference storageReference = storageRef
                    .child("Images/" + FirebaseAuth.getInstance().getCurrentUser().getUid() +"/medicine_" + key + ".jpg");
            UploadTask uploadTask = storageReference.putFile(uri);

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
                        downloadUri = task.getResult();

                        //set Notification
                        setNotification();
                        uploadDataToFirebase();
                        updateID();
                        loading_anim.cancelAnimation();
                        loading_anim.setVisibility(View.GONE);
                        done_anim.setVisibility(View.VISIBLE);
                        done_anim.setSpeed((float)0.7);
                        done_anim.playAnimation();
                        done_anim.addAnimatorListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                done_anim.cancelAnimation();
                                done_anim.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        Toast.makeText(Add_medication.this, "Medicine Sucessfully Added", Toast.LENGTH_SHORT).show();
                    } else {
                        loading_anim.cancelAnimation();
                        loading_anim.setVisibility(View.GONE);
                        Toast.makeText(Add_medication.this, "ERROR:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
//                btnSave.setVisibility(View.VISIBLE);
                }
            });
        }else {
            Toast.makeText(this, "Please upload image of MEDICINE", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateID() {
        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("UniqueIdGenerator").setValue(unique_id+1);
    }

    private void setNotification() {
        String username=userInfoFromFirebase.userName;
        int id = unique_id;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("medicine_name",medicineName.getText().toString());
        intent.putExtra("image_uri",uri.toString());
        intent.putExtra("medicine_dosage",dosage);
        intent.putExtra("username",username);
        intent.putExtra("id",id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        if (selected_time.before(Calendar.getInstance())) {
            selected_time.add(Calendar.DATE, 1);
        }
        Toast.makeText(this, ""+selected_time.getTimeInMillis(), Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP,
                    selected_time.getTimeInMillis(),selected_time.getTimeInMillis()+2000, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, selected_time.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, selected_time.getTimeInMillis(), pendingIntent);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                selected_time.getTimeInMillis(),
                24*60*60*1000,
                pendingIntent);

    }

    public void subtractDosage(View view) {
        if(dosage==0.0){
            return;
        }
        dosage-=0.5;
        dosage_txt.setText(""+dosage);
    }

    public void addDosage(View view) {
        dosage+=0.5;
        dosage_txt.setText(""+dosage);
    }

    public void setTime(View view) {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    //    msec=c.get(Calendar.SECOND);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                         selected_time = Calendar.getInstance();
                        selected_time.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        selected_time.set(Calendar.MINUTE,minute);
                        selected_time.set(Calendar.SECOND,0);
                        String delegate = "hh:mm aaa";
                        time=""+ DateFormat.format(delegate,selected_time.getTime());
                        time_txt.setText(time);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void add_image(View view) {
         camera();
    }

    public void camera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        RESULT_LOAD_IMAGE_CAMERA);
            } else {
                ImagePicker.Companion.with(this)
                        .crop()
                        .compress(500)
                        .crop(16f, 9f)
                        .saveDir(new File(Environment.getExternalStorageDirectory(), "MedicMan"))
                        .start();
            }
        } else {
            ImagePicker.Companion.with(this)
                    .cameraOnly()
                    .crop()
                    .compress(1024)
                    .crop(16f, 9f)
                    .saveDir(new File(Environment.getExternalStorageDirectory(), "MedicMan"))
                    .start();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RESULT_LOAD_IMAGE_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    camera();
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(resultCode == Activity.RESULT_OK){
                uri=data.getData();
             //   Toast.makeText(this, ""+uri, Toast.LENGTH_SHORT).show();
             try {
                 b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
             } catch (IOException e) {
                 e.printStackTrace();
             }
             imageView.setBackground(new BitmapDrawable(getResources(), b));
        }
    }

}