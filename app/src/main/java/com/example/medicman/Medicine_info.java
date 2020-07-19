package com.example.medicman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Medicine_info extends AppCompatActivity implements MedicineGuideAdapter.OnMedInfoClickListener {
    BottomNavigationView bottomNavigationView;
    RecyclerView rvMedicineInfo;
    MedicineGuideAdapter adapter;
    static ArrayList<MedicineUserGuideInfo> medicineList;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_info);

        init();
    }


    public void init(){
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.info);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()){
                    case R.id.home:
                        i=new Intent(getApplicationContext(),Home.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.history:
                        i =new Intent(getApplicationContext(),History.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0,0);
                        break;
                    case R.id.mprofile:
                        i =new Intent(getApplicationContext(),Profile.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        medicineList = new ArrayList<>();
        rvMedicineInfo = findViewById(R.id.rv_med_info);

        rvMedicineInfo.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MedicineUserGuideInfo> options =
                new FirebaseRecyclerOptions.Builder<MedicineUserGuideInfo>()
                .setQuery(FirebaseDatabase.getInstance().getReference("MedicineUserGuideInfo"), MedicineUserGuideInfo.class)
                .build();

        adapter = new MedicineGuideAdapter(options, this);
        rvMedicineInfo.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MedicineUserGuideInfo");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    MedicineUserGuideInfo med = postSnapshot.getValue(MedicineUserGuideInfo.class);
                    medicineList.add(med);
                    Log.d("Info***", medicineList.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        rvMedicineInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = rvMedicineInfo.getChildLayoutPosition(view);
                Toast.makeText(Medicine_info.this, medicineList.get(pos).getMedName(), Toast.LENGTH_SHORT).show();
            }
        });


        Log.d("Med Info", medicineList.size()+"");
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onMedClick(int position) {
        Toast.makeText(this, medicineList.get(position).getMedName() + " clicked", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MedicineDetailedInfo.class);
        i.putExtra("MedInfo", medicineList.get(position));
        startActivity(i);

    }
}