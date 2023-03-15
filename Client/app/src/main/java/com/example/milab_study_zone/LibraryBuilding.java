package com.example.milab_study_zone;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LibraryBuilding extends AppCompatActivity {

    private TextView placeNameTextView;
    private TextView placeDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_building);

        placeNameTextView = findViewById(R.id.lib_place_name);
        placeDescriptionTextView = findViewById(R.id.lib_place_description);

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