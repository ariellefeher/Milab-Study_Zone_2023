package com.example.milab_study_zone;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IvcherBuilding extends AppCompatActivity {

    private TextView placeNameTextView;
    private TextView placeDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivcher_building);

        placeNameTextView = findViewById(R.id.ivcher_place_name);

        // Retrieve data from the marker that was clicked and update the UI elements
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String placeName = extras.getString("placeName");
            String placeDescription = extras.getString("placeDescription");

            placeNameTextView.setText(placeName);
            placeDescriptionTextView.setText(placeDescription);
        }
    }
}