package com.example.medicman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MedicineGuideAdapter extends FirebaseRecyclerAdapter<MedicineUserGuideInfo, MedicineGuideAdapter.MyHolder> {

    public MedicineGuideAdapter(@NonNull FirebaseRecyclerOptions<MedicineUserGuideInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull MedicineUserGuideInfo model) {
        holder.medName.setText(model.getMedName());
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_info_item, parent, false);

        return new MedicineGuideAdapter.MyHolder(view);
    }

    class MyHolder extends RecyclerView.ViewHolder  {

        TextView medName;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            medName=itemView.findViewById(R.id.tv_medicine_info);
        }
    }
}
