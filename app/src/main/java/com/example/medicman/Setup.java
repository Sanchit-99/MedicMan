package com.example.medicman;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setup extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseUser firebaseUser;

    TextView tvEmail;
    TextView tvDOB;
    EditText etUserName;
    EditText providerPhNo;
    RadioGroup rgGender;
    CircleImageView ivProfile;
    Button btnSave;
    Button btnSkip;


    Uri downloadUrl;
    Uri imageUri;
    String selectedGender;
    private boolean clickedOnRadiobtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
        init();
    }

    private void init() {
        tvEmail = findViewById(R.id.tv_user_email);
        tvDOB = findViewById(R.id.tv_dob);
        etUserName = findViewById(R.id.et_userName_setup);
        rgGender = findViewById(R.id.rg_gender);
        ivProfile = findViewById(R.id.setup_profile_img);
        providerPhNo = findViewById(R.id.et_phNo);
        btnSave = findViewById(R.id.btn_save);
        btnSkip = findViewById(R.id.btn_skip);
        downloadUrl = null;
        imageUri = null;
        selectedGender = "Male";

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        tvEmail.setText(firebaseUser.getEmail());
    }

    public void chooseProfile(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        RESULT_LOAD_IMAGE);
            } else {
                /*CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(Setup.this);*/
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), RESULT_LOAD_IMAGE);
            }
        } else {
            //low then marshmallow
            /*CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(Setup.this);*/
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), RESULT_LOAD_IMAGE);
        }
    }

    public void openDatePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int y,
                                          int m, int d) {
                        tvDOB.setText(d + "/" + (m + 1) + "/" + y);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    /*private void getImage() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
        } else {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RESULT_LOAD_IMAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    chooseProfile(ivProfile.getRootView());
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*switch (requestCode) {
            case RESULT_LOAD_IMAGE: {
                if (resultCode == RESULT_OK) {
                    Bitmap bmp = null;
                    try {
                        InputStream is = getContentResolver().openInputStream(data.getData());
                        bmp = BitmapFactory.decodeStream(is);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }*/
        if (requestCode == RESULT_LOAD_IMAGE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                ivProfile.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void saveDetailsToDB(View view) {
        if (etUserName.getText().toString().equals(""))
            Toast.makeText(this, "Empty User name", Toast.LENGTH_SHORT).show();
        if (providerPhNo.getText().toString().equals(""))
            Toast.makeText(this, "Invalid Phone number", Toast.LENGTH_SHORT).show();

        if (imageUri != null) {
            btnSave.setVisibility(View.INVISIBLE);
            storageRef = storage.getReferenceFromUrl("gs://medicman-8ca63.appspot.com");
            final StorageReference storageReference = storageRef.child("UserProfileImages/" + firebaseUser.getUid() + "/profile.jpg");
            UploadTask uploadTask = storageReference.putFile(imageUri);

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
                        downloadUrl = task.getResult();
                        storeToDb();
                        Toast.makeText(Setup.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(Setup.this, "ERROR:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    btnSave.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void storeToDb() {
        if (tvDOB.getText().toString().equals("")) {
            Toast.makeText(this, "Choose date of birth", Toast.LENGTH_SHORT).show();
            openDatePicker(ivProfile.getRootView());
        }
        if (!clickedOnRadiobtn) {
            Toast.makeText(this, "Select Your gender", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etUserName.getText().toString() == "")
            etUserName.setText("Default User");
        if (providerPhNo.getText().toString() == "")
            providerPhNo.setText("1234567890");


        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(tvEmail.getText().toString());
        userInfo.setGender(selectedGender);
        userInfo.setProfileUrl(downloadUrl == null ? "" : downloadUrl.toString());
        userInfo.setUserName(etUserName.getText().toString());
        userInfo.setProviderPhNo(providerPhNo.getText().toString());
        userInfo.setDob(tvDOB.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("User").child(firebaseUser.getUid()).setValue(userInfo);
    }

    public void skipSetup(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("You can set your profile later from the profile screen.")
                .setTitle("NOTE")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        storeToDb();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    public void getGender(View view) {
        clickedOnRadiobtn = true;
        switch (view.getId()) {
            case R.id.rb_male: {
                if (((RadioButton) view).isSelected())
                    selectedGender = "Male";
            }
            break;
            case R.id.rb_female: {
                if (((RadioButton) view).isSelected())
                    selectedGender = "Female";
            }
            break;
        }
    }
}
