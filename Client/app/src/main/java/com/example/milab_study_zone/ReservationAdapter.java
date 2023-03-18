package com.example.milab_study_zone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
    private JSONArray study_reservations;

    public ReservationAdapter(JSONArray study_reservations) {
        this.study_reservations = study_reservations;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        try {
            JSONObject reservation = study_reservations.getJSONObject(position);
            holder.studyAreaTextView.setText(reservation.getString("study_area"));
            holder.dateTextView.setText(reservation.getString("date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return study_reservations.length();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        public TextView studyAreaTextView;
        public TextView dateTextView;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            studyAreaTextView = itemView.findViewById(R.id.studyAreaTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
