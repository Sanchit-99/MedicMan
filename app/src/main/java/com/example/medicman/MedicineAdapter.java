package com.example.medicman;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MedicineAdapter extends FirebaseRecyclerAdapter<MedicineInfo, MedicineAdapter.MyHolder> {


    public MedicineAdapter(@NonNull FirebaseRecyclerOptions<MedicineInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull MedicineInfo model) {
        holder.name.setText(model.getName());
        holder.dose.setText(model.getDosage());
        holder.time.setText(model.getTime());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implement alarm cancel
                //delete from db
                //delete from ui
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

