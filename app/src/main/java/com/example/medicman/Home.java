package com.example.medicman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    MedicineAdapter adapter;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    TextView home_banner;
    static UserInfo userInfoFromFirebase;
    //static ArrayList<MedicineInfo> MedicinArray;
    static int unique_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        home_banner=findViewById(R.id.home_banner);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();



        if (user == null){
            Intent intent = new Intent(Home.this,Login.class);
            startActivity(intent);
            finish();
        }else {
            //MedicinArray = new ArrayList<>();

          //  Toast.makeText(this, "loggedin", Toast.LENGTH_SHORT).show();
             FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("User/" + user.getUid() + "/UserInfo");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userInfoFromFirebase=dataSnapshot.getValue(UserInfo.class);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

            DatabaseReference medRef = FirebaseDatabase.getInstance().getReference().child("User")
                    .child(mAuth.getCurrentUser().getUid());
            medRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild("MedicineInfo")) {
                        home_banner.setVisibility(View.GONE);
                    }else {
                        home_banner.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.home);
            floatingActionButton = findViewById(R.id.add_medicine);
            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            FirebaseRecyclerOptions<MedicineInfo> options =
                    new FirebaseRecyclerOptions.Builder<MedicineInfo>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MedicineInfo"), MedicineInfo.class)
                            .build();

            adapter = new MedicineAdapter(options,this);
            recyclerView.setAdapter(adapter);

//            database = FirebaseDatabase.getInstance();
//            DatabaseReference ref = database.getReference("User/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/MedicineInfo");
//
//// Attach a listener to read the data at our posts reference
//            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        MedicineInfo user = postSnapshot.getValue(MedicineInfo.class);
//                        MedicinArray.add(user);
//                        Log.d("Home : ", MedicinArray.toString());
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    System.out.println("The read failed: " + databaseError.getCode());
//                }
//            });

            //fetching unique id
            FirebaseDatabase databaseforid = FirebaseDatabase.getInstance();
            DatabaseReference ref_id = databaseforid.getReference("User/" + user.getUid() + "/UniqueIdGenerator");

            ref_id.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    unique_id=dataSnapshot.getValue(Integer.class);
                    Log.d("home","id updated "+unique_id);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), Add_medication.class);
                    startActivity(i);
                }
            });
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Intent i;
                    switch (item.getItemId()) {
//                        case R.id.history:
//                            i = new Intent(getApplicationContext(), History.class);
//                            startActivity(i);
//                            finish();
//                            overridePendingTransition(0, 0);
//                            break;
                        case R.id.mprofile:
                            i = new Intent(getApplicationContext(), Profile.class);
                            startActivity(i);
                            finish();
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.info:
                            i = new Intent(getApplicationContext(), Medicine_info.class);
                            startActivity(i);
                            finish();
                            overridePendingTransition(0, 0);
                            break;
                    }
                    return false;
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){

        }else{
            startActivity(new Intent(Home.this,Login.class));
            finish();
        }
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    protected void onResume() {

        super.onResume();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null){
            Intent intent = new Intent(Home.this,Login.class);
            startActivity(intent);
            finish();
        }
    }
}