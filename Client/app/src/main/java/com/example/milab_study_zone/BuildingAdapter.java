package com.example.milab_study_zone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder> {
    private JSONArray study_reservations;

    public BuildingAdapter(JSONArray study_reservations) {
        this.study_reservations = study_reservations;
    }

    @Override
    public BuildingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_building_reservations, parent, false);
        return new BuildingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BuildingViewHolder holder, int position) {
        try {
            JSONObject reservation = study_reservations.getJSONObject(position);

           if(reservation.getBoolean("isAvailable") == true) {
               holder.dayTextView.setText(reservation.getString("Day"));
           }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return study_reservations.length();
    }

    public static class BuildingViewHolder extends RecyclerView.ViewHolder {
        public TextView dayTextView;

        public BuildingViewHolder(View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
        }
    }
}
