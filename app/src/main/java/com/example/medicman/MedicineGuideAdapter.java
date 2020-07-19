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

    OnMedInfoClickListener medClickListener;

    public MedicineGuideAdapter(@NonNull FirebaseRecyclerOptions<MedicineUserGuideInfo> options, OnMedInfoClickListener listener) {
        super(options);
        medClickListener = listener;
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

        return new MedicineGuideAdapter.MyHolder(view, medClickListener);
    }

    public interface OnMedInfoClickListener {
        void onMedClick(int position);
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView medName;
        OnMedInfoClickListener medClickListener;

        public MyHolder(@NonNull View itemView, OnMedInfoClickListener listener) {
            super(itemView);
            medName = itemView.findViewById(R.id.tv_medicine_info);
            medClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            medClickListener.onMedClick(getAdapterPosition());
        }
    }
}
