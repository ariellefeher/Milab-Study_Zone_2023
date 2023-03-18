package com.example.milab_study_zone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EntreBuilding extends AppCompatActivity {

    private TextView placeNameTextView;
    private TextView placeDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entre_building);

        placeNameTextView = findViewById(R.id.entre_place_name);
        placeDescriptionTextView = findViewById(R.id.entre_place_description);

        // Retrieve data from the marker that was clicked and update the UI elements
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String placeName = extras.getString("placeName");
            String placeDescription = extras.getString("placeDescription");

            placeNameTextView.setText(placeName);
            placeDescriptionTextView.setText(placeDescription);
        }

        Button backButtonEntre = findViewById(R.id.backButtonEntre);
        backButtonEntre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

}