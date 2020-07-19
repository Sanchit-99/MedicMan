package com.example.medicman;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import static com.example.medicman.Home.MedicinArray;

public class MedicineAdapter extends FirebaseRecyclerAdapter<MedicineInfo, MedicineAdapter.MyHolder> {


    Context context;
    public MedicineAdapter(@NonNull FirebaseRecyclerOptions<MedicineInfo> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, final int position, @NonNull final MedicineInfo model) {
        holder.name.setText(model.getName());
        holder.dose.setText(model.getDosage());
        holder.time.setText(model.getTime());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implement alarm cancel
                //delete from db
                //delete from ui
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlertReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, model.id, intent, 0);
                alarmManager.cancel(pendingIntent);
                //MedicinArray.remove(position);
           //    Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();

                //delete data
                FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("MedicineInfo").child(model.key).removeValue();

                //delete image
                FirebaseStorage.getInstance().getReference().child("Images").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("medicine_"+model.key+".jpg").delete();
//                FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .child("MedicineInfo").setValue(MedicinArray);

            }
        });

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new MyHolder(view);
    }

    class MyHolder extends RecyclerView.ViewHolder  {

        TextView name,dose,time;
        ImageView delete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.medicine_name);
            dose=itemView.findViewById(R.id.medicine_dosage);
            time=itemView.findViewById(R.id.medicine_time);
            delete=itemView.findViewById(R.id.delete);


        }
    }

}

