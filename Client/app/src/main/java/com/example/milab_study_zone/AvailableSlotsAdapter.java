package com.example.milab_study_zone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvailableSlotsAdapter extends RecyclerView.Adapter<AvailableSlotsAdapter.AvailableSlotsViewHolder> {
    private List<GetStudyZoneFetcher> availableSlots;
    private OnSlotReservedListener onSlotReservedListener;

    public void setOnSlotReservedListener(OnSlotReservedListener listener) {
        this.onSlotReservedListener = listener;
    }

    public AvailableSlotsAdapter(List<GetStudyZoneFetcher> availableSlots) {
        this.availableSlots = availableSlots;
    }

    @NonNull
    @Override
    public AvailableSlotsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.available_slot_item, parent, false);
        return new AvailableSlotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableSlotsViewHolder holder, int position) {
        GetStudyZoneFetcher availableSlot = availableSlots.get(position);
        holder.nameTextView.setText(availableSlot.getlocation());
        holder.reservedCheckBox.setChecked(availableSlot.isAvailable());
        holder.reservedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onSlotReservedListener != null) {
                    onSlotReservedListener.onSlotReservedChange(position, isChecked);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return availableSlots.size();
    }

    public static class AvailableSlotsViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public CheckBox reservedCheckBox;

        public AvailableSlotsViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.location);
            reservedCheckBox = itemView.findViewById(R.id.reservedCheckBox);
        }
    }
    public interface OnSlotReservedListener {
        void onSlotReservedChange(int position, boolean isChecked);
    }

}
